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
        for(int i = 0; i < 30; i++)
            for(int j = 0; j < 30; j++)
                Main.defaultGraphics.renderImage(g, "img/stone.png", 3, 20 + i*48, 20 + j * 48);

        // FPS counter
        g.setColor(Color.WHITE);
        g.setFont(new Font(null, Font.PLAIN, 18));
        g.drawString("FPS: " + getFPS(), 20, 40);
        frames++;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

    // Key adapter
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {

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
