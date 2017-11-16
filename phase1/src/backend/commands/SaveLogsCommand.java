package backend.commands;

import backend.logger.Logger;

public class SaveLogsCommand implements Command{

  private Logger logger;
  private String directoryPath;
  private String fileName;

  public SaveLogsCommand(Logger logger, String directoryPath, String fileName){

    this.logger = logger;
    this.directoryPath = directoryPath;
    this.fileName = fileName;
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
    return null;
  }
}
