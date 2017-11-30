package tests.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import backend.commands.RenamePictureCommand;
import backend.models.Picture;
import org.junit.jupiter.api.Test;

class RenamePictureCommandTest {

  private final String picturePath =
      "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg";

  @Test
  void undo() {
    Picture picture = new Picture(picturePath);
    RenamePictureCommand command = new RenamePictureCommand(picture, "chicken");
    command.execute();
    command.undo();
    assertEquals(picture.getTaglessName(), "chick");
  }

  @Test
  void execute() {
    Picture picture = new Picture(picturePath);
    RenamePictureCommand command = new RenamePictureCommand(picture, "chicken");
    command.execute();
    assertEquals(picture.getTaglessName(), "chicken");
  }
}
