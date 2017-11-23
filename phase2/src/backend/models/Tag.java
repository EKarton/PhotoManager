package backend.models;

import java.io.Serializable;
import java.util.Observable;

/**
 * A class representing a tag to a picture.
 */
public class Tag extends Observable implements Serializable, Cloneable {

  /**
   * The label of the tag.
   */
  private String label;

  /**
   * A region of the picture that has this tag.
   */
  private Rectangle region;

  /**
   * Constructs a new tag with a given label.
   * @param label The label of this tag
   */
  public Tag(String label) {
    this(label, new Rectangle(0, 0, 0, 0));
  }

  /**
   * Constructs a new tag, specifying its label
   * and a region in the picture.
   * @param label The label of this tag
   * @param pictureRegion A region in the picture.
   */
  public Tag(String label, Rectangle pictureRegion){
    this.label = label;
    this.region = pictureRegion;
  }

  /**
   * Returns the label of this tag.
   * @return The label of this tag.
   */
  public String getLabel() {
    return label;
  }

  /**
   * Set a new label for this tag if the new label is different
   * from its current label.
   * It notifies all observers observing this tag, providing the
   * observers a copy of this instance before the label change.
   * @param label A new label to this Tag instance
   */
  public void setLabel(String label) {
    if (!label.equals(this.label)) {
      Tag oldTag = new Tag(this.label);
      this.label = label;

      this.setChanged();
      this.notifyObservers(oldTag);
    }
  }

  /**
   * Get the region of the picture that has this tag
   * @return The region of the picture
   */
  public Rectangle getRegion() {
    return region;
  }

  /**
   * Set a new region of the picture to this tag.
   * @param region The new region in the picture.
   */
  public void setRegion(Rectangle region) {
    this.region = region;
  }

  /**
   * Returns the string representation of this class.
   * @return The string representation of this class.
   */
  @Override
  public String toString() {
    return label;
  }

  /**
   * Determines if another Tag object is equal to this object.
   * Two tag objects are equal iff the other object is a
   * Tag instance and it has the same label.
   * @param anObject The other object to compare it to.
   * @return True if two objects are equal; else false.
   */
  @Override
  public boolean equals(Object anObject) {
    if (anObject instanceof Tag) {
      Tag otherTag = (Tag) anObject;
      return otherTag.label.equals(this.label) &&
          otherTag.getRegion().equals(this.region);
    }
    return false;
  }
}
