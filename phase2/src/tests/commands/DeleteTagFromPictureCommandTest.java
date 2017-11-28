package commands;

import static org.junit.Assert.assertFalse;
import org.junit.jupiter.api.Test;
import backend.commands.AddTagToPictureCommand;
import backend.models.Picture;
import backend.models.Tag;

class DeleteTagFromPictureCommandTest {
  private final String picturePath =
      "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg";

  @Test
  void undo() {
    Picture picture = new Picture(picturePath);
    Tag tag = new Tag("Grandma");
    AddTagToPictureCommand command = new AddTagToPictureCommand(picture, tag);
    command.execute();
    command.undo();
    assertFalse(picture.containsTag(tag));
  }

  @Test
  void execute() {
    Picture picture = new Picture(picturePath);
    Tag tag = new Tag("Grandma");
    AddTagToPictureCommand command = new AddTagToPictureCommand(picture, tag);
    command.execute();
    assert (picture.containsTag(tag));
  }
}
