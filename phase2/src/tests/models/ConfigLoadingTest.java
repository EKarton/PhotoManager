
package tests.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import backend.models.AppSettings;
import backend.models.Picture;
import backend.models.PictureManager;
import backend.models.Tag;

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
    assertEquals(setting.getHistoricalPicture().size(), 1);
    assertEquals(newSetting.getHistoricalPicture().get(0).getHistoricalTaglessNames(),
        (picture.getHistoricalTaglessNames()));
    assertEquals(picture.getTaglessName(), ("shimiISDUMB"));
    assertEquals(picture.getFullFileName(), ("shimiISDUMB @Chicken.jpg"));
    assertEquals(picture.getDirectoryPath(),
        ("C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile"));
    assertEquals(picture.getTags().size(), 1);
    assertEquals(picture.getHistoricalTaglessNames(), (hisNames));

  }

}
