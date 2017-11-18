package frontend.gui;

import backend.models.Picture;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observer;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;

/**
 * This is a controller to handle all action events from the MainView
 */
public class MainViewController extends Controller{

  private BackendService backendService;

  public MainViewController(MainView mainView, BackendService backendService) {
    super(mainView);
    this.backendService = backendService;
  }

  public void openDirectory(ActionEvent e) {
    String directory = this.getMainView().openDirectoryChooser().getAbsolutePath();
    this.backendService.resetBackendService(directory, false);

    this.getMainView().getListViewController().setItems(backendService.pictureManager().getPictures());
  }

  public void openDirectoryRecursively(ActionEvent e) {
    String directory = this.getMainView().openDirectoryChooser().getAbsolutePath();
    this.backendService.resetBackendService(directory, true);

    this.getMainView().getListViewController().setItems(backendService.pictureManager().getPictures());
  }

  public void openLog(ActionEvent e) {
    System.out.println("Open log");
  }

  public void save(ActionEvent e) {
    System.out.println("Save");
  }

  public void undo(ActionEvent e) {
    System.out.println("Undo");
  }

  public void redo(ActionEvent e) {
    System.out.println("Redo");
  }
}
