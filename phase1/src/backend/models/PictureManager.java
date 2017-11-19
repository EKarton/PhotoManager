package backend.models;

import backend.files.FileManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A class used to keep track of the pictures.
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
   * the current directory of this manager.
   */
  private String currDir;

  /**
   * Populate the picture manager with pictures under a certain directory
   *
   * @param directoryPath A directory path
   * @throws IOException Thrown when the directory does not exist.
   */
  public PictureManager(String directoryPath, boolean recursive) throws IOException {
    FileManager manager = new FileManager();
    this.currDir = directoryPath;

    List<File> files;
    if (recursive)
      files = manager.getImageListRec(directoryPath);
    else
      files = manager.getImageList(directoryPath);

    for (File file : files) {
      Picture picture = new Picture(file.getAbsolutePath());
      pictures.add(picture);
      picture.addObserver(this);
      this.addAvailableTags(picture);
    }
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
  public ArrayList<Picture> getPictureWithTag(Tag tag) {
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
        this.refreshAvailableTags();
      }
    }
  }

  /**
   * Adds a unique picture to this class. It will also add this as an observer to the new picture.
   * If the picture already exist, it will not add it. To see if a picture exist, refer to the
   * Picture.equals() to see if two pictures are equal
   * 
   * @param picture A picture
   */
  public void addPicture(Picture picture) {
    if (!pictures.contains(picture)) {
      pictures.add(picture);
      picture.addObserver(this);
      this.addAvailableTags(picture);
    }
  }

  /**
   * Untracks a picture from this class and the picture no longer becomes observed from this class.
   * If the picture does not exist, it will do nothing
   * 
   * @param picture A picture in this class.
   */
  public void untrackPicture(Picture picture) {
    for (Picture thePicture : this.pictures) {
      if (thePicture.equals(picture)) {
        pictures.remove(thePicture);
        thePicture.deleteObserver(this); // deleting observer from the actuall picture instead of
                                         // a reference
        break;
      }
    }
  }

  /**
   * Determines whether a picture is in this instance or not. If it is in this instance, it is being
   * tracked.
   * 
   * @return True if the picture is in this instance; else false.
   */
  public boolean contains(Picture picture) {
    return pictures.contains(picture);
  }

  /**
   * Return a list of pictures stored in this class that has a certain tag
   *
   * @param tag A tag
   * @return Pictures in this class containing a tag.
   */
  private List<Picture> getPictureFromTags(Tag tag) {
    List<Picture> picturesWithTag = new ArrayList<Picture>();
    for (Picture picture : pictures) {
      if (picture.containsTag(tag)) {
        picturesWithTag.add(picture);
      }
    }
    return picturesWithTag;
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
   * helper function for updating picture, chages will be reflected on the OS
   * 
   * @param newPicture
   * @param oldPicture
   */
  private void updatePicture(Picture newPicture, Picture oldPicture) {
    FileManager manager = new FileManager();
    if (pictures.contains(newPicture)) {
      if (!newPicture.getDirectoryPath().equals(oldPicture.getDirectoryPath())) {
        manager.moveFile(oldPicture.getAbsolutePath(), newPicture.getDirectoryPath());
      }

      if (!newPicture.getFullFileName().equals(oldPicture.getFullFileName())) {
        String fileNameWithoutExtension = newPicture.getFullFileName();
        if (fileNameWithoutExtension.contains("."))
          fileNameWithoutExtension = fileNameWithoutExtension.split("\\.")[0];

        boolean a = manager.renameFile(oldPicture.getAbsolutePath(), fileNameWithoutExtension);
      }

      // Remove it from the picture manager if it is outside the current directory
      boolean isUnderCurDir = newPicture.getAbsolutePath().contains(this.currDir);
      if (!isUnderCurDir)
        this.untrackPicture(newPicture);
    }
  }

  /**
   * Update the availableTags of this manager
   */
  private void refreshAvailableTags() {
    for (Tag tag : this.getAvailableTags()) {
      boolean usefulTag = false;
      for (Picture picture : this.pictures) {
        if (picture.containsTag(tag)) {
          usefulTag = true;
          break;
        }
      }
      if (!usefulTag) {
        this.availableTags.remove(tag);
      }
    }
  }

  /**
   * Whenever a new Picture is added, it will update the available tags
   * 
   * @param picture
   */
  private void addAvailableTags(Picture picture) {
    for (Tag tag : picture.getTags()) {
      if (!this.availableTags.contains(tag)) {
        this.availableTags.add(tag);
      }
    }
  }

  /**
   * @return all the available tags in a list
   */
  public ArrayList<Tag> getAvailableTags() {
    return new ArrayList<Tag>(availableTags);
  }

  public String getCurrDir() {
    return this.currDir;
  }
}
