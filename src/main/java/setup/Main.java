package setup;

import log.Logger;
import render.ContentRenderer;
import render.DataRenderer;
import render.HUDRenderer;
import render.RenderPanel;
import sort.SortCollection;
import sort.SortResultType;
import sort.sorters.BubbleSort;
import sort.sorters.HeapSort;
import sort.sorters.QuickSort;
import sort.sorters.ShellSort;
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
    private HUDRenderer hudRenderer;
    private DataRenderer dataRenderer;
    private static boolean refreshVisuals = true;

    public static Logger LOGGER;

    public Thread renderThread, sortThread;

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
        this.hudRenderer= new HUDRenderer();
        this.hudRenderer.createBufferedImage(this.width,this.height);
        this.dataRenderer = new DataRenderer();
        this.dataRenderer.createBufferedImage(this.width,this.height);

        //
        this.renderPanel = new RenderPanel(this.contentRenderer.getBufferedImage(),this.hudRenderer.getBufferedImage(),this.dataRenderer.getBufferedImage(),this.width,this.height);
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

            //BubbleSort sort = new BubbleSort(this.sortCollection);
            //BubbleSort sort = new BubbleSort(this.sortCollection);
            //ShellSort sort = new ShellSort(this.sortCollection);
            QuickSort sort = new QuickSort(this.sortCollection);
            while (true){
                //
                sort.process();

                sort.reset();

                if(sortCollection.valid == sortCollection.getLength()){
                    sortCollection.randomize(sort);
                }
            }
        });
        this.sortCollection = SortCollection.create(this,2000,2000);

        //
        this.renderThread.start();
        this.sortThread.start();
    }

    private void renderApp(){
        this.renderPanel.invalidate();
        this.contentRenderer.render(sortCollection, refreshVisuals);
        this.hudRenderer.render(sortCollection,refreshVisuals);

        String text = "";
        if(this.sortCollection != null){
            text = "Sorted: " + sortCollection.lastSorted + "\nScanned: " + sortCollection.lastScanned + "\nValid: " + sortCollection.valid;
        }
        this.dataRenderer.render(text,refreshVisuals,showKeybindings,18);

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

                contentRenderer.setSize(width, height);
                hudRenderer.setSize(width, height);
                dataRenderer.setSize(width, height);

                renderPanel.setImage(contentRenderer.getBufferedImage(),hudRenderer.getBufferedImage(),dataRenderer.getBufferedImage(),width,height);
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
