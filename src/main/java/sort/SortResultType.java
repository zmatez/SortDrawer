package sort;

import java.awt.*;

public enum SortResultType {
    DEFAULT("default",Color.WHITE),
    CHECKING("checking",Color.RED),
    GETTING("getting",Color.PINK),
    SORTING("sorting",Color.GREEN);

    private String name;
    private Color color;
    private boolean withSound = false;

    private SortResultType(String name, Color color){
        this.name = name;
        this.color = color;
    }
    private SortResultType(String name, Color color, boolean withSound){
        this.name = name;
        this.color = color;
        this.withSound = withSound;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public boolean isWithSound() {
        return withSound;
    }
}
