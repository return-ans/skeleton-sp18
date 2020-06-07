import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        HashMap<Character, Integer> mp = new HashMap<>();
        for (char c : inputSymbols) {
            if (!mp.containsKey(c)) {
                mp.put(c, 1);
            } else {
                int tmp = mp.get(c);
                mp.replace(c, tmp + 1);
            }
        }
        return mp;
    }

    public static void main(String[] args) {
        if (args.length <= 0) {
            System.out.println("Usage: SeamCarver [filename] [numPixels] [horizontal | yN]");
            return;
        }
        String readName = args[0];
        String writeName = readName + ".huf";
//        String readName = "watermelonsugar.txt";
//        String writeName = readName + ".huf";

        // 1: Read the file as 8 bit symbols.
        char[] inputSymbols = FileUtils.readFile(readName);

        // 2: Build frequency table.
        Map<Character, Integer> freq = buildFrequencyTable(inputSymbols);

        // 3: Use frequency table to construct a binary decoding trie.
        BinaryTrie bt = new BinaryTrie(freq);

        // 4: Write the binary decoding trie to the .huf file.
        // HOW TO WRITE HUFFMAN TREE???  preOrder
        /** Create a file called with ".huf" that ObjectWriter ow will write to. */
        ObjectWriter ow = new ObjectWriter(writeName);
        ow.writeObject(BinaryTrie.getTrieBitList());
        // trie is ArrayList object different from content next

        // 5: (optional: write the number of symbols to the .huf file)
        // 6: Use binary trie to create lookup table for encoding.
        Map<Character, BitSequence> lookUpTable = bt.buildLookupTable();

        // 7: Create a list of bit-sequences.
        List<BitSequence> bitList = new ArrayList<>();

        // 8: For each 8 bit symbol:
        // Lookup that symbol in the lookup table.
        // Add the appropriate bit sequence to the list of bit-sequences.
        for (char c : inputSymbols) {
            bitList.add(lookUpTable.get(c));
        }

        // 9: Assemble all bit sequences into one huge bit sequence.
        BitSequence totalBitSequence = BitSequence.assemble(bitList);

        // 10: Write the huge bit sequence to the .huf file.
        ow.writeObject(totalBitSequence);
    }
}
