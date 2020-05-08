package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 *
 * @author LZ
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets; //2-D array
    //same hashcode in same ArrayMap!!
    private int size; //the number of key-value pairs

    private double loadFactor() {
        return (double) size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        //ArrayMap class in bucket are null initially!!!!!
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /**
     * Recalculate hashcode to put in the new array
     * Reorganise the bucket
     *
     * @param newBucketNum
     */
    private void resize(int newBucketNum) {
        ArrayMap<K, V>[] temp = new ArrayMap[newBucketNum];
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null && buckets[i].size() > 0) {
                Set<K> st = buckets[i].keySet();
                for (K k : st) {
                    V v = buckets[i].get(k);
                    int newHashcode = Math.floorMod(k.hashCode(), newBucketNum);
                    //new position in the new array
                    //new a ArrayMap in the bucket if it's null first
                    if (temp[newHashcode] == null) {
                        //not null
                        temp[newHashcode] = new ArrayMap<>();
                    }
                    //should put in an exist ArrayMap in the bucket
                    temp[newHashcode].put(k, v);
                }
            }
        }
        buckets = temp;
    }

    /**
     * Computes the hash function of the given key. Consists of
     * computing the hashcode, followed by modding by the number of buckets.
     * To handle negative numbers properly, uses floorMod instead of %.
     * <p>
     * Calculate the hashcode as the index of array to find item
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        if (size == 0) {
            return null;
        }
        /*
        this hashcode hashes to an ArrayMap class,
        may has some key-value pairs
         */
        return buckets[hash(key)].get(key);

    }

    /* Associates the specified value with the specified key in this map.
     *  If the key is already exist, just change its value and increase the size.
     * */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Null values not allowed.");
        }
        if (get(key) != null) {
            buckets[hash(key)].put(key, value);
            //just change the value
            return;
        }
        if (loadFactor() >= MAX_LF) {
            //double the number of the bucket
            resize(buckets.length * 2);
        }
        if (buckets[hash(key)] == null) {
            //not null
            buckets[hash(key)] = new ArrayMap<>();
            //guarantee that not put a ket-value pair into a null ArrayMap
        }
        //should put in an exist ArrayMap in the bucket
        buckets[hash(key)].put(key, value);
        size++;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
