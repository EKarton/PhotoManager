package frontend.gui;

import backend.models.Picture;
import java.awt.image.BufferedImage;
import java.io.File;
import backend.files.FileManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.imageio.ImageIO;

public class FileListCell extends ListCell<Picture>{
 
  private TextField textField;

  private MainView view;

  public FileListCell(MainView view){
    this.view = view;
  }
    
  @Override
  public void updateItem(Picture picture, boolean empty) {
    super.updateItem(picture, empty);
    if (empty) {
      setText(null);
    } else {
      setText(picture.getFullFileName());
    }
  }
  
  @Override
  public void startEdit() {
    super.startEdit();
    
    String name = this.getText();
    
    this.setText(null);  // remove display of text
    
    textField = new TextField(name.substring(0, name.lastIndexOf(".")));
    textField.setOnKeyPressed(this::handleTextFieldInput);
    
    this.setGraphic(textField);  // display a textfield
    
    textField.requestFocus();
  }
  
  @Override
  public void cancelEdit() {
    super.cancelEdit();
    this.setGraphic(null);
    this.setText(this.getItem().getFullFileName());
  }
  
  private void handleTextFieldInput(KeyEvent keyEvent) {
    KeyCode keyCode = keyEvent.getCode();
    
    if(keyCode == KeyCode.ENTER) {
      String text = textField.getText();
   
      if(text != "") {

        Picture picture = this.getItem();
        picture.setTaglessName(text);
        this.view.getPictureViewer().setPicture(this.getItem());

        /*

        this.view.getPictureImageView().setImage(null);
        Picture picture = this.getItem();
        picture.setTaglessName(text);

        try {
          InputStream inputStream = new FileInputStream(picture.getAbsolutePath());

          BufferedImage bufferedImage = ImageIO.read(inputStream);
          Image image = SwingFXUtils.toFXImage(bufferedImage, null);
          this.view.getPictureImageView().setImage(image);
          this.view.setPictureName(picture.getFullFileName());

          inputStream.close();
        }
        catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
        */

        this.cancelEdit();
      }
    }
    else if(keyCode == KeyCode.ESCAPE) {
      this.cancelEdit();
    }
  }
}
