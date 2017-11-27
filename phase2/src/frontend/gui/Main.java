package frontend.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
  
  private MainController mainController;
  
  public static void main(String[] args) {
   launch(args); 
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
    Parent root = loader.load();
    
    this.mainController = loader.getController();
    this.mainController.setStage(primaryStage);
    
    primaryStage.setTitle("Picture Manager");
    
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    
    primaryStage.show();
  }
  
  @Override
  public void stop() {
    this.mainController.save();
  }
  
  
}