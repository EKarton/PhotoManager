package backend.commands;

/**
 * An interface used to specify an undoable command.
 */
public interface Command {

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
