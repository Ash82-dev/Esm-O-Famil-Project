import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
public class Game extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    static String[] letters = {"آ","ا","ب","پ","ت","ث","ج","چ","ح","خ","د","ذ","ر","ز","ژ","س","ش","ص","ض","ط","ظ","ع","غ","ف",
            "ق","ک","گ","ل","م","ن","و","ه","ی"};
    static boolean[] clicked2 = new boolean[2];
    static boolean[] clicked5 = new boolean[2];
    static boolean[] clicked6 = new boolean[2];
    MediaPlayer sound;
    Button[] menuButtons;
    static OptionReader optionReader = new OptionReader();
    static ArrayList<String> chosen = new ArrayList<>();
    final static String[] theLetter = {null};
    static Font font1;
    static Button submit;
    static Text changing;
    static TextField[][] fields;
    public static Text[] options;
    public static Text[][] texts = new Text[11][5];
    public static final int[] round = {4};
    final static int[] roundCount = new int[1];
    final static int[] timeCount = new int[1];
    static boolean enable;
    static String str2  = "امتیاز : ";
    static String str = "حرف انتخاب شده : ";
    static Text winner = new Text(436,505,"شما برنده شدید");
    static Text loser = new Text(436,505,"شما بازنده شدید");
    static Text drawer = new Text(436,505,"بازی مساوی شد");
    static Label label1 = new Label();
    public static Text scoreBoard;
    static Text hostScore = new Text(372,420,"0");
    static Text guestScore = new Text(769,420,"0");
    static Text timer;
    static Text host = new Text(350,320,"میزبان");
    static Text guest = new Text(737,320,"میهمان");
    static int minutes = 0;
    static int seconds = 0;
    static IntegerProperty millis = new SimpleIntegerProperty(0);
    static int xText = 5;
    static int yText = 86;
    Button[] gameMode;
    Button[] gameMode1;
    VBox vBox2;
    Button arrows3;
    Button arrows4;
    Button arrows5;
    Button arrows6;
    static Button justDo;
    static Button justDo1;
    Label rounds;
    Label time;
    Button arrows7;
    Button arrows8;
    Button arrows9;
    Button arrows10;
    Label rounds1;
    Label time1;
    Rectangle box;
    Rectangle box1;
    static Rectangle rectangleCopy;
    static Rectangle blueBox;
    static Rectangle blueBox1;
    static Button arrows1Copy;
    static Button arrows2Copy;
    static Label labelCopy;

    private String toString(String s) {
        return s;
    }
    public static void showThem (boolean what){
        rectangleCopy.setVisible(what);
        blueBox.setVisible(what);
        arrows1Copy.setVisible(what);
        arrows2Copy.setVisible(what);
        labelCopy.setVisible(what);
        justDo.setVisible(what);
    }
    public void changeAll(boolean what){
        gameMode[0].setVisible(what);
        gameMode[1].setVisible(what);
        vBox2.setVisible(what);
        box1.setVisible(what);
        box.setVisible(what);
        arrows3.setVisible(what);
        arrows4.setVisible(what);
        arrows5.setVisible(what);
        arrows6.setVisible(what);
        rounds.setVisible(what);
        time.setVisible(what);
    }
    public void changeAll2 (boolean what){
        gameMode1[0].setDisable(what);
        gameMode1[1].setDisable(what);
        if (!what){
            if (clicked5[0]){
                arrows7.setDisable(what);
                arrows8.setDisable(what);
            } else {
                arrows9.setDisable(what);
                arrows10.setDisable(what);
            }
        } else {
            arrows7.setDisable(what);
            arrows8.setDisable(what);
            arrows9.setDisable(what);
            arrows10.setDisable(what);
        }
        rounds1.setDisable(what);
        time1.setDisable(what);
    }
    public static void disable(int cnt, int round, TextField[][] fields){
        for (int i = 0; i < cnt; i++) {
            fields[i][round].setDisable(true);
        }
        round--;
        if (round > -1){
            if (enable){
                for (int i = 0; i < cnt; i++) {
                    fields[i][round].setDisable(false);
                }
            } else {
                for (int i = 0; i < cnt; i++) {
                    fields[i][round].setVisible(true);
                }
            }
        }
    }
    void change(Text timer) {
        if(millis.getValue() == 1000) {
            seconds++;
            millis.setValue(0);
        }
        if(seconds == 60) {
            minutes++;
            seconds = 0;
        }
        timer.setText((((minutes/10) == 0) ? "0" : "") + minutes + ":"
                + (((seconds/10) == 0) ? "0" : "") + seconds);
        millis.setValue(millis.getValue() + 1);
    }
    void playMusic (boolean on){
        if (on){
            sound = new MediaPlayer(new Media(Paths.get("Sound Effects/sound.wav").toUri().toString()));
            sound.play();
        }
    }
    void disableEnable (boolean onOff){
        for (int i = 0; i < 4; i++) {
            menuButtons[i].setDisable(onOff);
        }
    }

    private static int compare(String option, String[] written){
        for (String w:written) {
            if (w.equals(option)){
                return 1;
            }
        }
        return 0;
    }

    public static int judge (String[] written, int cnt, int score) {
        boolean found;
        int i = 0;
        roundCount[0]--;
        String[] str = new String[cnt];
        for (int j = 0; j < str.length; j++) {
            str[j] = fields[j][round[0]].getText();
        }

        if (round[0] > -1){
            for (String s : str) {
                found = false;
                for (String option : optionReader.getNames()) {
                    if (option.equals(s)) {
                        if (compare(s, written) == 1) {
                            texts[i][round[0]].setText("5");
                            texts[i][round[0]].setFill(Color.rgb(242, 180, 20));
                            found = true;
                            i++;
                            score += 5;
                            break;
                        }
                        if (!found) {
                            texts[i][round[0]].setText("10");
                            texts[i][round[0]].setFill(Color.rgb(1, 133, 12));
                            found = true;
                            i++;
                            score += 10;
                        }
                    }
                }
                if (found) {
                    continue;
                }
                for (String option : optionReader.getFamilies()) {
                    if (option.equals(s)) {
                        if (compare(s, written) == 1) {
                            texts[i][round[0]].setText("5");
                            texts[i][round[0]].setFill(Color.rgb(242, 180, 20));
                            found = true;
                            i++;
                            score += 5;
                            break;
                        }
                        if (!found) {
                            texts[i][round[0]].setText("10");
                            texts[i][round[0]].setFill(Color.rgb(1, 133, 12));
                            found = true;
                            i++;
                            score += 10;
                        }
                    }
                }
                if (found) {
                    continue;
                }
                for (String option : optionReader.getCities()) {
                    if (option.equals(s)) {
                        if (compare(s, written) == 1) {
                            texts[i][round[0]].setText("5");
                            texts[i][round[0]].setFill(Color.rgb(242, 180, 20));
                            found = true;
                            i++;
                            score += 5;
                            break;
                        }
                        if (!found) {
                            texts[i][round[0]].setText("10");
                            texts[i][round[0]].setFill(Color.rgb(1, 133, 12));
                            found = true;
                            i++;
                            score += 10;
                        }
                    }
                }
                if (found) {
                    continue;
                }
                for (String option : optionReader.getCountries()) {
                    if (option.equals(s)) {
                        if (compare(s, written) == 1) {
                            texts[i][round[0]].setText("5");
                            texts[i][round[0]].setFill(Color.rgb(242, 180, 20));
                            found = true;
                            i++;
                            score += 5;
                            break;
                        }
                        if (!found) {
                            texts[i][round[0]].setText("10");
                            texts[i][round[0]].setFill(Color.rgb(1, 133, 12));
                            found = true;
                            i++;
                            score += 10;
                        }
                    }
                }
                if (found) {
                    continue;
                }
                for (String option : optionReader.getFoods()) {
                    if (option.equals(s)) {
                        if (compare(s, written) == 1) {
                            texts[i][round[0]].setText("5");
                            texts[i][round[0]].setFill(Color.rgb(242, 180, 20));
                            found = true;
                            i++;
                            score += 5;
                            break;
                        }
                        if (!found) {
                            texts[i][round[0]].setText("10");
                            texts[i][round[0]].setFill(Color.rgb(1, 133, 12));
                            found = true;
                            i++;
                            score += 10;
                        }
                    }
                }
                if (found) {
                    continue;
                }
                for (String option : optionReader.getFruits()) {
                    if (option.equals(s)) {
                        if (compare(s, written) == 1) {
                            texts[i][round[0]].setText("5");
                            texts[i][round[0]].setFill(Color.rgb(242, 180, 20));
                            found = true;
                            i++;
                            score += 5;
                            break;
                        }
                        if (!found) {
                            texts[i][round[0]].setText("10");
                            texts[i][round[0]].setFill(Color.rgb(1, 133, 12));
                            found = true;
                            i++;
                            score += 10;
                        }
                    }
                }
                if (found) {
                    continue;
                }
                for (String option : optionReader.getClothes()) {
                    if (option.equals(s)) {
                        if (compare(s, written) == 1) {
                            texts[i][round[0]].setText("5");
                            texts[i][round[0]].setFill(Color.rgb(242, 180, 20));
                            found = true;
                            i++;
                            score += 5;
                            break;
                        }
                        if (!found) {
                            texts[i][round[0]].setText("10");
                            texts[i][round[0]].setFill(Color.rgb(1, 133, 12));
                            found = true;
                            i++;
                            score += 10;
                        }
                    }
                }
                if (found) {
                    continue;
                }
                for (String option : optionReader.getObjects()) {
                    if (option.equals(s)) {
                        if (compare(s, written) == 1) {
                            texts[i][round[0]].setText("5");
                            texts[i][round[0]].setFill(Color.rgb(242, 180, 20));
                            found = true;
                            i++;
                            score += 5;
                            break;
                        }
                        if (!found) {
                            texts[i][round[0]].setText("10");
                            texts[i][round[0]].setFill(Color.rgb(1, 133, 12));
                            found = true;
                            i++;
                            score += 10;
                        }
                    }
                }
                if (found) {
                    continue;
                }
                for (String option : optionReader.getAnimals()) {
                    if (option.equals(s)) {
                        if (compare(s, written) == 1) {
                            texts[i][round[0]].setText("5");
                            texts[i][round[0]].setFill(Color.rgb(242, 180, 20));
                            found = true;
                            i++;
                            score += 5;
                            break;
                        }
                        if (!found) {
                            texts[i][round[0]].setText("10");
                            texts[i][round[0]].setFill(Color.rgb(1, 133, 12));
                            found = true;
                            i++;
                            score += 10;
                        }
                    }
                }
                if (found) {
                    continue;
                }
                for (String option : optionReader.getCars()) {
                    if (option.equals(s)) {
                        if (compare(s, written) == 1) {
                            texts[i][round[0]].setText("5");
                            texts[i][round[0]].setFill(Color.rgb(242, 180, 20));
                            found = true;
                            i++;
                            score += 5;
                            break;
                        }
                        if (!found) {
                            texts[i][round[0]].setText("10");
                            texts[i][round[0]].setFill(Color.rgb(1, 133, 12));
                            found = true;
                            i++;
                            score += 10;
                        }
                    }
                }
                if (found) {
                    continue;
                }
                for (String option : optionReader.getFlowers()) {
                    if (option.equals(s)) {
                        if (compare(s, written) == 1) {
                            texts[i][round[0]].setText("5");
                            texts[i][round[0]].setFill(Color.rgb(242, 180, 20));
                            found = true;
                            i++;
                            score += 5;
                            break;
                        }
                        if (!found) {
                            texts[i][round[0]].setText("10");
                            texts[i][round[0]].setFill(Color.rgb(1, 133, 12));
                            found = true;
                            i++;
                            score += 10;
                        }
                    }
                }
                if (!found) {
                    texts[i][round[0]].setText("0");
                    texts[i][round[0]].setFill(Color.rgb(247, 32, 68));
                    i++;
                }
            }
        }
        disable(cnt, round[0], fields);
        round[0]--;
        if (round[0] == -1) {
            enable = true;
            round[0] = 4;
            clearPage();
            for (int j = 0; j < cnt; j++) {
                fields[j][4].setDisable(false);
            }
        }
        scoreBoard.setText(str2 + score);
        return score;
    }
    private static void clearPage (){
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 5; j++) {
                fields[i][j].setText("");
                texts[i][j].setText("");
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception{
        int x = 1050;
        int y = 119;
        int xField = 23;
        int yField = 44;
        final boolean[] isServer = {false};
        final boolean[] on = {true};
        final boolean[] began = {false};
        final int[] temp = { 0 };
        final int[] cnt = { 0 };
        boolean[] clicked = new boolean[11];
        boolean[] clicked1 = new boolean[2];
        clicked1[0] = true;
        clicked2[0] = true;
        boolean[] clicked4 = new boolean[2];
        clicked4[1] = true;
        clicked5[0] = true;
        clicked6[0] = true;
        stage.setTitle("بازی اسم فامیل");

        Group root1 = new Group();                                                                                      //groups
        Group root2 = new Group();
        Group root3 = new Group();
        Group root4 = new Group();
        Group root5 = new Group();
        Group root6 = new Group();

        Scene menu = new Scene(root1,1200,800);                                                                         //scenes
        Scene gameSetting = new Scene(root6, 1200,800,Color.rgb(237, 135, 19));
        Scene letterChoosing = new Scene(root4,1200,800,Color.rgb(242, 216, 31));
        Scene wordChoosing = new Scene(root2,1200,800,Color.rgb(242, 218, 48));
        Scene setting = new Scene(root5,1200,800,Color.rgb(242, 218, 48));
        Scene page = new Scene(root3,1200,810);

        InputStream file = Files.newInputStream(Paths.get("Images/menu.jpg"));                                                 //images
        Image image = new Image(file);
        ImageView imageView = new ImageView(image);
        InputStream file1 = Files.newInputStream(Paths.get("Images/image.jpg"));
        Image image1 = new Image(file1);
        ImageView imageView1 = new ImageView(image1);
        imageView1.setY(20);
        InputStream file2 = Files.newInputStream(Paths.get("Images/icon.png"));
        Image image2 = new Image(file2);
        stage.getIcons().add(image2);

        menuButtons = new Button[4];                                                                                    //buttons
        Button[] words = new Button[11];
        Button[] forBack = new Button[2];
        Button[] forBack1 = new Button[2];
        Button[] settingButton = new Button[2];
        gameMode = new Button[2];
        gameMode1 = new Button[2];
        Button[] serverMode = new Button[2];
        Button[] onOff = new Button[2];
        Button resume = new Button("ادامه");
        Button arrows1 = new Button();
        arrows1Copy = new Button();
        Button arrows2 = new Button();
        arrows2Copy = new Button();
        arrows3 = new Button();
        arrows4 = new Button();
        arrows5 = new Button();
        arrows6 = new Button();
        arrows7 = new Button();
        arrows8 = new Button();
        arrows9 = new Button();
        arrows10 = new Button();
        submit = new Button("اتمام");
        Button ok = new Button("فهمیدم");
        Button apply = new Button("اعمال تغییرات و بازگشت");
        Button goBack = new Button("بازگشت");

        fields = new TextField[11][5];                                                                    //text fields

        VBox vBox = new VBox(0);
        VBox vBox1 = new VBox(41);
        vBox2 = new VBox(30);
        VBox vBox3 = new VBox(30);
        HBox hBox1 = new HBox(20);
        HBox hBox2 = new HBox(20);
        HBox hBox3 = new HBox(185);

        options = new Text[11];                                                                                  //texts and labels
        Text[] gameOption = new Text[3];
        Text[] gameOption1 = new Text[4];
        Text text = new Text(240, 200, "لطفاً حداقل پنج مورد را انتخاب کنید");
        Text text1 = new Text(325,200,"لطفاً یک حرف را انتخاب کنید");
        timer = new Text(1075,40,"00:00");
        Text buttonVolume = new Text(200,183,"صدای دکمه");
        Label label = new Label();
        labelCopy = new Label();
        Label label2 = new Label("آموزش");
        rounds = new Label("1");
        time = new Label("5");
        rounds1 = new Label("1");
        time1 = new Label("5");
        Text guide = new Text(147,200,"در این بازی شما قادر خواهید بود تا با یکی از دوستان خود بازی کنید." +
                                 " \n فردی باید به عنوان میزبان و دیگری باید به عنوان میهمان وارد شود." +
                                      " \n سپس میزبان به انتخاب کلمات بازی و حرف مورد نیاز بازی می پردازد." +
                                      " \n پس از آغاز بازی این تنها میزبان است که می تواند دکمه اتمام راند را" +
                                      " \n   بفشارد و بعد از آن حرف دیگری انتخاب کرده و بقیه بازی جریان پیدا  " +
                                      " \n  خواهد کرد. بازی به روش پایان می یابد که بستگی به انتخاب میزبان  " +
                                      " \n دارد او می تواند یکی از دو روش تعیین تعداد راند یا تعین زمان بازی را" +
                                        "\n انتخاب کند.\t\t\t\t\t\t\t       \t");
        Text lastText = new Text(394, 407, "حداقل پنج مورد را انتخاب کنید");

        Line[] lines = new Line[5];                                                                                     //lines
        Line[] lines1 = new Line[10];
        Line line = new Line(200,120,1000,120);

        Rectangle rectangle = new Rectangle(450,375,300,150);                                          //rectangles
        Rectangle rectangle1 = new Rectangle(100,50,1000,700);
        Rectangle rectangle2 = new Rectangle(0,0,1200,55);
        box = new Rectangle(800,218,200,50);
        box1 = new Rectangle(800,298,200,50);
        Rectangle box2 = new Rectangle(800,268,200,50);
        Rectangle box3 = new Rectangle(800,348,200,50);
        Rectangle bigBox = new Rectangle(0,163,1200,500);
        rectangleCopy = new Rectangle(450,325,300,150);
        blueBox = new Rectangle(250,250,700,400);
        blueBox1 = new Rectangle(250,250,700,400);
        Rectangle lastBox = new Rectangle(500,150, Color.rgb(222, 220, 193));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> change(timer)));                     //timeline
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        FileInputStream inputStream = new FileInputStream("Fonts/Lalezar-Regular.ttf");
        FileInputStream inputStream1 = new FileInputStream("Fonts/Lalezar-Regular.ttf");
        FileInputStream inputStream2 = new FileInputStream("Fonts/Lalezar-Regular.ttf");
        FileInputStream inputStream3 = new FileInputStream("Fonts/danstevis.otf");
        FileInputStream inputStream4 = new FileInputStream("Fonts/danstevis.otf");
        FileInputStream inputStream5 = new FileInputStream("Fonts/Lalezar-Regular.ttf");

        Font font = Font.loadFont(inputStream, 30);
        font1 = Font.loadFont(inputStream1,40);
        Font font2 = Font.loadFont(inputStream2,60);
        Font font3 = Font.loadFont(inputStream3,30);
        Font font4 = Font.loadFont(inputStream4,32);
        Font font5 = Font.loadFont(inputStream5,25);

        labelCopy.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean f = false;
            for (String s:chosen) {
                if (s.equals(labelCopy.getText())){
                    justDo.setDisable(true);
                    f = true;
                }
            }
            if (!f){
                justDo.setDisable(false);
            }
        });

        for (int i = 0; i < 4; i++) {                                                                                   //creating menu buttons
            menuButtons[i] = new Button();
            menuButtons[i].setBackground(null);
            menuButtons[i].setPrefSize(200,100);
            menuButtons[i].setFont(font);
            menuButtons[i].setTextFill(Color.rgb(245, 103, 2));
            switch (i){
                case 0:
                    menuButtons[i].setText("شروع");
                    break;
                case 1:
                    menuButtons[i].setText("تنظیمات");
                    break;
                case 2:
                    menuButtons[i].setText("آموزش");
                    break;
                case 3:
                    menuButtons[i].setText("خروج");
                    break;
            }
        }
        for (int i = 0; i < 11; i++) {                                                                                  //creating word buttons
            words[i] = new Button();
            clicked[i] = false;
            words[i].setPrefSize(200,100);
            words[i].setFont(font);
            words[i].setTextFill(Color.rgb(66, 96, 245));
            words[i].setStyle("-fx-background-color: #aebaf5");
            switch (i){
                case 0:
                    words[i].setText("غذا");
                    break;
                case 1:
                    words[i].setText("کشور");
                    break;
                case 2:
                    words[i].setText("شهر");
                    break;
                case 3:
                    words[i].setText("فامیل");
                    break;
                case 4:
                    words[i].setText("اسم");
                    break;
                case 5:
                    words[i].setText("ماشین");
                    words[i].setLayoutX(60);
                    words[i].setLayoutY(500);
                    break;
                case 6:
                    words[i].setText("حیوان");
                    words[i].setLayoutX(280);
                    words[i].setLayoutY(500);
                    break;
                case 7:
                    words[i].setText("اشیاء");
                    words[i].setLayoutX(500);
                    words[i].setLayoutY(500);
                    break;
                case 8:
                    words[i].setText("پوشاک");
                    words[i].setLayoutX(720);
                    words[i].setLayoutY(500);
                    break;
                case 9:
                    words[i].setText("میوه");
                    words[i].setLayoutX(940);
                    words[i].setLayoutY(500);
                    break;
                case 10:
                    words[i].setText("گل");
                    words[i].setLayoutX(500);
                    words[i].setLayoutY(620);
                    break;
            }
        }
        for (int i = 0; i < 2; i++) {                                                                                   //creating option choosing menu's forward and backward buttons
            forBack[i] = new Button();
            forBack[i].setLayoutY(690);
            forBack[i].setPrefSize(200,60);
            forBack[i].setFont(font);
            forBack[i].setBackground(null);
            if (i == 0){
                forBack[i].setText("تایید");
                forBack[i].setTextFill(Color.rgb(1, 133, 12));
                forBack[i].setLayoutX(940);
            } else {
                forBack[i].setText("بازگشت");
                forBack[i].setTextFill(Color.rgb(245, 103, 2));
                forBack[i].setLayoutX(60);
            }
        }
        for (int i = 0; i < 2; i++) {                                                                                   //creating letter choosing menu's forward and backward buttons
            forBack1[i] = new Button();
            forBack1[i].setLayoutY(690);
            forBack1[i].setPrefSize(200,60);
            forBack1[i].setFont(font);

            forBack1[i].setBackground(null);
            if (i == 0){
                forBack1[i].setText("ادامه");
                forBack1[i].setTextFill(Color.rgb(1, 133, 12));
                forBack1[i].setLayoutX(940);
            } else {
                forBack1[i].setText("بازگشت");
                forBack1[i].setTextFill(Color.rgb(245, 103, 2));
                forBack1[i].setLayoutX(60);
            }
        }
        for (int i = 0; i < 11; i++) {                                                                                  //creating game's chosen words
            options[i] = new Text();
            options[i].setFont(font3);
            options[i].setFill(Color.rgb(247, 32, 68));
            switch (i){
                case 0:
                    options[i].setText("غذا");
                    break;
                case 1:
                    options[i].setText("کشور");
                    break;
                case 2:
                    options[i].setText("شهر");
                    break;
                case 3:
                    options[i].setText("فامیل");
                    break;
                case 4:
                    options[i].setText("اسم");
                    break;
                case 5:
                    options[i].setText("ماشین");
                    break;
                case 6:
                    options[i].setText("حیوان");
                    break;
                case 7:
                    options[i].setText("اشیاء");
                    break;
                case 8:
                    options[i].setText("پوشاک");
                    break;
                case 9:
                    options[i].setText("میوه");
                    break;
                case 10:
                    options[i].setText("گل");
                    break;
            }
        }
        for (int i = 0; i < 5; i++) {                                                                                   //creating horizontal lines
            lines[i] = new Line(x,0,x,810);
            lines[i].setStroke(Color.rgb(227, 206, 20));
            lines[i].setStrokeWidth(2);
            x -= 210;
        }
        for (int i = 0; i < 10; i++) {                                                                                  //creating vertical lines
            lines1[i] = new Line(0,y,1200,y);
            lines1[i].setStroke(Color.rgb(227, 206, 20));
            lines1[i].setStrokeWidth(2.5);
            y += 67;
            switch (i){
                case 0:
                case 7:
                    y += 1;
                    break;
                case 1:
                    y += 5;
                    break;
                case 2:
                    y += 2;
                    break;
                case 3:
                    y += 3;
                    break;
                case 4:
                case 5:
                case 6:
                case 8:
                    y += 4;
                    break;
            }
        }
        for (int i = 0; i < 11; i++) {                                                                                  //creating text fields
            for (int j = 0; j < 5; j++) {
                fields[i][j] = new TextField();
                fields[i][j].setFont(font4);
                fields[i][j].setBackground(null);
                fields[i][j].setAlignment(Pos.CENTER_RIGHT);
                fields[i][j].setStyle("-fx-text-fill: #1e1ef7");
                fields[i][j].setPrefSize(199,50);
                fields[i][j].setLayoutX(xField);
                fields[i][j].setLayoutY(yField);
                if (j != round[0]){
                    fields[i][j].setVisible(false);
                }
                xField += 210;
            }
            xField = 23;
            yField += 70;
        }
        for (int i = 0; i < 2; i++) {
            settingButton[i] = new Button();
            settingButton[i].setPrefSize(200,100);
            settingButton[i].setFont(font1);
            settingButton[i].setBackground(null);
            switch (i){
                case 0:
                    settingButton[i].setText("بازی");
                    settingButton[i].setTextFill(Color.rgb(245, 103, 2));
                    break;
                case 1:
                    settingButton[i].setText("صدا");
                    settingButton[i].setTextFill(Color.rgb(252, 145, 68));
                    break;
            }
        }
        for (int i = 0; i < 3; i++) {
            gameOption[i] = new Text();
            gameOption[i].setFont(font);
            gameOption[i].setFill(Color.rgb(245, 103, 2));
            switch (i){
                case 0:
                    gameOption[i].setText("سبک بازی");
                    break;
                case 1:
                    gameOption[i].setText("تعداد راند");
                    break;
                case 2:
                    gameOption[i].setText("زمان بازی");
                    break;
            }
        }
        for (int i = 0; i < 4; i++) {
            gameOption1[i] = new Text();
            gameOption1[i].setFont(font);
            gameOption1[i].setFill(Color.rgb(245, 103, 2));
            switch (i){
                case 0:
                    gameOption1[i].setText("سبک بازی");
                    break;
                case 1:
                    gameOption1[i].setText("تعداد راند");
                    break;
                case 2:
                    gameOption1[i].setText("زمان بازی");
                    break;
                case 3:
                    gameOption1[i].setText("حالت بازی");
                    break;
            }
        }
        for (int i = 0; i < 2; i++) {
            gameMode[i] = new Button();
            gameMode[i].setPrefSize(200,60);
            gameMode[i].setFont(font5);
            switch (i){
                case 0:
                    gameMode[i].setLayoutX(595);
                    gameMode[i].setLayoutY(138);
                    gameMode[i].setText("تعیین تعداد راند");
                    gameMode[i].setStyle("-fx-background-color: #4260f5");
                    gameMode[i].setTextFill(Color.rgb(42, 199, 212));
                    break;
                case 1:
                    gameMode[i].setLayoutX(800);
                    gameMode[i].setLayoutY(138);
                    gameMode[i].setText("تعیین زمان کل");
                    gameMode[i].setTextFill(Color.rgb(66, 96, 245));
                    gameMode[i].setStyle("-fx-background-color: #aebaf5");
                    break;
            }
            onOff[i] = new Button();
            onOff[i].setVisible(false);
            onOff[i].setPrefSize(200,60);
            onOff[i].setFont(font5);
            switch (i){
                case 0:
                    onOff[i].setLayoutX(595);
                    onOff[i].setLayoutY(138);
                    onOff[i].setText("خاموش");
                    onOff[i].setTextFill(Color.rgb(66, 96, 245));
                    onOff[i].setStyle("-fx-background-color: #aebaf5");
                    break;
                case 1:
                    onOff[i].setLayoutX(800);
                    onOff[i].setLayoutY(138);
                    onOff[i].setText("روشن");
                    onOff[i].setStyle("-fx-background-color: #4260f5");
                    onOff[i].setTextFill(Color.rgb(42, 199, 212));
                    break;
            }
        }
        for (int i = 0; i < 2; i++) {
            serverMode[i] = new Button();
            serverMode[i].setPrefSize(200,60);
            serverMode[i].setFont(font5);
            switch (i){
                case 0:
                    serverMode[i].setLayoutX(595);
                    serverMode[i].setLayoutY(416);
                    serverMode[i].setText("میزبان");
                    serverMode[i].setStyle("-fx-background-color: #4260f5");
                    serverMode[i].setTextFill(Color.rgb(42, 199, 212));
                    break;
                case 1:
                    serverMode[i].setLayoutX(800);
                    serverMode[i].setLayoutY(416);
                    serverMode[i].setText("میهمان");
                    serverMode[i].setTextFill(Color.rgb(66, 96, 245));
                    serverMode[i].setStyle("-fx-background-color: #aebaf5");
                    break;
            }
        }
        for (int i = 0; i < 2; i++) {
            gameMode1[i] = new Button();
            gameMode1[i].setPrefSize(200, 60);
            gameMode1[i].setFont(font5);
            switch (i) {
                case 0:
                    gameMode1[i].setLayoutX(595);
                    gameMode1[i].setLayoutY(188);
                    gameMode1[i].setText("تعیین تعداد راند");
                    gameMode1[i].setStyle("-fx-background-color: #4260f5");
                    gameMode1[i].setTextFill(Color.rgb(42, 199, 212));
                    break;
                case 1:
                    gameMode1[i].setLayoutX(800);
                    gameMode1[i].setLayoutY(188);
                    gameMode1[i].setText("تعیین زمان کل");
                    gameMode1[i].setTextFill(Color.rgb(66, 96, 245));
                    gameMode1[i].setStyle("-fx-background-color: #aebaf5");
                    break;
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 5; j++) {
                texts[i][j] = new Text("");
                texts[i][j].setFont(font1);
                texts[i][j].setX(xText);
                texts[i][j].setY(yText);
                xText += 210;
            }
            yText += 70;
            xText = 5;
        }

        host.setFont(font1);
        host.setFill(Color.rgb(66, 96, 245));
        guest.setFont(font1);
        guest.setFill(Color.rgb(66, 96, 245));
        hostScore.setFont(font2);
        hostScore.setFill(Color.rgb(66, 96, 245));
        guestScore.setFont(font2);
        guestScore.setFill(Color.rgb(66, 96, 245));

        winner.setFont(font2);
        winner.setFill(Color.rgb(1, 133, 12));
        loser.setFont(font2);
        loser.setFill(Color.rgb(247, 32, 68));
        drawer.setFont(font2);
        drawer.setFill(Color.rgb(242, 216, 31));

        justDo = new Button("تایید");
        justDo.setPrefSize(200,60);
        justDo.setLayoutX(500);
        justDo.setLayoutY(530);
        justDo.setFont(font);
        justDo.setTextFill(Color.rgb(1, 133, 12));
        justDo.setBackground(null);

        justDo1 = new Button("خروج");
        justDo1.setPrefSize(200,60);
        justDo1.setLayoutX(500);
        justDo1.setLayoutY(560);
        justDo1.setFont(font);
        justDo1.setTextFill(Color.rgb(1, 133, 12));
        justDo1.setBackground(null);

        scoreBoard = new Text(str2 + 0);
        scoreBoard.setLayoutX(770);
        scoreBoard.setLayoutY(37);
        scoreBoard.setFont(font);
        scoreBoard.setFill(Color.rgb(242, 216, 31));

        line.setStrokeWidth(3);
        line.setStroke(Color.rgb(245, 103, 2));

        resume.setLayoutX(340);
        resume.setLayoutY(590);
        resume.setPrefSize(200,60);
        resume.setFont(font);
        resume.setTextFill(Color.rgb(109, 191, 117));
        resume.setBackground(null);

        submit.setTextFill(Color.rgb(242, 216, 31));
        submit.setBackground(null);
        submit.setFont(font5);
        submit.setPrefSize(150,20);
        submit.setLayoutX(0);
        submit.setLayoutY(-9);

        guide.setFill(Color.WHITE);
        guide.setFont(font1);
        guide.setVisible(false);
        guide.setTextAlignment(TextAlignment.RIGHT);

        ok.setTextFill(Color.WHITE);
        ok.setFont(font);
        ok.setPrefSize(150,50);
        ok.setLayoutX(525);
        ok.setLayoutY(650);
        ok.setVisible(false);
        ok.setBackground(null);

        apply.setTextFill(Color.rgb(252, 145, 68));
        apply.setBackground(null);
        apply.setFont(font);
        apply.setPrefSize(300,60);
        apply.setLayoutX(175);
        apply.setLayoutY(680);

        goBack.setTextFill(Color.rgb(252, 145, 68));
        goBack.setBackground(null);
        goBack.setFont(font);
        goBack.setPrefSize(200,60);
        goBack.setLayoutX(140);
        goBack.setLayoutY(590);

        text.setFont(font2);
        text.setFill(Color.rgb(245, 103, 2));
        text1.setFont(font2);
        text1.setFill(Color.rgb(245, 103, 2));

        timer.setFont(font1);
        timer.setFill(Color.rgb(227, 206, 20));

        changing = new Text(295,390,"در حال تغییر حرف انتخاب شده");
        changing.setFont(font2);
        changing.setFill(Color.rgb(66, 96, 245));

        vBox.setPadding(new Insets(360,50,100,950));
        vBox1.setPadding(new Insets(63,100,763,1100));
        vBox2.setPadding(new Insets(150,1000,650,200));
        vBox3.setPadding(new Insets(200,1000,600,200));
        hBox1.setPadding(new Insets(350,850,500,60));
        hBox2.setPadding(new Insets(500,850,500,60));
        hBox3.setPadding(new Insets(30,900,770,300));
        vBox.getChildren().addAll(menuButtons[0],menuButtons[1],menuButtons[2],menuButtons[3]);
        vBox2.getChildren().addAll(gameOption);
        vBox3.getChildren().addAll(gameOption1);
        hBox1.getChildren().addAll(words[0],words[1],words[2],words[3],words[4]);
        hBox2.getChildren().addAll(words[5],words[6],words[7],words[8],words[9]);
        hBox3.getChildren().addAll(settingButton);

        rectangle.setFill(Color.rgb(222, 220, 193));
        rectangle.setStyle("-fx-stroke: #f56702; -fx-stroke-width: 3");
        rectangle.setArcWidth(7);
        rectangle.setArcHeight(7);

        rectangleCopy.setFill(Color.rgb(222, 220, 193));
        rectangleCopy.setStyle("-fx-stroke: #4260f5; -fx-stroke-width: 3");
        rectangleCopy.setArcWidth(7);
        rectangleCopy.setArcHeight(7);

        rectangle1.setFill(Color.rgb(128, 127, 120));
        rectangle1.setArcWidth(7);
        rectangle1.setArcHeight(7);
        rectangle1.setVisible(false);
        rectangle1.setStyle("-fx-stroke: #000000; -fx-stroke-width: 4");

        rectangle2.setFill(Color.rgb(245, 103, 2));

        box.setFill(Color.rgb(222, 220, 193));
        box.setStyle("-fx-stroke: #4260f5; -fx-stroke-width: 3");
        box.setArcWidth(7);
        box.setArcHeight(7);
        box1.setFill(Color.rgb(222, 220, 193));
        box1.setStyle("-fx-stroke: #4260f5; -fx-stroke-width: 3");
        box1.setArcWidth(7);
        box1.setArcHeight(7);
        box1.setDisable(true);

        box2.setFill(Color.rgb(222, 220, 193));
        box2.setStyle("-fx-stroke: #4260f5; -fx-stroke-width: 3");
        box2.setArcWidth(7);
        box2.setArcHeight(7);
        box3.setFill(Color.rgb(222, 220, 193));
        box3.setStyle("-fx-stroke: #4260f5; -fx-stroke-width: 3");
        box3.setArcWidth(7);
        box3.setArcHeight(7);
        box3.setDisable(true);

        bigBox.setFill(Color.rgb(242, 216, 31));

        lastBox.setStyle("-fx-stroke: #f56702; -fx-stroke-width: 3;");
        lastBox.setX(350);
        lastBox.setLayoutY(325);
        lastBox.setArcHeight(10);
        lastBox.setArcWidth(10);

        lastText.setFont(font1);
        lastText.setFill(Color.rgb(245, 103, 2));

        arrows1.setLayoutX(775);
        arrows1.setLayoutY(420);
        arrows1.setPrefSize(55,55);
        arrows1.setRotate(90);
        arrows1.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #f56702;");
        arrows2.setLayoutX(370);
        arrows2.setLayoutY(420);
        arrows2.setPrefSize(55,55);
        arrows2.setRotate(-90);
        arrows2.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #f56702;");

        arrows1Copy.setLayoutX(775);
        arrows1Copy.setLayoutY(370);
        arrows1Copy.setPrefSize(55,55);
        arrows1Copy.setRotate(90);
        arrows1Copy.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #4260f5;");
        arrows2Copy.setLayoutX(370);
        arrows2Copy.setLayoutY(370);
        arrows2Copy.setPrefSize(55,55);
        arrows2Copy.setRotate(-90);
        arrows2Copy.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #4260f5;");

        arrows3.setLayoutX(970);
        arrows3.setLayoutY(229);
        arrows3.setPrefSize(5,5);
        arrows3.setRotate(90);
        arrows3.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #4260f5;");
        arrows4.setLayoutX(810);
        arrows4.setLayoutY(229);
        arrows4.setPrefSize(5,5);
        arrows4.setRotate(-90);
        arrows4.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #4260f5;");

        arrows5.setLayoutX(970);
        arrows5.setLayoutY(309);
        arrows5.setPrefSize(5,5);
        arrows5.setRotate(90);
        arrows5.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #4260f5;");
        arrows5.setDisable(true);
        arrows6.setLayoutX(810);
        arrows6.setLayoutY(309);
        arrows6.setPrefSize(5,5);
        arrows6.setRotate(-90);
        arrows6.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #4260f5;");
        arrows6.setDisable(true);

        arrows7.setLayoutX(970);
        arrows7.setLayoutY(279);
        arrows7.setPrefSize(5,5);
        arrows7.setRotate(90);
        arrows7.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #4260f5;");
        arrows8.setLayoutX(810);
        arrows8.setLayoutY(279);
        arrows8.setPrefSize(5,5);
        arrows8.setRotate(-90);
        arrows8.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #4260f5;");

        arrows9.setLayoutX(970);
        arrows9.setLayoutY(359);
        arrows9.setPrefSize(5,5);
        arrows9.setRotate(90);
        arrows9.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #4260f5;");
        arrows9.setDisable(true);
        arrows10.setLayoutX(810);
        arrows10.setLayoutY(359);
        arrows10.setPrefSize(5,5);
        arrows10.setRotate(-90);
        arrows10.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\"; -fx-background-color: #4260f5;");
        arrows10.setDisable(true);

        buttonVolume.setFont(font);
        buttonVolume.setFill(Color.rgb(245, 103, 2));
        buttonVolume.setVisible(false);

        label.setLayoutX(585);
        label.setLayoutY(392);
        label.setFont(font2);
        label.setTextFill(Color.rgb(66, 96, 245));
        label.setText(letters[temp[0]]);

        labelCopy.setLayoutX(585);
        labelCopy.setLayoutY(342);
        labelCopy.setFont(font2);
        labelCopy.setTextFill(Color.rgb(66, 96, 245));
        labelCopy.setText(letters[temp[0]]);

        label1.setLayoutX(360);
        label1.setLayoutY(2);
        label1.setFont(font);
        label1.setTextFill(Color.rgb(242, 216, 31));

        label2.setFont(font1);
        label2.setTextFill(Color.WHITE);
        label2.setLayoutX(550);
        label2.setLayoutY(60);
        label2.setVisible(false);

        rounds.setLayoutX(889);
        rounds.setLayoutY(207);
        rounds.setFont(font1);
        rounds.setTextFill(Color.rgb(66, 96, 245));
        time.setLayoutX(884);
        time.setLayoutY(287);
        time.setFont(font1);
        time.setTextFill(Color.rgb(66, 96, 245));

        rounds1.setLayoutX(889);
        rounds1.setLayoutY(257);
        rounds1.setFont(font1);
        rounds1.setTextFill(Color.rgb(66, 96, 245));
        time1.setLayoutX(884);
        time1.setLayoutY(337);
        time1.setFont(font1);
        time1.setTextFill(Color.rgb(66, 96, 245));

        blueBox.setArcWidth(7);
        blueBox.setArcHeight(7);
        blueBox.setFill(Color.rgb(174, 186, 245));
        blueBox.setStyle("-fx-stroke: #4260f5; -fx-stroke-width: 4");

        blueBox1.setArcWidth(7);
        blueBox1.setArcHeight(7);
        blueBox1.setFill(Color.rgb(174, 186, 245));
        blueBox1.setStyle("-fx-stroke: #4260f5; -fx-stroke-width: 4");

        for (int i = 0; i < 4; i++) {                                                                                   //hovering over or clicking on menu buttons
            int finalI = i;
            menuButtons[i].hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue){
                    playMusic(on[0]);
                    menuButtons[finalI].setFont(font1);
                } else {
                    menuButtons[finalI].setFont(font);
                }
            });
            menuButtons[i].setOnAction(event -> {
                if (finalI == 0){
                    clicked5[0] = clicked2[0];
                    clicked5[1] = clicked2[1];

                    if (!clicked5[0]){
                        gameMode1[0].setTextFill(Color.rgb(66, 96, 245));
                        gameMode1[0].setStyle("-fx-background-color: #aebaf5");
                        gameMode1[1].setStyle("-fx-background-color: #4260f5");
                        gameMode1[1].setTextFill(Color.rgb(42, 199, 212));
                        arrows7.setDisable(true);
                        arrows8.setDisable(true);
                        arrows9.setDisable(false);
                        arrows10.setDisable(false);
                    } else {
                        gameMode1[1].setTextFill(Color.rgb(66, 96, 245));
                        gameMode1[1].setStyle("-fx-background-color: #aebaf5");
                        gameMode1[0].setStyle("-fx-background-color: #4260f5");
                        gameMode1[0].setTextFill(Color.rgb(42, 199, 212));
                        arrows7.setDisable(false);
                        arrows8.setDisable(false);
                        arrows9.setDisable(true);
                        arrows10.setDisable(true);
                    }
                    rounds1.setText(rounds.getText());
                    time1.setText(time.getText());
                    stage.setScene(gameSetting);
                    stage.show();
                }
                else if (finalI == 1){
                    stage.setScene(setting);
                    stage.show();
                }
                else if (finalI == 2){
                    rectangle1.setVisible(true);
                    label2.setVisible(true);
                    ok.setVisible(true);
                    guide.setVisible(true);
                    disableEnable(true);
                } else {
                    Platform.exit();
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            settingButton[i].hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue){
                    if (!clicked1[finalI]) {
                        playMusic(on[0]);
                    }
                    settingButton[finalI].setTextFill(Color.rgb(245, 103, 2));
                } else {
                    if (!clicked1[finalI]){
                        settingButton[finalI].setTextFill(Color.rgb(252, 145, 68));
                    }
                }
            });
        }

        for (int i = 0; i < 11; i++) {                                                                                  //hovering over or clicking words
            int finalI = i;
            words[i].hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue){
                    playMusic(on[0]);
                    words[finalI].setStyle("-fx-background-color: #4260f5");
                    words[finalI].setTextFill(Color.rgb(42, 199, 212));
                } else {
                    if (!clicked[finalI]){
                        words[finalI].setStyle("-fx-background-color: #aebaf5");
                        words[finalI].setTextFill(Color.rgb(66, 96, 245));
                    }
                }
            });
            words[i].setOnAction(event -> {
                if (!clicked[finalI]){
                    words[finalI].setStyle("-fx-background-color: #4260f5");
                    words[finalI].setTextFill(Color.rgb(42, 199, 212));
                    words[finalI].setFont(font1);
                } else {
                    words[finalI].setStyle("-fx-background-color: #aebaf5");
                    words[finalI].setTextFill(Color.rgb(66, 96, 245));
                    words[finalI].setFont(font);
                }
                clicked[finalI] = !clicked[finalI];
            });
        }

        for (int i = 0; i < 2; i++) {                                                                                   //hovering over forward and backward buttons
            int finalI = i;
            forBack[i].hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue){
                    playMusic(on[0]);
                    if (finalI == 0){
                        forBack[finalI].setStyle("-fx-background-color:#51c734");
                    } else {
                        forBack[finalI].setStyle("-fx-background-color: #c73434");
                    }
                } else {
                    forBack[finalI].setStyle("-fx-background-color: #f2da30");
                }
            });

            forBack1[i].hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue){
                    playMusic(on[0]);
                    if (finalI == 0){
                        forBack1[finalI].setStyle("-fx-background-color: #51c734");
                    } else {
                        forBack1[finalI].setStyle("-fx-background-color: #c73434");
                    }
                } else {
                    forBack1[finalI].setStyle("-fx-background-color: #f2da30");
                }
            });

            forBack[i].setOnAction(event -> {
                cnt[0] = 0;
                for (int j = 0; j < 11; j++) {
                    if (clicked[j])
                        cnt[0]++;
                }
                if (finalI == 0 && cnt[0] >= 5){
                    stage.setScene(letterChoosing);
                    stage.show();
                }
                else if (finalI == 0 && cnt[0] < 5){
                    lastBox.setVisible(true);
                    lastText.setVisible(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.playFromStart();
                    pause.setOnFinished(event1 -> {
                        lastBox.setVisible(false);
                        lastText.setVisible(false);
                    });
                }
                else if (finalI == 1){
                    stage.setScene(gameSetting);
                    stage.show();
                }
            });

            forBack1[i].setOnAction(event -> {
                if (finalI == 0){
                    if (!began[0]){
                        began[0] = true;
                        theLetter[0] = label.getText();
                        label1.setText(str + toString(theLetter[0]));
                        chosen.add(theLetter[0]);
                        forBack1[1].setVisible(false);
                        try {
                            OutputStream out = new FileOutputStream("Game Saves/options.txt");
                            PrintWriter writer = new PrintWriter(out,true);

                            for (int j = 4; j >= 0; j--) {
                                if (clicked[j]) {
                                    writer.println(options[j].getText());
                                    vBox1.getChildren().add(options[j]);
                                }
                            }
                            for (int j = 9; j >= 5; j--) {
                                if (clicked[j]) {
                                    writer.println(options[j].getText());
                                    vBox1.getChildren().add(options[j]);
                                }
                            }
                            if (clicked[10]) {
                                writer.println(options[10].getText());
                                vBox1.getChildren().add(options[10]);
                            }
                            writer.println(theLetter[0]);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        for (int j = 0; j < cnt[0]; j++) {
                            root3.getChildren().addAll(fields[j]);
                        }
                        if (clicked6[0]){
                            for (int j = 0; j < cnt[0]; j++) {
                                fields[j][4].setDisable(true);
                            }
                            new Server(6666, timeline, cnt[0], fields);
                        }
                    }
                    stage.setScene(page);
                    stage.show();
                } else {
                    for (int j = 0; j < 11; j++) {
                        clicked[j] = false;
                        words[j].setStyle("-fx-background-color: #aebaf5");
                        words[j].setTextFill(Color.rgb(66, 96, 245));
                    }
                    began[0] = false;
                    stage.setScene(wordChoosing);
                    stage.show();
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            gameMode[i].hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue){
                    if (!clicked2[finalI]) {
                        playMusic(on[0]);
                    }
                    gameMode[finalI].setStyle("-fx-background-color: #4260f5");
                    gameMode[finalI].setTextFill(Color.rgb(42, 199, 212));
                } else {
                    if (!clicked2[finalI]){
                        gameMode[finalI].setStyle("-fx-background-color: #aebaf5");
                        gameMode[finalI].setTextFill(Color.rgb(66, 96, 245));
                    }
                }
            });

            gameMode[i].setOnAction(event -> {
                if (finalI == 0){
                    gameMode[0].setStyle("-fx-background-color: #4260f5");
                    gameMode[0].setTextFill(Color.rgb(42, 199, 212));
                    gameMode[1].setStyle("-fx-background-color: #aebaf5");
                    gameMode[1].setTextFill(Color.rgb(66, 96, 245));
                    arrows3.setDisable(false);
                    arrows4.setDisable(false);
                    arrows5.setDisable(true);
                    arrows6.setDisable(true);
                    clicked2[0] = true;
                    clicked2[1] = false;
                } else {
                    gameMode[1].setStyle("-fx-background-color: #4260f5");
                    gameMode[1].setTextFill(Color.rgb(42, 199, 212));
                    gameMode[0].setStyle("-fx-background-color: #aebaf5");
                    gameMode[0].setTextFill(Color.rgb(66, 96, 245));
                    arrows3.setDisable(true);
                    arrows4.setDisable(true);
                    arrows5.setDisable(false);
                    arrows6.setDisable(false);
                    clicked2[1] = true;
                    clicked2[0] = false;
                }
            });
        }

        for (int i = 0; i < 2; i++){
            int finalI = i;
            gameMode1[i].hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    if (!clicked5[finalI]) {
                        playMusic(on[0]);
                    }
                    gameMode1[finalI].setStyle("-fx-background-color: #4260f5");
                    gameMode1[finalI].setTextFill(Color.rgb(42, 199, 212));
                } else {
                    if (!clicked5[finalI]) {
                        gameMode1[finalI].setStyle("-fx-background-color: #aebaf5");
                        gameMode1[finalI].setTextFill(Color.rgb(66, 96, 245));
                    }
                }
            });
            gameMode1[i].setOnAction(event -> {
                if (finalI == 0){
                    gameMode1[0].setStyle("-fx-background-color: #4260f5");
                    gameMode1[0].setTextFill(Color.rgb(42, 199, 212));
                    gameMode1[1].setStyle("-fx-background-color: #aebaf5");
                    gameMode1[1].setTextFill(Color.rgb(66, 96, 245));
                    arrows7.setDisable(false);
                    arrows8.setDisable(false);
                    arrows9.setDisable(true);
                    arrows10.setDisable(true);
                    clicked5[0] = true;
                    clicked5[1] = false;
                } else {
                    gameMode1[1].setStyle("-fx-background-color: #4260f5");
                    gameMode1[1].setTextFill(Color.rgb(42, 199, 212));
                    gameMode1[0].setStyle("-fx-background-color: #aebaf5");
                    gameMode1[0].setTextFill(Color.rgb(66, 96, 245));
                    arrows7.setDisable(true);
                    arrows8.setDisable(true);
                    arrows9.setDisable(false);
                    arrows10.setDisable(false);
                    clicked5[1] = true;
                    clicked5[0] = false;
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            serverMode[i].hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue){
                    if (!clicked6[finalI]) {
                        playMusic(on[0]);
                    }
                    serverMode[finalI].setStyle("-fx-background-color: #4260f5");
                    serverMode[finalI].setTextFill(Color.rgb(42, 199, 212));
                } else {
                    if (!clicked6[finalI]){
                        serverMode[finalI].setStyle("-fx-background-color: #aebaf5");
                        serverMode[finalI].setTextFill(Color.rgb(66, 96, 245));
                    }
                }
            });

            serverMode[i].setOnAction(event -> {
                if (finalI == 0){
                    serverMode[0].setStyle("-fx-background-color: #4260f5");
                    serverMode[0].setTextFill(Color.rgb(42, 199, 212));
                    serverMode[1].setStyle("-fx-background-color: #aebaf5");
                    serverMode[1].setTextFill(Color.rgb(66, 96, 245));
                    changeAll2(false);
                    clicked6[0] = true;
                    clicked6[1] = false;
                } else {
                    serverMode[1].setStyle("-fx-background-color: #4260f5");
                    serverMode[1].setTextFill(Color.rgb(42, 199, 212));
                    serverMode[0].setStyle("-fx-background-color: #aebaf5");
                    serverMode[0].setTextFill(Color.rgb(66, 96, 245));
                    changeAll2(true);
                    clicked6[1] = true;
                    clicked6[0] = false;
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            settingButton[i].setOnAction(event -> {
                if (finalI == 0) {
                    settingButton[0].setTextFill(Color.rgb(245, 103, 2));
                    settingButton[1].setTextFill(Color.rgb(252, 145, 68));
                    changeAll(true);
                    buttonVolume.setVisible(false);
                    onOff[0].setVisible(false);
                    onOff[1].setVisible(false);
                    clicked1[0] = true;
                    clicked1[1] = false;
                } else {
                    settingButton[1].setTextFill(Color.rgb(245, 103, 2));
                    settingButton[0].setTextFill(Color.rgb(252, 145, 68));
                    changeAll(false);
                    buttonVolume.setVisible(true);
                    onOff[0].setVisible(true);
                    onOff[1].setVisible(true);
                    clicked1[1] = true;
                    clicked1[0] = false;
                }
            });
            onOff[i].hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    if (!clicked4[finalI]) {
                        playMusic(on[0]);
                    }
                    onOff[finalI].setStyle("-fx-background-color: #4260f5");
                    onOff[finalI].setTextFill(Color.rgb(42, 199, 212));
                } else {
                    if (!clicked4[finalI]) {
                        onOff[finalI].setStyle("-fx-background-color: #aebaf5");
                        onOff[finalI].setTextFill(Color.rgb(66, 96, 245));
                    }
                }
            });

            onOff[i].setOnAction(event -> {
                if (finalI == 0) {
                    on[0] = false;
                    onOff[0].setStyle("-fx-background-color: #4260f5");
                    onOff[0].setTextFill(Color.rgb(42, 199, 212));
                    onOff[1].setStyle("-fx-background-color: #aebaf5");
                    onOff[1].setTextFill(Color.rgb(66, 96, 245));
                    clicked4[0] = true;
                    clicked4[1] = false;
                } else {
                    on[0] = true;
                    onOff[1].setStyle("-fx-background-color: #4260f5");
                    onOff[1].setTextFill(Color.rgb(42, 199, 212));
                    onOff[0].setStyle("-fx-background-color: #aebaf5");
                    onOff[0].setTextFill(Color.rgb(66, 96, 245));
                    clicked4[1] = true;
                    clicked4[0] = false;
                }
            });
        }

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 5; j++) {
                int finalI = i;
                int finalJ = j;
                fields[i][j].setOnKeyReleased(event -> {
                    if (!fields[finalI][finalJ].getText().equals("")){
                        if (!fields[finalI][finalJ].getText().substring(0,1).equals(theLetter[0])){
                            fields[finalI][finalJ].setText("");
                        }
                    }
                });
            }
        }

        submit.hoverProperty().addListener((observable, oldValue, newValue) -> {                                        //hovering over submit button
            if (newValue){
                playMusic(on[0]);
                submit.setStyle("-fx-background-color: #c73434");
            } else {
                submit.setStyle("-fx-background-color: #f56702");
                submit.setTextFill(Color.rgb(242, 216, 31));
            }
        });

        ok.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                playMusic(on[0]);
                ok.setStyle("-fx-background-color: #0c0d0d");
            } else {
                ok.setStyle("-fx-background-color: #807f78");
            }
        });

        ok.setOnAction(event -> {
            rectangle1.setVisible(false);
            label2.setVisible(false);
            ok.setVisible(false);
            guide.setVisible(false);
            disableEnable(false);
        });

        arrows1.setOnAction(event -> {                                                                                  //clicking on arrow buttons
            if (temp[0] == 32){
                temp[0] = -1;
            }
            label.setText(letters[++temp[0]]);
        });

        arrows2.setOnAction(event -> {
            if (temp[0] == 0){
                temp[0] = 33;
            }
            label.setText(letters[--temp[0]]);
        });

        arrows1Copy.setOnAction(event -> {                                                                                  //clicking on arrow buttons
            if (temp[0] == 32){
                temp[0] = -1;
            }
            labelCopy.setText(letters[++temp[0]]);
        });


        arrows2Copy.setOnAction(event -> {
            if (temp[0] == 0){
                temp[0] = 33;
            }
            labelCopy.setText(letters[--temp[0]]);
        });

        arrows3.setOnAction(event -> {
            int temp1 = Integer.parseInt(rounds.getText());
            if (temp1 == 10){
                temp1 = 0;
            }
            rounds.setText(String.valueOf(temp1 + 1));
        });

        arrows4.setOnAction(event -> {
            int temp1 = Integer.parseInt(rounds.getText());
            if (temp1 == 1){
                temp1 = 11;
            }
            rounds.setText(String.valueOf(temp1 - 1));
        });

        arrows5.setOnAction(event -> {
            int temp1 = Integer.parseInt(time.getText());
            switch (temp1){
                case 5:
                case 10:
                    temp1 = temp1 + 5;
                    break;
                case 15:
                    temp1 = 30;
                    break;
                case 30:
                    temp1 = 60;
                    break;
                case 60:
                    temp1 = 5;
                    break;
            }
            time.setText(String.valueOf(temp1));
        });

        arrows6.setOnAction(event -> {
            int temp1 = Integer.parseInt(time.getText());
            switch (temp1){
                case 10:
                case 15:
                    temp1 = temp1 - 5;
                    break;
                case 30:
                    temp1 = 15;
                    break;
                case 60:
                    temp1 = 30;
                    break;
                case 5:
                    temp1 = 60;
                    break;
            }
            time.setText(String.valueOf(temp1));
        });

        arrows7.setOnAction(event -> {
            int temp1 = Integer.parseInt(rounds1.getText());
            if (temp1 == 10){
                temp1 = 0;
            }
            rounds1.setText(String.valueOf(temp1 + 1));
        });

        arrows8.setOnAction(event -> {
            int temp1 = Integer.parseInt(rounds1.getText());
            if (temp1 == 1){
                temp1 = 11;
            }
            rounds1.setText(String.valueOf(temp1 - 1));
        });

        arrows9.setOnAction(event -> {
            int temp1 = Integer.parseInt(time1.getText());
            switch (temp1){
                case 5:
                case 10:
                    temp1 = temp1 + 5;
                    break;
                case 15:
                    temp1 = 30;
                    break;
                case 30:
                    temp1 = 60;
                    break;
                case 60:
                    temp1 = 5;
                    break;
            }
            time1.setText(String.valueOf(temp1));
        });

        arrows10.setOnAction(event -> {
            int temp1 = Integer.parseInt(time1.getText());
            switch (temp1){
                case 10:
                case 15:
                    temp1 = temp1 - 5;
                    break;
                case 30:
                    temp1 = 15;
                    break;
                case 60:
                    temp1 = 30;
                    break;
                case 5:
                    temp1 = 60;
                    break;
            }
            time1.setText(String.valueOf(temp1 - 15));
        });

        apply.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                playMusic(on[0]);
                apply.setTextFill(Color.rgb(245, 103, 2));
            } else {
                apply.setTextFill(Color.rgb(252, 145, 68));
            }
        });
        apply.setOnAction(event -> {
            if (clicked2[0]){
                roundCount[0] = Integer.parseInt(rounds.getText());
            }
            else if (clicked2[1]){
                timeCount[0] = Integer.parseInt(time.getText());
            }
            stage.setScene(menu);
            stage.show();
        });

        goBack.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                playMusic(on[0]);
                goBack.setTextFill(Color.rgb(245, 103, 2));
            } else {
                goBack.setTextFill(Color.rgb(252, 145, 68));
            }
        });
        goBack.setOnAction(event -> {
            stage.setScene(menu);
            stage.show();
        });

        justDo.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                playMusic(on[0]);
                justDo.setStyle("-fx-background-color: #51c734");
            } else  {
                justDo.setStyle("-fx-background-color: #aebaf5");
            }
        });
        justDo1.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                playMusic(on[0]);
                justDo1.setStyle("-fx-background-color: #51c734");
            } else  {
                justDo1.setStyle("-fx-background-color: #aebaf5");
            }
        });

        resume.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                playMusic(on[0]);
                resume.setTextFill(Color.rgb(1, 133, 12));
            } else {
                resume.setTextFill(Color.rgb(109, 191, 117));
            }
        });
        resume.setOnAction(event -> {
            if (clicked5[0]){
                roundCount[0] = Integer.parseInt(rounds1.getText());
            }
            else if (clicked5[1]){
                timeCount[0] = Integer.parseInt(time1.getText());
            }
            isServer[0] = clicked6[0];
            if (!isServer[0]){
                try {
                    File file3 = new File("Game Saves/options.txt");
                    Scanner in = new Scanner(file3);

                    while (in.hasNext()){
                        String s = in.nextLine();
                        for (int i = 0; i < 11; i++) {
                            if (s.equals(options[i].getText())){
                                vBox1.getChildren().add(options[i]);
                                cnt[0]++;
                            }
                        }
                        for (String letter : letters) {
                            if (s.equals(letter)) {
                                label1.setText(str + s);
                                theLetter[0] = s;
                            }
                        }
                    }
                } catch (IOException ignored) {}
                for (int j = 0; j < cnt[0]; j++) {
                    root3.getChildren().addAll(fields[j]);
                }
                timeline.play();
                root3.getChildren().removeAll(submit,arrows1Copy,arrows2Copy,labelCopy,rectangleCopy);
                new Client("127.0.0.1", 6666, fields, cnt[0], timeline);
                stage.setScene(page);
                stage.show();
            } else {
                if (clicked5[0]){
                    roundCount[0] = Integer.parseInt(rounds1.getText());
                    timeCount[0] = 300;
                } else {
                    roundCount[0] = 1000;
                    timeCount[0] = Integer.parseInt(time1.getText());
                }
                stage.setScene(wordChoosing);
                stage.show();
            }
        });

        submit.setDisable(true);
        rectangleCopy.setVisible(false);
        blueBox.setVisible(false);
        blueBox1.setVisible(false);
        arrows1Copy.setVisible(false);
        arrows2Copy.setVisible(false);
        labelCopy.setVisible(false);
        justDo.setVisible(false);
        justDo1.setVisible(false);
        changing.setVisible(false);
        host.setVisible(false);
        guest.setVisible(false);
        hostScore.setVisible(false);
        guestScore.setVisible(false);
        winner.setVisible(false);
        loser.setVisible(false);
        drawer.setVisible(false);
        lastBox.setVisible(false);
        lastText.setVisible(false);

        root1.getChildren().addAll(imageView,vBox,rectangle1,label2,ok,guide);
        root2.getChildren().addAll(hBox1,words[5],words[6],words[7],words[8],words[9],words[10],text,forBack[0],forBack[1],lastBox,lastText);
        root3.getChildren().addAll(imageView1,vBox1,lines[0],lines[1],lines[2],lines[3],lines[4],lines1[0],lines1[1],lines1[2],
        lines1[3],lines1[4],lines1[5],lines1[6],lines1[7],lines1[8],lines1[9],rectangle2,submit,timer);
        for (int i = 0; i < 11; i++) {
            root3.getChildren().addAll(texts[i]);
        }
        root3.getChildren().add(scoreBoard);
        root3.getChildren().addAll(blueBox,rectangleCopy,arrows1Copy,arrows2Copy,labelCopy,justDo,label1,changing,blueBox1,justDo1,hostScore,guestScore,host,guest,drawer,winner,loser);
        root4.getChildren().addAll(forBack1[0],forBack1[1],text1,rectangle,arrows1,arrows2,label);
        root5.getChildren().addAll(line,vBox2,hBox3,gameMode[0],gameMode[1],onOff[0],onOff[1],buttonVolume,box,box1,arrows3,arrows4,arrows5,
                arrows6,rounds,time,apply);
        root6.getChildren().addAll(bigBox,vBox3,gameMode1[0],gameMode1[1],box2,box3,arrows7,arrows8,arrows9,arrows10,rounds1,time1,goBack,resume,serverMode[0],serverMode[1]);
        stage.setScene(menu);
        stage.show();
    }
}