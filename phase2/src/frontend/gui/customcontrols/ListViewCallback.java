package frontend.gui.customcontrols;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Handles callback for list view to add a context menu
 * @param <T>
 */
public class ListViewCallback<T> implements Callback<ListView<T>, ListCell<T>> {

  /**
   * The context menu. This is a menu for renaming and moving the files
   */
  private ContextMenu contextMenu;

  /** The object that will be renamed if this cell is renamed */
  private Renamable renamable;

  /**
   * Constructs a ListViewCallback
   * 
   * @param contextMenu the context menu
   */
  public ListViewCallback(ContextMenu contextMenu, Renamable renamable) {
    this.contextMenu = contextMenu;
    this.renamable = renamable;
  }

  /**
   * Sets the context menu to this cell
   */
  @Override
  public ListCell<T> call(ListView<T> param) {
    ListCell<T> cell = new CustomListCell<T>(this.renamable);
    cell.setContextMenu(this.contextMenu);
    return cell;
  }

}
