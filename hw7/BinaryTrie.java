import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie {
    private static Node root;
    private static Map<Character, BitSequence> lookUpTable;
    private static ArrayList<Boolean> trieBitList;

    // Huffman trie node
    static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left;
        private final Node right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        // is the node a leaf node?
        boolean isLeaf() {
//            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }

        public Node left() {
            return left;
        }

        public Node right() {
            return right;
        }

        public char ch() {
            return ch;
        }
    }

    // build the Huffman trie given frequencies
    private static Node buildTrie(Map<Character, Integer> freq) {

        // initialize priority queue with singleton trees
        MinPQ<Node> pq = new MinPQ<Node>();
        for (char c : freq.keySet())
            if (freq.get(c) > 0)
                pq.insert(new Node(c, freq.get(c), null, null));

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }


    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        root = null;
        root = buildTrie(frequencyTable);
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        Node cur = root;
        String path = new String();
        String strSeq = querySequence.toString();
        int idx = 0;
        while (!cur.isLeaf()) {
            path += strSeq.charAt(idx);
            if (strSeq.charAt(idx) - '0' == 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
            idx++;
        }
        return new Match(new BitSequence(path), cur.ch);

    }

    static void dfs(Node root, String s) {
        if (root.isLeaf()) {
            lookUpTable.put(root.ch, new BitSequence(s));
            return;
        }
        if (root.left != null) {
            dfs(root.left, s + "0");
        }
        if (root.right != null) {
            dfs(root.right, s + "1");
        }
    }

    private static void charToBit(int n) {
        ArrayList<Boolean> tmp = new ArrayList<>();
        while (n > 0) {
            if (n % 2 == 1) {
                tmp.add(0, true);
            } else {
                tmp.add(0, false);
            }
            n = n >> 1;
        }
        while (tmp.size() < 8) {
            tmp.add(0, false);
        }
        trieBitList.addAll(tmp);
    }

    // write bit-string-encoded trie to standard output
    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            trieBitList.add(true); // meets the leaf
            // after '1' is a character
            charToBit(x.ch);
            return;
        }
        trieBitList.add(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    public static ArrayList<Boolean> getTrieBitList() {
        trieBitList = new ArrayList<>();
        writeTrie(root);
        return trieBitList;
    }

    public static Map<Character, BitSequence> buildLookupTable() {
        // each character hashes to a bit-sequence
        lookUpTable = new HashMap<>();
        String s = new String();
        dfs(root, s);
        return lookUpTable;
    }
}
