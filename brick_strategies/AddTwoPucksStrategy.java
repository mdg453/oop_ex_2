package brick_strategies;

import danogl.collisions.Layer;
import gameobjects.Puck;
import utils.AddGameObject;
import danogl.GameObject;
import utils.RemoveGameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import gameobjects.BaseOfBall;

/**
 * Collision strategy for adding 2 pucks to the screen.
 *
 * @see BasicCollisionStrategy
 * @see Puck
 */
public class AddTwoPucksStrategy extends BasicCollisionStrategy {

    private static final int ADD_PUCKS_NUMBER = 2;
    private final AddGameObject gameObjectAdder;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;

    /**
     * Constructs a new AddTwoPucksStrategy.
     *
     * @param gameObjectRemover A utility for removing game objects.
     * @param gameObjectAdder A utility for adding game objects.
     * @param imageReader      A utility for reading images.
     * @param soundReader      A utility for reading sounds.
     * @param inputListener    A utility for handling user input.
     */
    public AddTwoPucksStrategy(RemoveGameObject gameObjectRemover, AddGameObject gameObjectAdder,
                               ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener) {
        super(gameObjectRemover);
        this.gameObjectAdder = gameObjectAdder;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
    }

    /**
     * Adds 2 pucks to the screen when a brick is hit.
     *
     * @param thisObject  The brick that was hit.
     * @param otherObject The object that hit the brick.
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        for (int i = 0; i < ADD_PUCKS_NUMBER; i++) {
            var puck = new Puck(
                    thisObject.getCenter(),
                    imageReader,
                    soundReader,
                    inputListener
            );
            gameObjectAdder.add(puck, Layer.DEFAULT);
        }
        System.out.println("Two pucks added to the game.");
    }

}
