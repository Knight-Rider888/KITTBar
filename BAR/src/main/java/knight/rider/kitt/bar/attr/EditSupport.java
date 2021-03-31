package knight.rider.kitt.bar.attr;

public enum EditSupport {

    WRITE_AND_CLEAR(0), ONLY_WRITE(2), NONE_SUPPORT(1);

    private int type;

    EditSupport(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
