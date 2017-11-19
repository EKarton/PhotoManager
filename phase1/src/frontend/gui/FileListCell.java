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

  private MainView view;  //TODO should it have this

  public FileListCell(MainView view){
    this.view = view;
  }
    
  @Override
  public void updateItem(Picture item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setText(null);
    } else {
      setText(item.getTaglessName());
    }
  }
  
  @Override
  public void startEdit() {
    super.startEdit();
    
    String name = this.getText();
    
    this.setText(null);  // remove display of text
    
    textField = new TextField(this.getItem().getTaglessName());  // TODO make sure this works, I had below
//    textField = new TextField(name);
    
    textField.setOnKeyPressed(this::handleTextFieldInput);
    
    this.setGraphic(textField);  // display a textfield
    
    textField.requestFocus();
  }
  
  @Override
  public void cancelEdit() {
    super.cancelEdit();
    this.setGraphic(null);
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

        this.commitEdit(this.getItem());
      }
    }
    else if(keyCode == KeyCode.ESCAPE) {
      this.cancelEdit();
    }
  }
}
