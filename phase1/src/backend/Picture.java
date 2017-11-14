package backend;

import java.io.File;
import java.io.Serializable;
import java.util.Observable;

/**
 * A class used to represent a picture in the IO level
 */
public class Picture extends Observable implements Serializable{

    /**
     * The absolute path to the picture, i.e,
     * contains the parent directory and the full file name
     */
    private String absolutePath;

    /**
     * The directory path to the picture, i.e,
     * it is the absolute path without the full file name.
     */
    private String directoryPath;

    /**
     * The file name, with the tag names in them
     */
    private String fullFileName;

    /**
     * The file name, without the tag names.
     */
    private String taglessName;

    /**
     * Creates an instance of Picture
     * @param absolutePath The absolute path to the Picture
     */
    public Picture(String absolutePath){
        setObjectProperties(absolutePath);
    }

    /**
     * Get the absolute path to the picture
     * @return The absolute path to the picture
     */
    public String getAbsolutePath(){
        return this.absolutePath;
    }

    /**
     * Get the directory path to the picture
     * @return The directory path to the picture
     */
    public String getDirectoryPath(){
        return this.directoryPath;
    }

    /**
     * Get the full file name of this picture, including its tags.
     * @return The full file name of this picture.
     */
    public String getFullFileName(){
        return this.fullFileName;
    }

    /**
     * Get the name of this picture without the tags
     * @return The name of this picture without the tags
     */
    public String getTaglessName(){
        return this.taglessName;
    }

    /**
     * Set the absolute path, directory path, tagless name, and full file name
     * of this object
     * @param absolutePath The absolute path to this picture
     */
    private void setObjectProperties(String absolutePath){
        this.absolutePath = absolutePath;
        File file = new File(absolutePath);

        this.directoryPath = file.getParent();
        this.fullFileName = file.getName();

        String[] nameParts = fullFileName.split("@");
        this.taglessName = "";
        if (nameParts.length >= 1)
            this.taglessName = nameParts[0];
    }

    /**
     * Set the absolute path of this picture
     * It will notify all the observers that it has changed.
     * It will send a copy of its old Picture instance to the observers.
     * @param absolutePath The new absolute path to this picture
     */
    public void setAbsolutePath(String absolutePath){
        String oldAbsolutePath = this.absolutePath;
        setObjectProperties(absolutePath);

        super.setChanged();
        super.notifyObservers(new Picture(oldAbsolutePath));
    }

    /**
     * Set the directory path of this picture
     * It will notify all the observers that it has changed.
     * It will send a copy of its old Picture instance to the observers
     * @param directoryPath The new directory path to this picture.
     */
    public void setDirectoryPath(String directoryPath){
        String oldAbsolutePath = this.absolutePath;
        this.directoryPath = directoryPath;
        this.absolutePath = this.directoryPath + "//" + this.fullFileName;

        super.setChanged();
        super.notifyObservers(new Picture(oldAbsolutePath));
    }

    /**
     * Set the tagless name of this picture.
     * It will notify all the observers that it has changed.
     * It will send a copy of its old Picture instance to the observers
     * @param taglessName The new tagless name of this picture.
     */
    public void setTaglessName(String taglessName){
        String oldAbsolutePath = this.absolutePath;
        StringBuilder tags = new StringBuilder();
        String[] nameParts = fullFileName.split("@");
        for (int i = 1; i < nameParts.length; i++)
            tags.append(" @").append(nameParts[i]);

        this.fullFileName = taglessName + tags.toString();
        this.absolutePath = this.directoryPath + "//" + this.fullFileName;

        super.setChanged();
        super.notifyObservers(new Picture(oldAbsolutePath));
    }

    @Override
    public String toString(){
        return "Abs path: " + absolutePath;
    }
}
