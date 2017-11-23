package frontend.gui;

import backend.models.Picture;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ListViewChangeListener implements ChangeListener<Picture> {

  @Override
  public void changed(ObservableValue<? extends Picture> observable, Picture oldValue,
      Picture newValue) {
    if (newValue != null) { // since we set it to null in edit mode
      // update the picture view
//      this.getMainView().getPictureViewer().updatePictureViewer(newValue);
      System.out.println("HELLO");
    }
  }

}
