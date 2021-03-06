package frontend.gui.windows;

import frontend.gui.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  /**
   * The main controller
   */
  private MainController mainController;

  /**
   * Launches the javaFX application
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Start the javafx program
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/MainView.fxml"));
    Parent root = loader.load();

    this.mainController = loader.getController();
    this.mainController.setStage(primaryStage);

    primaryStage.setTitle("Picture Manager");

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);

    primaryStage.setResizable(false);

    primaryStage.show();
  }

  /**
   * Saves the program state
   */
  @Override
  public void stop() {
    this.mainController.save();
  }


}
