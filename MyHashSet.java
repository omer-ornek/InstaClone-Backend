import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashSet<T> implements Iterable<T>{
    private int tableSize;
    private int numElements;
    private MyLinkedList<T,Void>[] table;
    private static final double MAX_LOAD = 0.75;
    public MyHashSet(int tableSize) {
        this.tableSize = tableSize;
        table = new MyLinkedList[tableSize];
    }
    public MyHashSet() {
        this(101);
    }
    private void rehash() {
        int newSize = Primes.nextPrime(tableSize);
        MyLinkedList<T, Void>[] oldTable = table;
        table = new MyLinkedList[newSize];
        tableSize = newSize;
        numElements = 0;
        for (MyLinkedList<T, Void> list : oldTable) {
            if (list != null) {
                Node<T, Void> current = list.head;
                while (current != null) {
                    add(current.getKey());
                    current = current.next;
                }
            }
        }
    }
    private int hash(T key) {
        int i = key.hashCode() % tableSize;
        if (i < 0) i += tableSize;
        return i;
    }
    public boolean contains(T key) {
        int h = hash(key);
        if (table[h] == null) return false;
        return table[h].contains(key);
    }
    public boolean add(T key) {
        if (contains(key)) return false;
        int h = hash(key);
        if (table[h] == null) {
            table[h] = new MyLinkedList<>();
        }
        table[h].add(key);
        numElements++;
        if (((double) numElements / tableSize) >= MAX_LOAD) {
            rehash();
        }
        return true;
    }
   public boolean remove(T key) {
        if (!contains(key)) return false;
        int h = hash(key);
        table[h].remove(key);
        numElements--;
        return true;
    }
    public void empty() {
        table = new MyLinkedList[tableSize];
        numElements = 0;
   }

    public T get(T key) {
        int h = hash(key);
        if (table[h] == null) return null;
        return table[h].getKey(key);
    }
    public int getTableSize() {
        return tableSize;
    }
    public int getNumElements() {
        return numElements;
    }

    public Iterator<T> iterator() {
        return new MyHashSetIterator();
    }
    private class MyHashSetIterator implements Iterator<T> {
        private int currentBucket = 0; // Current bucket in the hash table
        private Node<T, Void> currentNode = null; // Current node in the bucket

        public MyHashSetIterator() {
            // Find the first non-empty bucket
            moveToNextNonEmptyBucket();
        }

        @Override
        public boolean hasNext() {
            return currentBucket < tableSize && currentNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T currentKey = currentNode.getKey(); // Get the current key
            currentNode = currentNode.next; // Move to the next node in the list

            // If we reached the end of the current bucket, move to the next one
            if (currentNode == null) {
                currentBucket++;
                moveToNextNonEmptyBucket();
            }

            return currentKey;
        }

        // Move to the next non-empty bucket
        private void moveToNextNonEmptyBucket() {
            while (currentBucket < tableSize && (table[currentBucket] == null || table[currentBucket].head == null)) {
                currentBucket++;
            }
            if (currentBucket < tableSize && table[currentBucket] != null) {
                currentNode = table[currentBucket].head;
            }
        }
    }

}
