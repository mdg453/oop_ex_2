package gameobjects;

import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;
import danogl.GameObject;


public class TurboBall extends Ball {
    private static final float TURBO_SPEED_FACTOR = 1.4f;
    private static final int COLLISIONS_BEFORE_RESET = 6;
    private static final String TURBO_BALL_IMAGE_PATH = "assets/redball.png";

    private final Renderable normalRenderable;
    private final Renderable turboRenderable;
    private boolean isTurboModeActive;
    private int turboCollisionCounter;

    public TurboBall(Vector2 windowDimensions, ImageReader imageReader, SoundReader soundReader,
                     UserInputListener inputListener) {
        super(windowDimensions, imageReader, soundReader, inputListener);
        this.normalRenderable = this.renderer().getRenderable();
        this.turboRenderable = imageReader.readImage(TURBO_BALL_IMAGE_PATH, true);
        this.isTurboModeActive = false;
        this.turboCollisionCounter = 0;
    }

//    public void activateTurboMode() {
//        if (isTurboModeActive) return;
//
//        isTurboModeActive = true;
//        turboCollisionCounter = 0;
//        setVelocity(getVelocity().mult(TURBO_SPEED_FACTOR));
//        renderer().setRenderable(turboRenderable);
//    }

    public void activateTurboMode() {
        if (isTurboModeActive) return;

        isTurboModeActive = true;
        turboCollisionCounter = 0;
        setVelocity(getVelocity().mult(TURBO_SPEED_FACTOR));
        renderer().setRenderable(turboRenderable);
    }

    private void deactivateTurboMode() {
        isTurboModeActive = false;
        setVelocity(getVelocity().mult(1 / TURBO_SPEED_FACTOR));
        renderer().setRenderable(normalRenderable);
    }
    @Override
    public void onCollisionEnter(GameObject other, danogl.collisions.Collision collision) {
        super.onCollisionEnter(other, collision);

        if (isTurboModeActive) {
            turboCollisionCounter++;
            if (turboCollisionCounter >= COLLISIONS_BEFORE_RESET) {
                deactivateTurboMode();
            }
        }
    }

//    private void deactivateTurboMode() {
//        isTurboModeActive = false;
//        setVelocity(getVelocity().mult(1 / TURBO_SPEED_FACTOR));
//
//        renderer().setRenderable(normalRenderable);
//    }

    public boolean isTurboModeActive() {
        return isTurboModeActive;
    }
}
