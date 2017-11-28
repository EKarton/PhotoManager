package backend.commands;

import backend.files.LogFormatter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NoInitialContextException;

/**
 * 
 * A manager for managing commands and logging commands. It can redo/undo a command. Currently it
 * only logs commands that have a severity level of INFO.
 *
 */
public class CommandManager {
  /**
   * the Stack of Commands
   */
  private Stack<Command> commandStack = new Stack<Command>();

  /**
   * the Stack of commands that are redone.
   */
  private Stack<Command> redoStack = new Stack<>();

  /**
   * a Logger , currently set to only log INFO level Logs.
   */
  private static final Logger logger = Logger.getLogger(CommandManager.class.getName());
  /**
   * name of the LogFile
   */
  private final String LOG_FILENAME = "renamingHistory";
  /**
   * a FileHandler for the logger.
   */
  private Handler logFileHandler;

  /**
   * Constructs a new CommandManager
   * 
   * @throws SecurityException When opening up the renamingHistory file fails.
   * @throws IOException When the file cannot be opened.
   */
  public CommandManager() throws SecurityException, IOException {
    logger.setLevel(Level.INFO);
    logFileHandler = new FileHandler(LOG_FILENAME, true);
    logFileHandler.setFormatter(new LogFormatter());
    logger.addHandler(logFileHandler);
  }

  /**
   * Add this command to the command Stack, and logs it.
   * 
   * @param command A command to add to the command manager.
   */
  public void addCommand(Command command) {
    this.commandStack.push(command);
    logger.log(command.getLogRecord());
  }

  /**
   * Undo the most recent command added.
   * 
   * @throws NoInitialContextException Thrown when there are no more commands to undo.
   */
  public void undoRecentCommand() throws NoInitialContextException {
    if (commandStack.size() >= 1) {
      Command mostRecentCommand = commandStack.pop();
      redoStack.push(mostRecentCommand);
      mostRecentCommand.undo();
      Level logLevel = mostRecentCommand.getLogRecord().getLevel();
      String logMessage = "undo " + mostRecentCommand.getLogRecord().getMessage();
      logger.log(logLevel, logMessage);
    } else
      throw new NoInitialContextException("There are no commands present to undo!");
  }

  /**
   * Redo the most recent undone command.
   * 
   * @throws NoInitialContextException Thrown when there are no more commands to redo.
   */
  public void redoRecentCommand() throws NoInitialContextException {
    if (redoStack.size() >= 1) {
      Command mostRecentCommand = redoStack.pop();
      commandStack.push(mostRecentCommand);
      mostRecentCommand.execute();
      Level logLevel = mostRecentCommand.getLogRecord().getLevel();
      String logMessage = "redo " + mostRecentCommand.getLogRecord().getMessage();
      logger.log(logLevel, logMessage);
    } else
      throw new NoInitialContextException("There are no commands present to redo!");
  }

  /**
   * Get Logging information saved onto a file named LOG_FILENAME;
   * 
   * @return A string of log information stored in the renamingHistory file.
   */
  public String getLogs() {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(LOG_FILENAME));
      StringBuilder fileContent = new StringBuilder();
      String currLine = reader.readLine();
      while (currLine != null) {
        fileContent.append(currLine);
        fileContent.append("\n");
        currLine = reader.readLine();
      }
      reader.close();
      return fileContent.toString();
    } catch (IOException e) {
      return "";
    }
  }
}
