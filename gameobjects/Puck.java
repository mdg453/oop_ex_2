package gameobjects;

import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

public class Puck extends BaseOfBall {

    private static final String PUCK_IMAGE_PATH = "assets/mockBall.png";

    /**
     * Constructs a new Puck object.
     *
     * @param center       The center position of the puck.
     * @param imageReader  Utility for reading images.
     * @param soundReader  Utility for handling sounds.
     * @param inputListener Utility for handling user input.
     */
    public Puck(Vector2 center, ImageReader imageReader, SoundReader soundReader,
                UserInputListener inputListener) {
        super(
                center,
                BaseOfBall.DEFAULT_SIZE.mult(0.75f), // Puck size is derived from BaseOfBall.DEFAULT_SIZE
                imageReader.readImage(PUCK_IMAGE_PATH, true),
                soundReader,
                inputListener
        );

        // Set random velocity in the top half of the unit circle
        var rand = new java.util.Random();
        float angle = (float) (rand.nextDouble() * Math.PI); // Random angle between 0 and π
        float x = (float) Math.cos(angle); // x-component of velocity
        float y = (float) -Math.sin(angle); // y-component of velocity (negative for upward movement)
        this.setVelocity(new Vector2(x, y).normalized().mult(DEFAULT_MOVEMENT_SPEED));
    }
}

