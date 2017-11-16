package backend.commands.tests;

import backend.models.Picture;
import backend.models.PictureManager;
import backend.commands.AddPictureCommand;

class AddPictureCommandTest {

  private final String picturePath = "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg";

  @org.junit.jupiter.api.Test
  void undo() {
    Picture picture = new Picture(picturePath);
    PictureManager manager = new PictureManager();
    AddPictureCommand command = new AddPictureCommand(picture, manager);
    command.execute();
    command.undo();

    assert(manager.getPictures().size() == 0);
  }

  @org.junit.jupiter.api.Test
  void execute() {
    Picture picture = new Picture(picturePath);
    PictureManager manager = new PictureManager();
    AddPictureCommand command = new AddPictureCommand(picture, manager);
    command.execute();

    assert(manager.getPictures().size() == 1);
  }

  @org.junit.jupiter.api.Test
  void getLogMessage() {
    Picture picture = new Picture(picturePath);
    PictureManager manager = new PictureManager();
    AddPictureCommand command = new AddPictureCommand(picture, manager);
    assert(command.getLogMessage().equals(""));
    command.execute();
    assert(command.getLogMessage().equals(""));
    command.undo();
    assert(command.getLogMessage().equals(""));
  }
}