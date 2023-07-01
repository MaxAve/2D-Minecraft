public class Lighting {
    public static int[][] lightLevelMap = new int[Terrain.WORLD_HEIGHT][Terrain.overworld[0].length];

    public static void updateLight() {
        for(int i = 0; i < lightLevelMap.length; i++)
            for(int j = 0; j < lightLevelMap[0].length; j++)
                lightLevelMap[i][j] = 0;
        
        // Sky light
        for(int x = 0; x < lightLevelMap[0].length; x++) {
            int lightLevel = 255;
            for(int y = 1; y < lightLevelMap.length; y++) {
                lightLevel -= Tile.getTileByName(Terrain.getBlock(x, y-1)).opacity;
                if(lightLevel < 0)
                    lightLevel = 0;
                lightLevelMap[y][x] = lightLevel;
            }
        }

        // Smoothen out general light
        for(int x = 0; x < lightLevelMap[0].length; x++) {
            for(int y = 1; y < lightLevelMap.length; y++) {
                try {
                    if(lightLevelMap[y][x] < 255) {
                        lightLevelMap[y][x] = (lightLevelMap[y-1][x] + lightLevelMap[y+1][x] + lightLevelMap[y][x-1] + lightLevelMap[y][x+1] + lightLevelMap[y][x]) / 4;
                    }
                } catch(IndexOutOfBoundsException e){}
            }
        }

        // Block source light
        for(int x = 0; x < lightLevelMap[0].length; x++) {
            for(int y = 1; y < lightLevelMap.length; y++) {
                if(Terrain.getBlock(x, y) == "torch") {
                    for(int _x = x-6; _x < x+6; _x++) {
                        for(int _y = y-6; _y < y+6; _y++) {
                            if((Math.abs(_x - x) + Math.abs(_y - y)) < 7) {
                                if (
                                    Tile.getTile(Terrain.overworld[_y+1][_x]).isTransparent
                                    || Tile.getTile(Terrain.overworld[_y-1][_x]).isTransparent
                                    || Tile.getTile(Terrain.overworld[_y][_x+1]).isTransparent
                                    || Tile.getTile(Terrain.overworld[_y][_x-1]).isTransparent
                                ) {
                                    lightLevelMap[_y][_x] += (255 - (Math.abs(_x - x) + Math.abs(_y - y))*42);
                                    if(lightLevelMap[_y][_x] < 0)
                                        lightLevelMap[_y][_x] = 0;
                                    if(lightLevelMap[_y][_x] > 255)
                                        lightLevelMap[_y][_x] = 255;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
