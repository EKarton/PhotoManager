package backend.commands;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import backend.models.Picture;
import backend.models.Tag;

/**
 * A command class used to add multiple tags to a picture. Has a severity level of FINE.
 */
public class AddTagsToPicCommand implements Command {

  /**
   * A tag to add to the picture
   */
  private List<Tag> tagsToAdd;

  /**
   * The picture to add the tag to
   */
  private Picture picture;

  /**
   * Creates an instance of AddTagToPictureCommand. Note that the tag specified should exist in the
   * picture.
   * 
   * @param picture A picture
   * @param tag A tag to add to the picture
   */
  public AddTagsToPicCommand(Picture picture, List<Tag> tags) {
    this.picture = picture;
    this.tagsToAdd = tags;
  }

  /**
   * Undo the command by deleting the tag from the picture. If for whatever reason the tag does not
   * exist in the picture, it will do nothing.
   */
  @Override
  public void undo() {
    for (Tag tag : this.tagsToAdd) {
      picture.deleteTag(tag);
    }
  }

  /**
   * Execute the command by adding a tag to this picture. Note that if the picture does not exist,
   * it will not do anything. If the tag already exist, it will not do anything.
   */
  @Override
  public void execute() {
    for (Tag tag : this.tagsToAdd) {
      picture.addTag(tag);
    }
  }

  @Override
  public LogRecord getLogRecord() {
    return new LogRecord(Level.FINE, "added " + tagsToAdd + " Tag To " + picture.getTaglessName());
  }

}
