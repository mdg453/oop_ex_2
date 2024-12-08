//package utils;
//
//import danogl.GameObject;
//import danogl.collisions.Layer;
//
//public interface RemoveGameObject {
//    void remove(GameObject gameObject, int layer);
//
//    default void remove(GameObject gameObject) {
//        remove(gameObject, Layer.DEFAULT);
//    }
//
//    /**
//     * Removes multiple game objects from the specified layer.
//     *
//     * @param layer     The layer to remove the game objects from.
//     * @param objects   The game objects to remove.
//     */
//    default void removeMultiple(int layer, GameObject... objects) {
//        for (GameObject object : objects) {
//            remove(object, layer);
//        }
//    }
//
//    /**
//     * Removes multiple game objects from the default layer.
//     *
//     * @param objects The game objects to remove.
//     */
//    default void removeMultiple(GameObject... objects) {
//        removeMultiple(Layer.DEFAULT, objects);
//    }
//}

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import utils.RemoveGameObject;

public class RemoveGameObjectImp implements RemoveGameObject {
    private final GameObjectCollection gameObjects;

    public RemoveGameObjectImp(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public void remove(GameObject gameObject, int layer) {
        System.out.println("Removing " + gameObject.getClass().getSimpleName() + " from layer " + layer);
        gameObjects.removeGameObject(gameObject, layer);
    }
}