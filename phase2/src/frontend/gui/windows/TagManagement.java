package frontend.gui.windows;

import backend.commands.RenameTagCommand;
import backend.models.Tag;
import frontend.gui.controllers.MainController;
import frontend.gui.customcontrols.ListViewCallback;
import frontend.gui.customcontrols.Renamable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tag manager pop up window
 */
public class TagManagement implements ChangeListener<Tag>, Renamable {

  /**
   * The stage of the pop up window
   */
  private Stage window;

  /**
   * The main controller of the program
   */
  private MainController mainController;

  /**
   * The list of tags
   */
  @FXML
  private ListView<Tag> tagListView;

  /**
   * Search box to search for tags
   */
  @FXML
  private TextField searchBox;

  /**
   * Button for renaming tags
   */
  @FXML
  private Button renameTagButton;

  /**
   * Constructs the tag manager window
   *
   * @param title the title of the window
   * @param mainController the main controller
   */
  public TagManagement(String title, MainController mainController) {
    this.window = new Stage();

    this.window.setTitle(title);

    this.mainController = mainController;

    // this window has to be exited to go back to other program
    this.window.initModality(Modality.APPLICATION_MODAL);

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/TagManagement.fxml"));
      loader.setController(this);
      Parent root = loader.load();

      this.window.setTitle(title);

      Scene scene = new Scene(root);
      this.window.setScene(scene);

      setUpComponents();

    } catch (IOException e) {
      // other wise dialog fails to load
    }

  }

  /**
   * Sets up the components
   */
  private void setUpComponents() {
    this.tagListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.tagListView.getItems()
        .setAll(this.mainController.getBackendService().getPictureManager().getAvailableTags());
    this.tagListView.requestFocus();
    this.tagListView.getSelectionModel().selectedItemProperty().addListener(this);

    this.tagListView.setCellFactory(new ListViewCallback<>(null, this));
  }

  /**
   * Updates the list of tags
   */
  private void updateTagList() {
    this.tagListView.getItems()
        .setAll(this.mainController.getBackendService().getPictureManager().getAvailableTags());
  }

  /**
   * Opens up a pop up window for adding tags
   */
  @FXML
  public void addTag() {
    // Code derived from http://o7planning.org/en/11537/javafx-textinputdialog-tutorial
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Add Tag");
    dialog.setHeaderText("Add Tag");
    dialog.setContentText("Name of tag:");

    Optional<String> result = dialog.showAndWait();

    if (result.isPresent()) {
      Tag tag = new Tag(result.get());
      if (!tag.getLabel().equals("") && !tag.getLabel().contains(".")
          && !tag.getLabel().contains("@")) {
        this.mainController.getBackendService().getPictureManager().addTagToCollection(tag);
        updateTagList();
      }
    }
  }

  /**
   * Opens up a pop up window for deleting tags
   */
  @FXML
  public void deleteTag() {
    List<Tag> selectedTags = this.tagListView.getSelectionModel().getSelectedItems();

    for (Tag tag : selectedTags) {
      this.mainController.getBackendService().getPictureManager().deleteTag(tag);
      this.mainController.getBackendService().getAppSettings()
          .addPicFromManager(this.mainController.getBackendService().getPictureManager());
    }
    updateTagList();
    this.mainController.getPictureViewController().refresh();
  }

  /**
   * Renames a selected tag to a new name.
   */
  @FXML
  public void renameTag() {
    // Get the new name for the tag
    // Code for the dialog box is derived from
    // http://o7planning.org/en/11537/javafx-textinputdialog-tutorial
    List<Tag> selectedTags = this.tagListView.getSelectionModel().getSelectedItems();
    if (selectedTags.size() == 1) {
      TextInputDialog newNameDialog = new TextInputDialog();
      newNameDialog.setHeaderText("Enter the new name of the tag:");
      newNameDialog.setContentText("New name of tag:");
      Optional<String> result = newNameDialog.showAndWait();
      if (result.isPresent()) {
        this.rename(result.get());
      }
    }
  }

  /**
   * Renames tags through the listview
   */

  @Override
  public void rename(String newName) {
    if (newName.equals("") || newName.contains(".") || newName.contains("@")) {
      return;
    }

    Tag tag = this.tagListView.getSelectionModel().getSelectedItem();
    for (Tag duplicaTag : this.mainController.getBackendService().getPictureManager()
        .getAvailableTags()) {
      if (duplicaTag.getLabel().equals(newName)) {
        return;
      }
    }
    RenameTagCommand renameTag =
        new RenameTagCommand(this.tagListView.getSelectionModel().getSelectedItem(), newName);
    mainController.getBackendService().getCommandManager().addCommand(renameTag);
    renameTag.execute();
    updateTagList();
    this.mainController.getPictureViewController().refresh();
  }

  /**
   * Filters the tags displayed based on the search bar
   */
  @FXML
  public void onFilterTagsList(KeyEvent keyEvent) {
    String filter = keyEvent.getCharacter().toLowerCase();
    String curText = this.searchBox.getText() + filter;
    if (filter.length() == 1  && Character.isLetterOrDigit(filter.charAt(0))) {
      ArrayList<Tag> filteredTags = new ArrayList<>();
      for (Tag tag : this.mainController.getBackendService().getPictureManager()
          .getAvailableTags()) {
        if (tag.getLabel().toLowerCase().contains(curText)) {
          filteredTags.add(tag);
        }
      }

      this.tagListView.getItems().setAll(filteredTags);
    } else {
      this.tagListView.getItems()
          .setAll(this.mainController.getBackendService().getPictureManager().getAvailableTags());
    }
  }

  /**
   * Show the pop up window
   */
  public void show() {
    this.window.show();
  }

  /**
   * This method needs to be provided by an implementation of {@code ChangeListener}. It is called
   * if the value of an {@link ObservableValue} changes.
   * <p>
   * In general is is considered bad practice to modify the observed value in this method.
   *
   * @param observable The {@code ObservableValue} which value changed
   * @param oldValue The old value
   */
  @Override
  public void changed(ObservableValue<? extends Tag> observable, Tag oldValue, Tag newValue) {
    // Hide the rename button if there is more than one item selected
    List<Tag> tagsSelected = this.tagListView.getSelectionModel().getSelectedItems();
    if (tagsSelected.size() > 1) {
      this.renameTagButton.setVisible(false);
    } else if (tagsSelected.size() == 1) {
      this.renameTagButton.setVisible(true);
    }
  }
}
