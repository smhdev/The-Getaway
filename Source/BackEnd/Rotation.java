package BackEnd;

/**
 * This class stores the enum rotations of the floor tiles.
 *
 * @author George Sanger
 * @version 1.0
 */
public enum Rotation {
    UP, RIGHT, DOWN, LEFT;

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
}

