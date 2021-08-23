package knight.rider.kitt.bar.attr;

public enum TextStyle {
    NORMAL(0), BOLD(1), ITALIC(2), BOLD_ITALIC(3);
    private final int style;

    TextStyle(int style) {
        this.style = style;
    }

    public int getStyle() {
        return style;
    }
}
