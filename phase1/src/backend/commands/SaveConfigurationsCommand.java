package backend.commands;

public class SaveConfigurationsCommand implements Command {

  private String directoryPath;
  private String fileName;


  public SaveConfigurationsCommand(String directoryPath, String fileName) {
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
    return "ConfigLogSaved " + directoryPath + "\\" + fileName;
  }
}
