package frontend.gui;

import backend.models.Picture;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FileListCell extends ListCell<Picture>{
 
  private TextField textField;
    
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
    
    textField = new TextField(name);
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
        this.getItem().setTaglessName(text);
        this.commitEdit(this.getItem());
      }
    }
    else if(keyCode == KeyCode.ESCAPE) {
      this.cancelEdit();
    }
  }
}
