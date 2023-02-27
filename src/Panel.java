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
    private static short FPS = 0; // Number of frames rendered in a second

	// Constructor
	// Initializes the Panel
	public Panel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT/2));
		this.setBackground(Color.BLACK);
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
        GameGraphics.renderTerrain(g, -playerX, -playerY);

        // Debug
        g.setColor(Color.WHITE);
        g.setFont(new Font(null, Font.PLAIN, 18));

        g.drawString("FPS: " + getFPS(), 20, 40); // FPS counter
        g.drawString("Position: " + playerX + ", " + playerY, 20, 60); // Position

        frames++;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

    //TODO remove later
    public static int playerX=0, playerY=0, speed=50;

    // Key adapter
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
                case KeyEvent.VK_X:
                GameSettings.xRayModeOn = !GameSettings.xRayModeOn;
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
                    FPS = frames;
                    frames = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static short getFPS() {
        return FPS;
    }
}
