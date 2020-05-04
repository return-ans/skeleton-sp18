public class OffByOne implements CharacterComparator {

    @Override
    /**
     * Two are different by exactly one
     */
    public boolean equalChars(char x, char y) {
        int cmp = Math.abs(x - y);
        return cmp == 1;
    }


}
