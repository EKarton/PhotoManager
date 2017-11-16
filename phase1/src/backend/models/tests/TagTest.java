package backend.models.tests;

import static org.junit.jupiter.api.Assertions.*;

import backend.models.Picture;
import backend.models.Tag;
import org.junit.jupiter.api.Test;

class TagTest {

  @Test
  void getLabel() {
    Tag tag = new Tag("wasd");
    assert(tag.getLabel().equals("wasd"));
  }

  @Test
  void setLabel() {
    Tag tag = new Tag("wasd");
    tag.setLabel("chicken");
    assert(tag.getLabel().equals("chicken"));
  }

  @Test
  void equals() {
    Tag tag1 = new Tag("wasd");
    Tag tag2 = new Tag("wasd");
    Object tag3 = new Picture("C:\\Users\\Chicken.png");
    Tag tag4 = new Tag("hehe");
    assert(tag1.equals(tag2));
    assert(tag1.equals(tag1));
    assert(tag1.equals(tag3) == false);
    assert(tag1.equals(tag4) == false);
  }

}