package frontend.gui;

import javax.naming.NoInitialContextException;
import javafx.event.ActionEvent;

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

    String logs = this.backendService.getCommandManager().getLogs();
    if (logs != "") {
      TextDialog dialog = new TextDialog(logs);
      dialog.setTitle("Rename logs");
      dialog.show();
    }
  }

  public void save(ActionEvent e) {
    System.out.println("Save");
  }

  public void undo(ActionEvent e) {
    System.out.println("Undo");

    try {
      this.backendService.getCommandManager().undoRecentCommand();
    }
    catch (NoInitialContextException e1) {  }
  }

  public void redo(ActionEvent e) {
    System.out.println("Redo");

    try {
      this.backendService.getCommandManager().redoRecentCommand();
    }
    catch (NoInitialContextException e1) { }
  }
}
