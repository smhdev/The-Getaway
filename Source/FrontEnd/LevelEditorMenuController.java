package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Used to control the options for level editor
 *
 * @version 1.0
 * @since 1.0
 * @author Liam B
 */
public class LevelEditorMenuController extends StateLoad{

    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());
    private boolean levelEditorOption = true; // True for new level or False for existing level

    @FXML
    private Button backButton;
    @FXML
    private RadioButton newButton;
    @FXML
    private RadioButton existingButton;
    @FXML
    private FlowPane existingOption;
    @FXML
    private ChoiceBox<String> selectGameBoard;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup levelEditorOption = new ToggleGroup();
        newButton.setToggleGroup(levelEditorOption);
        existingButton.setToggleGroup(levelEditorOption);

        existingOption.setDisable(true);

        if (selectGameBoard.getValue() == null) {
            String[] gameBoards;
            File gameBoardLocation = new File("Gameboards");
            gameBoards = gameBoardLocation.list();
            if (gameBoards != null) {
                for (String gameBoard : gameBoards) {
                    gameBoard = gameBoard.substring(0, gameBoard.length() - 4);
                    selectGameBoard.getItems().add(gameBoard);
                }
            }
            selectGameBoard.getSelectionModel().selectFirst();
        }
    }

    /**
     * Toggles
     */
    public void onLevelEditorToggle(){
        levelEditorOption = !levelEditorOption;
        existingOption.setDisable(levelEditorOption);
    }

    /**
     * Returns to main menu
     */
    public void onBackButton() {
        WindowLoader wl = new WindowLoader(backButton);
        wl.load("MenuScreen", getInitData());
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }
}
