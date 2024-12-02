package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.util.Vector2;
import brick_strategies.CollisionStrategy;
import brick_strategies.CollisionStrategyGenerator;

public class Brick extends GameObject {
    private static final String BRICK_IMAGE_PATH = "C:/Users/meita/Documents/university/y4s1/oop/ex2/oop_ex_2/" +
            "assets/assets/brick.png";
    private CollisionStrategy collisionStrategy;

    /**
     * Constructs a new Brick with the given top-left corner and dimensions.
     * The renderable image is automatically loaded using the image path.
     *
     * @param topLeftCorner         The top-left corner of the brick.
     * @param dimensions            The dimensions of the brick.
     * @param imageReader           The ImageReader for loading the image.
     * @param strategyGenerator     The generator to create the CollisionStrategy.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions,
                 ImageReader imageReader, CollisionStrategyGenerator strategyGenerator) {
        super(topLeftCorner, dimensions,
                imageReader.readImage(BRICK_IMAGE_PATH, true));
        if (imageReader.readImage(BRICK_IMAGE_PATH, true) == null) {
            System.err.println("Failed to load brick image from path: " + BRICK_IMAGE_PATH);
        }
        this.collisionStrategy = strategyGenerator.generateStrategy();
    }

    /**
     * Called when the brick collides with another game object.
     *
     * @param other     The other game object involved in the collision.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        collisionStrategy.onCollision(this, other);
    }
}



































//package gameobjects;
//
//import danogl.GameObject;
//import danogl.collisions.Collision;
//import danogl.gui.ImageReader;
//import danogl.util.Vector2;
//import brick_strategies.CollisionStrategy;
//import brick_strategies.CollisionStrategyGenerator;
//
//public class Brick extends GameObject {
//    private static final String BRICK_IMAGE_PATH = "assets/brick.png";
//    private CollisionStrategy collisionStrategy;
//
//    /**
//     * Constructs a new Brick with the given top-left corner and dimensions.
//     * The renderable image is automatically loaded using the image path.
//     *
//     * @param topLeftCorner         The top-left corner of the brick.
//     * @param dimensions            The dimensions of the brick.
//     * @param imageReader           The ImageReader for loading the image.
//     * @param strategyGenerator     The generator to create the CollisionStrategy.
//     */
//    public Brick(Vector2 topLeftCorner, Vector2 dimensions,
//                 ImageReader imageReader, CollisionStrategyGenerator strategyGenerator) {
//        super(topLeftCorner, dimensions,
//                imageReader.readImage(BRICK_IMAGE_PATH, true));
//        this.collisionStrategy = strategyGenerator.generateStrategy();
//    }
//
//    /**
//     * Called when the brick collides with another game object.
//     *
//     * @param other     The other game object involved in the collision.
//     * @param collision The collision details.
//     */
//    @Override
//    public void onCollisionEnter(GameObject other, Collision collision) {
//        collisionStrategy.onCollision(this, other);
//    }
//}
