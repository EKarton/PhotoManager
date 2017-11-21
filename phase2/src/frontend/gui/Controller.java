package frontend.gui;

import backend.files.FileManager;

public abstract class Controller {

  private MainView mainView;
  private FileManager fileManager;
  private BackendService backendService;

  /**
   * Constucts a controller
   * 
   * @param mainView the main view of the application
   * @param backendService the BackEnd service being used by the application
   */
  public Controller(MainView mainView, BackendService backendService) {
    this.mainView = mainView;
    this.backendService = backendService;
    this.fileManager = new FileManager();
  }

  /**
   * Get the main view used by this program
   * 
   * @return the main view
   */
  public MainView getMainView() {
    return mainView;
  }

  /**
   * Gets the file manager used by this program
   * 
   * @return the file manager
   */
  public FileManager getFileManager() {
    return this.fileManager;
  }

  /**
   * Get the backend service used by this program
   * 
   * @return
   */
  public BackendService getBackendService() {
    return this.backendService;
  }

}
