package backend.commands;

import java.util.LinkedList;
import java.util.Stack;
import javax.naming.NoInitialContextException;

public class CommandManager {
  private Stack<Command> commandStack = new Stack<Command>();

  private boolean areCommandsLimited = false;
  private int maxNumCommands = 10;

  public CommandManager(){
  }

  public CommandManager(boolean limitStoredCommands, int maxNumCommands){
    this.areCommandsLimited = limitStoredCommands;
    this.maxNumCommands = maxNumCommands;
  }

  public void addCommand(Command command){
    if (areCommandsLimited && maxNumCommands > commandStack.size())
      this.commandStack.push(command);
  }

  public void undoRecentCommand() throws NoInitialContextException {
    if (commandStack.size() >= 1) {
      Command mostRecentCommand = commandStack.pop();
      mostRecentCommand.undo();
    }
    else
      throw new NoInitialContextException("There are no commands present!");
  }
}
