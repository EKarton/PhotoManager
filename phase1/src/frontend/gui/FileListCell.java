package frontend.gui;

import backend.commands.RenamePictureCommand;
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
    
    textField = new TextField(this.getItem().getTaglessName());
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
        RenamePictureCommand renamePictureCommand = new RenamePictureCommand(picture, text);
        view.getBackendService().getCommandManager().addCommand(renamePictureCommand);
        renamePictureCommand.execute();
        this.view.getPictureViewer().setPicture(this.getItem());

        this.cancelEdit();
      }
    }
    else if(keyCode == KeyCode.ESCAPE) {
      this.cancelEdit();
    }
  }
}
