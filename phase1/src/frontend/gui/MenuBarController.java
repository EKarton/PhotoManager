package frontend.gui;

import java.io.File;
import javax.naming.NoInitialContextException;
import javafx.event.ActionEvent;

/**
 * This is a controller to handle all action events from MenuBar
 */
public class MenuBarController extends Controller {

  public MenuBarController(MainView mainView, BackendService backendService) {
    super(mainView, backendService);
  }

  public void openDirectory(ActionEvent e) {
    this.openDirectory(false);
  }

  public void openDirectoryRecursively(ActionEvent e) {
    this.openDirectory(true);
  }

  private void openDirectory(boolean recursive) {
    File file = this.getMainView().openDirectoryChooser();

    if (file != null) {
      String directory = file.getAbsolutePath();

      this.getBackendService().resetBackendService(directory, recursive);
      this.getMainView().getListViewController()
          .setItems(this.getBackendService().getPictureManager().getPictures());
    }
  }

  public void openLog(ActionEvent e) {
    String logs = this.getBackendService().getCommandManager().getLogs();
    if (logs != "") {
      TextDialog dialog = new TextDialog(logs);
      dialog.setTitle("Rename logs");
      dialog.show();
    }
  }

  public void save(ActionEvent e) {
    this.getBackendService().save();
  }

  public void undo(ActionEvent e) {
    try {
      this.getBackendService().getCommandManager().undoRecentCommand();
    } catch (NoInitialContextException e1) {
    }
  }

  public void redo(ActionEvent e) {
    try {
      this.getBackendService().getCommandManager().redoRecentCommand();
    } catch (NoInitialContextException e1) {
    }
  }
}
