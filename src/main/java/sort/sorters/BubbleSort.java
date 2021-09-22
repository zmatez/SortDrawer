package sort.sorters;

import sort.ISort;
import sort.SortCollection;
import sort.SortResultType;
import util.Utilities;

public class BubbleSort extends ISort {
    public BubbleSort(SortCollection collection) {
        super(collection);
    }

    @Override
    public void sort() {
        int n = this.collection.getLength();
        int temp = 0;
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                this.collection.values[j].setType(SortResultType.SORTING);
                this.apply();
                if(this.collection.values[j-1].getValue() > this.collection.values[j].getValue()){
                    //swap elements
                    temp = this.collection.values[j-1].getValue();
                    this.collection.values[j-1].setType(SortResultType.CHECKING);
                    this.apply();

                    this.collection.values[j-1].setValue(this.collection.values[j].getValue());
                    this.collection.values[j].setValue(temp);
                    this.collection.values[j].setType(SortResultType.SORTING);
                    this.apply();

                    //this.collection.values[j-1].setType(SortResultType.DEFAULT);
                    //this.apply();
                }
                //this.collection.values[j].setType(SortResultType.DEFAULT);
                //this.apply();
                checkValid();

            }
            checkValid();
        }
    }
}
