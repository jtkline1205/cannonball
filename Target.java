import java.awt.*;

/**
 * Represents a target in the game. A target has dimensions,
 * a color, and a position on the screen. Targets disappear from play
 * when hit with a cannonball.
 */
public class Target {
   private int height;
   private int width;
   private Color color;
   private int xPosition;
   private int yPosition;
   private boolean hasBeenHit;
   
   public Target(int height, int width, Color color, int xPos, int yPos) {
      this.height = height;
      this.width = width;
      this.color = color;
      this.xPosition = xPos;
      this.yPosition = yPos;
      this.hasBeenHit = false;
   }
   
   public int getHeight() {
      return this.height;
   }
   
   public int getWidth() {
      return this.width;
   }
   
   public Color getColor() {
      return this.color;
   }
   
   public int getXPosition() {
      return this.xPosition;
   }
   
   public int getYPosition() {
      return this.yPosition;
   }
   
   public void wasHit() {
      this.hasBeenHit = true;
   }
   
}
      
