package utils;

/**
 * A concrete implementation of AddLife that accepts a Runnable
 * to perform the actual increment operation.
 */
public class AddLifeImpl implements AddLife {
    private final Runnable incrementLife;

    /**
     * Constructs a new AddLifeImpl.
     * @param incrementLife A Runnable that increments the player's life count.
     */
    public AddLifeImpl(Runnable incrementLife) {
        this.incrementLife = incrementLife;
    }

    @Override
    public void addLife() {
        incrementLife.run();
    }
}
