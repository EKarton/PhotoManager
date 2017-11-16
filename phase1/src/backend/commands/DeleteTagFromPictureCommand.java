package backend.commands;

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
   * Creates a DeleteTagFromPictureCommand specifying a tag to delete from a picture.
   * Note: the tag should be already in the picture.
   * @param picture A picture containing the tag
   * @param tag The tag to delete.
   */
  public DeleteTagFromPictureCommand(Picture picture, Tag tag){
    this.picture = picture;
    this.tag = tag;
  }

  /**
   * Undo the command by re-adding the tag back to the picture.
   * If for some reason the tag already exist in the picture,
   * it will not add the tag again to the picture.
   */
  @Override
  public void undo() {
    if (!picture.containsTag(tag))
      picture.addTag(tag);
  }

  /**
   * Execute the command by removing the tag from the picture.
   * If for some reason the tag is already not in the picture,
   * it will do nothing.
   */
  @Override
  public void execute() {
    if (picture.containsTag(tag))
      picture.deleteTag(tag);
  }

  /**
   * Returns a log message for this command
   * @return A log message for this command
   */
  @Override
  public String getLogMessage() {
    return "Removed a tag " + tag + " from picture " + picture;
  }
}
