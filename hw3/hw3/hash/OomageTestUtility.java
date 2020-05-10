package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* DONE:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        /* N:     number of items
           M:     number of buckets */
        int N = oomages.size();
        double lo = (double) N / 50;
        double hi = (double) N / 2.5;
        //count the number of o in each bucket
        int[] numInBucket = new int[M];
        for (Oomage s : oomages) {
            int bucketNumber = (s.hashCode() & 0x7FFFFFFF) % M;
            numInBucket[bucketNumber] += 1;
        }
        for (int num : numInBucket) {
            if (!(lo <= (double) num && (double) num <= hi)) {
                return false;
            }
        }
        return true;
    }
}
