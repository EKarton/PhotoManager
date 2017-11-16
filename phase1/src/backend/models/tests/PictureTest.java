package backend.models.tests;

import backend.models.Picture;
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
    assert(picture.getTaglessName().equals("chick.jpg"));
  }

  @Test
  void setAbsolutePath() {
    Picture picture = new Picture(picPath);
    picture.setAbsolutePath("C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\getFileList\\baby chicken @wasd.jpg");
    assert(picture.getTaglessName().equals("baby chicken @wasd.jpg"));
    assert(picture.getFullFileName().equals(""));
  }

  @Test
  void setDirectoryPath() {
  }

  @Test
  void setTaglessName() {
  }

  @Test
  void getTags() {
  }

  @Test
  void addTag() {
  }

  @Test
  void deleteTag() {
  }

  @Test
  void containsTag() {
  }

  @Test
  void update() {
  }

}