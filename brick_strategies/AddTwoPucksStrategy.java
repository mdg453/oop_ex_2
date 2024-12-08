//package brick_strategies;
//
//import danogl.collisions.Layer;
//import gameobjects.Puck;
//import utils.AddGameObject;
//import danogl.GameObject;
//import utils.RemoveGameObject;
//import danogl.gui.ImageReader;
//import danogl.gui.SoundReader;
//import danogl.gui.UserInputListener;
//import danogl.util.Vector2;
//import gameobjects.BaseOfBall;
//import utils.RemoveGameObject;
///**
// * Collision strategy for adding 2 pucks to the screen.
// *
// * @see BasicCollisionStrategy
// * @see Puck
// */
//public class AddTwoPucksStrategy extends BasicCollisionStrategy {
//
//    private static final int ADD_PUCKS_NUMBER = 2;
//    private final AddGameObject gameObjectAdder;
//    private final ImageReader imageReader;
//    private final SoundReader soundReader;
//    private final UserInputListener inputListener;
//
//
//    /**
//     * Constructs a new AddTwoPucksStrategy.
//     *
//     * @param gameObjectRemover A utility for removing game objects.
//     * @param gameObjectAdder A utility for adding game objects.
//     * @param imageReader      A utility for reading images.
//     * @param soundReader      A utility for reading sounds.
//     * @param inputListener    A utility for handling user input.
//     */
////    public AddTwoPucksStrategy(RemoveGameObject gameObjectRemover, AddGameObject gameObjectAdder,
////                               ImageReader imageReader, SoundReader soundReader,
////                               UserInputListener inputListener) {
////        super(gameObjectRemover);
////        this.gameObjectAdder = gameObjectAdder;
////        this.imageReader = imageReader;
////        this.soundReader = soundReader;
////        this.inputListener = inputListener;
////    }
//
//
//    public AddTwoPucksStrategy(RemoveGameObject removeGameObject, AddGameObject gameObjectAdder,
//                               ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
//                               Vector2 windowSize) {
//        super(removeGameObject);
//        this.gameObjectAdder = gameObjectAdder;
//        this.imageReader = imageReader;
//        this.soundReader = soundReader;
//        this.inputListener = inputListener;
//        this.windowSize = windowSize; // Store the window size
//        this.removeGameObject = removeGameObject; // Store the RemoveGameObject instance
//    }
//
//    /**
//     * Adds 2 pucks to the screen when a brick is hit.
//     *
//     * @param thisObject  The brick that was hit.
//     * @param otherObject The object that hit the brick.
//     */
////    @Override
////    public void onCollision(GameObject thisObject, GameObject otherObject) {
////        super.onCollision(thisObject, otherObject);
////        for (int i = 0; i < ADD_PUCKS_NUMBER; i++) {
////            var puck = new Puck(
////                    thisObject.getCenter(),
////                    imageReader,
////                    soundReader,
////                    inputListener
////            );
////            gameObjectAdder.add(puck, Layer.DEFAULT);
////        }
////        System.out.println("Two pucks added to the game.");
////    }
//    @Override
//    public void onCollision(GameObject thisObject, GameObject otherObject) {
//        super.onCollision(thisObject, otherObject);
//        for (int i = 0; i < ADD_PUCKS_NUMBER; i++) {
//            Vector2 puckPosition = thisObject.getCenter(); // Center of the brick
//            Puck puck = new Puck(
//                    puckPosition,
//                    windowSize, // Pass the windowSize parameter
//                    imageReader,
//                    soundReader,
//                    inputListener,
//                    removeGameObject // Pass the RemoveGameObject instance
//            );
//            gameObjectAdder.add(puck, Layer.DEFAULT);
//        }
//        System.out.println("Two pucks added to the game.");
//    }
//
//}
package brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import gameobjects.Puck;
import utils.AddGameObject;
import utils.RemoveGameObject;

/**
 * Strategy for adding two pucks to the game upon collision.
 */
public class AddTwoPucksStrategy extends BasicCollisionStrategy {

    private static final int ADD_PUCKS_NUMBER = 2;
    private final AddGameObject gameObjectAdder;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final Vector2 windowSize;

    /**
     * Constructs a new AddTwoPucksStrategy.
     *
     * @param removeGameObject Utility for removing game objects.
     * @param gameObjectAdder  Utility for adding game objects.
     * @param imageReader      Reader for images used in the game.
     * @param soundReader      Reader for sounds used in the game.
     * @param inputListener    Listener for user inputs.
     * @param windowSize       Size of the game window.
     */
    public AddTwoPucksStrategy(RemoveGameObject removeGameObject, AddGameObject gameObjectAdder,
                               ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, Vector2 windowSize) {
        super(removeGameObject);
        this.gameObjectAdder = gameObjectAdder;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowSize = windowSize;
    }

    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        for (int i = 0; i < ADD_PUCKS_NUMBER; i++) {
            Vector2 puckPosition = thisObject.getCenter(); // Position at the center of the brick
            Puck puck = new Puck(
                    puckPosition,
                    windowSize, // Pass the window size to the puck
                    imageReader,
                    soundReader,
                    inputListener,
                    getRemoveGameObject() // Access the RemoveGameObject instance via getter
            );
            gameObjectAdder.add(puck, Layer.DEFAULT);
        }
        System.out.println("Two pucks added to the game.");
    }
}
