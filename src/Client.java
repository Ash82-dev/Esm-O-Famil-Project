import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import java.net.*;
import java.io.*;

public class Client {
    static int count;
    static int round = 4;
    static TextField[][] fields;
    static String[] str;
    static String theLetter;
    static Socket socket;
    static InputStream fromServer;
    static OutputStream toServer;
    static DataInputStream reader;
    static DataOutputStream out;
    static int k = 0;
    static int score = 0;
    static int hisScore;

    public static void sendWords (){
        try {
            out = new DataOutputStream(toServer);
            for (int i = 0; i < count; i++) {
                out.writeUTF(fields[i][round].getText());
            }
            round--;
            if (round == 0){
                round = 4;
            }
        } catch (IOException ignored){}
    }
    public static void receiveWords (){
        try {
            str = new String[count];
            for (int i = 0; i < count; i++) {
                str[k++] = reader.readUTF();
            }
            k = 0;
        } catch (IOException ignored){}
    }
    public Client (String address, int port, TextField[][] fields, int cnt, Timeline timeline) {
        Client.fields = fields;
        count = cnt;
        Runnable runnable = () -> {
            try {
                socket = new Socket(address, port);
                fromServer = socket.getInputStream();
                toServer = socket.getOutputStream();
                reader = new DataInputStream(fromServer);
                out = new DataOutputStream(toServer);
                out.flush();
                Game.justDo.setOnAction(event -> {
                    try {
                        out.writeUTF("2");
                        Game.label1.setText(Game.str + theLetter);
                        Game.justDo.setDisable(true);
                    } catch (IOException ignored) {}
                    Game.showThem(false);
                    Game.changing.setVisible(false);
                    for (int i = 0; i < count; i++) {
                        Game.fields[i][round].setDisable(false);
                    }
                });
                Game.justDo.setDisable(true);
                Game.justDo1.setOnAction(event -> Platform.exit());
                while (true){
                    String s = reader.readUTF();
                    switch (s) {
                        case "1":
                            timeline.stop();
                            sendWords();
                            receiveWords();
                            score = Game.judge(str, cnt, score);
                            Game.showThem(true);
                            Game.changing.setVisible(true);
                            for (int i = 0; i < count; i++) {
                                Game.fields[i][round].setDisable(true);
                            }
                            break;
                        case "2":
                            timeline.play();
                            theLetter = reader.readUTF();
                            Game.theLetter[0] = theLetter;
                            Game.justDo.setDisable(false);
                            break;
                        case "10":
                            timeline.stop();
                            DataOutputStream dataOutputStream = new DataOutputStream(toServer);
                            PrintWriter printWriter = new PrintWriter(dataOutputStream,true);
                            printWriter.println(score);
                            hisScore = Integer.parseInt(reader.readUTF());
                            if (hisScore > score) {
                                Game.loser.setVisible(true);
                            } else if (hisScore < score){
                                Game.winner.setVisible(true);
                            } else {
                                Game.drawer.setVisible(true);
                            }
                            for (int i = 0; i < 11; i++) {
                                for (int j = 0; j < 5; j++) {
                                    Game.fields[i][j].setDisable(true);
                                }
                            }
                            Game.justDo1.setVisible(true);
                            Game.blueBox1.setVisible(true);
                            Game.host.setVisible(true);
                            Game.guest.setVisible(true);
                            Game.hostScore.setVisible(true);
                            Game.guestScore.setVisible(true);
                            Game.hostScore.setText(String.valueOf(hisScore));
                            Game.guestScore.setText(String.valueOf(score));
                            break;
                    }
                }
            } catch (IOException ignored){}
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}