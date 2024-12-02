package utils;

import danogl.GameObject;
import danogl.collisions.Layer;

public interface AddGameObject {
    void add(GameObject gameObject, int layer);

    default void add(GameObject gameObject) {
        add(gameObject, Layer.DEFAULT);
    }

    /**
     * Adds multiple game objects to the specified layer.
     *
     * @param layer     The layer to add the game objects to.
     * @param objects   The game objects to add.
     */
    default void addMultiple(int layer, GameObject... objects) {
        for (GameObject object : objects) {
            add(object, layer);
        }
    }

    /**
     * Adds multiple game objects to the default layer.
     *
     * @param objects The game objects to add.
     */
    default void addMultiple(GameObject... objects) {
        addMultiple(Layer.DEFAULT, objects);
    }
}
