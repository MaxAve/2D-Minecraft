import java.util.Random;

public class Terrain {
    // Worlds are stored as 2D arrays of 8-bit integer values, which represent tile IDs
    public static short[][] overworld = new short[20][20];
    public static final int LOADED_TERRAIN_WIDTH = overworld[0].length;
    public static final int LOADED_TERRAIN_HEIGHT = overworld.length;
    private static Random random = new Random();
    
    public static void generateTree(int x, int y, int type, String leafBlock, String stemBlock) {
        if(type == 1) {
            for(int i = 0; i < 5; i++) {
                overworld[y-i][x] = Tile.getTileID(stemBlock);
            }
        }
    }

    public static void generateTerrain(int stepRange, int stepChance, int treeDistribution) {
        for(int i = 0; i < overworld.length; i++) {
            for(int j = 0; j < overworld[0].length; j++) {
                overworld[i][j] = (short)(random.nextInt(7)+1);
            }
        }
        /*int pillarHeight = 15;
        for(int x = 0; x < overworld[0].length; x++) {
            if(random.nextInt(101) <= stepChance) {
                int hightStep = random.nextInt(stepRange) - stepRange / 2;
                if(pillarHeight + hightStep >= 0 && pillarHeight + hightStep <= LOADED_TERRAIN_HEIGHT) {
                    pillarHeight += hightStep;
                }
            }
            for(int y = 0; y < pillarHeight; y++) {
                overworld[overworld.length - y - 1][x] = Tile.getTileID("stone");
            }
            for(int y = pillarHeight; y < pillarHeight+4; y++) {
                overworld[overworld.length - y - 1][x] = Tile.getTileID("dirt");
            }
            if(random.nextInt(101) <= treeDistribution && x % 2 == 0) { // Trees are not allowed to spawn right next to other trees
                generateTree(x, overworld.length - (pillarHeight+4) - 1, 1, "oak_leaves", "oak_wood");
                overworld[overworld.length - (pillarHeight+4) - 1][x] = Tile.getTileID("dirt");
            } else {
                overworld[overworld.length - (pillarHeight+4) - 1][x] = Tile.getTileID("grass_block");
            }
        }*/
    }
}
