////package brick_strategies;
////
////import danogl.GameObject;
////import danogl.collisions.Layer;
////import utils.RemoveGameObject;
////import danogl.collisions.GameObjectCollection;
////import brick_strategies.CollisionStrategy;
////
////
//// ///**
//// * A basic collision strategy that removes the object from the
//// * screen when a collision is detected.
//// *
//// * @see CollisionStrategy
//// */
////public class BasicCollisionStrategy implements CollisionStrategy {
////    private final RemoveGameObject gameObjectRemover;
////
////    /**
////     * Constructs a new BasicCollisionStrategy.
////     *
////     * @param gameObjectRemover A utility for removing game objects.
////     */
////    public BasicCollisionStrategy(RemoveGameObject gameObjectRemover) {
////        this.gameObjectRemover = gameObjectRemover;
////    }
////
////    /**
////     * Removes the object from the screen when a collision is detected
////     * and prints a message to the console.
////     *
////     * @param thisObject  The object that this strategy is attached to.
////     * @param otherObject The object that this object collided with.
////     */
////    @Override
////    public void onCollision(GameObject thisObject, GameObject otherObject) {
////        gameObjectRemover.remove(thisObject, Layer.STATIC_OBJECTS);
////        System.out.printf("%s removed from the game.%n",
////                thisObject.getClass().getSimpleName());
////    }
////}
////
////
//
//package brick_strategies;
//
//import danogl.GameObject;
//import danogl.collisions.Layer;
//import utils.RemoveGameObject;
//import brick_strategies.CollisionStrategy;
//
///**
// * A basic collision strategy that removes the object from the
// * screen when a collision is detected.
// *
// * @see CollisionStrategy
// */
//public class BasicCollisionStrategy implements CollisionStrategy {
//    private final RemoveGameObject gameObjectRemover;
//
//    /**
//     * Constructs a new BasicCollisionStrategy.
//     *
//     * @param gameObjectRemover A utility for removing game objects.
//     */
//    public BasicCollisionStrategy(RemoveGameObject gameObjectRemover) {
//        this.gameObjectRemover = gameObjectRemover;
//    }
//
//    /**
//     * Removes the object from the screen when a collision is detected
//     * and prints a message to the console.
//     *
//     * @param thisObject  The object that this strategy is attached to.
//     * @param otherObject The object that this object collided with.
//     */
//    @Override
//    public void onCollision(GameObject thisObject, GameObject otherObject) {
//        gameObjectRemover.remove(thisObject, Layer.STATIC_OBJECTS);
//        System.out.printf("%s removed from the game.%n",
//                thisObject.getClass().getSimpleName());
//    }
//}

package brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import utils.RemoveGameObject;

/**
 * Basic collision strategy that removes game objects.
 */
public class BasicCollisionStrategy implements CollisionStrategy {

    private final RemoveGameObject removeGameObject;

    public BasicCollisionStrategy(RemoveGameObject removeGameObject) {
        this.removeGameObject = removeGameObject;
    }

    protected RemoveGameObject getRemoveGameObject() {
        return removeGameObject;
    }

    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        System.out.println("Collision detected: " + thisObject.getClass().getSimpleName() + " with " + otherObject.getClass().getSimpleName());
        getRemoveGameObject().remove(thisObject, Layer.STATIC_OBJECTS);
    }
}


