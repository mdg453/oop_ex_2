package gameobjects;

import java.awt.event.KeyEvent;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

/**
 * A base class for the player's paddle.
 *
 * @see Paddle
 */
public abstract class BaseOfPaddle extends GameObject {

    /** The path to the paddle image. */
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    /** The size of the paddle. */
    private static final Vector2 PADDLE_SIZE = new Vector2(100, 15);
    /** The speed of the paddle. */
    private static final float MOVEMENT_SPEED = 400;

    private final UserInputListener inputListener;
    private final Vector2 windowSize;

    /**
     * Constructs a new BaseOfPaddle with the given center.
     *
     * @param center The center position of the paddle.
     * @param inputListener The input listener to capture user input.
     * @param imageReader The image reader to load the paddle image.
     * @param windowSize The size of the game window (passed as a Vector2).
     */
    public BaseOfPaddle(Vector2 center, UserInputListener inputListener, ImageReader imageReader,
                        Vector2 windowSize) {
        super(Vector2.ZERO, PADDLE_SIZE, imageReader.readImage(PADDLE_IMAGE_PATH,
                true));
        setCenter(center);
        this.inputListener = inputListener;
        this.windowSize = windowSize;
    }

    /**
     * Updates the paddle's position based on user input.
     * Moves left and right according to the user's input.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Reset the velocity to zero and check user input
        setVelocity(Vector2.ZERO);

        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            setVelocity(Vector2.RIGHT.mult(MOVEMENT_SPEED));
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            setVelocity(Vector2.LEFT.mult(MOVEMENT_SPEED));
        }

        // Prevent the paddle from moving out of bounds
        if (getTopLeftCorner().x() < 0) {
            setTopLeftCorner(new Vector2(0, getTopLeftCorner().y()));
        }
        else if (getTopLeftCorner().x() + getDimensions().x() > windowSize.x()) {
            setTopLeftCorner(new Vector2(
                    windowSize.x() - getDimensions().x(),
                    getTopLeftCorner().y()));
        }
    }
}
