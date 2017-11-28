package backend.commands;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import backend.models.Picture;
import backend.models.Tag;

/**
 * A command used to delete a tag from a picture.
 * 
 * Severity Level: Fine
 */
public class DeleteTagsFromPicCommand implements Command {

  /**
   * A picture containing the tag to delete
   */
  private Picture picture;

  /**
   * The tags to delete.
   */
  private List<Tag> tags;

  /**
   * Creates a DeleteTagFromPictureCommand specifying tags to delete from a picture. Note: the tag
   * should be already in the picture.
   * 
   * @param picture A picture containing the tag
   * @param tag The tag to delete.
   */
  public DeleteTagsFromPicCommand(Picture picture, List<Tag> tags) {
    this.picture = picture;
    this.tags = tags;
  }

  /**
   * Undo the command by re-adding the tags back to the picture. If for some reason the tag already
   * exist in the picture, it will not add the tag again to the picture.
   */
  @Override
  public void undo() {
    for (Tag tag : this.tags) {
      picture.addTag(tag);
    }
  }

  /**
   * Execute the command by removing the tags from the picture. If for some reason the tag is
   * already not in the picture, it will do nothing.
   */
  @Override
  public void execute() {
    for (Tag tag : this.tags) {
      picture.deleteTag(tag);
    }
  }

  @Override
  public LogRecord getLogRecord() {
    return new LogRecord(Level.FINE, "Removed " + tags + " from " + picture.getTaglessName());
  }
}
