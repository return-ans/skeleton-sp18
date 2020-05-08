package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author LZ
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */
    //put directly if size==0

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null; //sentinel node
        size = 0;
    }

    private void validate(K key) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
    }

    /**
     * Returns the value mapped to by KEY in the subtree rooted in P.
     * or null if this map contains no mapping for the key.
     */
//    private V getHelper(K key, Node p) {
//        throw new UnsupportedOperationException();
//    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        validate(key);
        if (size == 0) {
            return null;
        }
        Node tmp = root;
        while (tmp != null) {
            int cmp = key.compareTo(tmp.key);
            if (cmp == 0) {
                return tmp.value;
            }
            if (cmp > 0) {
                //this is bigger than tmp, move to right
                tmp = tmp.right;
            } else {
                //less than tmp, move to left
                tmp = tmp.left;
            }
        }
        return null;
    }

    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
//    private Node putHelper(K key, V value, Node p) {
//        throw new UnsupportedOperationException();
//    }

    /**
     * Inserts the key KEY into a null child
     * Special!! if size==1, put directly
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Null values not allowed.");
        }
        if (size == 0) {
            //put directly if size==0
            root = new Node(key, value);
            size++;
            return;
        }
        Node tmp = root;
        int cmp;
        while (true) {
            //leaf node
            cmp = key.compareTo(tmp.key);
            if (cmp == 0) {
                tmp.value = value;
                return;
            }
            if (cmp > 0) {
                //this is bigger than tmp, move to right if right is not null
                if (tmp.right == null) {
                    tmp.right = new Node(key, value); //insert if it's null
                    size++;
                    return;
                }
                tmp = tmp.right;
            } else {
                //less than tmp, move to left if left is not null
                if (tmp.left == null) {
                    tmp.left = new Node(key, value); //insert if it's null
                    size++;
                    return;
                }
                tmp = tmp.left;
            }
        }

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

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
