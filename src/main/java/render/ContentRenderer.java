package render;

import setup.Main;
import sort.SortCollection;
import sort.SortValue;
import util.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Arrays;

public class ContentRenderer {
    private int width, height;
    private BufferedImage bufferedImage;
    private Graphics2D graphics;

    private Color LIGHT = new Color(255, 255, 255), DARK = new Color(0, 0, 0), BACKGROUND = new Color(0, 0, 0);

    public ContentRenderer(){

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
            graphics.clearRect(0, 0, width, height);
            return;
        }
        if(refresh) {
            graphics.clearRect(0, 0, width, height);
        }
        if(collection.isDirty()){
            int length = collection.getLength();

            for(int x = 0; x < this.width; x++){
                int index = Utilities.scaleBetween(x,0,length,0,this.width);
                SortValue sortValue = collection.values[index];
                int value = Utilities.scaleBetween(sortValue.getValue(),0,this.height,0,collection.getMax());

                graphics.setColor(BACKGROUND);
                graphics.fillRect(x,0,1,value);
                graphics.setColor(sortValue.getType().getColor());
                graphics.fillRect(x,value,1,height);
            }

            collection.rendered();
        }
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
