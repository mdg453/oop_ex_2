//package gameobjects;
//
//import danogl.GameObject;
//import danogl.collisions.Collision;
//import danogl.gui.ImageReader;
//import danogl.gui.rendering.Renderable;
//import danogl.util.Vector2;
//import utils.AddLife;
//import utils.RemoveGameObject;
//import gameobjects.Lives;
//
///**
// * A falling life in the game.
// *
// * @see brick_strategies.AddLifeCollisionStrategy
// * @author
// */
//public class Life extends GameObject {
//
//    private final AddLife addLifeCommand;
//    private final RemoveGameObject remover;
//
//    /**
//     * Constructs a new Life with the given center and dependencies.
//     *
//     * @param center        The center of the life.
//     * @param imageReader   ImageReader for loading the heart image.
//     * @param addLifeCommand Command for adding a life.
//     * @param remover       Utility for removing game objects.
//     */
//    public Life(Vector2 center, ImageReader imageReader, AddLife addLifeCommand, RemoveGameObject remover) {
//        super(Vector2.ZERO,
//                new Vector2(Lives.HEART_HEIGHT, Lives.HEART_HEIGHT),
//                imageReader.readImage(Lives.HEART_IMAGE_PATH, true));
//        this.addLifeCommand = addLifeCommand;
//        this.remover = remover;
//        setCenter(center);
//        setVelocity(Vector2.DOWN.mult(100));
//    }
//
//    /**
//     * A method which checks if the life should collide with the given game object.
//     * If the game object is not a paddle or a bottom wall, the life should not collide with it.
//     */
//    @Override
//    public boolean shouldCollideWith(GameObject other) {
//        return (other instanceof Paddle || other instanceof BottomWall) && super.shouldCollideWith(other);
//    }
//
//    /**
//     * A method which is called when a collision occurs.
//     * On collision with a paddle, a life is added to the player and the life is removed.
//     */
//    @Override
//    public void onCollisionEnter(GameObject other, Collision collision) {
//        super.onCollisionEnter(other, collision);
//        if (other instanceof Paddle) {
//            addLifeCommand.addLife();
//        }
//        remover.remove(this);
//    }
//}
//    @Override
//    public boolean shouldCollideWith(GameObject other) {
//        return (other instanceof Paddle || other instanceof BottomWall) && super.shouldCollideWith(other);
//    }
package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.util.Vector2;
import utils.AddLife;
import utils.RemoveGameObject;

import java.util.function.Supplier;

import danogl.gui.rendering.TextRenderable;


/**
 * A falling life in the game.
 *
 * @see brick_strategies.AddLifeCollisionStrategy
 * @author
 */

import java.awt.Color;

/**
 * A falling life in the game.
 */
public class Life extends GameObject {

    private final AddLife addLifeCommand;
    private final RemoveGameObject remover;
    private final Supplier<Integer> livesSupplier;
    private final TextRenderable textRenderable;

    public Life(Vector2 center, ImageReader imageReader, AddLife addLifeCommand,
                RemoveGameObject remover, Supplier<Integer> livesSupplier) {
        super(Vector2.ZERO,
                new Vector2(Lives.HEART_HEIGHT, Lives.HEART_HEIGHT),
                imageReader.readImage(Lives.HEART_IMAGE_PATH, true));
        this.addLifeCommand = addLifeCommand;
        this.remover = remover;
        this.livesSupplier = livesSupplier;

        // Initialize TextRenderable
        this.textRenderable = new TextRenderable("", "Comic Sans MS");
        this.textRenderable.setColor(Color.GREEN);
        setCenter(center);
        setVelocity(Vector2.DOWN.mult(100));
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other instanceof Paddle || other instanceof BottomWall) && super.shouldCollideWith(other);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Paddle) {
            addLifeCommand.addLife();
        }
        remover.remove(this, Layer.DEFAULT); // Specify the correct layer

    }

    public void updateLives() {
        int lives = Math.min(livesSupplier.get(), Lives.MAX_LIVES);

        // Update the textRenderable with the current lives count
        String text = String.valueOf(lives);
        if (lives >= 3) {
            textRenderable.setColor(Color.GREEN);
        } else if (lives == 2) {
            textRenderable.setColor(Color.YELLOW);
        } else {
            textRenderable.setColor(Color.RED);
        }
        textRenderable.setString(text);

        System.out.println("Lives updated: " + text);
    }
}

