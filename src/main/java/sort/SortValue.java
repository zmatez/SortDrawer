package sort;

public class SortValue {
    private int value = 0;
    private SortResultType type = SortResultType.DEFAULT;

    public SortValue(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public SortResultType getType() {
        return type;
    }

    public void setType(SortResultType type) {
        this.type = type;
    }
}
