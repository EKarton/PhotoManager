package backend.commands;

import backend.PictureManager;

public class SaveConfigurationsCommand implements Command {

  private String directoryPath;
  private String fileName;
  private PictureManager manager;

  public SaveConfigurationsCommand(PictureManager manager, String directoryPath, String fileName){
    this.manager = manager;
    this.directoryPath = directoryPath;
    this.fileName = fileName;
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

  }

  /**
   * Returns a log message for this command
   *
   * @return A log message for this command
   */
  @Override
  public String getLogMessage() {
    return "Saving logs to " + directoryPath + "\\" + fileName;
  }
}
