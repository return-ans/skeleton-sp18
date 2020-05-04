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

    /**
     * Recursive method
     *
     * @param dq
     * @return
     */
    private boolean help(Deque<Character> dq) {
        if (dq.size() <= 1) {
            return true;
        }
        Character head = dq.removeFirst();
        Character tail = dq.removeLast();
        return (head == tail) && help(dq);
    }

    /**
     * Character type
     *
     * @param word *** word may be null
     * @return
     */
    public boolean isPalindrome(String word) {
        if (word == null) return true;
        Deque<Character> tmp = wordToDeque(word);
        return help(tmp);
    }

    private boolean helpc(Deque<Character> dq, CharacterComparator cc) {
        if (dq.size() <= 1) {
            return true;
        }
        Character head = dq.removeFirst();
        Character tail = dq.removeLast();
        //continue to the rest of the deque
        return (cc.equalChars(head, tail)) && helpc(dq, cc);
    }

    /**
     * judge using deque
     *
     * @param word
     * @param cc   (abstract)
     * @return
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) return true;
        Deque<Character> tmp = wordToDeque(word);
        return helpc(tmp, cc);
    }

}
