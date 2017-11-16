package backend.commands;

import backend.Picture;
import backend.PictureManager;

/**
 * A Command class used to delete a picture from the PictureManager
 */
public class DeletePictureCommand implements Command{

  /**
   * A picture to delete from the manager
   */
  private Picture picture;

  /**
   * A manager that contains the pictures.
   */
  private PictureManager manager;

  /**
   * Creates an instance of DeletePictureCommand, specifying a picture to
   * delete from a manager.
   * Note that picture should already exist in the manager.
   * @param picture A picture to remove from the manager
   * @param manager A manager
   */
  public DeletePictureCommand(Picture picture, PictureManager manager){

    this.picture = picture;
    this.manager = manager;
  }

  /**
   * Undo the command by re-adding the picture back to the manager.
   * Note that if the picture already exist in the manager, it will not
   * re-add it back to the manager.
   */
  @Override
  public void undo() {
    if (!manager.contains(picture))
      manager.addPicture(picture);
  }

  /**
   * Execute the command by deleting the picture from the manager.
   * Note that if the picture is no longer in the manager, it will
   * not do anything.
   */
  @Override
  public void execute() {
    if (manager.contains(picture))
      manager.untrackPicture(picture);
  }

  /**
   * Returns a log message for this command
   * @return A log message for this command
   */
  @Override
  public String getLogMessage() {
    return "Deleted a picture, " + picture + ", from the manager " + manager;
  }

  @Override
  public void redo() {
    // TODO Auto-generated method stub
    
  }
}
