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
 * Allows users to select their own player profiles, vehicles and check their progression in the game.
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
    private ImageView carIcon;
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
    int currentIndexCar = 0;
    final File iconImage0 = new File("Assets\\icon0.png");
    final Image icon0 = new Image(iconImage0.toURI().toString());
    final File iconImage1 = new File("Assets\\icon1.png");
    final Image icon1 = new Image(iconImage1.toURI().toString());
    final File iconImage2 = new File("Assets\\icon2.png");
    final Image icon2 = new Image(iconImage2.toURI().toString());
    final File iconImage3 = new File("Assets\\icon3.png");
    final Image icon3 = new Image(iconImage3.toURI().toString());
    final File iconImage4 = new File("Assets\\icon4.png");
    final Image icon4 = new Image(iconImage4.toURI().toString());
    final File iconImage5 = new File("Assets\\icon5.png");
    final Image icon5 = new Image(iconImage5.toURI().toString());
    final File iconImage6 = new File("Assets\\icon6.png");
    final Image icon6 = new Image(iconImage6.toURI().toString());

    final File carImage0 = new File("Assets\\car1.png");
    final Image carIcon0 = new Image(carImage0.toURI().toString());
    final File carImage1 = new File("Assets\\truck1.png");
    final Image carIcon1 = new Image(carImage1.toURI().toString());
    final File carImage2 = new File("Assets\\bike1.png");
    final Image carIcon2 = new Image(carImage2.toURI().toString());
    final File carImage3 = new File("Assets\\sportscar1.png");
    final Image carIcon3 = new Image(carImage3.toURI().toString());

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
                String user = playerList.getSelectionModel().getSelectedItem();
                displayUserInfo(user);
            }
        }
    }


    /**
     * When a player in the list is clicked on, the player picture is updated to their picture, their level and xp
     * progress and win and lose stats
     */
    public void onClickPlayer() {
        carIcon.setOpacity(1);
        playerIcon.setOpacity(1);
        playerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayUserInfo(newValue);
        });
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * When the select player profile picture button is pressed, read the players profile and check if they are a high
     * enough level to be able to use the desired picture. If they are, then rewrite their file with the new updated
     * profile picture.
     */
    public void onSelectProfilePic() {
        String playerPicked = playerList.getSelectionModel().getSelectedItem();
        try {
            Profile profile = Profile.readProfile(playerPicked);
            profile.setLevel();
            if (profile.getIcon().equals("icon" + currentIndex)) {
                playerRecord.setStyle("-fx-background-color:red");
                playerRecord.setText(profile.getName() + " has already selected this player icon!");
                return;
            }

            if (!profilePicLevelCheck(currentIndex, profile.getLevel())) {
                playerRecord.setStyle("-fx-background-color:red");
                playerRecord.setText(profile.getName() + " is not a high enough level for this profile picture!");
                return;
            }

            playerRecord.setStyle("-fx-background-color: transparent");
            PrintWriter user = new PrintWriter(new FileWriter("SaveData\\UserData\\" + playerPicked + ".txt"));
            user.flush();
            user.write(profile.getWins() + " " + profile.getLosses() + " " + profile.getXp() + " "
                    + profile.getWinStreak() + " icon" + currentIndex + " " + profile.getCarIcon());
            user.close();
            playerRecord.setText(profile.getName() + " has changed profile pictures.");
            MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When a player selects a car picture, write that to their player profile. If they are not a high enough level,
     * notify the user.
     */
    public void onSelectCarPic() {
        String playerPicked = playerList.getSelectionModel().getSelectedItem();
        try {
            Profile profile = Profile.readProfile(playerPicked);
            profile.setLevel();
            String carSelected = "car";
            switch(currentIndexCar) {
                case 0:
                    carSelected = "car";
                    break;
                case 1:
                    carSelected = "truck";
                    break;
                case 2:
                    carSelected = "bike";
                    break;
                case 3:
                    carSelected = "sportscar";
                    break;
            }

            if (profile.getCarIcon().equals(carSelected)) {
                playerRecord.setStyle("-fx-background-color:red");
                playerRecord.setText(profile.getName() + " has already selected this vehicle!");
                return;
            }

            if (!carIconLevelCheck(carSelected, profile.getLevel())) {
                playerRecord.setStyle("-fx-background-color:red");
                carIcon.setOpacity(.5);
                playerRecord.setText(profile.getName() + " is not high enough level to choose this vehicle!");
                return;
            }
            playerRecord.setStyle("-fx-background-color: transparent");
            PrintWriter user = new PrintWriter(new FileWriter("SaveData\\UserData\\" + playerPicked + ".txt"));
            user.flush();
            user.write(profile.getWins() + " " + profile.getLosses() + " " + profile.getXp() + " "
                    + profile.getWinStreak() + " " + profile.getIcon() + " " + carSelected);
            user.close();
            playerRecord.setText(profile.getName() + " has changed their vehicle to " + carSelected + ".");
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

        }
        else {
            input.setStyle("-fx-border-color: default");
            playerList.getItems().addAll(newName);
            PrintWriter newUser = new PrintWriter(new FileWriter("SaveData\\UserData\\" + newName + ".txt"));
            newUser.write("0 0 0 0 icon0 car");
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
     * This button switch the player icon which will save in player profile created. If the player is not a high enough
     * level, grey out the image so that the users know they cannot select it.
     */
    public void nextIcon() {
        Profile profile = null;
        String playerPicked = playerList.getSelectionModel().getSelectedItem();
        try {
            profile = Profile.readProfile(playerPicked);
            profile.setLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerIcon.setOpacity(1);
        currentIndex++;
        if (currentIndex == 0) {
            playerIcon.setImage(icon0);
        } else if (currentIndex == 1) {
            playerIcon.setImage(icon1);
            if (!profilePicLevelCheck(currentIndex, profile.getLevel())) {
                playerIcon.setOpacity(0.5);
            }

        } else if (currentIndex == 2) {
            if (!profilePicLevelCheck(currentIndex, profile.getLevel())) {
                playerIcon.setOpacity(0.5);
            }
            playerIcon.setImage(icon2);

        } else if (currentIndex == 3) {
            playerIcon.setImage(icon3);
            if (!profilePicLevelCheck(currentIndex, profile.getLevel())) {
                playerIcon.setOpacity(0.5);
            }

        } else if (currentIndex == 4) {
            playerIcon.setImage(icon4);
            if (!profilePicLevelCheck(currentIndex, profile.getLevel())) {
                playerIcon.setOpacity(0.5);
            }

        } else if (currentIndex == 5) {
            playerIcon.setImage(icon5);
            if (!profilePicLevelCheck(currentIndex, profile.getLevel())) {
                playerIcon.setOpacity(0.5);
            }

        } else if (currentIndex == 6) {
            playerIcon.setImage(icon6);
            if (!profilePicLevelCheck(currentIndex, profile.getLevel())) {
                playerIcon.setOpacity(0.5);
            }

        } else if (currentIndex == 7) {
            playerIcon.setImage(icon0);
            currentIndex = 0;
            playerIcon.setOpacity(1);
        }
    }

    /**
     * This button allows the user to cycle through the available car icons for use in game. If they are not a
     * high enough level to use a vehicle model, the item in the list will be greyed out for them.
     */
    public void nextCarIcon() {
        Profile profile = null;
        String playerPicked = playerList.getSelectionModel().getSelectedItem();
        try {
            profile = Profile.readProfile(playerPicked);
            profile.setLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        carIcon.setOpacity(1);
        currentIndexCar++;
        if (currentIndexCar == 0) {
            carIcon.setImage(carIcon0);
        } else if (currentIndexCar == 1) {
            carIcon.setImage(carIcon1);
            if (!carIconLevelCheck("truck", profile.getLevel())) {
                carIcon.setOpacity(0.5);
            }
        } else if (currentIndexCar == 2) {
            carIcon.setImage(carIcon2);
            if (!carIconLevelCheck("bike", profile.getLevel())) {
                carIcon.setOpacity(0.5);
            }
        } else if (currentIndexCar == 3) {
            if (!carIconLevelCheck("sportscar", profile.getLevel())) {
                carIcon.setOpacity(0.5);
            }
            carIcon.setImage(carIcon3);
        } else if (currentIndexCar == 4) {
            currentIndexCar = 0;
            carIcon.setImage(carIcon0);
            carIcon.setOpacity(1);
        }
    }

    /**
     * Displays the users details (wins, losses, winstreak and level) in the information box at the bottom of the
     * screen. Also shows the xp progression details.
     * @param username The name of the user to display the details of
     */
    public void displayUserInfo(String username) {
        File user = new File("SaveData\\UserData\\" + username + ".txt");
        String userIcon = null;
        String userCarIcon = null;
        try {
            Scanner scanner = new Scanner(user);
            scanner.nextInt();
            scanner.nextInt();
            scanner.nextInt();
            scanner.nextInt();
            userIcon = scanner.next();
            userCarIcon = scanner.next();
            File iconImage = new File("Assets\\" + userIcon + ".png");
            File carImage = new File("Assets\\" + userCarIcon + "1.png");
            playerIcon.setImage(new Image(iconImage.toURI().toString()));
            carIcon.setImage(new Image(carImage.toURI().toString()));

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
        profile.setLevel();
        playerRecord.setText(
                profile.getName() + " has " +
                        profile.getWins() +
                        " wins, " +
                        profile.getLosses() +
                        " losses, a win streak of " +
                        profile.getWinStreak() +
                        " and they are level " +
                        profile.getLevel());


        xpInfo.setText("XP: " + profile.getXp() + " / " + profile.getLevel() * 400 + "    " + "XP to next level: " +
                ((profile.getLevel()) * 400 - profile.getXp()));
        levelInfo.setText(profile.getName() + " is level: " + profile.getLevel());
        if (profile.getXp() == 0) {
            //if the user is brand new, no need for any bar progression
            xpBar.progressProperty().set(0);
        }
        else {
            //formula to calculate the distance along the bar to set the players xp
            xpBar.progressProperty().set((float) 1 / 400 * (profile.getXp() - ((profile.getLevel() - 1) * 400)));
        }
        int userCarIndex = 0;
        //convert the car icon used by the user in their file back into the index
        switch (userCarIcon) {
            case "car":
                userCarIndex = 0;
                break;
            case "truck":
                userCarIndex = 1;
                break;
            case "bike":
                userCarIndex = 2;
                break;
            case "sportscar":
                userCarIndex = 3;
                break;
        }
        //set the current index to the users current selected car's index, so that when cycled again, the index is
        //corrected
        currentIndexCar = userCarIndex;
        int userIconIndex = 0;
        //do the same for the user icons
        switch (userIcon) {
            case "icon0":
                userIconIndex = 0;
                break;
            case "icon1":
                userIconIndex = 1;
                break;
            case "icon2":
                userIconIndex = 2;
                break;
            case "icon3":
                userIconIndex = 3;
                break;
            case "icon4":
                userIconIndex = 4;
                break;
            case "icon5":
                userIconIndex = 5;
                break;
            case "icon6":
                userIconIndex = 6;
                break;
        }
        currentIndex = userIconIndex;
        //reset the player record style.
        playerRecord.setStyle("-fx-background-color: transparent");
    }

    /**
     * Checks whether a user is a high enough level to use a player profile picture
     * @param pictureIndex the index of the player profile picture the user desires to select
     * @param playerLevel the level of the player requesting use of the picture
     * @return boolean of whether the player is a high enough level to use the desired profile picture
     */
    public boolean profilePicLevelCheck(int pictureIndex, int playerLevel) {
        boolean isUnlocked = false;
        switch (pictureIndex) {
            case 0:
                isUnlocked = true;
                break;
            case 1:
                isUnlocked = true;
                System.out.println(playerLevel);
                break;
            case 2:
                isUnlocked = true;
                break;
            case 3:
                isUnlocked = true;
                break;
            case 4:
                if (playerLevel >= 5) {
                    isUnlocked = true;
                }
                break;
            case 5:
                if (playerLevel >= 7) {
                    isUnlocked = true;
                }
                break;
            case 6:
                if (playerLevel >= 9) {
                    isUnlocked = true;
                }
                break;
        }
        return isUnlocked;
    }

    /**
     * Checks to see whether a user is a high enough level to use a vehicle
     * @param vehicle the user wants to select
     * @param playerLevel of the player requesting to use the vehicle
     * @return boolean of whether the player is a high enough level to use the requested vehicle
     */
    public boolean carIconLevelCheck(String vehicle, int playerLevel) {
        boolean isUnlocked = false;
        switch(vehicle) {
            case "car":
                isUnlocked = true;
                break;
            case "truck":
                System.out.println(playerLevel);
                if (playerLevel >= 2) {
                    isUnlocked = true;

                }
                break;
            case "bike":
                if (playerLevel >= 5) {
                    isUnlocked = true;
                }
                break;
            case "sportscar":
                if (playerLevel >= 10) {
                    isUnlocked = true;
                }
                break;
        }
        return isUnlocked;
    }
}

