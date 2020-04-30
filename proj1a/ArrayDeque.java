public class ArrayDeque<T> {

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
        capacity = 4;
        FACTOR = 2;
        //As for generic array, use Object
        items = (T[]) new Object[capacity];
        size = 0;
        //two sentinels begin at the mid of the array
        firstIdx = capacity / 2; //first sentinel set before the first item
        lastIdx = capacity / 2; //last sentinel set after the last item
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

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
     * firstIdx and lastIdx should update after revising
     * Resize down after removals to save memory
     */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (lastIdx > firstIdx) {
            /* [firstIdx x x x x x x x x x lastIdx] */
            System.arraycopy(items, firstIdx, a, 0, size);
        } else {
            /* [x x x x x lastIdx o o o o firstIdx x x x x x x]
             * firstIdx should update
             */
            int leftNum = lastIdx + 1;
            int rightNum = size - leftNum;
            System.arraycopy(items, 0, a, 0, leftNum);
            System.arraycopy(items, firstIdx, a, capacity - rightNum, rightNum);
            /* firstIdx should change after resizing */
            firstIdx = capacity - rightNum;
        }
        items = a;
    }

    public void addFirst(T item) {
        if (size == 0) {
            items[firstIdx] = item;
            size++;
            return;
        }
        if (size == capacity) {
            int newCapacity = capacity * FACTOR;
            resize(newCapacity);
            capacity = newCapacity;
        }
        //move the firstIdx if not full
        if (firstIdx > 0) {
            firstIdx--;
        }
        else if (firstIdx == 0) {
            firstIdx = capacity - 1;
        }
        items[firstIdx] = item;
        size++;
    }

    public void addLast(T item) {
        if (size == 0) {
            items[firstIdx] = item;
            size++;
            return;
        }
        if (size == capacity) {
            int newCapacity = capacity * FACTOR;
            resize(newCapacity);
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
        //if (size * 2 < capacity - 1) resize(capacity / 2);
        return ret;
    }

    public T removeLast() {
        if (size == 0) return null;
        T ret = items[lastIdx];
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
        //if (size * 2 < capacity - 1) resize(capacity / 2);
        return ret;
    }

    public void printDeque() {
        if (size == 0) return;
        int tmp = firstIdx;
        for (int i = 0; i < size; i++) {
            System.out.print(items[tmp]);
            if (i != size - 1) System.out.print(" ");
            tmp++;
            tmp %= capacity;
            //mod the capacity to circulate in the array
        }
    }

//    public static void main(String args[]){
//        ArrayDeque<Integer> adq=new ArrayDeque<Integer>();
//        for(int i=0;i<20;i++){
//            if(i%2==0) adq.addFirst(i);
//            else adq.addLast(i);
//        }
//        for(int i=0;i<11;i++){
//            if(i%2==0) adq.removeFirst();
//            else adq.removeLast();
//        }
//
//    }

}
