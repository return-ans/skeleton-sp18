import edu.princeton.cs.algs4.Queue;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     * <p>
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item : q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /**
     * Returns a random item from the given queue.
     */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted A Queue of unsorted items
     * @param pivot    The item to pivot on
     * @param less     An empty Queue. When the function completes, this queue will contain
     *                 all of the items in unsorted that are less than the given pivot.
     * @param equal    An empty Queue. When the function completes, this queue will contain
     *                 all of the items in unsorted that are equal to the given pivot.
     * @param greater  An empty Queue. When the function completes, this queue will contain
     *                 all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        while (!unsorted.isEmpty()) {
            Item tmp = unsorted.dequeue();
            if (tmp.compareTo(pivot) == 0) {
                equal.enqueue(tmp);
            } else if (tmp.compareTo(pivot) < 0) {
                //greater items go to right
                less.enqueue(tmp);
            } else {
                //less items go to left
                greater.enqueue(tmp);
            }
        }
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        /* As for a pivot:
           elements less than the pivot are on the left of the pivot
           elements bigger than the pivot are on the right of the pivot
         */
        // boundary condition: one or two element(s)
        int len = items.size();
        if (len <= 1) {
            return items;
        }
        if (len == 2) {
            Item aa = items.dequeue();
            Item bb = items.dequeue();
            if (aa.compareTo(bb) <= 0) {
                items.enqueue(aa);
                items.enqueue(bb);
            } else {
                items.enqueue(bb);
                items.enqueue(aa);
            }
            return items;
        }
        // then find pivot
        Item pivotItem = getRandomItem(items);
        Queue<Item> less = new Queue<>();
        Queue<Item> greater = new Queue<>();
        Queue<Item> equal = new Queue<>();
        // --->partitions the queue
        partition(items, pivotItem, less, equal, greater);
        // --->two sub-questions of less part and greater part
        Queue<Item> left = quickSort(less);
        Queue<Item> right = quickSort(greater);
        // --->catenate and return
        Queue<Item> a = catenate(left, equal);
        return catenate(a, right);
    }

    public static void main(String[] args) {
        Queue<Integer> q1 = new Queue<>();
        q1.enqueue(2);
        q1.enqueue(89);
        q1.enqueue(9);
        q1.enqueue(812);
        q1.enqueue(8);
        q1.enqueue(54);
        System.out.println("the original queue:");
        for (int x : q1) {
            System.out.println(x);
        }
        Queue<Integer> q2 = QuickSort.quickSort(q1);
        System.out.println("the sorted queue:");
        for (int x : q2) {
            System.out.println(x);
        }

    }
}
