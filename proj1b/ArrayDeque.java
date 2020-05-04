public class ArrayDeque<T> implements Deque<T> {

    /**
     * One array, two pointers
     * Make a circle in one array,
     * if firstIdx has no space in the front of the array,
     * add first begin in (capacity-1), then firstIdx>lastIdx
     */
    private int capacity; //initial a capacity
    private int size;
    /**
     * two pointers circulate int the array
     */
    private int firstIdx; // <----
    private int lastIdx; // ---->
    private int FACTOR;
    private T[] items;

    public ArrayDeque() {
        //May resize these arrays if add too many items
        capacity = 16;
        FACTOR = 2;
        //As for generic array, use Object
        items = (T[]) new Object[capacity];
        size = 0;
        //two sentinels begin at the mid of the array
        firstIdx = capacity / 2; //first sentinel set before the first item
        lastIdx = capacity / 2; //last sentinel set after the last item
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T get(int index) {
        if (size == 0 || size < index + 1) {
            return null;
        }
        int tmp = firstIdx;
        for (int i = 0; i < index; i++) {
            tmp++;
            tmp %= capacity;
        }
        return items[tmp];
    }

    /**
     * Both firstIdx and lastIdx should update after revising!!!!
     * Resize down after removals to save memory
     */
    private void resize(int cap, boolean flag) {
        T[] a = (T[]) new Object[cap];
        if (lastIdx > firstIdx) {
            /* [firstIdx x x x x x x x x x lastIdx] */
            System.arraycopy(items, firstIdx, a, 0, size);
            /* Start from index 0 */
            firstIdx = 0;
            lastIdx = size - 1;

        } else {
            /* [x x x x x lastIdx o o o o firstIdx x x x x x x]
             * firstIdx should update either up or down
             */
            int leftNum = lastIdx + 1;
            int rightNum = size - leftNum;
            System.arraycopy(items, 0, a, 0, leftNum);
            System.arraycopy(items, firstIdx, a, cap - rightNum, rightNum);
            /* firstIdx should change after resizing */
            firstIdx = cap - rightNum;
        }
        capacity = cap;
        items = a;
    }

    @Override
    public void addFirst(T item) {
        if (size == 0) {
            items[firstIdx] = item;
            size++;
            return;
        }
        if (size == capacity) {
            int newCapacity = capacity + capacity / FACTOR;
            resize(newCapacity, true);
            capacity = newCapacity;
        }
        //move the firstIdx if not full
        if (firstIdx > 0) {
            firstIdx--;
        } else if (firstIdx == 0) {
            firstIdx = capacity - 1;
        }
        items[firstIdx] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == 0) {
            items[firstIdx] = item;
            size++;
            return;
        }
        if (size == capacity) {
            int newCapacity = capacity + capacity / FACTOR;
            resize(newCapacity, true);
            capacity = newCapacity;
        }
        lastIdx++;
        lastIdx %= capacity;
        items[lastIdx] = item;
        size++;
    }

    /**
     * It should make null after removing an item
     *
     * @return
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T ret = items[firstIdx];
        items[firstIdx] = null;
        /* If the size just is one, just make null the item,
            do not need to shift the pointer
         */
        if (size > 1) {
            firstIdx++;
            firstIdx %= capacity;
        }
        size--;
        if (size > 1 && size * 2 < capacity - 1) {
            resize(capacity - capacity / 2, false);
            //lastIdx may have to shift after size's shrinking
        }
        return ret;
    }

    /**
     * Should maintain a mininum size of the array
     *
     * @return
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T ret = items[lastIdx];
        items[lastIdx] = null;
        /* If the size just is one, just make null the item,
            do not need to shift the pointer
         */
        if (size > 1) {
            lastIdx--;
            if (lastIdx == -1) {
                lastIdx = capacity - 1;
            }
        }
        size--;
        if (size > 1 && size * 2 < capacity - 1) {
            resize(capacity - capacity / 2, false);
            //lastIdx may have to shift after size's shrinking
        }
        return ret;
    }

    @Override
    public void printDeque() {
        if (size == 0) {
            return;
        }
        int tmp = firstIdx;
        for (int i = 0; i < size; i++) {
            System.out.print(items[tmp]);
            if (i != size - 1) {
                System.out.print(" ");
            }
            tmp++;
            tmp %= capacity;
            //mod the capacity to circulate in the array
        }
    }

//    public static void main(String args[]){
//        ArrayDeque<Integer> adq=new ArrayDeque<Integer>();
//        int check;
//        adq.addFirst(0);
//        adq.addLast(1);
//        adq.addLast(2);
//        boolean e=adq.isEmpty();
//        adq.removeLast();
//        adq.addFirst(3);
//        adq.removeFirst();
//        adq.printDeque();
//        for(int i=0;i<10;i++){
//            adq.removeFirst();
//        }
//        for(int i=0;i<20;i++){
//            adq.addLast(i);
//        }
//
//
//
//
//    }

}
