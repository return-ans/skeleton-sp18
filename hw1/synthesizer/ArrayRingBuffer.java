package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;
    private int fillCount;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     * Some methods inherit, and some methods override
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public void enqueue(T x) {
        if (isFull() == true) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            if (isEmpty() != true) {
                last++;
                last %= capacity(); //circle
            }
            rb[last] = x;
            fillCount++;
        }

    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T ret = rb[first];
            rb[first] = null;
            if (fillCount() > 1) {
                first++;
                first %= capacity(); //circle
            }
            fillCount--;
            return ret;
        }

    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            return rb[first];
        }

    }

    // DONE: When you get to part 5, implement the needed code to support iteration.
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int curPos;
        private int count;

        public ArrayRingBufferIterator() {
            curPos = first;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count < fillCount();
        }

        @Override
        public T next() {
            T curItem = rb[curPos];
            curPos++;
            curPos %= capacity();
            count++;
            return curItem;
        }
    }

    @Override
    public boolean equals(Object obj) {
        //compare to itself
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> o = (ArrayRingBuffer<T>) obj;
        if (o.fillCount() != this.fillCount()) {
            return false;
        }
        Iterator<T> thisIt = this.iterator();
        Iterator<T> objIt = o.iterator();
        while (thisIt.hasNext() && objIt.hasNext()) {
            if (thisIt.next() != objIt.next()) {
                return false;
            }
        }
        return true;
    }
}
