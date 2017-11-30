package backend.commands;

import backend.models.Picture;
import backend.models.PictureManager;
import backend.models.Tag;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Command for reverting a picture to a previous Tag state
 *
 * Severity Level: INFO
 */
public class RevertTagStateCommand implements Command {

  /**
   * the Tags to revert to
   */
  private List<Tag> tagsToRevert;

  /**
   * the current list of tags of the picture
   */
  private List<Tag> currTags;

  /**
   * the picture to rename
   */
  private Picture picture;

  /**
   * the pictureManager to update information on
   */
  private PictureManager manager;

  /**
   * Revert a picture back to a previous tag state
   */
  public RevertTagStateCommand(PictureManager manager, Picture picture, List<Tag> historicalTags) {
    this.tagsToRevert = historicalTags;
    this.currTags = picture.getTags();
    this.picture = picture;
    this.manager = manager;
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
   * helper function for reverting, note: if a tag was renamed, reverting to a state where that tag
   * was at its previous name will add the tag with previous name as a new tag to the collection
   *
   */
  private void revertHelper(List<Tag> tagsToRevert, List<Tag> currTags) {
    for (Tag tag : tagsToRevert) {
      if (!currTags.contains(tag)) {
        if (!manager.getAvailableTags().contains(tag)) {
          manager.addTagToCollection(tag);
        }
        for (Tag thisTag : manager.getAvailableTags()) {
          if (thisTag.equals(tag)) {
            picture.addTag(thisTag);
          }
        }
      }
    }

    for (Tag tag : currTags) {
      if (!tagsToRevert.contains(tag)) {
        picture.deleteTag(tag);
      }
    }
  }


  /**
   * Info level: FINE
   */
  @Override
  public LogRecord getLogRecord() {
    return new LogRecord(Level.FINE,
        "Reverted Picture :" + this.picture + "To previous set of tags:" + this.tagsToRevert);
  }
}
