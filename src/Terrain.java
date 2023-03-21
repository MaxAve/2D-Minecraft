import java.util.Random;

public class Terrain {
    // Worlds are stored as 2D arrays of 8-bit integer values, which represent tile IDs
    public static short[][] overworld = new short[200][100];
    public static final int LOADED_TERRAIN_WIDTH = overworld[0].length;
    public static final int LOADED_TERRAIN_HEIGHT = overworld.length;

    public static int selectedBlockX = -1;
    public static int selectedBlockY = -1;

    private static Random random = new Random(); // Random number generation



    /*
     * Contains all neccessary methods to generate trees
     */
    public class Tree {
        /*
         * Generates a rectangle of leaves
         * If a leaf tries to replace a stem block, it will replace the stem block with its "thick variant"
         * which should turn into a stem block when broken by a player
         */
        public static void addLeaves(int x, int y, int width, int height, String leafBlock, String thickLeafBlock, String stemBlock) {
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    if(overworld[y+i][x+j] == Tile.getTileID("air")) {
                        overworld[y + i][x + j] = Tile.getTileID(leafBlock);
                    } else if(overworld[y+i][x+j] == Tile.getTileID(stemBlock)) {
                        overworld[y + i][x + j] = Tile.getTileID(thickLeafBlock);
                    }
                }
            }
        }

        /*
        * Generates a tree stem
        * If the stem tries to replace a leaf block, it will instead replace it with a
        * thick leaf block which should turn into a stem block when broken by a player
        */
        public static void addStem(int x, int y, int width, int height, String leafBlock, String thickLeafBlock, String stemBlock) {
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    if(overworld[y-i][x+j] != Tile.getTileID(leafBlock)) {
                        overworld[y-i][x+j] = Tile.getTileID(stemBlock);
                    } else {
                        overworld[y-i][x+j] = Tile.getTileID(thickLeafBlock);
                    }
                }
            }
        }
        
        /*
        * Creates a tree of a given type with the base of the tree being at x y
        * Different types create different trees
        */
        public static void generateTree(int x, int y, int type, String leafBlock, String thickLeafBlock, String stemBlock) {
            try {
                
                if(type == 0) { // Type 0 - small classic oak tree

                    addLeaves(x - 2, y - 2, 5, 2, "oak_leaves", "thick_oak_leaves", "oak_wood");
                    addLeaves(x - 1, y - 4, 3, 2, "oak_leaves", "thick_oak_leaves", "oak_wood");
                    addStem(x, y, 1, 4, "oak_leaves", "thick_oak_leaves", "oak_wood");

                } else if(type == 1) { // Type 1 - medium classic oak tree

                    addLeaves(x - 2, y - 3, 5, 2, "oak_leaves", "thick_oak_leaves", "oak_wood");
                    addLeaves(x - 1, y - 5, 3, 2, "oak_leaves", "thick_oak_leaves", "oak_wood");
                    addStem(x, y, 1, 5, "oak_leaves", "thick_oak_leaves", "oak_wood");

                } else if(type == 2) { // Type 2 - taller classic oak tree

                    addLeaves(x - 2, y - 4, 5, 2, "oak_leaves", "thick_oak_leaves", "oak_wood");
                    addLeaves(x - 1, y - 6, 3, 2, "oak_leaves", "thick_oak_leaves", "oak_wood");
                    addStem(x, y, 1, 6, "oak_leaves", "thick_oak_leaves", "oak_wood");

                } else if(type == 3) { // Type 3 - small tree with rounded leaves

                    addLeaves(x - 1, y - 5, 3, 5, "oak_leaves", "thick_oak_leaves", "oak_wood");
                    addLeaves(x - 2, y - 4, 5, 3, "oak_leaves", "thick_oak_leaves", "oak_wood");
                    addStem(x, y, 1, 5, "oak_leaves", "thick_oak_leaves", "oak_wood");

                } else if(type == 4) { // Type 4 - tall tree with rounded leaves

                    addLeaves(x - 1, y - 7, 3, 5, "oak_leaves", "thick_oak_leaves", "oak_wood");
                    addLeaves(x - 2, y - 6, 5, 3, "oak_leaves", "thick_oak_leaves", "oak_wood");
                    addStem(x, y, 1, 7, "oak_leaves", "thick_oak_leaves", "oak_wood");

                }

            } catch(IndexOutOfBoundsException e){} // Ignore IndexOutOfBoundExceptions
        }
    }



    /*
     * Generates an overworld-type terrain
     */
    private static short currentTerrainPillarHeight = 80;
    public static void generateTerrain(int stepRange, int stepChance, int treeDistribution, int grassDistribution, int terrainInclineDirectionChangeChance, boolean inclineStartUpwards) {
        for(int i = 0; i < overworld.length; i++) {
            for(int j = 0; j < overworld[0].length; j++) {
                overworld[i][j] = Tile.getTileID("air");
            }
        }
        
        /*
         * New terrain is generated with pillars, which are 1 block wide and ~80 blocks tall
         * Ores are generated in veins after all pillars have been generated
         */
        boolean rising = inclineStartUpwards; // rising==true would mean that the terrain will slope upwards
        for(int x = 0; x < overworld[0].length; x++) {
            // Terrain (bedrock, stone, dirt, grass)
            for(int i = 0; i < currentTerrainPillarHeight; i++) {
                short tileType = 0;
                if(i == 0) {
                    tileType = Tile.getTileID("bedrock");
                } else if(i == 1) {
                    if(random.nextInt(3) == 0 || random.nextInt(3) == 1) {
                        tileType = Tile.getTileID("bedrock");
                    } else {
                        tileType = Tile.getTileID("stone");
                    }
                } else if(i == 2) {
                    if(random.nextInt(2) == 0) {
                        tileType = Tile.getTileID("bedrock");
                    } else {
                        tileType = Tile.getTileID("stone");
                    }
                } else if(i < currentTerrainPillarHeight - 5) {
                    tileType = Tile.getTileID("stone");
                } else if(i < currentTerrainPillarHeight - 1) {
                    tileType = Tile.getTileID("dirt");
                } else {
                    tileType = Tile.getTileID("grass_block");
                }
                overworld[overworld.length - i - 1][x] = tileType;
            }

            // Grass
            if(random.nextInt(100) < grassDistribution && overworld[overworld.length - currentTerrainPillarHeight - 1][x] == Tile.getTileID("air")) {
                overworld[overworld.length - currentTerrainPillarHeight - 1][x] = Tile.getTileID("grass");
            }

            /*
             * Tree
             */
            if(x > 0 && overworld[overworld.length - currentTerrainPillarHeight - 1][x-1] != Tile.getTileID("oak_wood")) {
                if(random.nextInt(100) < treeDistribution && (overworld[overworld.length - currentTerrainPillarHeight][x] == Tile.getTileID("grass_block") || overworld[overworld.length - currentTerrainPillarHeight][x] == Tile.getTileID("dirt"))) {
                    Tree.generateTree(x, overworld.length - currentTerrainPillarHeight - 1, random.nextInt(5), "oak_leaves", "thick_oak_leaves", "oak_wood");
                    overworld[overworld.length - currentTerrainPillarHeight][x] = Tile.getTileID("dirt");
                }
            }

            // Update level for next pillar
            if(random.nextInt(100) < stepChance) {
                currentTerrainPillarHeight += (rising ? 1 : -1);
            }
            // Attempt to change incline direction
            if(random.nextInt(100) < terrainInclineDirectionChangeChance) {
                rising = !rising;
            }

            // Ore vein bases
            // Ores are generated by creating a base ore block first and then surrounding them with more ores in the future
            for(int y = 0; y < overworld.length; y++) {
                attemptOreGeneration(x, y, "coal_ore", "stone", GameSettings.coalOreGenerationChance, 60);
                attemptOreGeneration(x, y, "iron_ore", "stone", GameSettings.ironOreGenerationChance, 45);
                attemptOreGeneration(x, y, "redstone_ore", "stone", GameSettings.diamondOreGenerationChance, 20);
                attemptOreGeneration(x, y, "lapis_ore", "stone", GameSettings.lapisOreGenerationChance, 30);
                attemptOreGeneration(x, y, "gold_ore", "stone", GameSettings.goldOreGenerationChance, 20);
                attemptOreGeneration(x, y, "diamond_ore", "stone", GameSettings.diamondOreGenerationChance, 20);
            }
        }

        // Spread ores
        spreadOre("coal_ore", "stone", GameSettings.coalOreVeinSizeIncrease, GameSettings.coalOreVeinSpreadChance);
        spreadOre("iron_ore", "stone", GameSettings.ironOreVeinSizeIncrease, GameSettings.ironOreVeinSpreadChance);
        spreadOre("redstone_ore", "stone", GameSettings.ironOreVeinSizeIncrease, GameSettings.redstoneOreVeinSpreadChance);
        spreadOre("lapis_ore", "stone", GameSettings.lapisOreVeinSizeIncrease, GameSettings.lapisOreVeinSpreadChance);
        spreadOre("gold_ore", "stone", GameSettings.goldOreVeinSizeIncrease, GameSettings.goldOreVeinSpreadChance);
        spreadOre("diamond_ore", "stone", GameSettings.diamondOreVeinSizeIncrease, GameSettings.diamondOreVeinSpreadChance);
    }

    // Spreads an ore
    public static void spreadOre(String ore, String replacable, int size, int spreadChance) {
        for(int a = 0; a < size; a++) {
            for(int i = 0; i < overworld.length; i++) {
                for(int j = 0; j < overworld[0].length; j++) {
                    try {
                        if(
                            (Tile.getTile(overworld[i-1][j]).name.equals(ore)
                            || Tile.getTile(overworld[i+1][j]).name.equals(ore)
                            || Tile.getTile(overworld[i][j-1]).name.equals(ore)
                            || Tile.getTile(overworld[i][j+1]).name.equals(ore))
                            && random.nextInt(100) < spreadChance
                            && Tile.getTile(overworld[i][j]).name.equals(replacable)
                        ) {
                            overworld[i][j] = Tile.getTileID(ore);
                        }
                    } catch(IndexOutOfBoundsException e){}
                }
            }
        }
    }

    public static void attemptOreGeneration(int x, int y, String ore, String replacable, double generationChance, int depth) {
        if(random.nextDouble(100) < generationChance * (double)((y/overworld.length) + 1) && y > overworld.length - depth && Tile.getTile(overworld[y][x]).name.equals(replacable)) {
            overworld[y][x] = Tile.getTileID(ore);
        }
    }    
}