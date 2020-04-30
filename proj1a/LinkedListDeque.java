public class LinkedListDeque<T> {

    /**
     * create a ListNode class in LinkedListDeque class
     */
    private class ListNode {
        private T item;
        private ListNode prev;
        private ListNode next;

        //Constructor of a ListNode
        public ListNode(T item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }
    }

    /**
     * initial two sentinel(head,tail) of this deque using ListNode class
     */
    private int size; //the number of the nodes
    private T item;
    private ListNode head;
    private ListNode tail;

    /**
     * Create two sentinel nodes, and point to each other at first
     */
    public LinkedListDeque() {
        size = 0;
        head = new ListNode(null);
        tail = new ListNode(null);
        head.next = tail;
        tail.prev = head;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(T item) {
        ListNode tmp = new ListNode(item);
        tmp.next = head.next;
        tmp.prev = head;
        tmp.next.prev = tmp;
        head.next = tmp;
        size++;
    }

    public void addLast(T item) {
        ListNode tmp = new ListNode(item);
        tmp.next = tail;
        tmp.prev = tail.prev;
        tmp.prev.next = tmp;
        tail.prev = tmp;
        size++;
    }

    public void printDeque() {
        if (size == 0) {
            return;
        }
        ListNode tmp = head.next;
        for (int i = 0; i < size; i++) {
            System.out.print(tmp.item);
            if (i != size - 1) System.out.print(" ");
            tmp = tmp.next;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        ListNode tmp = head.next;
        T ret = tmp.item;
        tmp.next.prev = head;
        head.next = tmp.next;
        tmp = null;
        size--;
        return ret;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        ListNode tmp = tail.prev;
        T ret = tmp.item;
        tmp.prev.next = tail;
        tail.prev = tmp.prev;
        /* make null after removal */
        tmp = null;
        size--;
        return ret;
    }

    public T get(int index) {
        if (size == 0 || size < index + 1) {
            return null;
        }
        ListNode tmp = head.next;
        /* Beginning at the first item */
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        return tmp.item;
    }

    private T rec(ListNode L, int idx) {
        if (idx == 0) {
            return L.item;
        }
        return rec(L.next, idx - 1);
    }

    public T getRecursive(int index) {
        if (size == 0 || size < index + 1) {
            return null;
        }
        ListNode tmp = head.next;
        return rec(tmp, index);
    }


}
