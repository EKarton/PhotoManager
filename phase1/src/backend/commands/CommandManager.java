package backend.commands;

import java.io.IOException;
import java.util.logging.Formatter;
import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.naming.NoInitialContextException;

public class CommandManager {
  private Stack<Command> commandStack = new Stack<Command>();

  private static Logger logger = Logger.getLogger("commandLogger");
  private Handler logFileHandler;

  private boolean areCommandsLimited = false;
  private int maxNumCommands = 10;

  public CommandManager() throws SecurityException, IOException {
    logFileHandler = new FileHandler("/commandHistory.log", true);
    logFileHandler.setFormatter(new SimpleFormatter());
    logger.addHandler(logFileHandler);
  }

  public CommandManager(boolean limitStoredCommands, int maxNumCommands)
      throws SecurityException, IOException {
    this.areCommandsLimited = limitStoredCommands;
    this.maxNumCommands = maxNumCommands;
    logFileHandler = new FileHandler("/commandHistory.log", true);
    logFileHandler.setFormatter(new SimpleFormatter());
    logger.addHandler(logFileHandler);
  }

  public void addCommand(Command command) {
    if (areCommandsLimited && maxNumCommands > commandStack.size()) {
      this.commandStack.push(command);
      logger.log(Level.ALL, command.getLogMessage());
    }
  }


  public void undoRecentCommand() throws NoInitialContextException {
    if (commandStack.size() >= 1) {
      Command mostRecentCommand = commandStack.pop();
      mostRecentCommand.undo();
      logger.log(Level.ALL, "redo" + mostRecentCommand.getLogMessage());
    } else
      throw new NoInitialContextException("There are no commands present!");
  }
}
