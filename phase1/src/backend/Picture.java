package backend;

import java.util.Observable;

public class Picture extends Observable{
    private String filePath;
    private String name;

    public Picture(String filePath, String name){
        this.filePath = filePath;
        this.name = name;
    }

    public String getFilePath(){
        return filePath;
    }

    public String getName(){
        return name;
    }

    public void setFilePath(String filePath){
        String oldFilePath = this.filePath;
        this.filePath = filePath;
        this.setChanged();
        this.notifyObservers(new Picture(oldFilePath, name));
    }

    public void setImageName(String name){
        String oldImageName = this.name;
        this.name = name;
        this.setChanged();
        this.notifyObservers(new Picture(filePath, oldImageName));
    }

    @Override
    public String toString(){
        return filePath;
    }
}
