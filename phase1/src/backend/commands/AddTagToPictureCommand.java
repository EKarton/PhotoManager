package backend.commands;

import backend.models.PictureManager;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import backend.models.Picture;
import backend.models.Tag;

/**
 * A command class used to add a tag to a picture.
 */
public class AddTagToPictureCommand implements Command {

  /**
   * A tag to add to the picture
   */
  private Tag tagToAdd;

  /**
   * The picture to add the tag to
   */
  private Picture picture;

  /**
   * Creates an instance of AddTagToPictureCommand. Note that the tag specified should exist in
   * the picture.
   * 
   * @param picture A picture
   * @param tag A tag to add to the picture
   */
  public AddTagToPictureCommand(Picture picture, Tag tag) {
    this.picture = picture;
    this.tagToAdd = tag;
  }

  /**
   * Undo the command by deleting the tag from the picture. If for whatever reason the tag does not
   * exist in the picture, it will do nothing.
   */
  @Override
  public void undo() {
    picture.deleteTag(tagToAdd);
  }

  /**
   * Execute the command by adding a tag to this picture. Note that if the picture does not exist,
   * it will not do anything. If the tag already exist, it will not do anything.
   */
  @Override
  public void execute() {
    picture.addTag(tagToAdd);
  }

  @Override
  public LogRecord getLogRecord() {
    return new LogRecord(Level.FINE, "added " + tagToAdd + " Tag To " + picture.getTaglessName());
  }
}
