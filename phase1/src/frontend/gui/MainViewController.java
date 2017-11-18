package frontend.gui;

import java.io.IOException;
import java.util.Observer;
import javafx.beans.Observable;
import javafx.event.ActionEvent;

/**
 * This is a controller to handle all action events from the MainView
 */
public class MainViewController extends Controller implements Observer{

  public MainViewController(MainView mainView, BackendService service) {
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

  /**
   * This method is called whenever the observed object is changed. An application calls an
   * <tt>Observable</tt> object's <code>notifyObservers</code> method to have all the object's
   * observers notified of the change.
   *
   * @param o the observable object.
   * @param arg an argument passed to the <code>notifyObservers</code>
   */
  @Override
  public void update(java.util.Observable o, Object arg) {

  }
}
