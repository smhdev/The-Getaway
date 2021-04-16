package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UnlockablesScreenController extends StateLoad {

    private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
    private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());
    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());

    //Files and images of the unlockables
    File unlockable1File = new File("Assets\\truck1.png");
    private final Image unlockable1Image = new Image(unlockable1File.toURI().toString());
    File unlockable2File = new File("Assets\\icon4.png");
    private final Image unlockable2Image = new Image(unlockable2File.toURI().toString());
    File unlockable3File = new File("Assets\\bike1.png");
    private final Image unlockable3Image = new Image(unlockable3File.toURI().toString());
    File unlockable4File = new File("Assets\\icon5.png");
    private final Image unlockable4Image = new Image(unlockable4File.toURI().toString());
    File unlockable5File = new File("Assets\\icon6.png");
    private final Image unlockable5Image = new Image(unlockable5File.toURI().toString());
    File unlockable6File = new File("Assets\\sportscar1.png");
    private final Image unlockable6Image = new Image(unlockable6File.toURI().toString());


    @FXML
    ImageView unlockable1;
    @FXML
    ImageView unlockable2;
    @FXML
    ImageView unlockable3;
    @FXML
    ImageView unlockable4;
    @FXML
    ImageView unlockable5;
    @FXML
    ImageView unlockable6;
    @FXML
    Label unlockableText1;
    @FXML
    Label unlockableText2;
    @FXML
    Label unlockableText3;
    @FXML
    Label unlockableText4;
    @FXML
    Label unlockableText5;
    @FXML
    Label unlockableText6;
    @FXML
    Button backButton;

    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        unlockable1.setImage(unlockable1Image);
        unlockable2.setImage(unlockable2Image);
        unlockable3.setImage(unlockable3Image);
        unlockable4.setImage(unlockable4Image);
        unlockable5.setImage(unlockable5Image);
        unlockable6.setImage(unlockable6Image);

        unlockableText1.setText("Level 2");
        unlockableText2.setText("Level 5");
        unlockableText3.setText("Level 5");
        unlockableText4.setText("Level 7");
        unlockableText5.setText("Level 9");
        unlockableText6.setText("Level 10");
    }

    /**
     * Called when the player clicks on the 'Back to menu" button
     * opens the menu screen
     */
    public void onBack() {
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        WindowLoader wl = new WindowLoader(backButton);
        wl.load("MenuScreen", getInitData());
    }


}
