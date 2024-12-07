package gameobjects;

import danogl.gui.ImageReader;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import gameobjects.BaseOfBall;
import utils.AddGameObject;
import utils.RemoveGameObject;

public class ExtraPaddle extends BaseOfPaddle {

    private static final int COLLISIONS_BEFORE_DISAPPEARING = 4;
    private int collisions;
    private final AddGameObject addGameObject;
    private final RemoveGameObject removeGameObject;

    /**
     * Constructs a new ExtraPaddle.
     *
     * @param center           The center position of the paddle.
     * @param inputListener    The input listener to capture user input.
     * @param imageReader      The image reader to load the paddle image.
     * @param windowSize       The size of the game window.
     * @param addGameObject    Utility to add game objects dynamically.
     * @param removeGameObject Utility to remove game objects dynamically.
     */
    public ExtraPaddle(Vector2 center, UserInputListener inputListener, ImageReader imageReader,
                       Vector2 windowSize, AddGameObject addGameObject,
                       RemoveGameObject removeGameObject) {
        super(center, inputListener, imageReader, windowSize);
        this.addGameObject = addGameObject;
        this.removeGameObject = removeGameObject;
    }

    /**
     * Called on collision. Decreases the collision counter.
     *
     * @param other     The other object involved in the collision.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, danogl.collisions.Collision collision) {
        super.onCollisionEnter(other, collision);
        if (!(other instanceof BaseOfBall)) {
            return;
        }
        collisions--;
        if (collisions == 0) {
            removeGameObject.remove(this); // Remove the paddle from the game
        }
    }

    /**
     * Initializes the extra paddle with collision count and placement.
     */
    public void initialize() {
        if (collisions > 0) {
            return; // Already initialized
        }
        collisions = COLLISIONS_BEFORE_DISAPPEARING;
        setCenter(windowSize.mult(0.5f)); // Center the paddle
        addGameObject.add(this); // Add to the game
    }
}
