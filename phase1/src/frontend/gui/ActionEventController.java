package frontend.gui;

import java.io.File;
import javax.naming.NoInitialContextException;
import backend.models.PictureManager;
import javafx.event.ActionEvent;

/**
 * This is a controller to handle all action events from the MainView
 */
public class ActionEventController extends Controller {

  /**
   * Constructs an ActionEventController
   * 
   * @param view the view this controller is associated with
   */
  public ActionEventController(MainView mainView) {
    super(mainView);
  }

  private void openDirectory(boolean recursive) {
    File file = this.getMainView().openDirectoryChooser(this.getMainView().getMainStage());

    if (file != null) {
      String directory = file.getAbsolutePath();
      
      try {
        PictureManager pictureManager = new PictureManager(directory, recursive);
        this.getMainView().getMainController().setPictureManager(pictureManager);
        this.getMainView().getListViewController().setItems(pictureManager.getPictures());
      } catch (Exception exception) {
        this.getMainView().getListViewController().setItems(null);
      }
    }
  }

  public void openDirectory(ActionEvent e) {
    this.openDirectory(false);
  }

  public void openDirectoryRecursively(ActionEvent e) {
    this.openDirectory(true);
  }

  public void openLog(ActionEvent e) {
    // TODO openLog
  }

  public void save(ActionEvent e) {
    // TODO save
  }

  public void undo(ActionEvent e) {
    try {
      this.getMainView().getMainController().getCommandManager().undoRecentCommand();
    } catch (NoInitialContextException e1) {
    }
  }

  public void redo(ActionEvent e) {
    // TODO redo
  }
}
