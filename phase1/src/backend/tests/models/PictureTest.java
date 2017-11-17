package backend.tests.models;

import backend.models.Picture;
import backend.models.Tag;
import java.util.List;
import org.junit.jupiter.api.Test;

class PictureTest {

  @Test
  void getAbsolutePath() {
    Picture picture = new Picture("C:\\Users\\chick @Chicken.jpg");
    System.out.println(picture.getAbsolutePath());
    assert(picture.getAbsolutePath().equals("C:\\Users\\chick @Chicken.jpg"));
  }

  @Test
  void getDirectoryPath() {
    Picture picture = new Picture("C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken - Copy.jpg");
    assert(picture.getDirectoryPath().equals("C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile"));
  }

  @Test
  void getFullFileName() {
    Picture picture = new Picture("C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken - Copy.jpg");
    assert(picture.getFullFileName().equals("chick @Chicken - Copy.jpg"));
  }

  @Test
  void getTaglessName() {
    Picture picture = new Picture("C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken - Copy.jpg");
    assert(picture.getTaglessName().equals("chick"));
  }

  @Test
  void setDirectoryPath() {
    Picture picture = new Picture("C:\\Grandma\\baby chicken @Chicken.jpg");
    picture.setDirectoryPath("C:\\Grandma");
    assert(picture.getTaglessName().equals("baby chicken"));
    assert(picture.getFullFileName().equals("baby chicken @Chicken.jpg"));
    assert(picture.getDirectoryPath().equals("C:\\Grandma"));
    assert(picture.getTags().size() == 1);
  }

  @Test
  void setTaglessName() {
    Picture picture = new Picture("C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg");
    picture.setTaglessName("baby chicken");
    assert(picture.getTaglessName().equals("baby chicken"));
    assert(picture.getFullFileName().equals("baby chicken @Chicken.jpg"));
    assert(picture.getDirectoryPath().equals("C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile"));
    assert(picture.getTags().size() == 1);
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
    Picture picture = new Picture("C:\\Grandma\\chicks @Chicken @Cute @Yellow.jpg");
    picture.addTag(new Tag("Brown"));
    assert(picture.getTags().size() == 4);
    assert(picture.getTags().get(0).getLabel().equals("Chicken"));
    assert(picture.getTags().get(1).getLabel().equals("Cute"));
    assert(picture.getTags().get(2).getLabel().equals("Yellow"));
    assert(picture.getTags().get(3).getLabel().equals("Brown"));
    assert(picture.getAbsolutePath().equals("C:\\Grandma\\chicks @Chicken @Cute @Yellow @Brown.jpg"));
    assert(picture.getDirectoryPath().equals("C:\\Grandma"));
    assert(picture.getFullFileName().equals("chicks @Chicken @Cute @Yellow @Brown.jpg"));
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
    assert(picture.containsTag(new Tag("Cute")));
  }
}