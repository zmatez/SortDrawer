package sort;

import setup.Main;
import util.Utilities;

public abstract class ISort {
    protected SortCollection collection;
    protected int sortDelay = 100000;
    protected int normalDelay = 1000000;

    public int sorted = 0;
    public int scanned = 0;
    public int valid = 0;

    public ISort(SortCollection collection){
        this.collection = collection;
    }

    public void process(){
        sort();
    }

    public void checkValid(){
        valid = 0;
        int last = -1;
        for (int i = 0; i < collection.values.length; i++) {
            SortValue value = collection.values[i];
            int val = value.getValue();
            if(val == last+1){
                valid++;
                value.setValid(true);
            }else{
                value.setValid(false);
            }
            last = val;
        }

        collection.valid = valid;
    }

    public abstract void sort();

    public void reset(){
        sorted = 0;
        scanned = 0;
    }

    public void apply(){
        collection.markDirty();
        collection.lastSorted = sorted;
        collection.lastScanned = scanned;
        Utilities.waitNanos(sortDelay);
    }
    public void apply(int delay){
        collection.markDirty();
        collection.lastSorted = sorted;
        collection.lastScanned = scanned;
        Utilities.waitNanos(delay);
    }

    public void delay(){
        Utilities.waitNanos(normalDelay);
    }
}
