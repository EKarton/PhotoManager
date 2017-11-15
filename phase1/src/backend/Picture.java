package backend;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * A class used to represent a picture in the IO level
 */
public class Picture extends Observable implements Serializable{

    public Picture(String absolutePath){
    }

    public String getAbsolutePath(){
        return null;
    }

    public void setAbsolutePath(String absolutePath){
    }

    public String getDirectoryPath(){
        return null;
    }

    public void setDirectoryPath(){
    }

    public String getFullFileName(){
        return null;
    }

    public String getTaglessName(){
        return null;
    }

    public void setTaglessName(String newTaglessName){

    }

    public ArrayList<Tag> getTags(){
        return null;
    }

    public void removeTags(){

    }

    public void addTag(Tag tag){

    }

    public boolean containsTag(Tag tag){
        return false;
    }


    @Override
    public String toString(){
        return null;
    }
}
