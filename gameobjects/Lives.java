package gameobjects;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import utils.AddGameObject;
import utils.RemoveGameObject;

import java.awt.Color;
import java.util.function.Supplier;

public class Lives extends GameObject {

    public static final String HEART_IMAGE_PATH = "assets/heart.png";
    private static final int HEART_GAP = -4;
    public static final int HEART_HEIGHT = 20;
    private final Supplier<Integer> livesSupplier;
    private final AddGameObject addGameObject;
    private final RemoveGameObject removeGameObject;
    private final TextRenderable textRenderable;
    private final GameObject textObject;
    private final ImageRenderable heartImage;
    private GameObject[] hearts = new GameObject[0];

    /**
     * Construct a new Lives instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     * @param livesSupplier    Supplier to get the current number of lives.
     * @param imageReader      For loading the heart image.
     * @param addGameObject    Utility to add new game objects to the game.
     * @param removeGameObject Utility to remove game objects from the game.
     */
    public Lives(
            Vector2 topLeftCorner,
            Supplier<Integer> livesSupplier,
            ImageReader imageReader,
            AddGameObject addGameObject,
            RemoveGameObject removeGameObject) {
        super(topLeftCorner, new Vector2(HEART_HEIGHT, HEART_HEIGHT), null);
        this.livesSupplier = livesSupplier;
        this.addGameObject = addGameObject;
        this.removeGameObject = removeGameObject;

        heartImage = imageReader.readImage(HEART_IMAGE_PATH, true);
        textRenderable = new TextRenderable("", "Comic Sans MS");
        textObject = new GameObject(topLeftCorner, Vector2.ZERO, textRenderable);

        addGameObject.add(textObject, Layer.UI);
        updateLives();
    }

    /**
     * Updates the text displaying the number of lives.
     *
     * @param lives The current number of lives.
     * @return The width of the updated text.
     */
    private int updateText(int lives) {
        String text = String.valueOf(lives);
        if (lives >= 3) {
            textRenderable.setColor(Color.GREEN);
        } else if (lives == 2) {
            textRenderable.setColor(Color.YELLOW);
        } else {
            textRenderable.setColor(Color.RED);
        }
        textRenderable.setString(text);

        int textWidth = text.length() * HEART_HEIGHT + HEART_GAP;
        textObject.setDimensions(new Vector2(textWidth, HEART_HEIGHT));
        textObject.setTopLeftCorner(getTopLeftCorner());
        return textWidth;
    }

    /**
     * Updates the heart icons based on the current number of lives.
     *
     * @param lives     The current number of lives.
     * @param textWidth The width of the text displaying the number of lives.
     */
    private void updateHearts(int lives, int textWidth) {
        // Remove existing hearts
        for (GameObject heart : hearts) {
            removeGameObject.remove(heart, Layer.UI);
        }

        // Add new hearts
        hearts = new GameObject[lives];
        for (int i = 0; i < lives; i++) {
            Vector2 heartPosition = new Vector2(
                    getTopLeftCorner().x() + textWidth + i * (HEART_HEIGHT + HEART_GAP),
                    getTopLeftCorner().y());
            GameObject heart = new GameObject(
                    heartPosition,
                    new Vector2(HEART_HEIGHT, HEART_HEIGHT),
                    heartImage);
            addGameObject.add(heart, Layer.UI);
            hearts[i] = heart;
        }
    }

    /**
     * Updates the lives display (text and hearts).
     */
    public void updateLives() {
        int lives = livesSupplier.get();
        if (lives <= 0) {
            return;
        }
        int textWidth = updateText(lives);
        updateHearts(lives, textWidth);
    }
}
