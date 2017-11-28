package backend.models;

import backend.files.FileManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class used to keep track of the pictures and its tags.
 *
 * Each time a picture is added to this class, any changes to that picture (such as name changes)
 * will reflect the associated file changes in the OS.
 *
 * @author Emilio Kartono, Shimi Smith, Tarry Dang
 * @version 2
 */
public class PictureManager implements Observer {

  /**
   * A list of pictures that this class keeps track of.
   */
  private ArrayList<Picture> pictures = new ArrayList<Picture>();

  /**
   * A list of all tags that's used by all pictures.
   */
  private ArrayList<Tag> availableTags = new ArrayList<Tag>();

  /**
   * The current directory of this manager.
   */
  private String currDir = "";

  /**
   * True if the manager recursively adds all pictures under that directory.
   */
  private boolean isRecursive = false;

  /**
   * The compiled regex Pattern for filename check.(avoid multiple regex compilation for
   * efficiency). Example: "@.jpg" is not a valid name file name cannot contain any special letter
   * (except for tags and file extension)
   */
  private static final Pattern fileNameSpec = Pattern.compile("\\w+(\\s\\w+)*(\\s@\\w+)*");

  /**
   * Populate the picture manager with pictures under a certain directory
   *
   * @param directoryPath A directory path
   * @param recursive Determines whether to grab the images recursively or not.
   * @throws IOException Thrown when the directory does not exist.
   */
  public PictureManager(String directoryPath, boolean recursive) throws IOException {
    this.currDir = directoryPath;
    this.isRecursive = recursive;

    List<File> files;
    if (recursive)
      files = FileManager.getImageListRec(directoryPath);
    else
      files = FileManager.getImageList(directoryPath);

    for (File file : files) {
      if (this.nameCheck(file)) {
        Picture picture = new Picture(file.getAbsolutePath());
        this.addPicture(picture);
      }
    }
  }

  /**
   * Check if the file contains a valid name.
   * 
   * @param file The file
   * @return True if the file has a valid name; else false.
   */
  private boolean nameCheck(File file) {
    String nameWithoutFileExtension = file.getName().split("\\.")[0].trim();
    Matcher m = fileNameSpec.matcher(nameWithoutFileExtension);
    if (m.matches()) {
      return true;
    }
    return false;
  }

  /**
   * Creates a PictureManager instance with no pictures to keep track of.
   */
  public PictureManager() {}

  /**
   * Return a copy of the list of pictures that this class has.
   *
   * @return A list of pictures in this class.
   */
  public ArrayList<Picture> getPictures() {
    return new ArrayList<Picture>(pictures);
  }

  /**
   * Returns a list of pictures stored in this class that have that tag.
   * 
   * @param tag The tag to search for
   * @return A list of pictures that this tag belongs to.
   */
  public ArrayList<Picture> getPicturesWithTag(Tag tag) {
    ArrayList<Picture> picturesWithTags = new ArrayList<>();
    for (Picture picture : pictures) {
      if (picture.containsTag(tag))
        picturesWithTags.add(picture);
    }
    return picturesWithTags;
  }

  /**
   * Deletes a tag. It will update all the pictures in this instance that has this tag so that these
   * pictures do not have the deleted tag anymore.
   *
   * @param tag The tag to delete.
   */
  public void deleteTag(Tag tag) {
    for (Picture picture : pictures) {
      if (picture.containsTag(tag)) {
        picture.deleteTag(tag);
      }
    }
    this.availableTags.remove(tag);
  }

  /**
   * Add a new tag to the availableTags. If the tag already exist in the available tags, it will do
   * nothing.
   * 
   * @param tag A new tag to add to the collection
   */
  public void addTagToCollection(Tag tag) {
    if (!availableTags.contains(tag)) {
      this.availableTags.add(tag);
    }
  }

  /**
   * Adds a unique picture to this class. It will also add this instance as an observer to the
   * picture as well as add any tags from the picture not in the collection to the collection. If
   * the picture already exist, it will not add it. To see if a picture exist, refer to the
   * Picture.equals() to see if two pictures are equal.
   * 
   * @param picture A picture to add
   */
  public void addPicture(Picture picture) {
    if (!pictures.contains(picture)) {
      pictures.add(picture);
      picture.addObserver(this);

      for (Tag tag : picture.getTags()) {
        if (availableTags.contains(tag)) {
          this.replaceTagInPicture(picture, tag);
        } else {
          this.availableTags.add(tag);
        }
      }
    }
  }

  /**
   * helper function for replacing the tag with the actual tag that is being observed. See
   * Picture.replaceTag(Tag tag)
   * 
   * @param picture
   * @param tag
   */
  private void replaceTagInPicture(Picture picture, Tag tag) {
    for (Tag thisTag : this.availableTags) {
      if (thisTag.equals(tag)) {
        picture.replaceTag(thisTag);
        break;
      }
    }
  }

  /**
   * Untracks a picture from this class and the picture no longer becomes observed from this class.
   * If the picture does not exist, it will do nothing
   * 
   * @param picture A picture in this class to untrack from.
   */
  public void untrackPicture(Picture picture) {
    for (Picture thePicture : this.pictures) {
      if (thePicture.equals(picture)) {
        pictures.remove(thePicture);
        thePicture.deleteObserver(this);
        break;
      }
    }
  }

  /**
   * Determines whether a picture is in this instance or not. If it is in this instance, it is being
   * tracked.
   * 
   * @param picture A picture to test
   * @return True if the picture is in this instance; else false.
   */
  public boolean contains(Picture picture) {
    return pictures.contains(picture);
  }

  /**
   * Determines whether a tag is in this instance or not.
   * 
   * @param tag A tag to test.
   * @return True if the tag is in this collection of tags; else false.
   */
  public boolean contains(Tag tag) {
    return this.availableTags.contains(tag);
  }

  /**
   * This method is called whenever the observed object is changed. When the arguements and the
   * observable object is a Picture object, and this instance is keeping track of this object, it
   * will perform the changes of the Picture class in the IO level.
   *
   * @param o the observable object.
   * @param arg an argument passed to the <code>notifyObservers</code>
   */
  @Override
  public void update(Observable o, Object arg) {
    // If there is a commands with the pictures
    if (o instanceof Picture && arg instanceof Picture) {
      Picture newPicture = (Picture) o;
      Picture oldPicture = (Picture) arg;
      this.updatePicture(newPicture, oldPicture);
    }
  }

  /**
   * A helper function for the update(), where changes to a picture will reflect the changes in the
   * OS. Note: if newPicture does not exist in this class, it will do nothing.
   * 
   * @param newPicture The picture with the new properties
   * @param oldPicture A copy of newPicture, but with its properties from an earlier state.
   */
  private void updatePicture(Picture newPicture, Picture oldPicture) {
    if (pictures.contains(newPicture)) {

      // If there was a directory change
      if (!newPicture.getDirectoryPath().equals(oldPicture.getDirectoryPath())) {
        FileManager.moveFile(oldPicture.getAbsolutePath(), newPicture.getDirectoryPath());
      }

      // If there was a file name change
      if (!newPicture.getFullFileName().equals(oldPicture.getFullFileName())) {
        String fileName = newPicture.getFullFileName();
        if (fileName.contains("."))
          fileName = fileName.split("\\.")[0];

        FileManager.renameFile(oldPicture.getAbsolutePath(), fileName);
      }

      // Remove it from the picture manager if it is outside the current directory
      boolean isUnderCurDir = newPicture.getAbsolutePath().contains(this.currDir);
      // Remove it if the manager does not handle pictures non-recursively
      if (!isUnderCurDir && !isRecursive) {
        this.untrackPicture(newPicture);
      }
    }
  }

  /**
   * Returns a list of all of the available tags stored in this class.
   * 
   * @return A list of all available tags in this class.
   */
  public ArrayList<Tag> getAvailableTags() {
    return new ArrayList<Tag>(availableTags);
  }


  /**
   * Returns the current directory of this PictureManager.
   * 
   * @return The current directory of this PictureManager.
   */
  public String getCurrDir() {
    return this.currDir;
  }

  /**
   * true if the manager recursively adds all pictures under that directory.
   */
  public boolean isRecursive() {
    return isRecursive;
  }
}
