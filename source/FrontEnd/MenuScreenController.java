package FrontEnd;

import MessageOfTheDay.MessageOfTheDay;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import sun.font.FontScalerException;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Use to control the Menu screen scene.
 *
 * @author David Langmaid, Liam
 * @version 1.1
 * @since 1
 */
public class MenuScreenController extends StateLoad {

    /* These final variables are used for the game's Sound Effects (SFX) */

    private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
    private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());


    @FXML
    private Button newGameButton;
    @FXML
    private Label MoTD;
    @FXML
    private Text Header;

    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String message;
        try {
            message = MessageOfTheDay.puzzle();
        } catch (Exception e) {
            message = "Error with Server" + e.getCause();
        }
        MoTD.setText(message);
        try {
            Header.setFont(Font.loadFont("file:Assets/WickedSeventies.ttf", 20));
            System.out.println(Header.getFont());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to exit the application
     */
    public void onQuitButton() {
        Platform.exit();
    }

    /**
	 * Called when new game is clicked
     * opens game setup
     */
    public void onNewGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("GameSetup", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * called when load game is clicked
     * opens load game screen
     */
    public void onLoadGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("LoadGame", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * called when settings button is clicked
     * opens settings window
     */
    public void onSettings() {
        wl = new WindowLoader(newGameButton);
        wl.load("Settings", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * called when profiles button is clicked
     * opens profile window
     */
    public void onPlayerProfiles() {
        wl = new WindowLoader(newGameButton);
        wl.load("Profiles", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * called when leaderboard button is clicked
     * opens leaderboard window
     */
    public void onLeaderBoard() {
        wl = new WindowLoader(newGameButton);
        wl.load("Leaderboard", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }


    /**
     * called when the how to play button is clicked
     * opens the how to play window
     */
    public void onHowToPlay() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/HowToPlay", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * Called when level editor button is clicked
     * Opens the level editor window
     */
    public void onLevelEditor() {
        wl = new WindowLoader(newGameButton);
        wl.load("LevelEditorMenu", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));

    }

    /**
     * Called when the unlockable button is clicked
     * allows the user to check the level that items are unlocked
     */
    public void onUnlockables() {
        wl = new WindowLoader(newGameButton);
        wl.load("UnlockablesScreen", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

}
