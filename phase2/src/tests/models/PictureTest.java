
package tests.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import backend.models.Picture;
import backend.models.Tag;

class PictureTest {

  @Test
  void getAbsolutePath() {
    Picture picture = new Picture("C:\\Users\\chick @Chicken.jpg");
    assertEquals(picture.getAbsolutePath(), "C:\\Users\\chick @Chicken.jpg");
  }

  @Test
  void getDirectoryPath() {
    Picture picture = new Picture(
        "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken - Copy.jpg");
    assertEquals(picture.getDirectoryPath(),
        "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile");
  }

  @Test
  void getFullFileName() {
    Picture picture = new Picture(
        "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken - Copy.jpg");
    assertEquals(picture.getFullFileName(), "chick @Chicken - Copy.jpg");
  }

  @Test
  void getTaglessName() {
    Picture picture = new Picture(
        "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken - Copy.jpg");
    assertEquals(picture.getTaglessName(), "chick");
  }

  @Test
  void setDirectoryPath() {
    Picture picture = new Picture("C:\\Grandma\\baby chicken @Chicken.jpg");
    picture.setDirectoryPath("C:\\Grandma");
    assertEquals(picture.getTaglessName(), "baby chicken");
    assertEquals(picture.getFullFileName(), "baby chicken @Chicken.jpg");
    assertEquals(picture.getDirectoryPath(), "C:\\Grandma");
    assertEquals(picture.getTags().size(), 1);
  }

  @Test
  void setTaglessName() {
    Picture picture = new Picture(
        "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg");
    picture.setTaglessName("baby chicken");
    picture.setTaglessName("shimiISDUMB");
    ArrayList<String> hisNames = new ArrayList<String>();
    hisNames.add("chick");
    hisNames.add("baby chicken");
    hisNames.add("shimiISDUMB");
    assertEquals(picture.getTaglessName(), "shimiISDUMB");
    assertEquals(picture.getFullFileName(), "shimiISDUMB @Chicken.jpg");
    assertEquals(picture.getDirectoryPath(),
        "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile");
    assertEquals(picture.getTags().size(), 1);
    assertEquals(picture.getHistoricalTaglessNames(), hisNames);
  }

  @Test
  void getTags() {
    Picture picture = new Picture("C:\\chicks @Chicken @Cute @Yellow.jpg");
    assertEquals(picture.getTags().size(), 3);
    assertEquals(picture.getTags().get(0).getLabel(), "Chicken");
    assertEquals(picture.getTags().get(1).getLabel(), "Cute");
    assertEquals(picture.getTags().get(2).getLabel(), "Yellow");
  }

  @Test
  void addTag() {
    Picture picture = new Picture("C:\\Grandma\\chicks @Chicken @Cute @Yellow.jpg");
    picture.addTag(new Tag("Brown"));
    assertEquals(picture.getTags().size(), 4);
    assertEquals(picture.getTags().get(0).getLabel(), "Chicken");
    assertEquals(picture.getTags().get(1).getLabel(), "Cute");
    assertEquals(picture.getTags().get(2).getLabel(), "Yellow");
    assertEquals(picture.getTags().get(3).getLabel(), "Brown");
    assertEquals(picture.getAbsolutePath(),
        "C:\\Grandma\\chicks @Chicken @Cute @Yellow @Brown.jpg");
    assertEquals(picture.getDirectoryPath(), "C:\\Grandma");
    assertEquals(picture.getFullFileName(), "chicks @Chicken @Cute @Yellow @Brown.jpg");
  }

  @Test
  void deleteTag() {
    Picture picture = new Picture("C:\\chicks @Chicken @Cute @Yellow @Brown @Young.jpg");

    picture.deleteTag(picture.getTags().get(0));
    assertEquals(picture.getTags().size(), 4);
    assertEquals(picture.getTags().get(0).getLabel(), "Cute");
    assertEquals(picture.getTags().get(1).getLabel(), "Yellow");
    assertEquals(picture.getTags().get(2).getLabel(), "Brown");
    assertEquals(picture.getTags().get(3).getLabel(), "Young");

    picture.deleteTag(picture.getTags().get(3));
    assertEquals(picture.getTags().size(), 3);
    assertEquals(picture.getTags().get(0).getLabel(), "Cute");
    assertEquals(picture.getTags().get(1).getLabel(), "Yellow");
    assertEquals(picture.getTags().get(2).getLabel(), "Brown");

    picture.deleteTag(picture.getTags().get(1));
    assertEquals(picture.getTags().size(), 2);
    assertEquals(picture.getTags().get(0).getLabel(), "Cute");
    assertEquals(picture.getTags().get(1).getLabel(), "Brown");
  }

  @Test
  void containsTag() {
    Picture picture = new Picture("C:\\chicks @Chicken @Cute @Yellow @Brown @Young.jpg");
    assert (picture.containsTag(picture.getTags().get(0)));
    assert (picture.containsTag(picture.getTags().get(1)));
    assert (picture.containsTag(picture.getTags().get(2)));
    assert (picture.containsTag(picture.getTags().get(3)));
    assert (picture.containsTag(picture.getTags().get(4)));
    assert (picture.containsTag(new Tag("Cute")));
  }
}
