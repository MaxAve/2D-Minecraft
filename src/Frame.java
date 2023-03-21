import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Frame extends JFrame {
    private static int mouseClickX, mouseClickY; // Stores the position of the last mouse click

    // Frame constructor
	public Frame() {
		this.add(new Panel());
		this.setTitle("2D Minecraft");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
        //this.setIcon("");
		this.addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        mouseClickX = e.getX();
		        mouseClickY = e.getY();
                if(Tile.getTile(Terrain.overworld[Terrain.selectedBlockY][Terrain.selectedBlockX]).name.equals("air")) {
                    Terrain.overworld[Terrain.selectedBlockY][Terrain.selectedBlockX] = Tile.getTileID("cobblestone");
                } else {
                    if(Tile.getTile(Terrain.overworld[Terrain.selectedBlockY][Terrain.selectedBlockX]).name.equals("thick_oak_leaves")) {
                        Terrain.overworld[Terrain.selectedBlockY][Terrain.selectedBlockX] = Tile.getTileID("oak_wood");
                    } else {
                        Terrain.overworld[Terrain.selectedBlockY][Terrain.selectedBlockX] = Tile.getTileID("air");
                    }
                }
		    }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
		});
	}

    // Gets mouse click position
    public static IntVector2 getMouseClick() {
        return new IntVector2(mouseClickX, mouseClickY);
    }

    // Needs to be called after getting mouse click position
    public static void resetMouseClick() {
        mouseClickX = -1;
        mouseClickY = -1;
    }

    // Attempts to set the icon of the frame to a predefined image
    public void setIcon(String path) {
        try {
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(path)));
        } catch(Exception e) {
            System.err.println("Failed to load window icon because an error occured.\nERROR: " + e);
        }
    }
}
