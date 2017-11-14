package backend.logger.changeLogs;

import backend.logger.Log;

/**
 * This represents a change where the tags were reverted to an older set
 */
public class TagRevertLog extends Log{
  
  /**The number of log in the tag changes log we are reverting to*/
  private int revert;
  
  public TagRevertLog(int revert) {
    super();
    this.revert =  revert;
  }
  
  public int getRevert() {
    return revert;
  }
}
