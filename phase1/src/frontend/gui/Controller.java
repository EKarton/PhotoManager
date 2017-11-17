package frontend.gui;

import backend.files.FileManager;

public class Controller {

  private MainView mainView;
  private FileManager fileManager;
  
  public Controller(MainView mainView) {
    this.mainView = mainView;
    this.fileManager = new FileManager();
  }

  public MainView getMainView() {
    return mainView;
  }

  public FileManager getFileManager() {
    return fileManager;
  }

}
