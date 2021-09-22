package sort.sorters;

import sort.ISort;
import sort.SortCollection;
import sort.SortResultType;

public class HeapSort extends ISort {
    public HeapSort(SortCollection collection) {
        super(collection);
    }

    @Override
    public void sort() {
        buildheap();
        int sizeOfHeap= collection.getLength()-1;
        for(int i=sizeOfHeap; i>0; i--) {
            exchange(0, i);
            sizeOfHeap=sizeOfHeap-1;
            heapify(0,sizeOfHeap);
        }
    }

    public void buildheap() {
        for(int i=(collection.getLength()-1)/2; i>=0; i--){
            heapify(i,collection.getLength()-1);
        }
    }

    public void heapify(int i,int size) {
        int left = 2*i+1;
        int right = 2*i+2;
        int max;
        collection.values[left].setType(SortResultType.CHECKING);
        collection.values[i].setType(SortResultType.CHECKING);
        apply();
        if(left <= size && collection.values[left].getValue() > collection.values[i].getValue()){
            max=left;
        } else {
            max=i;
        }

        collection.values[right].setType(SortResultType.CHECKING);
        collection.values[max].setType(SortResultType.CHECKING);
        apply();
        if(right <= size && collection.values[right].getValue() > collection.values[max].getValue()) {
            max=right;
        }
        // If max is not current node, exchange it with max of left and right child
        if(max!=i) {
            exchange(i, max);
            heapify(max,size);
        }
    }

    public void exchange(int i, int j) {
        int t = collection.values[i].getValue();
        collection.values[i].setType(SortResultType.GETTING);
        apply();
        collection.values[i].setValue(collection.values[j].getValue());
        collection.values[i].setType(SortResultType.SORTING);
        collection.values[j].setType(SortResultType.GETTING);
        apply();
        collection.values[j].setValue(t);
        collection.values[i].setType(SortResultType.SORTING);
        apply();
    }

}
