//package brick_strategies;
//
//import bricker.brick_strategies.AddLifeCollisionStrategy;
//import bricker.brick_strategies.ExtraPaddleStrategy;
//import danogl.gui.ImageReader;
//import danogl.gui.SoundReader;
//import danogl.gui.UserInputListener;
//import danogl.util.Vector2;
//import utils.AddGameObject;
//import utils.RemoveGameObject;
//import gameobjects.TurboBall;
//import utils.AddLife;
//
//import java.util.Random;
//
//public class CollisionStrategyGeneratorLevel2 implements CollisionStrategy{
//
//    /** The maximum amount of actual behaviors to combine. */
//    private static final int MAX_ACTUAL_BEHAVIORS_AMOUNT = 3;
//
//    UserInputListener inputListener;
//    ImageReader imageReader;
//    Vector2 windowSize;
//    AddGameObject addGameObject;
//    RemoveGameObject removeGameObject;
//    AddGameObject gameObjectAdder;
//    SoundReader soundReader;
//    TurboBall turboBall;
//    AddLife addLifeCommand;
//
//
//
//    /** The basic collision strategy. */
//    private BasicCollisionStrategy basicStrategy = new BasicCollisionStrategy(removeGameObject);
//
//    /** The add pucks collision strategy. */
//    private AddTwoPucksStrategy addPucksStrategy = new AddTwoPucksStrategy(removeGameObject, gameObjectAdder, imageReader, soundReader, inputListener);
//
//    /** The extra paddle collision strategy. */
//    private bricker.brick_strategies.ExtraPaddleStrategy extraPaddleStrategy = new ExtraPaddleStrategy(
//            inputListener, imageReader, windowSize, addGameObject, removeGameObject);
//
//    /** The turbo ball collision strategy. */
//    private TurboBallStrategy turboBallStrategy = new TurboBallStrategy(removeGameObject, turboBall);
//
//    /** The add life collision strategy. */
//    private bricker.brick_strategies.AddLifeCollisionStrategy addLifeStrategy = new AddLifeCollisionStrategy(addLifeCommand, removeGameObject, imageReader);
//
//    /**
//     * The strategies to choose from, with a weighted balance.
//     * The last one being a <code>DualBehaviorCollisionStrategy.</code>
//     */
//    private CollisionStrategy[] strategies = new CollisionStrategy[] {
//            basicStrategy,
//            basicStrategy,
//            basicStrategy,
//            basicStrategy,
//            basicStrategy,
//            addPucksStrategy,
//            extraPaddleStrategy,
//            turboBallStrategy,
//            addLifeStrategy,
//            null
//    };
//
//    public CollisionStrategyGeneratorLevel2() {
//        super();
//    }
//
//    /**
//     * Generates a random collision strategy.
//     *
//     * @return the collision strategy
//     */
//    @Override
//    public CollisionStrategy generateStrategy() {
//        Random rand = new Random();
//        int index = rand.nextInt(strategies.length);
//        CollisionStrategy strategy;
//        if (index == strategies.length - 1) {
//            // If the randomly chosen index is for DualBehaviorCollisionStrategy
//            strategy = new DualBehaviorCollisionStrategy(
//                    MAX_ACTUAL_BEHAVIORS_AMOUNT,
//                    addPucksStrategy, extraPaddleStrategy, turboBallStrategy, addLifeStrategy);
//        } else {
//            strategy = strategies[index];
//        }
//        // Print the generated strategy instead of using Logger
//        System.out.println("Generated strategy: " + strategy.getClass().getSimpleName());
//        return strategy;
//    }
//
//}

package brick_strategies;

import bricker.brick_strategies.AddLifeCollisionStrategy;
import bricker.brick_strategies.ExtraPaddleStrategy;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import utils.AddGameObject;
import utils.RemoveGameObject;
import gameobjects.TurboBall;
import utils.AddLife;

import java.util.Random;

/**
 * A collision strategy generator for level 2 of the game.
 */
public class CollisionStrategyGeneratorLevel2 implements CollisionStrategy {

    /** The maximum amount of actual behaviors to combine. */
    private static final int MAX_ACTUAL_BEHAVIORS_AMOUNT = 3;

    private final UserInputListener inputListener;
    private final ImageReader imageReader;
    private final Vector2 windowSize;
    private final AddGameObject addGameObject;
    private final RemoveGameObject removeGameObject;
    private final AddGameObject gameObjectAdder;
    private final SoundReader soundReader;
    private final TurboBall turboBall;
    private final AddLife addLifeCommand;

    /** The basic collision strategy. */
    private final BasicCollisionStrategy basicStrategy;
    /** The add pucks collision strategy. */
    private final AddTwoPucksStrategy addPucksStrategy;
    /** The extra paddle collision strategy. */
    private final ExtraPaddleStrategy extraPaddleStrategy;
    /** The turbo ball collision strategy. */
    private final TurboBallStrategy turboBallStrategy;
    /** The add life collision strategy. */
    private final AddLifeCollisionStrategy addLifeStrategy;

    /** The strategies to choose from, with a weighted balance. */
    private final CollisionStrategy[] strategies;

    /**
     * Constructs a new CollisionStrategyGeneratorLevel2.
     *
     * @param inputListener    Input listener for game interactions.
     * @param imageReader      Image reader for loading textures.
     * @param windowSize       The size of the game window.
     * @param addGameObject    Utility to add game objects dynamically.
     * @param removeGameObject Utility to remove game objects dynamically.
     * @param soundReader      Sound reader for audio effects.
     * @param turboBall        The turbo ball object for the game.
     * @param addLifeCommand   Utility to handle adding lives.
     */
    public CollisionStrategyGeneratorLevel2(UserInputListener inputListener, ImageReader imageReader,
                                            Vector2 windowSize, AddGameObject addGameObject,
                                            RemoveGameObject removeGameObject, SoundReader soundReader,
                                            TurboBall turboBall, AddLife addLifeCommand) {
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.windowSize = windowSize;
        this.addGameObject = addGameObject;
        this.removeGameObject = removeGameObject;
        this.gameObjectAdder = addGameObject;
        this.soundReader = soundReader;
        this.turboBall = turboBall;
        this.addLifeCommand = addLifeCommand;

        // Initialize strategies
        this.basicStrategy = new BasicCollisionStrategy(removeGameObject);
        this.addPucksStrategy = new AddTwoPucksStrategy(removeGameObject, gameObjectAdder, imageReader, soundReader, inputListener);
        this.extraPaddleStrategy = new ExtraPaddleStrategy(inputListener, imageReader, windowSize, addGameObject, removeGameObject);
        this.turboBallStrategy = new TurboBallStrategy(removeGameObject, turboBall);
        this.addLifeStrategy = new AddLifeCollisionStrategy(addLifeCommand, removeGameObject, imageReader);

        // Strategy pool
        this.strategies = new CollisionStrategy[] {
                basicStrategy,
                basicStrategy,
                basicStrategy,
                basicStrategy,
                basicStrategy,
                addPucksStrategy,
                extraPaddleStrategy,
                turboBallStrategy,
                addLifeStrategy,
                null // Placeholder for DualBehaviorCollisionStrategy
        };
    }

    /**
     * Generates a random collision strategy.
     *
     * @return the collision strategy
     */
    public CollisionStrategy generateStrategy() {
        Random rand = new Random();
        int index = rand.nextInt(strategies.length);
        CollisionStrategy strategy;
        if (index == strategies.length - 1) {
            // If the randomly chosen index is for DualBehaviorCollisionStrategy
            strategy = new DualBehaviorCollisionStrategy(
                    MAX_ACTUAL_BEHAVIORS_AMOUNT,
                    addPucksStrategy, extraPaddleStrategy, turboBallStrategy, addLifeStrategy);
        } else {
            strategy = strategies[index];
        }
        // Print the generated strategy
        System.out.println("Generated strategy: " + strategy.getClass().getSimpleName());
        return strategy;
    }

    /**
     * An action to perform when a collision occurs.
     *
     * @param thisObject  The object that has this strategy.
     * @param otherObject The object that collided with this object.
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        // Generate a random strategy and execute its collision behavior
        CollisionStrategy strategy = generateStrategy();
        strategy.onCollision(thisObject, otherObject);
    }
}

