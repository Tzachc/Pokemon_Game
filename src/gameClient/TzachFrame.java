package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

public class TzachFrame extends JFrame{
    private int _ind;
    private Arena _ar;
    //private gameClient.util.Range2Range _w2f;
    MyPanel jPanel;
    //private Arena _ar;
    private gameClient.util.Range2Range _w2f;
    private game_service _game;
    private Image graphImg;

    private static boolean _open = true;
    private static int _scenario = 0;
    private static int _id = -1;

    TzachFrame(){}

    TzachFrame(String a) {
        super(a);
        int _ind = 0;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon image = new ImageIcon("data/Pokemon-Logo.png");
        this.setIconImage(image.getImage());

    }

    public void update(Arena ar) {
        this._ar = ar;
        //updateFrame();
        initPanel();
        jPanel.update();
    }

    private void initPanel() {
        jPanel = new MyPanel(_ar);
        this.add(jPanel);
        this.setLocationRelativeTo(null);

    }

    public void paint(Graphics g) {
        jPanel.updatePanel();
        jPanel.repaint();
    }

}

