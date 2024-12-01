package utils;

import danogl.GameObject;
import danogl.collisions.Layer;

public interface AddGameObject {
    void add(GameObject gameObject, int layer);

    public default void add(GameObject gameObject) {
        add(gameObject, Layer.DEFAULT);
    }

}
