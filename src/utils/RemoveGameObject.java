package utils;

import danogl.GameObject;
import danogl.collisions.Layer;

public interface RemoveGameObject {
    void remove(GameObject gameObject, int layer);

    public default void remove(GameObject gameObject) {
        remove(gameObject, Layer.DEFAULT);
    }
}


