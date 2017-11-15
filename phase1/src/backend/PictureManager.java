package backend;

import backend.files.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PictureManager implements Observer {

    private ArrayList<Picture> pictures = new ArrayList<Picture>();

    public PictureManager(String directoryPath) throws IOException {
        FileManager manager = new FileManager();
        List<File> files = manager.getFileList(directoryPath);

        for (File file : files) {
            Picture picture = new Picture(file.getAbsolutePath());
            pictures.add(picture);
            picture.addObserver(this);
        }
    }

    public PictureManager(){

    }

    public ArrayList<Picture> getPictures(){
        return (ArrayList<Picture>) pictures.clone();
    }

    public void deleteTag(Tag tag){
        for (Picture picture : pictures)
            picture.removeTags(tag);
    }

    public void addPicture(Picture picture){
        pictures.add(picture);
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        FileManager manager = new FileManager();

        if (o instanceof Picture){
            Picture newPicture = (Picture) o;
            Picture oldPicture = (Picture) arg;

            if (!newPicture.getDirectoryPath().equals(oldPicture.getDirectoryPath())){
                manager.moveFile(oldPicture.getAbsolutePath(), newPicture.getFullFileName());
            }

            if (!newPicture.getFullFileName().equals(oldPicture.getFullFileName())){
                manager.renameFile(newPicture.getAbsolutePath(), newPicture.getFullFileName());
            }
        }
    }
}
