public class Tile {
    public static java.util.ArrayList<Tile> tileInstances = new java.util.ArrayList<>(); // Tile instances are stored here
    public static final int DEFAULT_TILE_SIZE = 10; // Tile size in pixels



    // ----------------------------------------------------------
    //
    //      WHEN CREATING NEW TILES, ADD THEM TO THIS METHOD
    //
    // ----------------------------------------------------------
    public static void generateTiles() {
        newTile("air", null, null, true);
        newTile("stone", GameGraphics.defaultTileAtlas, new IntVector2(0, 0), false);
        newTile("dirt", GameGraphics.defaultTileAtlas, new IntVector2(1, 0), false);
        newTile("grass_block", GameGraphics.defaultTileAtlas, new IntVector2(2, 0), false);
        newTile("oak_wood", GameGraphics.defaultTileAtlas, new IntVector2(3, 0), false);
        newTile("oak_leaves", GameGraphics.defaultTileAtlas, new IntVector2(4, 0), false);
        newTile("thick_oak_leaves", GameGraphics.defaultTileAtlas, new IntVector2(5, 0), false);
        newTile("bedrock", GameGraphics.defaultTileAtlas, new IntVector2(6, 0), false);
    }



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

    public static void newTile(String name, GameGraphics.TextureAtlas atlas, IntVector2 atlasImageLink, boolean isTransparent) {
        Tile tile = new Tile(name, atlas, atlasImageLink, isTransparent);
        tileInstances.add(tile);
    }

    // Save data
    public char[] saveID; // The ID by which the tile will be saved in world files (2 chars)
    public short allocID; // Worlds are saved using text characters, however, we can use a single 8-bit number to display loaded tiles to save memory

    // Properties
    public String name; // Tile name to be displayed in-game
    public GameGraphics.TextureAtlas atlas; // The atlas to be used for the texture
    public IntVector2 atlasImageLink; // The position of the appropriate texture for the tile
    public boolean isTransparent;

    public Tile(String name, GameGraphics.TextureAtlas atlas, IntVector2 atlasImageLink, boolean isTransparent) {
        this.name = name;
        this.atlas = atlas;
        this.atlasImageLink = atlasImageLink;
        this.isTransparent = isTransparent;
        this.allocID = Integer.valueOf(tileInstances.size()).shortValue();
        this.saveID = new char[]{(char)(allocID+33), (allocID+33) < 255 ? '-' : (char)(allocID - 221)};
    }
}
