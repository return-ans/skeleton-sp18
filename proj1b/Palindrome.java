public class Palindrome {
    /**
     * Put a string into a deque
     *
     * @param word
     * @return
     */
    public Deque<Character> wordToDeque(String word) {
        //interface(abstract) name    deque(special) name
        Deque<Character> ret = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            ret.addLast(word.charAt(i));
        }
        return ret;
    }

    public boolean isPalindrome(String word) {
        for (int i = 0; i < word.length() / 2; i++) {
            //Symmetric
            if (word.charAt(i) != word.charAt(word.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Symmetric in odd-by-one, different by exactly one
     *
     * @param word
     * @param cc  (abstract)
     * @return
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        for (int i = 0; i < word.length() / 2; i++) {
            //Two char are different by one
            if (!cc.equalChars(word.charAt(i), word.charAt(word.length() - 1 - i))) {
                return false;
            }
        }
        return true;
    }

}
