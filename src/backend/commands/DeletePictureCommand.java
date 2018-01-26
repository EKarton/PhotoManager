package backend.commands;

import backend.models.Picture;
import backend.models.PictureManager;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * A Command class used to delete a picture from the PictureManager.
 *
 * severity level: FINE
 */
public class DeletePictureCommand implements Command {

  /**
   * A picture to delete from the manager
   */
  private Picture picture;

  /**
   * A manager that contains the pictures.
   */
  private PictureManager manager;

  /**
   * Creates an instance of DeletePictureCommand, specifying a picture to delete from a manager.
   * Note that picture should already exist in the manager.
   *
   * @param picture A picture to remove from the manager
   * @param manager A manager
   */
  public DeletePictureCommand(Picture picture, PictureManager manager) {

    this.picture = picture;
    this.manager = manager;
  }

  /**
   * Undo the command by re-adding the picture back to the manager. Note that if the picture already
   * exist in the manager, it will not re-add it back to the manager.
   */
  @Override
  public void undo() {
    manager.addPicture(picture);
  }

  /**
   * Execute the command by deleting the picture from the manager. Note that if the picture is no
   * longer in the manager, it will not do anything.
   */
  @Override
  public void execute() {
    manager.untrackPicture(picture);
  }

  /**
   * @return LogRecord a logRecord for this command with a severity level of FINE.
   */
  @Override
  public LogRecord getLogRecord() {
    return new LogRecord(Level.FINE, "Deleted Picture " + picture.getTaglessName());
  }
}
