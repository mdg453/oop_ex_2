package gameobjects;

import java.awt.event.KeyEvent;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public abstract class BaseOfBall extends GameObject {
    // Constants
    protected static final float DEFAULT_MOVEMENT_SPEED = 200;
    protected static final Vector2 DEFAULT_SIZE = new Vector2(20, 20);
    protected static final String COLLISION_SOUND_PATH = "assets/Bubble5_4.wav";

    // Fields
    protected Sound collisionSound;
    private int collisionCounter = 0;
    private final UserInputListener inputListener;

    // Constructor with SoundReader
    public BaseOfBall(Vector2 center, Vector2 dimensions, Renderable renderable, SoundReader soundReader,
                      UserInputListener inputListener) {
        super(Vector2.ZERO, dimensions, renderable);
        setCenter(center);
        this.collisionSound = soundReader != null ? soundReader.readSound(COLLISION_SOUND_PATH) : null;
        this.inputListener = inputListener;
    }

    // Constructor without SoundReader
    public BaseOfBall(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                      UserInputListener inputListener) {
        this(topLeftCorner, dimensions, renderable, null, inputListener);
    }

    // Getters
    public int getCollisionCounter() {
        return collisionCounter;
    }

    // Collision Handling
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.setVelocity(this.getVelocity().flipped(collision.getNormal()));
        this.collisionCounter++;
        if (collisionSound != null) {
            collisionSound.play();
        }
        System.out.printf("Collision with %s. Total collisions: %d%n", other.getClass().getSimpleName(),
                collisionCounter);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (inputListener != null && inputListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            this.setVelocity(getVelocity().normalized().mult(3 * DEFAULT_MOVEMENT_SPEED));
        }
        else {
            this.setVelocity(getVelocity().normalized().mult(DEFAULT_MOVEMENT_SPEED));
        }
    }
}
