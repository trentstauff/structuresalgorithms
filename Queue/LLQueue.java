import java.util.NoSuchElementException;

public class LLQueue<T> {
    private class NewNode{
        T value;
        NewNode next;
    }

    private NewNode front;
    private NewNode rear;
    private int size;

    public LLQueue(){
        front = null;
        rear = null;
        size = 0;
    }

    public boolean isEmpty(){ return size == 0;}

    public int size(){return size;}

    public T peek(){
        if(isEmpty()) throw new NoSuchElementException("Queue is empty");
        return front.value;
    }

    public void enqueue(T value){
        NewNode n = new NewNode();
        n.value = value;
        if(isEmpty()){
            front = n;
        } else {
            rear.next = n;
        }
        rear = n;
        rear.next = null;
        size++;
    }

    public T dequeue(){
        if(size == 0) throw new NoSuchElementException("Queue is empty");
        T value = front.value;
        NewNode oldFront = front;
        front = front.next;
        oldFront = null;
        size--;
        return value;
    }

    public void printQueue(){
        NewNode p = front;
        System.out.print("(front) [");
        while(p != null) {
            if (p.next == null) {
                System.out.print(p.value);
                break;
            } else {
                System.out.print(p.value + ", ");
                p = p.next;
            }
        }
        System.out.print("] (end)");
        System.out.println("");
    }

    public static void main(String[] args) {
        LLQueue<Integer> queue = new LLQueue();
        LLQueue<String> queueS = new LLQueue();
        queue.enqueue(5);
        queue.enqueue(4);
        queue.enqueue(2);
        queue.enqueue(511);
        queue.dequeue();
        queue.dequeue();
        System.out.println("First value is " + queue.peek());
        queue.enqueue(100);
        queue.printQueue();

        System.out.println("");

        queueS.enqueue("Placeholder");
        queueS.enqueue("Trent");
        queueS.enqueue("Darcie");
        queueS.enqueue("Andrew");
        queueS.enqueue("Kristine");
        queueS.enqueue("Harley");
        System.out.println("First value is " + queueS.peek());
        queueS.printQueue();
        queueS.dequeue();
        queueS.printQueue();
    }
}
