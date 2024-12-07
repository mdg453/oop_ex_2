package bricker.brick_strategies;

import brick_strategies.BasicCollisionStrategy;
import gameobjects.Life;
import utils.AddLife;  // Import the interface
import utils.RemoveGameObject;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.util.Vector2;

/**
 * Collision strategy for adding a falling life to the screen.
 *
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 * @see Life
 */
public class AddLifeCollisionStrategy extends BasicCollisionStrategy {

    private final AddLife addLifeCommand;
    private final RemoveGameObject removeGameObject;
    private final ImageReader imageReader;

    /**
     * Constructs a new AddLifeCollisionStrategy.
     *
     * @param addLifeCommand   Command for adding lives.
     * @param removeGameObject Command for removing game objects.
     * @param imageReader      ImageReader for loading images.
     */
    public AddLifeCollisionStrategy(AddLife addLifeCommand, RemoveGameObject removeGameObject,
                                    ImageReader imageReader) {
        super(removeGameObject); // Pass removeGameObject to BasicCollisionStrategy
        this.addLifeCommand = addLifeCommand;
        this.removeGameObject = removeGameObject;
        this.imageReader = imageReader;
    }

    /**
     * Adds a life to the screen when a brick is hit.
     *
     * @param thisObject the brick that was hit
     * @param otherObject the object that hit the brick
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);

        // Create a new Life object when a brick is hit
        Life life = new Life(thisObject.getCenter(), imageReader, addLifeCommand, removeGameObject);

        // Add the Life object to the game (via the AddLife implementation)
        addLifeCommand.addLife();  // This increases the player's lives

        // Log the event (console log in this case)
        System.out.println("Life added at position: " + thisObject.getCenter());
    }
}
