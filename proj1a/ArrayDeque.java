public class ArrayDeque<T> {

    /**
     * One array, two pointers
     * Make a circle in one array,
     * if firstIdx has no space in the front of the array, add first begin in (capacity-1), then firstIdx>lastIdx
     */
    private int capacity; //initial a capacity
    private int size;
    /**
     *     two pointers circulate int the array
     */
    private int firstIdx;// <----
    private int lastIdx;// ---->
    private int FACTOR;
    private T[] items;

    public ArrayDeque(){
        //May resize these arrays if add too many items
        capacity=4;
        FACTOR=2;
        //As for generic array, use Object
        items=(T[]) new Object[capacity];
        size=0;
        //two sentinels begin at the mid of the array
        firstIdx=capacity/2;//first sentinel set before the first item
        lastIdx=capacity/2;//last sentinel set after the last item
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        if(size==0) return true;
        else return false;
    }

    public T get(int index){
        if(size==0||size<index+1) return null;
        int tmp=firstIdx;
        for(int i=0;i<index;i++){
            tmp++;
            tmp%=capacity;
        }
        return items[tmp];
    }

    /**
     * resize the array with FACTOR*capacity
     * And the firstIdx and lastIdx should update after revising
     * @param capacity
     */
    private void resize(int capacity){
        T[] a=(T[]) new Object[capacity];
        if(lastIdx>firstIdx){
            /* [firstIdx x x x x x x x x x lastIdx] */
            System.arraycopy(items,firstIdx,a,0,size);
        }
        else{
            /* [x x x x x lastIdx o o o o firstIdx x x x x x x]
            * firstIdx should update
            */
            int leftNum=lastIdx+1;//6
            int rightNum=size-leftNum;//7 cap=17
            System.arraycopy(items,0,a,0,leftNum);
            System.arraycopy(items,firstIdx,a,capacity-rightNum,rightNum);
            firstIdx=capacity-rightNum;
        }
        items=a;

    }

    public void addFirst(T item){
        if(size==0){
            items[firstIdx]=item;
            size++;
            return;
        }
        if(size==capacity){
            int newCapacity=capacity*FACTOR;
            resize(newCapacity);
            capacity=newCapacity;
        }
        //move the firstIdx if not full
        if(firstIdx>0){
            firstIdx--;
        }
        else if(firstIdx==0){
            firstIdx=capacity-1;
        }
        items[firstIdx]=item;
        size++;
    }

    public void addLast(T item){
        if(size==0){
            items[firstIdx]=item;
            size++;
            return;
        }
        if(size==capacity){
            int newCapacity=capacity*FACTOR;
            resize(newCapacity);
            capacity=newCapacity;
        }
        lastIdx++;
        lastIdx%=capacity;
        items[lastIdx]=item;
        size++;
    }

    public T removeFirst(){
        if(size==0) return null;
        T ret=items[firstIdx];
        firstIdx++;
        firstIdx%=capacity;
        size--;
        return ret;
    }

    public T removeLast(){
        if(size==0) return null;
        T ret=items[lastIdx];
        lastIdx--;
        if(lastIdx==-1){
            lastIdx=capacity-1;
        }
        size--;
        return ret;
    }

    public void printDeque(){
        if(size==0) return;
        int tmp=firstIdx;
        for(int i=0;i<size;i++){
            System.out.print(items[tmp]);
            if(i!=size-1)  System.out.print(" ");
            tmp++;
            tmp%=capacity;
            //mod the capacity to circulate in the array
        }
    }

//    public static void main(String args[]){
//        ArrayDeque<Integer> adq=new ArrayDeque<Integer>();
//        boolean checkEmpty=adq.isEmpty();
//        int checkSize=adq.size();
//        int Get;
//        adq.addFirst(10);
//        Get=adq.get(0);
//        adq.addFirst(9);
//        adq.addFirst(1);
//        adq.addFirst(2);
//        Get=adq.get(1);
//        adq.addFirst(3);
//        adq.removeFirst();
//        adq.addFirst(92);
//        checkSize=adq.size();
//        adq.removeLast();
//        checkSize=adq.size();
//        adq.addFirst(12);
//        adq.addLast(8);
//        Get=adq.get(4);
//        adq.addLast(4);
//        checkSize=adq.size();
//        adq.addFirst(9);
//        adq.addLast(5);
//        adq.addLast(8);
//
//        adq.removeFirst();
//
//    }




}
