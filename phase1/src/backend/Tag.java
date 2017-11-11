package backend;

import java.util.Observable;

public class Tag extends Observable {
    private String label;

    public Tag (String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
        this.setChanged();
        this.notifyObservers(label);
    }

    @Override
    public String toString(){
        return label;
    }
}
