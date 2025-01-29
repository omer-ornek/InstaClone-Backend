import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class implements a max heap data structure to sort posts and generate feed .
 */
public class MaxHeap {
    private static final int DEFAULT_CAPACITY = 10;
    private int currentSize;
    private Post[] array;

    public MaxHeap() {
        currentSize = 0;
        array = new Post[DEFAULT_CAPACITY];
    }
    public MaxHeap(ArrayList<Post> items) {
        currentSize = items.size();
        array = new Post[(currentSize + 2) * 11 / 10];
        int i = 1;
        for (Post item : items)
            array[i++] = item;
        buildHeap();
    }
    private void enlargeArray() {
        int newLength = array.length * 2 + 1;
        Post[] newArray = new Post[newLength];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }
    public void insert(Post x) {
        if (currentSize == array.length - 1)
            enlargeArray();

        int hole = ++currentSize;

        // Percolate up
        for (array[0] = x; x.compareTo(array[hole / 2]) > 0; hole /= 2)
            array[hole] = array[hole / 2];
        array[hole] = x;
    }
    public Post deleteMax() {
        if (isEmpty())
            throw new NoSuchElementException("Heap is empty");

        Post maxItem = array[1]; // The root of the heap
        array[1] = array[currentSize--]; // Replace root with the last element
        percolateDown(1); // Restore heap property
        return maxItem;
    }
    private void percolateDown(int hole) {
        int child;
        Post tmp = array[hole];

        while (hole * 2 <= currentSize) {
            child = hole * 2;

            if (child != currentSize && array[child + 1].compareTo(array[child]) > 0)
                child++;

            if (array[child].compareTo(tmp) > 0) {
                array[hole] = array[child];
            } else {
                break;
            }
            hole = child;
        }

        array[hole] = tmp;
    }
    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--)
            percolateDown(i);
    }
    public boolean isEmpty() {
        return currentSize == 0;
    }
    public int size() {
        return currentSize;
    }
    public Post peekMax() {
        if (isEmpty())
            throw new NoSuchElementException("Heap is empty");
        return array[1];
    }
}
