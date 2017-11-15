package frontend.gui;

import java.io.File;
import javafx.event.ActionEvent;

/**
 * This is a controller to handle all action events from the MainView
 */
public class ActionEventController {

  private MainView view;

  /**
   * Constructs an ActionEventController
   * 
   * @param view the view this controller is associated with
   */
  public ActionEventController(MainView view) {
    this.view = view;
  }

  public void addImage(ActionEvent e) {
    System.out.println("Add Image");
  }

  public void addDirectory(ActionEvent e) {
    File dir = this.view.openDirectoryChooser(this.view.getMainStage());
  }

  public void openDirectory(ActionEvent e) {
    File dir = this.view.openDirectoryChooser(this.view.getMainStage());
  }

  public void openLog(ActionEvent e) {
    System.out.println("Open Log");
  }
}
