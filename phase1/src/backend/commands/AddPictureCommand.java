package backend.commands;

import backend.Picture;
import backend.PictureManager;

public class AddPictureCommand implements Command {

  private Picture picture;
  private PictureManager manager;

  public AddPictureCommand(Picture picture, PictureManager manager){
    this.picture = picture;
    this.manager = manager;
  }

  /**
   * Redos the command again
   */
  @Override
  public void redo() {

  }

  /**
   * Undo the command
   */
  @Override
  public void undo() {
  }

  /**
   * Execute the command
   */
  @Override
  public void execute() {
    manager.addPicture(picture);
  }

  /**
   * Returns a log message for this command
   *
   * @return A log message for this command
   */
  @Override
  public String getLogMessage() {
    return null;
  }
}
