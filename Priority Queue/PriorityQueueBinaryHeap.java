import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PriorityQueueBinaryHeap<T extends Comparable<T>> {


    // implements a priority queue where the lower the value,
    // the higher the queue position.
    // ie if your priority is 0, you have the top priority.
    // this is implemented with a min heap

    // number of elements inside the heap
    private int heapSize = 0;

    // the internal capacity of the heap
    private int heapCapacity = 0;

    private List<T> heap = null;

    public PriorityQueueBinaryHeap(){
        this(1);
    }

    // create a priority queue with an initial capacity
    public PriorityQueueBinaryHeap(int size){
        heap = new ArrayList<>(size);
    }


    // create a priority queue using heapify in O(n)
    // http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
    public PriorityQueueBinaryHeap(T[] elems){

        heapSize = heapCapacity = elems.length;
        heap = new ArrayList<>(heapCapacity);

        // add all elems into the heap
        for(int i = 0; i < heapCapacity; i++) heap.add(elems[i]);

        // heapify process
        for(int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i--) sink(i);
    }

    public boolean isEmpty() {return heapSize == 0;}

    public int size(){return heapSize;}

    public void clear(){
        for(int i = 0; i < heapCapacity; i++) heap.set(i, null);

        heapSize = 0;
    }

    public T peek(){

        if(isEmpty()) return null;
        return heap.get(0);
    }

    public T poll(){
        return removeAt(0);
    }

    public boolean contains(T elem){
        for(int i = 0; i < heapCapacity; i++){
            if(heap.get(i).equals(elem))
                return true;
        }
        return false;
    }

    public void add(T elem){

        if(elem == null) throw new IllegalArgumentException();

        if(heapSize < heapCapacity){
            heap.set(heapSize, elem);
        } else {
            heap.add(elem);
            heapCapacity++;
        }

        swim(heapSize);
        heapSize++;
    }

    private boolean less(int i, int j) {
        T node1 = heap.get(i);
        T node2 = heap.get(j);
        return node1.compareTo(node2) <= 0;
    }

    private void swim(int k){

        int parent = (k - 1)/2;

        while(k > 0 && less(k, parent)){

            swap(parent, k);
            k = parent;
            parent = (k - 1)/2;
        }
    }

    private void sink(int k){

        while(true){

            int left = (2 * k + 1);
            int right = (2 * k + 2);
            int smallest = left;

            if (right < heapSize && less(right, left))
                smallest = right;

            if(left >= heapSize || less(k, smallest))
                break;

            swap(smallest, k);
            k = smallest;
        }
    }

    private void swap(int i, int j){

        T elem_i = heap.get(i);
        T elem_j = heap.get(j);

        heap.set(i, elem_j);
        heap.set(j, elem_i);
    }

    public boolean remove(T element){

        if(element == null) return false;

        for(int i = 0; i < heapSize; i++){

            if(element.equals(heap.get(i))) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    public T removeAt(int i){

        if(isEmpty()) return null;

        heapSize--;
        T removed = heap.get(i);
        swap(i, heapSize);

        heap.set(heapSize, null);

        if(i == heapSize) return removed;

        // store the elem to see if it sunk
        T elem = heap.get(i);

        sink(i);
        // if the elem is the same it didnt sink so swim
        if(heap.get(i).equals(elem)) swim(i);
        return removed;
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    public static void main(String[] args) {
        PriorityQueueBinaryHeap<Integer> heap = new PriorityQueueBinaryHeap<>();
        heap.add(1);
        heap.add(5);

        heap.add(0);
        heap.add(13);
        heap.add(14);
        heap.add(7);
        heap.add(3);
        heap.add(1);
        heap.poll();
        heap.remove(1);
        System.out.println(heap.toString());
        System.out.println(heap.peek());
    }
}
