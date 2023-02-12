public class Tile {
    // Tile instances are stored here
    public static java.util.ArrayList<Tile> tileInstances = new java.util.ArrayList<>();

    // Returns the Tile instance that has the matching ID
    public static Tile getTile(int ID) {
        for(Tile tile : tileInstances) {
            if(tile.allocID == ID) {
                return tile;
            }
        }
        return null;
    }

    // Returns the ID of the tile that matches the name
    public static short getTileID(String name) {
        for(Tile tile : tileInstances) {
            if(tile.name.equals(name)) {
                return tile.allocID;
            }
        }
        return 0;
    }

    public static void newTile(String name, String path) {
        Tile tile = new Tile(name, path);
        tileInstances.add(tile);
    }

    public static void generateTiles() {
        newTile("air", null);
        newTile("stone", "img/stone.png");
        newTile("dirt", "img/dirt.png");
        newTile("grass_block", "img/grass_block.png");
    }

    // Save data
    char[] saveID; // The ID by which the tile will be saved in world files (2 chars)
    short allocID; // Worlds are saved using text characters, however, we can use a single 8-bit number to display loaded tiles to save memory

    // Properties
    String name; // Tile name to be displayed in-game
    String imagePath;

    public Tile(String name, String path) {
        this.name = name;
        this.imagePath = path;
        this.allocID = Integer.valueOf(tileInstances.size()).shortValue();
        this.saveID = new char[]{(char)(allocID+33), (allocID+33) < 255 ? '-' : (char)(allocID - 221)};
    }
}
