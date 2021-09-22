package sort.sorters;

import sort.ISort;
import sort.SortCollection;
import sort.SortResultType;

public class QuickSort extends ISort {
    public QuickSort(SortCollection collection) {
        super(collection);
    }

    @Override
    public void sort() {
        this.sort(0,this.collection.getLength()-1);
    }

    int partition(int low, int high) {
        int pivot = this.collection.values[high].getValue();
        this.collection.values[high].setType(SortResultType.GETTING);
        apply();
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than or
            // equal to pivot
            this.collection.values[j].setType(SortResultType.GETTING);
            apply();
            if (this.collection.values[j].getValue() <= pivot) {
                i++;

                // swap arr[i] and arr[j]
                int temp = this.collection.values[i].getValue();
                this.collection.values[i].setType(SortResultType.CHECKING);
                apply();
                this.collection.values[i].setValue(this.collection.values[j].getValue());
                this.collection.values[i].setType(SortResultType.SORTING);
                this.collection.values[j].setType(SortResultType.CHECKING);
                apply();
                this.collection.values[j].setValue(temp);
                this.collection.values[j].setType(SortResultType.SORTING);
                apply();
                sorted++;
            }

            scanned++;
            checkValid();
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = this.collection.values[i + 1].getValue();
        this.collection.values[i+1].setType(SortResultType.CHECKING);
        apply();
        this.collection.values[i + 1].setValue(this.collection.values[high].getValue());
        this.collection.values[i+1].setType(SortResultType.SORTING);
        this.collection.values[high].setType(SortResultType.CHECKING);
        apply();
        this.collection.values[high].setValue(temp);
        this.collection.values[high].setType(SortResultType.CHECKING);
        apply();
        sorted++;
        scanned++;

        checkValid();
        return i + 1;
    }

    void sort(int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(low, pi - 1);
            sort(pi + 1, high);
        }
    }
}
