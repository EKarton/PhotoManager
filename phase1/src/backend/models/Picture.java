package backend.models;

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
  private String tagNames = "";

  /**
   * The tags in the name .
   */
  private String fileExt;


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
   * Set the absolute path, directory path, tagless name, and full file name and tags of this object
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
    String endOfFile = nameParts[nameParts.length - 1];

    this.fileExt = "." + endOfFile.split("\\.")[1]; // sets the file extension,
    // which are the chars after
    // the last ".

    this.taglessName = "";
    if (nameParts.length >= 1) {
      this.taglessName = nameParts[0];
    }

    String theTagName;
    for (int i = 1; i < nameParts.length; i++) {

      if (i == nameParts.length - 1) {// if it's at the last tag
        theTagName = endOfFile.split("\\.")[0];
      } else {
        theTagName = nameParts[i];
      }
      this.tagNames += " @" + theTagName; // Concatenate the tagNames
      Tag newTag = new Tag(theTagName);
      if (!this.containsTag(newTag)) {
        this.tags.add(newTag);
        newTag.addObserver(this);
      }
    }
  }


  /**
   * Set the absolute path of this picture It will notify all the observers that it has changed. It
   * will send a copy of its old Picture instance to the observers.
   *
   * @param absolutePath The new absolute path to this picture
   */
  public void setAbsolutePath(String absolutePath) {
    Picture oldPic = this.clone();

    setObjectProperties(absolutePath);

    super.setChanged();
    super.notifyObservers(oldPic);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Picture) {
      Picture otherPic = (Picture) o;
      if (otherPic.absolutePath == this.absolutePath) {
        return true;
      }
    }
    return false;
  }



  /**
   * Set the directory path of this picture It will notify all the observers that it has changed. It
   * will send a copy of its old Picture instance to the observers
   *
   * @param directoryPath The new directory path to this picture.
   */
  public void setDirectoryPath(String directoryPath) {
    Picture oldPic = this.clone();
    this.directoryPath = directoryPath;
    this.absolutePath = this.directoryPath + "//" + this.fullFileName;

    super.setChanged();
    super.notifyObservers(oldPic);
  }

  /**
   * Set the tagless name of this picture. It will notify all the observers that it has changed. It
   * will send a copy of its old Picture instance to the observers
   *
   * @param taglessName The new tagless name of this picture.
   */
  public boolean setTaglessName(String taglessName) {
    if ((taglessName + this.tagNames).length() <= 255) {// Make sure the name does not exceed
      // maximum char length.
      Picture oldPic = this.clone();

      this.fullFileName = taglessName + this.tagNames + this.fileExt;
      this.absolutePath = this.directoryPath + "//" + this.fullFileName;

      super.setChanged();
      super.notifyObservers(oldPic);
      return true;
    }
    return false;
  }


  @Override
  public String toString() {
    return "Abs path: " + absolutePath;
  }

  public ArrayList<Tag> getTags() {
    return new ArrayList<Tag>(this.tags);
  }

  /**
   * method for adding a tag to this picture
   *
   * @return true if the tag is successfully added, else false.
   */

  public boolean addTag(Tag tag) {
    if (!this.tags.contains(tag) && (this.fullFileName.length() + tag.getLabel().length()) <= 255) {
      Picture oldPic = this.clone();
      String newTagNames = this.tagNames + " @" + tag.getLabel();
      this.absolutePath =
          this.getDirectoryPath() + "//" + this.taglessName + newTagNames + this.fileExt;
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
    for (Tag originalTag : this.tags) {// for loop is necessary to deleting this Picture from the
      // actual Tag Object it's observing.
      if (originalTag.equals(tag)) {
        originalTag.deleteObserver(this);
        this.tags.remove(tag);

        String newTagNames = this.tagNames.replace(" @" + tag.getLabel(), "");
        this.absolutePath =
            this.getDirectoryPath() + "//" + this.taglessName + newTagNames + this.fileExt;
        this.setObjectProperties(this.absolutePath);

        super.setChanged();
        super.notifyObservers(oldPic);
        return true; // successfully deleted.
      }
    }
    return false;
  }


  /**
   * @return a copy of this picture
   */
  @Override
  public Picture clone() {
    Picture clone = new Picture(this.absolutePath);
    return clone;
  }

  public boolean containsTag(Tag tag) {
    if (this.tags.contains(tag)) {
      return true;
    }
    return false;
  }

  @Override
  public void update(Observable curObserved, Object change) {
    if (curObserved instanceof Tag && change instanceof Tag) {
      // see if it's a Tag naming Change, note: currently, only naming change on Tags will be
      // reflected on here.
      Tag currTag = (Tag) curObserved;
      Tag changedTag = (Tag) change;

      for (Tag tag : this.getTags()) {// Find the Tag with the name Change.
        if (tag.equals(changedTag)) {
          this.deleteTag(tag);
          this.addTag(currTag);
        }
      }
    }
  }


}
