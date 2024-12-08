package gameobjects;
import danogl.gui.rendering.RectangleRenderable;
import java.awt.Color;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class BottomWall extends GameObject {

    private static final int BOTTOM_WALL_WIDTH = 40;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public BottomWall(Vector2 windowDimensions) {
        super(new Vector2(0, windowDimensions.y() - BOTTOM_WALL_WIDTH),
                new Vector2(windowDimensions.x(), BOTTOM_WALL_WIDTH),
                new RectangleRenderable(Color.BLACK));
    }

    public static int getBottomWallWidth() {
        return BOTTOM_WALL_WIDTH;
    }
}
