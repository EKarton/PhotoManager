package backend.models;

/**
 * A class used to represent a Rectangle, represented by the
 * top-left coordinate of the Rectangle, its width, and its height.
 */
public class Rectangle {

  /**
   * The x coordinate of the top-left corner of the rectangle
   */
  private int x;

  /**
   * The y coordinate of the top-left corner of the rectangle
   */
  private int y;

  /**
   * The width of the rectangle
   */
  private int width;

  /**
   * The height of the rectangle
   */
  private int height;

  /**
   * Constructs a rectangle
   * @param x The x coordinate of the top-left corner of the rectangle
   * @param y The y coordinate of the top-left corner of the rectangle
   * @param width The width of the rectangle
   * @param height The height of the rectangle
   */
  public Rectangle(int x, int y, int width, int height){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Get the x coordinate of the top-left corner of the rectangle.
   * @return The x coordinate of the top-left corner of the rectangle.
   */
  public int getX() {
    return x;
  }

  /**
   * Set the x coordinate of the top-left corner of the rectangle.
   * @param x The new x coordinate of the top-left corner of the rectangle.
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Get the y coordinate of the top-left corner of the rectangle.
   * @return The y coordinate of the top-left corner of the rectangle.
   */
  public int getY() {
    return y;
  }

  /**
   * Set the y coordinate of the top-left corner of the rectangle.
   * @param y The new y coordinate of the top-left corner of the rectangle.
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Get the width of this rectangle.
   * @return The width of this rectangle.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Set the width of this rectangle.
   * @param width The new width of this rectangle.
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Get the height of this rectangle.
   * @return The height of this rectangle.
   */
  public int getHeight(){
    return this.height;
  }

  /**
   * Set the height of this rectangle.
   * @param height The new height of this rectangle.
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Determines whether two rectangles are equal.
   * They are equal if "other" is a Rectangle, has the same x/y coordinates,
   * and has the same width and height.
   * @param other The other rectangle
   * @return True if they are equal; else false.
   */
  @Override
  public boolean equals(Object other){
    if (other instanceof Rectangle){
      Rectangle otherRect = (Rectangle) other;
      if (otherRect.width == this.width && otherRect.height == this.height){
        if (otherRect.x == this.x && otherRect.y == this.y)
          return true;
      }
    }
    return false;
  }
}
