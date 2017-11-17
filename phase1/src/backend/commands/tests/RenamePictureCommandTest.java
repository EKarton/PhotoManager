package backend.commands.tests;

import backend.models.Picture;
import backend.commands.RenamePictureCommand;
import org.junit.jupiter.api.Test;

class RenamePictureCommandTest {

  private final String picturePath = "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg";

  @Test
  void undo() {
    Picture picture = new Picture(picturePath);
    RenamePictureCommand command = new RenamePictureCommand(picture, "chicken");
    command.execute();
    command.undo();
    assert(picture.getTaglessName().equals("chick"));
  }

  @Test
  void execute() {
    Picture picture = new Picture(picturePath);
    RenamePictureCommand command = new RenamePictureCommand(picture, "chicken");
    command.execute();
    assert(picture.getTaglessName().equals("chicken"));
  }

  @Test
  void getLogMessage() {
    Picture picture = new Picture(picturePath);
    RenamePictureCommand command = new RenamePictureCommand(picture, "chicken");
    assert(command.getLogMessage().equals(""));
    command.execute();
    assert(command.getLogMessage().equals(""));
    command.undo();
    assert(command.getLogMessage().equals(""));
  }

}