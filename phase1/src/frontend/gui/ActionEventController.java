package frontend.gui;

import javafx.event.ActionEvent;

/**
 * This is a controller to handle all action events from the MainView
 */
public class ActionEventController {
  public void addImage(ActionEvent e) {
    System.out.println("Add Image");
  }

  public void addDirectory(ActionEvent e) {
    System.out.println("Add Directory");
  }
  
  public void openDirectory(ActionEvent e) {
    System.out.println("Open dir");
  }
  
  public void openLog(ActionEvent e) {
    System.out.println("Open Log");
  }
}
