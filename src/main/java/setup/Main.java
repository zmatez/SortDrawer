package setup;

import log.Logger;
import render.ContentRenderer;
import render.DataRenderer;
import render.RenderPanel;
import sort.SortCollection;
import sort.SortResultType;
import util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    private static JFrame frame;
    private static Main instance;

    public int width, height;

    private RenderPanel renderPanel;
    private ContentRenderer contentRenderer;
    private DataRenderer dataRenderer;
    private static boolean refreshVisuals = true;

    public static Logger LOGGER;

    public Thread renderThread, sortThread;

    private int sortDelay = 100;

    //sort
    public SortCollection sortCollection = null;

    //metering
    private int ups;
    private long time;

    //keybindings
    public static boolean isAltDown = false;
    public static boolean isControlDown = false;
    public static boolean showKeybindings = false;

    //mouse
    public static int clickX, clickY;
    public static int mouseX, mouseY;
    public static boolean dragging;

    public static void main(String... args){
        LOGGER = new Logger();
        LOGGER.progress("Starting app");
        frame = new JFrame();
        frame.setSize(1600, 900);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("SortDraw - by matez");
        instance = new Main();

        LOGGER.success("Finished");
    }

    public Main(){
        this.width = frame.getWidth();
        this.height = frame.getHeight();

        this.contentRenderer = new ContentRenderer();
        this.contentRenderer.createBufferedImage(this.width,this.height);
        this.dataRenderer = new DataRenderer();
        this.dataRenderer.createBufferedImage(this.width,this.height);

        //
        this.renderPanel = new RenderPanel(this.contentRenderer.getBufferedImage(),null,this.dataRenderer.getBufferedImage(),this.width,this.height);
        this.renderPanel.setBounds(0, 0, width, height);
        this.renderPanel.setBackground(Color.BLACK);
        frame.add(this.renderPanel);
        frame.invalidate();
        frame.validate();
        frame.repaint();
        //
        setupListeners();

        LOGGER.success("Ready");
        loop();
    }

    private void loop(){
        this.renderThread = new Thread(() -> {
            while(true){
                renderApp();

                if (time != 0) {
                    ups = (int) (System.currentTimeMillis() - time);
                }
                time = System.currentTimeMillis();
                frame.setTitle("SortDraw - by matez   |   " + ups + "msu");
            }
        });

        this.sortThread = new Thread(() -> {

            /*
            int i = 0;
            boolean b = true;
            while(true){
                this.sortCollection.values[i].setType(SortResultType.DEFAULT);
                if(b){
                    i++;
                }else{
                    i--;
                }

                if(i <= 0){
                    b = true;
                }else if(i+1 >= this.sortCollection.getLength()){
                    b = false;
                }

                this.sortCollection.values[i].setType(SortResultType.CHECKING);

                this.sortCollection.markDirty();


                try {
                    Thread.sleep(sortDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

            while (true){
                //
                int n = sortCollection.getLength();
                int temp = 0;
                for(int i=0; i < n; i++){
                    for(int j=1; j < (n-i); j++){
                        sortCollection.values[j].setType(SortResultType.CHECKING);
                        sortCollection.markDirty();
                        Utilities.waitNanos(sortDelay);
                        if(sortCollection.values[j-1].getValue() > sortCollection.values[j].getValue()){
                            //swap elements
                            temp = sortCollection.values[j-1].getValue();
                            sortCollection.values[j-1].setType(SortResultType.SORTING);
                            sortCollection.markDirty();
                            Utilities.waitNanos(sortDelay);

                            sortCollection.values[j-1].setValue(sortCollection.values[j].getValue());
                            sortCollection.values[j].setValue(temp);
                            sortCollection.values[j].setType(SortResultType.SORTING);
                            sortCollection.markDirty();
                            Utilities.waitNanos(sortDelay);

                            sortCollection.values[j-1].setType(SortResultType.DEFAULT);
                            sortCollection.markDirty();
                            Utilities.waitNanos(sortDelay);

                        }
                        sortCollection.values[j].setType(SortResultType.DEFAULT);
                        sortCollection.markDirty();
                        Utilities.waitNanos(sortDelay);

                    }
                }
            }
        });
        this.sortCollection = SortCollection.create(this,2000,100);

        //
        this.renderThread.start();
        this.sortThread.start();
    }

    private void renderApp(){
        this.renderPanel.invalidate();
        this.contentRenderer.render(sortCollection, refreshVisuals);
        this.dataRenderer.render("Test",refreshVisuals,showKeybindings,18);

        //

        this.renderPanel.validate();
        this.renderPanel.repaint();

        refreshVisuals = false;
    }

    public void setupListeners() {
        renderPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        renderPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                clickX = e.getX();
                clickY = e.getY();
                dragging = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                renderPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                dragging = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                renderPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                mouseX = -1;
                mouseY = -1;
                dragging = false;
            }
        });

        renderPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

        });
        renderPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                keybinding(e,false);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keybinding(e,true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keybinding(e,false);
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                if (evt.getComponent() != frame) {
                    return;
                }

                width = frame.getWidth();
                height = frame.getHeight();

                renderPanel.setBounds(0, 0, width, height);
                frame.invalidate();
                frame.validate();
                frame.repaint();
            }
        });
        renderPanel.setFocusable(true);
        renderPanel.requestFocus();
    }

    private void keybinding(KeyEvent e, boolean press){
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //clickedNoise=-1;
        }


        showKeybindings = e.isShiftDown();
        isControlDown = e.isControlDown();
        isAltDown = e.isAltDown();
    }
}
