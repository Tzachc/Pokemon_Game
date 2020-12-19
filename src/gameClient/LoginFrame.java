package gameClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public class LoginFrame extends JFrame implements ActionListener {

    private static boolean active = true;
    private static int stage = 0;
    private static int id = -1;
    private static int index =0;
    private static JComboBox stageNumber;
    private static JButton no_ID_game;
    private static JButton startButtom;
    private static JButton exitButton;
    private static JTextField TXTbuttom;
    private static JTextField TXTbuttomS;
    private static JFrame login;
    private Image loginBack;
    private Graphics2D g2D;
    private JPanel panel;
    Image img = Toolkit.getDefaultToolkit().getImage("data/loginBack.png");

    LoginFrame() {
        this.loginBack = new ImageIcon("data/loginBack.png").getImage();

    }

    LoginFrame(String a){
        super(a);

    }
    public int getID() {
        return id;
    }

    public int getStage() {
        return Integer.parseInt(TXTbuttomS.getText());
    }

    public boolean isActiive() {
        return active;
    }

    public void exit() {
        index = index++;
        login.dispose();
    }
    public int getIndex(){
        return index;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stageNumber) {
            int scenario = stageNumber.getSelectedIndex();
            stage = scenario;
        }
        if (e.getSource() == no_ID_game) {
            active = false;
        }
        if (e.getSource() == startButtom) {
            try {
                int id = Integer.parseInt(TXTbuttom.getText());
                if (id > 0) {
                    LoginFrame.id = id;
                    active = false;
                }
            } catch (Exception ex) {
            }
        }
        if(e.getSource() == exitButton){
            active = false;
            exit();

        }
    }

    public void loginPanel() {
       // JPanel panel = new JPanel();
        Image img = Toolkit.getDefaultToolkit().getImage("data/loginBack.png");

        panel = new JPanel(){
            public void paintComponent(Graphics g){
                //Image img = Toolkit.getDefaultToolkit().getImage("data/loginBack.png");
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        //panel.setBackground(Color.orange);
        login = new JFrame();
        login.setTitle("Welcome!");
        login.setSize(500, 300);
        panel.setLayout(null);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // ImageIcon img = new ImageIcon("loginBack.jpg");
        login.setLocationRelativeTo(null);
        login.add(panel);
        JLabel user = new JLabel("ID:");
        user.setFont(new Font("Ariel",Font.BOLD,20));
        user.setForeground(Color.WHITE);
        user.setBounds(120, 60, 80, 25);

        JLabel scene = new JLabel("stage:");
        scene.setFont(new Font("Ariel",Font.BOLD,25));
        scene.setForeground(Color.WHITE);
        scene.setBounds(90, 95, 80, 25);

        TXTbuttom = new JTextField(20);
        TXTbuttom.setBounds(160, 60, 165, 25);

        TXTbuttomS = new JTextField(20);
        TXTbuttomS.setBounds(160,100,165,25);

        no_ID_game = new JButton("No ID game");
        no_ID_game.addActionListener(new LoginFrame());
        no_ID_game.setBounds(100, 140, 120, 30);

        startButtom = new JButton("Login");
        startButtom.addActionListener(new LoginFrame());
        startButtom.setBounds(240, 140, 120, 30);

        exitButton = new JButton("Exit menu");
        exitButton.addActionListener(new LoginFrame());
        exitButton.setBounds(175,180,120,30);

        panel.add(user);
        panel.add(scene);
        panel.add(TXTbuttom);
        panel.add(TXTbuttomS);
        panel.add(no_ID_game);
        panel.add(startButtom);
        panel.add(exitButton);
        login.setVisible(true);
    }

}

