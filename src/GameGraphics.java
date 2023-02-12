import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class GameGraphics {
    private HashMap<String, Color[][]> loadedImages = new HashMap<String, Color[][]>();

    public GameGraphics() {
        loadImage("img/stone.png");
    }

    private void loadImage(String path) {
        this.loadedImages.put(path, this.imageTo2D(path));
    }

    public Color[][] imageTo2D(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Color[][] imageColorData = new Color[image.getHeight()][image.getWidth()];
        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                imageColorData[y][x] = new Color(image.getRGB(x, y));
            }
        }
        return imageColorData;
    }

    public void renderImage(Graphics g, String imagePath, int scale, int xpos, int ypos) {
        if(xpos > 0 && xpos < Panel.SCREEN_WIDTH && ypos > 0 && ypos < Panel.SCREEN_HEIGHT) {
            Color[][] imageData = loadedImages.get(imagePath);
            for(int x = 0; x < imageData[0].length; x++) {
                for(int y = 0; y < imageData.length; y++) {
                    g.setColor(imageData[y][x]);
                    g.fillRect(xpos + x * scale, ypos + y * scale, scale, scale);
                }
            }
        }
    }
}
