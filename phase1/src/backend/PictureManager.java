package backend;

import backend.files.FileManager;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A class responsible for performing mapping interactions in the IO level
 */
public class PictureManager implements Observer {

    // Used to store the mappings of the pictures to the tags
    private MapRepository<Picture, Tag> mapRepository;

    /**
     * Creates a new instance of a PictureManager with an
     * initial Picture/Tag mappings in a MapRepository
     * @param repository The repository containing the Picture-tag mappings
     */
    public PictureManager(MapRepository<Picture, Tag> repository){
        mapRepository = repository;
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

        mapRepository = new FastMapRepository<Picture, Tag>();

        // Parse the pictures by getting its names and tags
        for (File picture : pictures){
            String[] tokenizeFileName = picture.getName().split("@");
            Picture newPic = new Picture(picture.getAbsolutePath());
            mapRepository.addItem(newPic);

            // Track the item
            newPic.addObserver(this);

            for (int i = 1; i < tokenizeFileName.length; i++){
                String tagName = tokenizeFileName[i];
                Tag newTag = new Tag(tagName);
                mapRepository.addTagToItem(newPic, newTag);

                // Track the tag
                newTag.addObserver(this);
            }
        }
    }

    /**
     * Returns the map repository used in this PictureManager
     * @return The map repository used in this PictureManager
     */
    public MapRepository<Picture, Tag> getMapRepository() {
        return mapRepository;
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

            // If the directory path changed
            if (!oldPicture.getAbsolutePath().equals(picture.getAbsolutePath()))
                manager.moveFile(oldPicture.getAbsolutePath(), picture.getAbsolutePath());
        }

        // If there was a change with the tag
        else if (o instanceof Tag && arg instanceof Tag){
            Tag newTag = (Tag) o;
            Tag oldTag = (Tag) arg;

            // If the tag name has been changed
            if (!newTag.getLabel().equals(oldTag.getLabel())){
                List<Picture> pictures = mapRepository.getItemsFromTag(newTag);

                // Rename each pic that has that tag
                for (Picture pic : pictures){
                    List<Tag> tags = mapRepository.getTagsFromItem(pic);

                    manager.renameFile(pic.getAbsolutePath(), generateAbsolutePath(pic, tags));
                }
            }
        }
    }

    /**
     * Generate a file name based on its picture
     * @param picture The picture
     * @param tags The tags with that picture
     * @return A new file name for that picture (does not include its file path)
     */
    private String generateAbsolutePath(Picture picture, List<Tag> tags){
        StringBuilder newFileNameBuilder = new StringBuilder(picture.getDirectoryPath());
        newFileNameBuilder.append(picture.getTaglessName());

        for (Tag tag : tags)
            newFileNameBuilder.append(" @").append(tag.getLabel());

        return newFileNameBuilder.toString();
    }
}