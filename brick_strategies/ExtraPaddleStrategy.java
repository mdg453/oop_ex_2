package bricker.brick_strategies;

import brick_strategies.BasicCollisionStrategy;
import gameobjects.ExtraPaddle;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import utils.AddGameObject;
import utils.RemoveGameObject;

/**
 * A collision strategy that adds an extra paddle to the game.
 *
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 * @see ExtraPaddle
 */
public class ExtraPaddleStrategy extends BasicCollisionStrategy {
    private final ExtraPaddle extraPaddle;
    private final AddGameObject addGameObject;
    private final RemoveGameObject removeGameObject;

    /**
     * Constructs a new ExtraPaddleStrategy.
     *
     * @param inputListener    The input listener for the paddle.
     * @param imageReader      The image reader to load the paddle image.
     * @param windowSize       The size of the game window.
     * @param addGameObject    Utility to add game objects dynamically.
     * @param removeGameObject Utility to remove game objects dynamically.
     */
    public ExtraPaddleStrategy(UserInputListener inputListener, ImageReader imageReader,
                               Vector2 windowSize, AddGameObject addGameObject,
                               RemoveGameObject removeGameObject) {
        super(removeGameObject); // Pass RemoveGameObject to parent class
        this.addGameObject = addGameObject;
        this.removeGameObject = removeGameObject;

        this.extraPaddle = new ExtraPaddle(
                windowSize.mult(0.5f), // Initial paddle position at the center
                inputListener,
                imageReader,
                windowSize,
                addGameObject,
                removeGameObject
        );
    }

    /**
     * An action to perform when a collision occurs.
     *
     * @param thisObject  The object that has this strategy.
     * @param otherObject The object that collided with this object.
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);

        // Initialize the extra paddle
        if (!isPaddleActive()) { // Prevent adding multiple paddles
            extraPaddle.initialize();
            addGameObject.add(extraPaddle); // Add the paddle to the game
        }
    }

    /**
     * Checks if the extra paddle is currently active in the game.
     *
     * @return true if the paddle is active, false otherwise.
     */
    private boolean isPaddleActive() {
        return extraPaddle.getTopLeftCorner() != null; // Assuming non-null position means active
    }
}


