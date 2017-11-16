package backend.commands;

public interface Command {

  /**
   * Redos the command again
   */
  void redo();

  /**
   * Undo the command
   */
  void undo();

  /**
   * Execute the command
   */
  void execute();

  /**
   * Returns a log message for this command
   * @return A log message for this command
   */
  String getLogMessage();
}
