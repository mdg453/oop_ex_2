package brick_strategies;

import danogl.GameObject;
import utils.RemoveGameObject;

/**
 * A collision strategy that combines two other strategies.
 *
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 */
public class DualBehaviorCollisionStrategy extends BasicCollisionStrategy {
    private final CollisionStrategy[] strategies;

    public DualBehaviorCollisionStrategy(int maxStrategies, CollisionStrategy... strategies) {
        super(new RemoveGameObject() {
            @Override
            public void remove(GameObject gameObject, int layer) {
                System.out.println("Removing " + gameObject.getClass().getSimpleName() + " from layer " + layer);
            }
        });

        this.strategies = strategies;
    }

    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        for (CollisionStrategy strategy : strategies) {
            strategy.onCollision(thisObject, otherObject);
        }
        System.out.println("Dual behavior collision strategy performed.");
    }
}
///**
// * An action to perform when a collision occurs.
// *
// * @param thisObject the object that has this strategy
// * @param otherObject the object that collided with this object
// */
//@Override
//public void onCollision(GameObject thisObject, GameObject otherObject) {
//    super.onCollision(thisObject, otherObject);
//    // Perform collision actions for all combined strategies
//    for (CollisionStrategy strategy : strategies) {
//        strategy.onCollision(thisObject, otherObject);
//    }
//    // Print out a message (no logger used)
//    System.out.println("Dual behavior collision strategy performed.");
//}
//}
//    public DualBehaviorCollisionStrategy(int maxStrategies, CollisionStrategy... strategies) {
//        super(new RemoveGameObject() {
//            @Override
//            public void remove(GameObject gameObject, int layer) {
//                System.out.println("Removing " + gameObject.getClass().getSimpleName() + " from layer " +
//                        layer);
//            }
//        });
//
//        Random rand = new Random();
//        for (int i = 0; i < AMOUNT_OF_BEHAVIORS; i++) {
//            int maxIndex = strategies.length;
//            // The amount of strategies the DualBehaviorCollisionStrategy can combine
//            int nextMax = maxStrategies - actualStrategiesCombined - (AMOUNT_OF_BEHAVIORS - i - 1);
//            if (nextMax >= AMOUNT_OF_BEHAVIORS)
//                maxIndex++; // Can choose a DualBehaviorCollisionStrategy
//
//            int index = rand.nextInt(maxIndex);
//
//            if (index == strategies.length) { // Create a new DualBehaviorCollisionStrategy
//                DualBehaviorCollisionStrategy dual = new DualBehaviorCollisionStrategy(nextMax, strategies);
//                this.strategies[i] = dual;
//                actualStrategiesCombined += dual.actualStrategiesCombined;
//            } else {
//                this.strategies[i] = strategies[index];
//                actualStrategiesCombined++;
//            }
//        }
//    }



