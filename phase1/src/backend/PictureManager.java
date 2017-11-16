package backend;

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
   * Populate the picture manager with pictures under a certain directory
   *
   * @param directoryPath A directory path
   * @throws IOException Thrown when the directory does not exist.
   */
  public PictureManager(String directoryPath) throws IOException {
    FileManager manager = new FileManager();
    List<File> files = manager.getFileList(directoryPath);

    for (File file : files) {
      Picture picture = new Picture(file.getAbsolutePath());
      pictures.add(picture);
      picture.addObserver(this);
    }
  }

  /**
   * Creates a PictureManager instance with no pictures to keep track of.
   */
  public PictureManager() {
  }

  /**
   * Return a copy of the list of pictures that this class has.
   *
   * @return A list of pictures in this class.
   */
  public ArrayList<Picture> getPictures() {
    return new ArrayList<Picture>(pictures);
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
  }

  /**
   * Adds a unique picture to this class. If the picture already exist, it will not add it.
   *
   * @param picture A picture
   */
  public void addPicture(Picture picture) {
    pictures.add(picture);
  }

  /**
   * Untracks a picture from this class.
   */
  public void untrackPicture(Picture picture){
    pictures.remove(picture);
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
    FileManager manager = new FileManager();

    // If there is a commands with the pictures
    if (o instanceof Picture && arg instanceof Picture) {
      Picture newPicture = (Picture) o;
      Picture oldPicture = (Picture) arg;

      if (pictures.contains(newPicture)) {
        if (!newPicture.getDirectoryPath().equals(oldPicture.getDirectoryPath())) {
          manager.moveFile(oldPicture.getAbsolutePath(), newPicture.getFullFileName());
        }

        if (!newPicture.getFullFileName().equals(oldPicture.getFullFileName())) {
          manager.renameFile(newPicture.getAbsolutePath(), newPicture.getFullFileName());
        }
      }
    }
  }
}
