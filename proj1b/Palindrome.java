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
        int len = word.length();
        int mid = len / 2;
        if (len <= 1) {
            return true;
        }
        for (int i = 0; i < mid; i++) {
            //Symmetric
            if (word.charAt(i) != word.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Symmetric in odd-by-one, different by exactly one
     *
     * @param word
     * @param cc   (abstract)
     * @return
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        int len = word.length();
        int mid = len / 2;
        if (len <= 1) {
            return true;
        }
        for (int i = 0; i < mid; i++) {
            //Two char are different by one
            if (!cc.equalChars(word.charAt(i), word.charAt(len - 1 - i))) {
                return false;
            }
        }
        return true;
    }

}
