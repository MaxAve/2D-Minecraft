public class Main {
    public static GameGraphics defaultGraphics = new GameGraphics();

    public static void main(String[] args) throws Exception {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
        new Frame();
        Tile.generateTiles();
        Terrain.generateTerrain(3, 35, 20, 50, 20, true);
    }
}
