package backend.logger.changeLogs;

import backend.logger.Log;

/**
 * This represents a change where a file was renamed, moved or deleted
 */
public class PicChangeLog extends Log {
  private String originalPath;
  private String newPath;

  public PicChangeLog(String originalPath, String newPath) {
    super();
    this.originalPath = originalPath;
    this.newPath = newPath;
  }

  public PicChangeLog(String orignalPath) {}

  public String getOriginalPath() {
    return this.originalPath;
  }

  public String getNewPath() {
    return this.newPath;
  }
}
