package pl.application.domain.room;

import pl.application.util.SystemUtils;

public enum BedType {
    SINGLE(1, SystemUtils.SINGLE_BED),
    DOUBLE(2, SystemUtils.DOUBLE_BED),
    KING_SIZE(2, SystemUtils.KING_SIZE);
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
