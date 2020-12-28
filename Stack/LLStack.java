import java.util.NoSuchElementException;

public class LLStack<T> {

    private class NewNode<T> {

        private T value;
        private NewNode next;
    }

    NewNode root;
    int size;

        public LLStack() {
            size = 0;
        }

        public LLStack(T value) {
            push(value);
        }

        public int size(){ return size; }

        public boolean isEmpty(){ return size == 0; }

        public void push(T value){
            NewNode oldRoot = root;
            root = new NewNode();
            root.value = value;
            root.next = oldRoot;
            size++;
    }

        public T pop(){
            if(isEmpty()) throw new NoSuchElementException("Stack Underflow");
            T value = (T) root.value;
            root = root.next;
            size--;
            return value;
        }

        public T peek(){
            if(isEmpty()) throw new NoSuchElementException("Stack Underflow");
            return (T) root.value;
        }

    public static void main(String[] args) {
        LLStack<Integer> stack = new LLStack<>();
        stack.push(4);
        stack.push(5);
        System.out.println(stack.isEmpty());
        System.out.println(stack.peek());
        System.out.println("Popping " + stack.pop());
        System.out.println(stack.peek());
        System.out.println("Popping " + stack.pop());
        System.out.println(stack.isEmpty());
//        System.out.println(stack.pop());

    }
}
