package backend.logger.changeLogs;

import backend.Tag;
import backend.logger.Log;

/**
 * This represents a change where a tag was added or deleted
 */
public class TagChangeLog extends Log{
  private Tag tag;
  private boolean add;
  
  public TagChangeLog(Tag tag, boolean add) {
    super();
    this.tag = tag;
    this.add = add;
  }
  
  public Tag getTag() {
    return this.tag;
  }
  
  public boolean isAdd() {
    return this.add;
  }
}
