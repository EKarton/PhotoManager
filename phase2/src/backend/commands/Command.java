package backend.commands;

import java.util.logging.LogRecord;

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
   * Returns a log record for this command
   * 
   * @return A log record for this command
   */
  LogRecord getLogRecord();
}
