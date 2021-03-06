package tests.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;

import backend.commands.AddPictureCommand;
import backend.commands.DeletePictureCommand;
import backend.models.Picture;
import backend.models.PictureManager;
import org.junit.jupiter.api.Test;

class DeletePictureCommandTest {

  private final String picturePath =
      "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg";

  @Test
  void undo() {
    Picture picture = new Picture(picturePath);
    PictureManager manager = new PictureManager();
    AddPictureCommand addPictureCommand = new AddPictureCommand(picture, manager);
    addPictureCommand.execute();

    DeletePictureCommand deletePictureCommand = new DeletePictureCommand(picture, manager);
    deletePictureCommand.execute();
    deletePictureCommand.undo();
    assert (manager.contains(picture));
  }

  @Test
  void execute() {
    Picture picture = new Picture(picturePath);
    PictureManager manager = new PictureManager();
    AddPictureCommand addPictureCommand = new AddPictureCommand(picture, manager);
    addPictureCommand.execute();

    DeletePictureCommand deletePictureCommand = new DeletePictureCommand(picture, manager);
    deletePictureCommand.execute();
    assertFalse(manager.getPictures().contains(picture));
  }
}
