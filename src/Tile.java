public class Tile {
    public static java.util.ArrayList<Tile> tileInstances = new java.util.ArrayList<>(); // Tile instances are stored here
    public static final int DEFAULT_TILE_SIZE = 16; // Tile size in pixels



    // ----------------------------------------------------------
    //
    //      WHEN CREATING NEW TILES, ADD THEM TO THIS METHOD
    //
    // ----------------------------------------------------------
    public static void generateTiles() {
        newTile("air", null, null, true, true, false);
        newTile("stone", GameGraphics.defaultTileAtlas, new Vector2.Int32(0, 0), false, false, true);
        newTile("dirt", GameGraphics.defaultTileAtlas, new Vector2.Int32(1, 0), false, false, true);
        newTile("grass_block", GameGraphics.defaultTileAtlas, new Vector2.Int32(2, 0), false, false, true);
        newTile("oak_wood", GameGraphics.defaultTileAtlas, new Vector2.Int32(3, 0), false, false, true);
        newTile("oak_leaves", GameGraphics.defaultTileAtlas, new Vector2.Int32(4, 0), true, false, true);
        newTile("thick_oak_leaves", GameGraphics.defaultTileAtlas, new Vector2.Int32(5, 0), false, false, true);
        newTile("bedrock", GameGraphics.defaultTileAtlas, new Vector2.Int32(6, 0), false, false, false);
        newTile("coal_ore", GameGraphics.defaultTileAtlas, new Vector2.Int32(7, 0), false, false, true);
        newTile("iron_ore", GameGraphics.defaultTileAtlas, new Vector2.Int32(8, 0), false, false, true);
        newTile("redstone_ore", GameGraphics.defaultTileAtlas, new Vector2.Int32(9, 0), false, false, true);
        newTile("gold_ore", GameGraphics.defaultTileAtlas, new Vector2.Int32(10, 0), false, false, true);
        newTile("emerald_ore", GameGraphics.defaultTileAtlas, new Vector2.Int32(11, 0), false, false, true);
        newTile("lapis_ore", GameGraphics.defaultTileAtlas, new Vector2.Int32(12, 0), false, false, true);
        newTile("diamond_ore", GameGraphics.defaultTileAtlas, new Vector2.Int32(13, 0), false, false, true);
        newTile("glass", GameGraphics.defaultTileAtlas, new Vector2.Int32(0, 1), true, false, true);
        newTile("bricks", GameGraphics.defaultTileAtlas, new Vector2.Int32(1, 1), false, false, true);
        newTile("cobblestone", GameGraphics.defaultTileAtlas, new Vector2.Int32(2, 1), false, false, true);
        newTile("oak_planks", GameGraphics.defaultTileAtlas, new Vector2.Int32(3, 1), false, false, true);
        newTile("grass", GameGraphics.defaultTileAtlas, new Vector2.Int32(4, 1), true, true, true);
        newTile("sand", GameGraphics.defaultTileAtlas, new Vector2.Int32(5, 1), false, false, true);
        newTile("gravel", GameGraphics.defaultTileAtlas, new Vector2.Int32(6, 1), false, false, true);
        newTile("water", GameGraphics.defaultTileAtlas, new Vector2.Int32(7, 1), true, true, false);
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

    // Returns the Tile instance that has the matching name
    public static Tile getTileByName(String name_) {
        for(Tile tile : tileInstances) {
            if(tile.name.equals(name_)) {
                return tile;
            }
        }
        return null;
    }

    // Returns the ID of the tile that matches the name
    public static char getTileID(String name) {
        for(Tile tile : tileInstances) {
            if(tile.name.equals(name)) {
                return tile.allocID;
            }
        }
        return 0;
    }

    public static void newTile(String name, GameGraphics.TextureAtlas atlas, Vector2.Int32 atlasImageLink, boolean isTransparent, boolean soft, boolean breakable) {
        Tile tile = new Tile(name, atlas, atlasImageLink, isTransparent, soft, breakable);
        tileInstances.add(tile);
    }

    // Save data
    public char[] saveID; // The ID by which the tile will be saved in world files (2 chars)
    public char allocID; // Worlds are saved using text characters, however, we can use a single 8-bit number to display loaded tiles to save memory

    // Properties
    public String name; // Tile name to be displayed in-game
    public GameGraphics.TextureAtlas atlas; // The atlas to be used for the texture
    public Vector2.Int32 atlasImageLink; // The position of the appropriate texture for the tile
    public boolean isTransparent;
    public boolean soft;
    public boolean breakable;

    public Tile(String name, GameGraphics.TextureAtlas atlas, Vector2.Int32 atlasImageLink, boolean isTransparent, boolean soft, boolean breakable) {
        this.name = name;
        this.atlas = atlas;
        this.soft = soft;
        this.atlasImageLink = atlasImageLink;
        this.isTransparent = isTransparent;
        this.breakable = breakable;
        this.allocID = (char)(tileInstances.size());
        this.saveID = new char[]{(char)(allocID+33), (allocID+33) < 255 ? '-' : (char)(allocID - 221)};
    }
}
