package backend.models.tests;

import backend.models.Picture;
import backend.models.Tag;
import org.junit.jupiter.api.Test;

class PictureTest {

  private final String picPath = "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken - Copy.jpg";

  @Test
  void getAbsolutePath() {
    Picture picture = new Picture(picPath);
    assert(picture.getAbsolutePath().equals(picPath));
  }

  @Test
  void getDirectoryPath() {
    Picture picture = new Picture(picPath);
    assert(picture.getDirectoryPath().equals("C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile"));
  }

  @Test
  void getFullFileName() {
    Picture picture = new Picture(picPath);
    assert(picture.getFullFileName().equals("chick @Chicken - Copy.jpg"));
  }

  @Test
  void getTaglessName() {
    Picture picture = new Picture(picPath);
    assert(picture.getTaglessName().equals("chick"));
  }

  @Test
  void setDirectoryPath() {
    Picture picture = new Picture(picPath);
    picture.setDirectoryPath("C:\\Grandma");
    assert(picture.getTaglessName().equals("baby chicken"));
    assert(picture.getFullFileName().equals("baby chicken @wasd @grandma.jpg"));
    assert(picture.getDirectoryPath().equals("C:\\Grandma"));
    assert(picture.getTags().size() == 2);
  }

  @Test
  void setTaglessName() {
    Picture picture = new Picture(picPath);
    picture.setTaglessName("chick");
    assert(picture.getTaglessName().equals("baby chicken"));
    assert(picture.getFullFileName().equals("baby chicken @wasd @grandma.jpg"));
    assert(picture.getDirectoryPath().equals("C:\\Grandma"));
    assert(picture.getTags().size() == 2);
  }

  @Test
  void getTags() {
    Picture picture = new Picture("C:\\chicks @Chicken @Cute @Yellow.jpg");
    assert(picture.getTags().size() == 3);
    assert(picture.getTags().get(0).getLabel().equals("Chicken"));
    assert(picture.getTags().get(1).getLabel().equals("Cute"));
    assert(picture.getTags().get(2).getLabel().equals("Yellow"));
  }

  @Test
  void addTag() {
    Picture picture = new Picture("C:\\chicks @Chicken @Cute @Yellow.jpg");
    picture.addTag(new Tag("Brown"));
    assert(picture.getTags().size() == 4);
    assert(picture.getTags().get(0).getLabel().equals("Chicken"));
    assert(picture.getTags().get(1).getLabel().equals("Cute"));
    assert(picture.getTags().get(2).getLabel().equals("Yellow"));
    assert(picture.getTags().get(3).getLabel().equals("Brown"));
  }

  @Test
  void deleteTag() {
    Picture picture = new Picture("C:\\chicks @Chicken @Cute @Yellow @Brown @Young.jpg");
    picture.deleteTag(picture.getTags().get(0));
    assert(picture.getTags().size() == 4);
    assert(picture.getTags().get(0).getLabel().equals("Cute"));
    assert(picture.getTags().get(1).getLabel().equals("Yellow"));
    assert(picture.getTags().get(2).getLabel().equals("Brown"));
    assert(picture.getTags().get(3).getLabel().equals("Young"));

    picture.deleteTag(picture.getTags().get(3));
    assert(picture.getTags().size() == 3);
    assert(picture.getTags().get(0).getLabel().equals("Cute"));
    assert(picture.getTags().get(1).getLabel().equals("Yellow"));
    assert(picture.getTags().get(2).getLabel().equals("Brown"));

    picture.deleteTag(picture.getTags().get(1));
    assert(picture.getTags().size() == 2);
    assert(picture.getTags().get(0).getLabel().equals("Cute"));
    assert(picture.getTags().get(1).getLabel().equals("Brown"));
  }

  @Test
  void containsTag() {
    Picture picture = new Picture("C:\\chicks @Chicken @Cute @Yellow @Brown @Young.jpg");
    assert(picture.containsTag(picture.getTags().get(0)));
    assert(picture.containsTag(picture.getTags().get(1)));
    assert(picture.containsTag(picture.getTags().get(2)));
    assert(picture.containsTag(picture.getTags().get(3)));
    assert(picture.containsTag(picture.getTags().get(4)));
    assert(picture.containsTag(new Tag("Cute")) == false);
  }
}