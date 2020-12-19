package gameClient;


import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class MyPanel extends JPanel {
    private Arena _ar;
    private gameClient.util.Range2Range _w2f;
    private Graphics2D g2D;
    private Image agent;
    private Image pokemon;
    private Image pokemon2;
    private Image background;
    private Image node;
    private Image timer;

    public MyPanel(Arena ar)
    {
        this._ar=ar;
        this.agent=new ImageIcon("data/Ash.png").getImage();
        this.pokemon=new ImageIcon("data/pichu.png").getImage();
        this.background=new ImageIcon("data/backround.jpg").getImage();
        this.node = new ImageIcon("data/tree.jpg").getImage();
        //this.drawTimer(this);
        this.timer = new ImageIcon("data/Pokemon-Logo2.png").getImage();
        this.pokemon2 = new ImageIcon("data/charm.jpg").getImage();

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paint(g);

    }
    public void update()
    {
        updatePanel();
    }

    public void updatePanel() {
        Range rx = new Range(20,this.getWidth()-20);
        Range ry = new Range(this.getHeight()-10,150);
        Range2D frame = new Range2D(rx,ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g,frame);
    }
    public void paint(Graphics g) {
        g2D=(Graphics2D)g;
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);
        g2D.drawImage(background, 0, 0, w, h, null);
        drawPokemons(g);
        drawGraph(g);
        drawAgants(g);
        drawInfo(g);
        // drawTimer(g);
    }

    private void drawPokemons(Graphics g) {
        g2D=(Graphics2D)g;
        List<CL_Pokemon> fs = _ar.getPokemons();
        if(fs!=null)
        {
            Iterator<CL_Pokemon> itr = fs.iterator();
            while(itr.hasNext())
            {
                CL_Pokemon f = itr.next();
                Point3D c = f.getLocation();

                int r=10;
                //  if(f.getType()<0) {g.setColor(Color.orange);}
                if(c!=null && f.getType()==-1)
                {
                    geo_location fp = this._w2f.world2frame(c);
                    g2D.drawImage(pokemon,(int)fp.x()-r-10, (int)fp.y()-r-10, 5*r, 5*r,null);
                }
                else{
                    geo_location fp = this._w2f.world2frame(c);
                    g2D.drawImage(pokemon2,(int)fp.x()-r-10, (int)fp.y()-r-10, 5*r, 5*r,null);

                }
            }
        }
    }

    private void drawGraph(Graphics g) {
        g2D=(Graphics2D)g;
        directed_weighted_graph gg = _ar.getGraph();
        Iterator<node_data> iter = gg.getV().iterator();
        while(iter.hasNext()) {
            node_data n = iter.next();
            g.setColor(Color.black);
            drawNode(n,5,g);
            Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
            while(itr.hasNext()) {
                edge_data e = itr.next();
                g.setColor(Color.black);
                drawEdge(e, g);
            }
        }
    }

    private void drawEdge(edge_data e, Graphics g) {
        g2D=(Graphics2D)g;
        directed_weighted_graph gg = _ar.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
    }

    private void drawNode(node_data n, int r, Graphics g) {
        g2D=(Graphics2D)g;
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
       // g.drawImage(node,(int)fp.x()-r-10, (int)fp.y()-r-10, 5*r, 5*r,null);
        g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
        g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
    }

    private void drawAgants(Graphics g)
    {
        g2D=(Graphics2D)g;
        List<CL_Agent> rs = _ar.getAgents();
        int i=0;
        while(rs!=null && i<rs.size())
        {
            geo_location c = rs.get(i).getLocation();
            int r=8;
            i++;
            if(c!=null)
            {
                geo_location fp = this._w2f.world2frame(c);
                g2D.drawImage(agent,(int)fp.x()-r-10, (int)fp.y()-r-10, 5*r, 5*r,null);
            }
        }
    }

    private void drawInfo(Graphics g) {
        g2D=(Graphics2D)g;
        List<String> str = _ar.get_info();
        String dt = "none";
        if(_ar.getTime()!=null) {
            g2D.setFont(new Font("Timer", Font.PLAIN, 30));
            g2D.drawString(_ar.getTime(), 380, 50);
            // g2D.drawImage(timer,350,0,null);
            for (int i = 0; i < str.size(); i++) {
                g2D.drawString(str.get(i) + " dt: " + dt, 100, 60 + i * 20);
            }
        }
    }
    public void drawTimer(Graphics g) {
        g.setColor(Color.black.darker());

       // JPanel panel = new JPanel();
       // panel.setBounds(50,75,300,300);
       // JLabel label = new JLabel("Timer: " + " ",timer,JLabel.CENTER);
       // panel.add(label);
       // this.add(panel);
        g.setFont(new Font("Timer: ",Font.ROMAN_BASELINE,30));
        g.drawString("Time to end: ", 300, 50);
    }
}