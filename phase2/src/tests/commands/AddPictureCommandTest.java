package tests.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import backend.commands.AddPictureCommand;
import backend.models.Picture;
import backend.models.PictureManager;

class AddPictureCommandTest {

  private final String picturePath =
      "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg";

  @org.junit.jupiter.api.Test
  void undo() {
    Picture picture = new Picture(picturePath);
    PictureManager manager = new PictureManager();
    AddPictureCommand command = new AddPictureCommand(picture, manager);
    command.execute();
    command.undo();

    assertEquals(manager.getPictures().size(), 0);
  }

  @org.junit.jupiter.api.Test
  void execute() {
    Picture picture = new Picture(picturePath);
    PictureManager manager = new PictureManager();
    AddPictureCommand command = new AddPictureCommand(picture, manager);
    command.execute();

    assertEquals(manager.getPictures().size(), 1);
  }
}
