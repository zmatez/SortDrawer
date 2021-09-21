package render;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderPanel extends JPanel {

    private BufferedImage content;
    private BufferedImage hud;
    private BufferedImage data;
    private int width, height;

    public RenderPanel(BufferedImage image, BufferedImage hud, BufferedImage data, int width, int height) {
        this.content = image;
        this.hud=hud;
        this.data=data;
        this.width=width;
        this.height=height;
    }

    public void setImage(BufferedImage image, BufferedImage hud, BufferedImage data, int width, int height) {
        this.content = image;
        this.hud=hud;
        this.data=data;
        this.width=width;
        this.height=height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(content, 0, 0,width,height, null);
        //g.drawImage(hud, 0, 0,width,height, null);
        g.drawImage(data, 0, 0, null);
    }

}