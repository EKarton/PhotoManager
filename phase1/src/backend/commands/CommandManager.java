package backend.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;
import javax.naming.NoInitialContextException;
import backend.files.LogFormatter;

public class CommandManager {
  private Stack<Command> commandStack = new Stack<Command>();
  private Stack<Command> redoStack = new Stack<>();

  private static final Logger logger = Logger.getLogger(CommandManager.class.getName());
  private final String LOG_FILENAME = "renamingHistory";
  private Handler logFileHandler;

  private boolean areCommandsLimited = false;
  private int maxNumCommands = 10;

  public CommandManager() throws SecurityException, IOException {
    logger.setLevel(Level.INFO);
    logFileHandler = new FileHandler(LOG_FILENAME, true);
    logFileHandler.setFormatter(new LogFormatter());
    logger.addHandler(logFileHandler);
  }

  public CommandManager(int maxNumCommands) throws SecurityException, IOException {
    this.areCommandsLimited = true;
    this.maxNumCommands = maxNumCommands;
    logger.setLevel(Level.INFO);
    logFileHandler = new FileHandler(LOG_FILENAME, true);
    logFileHandler.setFormatter(new LogFormatter());
    logger.addHandler(logFileHandler);
  }

  public void addCommand(Command command) {
    if (!areCommandsLimited || maxNumCommands > commandStack.size()) {
      this.commandStack.push(command);
      logger.log(command.getLogRecord());
    }
  }


  public void clearHistory() {
    this.commandStack.clear();
    this.redoStack.clear();
  }

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

  public String getLogs(){
    try {
      BufferedReader reader = new BufferedReader(new FileReader(LOG_FILENAME));
      StringBuilder fileContent = new StringBuilder();
      String currLine = reader.readLine();
      while (currLine != null) {
        fileContent.append(currLine);
        fileContent.append("\n");
        currLine = reader.readLine();
      }
      return fileContent.toString();
    }
    catch (IOException e) {
      return "";
    }
  }
}
