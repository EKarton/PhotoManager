package backend.commands;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import backend.models.Picture;
import backend.models.Tag;

/**
 * Command for reverting a picture to a previous Tag state
 * 
 * Severity Level: INFO
 *
 */
public class RevertTagStateCommand implements Command {

  /** the Tags to revert to */
  private ArrayList<Tag> tagsToRevert;

  /** the current list of tags of the picture */
  private ArrayList<Tag> currTags;

  /** the picture to rename */
  private Picture picture;

  /**
   * Revert a picture back to a previous tag state
   * 
   * @param picture
   * @param historicalTags
   */
  public RevertTagStateCommand(Picture picture, ArrayList<Tag> historicalTags) {
    this.tagsToRevert = historicalTags;
    this.currTags = picture.getTags();
    this.picture = picture;
  }

  /**
   * undo the revert
   */
  @Override
  public void undo() {
    this.revertHelper(currTags, tagsToRevert);
  }

  /**
   * Revert the tags back to a previous stage
   */
  @Override
  public void execute() {
    this.revertHelper(tagsToRevert, currTags);
  }

  /**
   * helper function for reverting
   * 
   * @param tagsToRevert
   * @param currTags
   */
  private void revertHelper(ArrayList<Tag> tagsToRevert, ArrayList<Tag> currTags) {
    for (Tag tag : tagsToRevert) {
      if (!currTags.contains(tag)) {
        picture.addTag(tag);
      }
    }
    for (Tag tag : currTags) {
      if (!tagsToRevert.contains(tag)) {
        picture.deleteTag(tag);
      }
    }
  }


  /**
   * 
   */
  @Override
  public LogRecord getLogRecord() {
    return new LogRecord(Level.FINE,
        "Reverted Picture :" + this.picture + "To previous set of tags:" + this.tagsToRevert);
  }
}
