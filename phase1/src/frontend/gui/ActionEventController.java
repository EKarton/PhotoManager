package frontend.gui;

import java.io.IOException;
import javafx.event.ActionEvent;

/**
 * This is a controller to handle all action events from the MainView
 */
public class ActionEventController extends Controller{

  /**
   * Constructs an ActionEventController
   * 
   * @param view the view this controller is associated with
   */
  public ActionEventController(MainView mainView) {
    super(mainView);
  }

  public void openDirectory(ActionEvent e) {
    String directory = this.getMainView().openDirectoryChooser(this.getMainView().getMainStage()).getAbsolutePath();

    try {
      this.getMainView().getListViewController().setItems(getFileManager().getImageList(directory));
    } catch (IOException e1) {
      // if for some reason it fails we will leave the display as before
    }
  }

  public void openDirectoryRecursively(ActionEvent e) {
    String directory = this.getMainView().openDirectoryChooser(this.getMainView().getMainStage()).getAbsolutePath();

    try {
      this.getMainView().getListViewController().setItems(getFileManager().getImageListRec(directory));
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
