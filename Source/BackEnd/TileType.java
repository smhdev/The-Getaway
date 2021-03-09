package BackEnd;

/**
 * This enum class stores the different tile types that a tile might be.
 *
 * @author Christian Sanger
 * @version 1.0
 */

public enum TileType {
    /**
     * A corner floor tile.
     */
    CORNER,
    /**
     * A straight floor tile.
     */
    STRAIGHT,
    /**
     * A t-shape junction floor tile.
     */
    T_SHAPE,
    /**
     * A fire action tile.
     */
    FIRE,
    /**
     * An ice action tile.
     */
    FROZEN,
    /**
     * A backtrack action tile.
     */
    BACKTRACK,
    /**
     * A double move action tile.
     */
    DOUBLE_MOVE,
    /**
     * A goal floor tile.
     */
    GOAL
}
