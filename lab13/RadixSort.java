/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 */
public class RadixSort {
    static String[][] bucket;

    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // DONE: Implement LSD Sort
        String[] tmp = new String[asciis.length];
        int maxLen = 0;
        int id = 0;
        for (String s : asciis) {
            if (s.length() > maxLen) {
                maxLen = s.length();
            }
            tmp[id++] = s;
        }
        if (maxLen == 0) {
            return asciis;
        }
        bucket = new String[257][asciis.length + 1];
        for (int i = maxLen; i > 0; i--) {
            int[] bucketIdx = new int[257];
            for (String s : tmp) {
                if (i > s.length()) {
                    bucket[256][bucketIdx[256]] = s;
                    bucketIdx[256]++;
                } else {
                    char c = s.charAt(i - 1);
                    bucket[c][bucketIdx[c]] = s;
                    bucketIdx[c]++;
                }
            }
            int idx = 0;
            for (int ii = 0; ii < bucketIdx[256]; ii++) {
                tmp[idx++] = bucket[256][ii];
            }
            for (int ii = 0; ii < 256; ii++) {
                for (int jj = 0; jj < bucketIdx[ii]; jj++) {
                    tmp[idx++] = bucket[ii][jj];
                }
            }
        }
        return tmp;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String at start)
     * @param end    int for where to end sorting in this method (does not include String at end)
     * @param index  the index of the character the method is currently sorting on
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }


//    public static void main(String[] args) {
//        String[] arr = {"set", "cat", "cad", "con", "cat", "can", "be", "let", "bet", "bat"};
//        String[] sorted = sort(arr);
//
//    }
}
