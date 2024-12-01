package gameobjects;

import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import gameobjects.BaseOfPaddle;

/**
 * A representation of the player's paddle.
 */
public class Paddle extends BaseOfPaddle {

    /** The distance of the paddle from the bottom of the screen. */
    private static final int BOTTOM = 50;

    /**
     * Constructs a new Paddle.
     *
     * @param windowWidth The width of the game window.
     * @param windowHeight The height of the game window.
     * @param inputListener The input listener for user input.
     * @param imageReader The image reader to load the paddle image.
     */
    public Paddle(float windowWidth, float windowHeight, UserInputListener inputListener,
                  ImageReader imageReader) {
        // Call the super constructor with all required parameters
        super(new Vector2(windowWidth * 0.5f, windowHeight - BOTTOM),
                inputListener, imageReader, new Vector2(windowWidth, windowHeight));
    }
}
