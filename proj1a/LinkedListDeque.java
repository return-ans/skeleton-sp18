import java.util.List;

public class LinkedListDeque<T> {

    /**
     * create a ListNode class in LinkedListDeque class
     */
    public class ListNode{
        public T item;
        public ListNode prev;
        public ListNode next;

        //Constructor of a ListNode
        public ListNode(T item){
            this.item=item;
            this.prev=null;
            this.next=null;
        }
    }

    /**
     * initial two sentinel(head,tail) of this deque using ListNode class
     */
    private int size; //the number of the nodes
    private T item;
    private ListNode head;
    private ListNode tail;
    LinkedListDeque<T> next;

    /**
     * Create two sentinel nodes, and point to each other at first
     */
    public LinkedListDeque(){
        this.size=0;
        this.head=new ListNode(null);
        this.tail=new ListNode(null);
        this.head.next=this.tail;
        this.tail.prev=this.head;
    }

    public boolean isEmpty(){
        if(this.size==0) return true;
        else return false;
    }

    public int size(){
        return this.size;
    }

    /**
     * insert a ListNode after head
     * @param item
     */
    public void addFirst(T item){
        ListNode tmp=new ListNode(item);
        tmp.next=head.next;
        tmp.prev=head;
        tmp.next.prev=tmp;
        head.next=tmp;
        size++;
    }

    /**
     * insert a ListNode before tail
     * @param item
     */
    public void addLast(T item){
        ListNode tmp=new ListNode(item);
        tmp.next=tail;
        tmp.prev=tail.prev;
        tmp.prev.next=tmp;
        tail.prev=tmp;
        size++;
    }

    public void printDeque(){
        if(size==0) return;
        ListNode tmp=head.next;
        for(int i=0;i<size;i++){
            System.out.print(tmp.item);
            if(i!=size-1) System.out.print(" ");
            tmp=tmp.next;
        }
    }

    public T removeFirst(){
        if(size==0) return null;
        ListNode tmp=head.next;
        tmp.next.prev=head;
        head.next=tmp.next;
        tmp.next=null;
        tmp.prev=null;
        size--;
        return tmp.item;
    }

    public T removeLast(){
        if(size==0) return null;
        ListNode tmp=tail.prev;
        tmp.prev.next=tail;
        tail.prev=tmp.prev;
        tmp.next=null;
        tmp.prev=null;
        size--;
        return tmp.item;
    }

    public T get(int index){
        if(size==0||size<index+1) return null;
        ListNode tmp=head.next;
        for(int i=0;i<index;i++){
            tmp=tmp.next;
        }
        return tmp.item;
    }

    private T rec(ListNode L,int idx){
        if(idx==0){
            return L.item;
        }
        return rec(L.next,idx-1);
    }

    public T getRecursive(int index){
        if(size==0||size>index+1) return null;
        ListNode tmp=head;
        return rec(head,index);
    }

//    public static void main(String args[]){
//        LinkedListDeque<Integer> lld=new LinkedListDeque<Integer>();
//        boolean checkEmpty=lld.isEmpty();
//
//        lld.addFirst(10);
//        lld.addFirst(9);
//        lld.addLast(8);
//        int checkSize=lld.size();
//        lld.removeFirst();
//        int Get=lld.get(1);
//    }


}
