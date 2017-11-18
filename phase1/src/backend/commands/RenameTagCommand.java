package backend.commands;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import backend.models.Tag;

/**
 * Command for renaming a Tag
 * 
 * Severity Level: INFO
 *
 */
public class RenameTagCommand implements Command {

  private Tag thisTag;

  private String newTagName;

  private String oldTagName;

  public RenameTagCommand(Tag tagToBeRenamed, String newName) {
    this.thisTag = tagToBeRenamed;
    this.newTagName = newName;
  }

  @Override
  public void undo() {
    thisTag.setLabel(oldTagName);
  }

  @Override
  public void execute() {
    this.oldTagName = thisTag.getLabel();
    this.thisTag.setLabel(newTagName);
  }

  @Override
  public LogRecord getLogRecord() {
    return new LogRecord(Level.INFO, "Renamed Tag @" + this.oldTagName + " to @" + this.newTagName);
  }
}
