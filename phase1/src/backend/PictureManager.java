package backend;

import backend.files.FileManager;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * A class responsible for performing mapping interactions in the IO level
 */
public class PictureManager implements Observer {

    // Used to store the mappings of the pictures to the tags
    private BiDirectionalMap<Picture, Tag> map;

    /**
     * Creates a new instance of a PictureManager with an
     * initial Picture/Tag mappings in a BiDirectionalMap
     * @param repository The repository containing the Picture-tag mappings
     */
    public PictureManager(BiDirectionalMap<Picture, Tag> repository){
        map = repository;
    }

    /**
     * Creates a new instance of a PictureManager that keep track.
     * of the Picture-Tag mappings under a directory path.
     * @param directoryPath A directory path.
     * @throws IOException Thrown when the directory path does not exist.
     */
    public PictureManager(String directoryPath) throws IOException{
        FileManager manager = new FileManager();
        List<File> pictures = manager.getFileList(directoryPath);

        map = new BiDirectionalHashMap<Picture, Tag>();

        // Parse the pictures by getting its names and tags
        for (File picture : pictures){
            String[] tokenizeFileName = picture.getName().split("@");
            Picture newPic = new Picture(picture.getAbsolutePath());
            map.addKey(newPic);

            // Track the item
            newPic.addObserver(this);

            for (int i = 1; i < tokenizeFileName.length; i++){
                String tagName = tokenizeFileName[i];
                Tag newTag = new Tag(tagName);
                map.addValueToKey(newPic, newTag);

                // Track the tag
                newTag.addObserver(this);
            }
        }
    }

    /**
     * Returns the map repository used in this PictureManager
     * @return The map repository used in this PictureManager
     */
    public BiDirectionalMap<Picture, Tag> getMap() {
        return map;
    }

    public List<Tag> getHistoricalTagsOfPicture(Picture picture){
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {

        FileManager manager = new FileManager();

        // If there is a change with the picture
        if (o instanceof Picture && arg instanceof Picture){
            Picture picture = (Picture) o;
            Picture oldPicture = (Picture) arg;
            handlePictureChanges(picture, oldPicture);
        }

        // If there was a change with the tag
        else if (o instanceof Tag && arg instanceof Tag){
            Tag newTag = (Tag) o;
            Tag oldTag = (Tag) arg;
            handleTagChanges(newTag, oldTag);
        }

        // If there was a change with the mappings
        else if (o instanceof BiDirectionalMap){
            BiDirectionalMap<Picture, Tag> newMap = (BiDirectionalMap<Picture, Tag>) o;
            handleMapChanges(newMap, this.map);
            this.map = newMap.getClone();
        }
    }

    private void handleTagChanges(Tag newTag, Tag oldTag){
        FileManager manager = new FileManager();
        // If the tag name has been changed
        if (!newTag.getLabel().equals(oldTag.getLabel())){
            List<Picture> pictures = map.getKeysFromValue(newTag);

            // Rename each pic that has that tag
            for (Picture pic : pictures){
                List<Tag> tags = map.getValuesFromKey(pic);

                manager.renameFile(pic.getAbsolutePath(), generateAbsolutePath(pic, tags));
            }
        }
    }

    private void handlePictureChanges(Picture newPicture, Picture oldPicture){
        FileManager manager = new FileManager();
        // If the directory path changed
        if (!oldPicture.getAbsolutePath().equals(newPicture.getAbsolutePath()))
            manager.moveFile(oldPicture.getAbsolutePath(), newPicture.getAbsolutePath());
    }

    private void handleMapChanges(BiDirectionalMap<Picture, Tag> newMap, BiDirectionalMap<Picture, Tag> oldMap){
        FileManager manager = new FileManager();
        List<Picture> newPictures = newMap.getKeys();
        for (Picture picture : newPictures){
            List<Tag> tags = newMap.getValuesFromKey(picture);

            String properAbsolutePath = generateAbsolutePath(picture, tags);
            String properFileName = generateFullFileName(picture, tags);
            if (!properAbsolutePath.equals(picture.getAbsolutePath())){
                picture.setAbsolutePath(properAbsolutePath);
                manager.renameFile(properAbsolutePath, properFileName);
            }
        }
    }

    private String generateFullFileName(Picture picture, List<Tag> tags){
        StringBuilder builder = new StringBuilder(picture.getTaglessName());
        for (Tag tag : tags)
            builder.append(" @").append(tag.getLabel());

        return builder.toString();
    }

    /**
     * Generate a file name based on its picture
     * @param picture The picture
     * @param tags The tags with that picture
     * @return A new file name for that picture (does not include its file path)
     */
    private String generateAbsolutePath(Picture picture, List<Tag> tags){
        File newFile = new File(picture.getDirectoryPath(), generateFullFileName(picture, tags));
        return newFile.getAbsolutePath();
    }
}