package frontend.gui;

import backend.models.Picture;
import frontend.gui.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/** Listens for selection changes in the ListView */
public class ListViewChangeListener implements ChangeListener<Picture> {

  /** The main controller, the controller of the main view where this ListView is displayed */
  private MainController mainController;

  /**
   * Constructs the ListViewChangeListener
   * 
   * @param mainController the main controller
   */
  public ListViewChangeListener(MainController mainController) {
    this.mainController = mainController;
  }

  /**
   * Updates the picture view when the selection changes
   */
  @Override
  public void changed(ObservableValue<? extends Picture> observable, Picture oldValue,
      Picture newValue) {
    if (newValue != null) { // since we set it to null in edit mode
      this.mainController.getPictureViewController().setPicture(newValue);
    }
  }

}
