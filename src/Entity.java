import java.util.Random;

public class Entity {
    public static final float GRAVITY_ACCELERATION = 5;

    public String type;
    public Vector2.Float32 position;
    public Vector2.Float32 hitbox;
    public boolean affectedByGravity;
    public final long PERSONAL_ID;
    public int renderScale;
    public int speedX;
    public int speedY;

    public Entity(String type, int x, int y, int w, int h, boolean affectedByGravity) {
        this.type = type;
        this.position = new Vector2.Float32(x, y);
        this.hitbox = new Vector2.Float32(w, h);
        this.affectedByGravity = affectedByGravity;
        this.renderScale = 1;
        this.speedX = 0;
        this.speedY = 0;
        this.PERSONAL_ID = new Random().nextLong(Long.MAX_VALUE);
    }

    public boolean collidingWidth(Entity entity) {
        return this.position.x + this.hitbox.x/2 < entity.position.x - entity.hitbox.x/2 && this.position.x - this.hitbox.x/2 > entity.position.x + entity.hitbox.x/2 && this.position.y + this.hitbox.y/2 < entity.position.y - entity.hitbox.y/2 && this.position.y - this.hitbox.y/2 > entity.position.y + entity.hitbox.y/2;
    }

    public void updatePhysics() {
        if(affectedByGravity) {
            this.speedY += GRAVITY_ACCELERATION;
        }
        
        this.position.x += this.speedX;
        this.position.y += this.speedY;
    }

    public void render(java.awt.Graphics graphics, java.awt.Color[][] sprite) {
        GameGraphics.renderImage((int)this.position.x, (int)this.position.y, sprite, renderScale, graphics, 255);
    }
}
