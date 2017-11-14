package backend.logger.changeLogs;

import backend.logger.Log;

/**
 *This represents a change where a file was renamed, moved or deleted
 */
public class FileChangeLog extends Log{
  private String originalPath;
  private String newPath;
  private boolean delete;
  
  public FileChangeLog(String originalPath, String newPath) {
    super();
    this.originalPath = originalPath;
    this.newPath = newPath;
    this.delete = false;
  }
  
  public FileChangeLog(String orignalPath) {
    this.delete = true;
  }
  
  public String getOriginalPath() {
    return this.originalPath;
  }
  
  public String getNewPath() {
   return this.newPath; 
  }
  
  public boolean isDelete() {
    return this.delete;
  }
}
