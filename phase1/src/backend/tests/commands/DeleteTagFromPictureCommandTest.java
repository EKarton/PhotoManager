package backend.tests.commands;

import backend.commands.AddTagToPictureCommand;
import backend.models.Picture;
import backend.models.Tag;
import org.junit.jupiter.api.Test;

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
    assert (picture.containsTag(tag) == false);
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
