package backend.tests.models;

import static org.junit.jupiter.api.Assertions.*;

import backend.files.FileManager;
import backend.models.Picture;
import backend.models.PictureManager;
import backend.models.Tag;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;

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
    assert(manager.getPictures().get(0) == picture1);
    assert(manager.getPictures().get(1) == picture2);

    manager.addPicture(picture1);
    manager.addPicture(picture2);
    manager.addPicture(picture3);
    assert(manager.getPictures().get(0) == picture1);
    assert(manager.getPictures().get(1) == picture2);
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

    List<Picture> pictureList1 = manager.getPictureWithTag(new Tag("Grandma"));
    List<Picture> pictureList2 = manager.getPictureWithTag(picture1.getTags().get(0));

    assert(pictureList1.size() == 1);
    assert(pictureList2.size() == 1);
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

    assert(manager.getPictures().size() == 3);
    assert(picture1.getTags().size() == 0);
    assert(picture2.getTags().size() == 0);
    assert(picture3.getTags().size() == 1);

    manager.deleteTag(new Tag("Grandma"));
    assert(manager.getPictures().size() == 3);
    assert(picture1.getTags().size() == 0);
    assert(picture2.getTags().size() == 0);
    assert(picture3.getTags().size() == 1);
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

    assert(manager.getPictures().size() == 2);

    manager.addPicture(picture1);
    manager.addPicture(picture2);
    manager.addPicture(picture3);

    assert(manager.getPictures().size() == 2);
  }

  @Test
  void untrackPicture() {
    Picture picture1 = new Picture("C:\\Grandma\\wasd @Grandma.png");
    PictureManager manager = new PictureManager();
    manager.addPicture(picture1);
    assert(picture1.countObservers() == 1);
    manager.untrackPicture(picture1);
    assert(picture1.countObservers() == 0);
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
    assert(!manager.contains(picture3));
  }

  @Test
  void update() {
    FileManager fileManager = new FileManager();
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
    assert(file.exists()) == false;

    // When the picture's directory is changed
    picture.setDirectoryPath("C:\\Users\\Emilio K\\Desktop\\New folder\\New folder");
    File newFile2 = new File("C:\\Users\\Emilio K\\Desktop\\New folder\\New folder\\chicken @cute.jpg");
    assert(newFile2.exists());
    assert(newFile.exists() == false);
    assert(file.exists()) == false;
  }
}