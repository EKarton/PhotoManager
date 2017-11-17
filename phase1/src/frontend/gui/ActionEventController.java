package frontend.gui;

import java.io.File;
import java.io.IOException;
import backend.files.FileManager;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

/**
 * This is a controller to handle all action events from the MainView
 */
public class ActionEventController {

  private MainView view;
  private FileManager fileManager = new FileManager();

  /**
   * Constructs an ActionEventController
   * 
   * @param view the view this controller is associated with
   */
  public ActionEventController(MainView view) {
    this.view = view;
  }

  public void addImage(ActionEvent e) {
    // TODO complete
    File file = this.view.openFileChooser(this.view.getMainStage());
  }

  public void addDirectory(ActionEvent e) {
    // TODO complete
    File dir = this.view.openDirectoryChooser(this.view.getMainStage());
  }

  public void openDirectory(ActionEvent e) {
    String directory = this.view.openDirectoryChooser(this.view.getMainStage()).getAbsolutePath();

    try {
      this.view.getListViewController().setItems(fileManager.getImageList(directory));
    } catch (IOException e1) {
      // if for some reason it fails we will leave the display as before
    }
  }

  public void openDirectoryRecursively(ActionEvent e) {
    String directory = this.view.openDirectoryChooser(this.view.getMainStage()).getAbsolutePath();

    try {
      this.view.getListViewController().setItems(fileManager.getImageListRec(directory));
    } catch (IOException e1) {
      // if for some reason it fails we will leave the display as before
    }
  }

  public void openLog(ActionEvent e) {
    // TODO complete
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
