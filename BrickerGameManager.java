import java.awt.Color;
import java.awt.event.KeyEvent;

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
import gameobjects.Lives;
import utils.AddGameObject;
import utils.RemoveGameObject;
import brick_strategies.BasicCollisionStrategy;
import brick_strategies.CollisionStrategyGenerator;

public class BrickerGameManager extends GameManager {

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
    private static final int INITIAL_LIVES = 4;

    // Fields
    private Vector2 windowDims;
    private WindowController windowController;
    private Ball ball;
    private int bricks;
    private int playerLives;
    private UserInputListener inputListener;
    private boolean gameOver = false;

    // Utility interfaces
    private AddGameObject addGameObjectCommand;
    private RemoveGameObject removeGameObjectCommand;

    // Constructor
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        // Initialize the number of lives once, so it persists between resets
        playerLives = INITIAL_LIVES;
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.windowDims = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.inputListener = inputListener;
        windowController.setTargetFramerate(80);

        // Initialize add and remove commands
        addGameObjectCommand = (gameObject, layer) -> gameObjects().addGameObject(gameObject, layer);
        removeGameObjectCommand = (gameObject, layer) -> gameObjects().removeGameObject(gameObject, layer);

        // Add background
        initializeBackground(imageReader);

        // Create game objects: walls, ball, paddle, bricks
        createWalls();
        createBalls(imageReader, soundReader, inputListener);
        createPaddle(imageReader, inputListener);

        CollisionStrategyGenerator strategyGenerator = () -> new BasicCollisionStrategy(removeGameObjectCommand);
        createBricks(imageReader, strategyGenerator);

        // Add Lives UI
        initializeLivesUI(imageReader);
    }

    private void createWalls() {
        Renderable wallImage = new RectangleRenderable(Color.GRAY);

        GameObject[] walls = new GameObject[] {
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
        int bricksAreaHeight = (int) ((windowDims.y() - 2 * BRICKS_MARGIN) * MAX_BRICKS_AREA_HEIGHT_RATIO);
        int bricksPerRow = DEFAULT_BRICKS_PER_ROW;
        int bricksPerColumn = DEFAULT_BRICKS_PER_COLUMN;

        Vector2 brickSize = new Vector2(
                (windowDims.x() - 2 * BRICKS_MARGIN - (bricksPerRow - 1) * BRICKS_GAP) / bricksPerRow,
                Math.min(MAX_BRICK_HEIGHT,
                        (bricksAreaHeight - (bricksPerColumn - 1) * BRICKS_GAP) / bricksPerColumn)
        );

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

    private void initializeBackground(ImageReader imageReader) {
        Renderable backgroundImage = imageReader.readImage(BACKGROUND_IMG_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDims, backgroundImage);
        addGameObjectCommand.add(background, Layer.BACKGROUND);
    }

    private void initializeLivesUI(ImageReader imageReader) {
        Lives lives = new Lives(
                new Vector2(10, 10),
                () -> playerLives,
                imageReader,
                (gameObject, layer) -> gameObjects().addGameObject(gameObject, layer),
                (gameObject, layer) -> gameObjects().removeGameObject(gameObject, layer)
        );
        addGameObjectCommand.add(lives, Layer.UI);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Optional: Press W to force a win
        if (inputListener.isKeyPressed(KeyEvent.VK_W) && !gameOver) {
            winGame();
            gameOver = true;
        }

        checkForEndGame();
    }

    protected void winGame() {
        if (!windowController.openYesNoDialog(WIN_MSG)) {
            windowController.closeWindow();
        }
        // When winning, reset the entire game including lives.
        playerLives = INITIAL_LIVES;
        windowController.resetGame();
    }

    protected void loseGame() {
        // This is called when no lives remain
        if (!windowController.openYesNoDialog(LOSE_MSG)) {
            windowController.closeWindow();
        }
        // Reset the entire game, including restoring full lives.
        playerLives = INITIAL_LIVES;
        windowController.resetGame();
    }

    private void checkForEndGame() {
        // If the ball passes below the window, we lose a life
        if (ball.getCenter().y() > windowDims.y()) {
            playerLives--;
            if (playerLives <= 0) {
                loseGame();
            } else {
                // If lives remain, reset the game board but keep the current lives count
                windowController.resetGame();
            }
        }
    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker Game", DEFAULT_WINDOW_SIZE).run();
    }
}
