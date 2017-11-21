package backend.commands;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import backend.models.Picture;

/**
 * A Command class used to specify a rename to a picture.
 * 
 * Severity Level:INFO
 */
public class RenamePictureCommand implements Command {

  /**
   * A picture to rename
   */
  private Picture picture;

  /**
   * The new name of the picture
   */
  private String newName;

  /**
   * The old name of the picture
   */
  private String oldName;

  /**
   * Creates a RenamePictureCommand which can rename a picture with a new name
   * 
   * @param picture A picture to rename
   * @param newName The new name for this picture.
   */
  public RenamePictureCommand(Picture picture, String newName) {

    this.picture = picture;
    this.oldName = picture.getTaglessName();
    this.newName = newName;
  }

  /**
   * Undo the command by renaming the picture back to its old name.
   */
  @Override
  public void undo() {
    picture.setTaglessName(oldName);
  }

  /**
   * Execute the command by renaming the picture to its new name.
   */
  @Override
  public void execute() {
    oldName = picture.getTaglessName();
    picture.setTaglessName(newName);
  }

  @Override
  public LogRecord getLogRecord() {
    return new LogRecord(Level.INFO, "Renamed Picture " + oldName + " to " + newName);
  }
}
