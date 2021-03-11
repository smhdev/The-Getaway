package BackEnd;

/**
 * This class stores the enum rotations of the floor tiles.
 *
 * @author George Sanger
 * @version 1.0
 */
public enum Rotation {
    /**
     * Rotation value of 0&deg;.
     */
    UP,
    /**
     * Rotation value of 90&deg;.
     */
    RIGHT,
    /**
     * Rotation value of 180&deg;.
     */
    DOWN,
    /**
     * Rotation value of 270&deg;.
     */
    LEFT;

    /**
     * Converts this Rotation to a double representing
     * the number of degrees clockwise from UP this rotation is.
     * @return The degree value of this Rotation.
     */
    public double degrees() {
        switch (this) {
            case UP:
                return 0;
            case RIGHT:
                return 90;
            case DOWN:
                return 180;
            case LEFT:
                return 270;
            default:
                throw new IllegalStateException("Unknown rotation " + this);
        }
    }

    /**
     * Gets the direction achieved by rotating 90 degrees clockwise.
     * @return The Rotation value that is 90&deg; clockwise from this one.
     */
    public Rotation clockwise() {
        switch (this) {
            case UP:
                return RIGHT;
            case RIGHT:
                return DOWN;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            default:
                throw new IllegalStateException("Unknown rotation " + this);
        }
    }
}

