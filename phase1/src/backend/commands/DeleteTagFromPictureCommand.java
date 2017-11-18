package backend.commands;

import backend.models.PictureManager;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import backend.models.Picture;
import backend.models.Tag;

/**
 * A command used to delete a tag from a picture.
 */
public class DeleteTagFromPictureCommand implements Command {

  /**
   * A picture containing the tag to delete
   */
  private Picture picture;

  /**
   * The tag to delete.
   */
  private Tag tag;

  /**
   * Creates a DeleteTagFromPictureCommand specifying a tag to delete from a picture. Note: the tag
   * should be already in the picture.
   * 
   * @param picture A picture containing the tag
   * @param tag The tag to delete.
   */
  public DeleteTagFromPictureCommand(Picture picture, Tag tag) {
    this.picture = picture;
    this.tag = tag;
  }

  /**
   * Undo the command by re-adding the tag back to the picture. If for some reason the tag already
   * exist in the picture, it will not add the tag again to the picture.
   */
  @Override
  public void undo() {
    picture.addTag(tag);
  }

  /**
   * Execute the command by removing the tag from the picture. If for some reason the tag is already
   * not in the picture, it will do nothing.
   */
  @Override
  public void execute() {
    picture.deleteTag(tag);
  }

  @Override
  public LogRecord getLogRecord() {
    return new LogRecord(Level.FINE, "Removed " + tag + " from " + picture.getTaglessName());
  }
}
