public class OffByOne implements CharacterComparator {

    @Override
    /**
     * Two are different by exactly one
     */
    public boolean equalChars(char x, char y) {
        return x - y == 1 || y - x == 1;
    }


}
