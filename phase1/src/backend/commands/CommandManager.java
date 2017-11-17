package backend.commands;

import java.io.IOException;
import java.util.Stack;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.naming.NoInitialContextException;

public class CommandManager {
  private Stack<Command> commandStack = new Stack<Command>();

  private static final Logger logger = Logger.getLogger(CommandManager.class.getName());
  private Handler logFileHandler;

  private boolean areCommandsLimited = false;
  private int maxNumCommands = 10;

  public CommandManager() throws SecurityException, IOException {
    logger.setLevel(Level.ALL);
    logFileHandler = new FileHandler("commandHistory", true);
    logFileHandler.setFormatter(new SimpleFormatter());
    logger.addHandler(logFileHandler);
  }

  public CommandManager(int maxNumCommands) throws SecurityException, IOException {
    this.areCommandsLimited = true;
    this.maxNumCommands = maxNumCommands;
    logger.setLevel(Level.ALL);
    logFileHandler = new FileHandler("commandHistory", true);
    logFileHandler.setFormatter(new SimpleFormatter());
    logger.addHandler(logFileHandler);
  }

  public void addCommand(Command command) {
    if (!areCommandsLimited || maxNumCommands > commandStack.size()) {
      this.commandStack.push(command);
      logger.log(Level.ALL, command.getLogMessage());
    }
  }


  public void undoRecentCommand() throws NoInitialContextException {
    if (commandStack.size() >= 1) {
      Command mostRecentCommand = commandStack.pop();
      mostRecentCommand.undo();
      logger.log(Level.ALL, "undo" + mostRecentCommand.getLogMessage());
    } else
      throw new NoInitialContextException("There are no commands present!");
  }
}
