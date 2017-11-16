package frontend.gui;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ListViewController<T> implements ChangeListener<T>{
  ObservableList<T> items;
  
//  new Callback
  Callback<ListView<T>, ListCell<T>> callBack;
  
  public ListViewController(){
    List<T> defaultEmptyList = new ArrayList<>();
    this.items = FXCollections.observableList(defaultEmptyList);
  }

  public ObservableList<T> getItems() {
    return this.items;
  }
  
  public void setItems(List<T> list) {
    this.items.setAll(list);
  }
  
  public void addItem(T item) {
    this.items.add(item);
  }

  @Override
  public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
   System.out.println(newValue);
  }  
  
  public void rename(ActionEvent e) {
    
  }
  
  public void move(ActionEvent e) {
    
  }
  
  public void delete(ActionEvent e) {
    
  }
}
