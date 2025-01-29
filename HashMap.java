import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class implements a hash map data structure.
 */
public class HashMap<K, V> implements Iterable<K> {
    private int tableSize;
    private int numElements;
    private MyLinkedList<K, V>[] table;
    private static final double MAX_LOAD = 0.75;

    public HashMap(int tableSize) {
        this.tableSize = tableSize;
        table = new MyLinkedList[tableSize];
    }
    public HashMap() {
        this(101);
    }
    private int hash(K key) {
        int i = key.hashCode() % tableSize;
        if (i < 0) i += tableSize;
        return i;
    }

    /**
     * This method puts the key-value pair into the hash map.
     * If the key already exists in the hash map, the value is updated.
     * @param key the key
     * @param value the value
     */
    public void put(K key, V value) {
        if (contains(key)) {
            table[hash(key)].remove(key);
            put(key, value);
            return;
        }
        int h = hash(key);
        if (table[h] == null) {
            table[h] = new MyLinkedList<>();
        }
        table[h].put(key, value);
        numElements++;
        if ((double) numElements / tableSize >= MAX_LOAD) rehash();
    }

    /**
     * This method returns the value associated with the key.
     * @param key the key
     * @return the value associated with the key
     */
    public K getKey(K key) {
        int h = hash(key);
        if (table[h] == null) return null;
        return table[h].getKey(key);
    }

    /**
     * This method returns the value associated with the key.
     * @param key the key
     * @return the value associated with the key
     */
    public V getValue(K key) {
        int h = hash(key);
        if (table[h] == null) return null;
        return table[h].getValue(key);
    }

    /**
     * This method returns the value associated with the key.
     * @param key the key
     * @return the value associated with the key
     */
    public boolean contains(K key) {
        int h = hash(key);
        if (table[h] == null) return false;
        return table[h].contains(key);
    }

    /**
     * This method removes the key-value pair from the hash map.
     * @param key the key
     * @return true if the key-value pair was removed, false otherwise
     */
    public boolean remove(K key) {
        if (!contains(key)) return false;
        int h = hash(key);
        table[h].remove(key);
        numElements--;
        return true;
    }

    /**
     * This method increases the table size and rehashes all the elements.
     */
    private void rehash() {
        int newSize = Primes.nextPrime(tableSize);
        MyLinkedList<K, V>[] oldTable = table;
        table = new MyLinkedList[newSize];
        tableSize = newSize;
        numElements = 0;
        for (MyLinkedList<K, V> list : oldTable) {
            if (list != null) {
                Node<K, V> temp = list.head;
                while (temp != null) {
                    put( temp.getKey(), (V) temp.getValue());
                    temp = temp.next;
                }
            }
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }
    /** This class implements an iterator for the hash map to iterate over keys when necessary. */
    private class HashMapIterator implements Iterator<K> {
        private int currentBucket = 0;
        private Node<K, V> currentNode = null;

        public HashMapIterator() {
            // Move to the first non-empty bucket
            moveToNextNonEmptyBucket();
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            K currentKey = currentNode.getKey();
            currentNode = currentNode.next;

            // If the current node is null, move to the next non-empty bucket
            if (currentNode == null) {
                currentBucket++;
                moveToNextNonEmptyBucket();
            }

            return currentKey;
        }

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