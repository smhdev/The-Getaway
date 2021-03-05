package FrontEnd;
;
import BackEnd.CustomBoard;
import BackEnd.FloorTile;
import BackEnd.GameboardEditor;
import BackEnd.TileType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;

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
    private RadioButton straightRB;
    @FXML
    private RadioButton tshapeRB;
    @FXML
    private RadioButton cornerRB;
    @FXML
    private RadioButton goalRB;
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
                        ImageView tileImg = createTileImageView(tileName, tileSize);
                        pane.getChildren().add(tileImg);

                        if (currentTile.isFixed()) {
                            // Add the locked icon on top
                            ImageView lockedImg = createTileImageView("fixed", tileSize);
                            pane.getChildren().add(lockedImg);
                        }

                    } else {
                        // No tile exists; create an image view with an empty tile
                        pane.getChildren().add(createTileImageView("empty", tileSize));
                    }
                    // Add the pane containing the images to the board grid pane
                    // at the given column and row
                    boardGridPane.add(pane, x, y);
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
     * Creates an ImageView for use as a tile on the game board.
     * @param name The name of the image to use.
     * @param size The fit width and height of the ImageView.
     * @return An ImageView with the tile's image
     *         and its fit size set to the provided size.
     */
    public ImageView createTileImageView(String name, int size) {
        ImageView tileImg = new ImageView(Assets.get(name));
        tileImg.setFitWidth(size);
        tileImg.setFitHeight(size);
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


    public void onStraightRB() {
        straightRB.setSelected(true);
        checkVisRestPlayerButton();
    }
    public void onTshapeRB() {
        tshapeRB.setSelected(true);
        checkVisRestPlayerButton();
    }
    public void onCornerRB() {
        cornerRB.setSelected(true);
        checkVisRestPlayerButton();
    }
    public void onGoalRB() {
        goalRB.setSelected(true);
        checkVisRestPlayerButton();
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
        WindowLoader wl = new WindowLoader(resetPlayerPositionButton);
        wl.load("MenuScreen", getInitData());
    }

}
