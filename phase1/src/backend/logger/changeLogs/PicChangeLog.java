package backend.logger.changeLogs;

import backend.logger.Log;

/**
 * This represents a change where a file was renamed, moved or deleted
 */
public class PicChangeLog extends Log {
  private String originalPath;
  private String newPath;
  private boolean nameChange;

  public PicChangeLog(String originalPath, String newPath, boolean nameChange) {
    super();
    this.originalPath = originalPath;
    this.newPath = newPath;
    this.nameChange = nameChange;
  }

  public String getOriginalPath() {
    return this.originalPath;
  }

  public String getNewPath() {
    return this.newPath;
  }

  public boolean isNameChange() {
    return nameChange;
  }
}
