package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import backend.files.FileManager;
import backend.models.Picture;
import backend.models.PictureManager;
import backend.models.Tag;

class PictureManagerTest {

  @Test
  void getPictures() {
    Picture picture1 = new Picture("C:\\Grandma\\wasd.png");
    Picture picture2 = new Picture("C:\\Grandma\\chick.png");
    Picture picture3 = new Picture("C:\\Grandma\\wasd.png");
    PictureManager manager = new PictureManager();
    manager.addPicture(picture1);
    manager.addPicture(picture2);
    manager.addPicture(picture3);
    assertEquals(manager.getPictures().get(0) , picture1);
    assertEquals(manager.getPictures().get(1) , picture2);

    manager.addPicture(picture1);
    manager.addPicture(picture2);
    manager.addPicture(picture3);
    assertEquals(manager.getPictures().get(0) , picture1);
    assertEquals(manager.getPictures().get(1) , picture2);
  }

  @Test
  void getPictureWithTag() {
    Picture picture1 = new Picture("C:\\Grandma\\wasd @Grandma.png");
    Picture picture2 = new Picture("C:\\Grandma\\wasd @Grandma.png");
    Picture picture3 = new Picture("C:\\Grandma\\wasd @Shimi.png");
    PictureManager manager = new PictureManager();
    manager.addPicture(picture1);
    manager.addPicture(picture2);
    manager.addPicture(picture3);

    List<Picture> pictureList1 = manager.getPicturesWithTag(new Tag("Grandma"));
    List<Picture> pictureList2 = manager.getPicturesWithTag(picture1.getTags().get(0));

    assertEquals(pictureList1.size() , 1);
    assertEquals(pictureList2.size() , 1);
  }

  @Test
  void deleteTag() {
    Picture picture1 = new Picture("C:\\Grandma\\wasd @Grandma.png");
    Picture picture2 = new Picture("C:\\Grandma\\chick @Grandma.png");
    Picture picture3 = new Picture("C:\\Grandma\\wasd @Shimi.png");
    PictureManager manager = new PictureManager();
    manager.addPicture(picture1);
    manager.addPicture(picture2);
    manager.addPicture(picture3);

    manager.deleteTag(picture1.getTags().get(0));

    assertEquals(manager.getPictures().size() , 3);
    assertEquals(picture1.getTags().size() , 0);
    assertEquals(picture2.getTags().size() , 0);
    assertEquals(picture3.getTags().size() , 1);

    manager.deleteTag(new Tag("Grandma"));
    assertEquals(manager.getPictures().size() , 3);
    assertEquals(picture1.getTags().size() , 0);
    assertEquals(picture2.getTags().size() , 0);
    assertEquals(picture3.getTags().size() , 1);
  }

  @Test
  void addPicture() {
    Picture picture1 = new Picture("C:\\Grandma\\wasd @Grandma.png");
    Picture picture2 = new Picture("C:\\Grandma\\wasd @Grandma.png");
    Picture picture3 = new Picture("C:\\Grandma\\wasd @Shimi.png");
    PictureManager manager = new PictureManager();
    manager.addPicture(picture1);
    manager.addPicture(picture2);
    manager.addPicture(picture3);

    assertEquals(manager.getPictures().size() , 2);

    manager.addPicture(picture1);
    manager.addPicture(picture2);
    manager.addPicture(picture3);

    assertEquals(manager.getPictures().size() , 2);
  }

  @Test
  void untrackPicture() {
    Picture picture1 = new Picture("C:\\Grandma\\wasd @Grandma.png");
    PictureManager manager = new PictureManager();
    manager.addPicture(picture1);
    assertEquals(picture1.countObservers(),1);
    manager.untrackPicture(picture1);
    assertEquals(picture1.countObservers(), 0);
  }

  @Test
  void contains() {
    Picture picture1 = new Picture("C:\\Grandma\\wasd @Grandma.png");
    Picture picture2 = new Picture("C:\\Grandma\\wasd @Grandma.png");
    Picture picture3 = new Picture("C:\\Grandma\\chick @Grandma.png");
    PictureManager manager = new PictureManager();
    manager.addPicture(picture1);
    assert(manager.contains(picture1));
    assert(manager.contains(picture2));
    assertFalse(manager.contains(picture3));
  }

  @Test
  void update() {
    Picture picture = new Picture("C:\\Users\\Emilio K\\Desktop\\New folder\\chick.jpg");
    PictureManager manager = new PictureManager();
    manager.addPicture(picture);


    // When the picture's name is changed
    picture.setTaglessName("chicken");
    File file = new File("C:\\Users\\Emilio K\\Desktop\\New folder\\chicken.jpg");
    assert(file.exists());

    // When the picture's list of tags are changed
    Tag newTag = new Tag("cute");
    picture.addTag(newTag);
    File newFile = new File("C:\\Users\\Emilio K\\Desktop\\New folder\\chicken @cute.jpg");
    assert(newFile.exists());
    assertFalse(file.exists());

    // When the picture's directory is changed
    picture.setDirectoryPath("C:\\Users\\Emilio K\\Desktop\\New folder\\New folder");
    File newFile2 =
        new File("C:\\Users\\Emilio K\\Desktop\\New folder\\New folder\\chicken @cute.jpg");
    assert(newFile2.exists());
    assertFalse(newFile.exists());
    assertFalse(file.exists());
  }

  @Test
  void moveFileOne() throws IOException {
    PictureManager manager = new PictureManager("C:\\Users\\Emilio K\\Desktop\\New folder", true);
    Picture pic = manager.getPictures().get(0);
    int curSize = manager.getPictures().size();

    pic.setDirectoryPath("C:\\Users\\Emilio K\\Desktop");
    int newSize = manager.getPictures().size();
    assertEquals(newSize , curSize - 1);
  }

  @Test
  void moveFileTwo() throws IOException {
    PictureManager manager = new PictureManager("C:\\Users\\Emilio K\\Desktop\\New folder", false);
    Picture pic = manager.getPictures().get(0);
    int curSize = manager.getPictures().size();

    pic.setDirectoryPath("C:\\Users\\Emilio K\\Desktop\\New folder\\New folder");
    int newSize = manager.getPictures().size();
    assertEquals(newSize , curSize - 1);
  }
}
