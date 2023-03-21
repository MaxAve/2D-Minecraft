import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Panel extends JPanel implements ActionListener {
    // Panel size
	static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static final int SCREEN_WIDTH = (int)screenSize.getWidth();
	static final int SCREEN_HEIGHT = (int)screenSize.getHeight();

	Timer timer;

    // Number of frame updates, this lets us calculate the FPS
    private static short frames = 0;

	// Constructor
	// Initializes the Panel
	public Panel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT/2));
		this.setBackground(new Color(199, 229, 252));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		timer = new Timer(0, this);
		timer.start();
        FPSCounter gameFPSCounter = new FPSCounter();
        gameFPSCounter.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
        /*
         * Terrain rendering
         */
        GameGraphics.renderTerrain(g, -playerX, -playerY);

        /*
         * Selected block highlight
         */
        g.setColor(new Color(255, 255, 255, 120));
        g.fillRect(Terrain.selectedBlockX * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale - playerX, Terrain.selectedBlockY * Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale - playerY, Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale, Tile.DEFAULT_TILE_SIZE * GameSettings.tileRenderScale);

        /*
         * In-game debug output (aka F3)
         */
        if(GameSettings.showDebug) {
            g.setColor(Color.WHITE);
            g.setFont(new Font(null, Font.PLAIN, 18));

            g.drawString("FPS: " + getFPS(), 20, 40); // FPS counter
            g.drawString("Position - x: " + playerX + ", y: " + playerY, 20, 60); // Position
            g.drawString("XRay: " + (GameSettings.xRayModeOn ? "true" : "false"), 20, 80); // XRay toggle
            g.drawString("Rendering: " + GameGraphics.tilesRendered + " tiles", 20, 100);
            g.drawString("Block - (x: " + Terrain.selectedBlockX + ", y: " + Terrain.selectedBlockY + "): " + Tile.getSelectedBlock().name, 20, 120);

            // FPS graph
            g.setColor(Color.BLACK);
            g.fillRect(20, SCREEN_HEIGHT - 400, 750, 300);
            for(int i = 0; i < Debug.fpsGraph.size(); i++) {
                g.setColor(new Color(255 - (Debug.fpsGraph.get(i) * 3 > 255 ? 255 : Debug.fpsGraph.get(i) * 3), (Debug.fpsGraph.get(i) * 3 > 255 ? 255 : Debug.fpsGraph.get(i) * 3), 0));
                g.fillRect(20 + i * 10, SCREEN_HEIGHT - 100 - Debug.fpsGraph.get(i)*3, 10, Debug.fpsGraph.get(i)*3);
            }
            g.setColor(Color.WHITE);
            g.drawRect(20, SCREEN_HEIGHT - 400, 750, 300);

            g.setColor(Color.WHITE);
            g.drawString("FPS", 25, SCREEN_HEIGHT - 380);
            g.drawString("Average: " + Debug.averageFPS, 25, SCREEN_HEIGHT - 360);
            g.drawString("Min: " + Debug.minFPS, 25, SCREEN_HEIGHT - 340);
            g.drawString("Max: " + Debug.maxFPS, 25, SCREEN_HEIGHT - 320);
        }

        frames++;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

    //TODO remove later
    public static int playerX=0, playerY=9000, speed=50;

    // Key adapter
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
                case KeyEvent.VK_X:
                    GameSettings.xRayModeOn = !GameSettings.xRayModeOn;
                    break;
                case KeyEvent.VK_W:
                    playerY -= speed;
                    break;
                case KeyEvent.VK_A:
                    playerX -= speed;
                    break;
                case KeyEvent.VK_S:
                    playerY += speed;
                    break;
                case KeyEvent.VK_D:
                    playerX += speed;
                    break;
                case KeyEvent.VK_F3:
                    GameSettings.showDebug = !GameSettings.showDebug;
                    break;
			}
		}
	}

    // Counts FPS
    private static class FPSCounter extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    sleep(1000);
                    Debug.FPS = frames;
                    frames = 0;
                    Debug.recordFPS();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static short getFPS() {
        return Debug.FPS;
    }
}
