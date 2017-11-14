package backend;

import java.io.Serializable;
import java.util.Observable;

public class Tag extends Observable implements Serializable{
    private String label;
    private String description;

    public Tag (String label, String description){
        this.label = label;
        this.description = description;
    }
    
    public Tag (String label){
      this.label = label;
      this.description = "no description given";
  }

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        Tag oldTag = new Tag(this.label);
        this.label = label;
        this.setChanged();
        this.notifyObservers(oldTag);
    }
    
    public void setDescription(String description){
      String oldDescription = this.description;
      this.description = description;
      this.setChanged();
      this.notifyObservers(oldDescription);
    }

    @Override
    public String toString(){
        return label;
    }
}
