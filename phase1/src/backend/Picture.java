package backend;

import java.util.Observable;

public class Picture extends Observable{
    private String filePath;

    public Picture(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath(){
        return filePath;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
        this.setChanged();
        this.notifyObservers(filePath);
    }

    @Override
    public String toString(){
        return filePath;
    }
}
