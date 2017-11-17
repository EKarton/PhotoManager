package backend.commands.tests;

import backend.models.Picture;
import backend.models.Tag;
import backend.commands.AddTagToPictureCommand;
import org.junit.jupiter.api.Test;

class DeleteTagFromPictureCommandTest {
  private final String picturePath = "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg";

  @Test
  void undo() {
    Picture picture = new Picture(picturePath);
    Tag tag = new Tag("Grandma");
    AddTagToPictureCommand command = new AddTagToPictureCommand(picture, tag);
    command.execute();
    command.undo();
    assert(picture.containsTag(tag));
  }

  @Test
  void execute() {
    Picture picture = new Picture(picturePath);
    Tag tag = new Tag("Grandma");
    AddTagToPictureCommand command = new AddTagToPictureCommand(picture, tag);
    command.execute();
    assert(picture.containsTag(tag) == false);
  }

  @Test
  void getLogMessage() {
    Picture picture = new Picture(picturePath);
    Tag tag = new Tag("Grandma");
    AddTagToPictureCommand command = new AddTagToPictureCommand(picture, tag);
    command.execute();
    assert(command.getLogMessage().equals(""));
    command.undo();
    assert(command.getLogMessage().equals(""));
  }
}