import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class GameGraphics {
    // Default atlas for tiles
    public static TextureAtlas defaultTileAtlas = new TextureAtlas(10, 10, "img/block_atlas.png");

    // An atlas is basically a bunch of texture put into one image
    // With this class you can generate new atlases and get textures from them
    public static class TextureAtlas {
        public Color[][] atlas; // The image assigned to the atlas

        // The atlas generates textures from a single image, these values represent the width and height of each sub-image (in pixels)
        public final int CELL_WIDTH;
        public final int CELL_HEIGHT;

        // Constructor
        public TextureAtlas(int cellWidth, int cellHeight, String atlasPath) {
            this.CELL_WIDTH = cellWidth;
            this.CELL_HEIGHT = cellHeight;
            this.atlas = new GameGraphics().imageTo2D(atlasPath);
        }

        // Get a texture from the atlas at the position x y
        public Color[][] getTexture(int x, int y) {
            Color[][] texture = new Color[CELL_HEIGHT][CELL_WIDTH];
            try {
                for(int i = 0; i < CELL_HEIGHT; i++) {
                    for(int j = 0; j < CELL_WIDTH; j++) {
                        texture[i][j] = atlas[y * CELL_HEIGHT + i][x * CELL_WIDTH + j];
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return texture;
        }
    }

    /**
     * Returns a Color[][] instance which represents an image
     * @param path
     * @return
     */
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

    // Draws a Color[][] instance to the screen
    public static void renderImage(int x, int y, Color[][] image, int scale, Graphics g) {
        for(int i = 0; i < image.length; i++) {
            for(int j = 0; j < image[0].length; j++) {
                g.setColor(image[i][j]);
                g.fillRect(x+j*scale, y+i*scale, scale, scale);
            }
        }
    }

    public static void renderTile(int x, int y, Tile tile, Graphics g) {
        renderImage(x, y, defaultTileAtlas.getTexture(tile.atlasImageLink.x, tile.atlasImageLink.y), GameSettings.tileRenderScale, g);
    }

    // Renders the entire terrain
    public static void renderTerrain(Graphics g, int xOffset, int yOffset) {
        for(int i = 0; i < Terrain.overworld.length; i++) {
            for(int j = 0; j < Terrain.overworld[0].length; j++) {
                if(Tile.getTile(Terrain.overworld[i][j]).atlasImageLink != null) {
                    GameGraphics.renderTile(j * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + xOffset, i * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + yOffset, Tile.getTile(Terrain.overworld[i][j]), g);
                }
            }
        }
    }
}
