package pl.application.domain.room;

import pl.application.util.Properties;

public enum BedType {
    SINGLE(1, Properties.SINGLE_BED),
    DOUBLE(2, Properties.DOUBLE_BED),
    KING_SIZE(2, Properties.KING_SIZE);
    private final int size;
    private final String asStr;
    BedType(int size, String asStr) {
        this.size = size;
        this.asStr = asStr;
    }
    public int getSize() {
        return this.size;
    }
    @Override
    public String toString() {
        return this.asStr;
    }
}
