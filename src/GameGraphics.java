import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class GameGraphics {
    // Default atlas for tiles
    public static TextureAtlas defaultTileAtlas = new TextureAtlas(16, 16, "img/block_atlas_16x16.png");

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
                imageColorData[y][x] = new Color(image.getRGB(x, y), true);
            }
        }
        return imageColorData;
    }

    // Draws a Color[][] instance to the screen
    public static void renderImage(int x, int y, Color[][] image, int scale, Graphics g, int a) {
        for(int i = 0; i < image.length; i++) {
            for(int j = 0; j < image[0].length; j++) {
                if(image[i][j].getAlpha() >= 255) {
                    g.setColor(new Color(image[i][j].getRed(), image[i][j].getGreen(), image[i][j].getBlue(), a));
                } else {
                    g.setColor(image[i][j]);
                }
                g.fillRect(x+j*scale, y+i*scale, scale, scale);
            }
        }
    }

    public static void renderTile(int x, int y, Tile tile, Graphics g, int a) {
        renderImage(x, y, defaultTileAtlas.getTexture(tile.atlasImageLink.x, tile.atlasImageLink.y), GameSettings.tileRenderScale, g, a);
    }

    public static short tilesRendered = 0; // The number of tiles rendered in the last frame

    // Renders the entire terrain
    public static void renderTerrain(Graphics g, int xOffset, int yOffset) {
        tilesRendered = 0;
        for(int i = 0; i < Terrain.overworld.length; i++) {
            for(int j = 0; j < Terrain.overworld[0].length; j++) {
                // Render block if an image is assigned to it and it is within reasonable range AND it is not off the screen
                if(Math.sqrt(Math.pow((j * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + xOffset) - Panel.SCREEN_WIDTH/2, 2) + Math.pow((i * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + yOffset) - Panel.SCREEN_HEIGHT/2, 2)) < GameSettings.tileRenderDistance && (j * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + xOffset) > -Tile.DEFAULT_TILE_SIZE*GameSettings.tileRenderScale && (j * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + xOffset) < Panel.SCREEN_WIDTH + Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale && (i * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + yOffset) > -Tile.DEFAULT_TILE_SIZE*GameSettings.tileRenderScale && (i * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + yOffset) < Panel.SCREEN_WIDTH + Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale) {
                    if(Tile.getTile(Terrain.overworld[i][j]) != null && Tile.getTile(Terrain.overworld[i][j]).atlasImageLink != null) {
                        // The tile will be rendered if there is a transparent tile next to it or xray is on
                        //if(GameSettings.xRayModeOn) {
                        GameGraphics.renderTile(j * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + xOffset, i * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + yOffset, Tile.getTile(Terrain.overworld[i][j]), g, 255);
/*                         } else if((i < Terrain.overworld.length-1 && i > 0 && j < Terrain.overworld[0].length-1 && j > 0)) {
                            try {
                                if (Tile.getTile(Terrain.overworld[i+1][j]).isTransparent
                                    || Tile.getTile(Terrain.overworld[i-1][j]).isTransparent
                                    || Tile.getTile(Terrain.overworld[i][j+1]).isTransparent
                                    || Tile.getTile(Terrain.overworld[i][j-1]).isTransparent
                                ) {
                                    // Draws a tile
                                    GameGraphics.renderTile(j * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + xOffset, i * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + yOffset, Tile.getTile(Terrain.overworld[i][j]), g, 255);
                                } else {
                                    // Draws a black tile
                                    g.setColor(Color.BLACK);
                                    g.fillRect(j * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + xOffset, i * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + yOffset, Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale, Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale);
                                }
                            } catch(IndexOutOfBoundsException e){} // Ignore IndexOutOfBoundsException
                            } else {
                            // Draws a tile
                            GameGraphics.renderTile(j * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + xOffset, i * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + yOffset, Tile.getTile(Terrain.overworld[i][j]), g, 255);
                        } */
                        tilesRendered++;
                    }

                    // Render shadow
                    if(!GameSettings.xRayModeOn && j < Lighting.lightLevelMap[0].length) {
                        g.setColor(new Color(0, 0, 0, 255-Lighting.lightLevelMap[i][j] < 0 ? 0 : 255-Lighting.lightLevelMap[i][j]));
                        g.fillRect((j * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + xOffset), i * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + yOffset, Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale, Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale);
                    }

                    // Block selection with mouse
                    // Blocks can only be selected if they are next to an air block
                    try {
                        if (Tile.getTile(Terrain.overworld[i+1][j]).soft
                        || Tile.getTile(Terrain.overworld[i-1][j]).soft
                        || Tile.getTile(Terrain.overworld[i][j+1]).soft
                        || Tile.getTile(Terrain.overworld[i][j-1]).soft
                        ) {
                            if(MouseInfo.getPointerInfo().getLocation().getX() < j * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + xOffset + Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale
                            && MouseInfo.getPointerInfo().getLocation().getX() > j * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + xOffset
                            && MouseInfo.getPointerInfo().getLocation().getY() > i * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + yOffset
                            && MouseInfo.getPointerInfo().getLocation().getY() < i * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale + yOffset + Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale) {
                                Terrain.selectedBlockX = j;
                                Terrain.selectedBlockY = i;
                            }
                        }
                    } catch(Exception e) {}
                }
            }
        }
    }
}
