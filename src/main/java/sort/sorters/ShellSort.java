package sort.sorters;

import sort.ISort;
import sort.SortCollection;
import sort.SortResultType;

public class ShellSort extends ISort {
    public ShellSort(SortCollection collection) {
        super(collection);
    }

    @Override
    public void sort() {
        int count = this.collection.getLength();
        for (int gap = count / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < count; i += 1) {
                int sort = this.collection.values[i].getValue();
                this.collection.values[i].setType(SortResultType.GETTING);
                this.apply();

                int index;
                for (index = i; index >= gap && this.collection.values[index - gap].getValue() > sort; index -= gap) {
                    this.collection.values[index].setValue(this.collection.values[index - gap].getValue());
                    this.collection.values[index].setType(SortResultType.CHECKING);
                    this.apply();
                }

                this.collection.values[index].setValue(sort);
                this.collection.values[index].setType(SortResultType.SORTING);
                this.apply();

                checkValid();
            }
            checkValid();
        }
    }
}
