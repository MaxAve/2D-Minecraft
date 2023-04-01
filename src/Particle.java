import java.util.ArrayList;
import java.awt.*;

public class Particle {
    public static ArrayList<Particle> particles = new ArrayList<>();

    // Particle position
    public Vector2.Float32 position;

    // Parameters
    public Vector2.Float32 startVelocity;
    public boolean gravity;

    // Particle physics (pun intended)
    private Vector2.Float32 velocity = new Vector2.Float32(0, 0);
    private float gAcceleration; // Acceleration by gravity
    private float gSpeed;
    private float resistance; // Air resistance
    private float maxFallSpeed;

    public Color[][] image;
    public short scale;

    // Constructor
    public Particle(Vector2.Float32 startPosition, Vector2.Float32 startVelocity, boolean applyGravity, float gAcceleration, float resistance, float maxFallSpeed, Color[][] image, short scale) {
        this.position = startPosition;
        this.startVelocity = startVelocity;
        this.gravity = applyGravity;
        this.gAcceleration = gAcceleration;
        this.resistance = resistance;
        this.velocity.x = this.startVelocity.x;
        this.velocity.y = this.startVelocity.y;
        this.maxFallSpeed = maxFallSpeed;
        this.image = image;
        this.scale = scale;
        particles.add(this);
    }

    // Moves the particle
    public void updatePhysicsState() {
        if(this.velocity.y <= this.maxFallSpeed)
            this.velocity.y += this.gSpeed;
        else
            this.velocity.y = this.maxFallSpeed;
        this.position.x += this.velocity.x;
        this.position.y += this.velocity.y;
        this.velocity.x /= resistance;
        this.gSpeed += this.gAcceleration;
    }

    public void renderParticle(Graphics g) {
        for(int i = 0; i < this.image.length; i++) {
            for(int j = 0; j < this.image[0].length; j++) {
                g.setColor(this.image[i][j]);
                g.fillRect(j*this.scale + (int)this.position.x, i*this.scale + (int)this.position.y, this.scale, this.scale);
            }
        }
    }
}
