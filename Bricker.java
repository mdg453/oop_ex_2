import java.awt.Color;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.GameManager;
import gameobjects.Brick;
import gameobjects.Ball;
import gameobjects.Paddle;
import utils.AddGameObject;
import utils.RemoveGameObject;
import brick_strategies.BasicCollisionStrategy;
import brick_strategies.CollisionStrategy;
import brick_strategies.CollisionStrategyGenerator;

public class Bricker extends GameManager {

    // Constants
    private static final int WALL_WIDTH = 50;
    private static final String BACKGROUND_IMG_PATH = "assets/DARK_BG2_small.jpeg";
    private static final Vector2 DEFAULT_WINDOW_SIZE = new Vector2(700, 500);
    private static final int DEFAULT_BRICKS_PER_ROW = 8;
    private static final int DEFAULT_BRICKS_PER_COLUMN = 7;
    private static final int BRICKS_GAP = 5;
    private static final int MAX_BRICK_HEIGHT = 15;
    private static final int BRICKS_MARGIN = 10;

    private static final float MAX_BRICKS_AREA_HEIGHT_RATIO = 0.4f;
    private static final String LOSE_MSG = "You lose! Play again?";
    private static final String WIN_MSG = "You win! Play again?";
    private static final int DEFAULT_BALLS_NUMBER = 1;

    // Fields
    private Vector2 windowDims;
    private WindowController windowController;
    private Ball ball;
    private int bricks;

    // Utility interfaces
    private AddGameObject addGameObjectCommand;
    private RemoveGameObject removeGameObjectCommand;

    // Constructor
    public Bricker(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.windowDims = windowController.getWindowDimensions();
        this.windowController = windowController;
        windowController.setTargetFramerate(80);

        // Initialize add and remove commands
        addGameObjectCommand = (gameObject, layer) -> gameObjects().addGameObject(gameObject, layer);
        removeGameObjectCommand = (gameObject, layer) -> gameObjects().removeGameObject(gameObject, layer);

        // Add background
        Renderable backgroundImage = imageReader.readImage(BACKGROUND_IMG_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDims, backgroundImage);
        addGameObjectCommand.add(background, Layer.BACKGROUND);

        // Create game objects
        createWalls();
        createBalls(imageReader, soundReader, inputListener);
        createPaddle(imageReader, inputListener);

        // Create bricks
        CollisionStrategyGenerator strategyGenerator = () -> new BasicCollisionStrategy(removeGameObjectCommand);
        createBricks(imageReader, strategyGenerator);
    }

    private void createWalls() {
        Renderable wallImage = new RectangleRenderable(Color.GRAY);

        GameObject[] walls = new GameObject[]{
                new GameObject(new Vector2(0, -WALL_WIDTH),
                        new Vector2(windowDims.x(), WALL_WIDTH), wallImage), // Top wall
                new GameObject(new Vector2(-WALL_WIDTH, 0),
                        new Vector2(WALL_WIDTH, windowDims.y()), wallImage), // Left wall
                new GameObject(new Vector2(windowDims.x(), 0),
                        new Vector2(WALL_WIDTH, windowDims.y()), wallImage) // Right wall
        };

        addGameObjectCommand.addMultiple(Layer.STATIC_OBJECTS, walls);
    }

    private void createBalls(ImageReader imageReader, SoundReader soundReader,
                             UserInputListener inputListener) {
        for (int i = 0; i < DEFAULT_BALLS_NUMBER; i++) {
            ball = new Ball(windowDims, imageReader, soundReader, inputListener);
            addGameObjectCommand.add(ball);
        }
    }

    private void createPaddle(ImageReader imageReader, UserInputListener inputListener) {
        Paddle paddle = new Paddle(windowDims.x(), windowDims.y(), inputListener, imageReader);
        addGameObjectCommand.add(paddle);
    }

    private void createBricks(ImageReader imageReader, CollisionStrategyGenerator strategyGenerator) {
        // Area for the bricks
        int bricksAreaHeight = (int) ((windowDims.y() - 2 * BRICKS_MARGIN) * MAX_BRICKS_AREA_HEIGHT_RATIO);
        int bricksPerRow = DEFAULT_BRICKS_PER_ROW;
        int bricksPerColumn = DEFAULT_BRICKS_PER_COLUMN;

        // Calculate the size of each brick
        Vector2 brickSize = new Vector2(
                (windowDims.x() - 2 * BRICKS_MARGIN - (bricksPerRow - 1) * BRICKS_GAP) / bricksPerRow,
                Math.min(
                        MAX_BRICK_HEIGHT,
                        (bricksAreaHeight - (bricksPerColumn - 1) * BRICKS_GAP) / bricksPerColumn)
        );

        // Add bricks
        for (int row = 0; row < bricksPerColumn; row++) {
            for (int col = 0; col < bricksPerRow; col++) {
                Vector2 position = new Vector2(
                        BRICKS_MARGIN + col * (brickSize.x() + BRICKS_GAP),
                        BRICKS_MARGIN + row * (brickSize.y() + BRICKS_GAP)
                );
                Brick brick = new Brick(position, brickSize, imageReader, strategyGenerator);
                addGameObjectCommand.add(brick, Layer.STATIC_OBJECTS);
                bricks++;
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForEndGame();
    }

    private void checkForEndGame() {
        if (ball.getCenter().y() > windowDims.y()) {
            if (windowController.openYesNoDialog(LOSE_MSG)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    public static void main(String[] args) {
        new Bricker("Bricker Game", DEFAULT_WINDOW_SIZE).run();
    }
}





























//import java.awt.Color;
//
//
//import danogl.GameObject;
//import danogl.collisions.Layer;
//import danogl.gui.*;
//import danogl.gui.rendering.RectangleRenderable;
//import danogl.gui.rendering.Renderable;
//import danogl.util.Vector2;
//import danogl.GameManager;
//import gameobjects.Ball;
//import gameobjects.Brick;
//import gameobjects.Paddle;
//import utils.AddGameObject;
//import brick_strategies.CollisionStrategy;
//import brick_strategies.CollisionStrategyGenerator;
//import brick_strategies.BasicCollisionStrategy;
//
//public class Bricker extends GameManager {
//
//    // Constants
//    private static final int WALL_WIDTH = 50;
//    private static final String BACKGROUND_IMG_PATH = "C:/Users/meita/Documents/university/y4s1/oop/ex2/oop_ex_2/" +
//            "assets/assets/DARK_BG2_small.jpeg";
//            // "assets/DARK_BG2_small.jpeg";
//    private static final Vector2 DEFAULT_WINDOW_SIZE = new Vector2(700, 500);
//    private static final int DEFAULT_BRICKS_PER_ROW = 8;
//    private static final int DEFAULT_BRICKS_PER_COLUMN = 7;
//    private static final int BRICKS_GAP = 5;
//    private static final int MAX_BRICK_HEIGHT = 15;
//    private static final int BRICKS_MARGIN = 10;
//
//    private static final float MAX_BRICKS_AREA_HEIGHT_RATIO = 0.4f;
//    private static final String LOSE_MSG = "You lose! Play again?";
//    private static final String WIN_MSG = "You win! Play again?";
//    private static final int DEFAULT_BALLS_NUMBER = 1;
//
//
//
//    // Fields
//    private Vector2 windowDims;
//    private WindowController windowController;
//    private ImageReader imageReader;
//    private Ball ball;
//    private Paddle paddle; // Reference to Paddle class
//
//    /** The number of bricks remaining. */
//    private int bricks;
////    /** The number of lives remaining. */
////    private int lives;
//    /** The number of balls remaining. */
//    private int balls;
//
//    // Constructor
//    public Bricker(String windowTitle, Vector2 windowDimensions) {
//        super(windowTitle, windowDimensions);
//    }
//
//    private AddGameObject addGameObjectCommand = new AddGameObject() {
//        @Override
//        public void add(GameObject gameObject, int layer) {
//            addObjectsToGame(layer, gameObject);
//        }
//    };
//
//    private void addObjectsToGame(int layer, GameObject... objects) {
//        for (var obj : objects) {
//            this.gameObjects().addGameObject(obj, layer);
//            // Print information about the added object
//            System.out.printf("%s added to layer %d%n", obj.getClass().getSimpleName(), layer);
//        }
//    }
//
//    private void addObjectsToGame(GameObject... objects) {
//        addObjectsToGame(Layer.DEFAULT, objects);
//    }
//
//
//    @Override
//    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
//                               UserInputListener inputListener, WindowController windowController) {
//        super.initializeGame(imageReader, soundReader, inputListener, windowController);
//
//        this.windowDims = windowController.getWindowDimensions();
//        this.windowController = windowController;
//        windowController.setTargetFramerate(80);
//
//        // Add background
//        Renderable backgroundImage = imageReader.readImage(BACKGROUND_IMG_PATH, false);
//        GameObject background = new GameObject(Vector2.ZERO, windowDims, backgroundImage);
//        gameObjects().addGameObject(background, Layer.BACKGROUND);
//
//        // Create game objects
//        createBalls(imageReader, soundReader, inputListener);
//        createPaddle(imageReader, inputListener);  // Using Paddle class now
//        createWalls();
//
//        // Create bricks with a specific collision strategy
//    //    CollisionStrategyGenerator strategyGenerator = new BasicCollisionStrategyGenerator();
//    //    createBricks(imageReader, strategyGenerator);
//    }
//
//
//    @Override
//    public void update(float deltaTime) {
//        super.update(deltaTime);
//        checkForEndGame();
//    }
//
//    private void checkForEndGame() {
//        double ballHeight = ball.getCenter().y();
//        String prompt = "";
//
//        // Determine win/lose conditions
//        if (ballHeight < 0) {
//            prompt = "You win!";
//        } else if (ballHeight > windowDims.y()) {
//            prompt = "You lose!";
//        }
//
//        if (!prompt.isEmpty()) {
//            prompt += " Play again?";
//            if (windowController.openYesNoDialog(prompt)) {
//                windowController.resetGame();
//            } else {
//                windowController.closeWindow();
//            }
//        }
//    }
//
//    private void createBalls(ImageReader imageReader, SoundReader soundReader,
//                             UserInputListener inputListener) {
//        // Use the Ball class constructor to create the ball.
//        for (int i = 0; i < DEFAULT_BALLS_NUMBER; i++){
//            ball = new Ball(windowDims, imageReader, soundReader, inputListener);
//            this.addObjectsToGame(ball);
//            balls++;
//        }
//
//    }
//
//    private void createPaddle(ImageReader imageReader, UserInputListener inputListener) {
//        paddle = new Paddle(windowDims.x(), windowDims.y(), inputListener, imageReader);
//        this.addObjectsToGame(paddle);
//    }
//
//    private void createWalls() {
//        Renderable wallImage = new RectangleRenderable(Color.GRAY);
//
//        // Create top, left, and right walls
//        GameObject[] walls = new GameObject[]{
//                new GameObject(new Vector2(0, -WALL_WIDTH),
//                        new Vector2(windowDims.x(), WALL_WIDTH), wallImage), // Top wall
//                new GameObject(new Vector2(-WALL_WIDTH, 0),
//                        new Vector2(WALL_WIDTH, windowDims.y()), wallImage), // Left wall
//                new GameObject(new Vector2(windowDims.x(), 0),
//                        new Vector2(WALL_WIDTH, windowDims.y()), wallImage) // Right wall
//        };
//
//        for (GameObject wall : walls) {
//            this.addObjectsToGame(Layer.STATIC_OBJECTS, walls);
//            gameObjects().addGameObject(wall, Layer.STATIC_OBJECTS);
//        }
//    }
//
//    private void createBricks(ImageReader imageReader, CollisionStrategyGenerator strategyGenerator) {
//        // area for the bricks
//        int bricksAreaHeight = (int) ((windowDims.y() - 2 * BRICKS_MARGIN) * MAX_BRICKS_AREA_HEIGHT_RATIO);
//        int bricksPerRow = DEFAULT_BRICKS_PER_ROW;
//        int bricksPerColumn = DEFAULT_BRICKS_PER_COLUMN;
//
//        // size of each brick calculation
//        Vector2 brickSize = new Vector2(
//                (windowDims.x() - 2 * BRICKS_MARGIN - (bricksPerRow - 1) * BRICKS_GAP) / bricksPerRow,
//                Math.min(
//                        MAX_BRICK_HEIGHT,
//                        (bricksAreaHeight - (bricksPerColumn - 1) * BRICKS_GAP) / bricksPerColumn)
//        );
//
//        bricks = bricksPerRow * bricksPerColumn;
//
//        for (int i = 0; i < bricksPerRow; i++) {
//            for (int j = 0; j < bricksPerColumn; j++) {
//                Vector2 brickPosition = new Vector2(
//                        BRICKS_MARGIN + i * (brickSize.x() + BRICKS_GAP),
//                        BRICKS_MARGIN + j * (brickSize.y() + BRICKS_GAP)
//                );
//                GameObject brick = new Brick(brickPosition, brickSize, imageReader, strategyGenerator);
//                this.addObjectsToGame(Layer.STATIC_OBJECTS, brick);
//            }
//        }
//    }
//
//
//    public static void main(String[] args) {
//        new Bricker("Bricker Game", new Vector2(700, 500)).run();
//    }
//}
