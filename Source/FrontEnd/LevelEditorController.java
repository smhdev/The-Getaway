package FrontEnd;
;
import BackEnd.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.util.Pair;

import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 *
 *
 * @version 1.0
 * @since 1.0
 * @author Liam B
 */
public class LevelEditorController extends StateLoad {

    @FXML
    private Slider straightSlider;
    @FXML
    private TextField straightInBox;
    @FXML
    private Slider goalSlider;
    @FXML
    private TextField goalInBox;
    @FXML
    private Slider tshapeSlider;
    @FXML
    private TextField tshapeInBox;
    @FXML
    private Slider conerSlider;
    @FXML
    private TextField cornerInBox;
    @FXML
    private Slider fireSlider;
    @FXML
    private TextField fireInBox;
    @FXML
    private Slider iceSlider;
    @FXML
    private TextField iceInBox;
    @FXML
    private Slider doublemoveSlider;
    @FXML
    private TextField doublemoveInBox;
    @FXML
    private Slider backtrackSlider;
    @FXML
    private TextField backtrackInBox;


    @FXML
    private ImageView straightImage;
    @FXML
    private ImageView goalImage;
    @FXML
    private ImageView cornerImage;
    @FXML
    private ImageView tshapeImage;
    @FXML
    private RadioButton fixRB;
    @FXML
    private RadioButton rotateRB;
    @FXML
    private RadioButton removeRB;
    @FXML
    private RadioButton p1RB;
    @FXML
    private RadioButton p2RB;
    @FXML
    private RadioButton p3RB;
    @FXML
    private RadioButton p4RB;
    @FXML
    private ToggleGroup floorActionPlayerSet;
    //used to select what to place on board
    //System.out.println(floorActionPlayerSet.getSelectedToggle().toString());

    @FXML
    private MenuItem exitButton;
    @FXML
    private MenuItem saveExitButton;
    @FXML
    private RadioMenuItem silkBagToggleButton;

    @FXML
    private Button resetPlayerPositionButton;
    private boolean silkBag = true; //True for silk bag or false to remove option

    @FXML
    private GridPane boardGridPane;

    private GameboardEditor editor;
    private CustomBoard customBoard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize is called twice, once automatically by JavaFX and again
        // in WindowLoader.load (line 59). The second time around getInitData
        // will actually return a HashMap. Hence we must check to see
        // if getInitData isn't null before we can retrieve data from it.
        if (getInitData() != null) {
            String customBoardName = getInitData().get("Custom Board Name");
            customBoard = GameboardEditor.loadFile("./Gameboards/" + customBoardName + ".txt");
            editor = new GameboardEditor(customBoard);
            // Bunch of calculations to work out the proper size of the tile
            int screenWidth = (int) Screen.getPrimary().getBounds().getHeight();
            int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
            int boardWidth = customBoard.getXSize();
            int boardHeight = customBoard.getYSize();
            int maxTileWidth = screenWidth / (boardWidth + 9);
            int maxTileHeight = screenHeight / (boardHeight + 9);
            int tileSize = Math.min(maxTileHeight, maxTileWidth);

            // Loop through the rows of the board
            for (int y = 0; y < boardHeight; y++) {
                // Add row constraints to this row
                boardGridPane.getRowConstraints().add(new RowConstraints(tileSize, tileSize, tileSize));
                // Loop through the columns of the row
                for (int x = 0; x < boardWidth; x++) {
                    // Add column constraints to this column
                    boardGridPane.getColumnConstraints().add(new ColumnConstraints(tileSize, tileSize, tileSize));
                    FloorTile currentTile = customBoard.getTileAt(x, y);
                    StackPane pane = new StackPane();

                    if (currentTile != null) {
                        // A tile exists here; create an image view containing the tile's picture
                        String tileName = currentTile.getType().name().toLowerCase();
                        ImageView tileImg = createTileImageView(tileName, tileSize, currentTile.getRotation());
                        tileImg.setUserData("TileImage " + tileName);
                        pane.getChildren().add(tileImg);

                        if (currentTile.isFixed()) {
                            // Add the locked icon on top
                            toggleFixedImage(pane, tileSize);
                        }

                    } else {
                        // No tile exists; create an image view with an empty tile
                        ImageView emptyImage = createTileImageView("empty", tileSize);
                        emptyImage.setUserData("EmptyImage");
                        pane.getChildren().add(emptyImage);
                    }
                    pane.setUserData(new Pair<Integer, Integer>(x, y));
                    // Add the pane containing the images to the board grid pane
                    // at the given column and row
                    boardGridPane.add(pane, x, y);

                    // Add an event handler when something gets dragged on top of the pane
                    pane.setOnDragOver((DragEvent event) -> {
                        if (event.getGestureSource() != pane && event.getDragboard().hasString()) {
                            String dbContent = event.getDragboard().getString();
                            // Make sure a tile is being dragged onto this pane
                            if (dbContent.equals("straight") || dbContent.equals("t_shape") ||
                                    dbContent.equals("corner") || dbContent.equals("goal")) {
                                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                            }
                        }
                        event.consume();
                    });

                    // Lambda expressions require variables to be final, so copy the
                    // coordinates to final variables before reading them
                    final int finalX = x;
                    final int finalY = y;
                    final Coordinate coordinates = new Coordinate(x, y);

                    // Add an event handler when something gets dropped on top of the pane
                    pane.setOnDragDropped((DragEvent event) -> {
                        Dragboard db = event.getDragboard();
                        if (db.hasString()) {
                            // Get the name of the new tile
                            String newTileName = db.getString();
                            // Remove all other images and add this new tile to the pane
                            swapOutTileImage(pane, newTileName, tileSize, Rotation.UP);

                            // Stick it in the board editor
                            TileType tileType;
                            switch (newTileName) {
                                case "straight":
                                    tileType = TileType.STRAIGHT;
                                    break;
                                case "t_shape":
                                    tileType = TileType.T_SHAPE;
                                    break;
                                case "corner":
                                    tileType = TileType.CORNER;
                                    break;
                                case "goal":
                                    tileType = TileType.GOAL;
                                    break;
                                default:
                                    throw new IllegalStateException("What the hell is a " +
                                            newTileName + "? Not a tile that's what.");
                            }

                            FloorTile tileToAdd = new FloorTile(tileType);
                            tileToAdd.setLocation(coordinates);
                            editor.putTile(tileToAdd);

                            System.out.printf("The tile at (%d, %d) is now a %s%n", finalX, finalY, newTileName);
                            event.setDropCompleted(true);
                        } else {
                            event.setDropCompleted(false);
                        }
                        event.consume();
                    });

                    // Add an event handler when the pane is clicked
                    pane.setOnMouseClicked((MouseEvent event) -> {

                        if (fixRB.isSelected()) {
                            // Fix or unfix this tile
                            FloorTile tile = customBoard.getTileAt(finalX, finalY);
                            if (tile != null) {
                                tile.setFixedBool(!tile.isFixed());
                                toggleFixedImage(pane, tileSize);
                                System.out.printf(
                                        tile.isFixed() ?
                                        "Tile (%d, %d) was fixed%n" :
                                        "Tile (%d, %d) was unfixed%n",
                                        finalX, finalY
                                );
                            }
                        } else if (rotateRB.isSelected()) {
                            // Rotate this tile
                            FloorTile tile = customBoard.getTileAt(finalX, finalY);
                            if (tile != null) {
                                tile.setRotation(tile.getRotation().clockwise());
                                String tileName = tile.getType().toString().toLowerCase();
                                swapOutTileImage(pane, tileName, tileSize, tile.getRotation());

                                System.out.printf("Tile (%d, %d) was rotated to %s%n",
                                        finalX, finalY, tile.getRotation()
                                );
                            }
                        } else if (removeRB.isSelected()) {
                            // Remove this tile
                            emptyTileImage(pane, tileSize);
                            editor.removeTileOnPosition(coordinates);
                            System.out.printf("Tile (%d, %d) was removed%n", finalX, finalY);
                        }
                    });
                }
            }
        }

        straightSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                straightInBox.setText(String.valueOf(Math.round((Double) newValue))));

        conerSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                cornerInBox.setText(String.valueOf(Math.round((Double) newValue))));

        tshapeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                tshapeInBox.setText(String.valueOf(Math.round((Double) newValue))));

        goalSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                goalInBox.setText(String.valueOf(Math.round((Double) newValue))));


        fireSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                fireInBox.setText(String.valueOf(Math.round((Double) newValue))));

        iceSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                iceInBox.setText(String.valueOf(Math.round((Double) newValue))));

        backtrackSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                backtrackInBox.setText(String.valueOf(Math.round((Double) newValue))));

        doublemoveSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                doublemoveInBox.setText(String.valueOf(Math.round((Double) newValue))));
    }

    /**
     * Helper function that swaps the ImageView containing a tile for a different one.
     * @param pane The pane containing tile ImageViews.
     * @param newTile The name of the new tile.
     * @param size The size of the new tile.
     * @param rotation The rotation of the new tile.
     */
    public void swapOutTileImage(Pane pane, String newTile, int size, Rotation rotation) {
        for (Node child : pane.getChildren()) {
            String userData = child.getUserData().toString();
            if (userData != null) {
                if (userData.startsWith("TileImage") || userData.startsWith("EmptyImage")) {
                    pane.getChildren().remove(child);
                    ImageView tileImageView = createTileImageView(newTile, size, rotation);
                    tileImageView.setUserData("TileImage " + newTile);
                    pane.getChildren().add(0, tileImageView);
                    break;
                }
            }
        }
    }

    /**
     * Helper function that swaps the ImageView containing a tile for an empty one.
     * @param pane The pane containing tile ImageViews.
     * @param size The size of the empty image.
     */
    public void emptyTileImage(Pane pane, int size) {
        for (Node child : pane.getChildren()) {
            String userData = child.getUserData().toString();
            if (userData != null) {
                if (userData.startsWith("TileImage") || userData.startsWith("EmptyImage")) {
                    pane.getChildren().remove(child);
                    ImageView emptyImageView = createTileImageView("empty", size);
                    emptyImageView.setUserData("EmptyImage");
                    pane.getChildren().add(emptyImageView);
                    break;
                }
            }
        }
        // Trying to use one for-each loop to remove the fixed icon will result in a
        // ConcurrentModificationException. Hence pane.getChildren() is iterated over twice.
        for (Node child : pane.getChildren()) {
            String userData = child.getUserData().toString();
            if (userData != null) {
                if (userData.startsWith("FixedImage") ) {
                    pane.getChildren().remove(child);
                    break;
                }
            }
        }
    }

    /**
     * Helper function that adds a fixed ImageView if it isn't there, or removes it if it is.
     * @param pane The pane containing a tile ImageView.
     * @param size The size of the locked image.
     */
    public void toggleFixedImage(Pane pane, int size) {
        for (Node child : pane.getChildren()) {
            String userData = child.getUserData().toString();
            if (userData != null) {
                if (userData.startsWith("FixedImage")) {
                    pane.getChildren().remove(child);
                    return;
                }
            }
        }
        // At this point, all of the children have been examined but none have the "FixedImage" userdata.
        // Therefore a fixed image must be added.
        ImageView lockedImg = createTileImageView("fixed", size);
        lockedImg.setUserData("FixedImage");
        pane.getChildren().add(lockedImg);
    }

    /**
     * Creates an ImageView for use as a tile on the game board.
     * @param name The name of the image to use.
     * @param size The fit width and height of the ImageView.
     * @return An ImageView with the tile's image
     *         and its fit size set to the provided size.
     */
    private ImageView createTileImageView(String name, int size) {
        return createTileImageView(name, size, Rotation.UP);
    }

    /**
     * Creates an ImageView for use as a tile on the game board with the specified rotation.
     * @param name The name of the image to use.
     * @param size The fit width and height of the ImageView.
     * @param rotation The orthogonal rotation of the tile.
     * @return An ImageView with the tile's image rotated to the specified direction
     *         and its fit size set to the provided size.
     */
    private ImageView createTileImageView(String name, int size, Rotation rotation) {
        ImageView tileImg = new ImageView(Assets.get(name));
        tileImg.setFitWidth(size);
        tileImg.setFitHeight(size);
        tileImg.setRotate(rotation.degrees());
        return tileImg;
    }

    /**
     *
     * @return HashMap
     */
    private HashMap getSilkBagData(){

        HashMap<TileType, Integer> silkBagMap = null;
        if (silkBag) {
            silkBagMap.put(TileType.STRAIGHT, Integer.valueOf(straightInBox.getText()));
            silkBagMap.put(TileType.CORNER, Integer.valueOf(cornerInBox.getText()));
            silkBagMap.put(TileType.T_SHAPE, Integer.valueOf(tshapeInBox.getText()));
            silkBagMap.put(TileType.GOAL, Integer.valueOf(goalInBox.getText()));
            silkBagMap.put(TileType.FIRE, Integer.valueOf(fireInBox.getText()));
            silkBagMap.put(TileType.FROZEN, Integer.valueOf(iceInBox.getText()));
            silkBagMap.put(TileType.BACKTRACK, Integer.valueOf(backtrackInBox.getText()));
            silkBagMap.put(TileType.DOUBLE_MOVE, Integer.valueOf(doublemoveInBox.getText()));
        }

        return silkBagMap;
    }

    /**
     * Helper method which starts a drag and drop event.
     * @param source The source of the dragging.
     * @param tileName The name of the tile being dragged.
     */
    private void startDragAndDrop(Node source, String tileName) {
        unselectActionRadioButtons();
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(tileName);
        db.setContent(content);
        System.out.println("Starting a drag event for " + content.getString());
    }

    public void onMouseDragStraightTile(MouseEvent event) {
        event.setDragDetect(true);
    }

    public void onDragStraightTile(MouseEvent event) {
        startDragAndDrop(straightImage, "straight");
    }

    public void onMouseDragTShapeTile(MouseEvent event) {
        event.setDragDetect(true);
    }

    public void onDragTShapeTile(MouseEvent event) {
        startDragAndDrop(tshapeImage, "t_shape");
    }

    public void onMouseDragCornerTile(MouseEvent event) {
        event.setDragDetect(true);
    }

    public void onDragCornerTile(MouseEvent event) {
        startDragAndDrop(cornerImage, "corner");
    }

    public void onMouseDragGoalTile(MouseEvent event) {
        event.setDragDetect(true);
    }

    public void onDragGoalTile(MouseEvent event) {
        startDragAndDrop(goalImage, "goal");
    }

    public void onFixRB() {
        fixRB.setSelected(true);
        checkVisRestPlayerButton();
    }
    public void onRotateRB() {
        rotateRB.setSelected(true);
        checkVisRestPlayerButton();
    }
    public void onRemoveRB() {
        removeRB.setSelected(true);
        checkVisRestPlayerButton();
    }

    public void onP1RB() {
        p1RB.setSelected(true);
        checkVisRestPlayerButton();
    }
    public void onP2RB() {
        p2RB.setSelected(true);
        checkVisRestPlayerButton();
    }
    public void onP3RB() {
        p3RB.setSelected(true);
        checkVisRestPlayerButton();
    }
    public void onP4RB() {
        p4RB.setSelected(true);
        checkVisRestPlayerButton();
    }

    private void checkVisRestPlayerButton() {
        switch (((RadioButton) floorActionPlayerSet.getSelectedToggle()).getId()) {
            case "p1RB":
            case "p2RB":
            case "p3RB":
            case "p4RB":
                resetPlayerPositionButton.setVisible(true);
                break;
            default:
                resetPlayerPositionButton.setVisible(false);
                break;
        }
    }

    public void unselectActionRadioButtons() {
        fixRB.setSelected(false);
        rotateRB.setSelected(false);
        removeRB.setSelected(false);
    }

    public void resetPlayerPosition() {
        switch (((RadioButton) floorActionPlayerSet.getSelectedToggle()).getId()) {
            case "p1RB":
            case "p2RB":
            case "p3RB":
            case "p4RB":
                //Rest Player
                break;
        }
    }

    //Menu Bar Controls
    public void onSilkBagToggle() {
        silkBag = !silkBag;

        silkBagToggleButton.setSelected(silkBag);
    }

    public void onAboutButton() {
        //Get the about page up
    }

    public void onExitButton() {
        WindowLoader wl = new WindowLoader(resetPlayerPositionButton);
        wl.load("MenuScreen", getInitData());
    }

    public void onSaveExitButton () {
        //Save Here
        CustomBoardSaveLoad.writeIniBoard("./Gameboards/" + getInitData().get("Custom Board Name") + ".txt", customBoard);
        WindowLoader wl = new WindowLoader(resetPlayerPositionButton);
        wl.load("MenuScreen", getInitData());
    }

}
