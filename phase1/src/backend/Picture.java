package backend;

import java.util.Observable;

/**
 * A class used to represent a picture
 */
public class Picture extends Observable{

    // The file path to this picture (does not include its file name!!)
    private String filePath;

    // The name of the picture (does not include its tag names!!!!)
    private String name;

    /**
     * Creates a new instance of the Picture class
     * @param filePath The file path to this picture (does not include its file name!!)
     * @param name The name to this picture
     */
    public Picture(String filePath, String name){
        this.filePath = filePath;
        this.name = name;
    }

    /**
     * Returns the file path to this picture (does not include its file name!!)
     * @return The file path to this picture
     */
    public String getFilePath(){
        return filePath;
    }

    /**
     * Returns the file name of this picture (does not include its tag names!!)
     * @return The name of this picture
     */
    public String getName(){
        return name;
    }

    /**
     * Set the file path of this picture
     * Pre-condition: it should not contain its file name
     * @param filePath The absolute path to this file
     */
    public void setFilePath(String filePath){
        String oldFilePath = this.filePath;
        this.filePath = filePath;
        this.setChanged();
        this.notifyObservers(new Picture(oldFilePath, name));
    }

    /**
     * Set the file name of this picture
     * Pre-condition: it should not contain its file path
     * @param name The name of this picture
     */
    public void setImageName(String name){
        String oldImageName = this.name;
        this.name = name;
        this.setChanged();
        this.notifyObservers(new Picture(filePath, oldImageName));
    }

    /**
     * Returns a string representation of this picture
     * @return The picture
     */
    @Override
    public String toString(){
        return "Path: " + filePath + " | Name: " + name;
    }
}
