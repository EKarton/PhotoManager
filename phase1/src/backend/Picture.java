package backend;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A class used to represent a picture in the IO level
 */


public class Picture extends Observable implements Serializable, Observer {

  /**
   * The absolute path to the picture, i.e, contains the parent directory and the full file name
   */
  private String absolutePath;

  /**
   * The directory path to the picture, i.e, it is the absolute path without the full file name.
   */
  private String directoryPath;

  /**
   * The file name, with the tag names in them
   */
  private String fullFileName;

  /**
   * The file name, without the tag names.
   */
  private String taglessName;

  /**
   * The file name, without the tag names.
   */
  private ArrayList<Tag> tags;

  /**
   * The tags in the name .
   */
  private String tagNames;


  /**
   * Creates an instance of Picture
   *
   * @param absolutePath The absolute path to the Picture
   */
  public Picture(String absolutePath) {
    this.tags = new ArrayList<Tag>();
    setObjectProperties(absolutePath);
  }

  /**
   * Get the absolute path to the picture
   *
   * @return The absolute path to the picture
   */
  public String getAbsolutePath() {
    return this.absolutePath;
  }

  /**
   * Get the directory path to the picture
   *
   * @return The directory path to the picture
   */
  public String getDirectoryPath() {
    return this.directoryPath;
  }

  /**
   * Get the full file name of this picture, including its tags.
   *
   * @return The full file name of this picture.
   */
  public String getFullFileName() {
    return this.fullFileName;
  }

  /**
   * Get the name of this picture without the tags
   *
   * @return The name of this picture without the tags
   */
  public String getTaglessName() {
    return this.taglessName;
  }

  /**
   * Set the absolute path, directory path, tagless name, and full file name and tags of this
   * object
   *
   * @param absolutePath The absolute path to this picture
   */
  private void setObjectProperties(String absolutePath) {
    this.absolutePath = absolutePath;
    File file = new File(absolutePath);

    this.directoryPath = file.getParent();
    this.fullFileName = file.getName();

    String[] nameParts = fullFileName.split(" @");// If the name of the picture originally have some
    // tags, we will split them
    this.taglessName = "";
    if (nameParts.length >= 1) {
      this.taglessName = nameParts[0];
    }

    for (int i = 1; i < nameParts.length; i++) {
      this.tagNames += nameParts[i];// Concatenate the tagNames
      this.tags.add(new Tag(nameParts[i]));
    }
  }

  /**
   * Set the absolute path of this picture It will notify all the observers that it has changed. It
   * will send a copy of its old Picture instance to the observers.
   *
   * @param absolutePath The new absolute path to this picture
   */
  public void setAbsolutePath(String absolutePath) {
    String oldAbsolutePath = this.absolutePath;
    setObjectProperties(absolutePath);

    super.setChanged();
    super.notifyObservers(new Picture(oldAbsolutePath));
  }

  /**
   * Set the directory path of this picture It will notify all the observers that it has changed. It
   * will send a copy of its old Picture instance to the observers
   *
   * @param directoryPath The new directory path to this picture.
   */
  public void setDirectoryPath(String directoryPath) {
    String oldAbsolutePath = this.absolutePath;
    this.directoryPath = directoryPath;
    this.absolutePath = this.directoryPath + "//" + this.fullFileName;

    super.setChanged();
    super.notifyObservers(new Picture(oldAbsolutePath));
  }

  /**
   * Set the tagless name of this picture. It will notify all the observers that it has changed. It
   * will send a copy of its old Picture instance to the observers
   *
   * @param taglessName The new tagless name of this picture.
   */
  public void setTaglessName(String taglessName) {
    String oldAbsolutePath = this.absolutePath;
    StringBuilder tags = new StringBuilder();
    String[] nameParts = fullFileName.split("@");
    for (int i = 1; i < nameParts.length; i++) {
      tags.append(" @").append(nameParts[i]);
    }
    this.fullFileName = taglessName + tags.toString();
    this.absolutePath = this.directoryPath + "//" + this.fullFileName;

    super.setChanged();
    super.notifyObservers(new Picture(oldAbsolutePath));
  }

  @Override
  public String toString() {
    return "Abs path: " + absolutePath;
  }

  public ArrayList<Tag> getTags() {
    return this.tags;
  }

  /**
   * method for adding a tag to this picture
   *
   * @return true if the tag is successfully added, else false.
   */

  public boolean addTags(Tag tag) {
    if (!this.tags.contains(tag)) {
      Picture oldPic = this.clone();
      this.tags.add(tag);
      this.absolutePath =
          this.getDirectoryPath() + "//" + " @" + tag.getLabel() + this.getFullFileName();
      this.setObjectProperties(this.absolutePath);

      super.setChanged();
      super.notifyObservers(oldPic);// provide an old copy of the picture for the observer.
      return true;
    }
    return false;
  }

  /**
   * method for deleting a tag form this picture
   *
   * @return true if the tag is successfully deleted, else false.
   */
  public boolean deleteTag(Tag tag) {
    Picture oldPic = this.clone();
    if (this.tags.contains(tag)) {
      this.tags.remove(tag);
      String newTagName = this.tagNames.replace(" @" + tag.getLabel(), "");
      String absPath = this.getDirectoryPath() + "//" + newTagName + this.getTaglessName();
      this.setAbsolutePath(absPath);

      super.setChanged();
      super.notifyObservers(oldPic);
      return true;
    }
    return false;
  }

  /**
   * @return a copy of this picture
   */
  @Override
  public Picture clone() {
    Picture clone = new Picture(this.absolutePath);
    clone.tags = new ArrayList<Tag>(this.tags);
    return clone;
  }

  public boolean containsTag(Tag tag) {
    if (this.tags.contains(tag)) {
      return true;
    }
    return false;
  }

  @Override
  public void update(Observable arg0, Object arg1) {
    // TODO Auto-generated method stub

  }


}
