package gameobjects;

import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.SoundReader;
import danogl.util.Vector2;

/**
 * The main ball of the game.
 *
 * @see BaseOfBall
 */
public class Ball extends BaseOfBall {

    /** The path to the ball image. */
    private static final String BALL_IMAGE_PATH = "assets/ball.png";

    /**
     * Constructs a new MainBall.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param imageReader The image reader to load ball images.
     * @param soundReader The sound reader for collision sounds.
     * @param inputListener The input listener for handling key presses.
     */
    public Ball(Vector2 windowDimensions, ImageReader imageReader, SoundReader soundReader,
                UserInputListener inputListener) {
        super(
                windowDimensions.mult(0.5f), // Place ball in the center of the window
                DEFAULT_SIZE,
                imageReader.readImage(BALL_IMAGE_PATH, true),
                soundReader,
                inputListener
        );
        // Set random velocity
        var rand = new java.util.Random();
        this.setVelocity(new Vector2(rand.nextFloat() - 0.5f, 1).normalized().
                mult(DEFAULT_MOVEMENT_SPEED));
    }
}

