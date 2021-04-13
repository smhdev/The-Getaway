package FrontEnd;

import BackEnd.Profile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Scanner;

/***
 * A controller class for profiles.fxml which allows a user to show the player profiles saved, create new player
 * profiles, delete player profiles and view player profiles.
 * It is loaded by clicking from it in the MenuScreen and allows the user to return to the MenuScreen with an action.
 * @author Zhan Zhang, Sam Harry
 * @version 1.1
 */
public class ProfilesController extends StateLoad {

    /* These final variables are used for the game's Sound Effects (SFX) */

    private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
    private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());
    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());
    private final String ERROR_SFX = "Assets\\SFX\\error.mp3";
    private final AudioClip ERROR_AUDIO = new AudioClip(new File(ERROR_SFX).toURI().toString());

    @FXML
    public Label playerRecord;
    @FXML
    public ImageView playerIcon;
    @FXML
    private Button backButton;
    @FXML
    private TextField input;
    @FXML
    private ListView<String> playerList;
    @FXML
    private Label levelInfo;
    @FXML
    private Label xpInfo;
    @FXML
    private ProgressBar xpBar;

    int currentIndex = 0;
    final File iconImage0 = new File("Assets\\icon0.png");
    final Image icon0 = new Image(iconImage0.toURI().toString());
    final File iconImage1 = new File("Assets\\icon1.png");
    final Image icon1 = new Image(iconImage1.toURI().toString());
    final File iconImage2 = new File("Assets\\icon2.png");
    final Image icon2 = new Image(iconImage2.toURI().toString());
    final File iconImage3 = new File("Assets\\icon3.png");
    final Image icon3 = new Image(iconImage3.toURI().toString());

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File userDataFolder = new File("SaveData\\UserData\\");
        String[] children = userDataFolder.list();
        playerIcon.setImage(icon0);
        if (playerList.getSelectionModel().getSelectedItem() == null) {
            if (children != null) {
                for (String filename : children) {
                    playerList.getItems().addAll(filename.substring(0, filename.length() - 4));
                }
                playerList.getSelectionModel().selectFirst();
            }
        }
    }

    /**
     * When a player in the list is clicked on, the player picture is updated to their picture
     */
    public void onClickPlayer() {
        playerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            File user = new File("SaveData\\UserData\\" + newValue + ".txt");
            try {
                Scanner scanner = new Scanner(user);
                scanner.nextInt();
                scanner.nextInt();
                scanner.nextInt();
                scanner.nextInt();
                File iconImage = new File("Assets\\" + scanner.next() + ".png");
                playerIcon.setImage(new Image(iconImage.toURI().toString()));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String playerPicked = playerList.getSelectionModel().getSelectedItem();
            input.setStyle("-fx-border-color: default");

            Profile profile = null;
            try {
                profile = Profile.readProfile(playerPicked);
            } catch (IOException e) {
                e.printStackTrace();
            }
            playerRecord.setText(
                    profile.getName() + " has " +
                            profile.getWins() +
                            " wins, " +
                            profile.getLosses() +
                            " losses, a win streak of " +
                            profile.getWinStreak() +
                            " and they are level " +
                            profile.getLevel());

            profile.setLevel();
            xpInfo.setText("XP: " + profile.getXp() + " / " + (profile.getLevel() + 1) * 400);
            levelInfo.setText(profile.getName() + " is level: " + profile.getLevel());
            if (profile.getXp() == 0) {
                xpBar.progressProperty().set(0);
            }
            else {
                xpBar.progressProperty().set((float)1/400 * (profile.getXp() - profile.getLevel() * 400));
            }
        });
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    public void onSelectProfilePic() {
        String playerPicked = playerList.getSelectionModel().getSelectedItem();
        try {
            Profile profile = Profile.readProfile(playerPicked);
            PrintWriter user = new PrintWriter(new FileWriter("SaveData\\UserData\\" + playerPicked + ".txt"));
            user.flush();
            user.write(profile.getWins() + " " + profile.getLosses() + " " + profile.getWinStreak() + " "
                    + profile.getLevel() + " icon" + currentIndex);
            user.close();
            playerRecord.setText(profile.getName() + " has changed profile pictures.");
            MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * the action on the button back, back to the menus screen.
     */
    public void onBackButton() {
        WindowLoader wl = new WindowLoader(backButton);
        wl.load("MenuScreen", getInitData());
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * try to create a user file with name typed, create one in UserData if there is not a file with
     * initialized data and turn text field to red if there is one.
     *
     * @throws IOException when FileWriter failed to write the file.
     */
    public void createFile() throws IOException {
        String newName = input.getText();
        File user = new File("SaveData\\UserData\\" + newName + ".txt");

        if (user.exists() && !user.isDirectory()) {
            input.setStyle("-fx-border-color: red");
        }
        if (user.exists() && !user.isDirectory() || newName.isEmpty()) {
            input.setStyle("-fx-border-color: red");
            ERROR_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));

        } else {
            input.setStyle("-fx-border-color: default");
            playerList.getItems().addAll(newName);
            PrintWriter newUser = new PrintWriter(new FileWriter("SaveData\\UserData\\" + newName + ".txt"));
            newUser.write("0 0 0 0 icon" + currentIndex);
            newUser.close();
            MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        }
    }

    /**
     * Deletes the file selected in the list, and turns the text field back to white if it is not.
     */
    public void deleteFile() {
        String newName = playerList.getSelectionModel().getSelectedItem();
        File user = new File("SaveData\\UserData\\" + newName + ".txt");
        if (user.delete()) {
            input.setStyle("-fx-border-color: default");
            playerList.getItems().remove(newName);

            MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));

        } else {
            ERROR_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
            input.setStyle("-fx-border-color: red");
        }
    }

    /**
     * This button switch the player icon which will save in player profile created.
     */
    public void nextIcon() {
        currentIndex++;
        if (currentIndex == 0) {
            playerIcon.setImage(icon0);
        } else if (currentIndex == 1) {
            playerIcon.setImage(icon1);
        } else if (currentIndex == 2) {
            playerIcon.setImage(icon2);
        } else if (currentIndex == 3) {
            playerIcon.setImage(icon3);
        } else if (currentIndex == 4) {
            currentIndex = 0;
            playerIcon.setImage(icon0);
        }
    }
}

