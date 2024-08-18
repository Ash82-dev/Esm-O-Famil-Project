import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import java.net.*;
import java.io.*;

public class Server {
    static int count;
    static int round = 4;
    static TextField[][] fields;
    static String[] str;
    static ServerSocket server;
    static Socket socket;
    static InputStream fromClient;
    static OutputStream toClient;
    static DataInputStream reader;
    static DataOutputStream writer;
    static int k = 0;
    static int score = 0;
    static int hisScore;
    private final int needed;

    public static void sendWords (){
        try {
            writer = new DataOutputStream(toClient);
            for (int i = 0; i < count; i++) {
                writer.writeUTF(fields[i][round].getText());
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
    public Server(int port, Timeline timeline, int cnt, TextField[][] fields) {
        count = cnt;
        Server.fields = fields;
        needed = Game.roundCount[0];
        Runnable runnable = () -> {
            try {
                server = new ServerSocket(port);
                socket = server.accept();
                for (int i = 0; i < count; i++) {
                    Game.fields[i][4].setDisable(false);
                }
                Game.submit.setDisable(false);
                timeline.play();
                fromClient = socket.getInputStream();
                toClient = socket.getOutputStream();
                reader = new DataInputStream(fromClient);
                writer = new DataOutputStream(toClient);
                writer.flush();
                Game.submit.setOnAction(event -> {
                    timeline.stop();
                    try {
                        writer.writeUTF("1");
                    } catch (IOException ignored) {}
                    receiveWords();
                    sendWords();
                    score = Game.judge(str,cnt,score);
                    for (int i = 0; i < count; i++) {
                        Game.fields[i][round].setDisable(true);
                    }
                    if (Game.roundCount[0] == 0){
                        try {
                            writer.writeUTF("10");
                            String p = reader.readLine();
                            if (needed == 1){
                                hisScore = Integer.parseInt(p);
                            } else if (p.length() == needed * 2) {
                                hisScore = Integer.parseInt(p.substring((needed * 2) - 2, needed * 2));
                            } else if (p.length() == (needed * 2) - 1) {
                                hisScore = Integer.parseInt(p.substring((needed * 2) - 2, (needed * 2) - 1));
                            } else if (p.length() == (needed * 2) + 1){
                                hisScore = Integer.parseInt(p.substring((needed * 2) - 2, (needed * 2) + 1));
                            }
                            writer.writeUTF(String.valueOf(score));
                        } catch (IOException ignored){}
                        if (hisScore > score){
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
                        Game.hostScore.setText(String.valueOf(score));
                        Game.guestScore.setText(String.valueOf(hisScore));
                    }
                    Game.showThem(true);
                    Game.submit.setDisable(true);
                });
                Game.justDo.setOnAction(event -> {
                    Game.showThem(false);
                    try {
                        timeline.play();
                        writer.writeUTF("2");
                        writer.writeUTF(Game.labelCopy.getText());
                        Game.label1.setText(Game.str + Game.labelCopy.getText());
                        Game.theLetter[0] = Game.labelCopy.getText();
                        Game.chosen.add(Game.theLetter[0]);
                        Game.submit.setDisable(false);
                        for (int i = 0; i < count; i++) {
                            Game.fields[i][round].setDisable(false);
                        }
                    } catch (IOException ignored) {}
                });
                Game.justDo1.setOnAction(event -> Platform.exit());
                Game.millis.addListener(observable -> {
                    boolean f = false;
                    for (String s: Game.chosen) {
                        if (s.equals(Game.labelCopy.getText())){
                            Game.justDo.setDisable(true);
                            f = true;
                        }
                    }
                    if (!f){
                        Game.justDo.setDisable(false);
                    }
                    if ((Game.minutes == Game.timeCount[0])){
                        timeline.stop();
                        try {
                            writer.writeUTF("1");
                            receiveWords();
                            sendWords();
                            score = Game.judge(str,cnt,score);
                            writer.writeUTF("10");
                            hisScore = Integer.parseInt(reader.readLine());
                            writer.writeUTF(String.valueOf(score));
                        } catch (IOException ignored) {}
                        Game.submit.setDisable(true);
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
                        Game.hostScore.setText(String.valueOf(score));
                        Game.guestScore.setText(String.valueOf(hisScore));
                    }
                });
            } catch (IOException ignored) {}
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}