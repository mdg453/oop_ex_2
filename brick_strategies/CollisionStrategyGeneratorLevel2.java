package brick_strategies;

import bricker.brick_strategies.ExtraPaddleStrategy;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import gameobjects.TurboBall;
import utils.AddGameObject;
import utils.RemoveGameObject;
import utils.AddLife;

import java.util.Random;

public class CollisionStrategyGeneratorLevel2 implements CollisionStrategyGenerator {

    private static final int MAX_ACTUAL_BEHAVIORS_AMOUNT = 3;

    private final UserInputListener inputListener;
    private final ImageReader imageReader;
    private final Vector2 windowSize;
    private final AddGameObject addGameObject;
    private final RemoveGameObject removeGameObject;
    private final SoundReader soundReader;
    private final TurboBall turboBall;
    private final AddLife addLifeCommand;

    private final BasicCollisionStrategy basicStrategy;
    private final AddTwoPucksStrategy addPucksStrategy;
    private final ExtraPaddleStrategy extraPaddleStrategy;
    private final TurboBallStrategy turboBallStrategy;
    private final AddLifeCollisionStrategy addLifeStrategy;

    private final CollisionStrategy[] strategies;

    public CollisionStrategyGeneratorLevel2(
            UserInputListener inputListener,
            ImageReader imageReader,
            Vector2 windowSize,
            AddGameObject addGameObject,
            RemoveGameObject removeGameObject,
            SoundReader soundReader,
            TurboBall turboBall,
            AddLife addLifeCommand) {

        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.windowSize = windowSize;
        this.addGameObject = addGameObject;
        this.removeGameObject = removeGameObject;
        this.soundReader = soundReader;
        this.turboBall = turboBall;
        this.addLifeCommand = addLifeCommand;

        // Initialize the strategies using the provided utilities
        this.basicStrategy = new BasicCollisionStrategy(removeGameObject);
        this.addPucksStrategy = new AddTwoPucksStrategy(
                removeGameObject,
                addGameObject,
                imageReader,
                soundReader,
                inputListener,
                windowSize
        );
        this.extraPaddleStrategy = new ExtraPaddleStrategy(
                inputListener,
                imageReader,
                windowSize,
                addGameObject,
                removeGameObject
        );
        this.turboBallStrategy = new TurboBallStrategy(removeGameObject, turboBall);
        this.addLifeStrategy = new AddLifeCollisionStrategy(
                addLifeCommand,
                removeGameObject,
                imageReader,
                addGameObject
        );

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
                null // for DualBehaviorCollisionStrategy
        };
    }

    @Override
    public CollisionStrategy generateStrategy() {
        Random rand = new Random();
        int index = rand.nextInt(strategies.length);
        CollisionStrategy strategy = strategies[index];
        if (strategy == null) {
            strategy = new DualBehaviorCollisionStrategy(
                    MAX_ACTUAL_BEHAVIORS_AMOUNT,
                    addPucksStrategy,
                    extraPaddleStrategy,
                    turboBallStrategy,
                    addLifeStrategy
            );
        }
        System.out.println("Generated strategy: " + strategy.getClass().getSimpleName());
        return strategy;
    }
}
    //    public CollisionStrategyGeneratorLevel2(
//            UserInputListener inputListener,
//            ImageReader imageReader,
//            Vector2 windowSize,
//            AddGameObject addGameObject,
//            RemoveGameObject removeGameObject,
//            SoundReader soundReader,
//            TurboBall turboBall,
//            AddLife addLifeCommand) {
//
//        this.inputListener = inputListener;
//        this.imageReader = imageReader;
//        this.windowSize = windowSize;
//        this.addGameObject = addGameObject;
//        this.removeGameObject = removeGameObject;
//        this.soundReader = soundReader;
//        this.turboBall = turboBall;
//        this.addLifeCommand = addLifeCommand;
//
//        // Initialize the strategies using the provided utilities
//        this.basicStrategy = new BasicCollisionStrategy(removeGameObject);
//        this.addPucksStrategy = new AddTwoPucksStrategy(removeGameObject, addGameObject, imageReader, soundReader, inputListener);
//        this.extraPaddleStrategy = new ExtraPaddleStrategy(inputListener, imageReader, windowSize, addGameObject, removeGameObject);
//        this.turboBallStrategy = new TurboBallStrategy(removeGameObject, turboBall);
//        this.addLifeStrategy = new AddLifeCollisionStrategy(addLifeCommand, removeGameObject, imageReader);
//
//        this.strategies = new CollisionStrategy[] {
//                basicStrategy,
//                basicStrategy,
//                basicStrategy,
//                basicStrategy,
//                basicStrategy,
//                addPucksStrategy,
//                extraPaddleStrategy,
//                turboBallStrategy,
//                addLifeStrategy,
//                null // for DualBehaviorCollisionStrategy
//        };
//    }
    //    @Override
//    public CollisionStrategy generateStrategy() {
//        Random rand = new Random();
//        int index = rand.nextInt(strategies.length);
//        CollisionStrategy strategy;
//        if (index == strategies.length - 1) {
//            // DualBehaviorCollisionStrategy chosen
//            strategy = new DualBehaviorCollisionStrategy(
//                    MAX_ACTUAL_BEHAVIORS_AMOUNT,
//                    addPucksStrategy, extraPaddleStrategy, turboBallStrategy, addLifeStrategy);
//        } else {
//            strategy = strategies[index];
//        }
//        System.out.println("Generated strategy: " + strategy.getClass().getSimpleName());
//        return strategy;
//    }

//    @Override
//    public CollisionStrategy generateStrategy() {
//        Random rand = new Random();
//        int index = rand.nextInt(strategies.length);
//        CollisionStrategy strategy = strategies[index];
//        if (strategy == null) {
//             strategy = new DualBehaviorCollisionStrategy(
//                     MAX_ACTUAL_BEHAVIORS_AMOUNT,
//                     addPucksStrategy, extraPaddleStrategy, turboBallStrategy, addLifeStrategy
//        );
//    }
//    System.out.println("Generated strategy: " + strategy.getClass().getSimpleName());
//    return strategy;
//}
//
//}

