package frontend.gui;

import java.io.File;
import backend.files.FileManager;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FileListCell extends ListCell<File>{
 
  private TextField textField;
    
  @Override
  public void updateItem(File item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setText(null);
    } else {
      setText(item.getName());
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
    this.setText(this.getItem().getName());
  }
  
  private void handleTextFieldInput(KeyEvent keyEvent) {
    KeyCode keyCode = keyEvent.getCode();
    
    if(keyCode == KeyCode.ENTER) {
      String text = textField.getText();
   
      if(text != "") {
        FileManager fileManager = new FileManager();
        fileManager.renameFile(this.getItem(), text);
        this.commitEdit(fileManager.getRenamedFile(this.getItem(), text));
      }
    }
    else if(keyCode == KeyCode.ESCAPE) {
      this.cancelEdit();
    }
  }
}
