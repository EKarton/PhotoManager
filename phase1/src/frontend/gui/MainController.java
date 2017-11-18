package frontend.gui;

import backend.commands.CommandManager;
import backend.models.PictureManager;
import javafx.application.Platform;

public class MainController extends Controller {
  private CommandManager commandManager;
  private PictureManager pictureManager;

  public MainController(MainView mainView) {
    super(mainView);

    try {
      this.commandManager = new CommandManager();
      this.pictureManager = new PictureManager();
    } catch (Exception e) {
      Platform.exit();
    }

  }
  
  public CommandManager getCommandManager() {
    return this.commandManager;
  }
  
  public PictureManager getPictureManager() {
    return this.pictureManager;
  }
  
  public void setPictureManager(PictureManager pictureManager) {
   this.pictureManager = pictureManager; 
  }
}
