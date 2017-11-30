package tests.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import backend.models.Picture;
import backend.models.PictureManager;
import backend.models.Tag;
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
    assertEquals(manager.getPictures().get(0), picture1);
    assertEquals(manager.getPictures().get(1), picture2);

    manager.addPicture(picture1);
    manager.addPicture(picture2);
    manager.addPicture(picture3);
    assertEquals(manager.getPictures().get(0), picture1);
    assertEquals(manager.getPictures().get(1), picture2);
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

    assertEquals(pictureList1.size(), 1);
    assertEquals(pictureList2.size(), 1);
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

    assertEquals(manager.getPictures().size(), 3);
    assertEquals(picture1.getTags().size(), 0);
    assertEquals(picture2.getTags().size(), 0);
    assertEquals(picture3.getTags().size(), 1);

    manager.deleteTag(new Tag("Grandma"));
    assertEquals(manager.getPictures().size(), 3);
    assertEquals(picture1.getTags().size(), 0);
    assertEquals(picture2.getTags().size(), 0);
    assertEquals(picture3.getTags().size(), 1);
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

    assertEquals(manager.getPictures().size(), 2);

    manager.addPicture(picture1);
    manager.addPicture(picture2);
    manager.addPicture(picture3);

    assertEquals(manager.getPictures().size(), 2);
  }

  @Test
  void untrackPicture() {
    Picture picture1 = new Picture("C:\\Grandma\\wasd @Grandma.png");
    PictureManager manager = new PictureManager();
    manager.addPicture(picture1);
    assertEquals(picture1.countObservers(), 1);
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
    assert (manager.contains(picture1));
    assert (manager.contains(picture2));
    assertFalse(manager.contains(picture3));
  }


}
