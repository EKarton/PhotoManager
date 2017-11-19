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
   * The directory path to the picture, i.e, it is the absolute path without the full file name.
   */
  private String directoryPath;

  /**
   * The file name, without the tag names.
   */
  private String taglessName;

  /**
   * The file extension (includes the .). Ex: ".jpg", ".gif"
   */
  private String fileExt;

  /**
   * An arraylist of tags that the picture currently has
   */
  private ArrayList<Tag> tags;

  /**
   * A list of all the tags that the Picture has had or currently holding
   */
  private ArrayList<Tag> historicalTags;

  /**
   * A List of all historical Full file names
   */
  private ArrayList<String> historicalTagLessNames;


  /**
   * Creates an instance of Picture given the absolute path of the picture
   * 
   * @param absolutePath The absolute path to the Picture
   */
  public Picture(String absolutePath) {
    this.tags = new ArrayList<Tag>();
    this.historicalTags = new ArrayList<Tag>();
    this.historicalTagLessNames = new ArrayList<String>();
    this.setPictureProperties(absolutePath);
  }

  /**
   * helper method for setting properties of a picture given an absolute path
   * 
   * @param absolutePath
   */
  private void setPictureProperties(String absolutePath) {
    File file = new File(absolutePath);

    this.directoryPath = file.getParent();
    String fullFileName = file.getName();
    this.fileExt = "." + fullFileName.split("\\.")[1].trim();
    String nameWithoutFileExtension = fullFileName.split("\\.")[0].trim();

    if (!file.getName().contains("@")) {
      this.taglessName = nameWithoutFileExtension.trim();
    } else {
      String[] nameParts = nameWithoutFileExtension.split("@");
      this.taglessName = nameParts[0].trim();

      for (int i = 1; i < nameParts.length; i++) {
        Tag newTag = new Tag(nameParts[i].trim());
        if (!tags.contains(newTag)) {
          tags.add(newTag);
          newTag.addObserver(this);
        }
      }
    }
    this.historicalTagLessNames.add(this.taglessName);
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
    // Make sure the name does not exceed maximum char length.
    String fullFileName = this.tagsToString() + taglessName + this.fileExt;
    if (taglessName.length() > 0 && fullFileName.length() <= 255) {
      Picture oldPic = this.clone();

      this.taglessName = taglessName;
      this.historicalTagLessNames.add(this.taglessName);// TODO: testing

      super.setChanged();
      super.notifyObservers(oldPic);
      return true;
    }
    return false;
  }

  /**
   * method for adding a tag to this picture
   * 
   * notifies all observers that a Tag has been added to this picture
   *
   * @return true if the tag is successfully added, else false.
   */

  public boolean addTag(Tag tag) {
    int lengthOfNewFileName = (this.getFullFileName() + " @" + tag.getLabel()).length();
    if (lengthOfNewFileName < 255 && !tags.contains(tag)) {
      Picture oldPic = this.clone();
      this.tags.add(tag);
      this.historicalTags.add(tag);
      // this.historicalTagLessNames.add(getFullFileName());

      tag.addObserver(this);

      super.setChanged();
      super.notifyObservers(oldPic);
      return true;
    }
    return false;
  }

  /**
   * method for deleting a tag form this picture
   * 
   * notifies all observers that a Tag has been deleted from this picture
   * 
   * @return true if the tag is successfully deleted, else false.
   */
  public boolean deleteTag(Tag tag) {
    if (tags.contains(tag)) {
      Picture oldPic = this.clone();
      tags.remove(tag);
      // this.historicalTagLessNames.add(getFullFileName());
      tag.deleteObserver(this);

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
    return new Picture(getAbsolutePath());
  }

  /**
   * Using Tag.equals() to compare tags.
   * 
   * @param tag
   * @return true if there tag is inside this picture
   */
  public boolean containsTag(Tag tag) {
    return this.tags.contains(tag);
  }

  /**
   * If any change has happened to a Tag in this picture, the picture will reflect on that change
   */
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

      // this.historicalTagLessNames.add(getFullFileName());
    }
  }

  /**
   * @return the Absolute path of the picture in a String
   */
  @Override
  public String toString() {
    return "Abs path: " + getAbsolutePath();
  }

  /**
   * two Picture Object are considered equal if they have the same absolutePath
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Picture) {
      Picture otherPic = (Picture) o;
      if (otherPic.getAbsolutePath().equals(this.getAbsolutePath())) {
        return true;
      }
    }
    return false;
  }

  /**
   * the String representation of all the tags, Ex: " @Family @Dog"
   * 
   * @return the String representation of all the tags
   */
  public String tagsToString() {
    StringBuilder builder = new StringBuilder();
    for (Tag tag : this.tags) {
      builder.append(" @" + tag.getLabel());
    }
    return builder.toString();
  }


  /**
   * Get the absolute path to the picture
   * 
   * @return The absolute path to the picture
   */
  public String getAbsolutePath() {
    File newFile = new File(directoryPath, getFullFileName());
    return newFile.getAbsolutePath();
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
    String fullFileName = taglessName;
    for (Tag tag : tags)
      fullFileName += " @" + tag.getLabel();
    fullFileName += fileExt;
    return fullFileName;
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
   * get all the tags in a List
   * 
   * @return all the tags this picture has in a List
   */
  public ArrayList<Tag> getTags() {
    return new ArrayList<Tag>(this.tags);
  }

  /**
   * get all historical tags in a list
   * 
   * @return all historical tags in a list
   */
  public ArrayList<Tag> getHistoricalTags() {
    return new ArrayList<Tag>(this.historicalTags);
  }

  /**
   * get all historical names of a picture (tagless) in a list
   * 
   * @return all historical names in a list
   */
  public ArrayList<String> getHistoricalTaglessNames() {
    return new ArrayList<String>(this.historicalTagLessNames);
  }
  
  public String getFileExtension() {
    return this.fileExt;
  }
}
