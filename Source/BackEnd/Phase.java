package BackEnd;

/**
 * This enum class holds the phases that each player is in.
 *
 * @author Christian Sanger
 * @version 1.0
 */

public enum Phase {
    /**
     * A tile is being drawn.
     */
    DRAW,
    /**
     * A floor tile is being placed.
     */
    FLOOR,
    /**
     * An action tile is being used.
     */
    ACTION,
    /**
     * A player is moving.
     */
    MOVE,
    /**
     * A player has won.
     */
    WIN
}
