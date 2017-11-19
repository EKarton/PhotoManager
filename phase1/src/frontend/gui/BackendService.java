package frontend.gui;

import java.io.IOException;
import backend.commands.CommandManager;
import backend.commands.RenamePictureCommand;
import backend.models.AppSettings;
import backend.models.Picture;
import backend.models.PictureManager;

/**
 * A service class used to simplify the calls to the backend services.
 */
public class BackendService {
  private CommandManager commandManager;
  private PictureManager pictureManager;
  private AppSettings appSettings;
  private MainView mainView;

  /**
   * Constructs a new BackendService. When the app settings was not found /corrupted / locked by
   * another application, it will create a new app settings file.
   */
  public BackendService(MainView mainView) {
    this.mainView = mainView;
    
    try {
      this.commandManager = new CommandManager();
      this.pictureManager = new PictureManager();
      this.appSettings = AppSettings.loadFromFile();
      this.appSettings.addPicToManager(pictureManager);
    } catch (IOException | ClassNotFoundException e) {
      this.appSettings = new AppSettings();
    }
  }

  /**
   * Resets the backend service by supplying the PictureManager with a new set of pictures in a
   * specific location in the OS.
   * 
   * @param directory The directory to grab pictures from
   * @param isRecursive True if to grab pictures recursively; else false.
   */
  public void resetBackendService(String directory, boolean isRecursive) {
    try {
      if (!directory.equals(this.pictureManager.getCurrDir())) {
        this.pictureManager = new PictureManager(directory, isRecursive);
        this.appSettings.addPicToManager(pictureManager);
      }
    } catch (IOException e) {
    }
  }

  /**
   * Returns the current command manager.
   * 
   * @return The current command manager.
   */
  public CommandManager getCommandManager() {
    return this.commandManager;
  }

  /**
   * Returns the current picture manager
   * 
   * @return The current picture manager.
   */
  public PictureManager getPictureManager() {
    return this.pictureManager;
  }

  /**
   * Returns the current app settings for this app.
   * 
   * @return The current app settings
   */
  public AppSettings getAppSettings() {
    return this.appSettings;
  }

  /**
   * Saves the app settings object called Config. It will also update the app settings by appending
   * it with the list of pictures from the PictureManager
   */
  public void save() {
    try {
      this.appSettings.addPicFromManager(this.pictureManager);
      this.appSettings.save();
    } catch (IOException e) {
      // This should never happen, but if it does just don't save
    }
  }

  public void rename(Picture picture, String newName) {
    RenamePictureCommand renamePictureCommand = new RenamePictureCommand(picture, newName);

    this.mainView.getBackendService().getCommandManager()
        .addCommand(renamePictureCommand);
    renamePictureCommand.execute();
    this.mainView.getPictureViewer().updatePictureViewer(picture);
  }

}
