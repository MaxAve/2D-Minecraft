public class Lighting {
    public static int[][] lightLevelMap = new int[Terrain.WORLD_HEIGHT][20];

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

        // Block source light
        for(int x = 0; x < lightLevelMap[0].length; x++) {
            for(int y = 1; y < lightLevelMap.length; y++) {
                if(Terrain.getBlock(x, y) == "torch") {
                    for(int _x = x-2; _x < x+2; _x++) {
                        for(int _y = y-2; _y < y+2; _y++) {
                            lightLevelMap[_y][_x] += (200 - (int)(Math.sqrt(Math.pow(_x - x, 2) + Math.pow(_y - y, 2))));
                            if(lightLevelMap[_y][_x] < 0)
                                lightLevelMap[_y][_x] = 0;
                        }
                    }
                }
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
    }
}
