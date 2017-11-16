package backend.commands.tests;

import backend.models.Picture;
import backend.models.PictureManager;
import backend.commands.AddPictureCommand;
import backend.commands.DeletePictureCommand;
import org.junit.jupiter.api.Test;

class DeletePictureCommandTest {

  private final String picturePath = "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg";

  @Test
  void undo() {
    Picture picture = new Picture(picturePath);
    PictureManager manager = new PictureManager();
    AddPictureCommand addPictureCommand = new AddPictureCommand(picture, manager);
    addPictureCommand.execute();

    DeletePictureCommand deletePictureCommand = new DeletePictureCommand(picture, manager);
    deletePictureCommand.execute();
    deletePictureCommand.undo();
    assert(manager.getPictures().contains(picture));
  }

  @Test
  void execute() {
    Picture picture = new Picture(picturePath);
    PictureManager manager = new PictureManager();
    AddPictureCommand addPictureCommand = new AddPictureCommand(picture, manager);
    addPictureCommand.execute();

    DeletePictureCommand deletePictureCommand = new DeletePictureCommand(picture, manager);
    deletePictureCommand.execute();
    assert(!manager.getPictures().contains(picture));
  }

  @Test
  void getLogMessage() {
    Picture picture = new Picture(picturePath);
    PictureManager manager = new PictureManager();
    AddPictureCommand addPictureCommand = new AddPictureCommand(picture, manager);
    addPictureCommand.execute();

    DeletePictureCommand deletePictureCommand = new DeletePictureCommand(picture, manager);
    assert(deletePictureCommand.getLogMessage().equals(""));
    deletePictureCommand.execute();
    assert(deletePictureCommand.getLogMessage().equals(""));
    deletePictureCommand.undo();
    assert(deletePictureCommand.getLogMessage().equals(""));
  }
}