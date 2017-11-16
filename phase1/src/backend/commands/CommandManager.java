package backend.commands;

import java.util.LinkedList;
import javax.naming.NoInitialContextException;

public class CommandManager {
  private LinkedList<Command> commandStack = new LinkedList<>();

  public void addCommand(Command command){
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
