package frontend.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class MainController implements Initializable {

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("GUI has just started");
  }
  
  public void openDirectory() {
    // TODO fill in
  }
  
  public void openDirectryRecursively() {
    // TODO fill in
  }
  
  public void openLog() {
    // TODO fill in
  }
  
  public void manageTags() {
    // TODO fill in
  }
  
  public void save() {
    System.out.println("Save");
  }
  
  public void undo() {
    System.out.println("Undo");
  }
  
  public void redo() {
    System.out.println("Redo");
  }

}
