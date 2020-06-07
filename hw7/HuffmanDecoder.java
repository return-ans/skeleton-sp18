import java.util.ArrayList;

public class HuffmanDecoder {
    private static BinaryTrie.Node root; // build Huffman tree from trieBitList
    private static ArrayList<Boolean> trieBitList;
    private static BitSequence contentBit;
    private static int idx;
    private static ArrayList<Character> decodeString;

    private static BinaryTrie.Node buildTree() {
        if (idx == trieBitList.size()) {
            return null;
        }
        boolean isLeaf = trieBitList.get(idx);
        idx++;
        if (isLeaf) {
            int sum = 0;
            for (int i = 0; i < 8; i++) {
                int dig = trieBitList.get(idx) ? 1 : 0;
                sum = sum * 2 + dig;
                idx++;
            }
            return new BinaryTrie.Node((char) sum, -1, null, null);
        } else {
            // assume that subtrees have built
            // left-subtree first, and right-subtree second
            return new BinaryTrie.Node('\0', -1, buildTree(), buildTree());
        }
    }

    public static void main(String[] args) {
        if (args.length <= 0) {
            System.out.println("Usage: SeamCarver [filename] [numPixels] [horizontal | yN]");
            return;
        }
        String readName = args[0];
        String decodeName = args[1];
//        String readName = "watermelonsugar.txt.huf";
//        String decodeName = "hhh.txt";


//        1: Read the Huffman coding trie.
        ObjectReader or = new ObjectReader(readName);
        /* Read first object(trie) from the file and cast. */
        Object x = or.readObject();
        trieBitList = (ArrayList<Boolean>) x;

        idx = 0;
        root = buildTree();

//        2: If applicable, read the number of symbols.

//        3: Read the massive bit sequence corresponding to the original txt.
        /* Read second object from the file ans cast. */
        Object y = or.readObject();
        contentBit = (BitSequence) y;
        int len = contentBit.length();
        decodeString = new ArrayList<>();
        for (int i = 0; i < len; ) {
            BinaryTrie.Node cur = root;
            while (!cur.isLeaf()) {
                int bit = contentBit.bitAt(i++);
                if (bit == 1) {
                    cur = cur.right();
                } else {
                    cur = cur.left();
                }
            }
            // find the char
            decodeString.add(cur.ch());
        }
//        4: Repeat until there are no more symbols:

//        4a: Perform a longest prefix match on the massive sequence.

//        4b: Record the symbol in some data structure.

//        4c: Create a new bit sequence containing the remaining unmatched bits.

//        5: Write the symbols in some data structure to the specified file.
        char[] chars = new char[decodeString.size()];
        for (int i = 0; i < decodeString.size(); i++) {
            chars[i] = decodeString.get(i);
        }
        FileUtils.writeCharArray(decodeName, chars);

    }
}
