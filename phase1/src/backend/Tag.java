package backend;

import java.io.Serializable;
import java.util.Observable;

public class Tag extends Observable implements Serializable {
  private String label;

  public Tag(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

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


  @Override
  public boolean equals(Object anObject) {
    if (anObject instanceof Tag) {
      Tag anTag = (Tag) anObject;
      if (this.getLabel() == anTag.getLabel()) {
        return true;
      }
    }
    return false;
  }
}
