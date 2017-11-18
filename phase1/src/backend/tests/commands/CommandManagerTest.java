package backend.tests.commands;

import backend.commands.AddPictureCommand;
import backend.commands.CommandManager;
import backend.models.Picture;
import backend.models.PictureManager;
import java.io.IOException;
import javax.naming.NoInitialContextException;
import org.junit.jupiter.api.Test;

class CommandManagerTest {

  private final String pic1 =
      "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg";
  private final String pic2 =
      "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken - Copy.jpg";

  @Test
  void addCommand() throws SecurityException, IOException {
    CommandManager commandManager = new CommandManager();
    Picture picture1 = new Picture(pic1);
    Picture picture2 = new Picture(pic2);
    PictureManager manager = new PictureManager();
    AddPictureCommand addPictureCommand1 = new AddPictureCommand(picture1, manager);
    AddPictureCommand addPictureCommand2 = new AddPictureCommand(picture2, manager);
    addPictureCommand1.execute();
    addPictureCommand2.execute();
    commandManager.addCommand(addPictureCommand1);
    commandManager.addCommand(addPictureCommand2);
    assert (manager.getPictures().size() == 2);
  }

  @Test
  void undoRecentCommand() throws NoInitialContextException, SecurityException, IOException {
    CommandManager commandManager = new CommandManager();
    Picture picture1 = new Picture(pic1);
    Picture picture2 = new Picture(pic2);
    PictureManager manager = new PictureManager();
    AddPictureCommand addPictureCommand1 = new AddPictureCommand(picture1, manager);
    AddPictureCommand addPictureCommand2 = new AddPictureCommand(picture2, manager);
    addPictureCommand1.execute();
    addPictureCommand2.execute();
    commandManager.addCommand(addPictureCommand1);
    commandManager.addCommand(addPictureCommand2);
    commandManager.undoRecentCommand();
    assert (manager.getPictures().size() == 1);
    commandManager.undoRecentCommand();
    assert (manager.getPictures().size() == 0);
  }
}
