package frontend.gui.services;

import java.io.IOException;
import backend.commands.CommandManager;
import backend.commands.RenamePictureCommand;
import backend.models.AppSettings;
import backend.models.Picture;
import backend.models.PictureManager;

/**
 * This class is used by the main controller to make calls to the backend (model)
 */
public class BackendService {
  /** The CommandManager used by our program */
  private CommandManager commandManager;

  /** The PictureManager used by our program to manager all of the user's photos */
  private PictureManager pictureManager;

  /** Holds the configuration data used by our program */
  private AppSettings appSettings;

  /**
   * Constructs a new BackendService. When the app settings was not found /corrupted / locked by
   * another application, it will create a new app settings file.
   */
  public BackendService() {

    try {
      this.commandManager = new CommandManager();
      this.pictureManager = new PictureManager();
      this.appSettings = AppSettings.loadFromFile();

      // try to use previously existing app settings
      this.appSettings.addPicToManager(pictureManager);
    } catch (IOException | ClassNotFoundException e) {
      // if it fails and we don't have app settings, make new app settings
      this.appSettings = new AppSettings();
    }
  }

  /**
   * Resets the backend service by supplying the PictureManager with a new set of pictures in a
   * specific location in the OS.
   * 
   * @param directory The directory to get pictures from
   * @param isRecursive True if you want to collect pictures recursively, otherwise false
   */
  public void resetBackendService(String directory, boolean isRecursive) {
    try {
      this.pictureManager = new PictureManager(directory, isRecursive);
      this.appSettings.addPicToManager(pictureManager);
    } catch (IOException e) {
      // This should never occur
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

  /**
   * Renames a picture
   * 
   * @param picture the picture to be renamed
   * @param newName the new name
   */
  public void rename(Picture picture, String newName) {

    if (picture == null || newName == null) {
      return;
    }

    if (!newName.contains("@") && !newName.equals(picture.getTaglessName())) {

      RenamePictureCommand renamePictureCommand = new RenamePictureCommand(picture, newName);
      this.commandManager.addCommand(renamePictureCommand);
      renamePictureCommand.execute(); // execute the command
    }
  }
}
