package sort;

import setup.Main;
import util.Utilities;

public class SortCollection {
    private Main main;
    private int length;
    private int max;
    public SortValue[] values;

    private boolean dirty = true;

    public SortCollection(Main main, int length, int max){
        this.main = main;
        this.length = length;
        this.max = max;

        values = new SortValue[length];
    }

    public static SortCollection create(Main main, int length, int max){
        SortCollection collection = new SortCollection(main, length, max);

        for(int i = 0; i < length; i++){
            int random = Utilities.rint(0,max);
            collection.values[i] = new SortValue(random);
        }

        return collection;
    }

    public int getLength() {
        return length;
    }

    public int getMax() {
        return max;
    }

    public void markDirty(){
        this.dirty = true;
    }

    public void rendered(){
        this.dirty = false;
    }

    public boolean isDirty() {
        return dirty;
    }
}
