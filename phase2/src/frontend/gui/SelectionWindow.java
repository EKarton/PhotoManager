package frontend.gui;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class SelectionWindow<T> {
  private Stage window;
  
  @FXML
  ListView<T> items;
  @FXML
  Button enterButton;
  
  public SelectionWindow(Window owner, String title, String enter, List<T> items) {
    this.window = new Stage();
     
     this.window.setTitle(title);
     this.window.initOwner(owner);
     
     // this window has to be exited to go back to other program
     this.window.initModality(Modality.APPLICATION_MODAL);
     
     window.setX(owner.getX() + owner.getWidth());
     window.setY(owner.getY());
     
     try {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectionWindow.fxml"));
       loader.setController(this);
       Parent root = loader.load();
       
       this.window.setTitle(title);
       
       enterButton.setText(enter);
       
       this.items.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       this.items.getItems().setAll(items);
       
       Scene scene = new Scene(root);
       this.window.setScene(scene);
       
     } catch (IOException e) {
       // other wise dialog fails to load
     }

   }
  
  public void enter() {
    this.window.close();
  }
   
   public List<T> show() {
     this.window.showAndWait();
     
     return this.items.getSelectionModel().getSelectedItems();
   }
}
