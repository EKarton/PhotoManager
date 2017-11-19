package frontend.gui;

import backend.commands.RenamePictureCommand;
import backend.models.Picture;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FileListCell extends ListCell<Picture>{
 
  private TextField textField;
  private FileListViewController controller;
  private String oldName;

  public FileListCell(FileListViewController controller){
    this.controller = controller;
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
    
    this.oldName = this.getText();
    
    this.setText(null);  // remove display of text
    
    textField = new TextField(this.oldName);
    textField.setOnKeyPressed(this::handleTextFieldInput);
    
    this.setGraphic(textField);  // display a textfield
    
    textField.requestFocus();  // put focus on the textfield
  }
  
  @Override
  public void cancelEdit() {
    super.cancelEdit();
    this.setGraphic(null);
    this.setText(this.oldName);
  }
  
  private void handleTextFieldInput(KeyEvent keyEvent) {
    KeyCode keyCode = keyEvent.getCode();
    
    if(keyCode == KeyCode.ENTER) {
      String text = textField.getText();
   
      if(text != "") {
        Picture picture = this.getItem();
        this.controller.getBackendService().rename(picture, text);

        this.commitEdit(this.getItem());
      }
    }
    else if(keyCode == KeyCode.ESCAPE) {
      this.cancelEdit();
    }
  }
}
