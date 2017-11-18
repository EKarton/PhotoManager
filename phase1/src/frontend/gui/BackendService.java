package frontend.gui;

import backend.commands.CommandManager;
import backend.models.AppSettings;
import backend.models.PictureManager;
import java.io.IOException;
import java.util.Observable;
import javafx.application.Platform;

public class BackendService extends Observable {
  private CommandManager commandManager;
  private PictureManager pictureManager;
  private AppSettings appSettings;

  public BackendService() {
    try {
      commandManager = new CommandManager();
      pictureManager = new PictureManager();
      appSettings = new AppSettings();

      appSettings.addPicToManager(pictureManager);

      setChanged();
      notifyObservers();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void resetBackendService(String directory, boolean isRecursive){
    try{
      pictureManager = new PictureManager(directory, isRecursive);
      appSettings.addPicToManager(pictureManager);

      setChanged();
      notifyObservers();
    }
    catch (IOException e){
    }
  }
  
  public CommandManager getCommandManager() {
    return this.commandManager;
  }
  
  public PictureManager pictureManager() {
    return this.pictureManager;
  }
}
