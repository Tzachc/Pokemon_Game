package gameClient;

import java.io.FileInputStream;

import javazoom.jl.player.Player;

class SimplePlayer implements Runnable{
    public static void main(String[] args) throws Exception {
       // Thread player = new Thread(new SimplePlayer());
       // player.start();
    }

    @Override
    public void run() {
        try {
            FileInputStream fis = new FileInputStream("data/song.mp3");
            Player playMP3 = new Player(fis);
            playMP3.play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
