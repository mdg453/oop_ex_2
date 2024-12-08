package utils;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

@FunctionalInterface
public interface RemoveGameObject {
    void remove(GameObject gameObject, int layer);
}


