
public class ArrayDeque<T> {

    private int size;
    private float usage;
    private int nextFirst;
    private int nextLast;
    private T Array[];

    public ArrayDeque() {

        Array = (T[]) new Object[8];
        size = 0;
        nextFirst = 2;

        if (nextFirst == 7) {
            nextLast = 0;
        } else {
            nextLast = nextFirst + 1;
        }

    }

    private boolean evalUsage() {

        return (usage < 0.25 && (Array.length > 16));

    }

    private void resize() {

        if (evalUsage() || Array.length == size) {

            int targetSize = Array.length;

            if (evalUsage()) {
                targetSize /= 2;
            } else {
                targetSize *= 2;
            }

            T[] NewArray = (T[]) new Object[targetSize];

            int maxIndex = 0;
            int startingIndex = 0;

            // shrinking case
            if (evalUsage()) {

                for (int i = 0; i < Array.length; i++) {
                    if (Array[i] == null && Array[i + 1] != null) {
                        startingIndex = i + 1;
                    }
                    if (Array[i] != null && Array[i + 1] == null) {
                        maxIndex = i;
                    }
                }
                System.arraycopy(Array, startingIndex, NewArray, 1, maxIndex - startingIndex + 1);

                Array = NewArray;
                nextFirst = 0;
                nextLast = maxIndex - startingIndex + 2;
            } else {
                // doubling case

                maxIndex = Array.length - 1;
                startingIndex = 0;

                System.arraycopy(Array, startingIndex, NewArray, 1, maxIndex - startingIndex + 1);
                Array = NewArray;

                nextFirst = 0;
                nextLast = maxIndex - startingIndex + 1;
            }

        }
    }

    public void addFirst(T item) {

        resize();

        Array[nextFirst] = item;
        size++;

        usage = (float) size / (float) Array.length;

        if (nextFirst != 0) {
            nextFirst--;
        } else {
            nextFirst = Array.length - 1;
        }
    }

    public void addLast(T item) {

        resize();

        Array[nextLast] = item;
        size++;

        usage = (float) size / (float) Array.length;

        if (nextLast != Array.length - 1) {
            nextLast++;
        } else {
            nextLast = 0;
        }

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {

        for (T p : Array) {
            System.out.println(p + " ");
        }
        System.out.println("\n");

    }

    public T removeFirst() {

        int index = nextFirst;

        if (index != Array.length - 1) {
            index++;
        } else {
            index = 0;
        }

        T value = Array[index];
        Array[index] = null;
        size--;
        usage = (float) size / (float) Array.length;

        nextFirst = index;

        resize();

        return value;
    }

    public T removeLast() {

        int index = nextLast;

        if (index != 0) {
            index--;
        } else {
            index = Array.length - 1;
        }

        T value = Array[index];
        Array[index] = null;
        size--;
        usage = (float) size / (float) Array.length;

        nextLast = index;

        resize();
        return value;
    }

    public T get(int index) {

        if (index >= this.Array.length - 1) {
            return null;
        } else {
            return this.Array[index];
        }

    }

    public static void main(String[] args) {
//        ArrayDeque<Integer> Array = new ArrayDeque<Integer>();
//        Array.addFirst(1);
//        Array.addFirst(2);
//        Array.addFirst(3);
//        Array.addFirst(4);
//        Array.addFirst(5);
//        Array.addFirst(6);
//        Array.addFirst(7);
//        Array.addFirst(8);
//        Array.addFirst(9);
//        Array.addFirst(100);
//        Array.removeFirst();
//        Array.removeFirst();
//        Array.printDeque();
//        System.out.println(Array.get(0));
    }
}