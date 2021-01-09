import java.util.*;

public class FenwickTree {

    // A Fenwick Tree implementation which supports point updates and sum range
    // queries
    // this tree is ONES BASED, ie the 0th index does not get used.

    final int N;

    private long[] tree;

    public FenwickTree(int size) {

        N = size;

        // we do size+1 so that we have enough room as 0th index is not used
        tree = new long[size + 1];
    }

    public FenwickTree(long[] valuesArray) {

        if (valuesArray == null)
            throw new IllegalArgumentException("Passed array cannot be null.");

        N = valuesArray.length;

        // 0th index is always 0
        valuesArray[0] = 0L;

        // make a copy of the values array to not change the passed array's content
        // this construction takes O(N) time
        tree = valuesArray.clone();

        for (int i = 1; i < valuesArray.length; i++) {

            int parent = i + lsb(i);

            if (parent < N)
                tree[parent] += tree[i];
        }
    }

    // gets the integer value of the lowest sig (one) bit
    private static int lsb(int num) {

        return num & -num; // bit manipulation, could do Integer.lowestOneBit(num)
    }

    // finds the prefix sum from [1,i]
    public long prefixSum(int index) {

        long sum = 0L;

        // this cascades downwards till 0
        while (index != 0) {

            sum += tree[index];
            index -= lsb(index);
        }

        return sum;
    }

    // returns the sum of interval [left, right]
    public long sum(int right, int left) {

        if (right < left)
            throw new IllegalArgumentException("Make sure right >= left.");

        return prefixSum(right) - prefixSum(left - 1);
    }

    // gets the value of the tree at index i
    public long get(int index) {

        if (index > N || index <= 0)
            throw new IllegalArgumentException("Index is out of range.");

        return sum(index, index);
    }

    public void add(long value, int index) {

        if (index > N || index <= 0)
            throw new IllegalArgumentException("Index is out of range.");

        while (index < N) {

            tree[index] += value;
            index += lsb(index);
        }
    }

    public void set(long value, int index) {

        if (index > N || index <= 0)
            throw new IllegalArgumentException("Index is out of range.");

        add(value - sum(index, index), index);

    }

    @Override
    public String toString() {

        return java.util.Arrays.toString(tree);
    }

    public static void main(String[] args) {

        long[] array = new long[] { 0, 1, 2, 3, 4 };

        FenwickTree tree = new FenwickTree(array);

        System.out.println(tree.toString());
    }

}