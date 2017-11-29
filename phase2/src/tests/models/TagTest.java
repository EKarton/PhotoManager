package tests.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import backend.models.Picture;
import backend.models.Tag;

class TagTest {


  @Test
  void getLabel() {
    Tag tag = new Tag("wasd");
    assertEquals(tag.getLabel(), ("wasd"));
  }

  @Test
  void setLabel() {
    Tag tag = new Tag("wasd");
    tag.setLabel("chicken");
    assertEquals(tag.getLabel(), ("chicken"));
  }

  @Test
  void equals() {
    Tag tag1 = new Tag("wasd");
    Tag tag2 = new Tag("wasd");
    Object tag3 = new Picture("C:\\Users\\Chicken.png");
    Tag tag4 = new Tag("hehe");
    assertEquals(tag1, (tag2));
    assertEquals(tag1, (tag1));
    assertNotEquals(tag1, tag3);
    assertNotEquals(tag1, (tag4));
  }

}
