import java.util.Random;

public class Terrain {
    // Worlds are stored as 2D arrays of 8-bit integer values, which represent tile IDs
    public static short[][] overworld = new short[100][1000];
    private static Random random = new Random();
    
    public static void generateTerrain() {
        int pillarHeight = 15;
        for(int x = 0; x < overworld[0].length; x++) {
            pillarHeight += random.nextInt(3) - 1;
            for(int y = 0; y < pillarHeight; y++) {
                overworld[overworld.length - y - 1][x] = Tile.getTileID("stone");
            }
            for(int y = pillarHeight; y < pillarHeight+4; y++) {
                overworld[overworld.length - y - 1][x] = Tile.getTileID("dirt");
            }
            overworld[overworld.length - (pillarHeight+4) - 1][x] = Tile.getTileID("grass_block");
        }
    }
}
