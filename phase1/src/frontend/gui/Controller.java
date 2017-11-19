package frontend.gui;

import backend.files.FileManager;

public class Controller {

  private MainView mainView;
  private FileManager fileManager;
  private BackendService backendService;
  
  public Controller(MainView mainView, BackendService backendService) {
    this.mainView = mainView;
    this.backendService = backendService;
    this.fileManager = new FileManager();
  }

  public MainView getMainView() {
    return mainView;
  }

  public FileManager getFileManager() {
    return this.fileManager;
  }
  
  public BackendService getBackendService() {
    return this.backendService;
  }

}
