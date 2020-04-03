package P1A;

public class LinkedListDeque<T> {

    public class NewNode<T> {

        // java implementation of a CDLL!

        public T value;
        public NewNode<T> next;
        public NewNode<T> prev;

        public NewNode(T num, NewNode<T> n, NewNode<T> p) {
            value = num;
            next = n;
            prev = p;
        }

        public NewNode(T num) {
            value = num;
            next = null;
            prev = null;
        }

    }

    private int size;
    private NewNode<T> sentinel;

    public LinkedListDeque() {

        sentinel = new NewNode<T>(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

    }

    public LinkedListDeque(LinkedListDeque<T> other) {
        sentinel = new NewNode<T>(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

        NewNode<T> p = other.sentinel.next;

        while (p != other.sentinel) {
            addLast(p.value);
            p = p.next;
        }
    }

    public void addFirst(T item) {

        sentinel.next = new NewNode<T>(item, sentinel.next, sentinel);
        sentinel.next.next.prev = sentinel.next;

        size++;
    }

    public void addLast(T item) {
        if (size == 0) {
            sentinel.next = sentinel.prev = new NewNode<T>(item, sentinel, sentinel);
        } else {
            sentinel.prev.next = sentinel.prev = new NewNode<T>(item, sentinel, sentinel.prev);

        }
        size++;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        NewNode<T> p = sentinel.next;
        while (p != sentinel) {
            if (p.next == sentinel) {
                System.out.print(p.value);
                break;
            } else {
                System.out.print(p.value + " ");
                p = p.next;
            }
        }
        System.out.println();
    }

    public T removeFirst() {
        T thing;
        if (sentinel.next == sentinel) {
            return null;
        } else {
            thing = sentinel.next.value;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
        }
        size--;
        return thing;
    }

    public T removeLast() {
        T thing;
        if (sentinel.prev == sentinel) {
            return null;
        } else {
            thing = sentinel.prev.value;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
        }
        size--;
        return thing;

    }

    public T get(int index) {
        NewNode<T> p = sentinel.next;
        int counter = 0;
        while (p != sentinel) {

            if (counter == index) {
                return p.value;
            }
            counter++;
            p = p.next;
        }
        return null;
    }

    // idk

    public T getRecursive(int index) {

        if (index == 0) {
            return sentinel.next.value;
        } else {
            return traverse(sentinel.next, index);
        }
    }

    public T traverse(NewNode<T> n, int i) {

        if (i == 0) {
            return n.value;
        } else {
            return traverse(n.next, i - 1);
        }

    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> list = new LinkedListDeque<Integer>();
        // where to test functions
    }
}