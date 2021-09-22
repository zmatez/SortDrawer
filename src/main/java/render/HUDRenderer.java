package render;

import setup.Main;
import sort.SortCollection;
import sort.SortValue;
import util.Utilities;

import javax.swing.plaf.synth.SynthTableUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class HUDRenderer {
    private int width, height;
    private BufferedImage bufferedImage;
    private Graphics2D graphics;
    private int prevPoint = 0;
    private int timer = 0, maxTimer = 500;
    private Color CHECKING_COLOR = new Color(24, 65, 199,100);

    public HUDRenderer(){

    }

    public void createBufferedImage(int width, int height) {
        Main.LOGGER.debug("Creating buffered image with size: " + width + "x" + height);
        bufferedImage = RenderUtils.createHardwareAcceleratedImage(width,height,true);
        graphics = (Graphics2D)bufferedImage.getGraphics();
        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        this.width=width;
        this.height=height;
    }

    public void render(SortCollection collection, boolean refresh) {
        if(collection == null){
            int[] data = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
            Arrays.fill(data, 0x00000000);//transparency
            return;
        }
        if(refresh) {
            int[] data = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
            Arrays.fill(data, 0x00000000);//transparency
        }
        if(collection.isDirty() && collection.lastChanged != null){
            int[] data = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
            Arrays.fill(data, 0x00000000);//transparency

            int value = Utilities.scaleBetween(collection.lastChanged.getValue(),0,this.height,0,collection.getMax());
            int offset = 3;
            graphics.setColor(CHECKING_COLOR);
            graphics.drawRect(value-offset,0,value+offset,height);
            prevPoint = value;
        }

        timer++;
        if(timer > maxTimer){
            timer = 0;
        }
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
        createBufferedImage(width, height);
    }
}
