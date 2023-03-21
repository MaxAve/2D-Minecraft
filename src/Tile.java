public class Tile {
    public static java.util.ArrayList<Tile> tileInstances = new java.util.ArrayList<>(); // Tile instances are stored here
    public static final int DEFAULT_TILE_SIZE = 16; // Tile size in pixels



    // ----------------------------------------------------------
    //
    //      WHEN CREATING NEW TILES, ADD THEM TO THIS METHOD
    //
    // ----------------------------------------------------------
    public static void generateTiles() {
        newTile("air", null, null, true, true);
        newTile("stone", GameGraphics.defaultTileAtlas, new IntVector2(0, 0), false, false);
        newTile("dirt", GameGraphics.defaultTileAtlas, new IntVector2(1, 0), false, false);
        newTile("grass_block", GameGraphics.defaultTileAtlas, new IntVector2(2, 0), false, false);
        newTile("oak_wood", GameGraphics.defaultTileAtlas, new IntVector2(3, 0), false, false);
        newTile("oak_leaves", GameGraphics.defaultTileAtlas, new IntVector2(4, 0), true, false);
        newTile("thick_oak_leaves", GameGraphics.defaultTileAtlas, new IntVector2(5, 0), false, false);
        newTile("bedrock", GameGraphics.defaultTileAtlas, new IntVector2(6, 0), false, false);
        newTile("coal_ore", GameGraphics.defaultTileAtlas, new IntVector2(7, 0), false, false);
        newTile("iron_ore", GameGraphics.defaultTileAtlas, new IntVector2(8, 0), false, false);
        newTile("redstone_ore", GameGraphics.defaultTileAtlas, new IntVector2(9, 0), false, false);
        newTile("gold_ore", GameGraphics.defaultTileAtlas, new IntVector2(10, 0), false, false);
        newTile("emerald_ore", GameGraphics.defaultTileAtlas, new IntVector2(11, 0), false, false);
        newTile("lapis_ore", GameGraphics.defaultTileAtlas, new IntVector2(12, 0), false, false);
        newTile("diamond_ore", GameGraphics.defaultTileAtlas, new IntVector2(13, 0), false, false);
        newTile("glass", GameGraphics.defaultTileAtlas, new IntVector2(0, 1), true, false);
        newTile("bricks", GameGraphics.defaultTileAtlas, new IntVector2(1, 1), false, false);
        newTile("cobblestone", GameGraphics.defaultTileAtlas, new IntVector2(2, 1), false, false);
        newTile("oak_planks", GameGraphics.defaultTileAtlas, new IntVector2(3, 1), false, false);
        newTile("grass", GameGraphics.defaultTileAtlas, new IntVector2(4, 1), true, true);
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

    public static Tile getSelectedBlock() {
        try {
            return getTile(Terrain.overworld[Terrain.selectedBlockY][Terrain.selectedBlockY]);
        } catch(ArrayIndexOutOfBoundsException e) {
            return new Tile("none", null, null, true, true);
        }
    }

    public static void newTile(String name, GameGraphics.TextureAtlas atlas, IntVector2 atlasImageLink, boolean isTransparent, boolean soft) {
        Tile tile = new Tile(name, atlas, atlasImageLink, isTransparent, soft);
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
    public boolean soft;

    public Tile(String name, GameGraphics.TextureAtlas atlas, IntVector2 atlasImageLink, boolean isTransparent, boolean soft) {
        this.name = name;
        this.atlas = atlas;
        this.soft = soft;
        this.atlasImageLink = atlasImageLink;
        this.isTransparent = isTransparent;
        this.allocID = Integer.valueOf(tileInstances.size()).shortValue();
        this.saveID = new char[]{(char)(allocID+33), (allocID+33) < 255 ? '-' : (char)(allocID - 221)};
    }
}
