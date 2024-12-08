package brick_strategies;

import danogl.GameObject;
import gameobjects.TurboBall;
import utils.RemoveGameObject;

/**
 * Collision strategy for activating turbo mode on the main ball.
 */
public class TurboBallStrategy extends BasicCollisionStrategy {
    private final TurboBall mainBall;

    /**
     * Constructs a new TurboBallStrategy.
     *
     * @param removeGameObject Utility to remove game objects.
     * @param mainBall         The main ball object.
     */
    public TurboBallStrategy(RemoveGameObject removeGameObject, TurboBall mainBall) {
        super(removeGameObject);
        this.mainBall = mainBall;
    }

    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);

        // Activate turbo mode if the main ball hits the brick
        if (otherObject == mainBall && !mainBall.isTurboModeActive()) {
            mainBall.activateTurboMode();
        }
    }
}
