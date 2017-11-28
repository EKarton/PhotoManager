package tests.models;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import backend.models.AppSettings;
import backend.models.Picture;
import backend.models.PictureManager;

class ConfigLoadingTest {

  @Test
  void test() throws IOException, ClassNotFoundException {
    AppSettings setting = new AppSettings();
    PictureManager manager = new PictureManager();
    Picture picture = new Picture(
        "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg");
    manager.addPicture(picture);
    picture.setTaglessName("baby chicken");
    picture.setTaglessName("shimiISDUMB");
    ArrayList<String> hisNames = new ArrayList<String>();
    hisNames.add("chick");
    hisNames.add("baby chicken");
    hisNames.add("shimiISDUMB");
    setting.addPicFromManager(manager);
    setting.save();
    AppSettings newSetting = AppSettings.loadFromFile();
    assert (setting.getHistoricalPicture().size() == 1);
    assert (newSetting.getHistoricalPicture().get(0).getHistoricalTaglessNames()
        .equals(picture.getHistoricalTaglessNames()));
    assert (picture.getTaglessName().equals("shimiISDUMB"));
    assert (picture.getFullFileName().equals("shimiISDUMB @Chicken.jpg"));
    assert (picture.getDirectoryPath()
        .equals("C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile"));
    assert (picture.getTags().size() == 1);

    assert (picture.getHistoricalTaglessNames().equals(hisNames));

  }

}
