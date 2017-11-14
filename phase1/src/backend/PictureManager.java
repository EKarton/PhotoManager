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
            String filePath = picture.getCanonicalPath();

            // Capture the file name and make a new item
            String fileName = "";
            if (tokenizeFileName.length > 0)
                fileName = tokenizeFileName[0];
            Picture newPic = new Picture(filePath, fileName);
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
            if (!oldPicture.getFilePath().equals(picture.getFilePath()))
                manager.moveFile(oldPicture.getFilePath(), picture.getFilePath());

            // If the name changed
            if (!oldPicture.getName().equals(picture.getName())){
                List<Tag> tags = mapRepository.getTagsFromItem(picture);
                manager.renameFile(picture.getFilePath(), generateFileName(picture, tags));
            }
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

                    manager.renameFile(pic.getFilePath(), generateFileName(pic, tags));
                }
            }
        }
        //update config files
    }

    /**
     * Generate a file name based on its picture
     * @param picture The picture
     * @param tags The tags with that picture
     * @return A new file name for that picture (does not include its file path)
     */
    private String generateFileName(Picture picture, List<Tag> tags){
        StringBuilder newFileNameBuilder = new StringBuilder(picture.getName());

        for (Tag tag : tags)
            newFileNameBuilder.append(" @").append(tag.getLabel());

        return newFileNameBuilder.toString();
    }
}