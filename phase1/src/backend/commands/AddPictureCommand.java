package backend.commands;

import backend.models.Picture;
import backend.models.PictureManager;

/**
 * A class used to specify an addition of a picture to a manager.
 */
public class AddPictureCommand implements Command {

  /**
   * A picture to add
   */
  private Picture picture;

  /**
   * The manager to hold the pictures.
   */
  private PictureManager manager;

  /**
   * Creates an AddPictureCommand instance
   * @param picture A picture to add
   * @param manager A manager used to store the pictures.
   */
  public AddPictureCommand(Picture picture, PictureManager manager){
    this.picture = picture;
    this.manager = manager;
  }

  /**
   * Undo the command. It will remove the picture from the manager.
   */
  @Override
  public void undo() {
    if (manager.contains(picture))
      manager.untrackPicture(picture);
  }

  /**
   * Execute the command by adding a picture to the picture manager.
   * It will only add it if the picture does not exist in the manager yet.
   */
  @Override
  public void execute() {
    if (!manager.contains(picture))
      manager.addPicture(picture);
  }

  /**
   * Returns a log message for this command
   * @return A log message for this command
   */
  @Override
  public String getLogMessage() {
    return "Added picture " + picture + " to the Picture Manager, " + manager;
  }
}
