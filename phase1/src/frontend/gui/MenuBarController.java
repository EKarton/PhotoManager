package frontend.gui;

import java.io.IOException;
import javax.naming.NoInitialContextException;
import javafx.event.ActionEvent;

/**
 * This is a controller to handle all action events from MenuBar
 */
public class MenuBarController extends Controller{

  public MenuBarController(MainView mainView, BackendService backendService) {
    super(mainView, backendService);
  }

  public void openDirectory(ActionEvent e) {
    String directory = this.getMainView().openDirectoryChooser().getAbsolutePath();
    this.getBackendService().resetBackendService(directory, false);

    this.getMainView().getListViewController().setItems(this.getBackendService().getPictureManager().getPictures());
  }

  public void openDirectoryRecursively(ActionEvent e) {
    String directory = this.getMainView().openDirectoryChooser().getAbsolutePath();
    this.getBackendService().resetBackendService(directory, true);

    this.getMainView().getListViewController().setItems(this.getBackendService().getPictureManager().getPictures());
  }

  public void openLog(ActionEvent e) {
    // TODO see what this does
    String logs = this.getBackendService().getCommandManager().getLogs();
    if (logs != "") {
      TextDialog dialog = new TextDialog(logs);
      dialog.setTitle("Rename logs");
      dialog.show();
    }
  }

  public void save(ActionEvent e) {
    try {
      this.getBackendService().getAppSettings().save();
    } catch (IOException e1) {
      // This should never happen, but if it does just don't save
    }
  }

  public void undo(ActionEvent e) {
    try {
      this.getBackendService().getCommandManager().undoRecentCommand();
    }
    catch (NoInitialContextException e1) {  }
  }

  public void redo(ActionEvent e) {
    try {
      this.getBackendService().getCommandManager().redoRecentCommand();
    }
    catch (NoInitialContextException e1) { }
  }
}
