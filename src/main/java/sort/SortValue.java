package sort;

public class SortValue {
    private int value = 0;
    protected SortCollection collection;
    private SortResultType type = SortResultType.DEFAULT;
    private boolean valid = false;

    public SortValue(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        this.collection.change(this);
    }

    public SortResultType getType() {
        return type;
    }

    public void setType(SortResultType type) {
        this.type = type;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }
}
