package backend;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A class responsible for performing mapping interactions in the IO level
 */
public class PictureManager implements Observer {
    public MapRepository getPictureMappingsUnderDirectory(String directoryPath){
        return null;
    }

    public List<Tag> getHistoricalTagsOfPicture(Picture picture){
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
