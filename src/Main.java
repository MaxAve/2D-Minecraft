public class Main {
    public static GameGraphics defaultGraphics = new GameGraphics();
    public static void main(String[] args) throws Exception {
        Tile.generateTiles();
        Terrain.generateTerrain();
        new Frame();
    }
}
