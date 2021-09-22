package sort;

import setup.Main;
import util.Utilities;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SortCollection {
    private Main main;
    private int length;
    private int max;
    public SortValue[] values;
    public SortValue lastChanged = null;


    //
    public int valid = 0;
    public int lastSorted = 0;
    public int lastScanned = 0;


    private boolean dirty = true;

    public SortCollection(Main main, int length, int max) {
        this.main = main;
        this.length = length;
        this.max = max;

        values = new SortValue[length];
    }

    public static SortCollection create(Main main, int length, int max) {
        SortCollection collection = new SortCollection(main, length, max);
        collection.randomize();

        return collection;
    }

    public void randomize(){
        int[] arr = new int[length];
        for(int i = 0; i < length; i++){
            arr[i] = i;
        }
        shuffleArray(arr);

        for (int i = 0; i < length; i++) {
            int random = arr[i];
            SortValue value = new SortValue(random);
            value.collection = this;
            values[i] = value;
        }
    }
    public void randomize(ISort sort){
        int[] arr = new int[length];
        for(int i = 0; i < length; i++){
            arr[i] = i;
        }
        shuffleArray(arr);

        for (int i = 0; i < length; i++) {
            int random = arr[i];
            SortValue value = new SortValue(random);
            value.collection = this;
            values[i] = value;
            sort.apply(1000000);
            sort.checkValid();
        }
    }

    private static void shuffleArray(int[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public int getLength() {
        return length;
    }

    public int getMax() {
        return max;
    }

    public void markDirty() {
        this.dirty = true;
    }

    public void rendered() {
        this.dirty = false;
    }

    public boolean isDirty() {
        return dirty;
    }

    protected void change(SortValue value){
        this.lastChanged = value;
    }
}
