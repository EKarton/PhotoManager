package backend.models;

import java.io.Serializable;
import java.util.Observable;

/**
 * A tag, has a label.
 */
public class Tag extends Observable implements Serializable {
  /**
   * the label of the tag, two tags are considered the same if they have the same label.
   */
  private String label;

  /**
   * Constructs a new tag with a given label
   * 
   * @param label
   */
  public Tag(String label) {
    this.label = label;
  }

  /**
   * @return this tag's label
   */
  public String getLabel() {
    return label;
  }

  /**
   * set a new label for this tag, notifies all observers observing this tag.
   * 
   * a.k.a any picture that has this tag will be observed hence the changes will be reflected on
   * themselves
   * 
   * @param label
   */
  public void setLabel(String label) {
    Tag oldTag = new Tag(this.label);
    this.label = label;
    this.setChanged();
    this.notifyObservers(oldTag);
  }

  @Override
  public String toString() {
    return label;
  }


  /**
   * two tags are considered equal if they have the same label.
   */
  @Override
  public boolean equals(Object anObject) {
    if (anObject instanceof Tag) {
      Tag otherTag = (Tag) anObject;
      if (this.getLabel().equals(otherTag.getLabel())) {
        return true;
      }
    }
    return false;
  }
}
