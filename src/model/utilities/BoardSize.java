package model.utilities;

public enum BoardSize {
    SMALL(3),
    MEDIUM(4),
    BIG(5);

    public final int size;

    BoardSize(int size) {
        this.size = size;
    }
}
