package frontend.gui;

import java.io.IOException;
import backend.commands.CommandManager;
import backend.models.AppSettings;
import backend.models.PictureManager;
import javafx.application.Platform;

public class BackendService {
  private CommandManager commandManager;
  private PictureManager pictureManager;
  private AppSettings appSettings;

  public BackendService() {
    try {
      commandManager = new CommandManager();
      pictureManager = new PictureManager();
      appSettings = new AppSettings();
    } catch (IOException e) {
      // TODO add fail safe
      Platform.exit();
    }
  }

  public void resetBackendService(String directory, boolean isRecursive) {
    try {
      pictureManager = new PictureManager(directory, isRecursive);
      appSettings.addPicToManager(pictureManager);
    } catch (IOException e) {
      //TODO add fail safe
    }
  }

  public CommandManager getCommandManager() {
    return this.commandManager;
  }

  public PictureManager getPictureManager() {
    return this.pictureManager;
  }
  
  public AppSettings getAppSettings() {
    return this.appSettings;
  }
}
