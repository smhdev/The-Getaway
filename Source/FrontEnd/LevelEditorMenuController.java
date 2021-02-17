package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

    @FXML
    private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
