package backend.logger.changeLogs;

import backend.Tag;
import backend.logger.Log;

/**
 * This represents a change where a tag was added or deleted
 */
public class TagChangeLog extends Log {
  private Tag tag;
  private String oldDescription;
  private String nameChange;

  public TagChangeLog(Tag tag, String oldDescription) {
    super();
    this.tag = tag;
    this.oldDescription = oldDescription;
  }

  public TagChangeLog(Tag tag, Tag oldTag) {
    super();
    this.tag = tag;
    this.nameChange = oldTag.getLabel();
  }

  public Tag getTag() {
    return this.tag;
  }

  public String getOldDescription() {
    return this.oldDescription;
  }

  public String getNameChange() {
    return nameChange;
  }

}
