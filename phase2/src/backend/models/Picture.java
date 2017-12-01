package backend.models;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A class used to represent a picture. It is being represented by a list of tags, its directory
 * path to the picture in the OS, its name without the tags, and its file extension.
 *
 * This class can be observed for any state changes. Any state changes to this class will notify all
 * the observers, returning the old copy of the instance to the observers
 */
public class Picture extends Observable implements Serializable, Observer, Cloneable {

  /**
   * The directory path to the picture.
   */
  private String directoryPath;

  /**
   * The file name, without its tag names and without its file extension.
   */
  private String taglessName;

  /**
   * The file extension with the ".". Ex: ".jpg", ".gif"
   */
  private String fileExt;

  /**
   * Tags that the picture currently has
   */
  private ArrayList<Tag> tags;

  /**
   * A list of all the tags that the Picture has had or currently holding
   */
  private ArrayList<ArrayList<Tag>> historicalTags;


  /**
   * A List of all historical tag-less names
   */
  private ArrayList<String> historicalTagLessNames;

  /**
   * Creates an instance of Picture given the absolute path of the picture. It will parse the
   * absolute path of this picture to get its properties, including the list of tags, directory
   * path, file name, etc.
   *
   * @param absolutePath The absolute path to the Picture.
   */
  public Picture(String absolutePath) {
    this.tags = new ArrayList<Tag>();
    this.historicalTags = new ArrayList<ArrayList<Tag>>();
    this.historicalTagLessNames = new ArrayList<String>();

    File file = new File(absolutePath);

    this.directoryPath = file.getParent();
    String fullFileName = file.getName();
    this.fileExt = "." + fullFileName.split("\\.")[1].trim();
    String nameWithoutFileExtension = fullFileName.split("\\.")[0].trim();

    // Parsing the tagless name and its tags.
    if (!file.getName().contains("@")) {
      this.taglessName = nameWithoutFileExtension.trim();
    } else {
      String[] nameParts = nameWithoutFileExtension.split("@");
      this.taglessName = nameParts[0].trim();
      for (int i = 1; i < nameParts.length; i++) {
        Tag newTag = new Tag(nameParts[i].trim());
        if (!tags.contains(newTag)) {
          this.tags.add(newTag);
          newTag.addObserver(this);
        }
      }
    }
    this.historicalTags.add(getTagsDeepCopy());
    this.historicalTagLessNames.add(this.taglessName);
  }

  /**
   * Set the directory path of this picture It will notify all the observers that it has changed. It
   * will send a copy of its old Picture instance to the observers.
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
   * Set the tagless name of this picture. It will notify all the observers that it has changed. If
   * the new tagless name is equal to the current tagless name, it will not do anything. It will
   * send a copy of its old Picture instance to the observers.
   *
   * @param taglessName The new tagless name of this picture.
   */
  public void setTaglessName(String taglessName) {
    if (this.taglessName.equals(taglessName)) {
      return;
    }

    StringBuilder builder = new StringBuilder();
    for (Tag tag : this.tags) {
      builder.append(" @").append(tag.getLabel());
    }

    // Make sure the name does not exceed maximum char length.
    String fullFileName = builder + taglessName + this.fileExt;
    if (taglessName.length() > 0 && fullFileName.length() <= 255) {
      Picture oldPic = this.clone();
      this.taglessName = taglessName;

      if (!this.historicalTagLessNames.contains(taglessName)) {
        this.historicalTagLessNames.add(this.taglessName);
      }

      super.setChanged();
      super.notifyObservers(oldPic);
    }
  }

  /**
   * Adds a new tag to this instance and this instance will observe that tag. If the tag already
   * exist in this instance, it will not add it. It checks if the tag exists in this instance by the
   * tag's .equals(). It will notify all the observers that a tag has been added to this picture.
   * All observers will get a copy of the old picture's state before the new tag has been added.
   *
   * note: Without saving the tags as historycal tags
   * 
   * @param tag The tag to add to this instance
   */
  public void addTagWithoutHistory(Tag tag) {
    int lengthOfNewFileName = this.getFullFileName().length() + tag.getLabel().length() + 1;
    if (lengthOfNewFileName < 255 && !tags.contains(tag)) {
      Picture oldPic = this.clone();
      this.tags.add(tag);
      tag.addObserver(this);

      super.setChanged();
      super.notifyObservers(oldPic);
    }
  }


  /**
   * Adds a new tag to this instance and this instance will observe that tag. If the tag already
   * exist in this instance, it will not add it. It checks if the tag exists in this instance by the
   * tag's .equals(). It will notify all the observers that a tag has been added to this picture.
   * All observers will get a copy of the old picture's state before the new tag has been added.
   *
   * @param tag The tag to add to this instance
   */
  public void addTag(Tag tag) {
    this.addTagWithoutHistory(tag);
    if (!this.getHistoricalTags().contains(getTagsDeepCopy())
        && !this.getTagsDeepCopy().isEmpty()) {
      this.historicalTags.add(getTagsDeepCopy());
    }

  }

  /**
   * adds a list of tags to this picture , with the same reasoning behind addTag()
   */
  public void addMultipleTags(List<Tag> tags) {
    for (Tag tag : tags) {
      this.addTagWithoutHistory(tag);
    }
    if (!this.getHistoricalTags().contains(getTagsDeepCopy())
        && !this.getTagsDeepCopy().isEmpty()) {
      this.historicalTags.add(getTagsDeepCopy());
    }
  }

  /**
   * Deletes a tag from this picture and will stop observing that tag. If the tag does not exist in
   * this instance, it will do nothing. It will notifies all observers that a Tag has been deleted
   * from this picture. It will send a copy of the picture before the tag has been deleted to the
   * observers.
   * 
   * Without saving the tags as historycal tags
   *
   * @param tag The tag to delete
   */
  public void deleteTagWithoutHisotry(Tag tag) {
    if (tags.contains(tag)) {
      Picture oldPic = this.clone();
      tags.remove(tag);
      tag.deleteObserver(this);

      super.setChanged();
      super.notifyObservers(oldPic);
    }
  }

  /**
   * Deletes a tag from this picture and will stop observing that tag. If the tag does not exist in
   * this instance, it will do nothing. It will notifies all observers that a Tag has been deleted
   * from this picture. It will send a copy of the picture before the tag has been deleted to the
   * observers.
   *
   * @param tag The tag to delete
   */
  public void deleteTag(Tag tag) {
    this.deleteTagWithoutHisotry(tag);

    if (!this.getHistoricalTags().contains(getTagsDeepCopy()) && !this.getTags().isEmpty()) {
      this.historicalTags.add(getTagsDeepCopy());
    }
  }


  /**
   * delete the given list of tags from this picture, if they exist on this picture
   *
   * @param tags tags to delete
   */
  public void deleteMultipleTags(List<Tag> tags) {
    for (Tag tag : tags) {
      this.deleteTagWithoutHisotry(tag);
    }
    if (!this.getHistoricalTags().contains(getTagsDeepCopy()) && !this.getTags().isEmpty()) {
      this.historicalTags.add(getTagsDeepCopy());
    }
  }

  /**
   * Constructs a hard copy of this instance
   *
   * @return A hard copy of this instance.
   */
  @Override
  public Picture clone() {
    return new Picture(getAbsolutePath());
  }

  /**
   * Determines if a tag already exist in this instance. It uses the Tag.equals() to compare and see
   * if the tag exists.
   *
   * @param tag A tag to compare it to.
   * @return Returns true if this tag is inside this picture
   */
  public boolean containsTag(Tag tag) {
    return this.tags.contains(tag);
  }

  /**
   * Get the absolute path to the picture, which includes its directory path and its full file name.
   * Ex: "C:\\Users\\Images\\jane @person.jpg"
   *
   * @return The absolute path to the picture
   */
  public String getAbsolutePath() {
    File newFile = new File(directoryPath, getFullFileName());
    return newFile.getAbsolutePath();
  }

  /**
   * Get the directory path to the picture. Ex: if the absolute path to this picture is
   * "C:\\Users\\jane.jpg", this method will return "C:\\Users"
   *
   * @return The directory path to the picture.
   */
  public String getDirectoryPath() {
    return this.directoryPath;
  }

  /**
   * Get the full file name of this picture, including its tags and its file extension. In other
   * words, it will return the name of the picture represented in the OS. Ex: "jane @person.jpg".
   *
   * @return The full file name of this picture.
   */
  public String getFullFileName() {
    StringBuilder fullFileName = new StringBuilder(taglessName);
    for (Tag tag : tags) {
      fullFileName.append(" @").append(tag.getLabel());
    }
    fullFileName.append(fileExt);
    return fullFileName.toString();
  }

  /**
   * Get the name of this picture without the tags. Ex: If the file name in the OS is "Jane
   *
   * @return The name of this picture without the tags.
   * @person.jpg", this method will return "jane".
   */
  public String getTaglessName() {
    return this.taglessName;
  }

  /**
   * Gets a copy of a list of tags in this picture.
   *
   * @return A copy of the list of tags in this picture.
   */
  public ArrayList<Tag> getTags() {
    return new ArrayList<Tag>(this.tags);
  }

  /**
   * Gets a actuall clone of a list of tags in this picture.
   *
   * @return A copy of the list of tags in this picture.
   */
  public ArrayList<Tag> getTagsDeepCopy() {
    ArrayList<Tag> tags = new ArrayList<Tag>();
    for (Tag tag : this.getTags()) {
      tags.add(new Tag(tag.getLabel()));
    }
    return tags;
  }

  /**
   * Get a copy of a list of all the historical tags
   *
   * @return A copy of a list of all historical tags
   */
  public ArrayList<ArrayList<Tag>> getHistoricalTags() {
    return new ArrayList<ArrayList<Tag>>(this.historicalTags);
  }

  /**
   * Get a copy of all historical (tag-less) names of a picture in a list.
   *
   * @return A copy of a list of all historical names.
   */
  public ArrayList<String> getHistoricalTaglessNames() {
    return new ArrayList<String>(this.historicalTagLessNames);
  }

  /**
   * Returns the file extension of this picture. Note: it includes the ".". Ex: ".jpg"
   *
   * @return the file extension as a string
   */
  public String getFileExtension() {
    return this.fileExt;
  }

  /**
   * Returns the string representation of this instance
   *
   * @return The string representation of this instance.
   */
  @Override
  public String toString() {
    return this.getTaglessName();
  }

  /**
   * A method that compares two Picture objects based on its absolute path. Note that if the "o" is
   * not an instance of Picture, it will return false
   *
   * @param o An object (ideally a Picture object)
   * @return True if the two picture objects have the same absolute path; else false.
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
   * This method handles when there is a tag change in this instance. It will also notify the
   * observers of this instance the change, providing the observers a copy of the state of this
   * instance before the tag change.
   */
  @Override
  public void update(Observable curObserved, Object change) {
    if (curObserved instanceof Tag && change instanceof Tag) {
      Tag oldTag = (Tag) change;
      Tag newTag = (Tag) curObserved;

      // Construct the old state of this picture before the tag changed.
      Picture oldPicture = this.clone();
      oldPicture.deleteTag(newTag);
      oldPicture.addTag(oldTag);

      this.setChanged();
      this.notifyObservers(oldPicture);
    }
  }
}
