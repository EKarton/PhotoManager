package frontend.gui;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import backend.models.Picture;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ListViewChangeListener implements ChangeListener<Picture> {

  private MainController mainController;
  
  public ListViewChangeListener(MainController mainController) {
    this.mainController = mainController;
  }
  
  @Override
  public void changed(ObservableValue<? extends Picture> observable, Picture oldValue,
      Picture newValue) {
    if (newValue != null) { // since we set it to null in edit mode
      this.mainController.getPictureViewController().setLabelText(newValue.getTaglessName());
//      try {
//        InputStream inputStream = new FileInputStream(newValue.getAbsolutePath());
//
//        BufferedImage bufferedImage = ImageIO.read(inputStream);
//        inputStream.close();
//        
//        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
//        this.mainController.setImage(image);
//      } catch (IOException e) {
//
//      }
    }
  }

}
