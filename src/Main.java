public class Main {
    public static GameGraphics defaultGraphics = new GameGraphics();
    public static void main(String[] args) throws Exception {
        Tile.generateTiles();
        Terrain.generateTerrain(3, 75, 10);
        new Frame();
    }
}
