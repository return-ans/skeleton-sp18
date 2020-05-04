public class OffByN implements CharacterComparator {
    private int diff;

    public OffByN(int N) {
        diff = N;
    }

    @Override
    /**
     * two chars are different by exactly diff
     */
    public boolean equalChars(char x, char y) {
        return x - y == diff || y - x == diff;
    }
}
