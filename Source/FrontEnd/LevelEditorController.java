package FrontEnd;
;
import BackEnd.CustomBoard;
import BackEnd.GameboardEditor;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Screen;

import java.net.URL;
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
    private ToggleGroup floorTiles;

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

    private GameboardEditor boardEditor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize is called twice, once automatically by JavaFX and again
        // in WindowLoader.load (line 59). The second time around getInitData
        // will actually return a HashMap. Hence we must check to see
        // if getInitData isn't null before we can retrieve data from it.
        if (getInitData() != null) {
            String customBoardName = getInitData().get("Custom Board Name");
            boardEditor = new GameboardEditor(customBoardName);
            int screenWidth = (int) Screen.getPrimary().getBounds().getHeight();
            int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
            int boardWidth = boardEditor.getBoard().getXSize();
            int boardHeight = boardEditor.getBoard().getYSize();
            int maxTileWidth = screenWidth / (boardWidth + 9);
            int maxTileHeight = screenHeight / (boardHeight + 9);
            int tileSize = Math.min(maxTileHeight, maxTileWidth);

            for (int i = 0; i < boardWidth; i++) {
                boardGridPane.getColumnConstraints().add(new ColumnConstraints(tileSize));
            }

            for (int i = 0; i < boardHeight; i++) {
                boardGridPane.getRowConstraints().add(new RowConstraints(tileSize));
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


    public void onStraightRB() {
        //System.out.println(floorTiles.getSelectedToggle().toString());
        straightRB.setSelected(true);
    }

    public void onTshapeRB() {
        tshapeRB.setSelected(true);
    }

    public void onCornerRB() {
        cornerRB.setSelected(true);
    }

    public void onGoalRB() {
        goalRB.setSelected(true);
    }


    public void resetPlayerPosition() {
    }


    //Menu Bar Controls
    public void onSilkBagToggle() {
        silkBag = !silkBag;

        if (silkBag) {
            silkBagToggleButton.setSelected(true);
        } else {
            silkBagToggleButton.setSelected(false);
        }
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
