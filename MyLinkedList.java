/**
 * This class is a custom implementation of a singly linked list to store in HashMap.
 * @param <K>
 * @param <V>
 */
public class MyLinkedList<K, V> {
    public Node<K, V> head;
    public Node<K, V> tail;
    private int size;
    public MyLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }
    public boolean contains(K key) {
        Node<K, V> temp = head;
        while (temp != null) {
            if (temp.getKey().equals(key)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
    public boolean add(K key) {
        if (contains(key))
            return false;
        Node<K, V> newNode = new Node<>(key);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }
    public boolean put(K key, V value) {
        if (contains(key))
            return false;
        Node<K, V> newNode = new Node<>(key, value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }
    public boolean remove(K key) {
        if (!contains(key))
            return false;

        // Case 1: Removing the head node
        if (head.getKey().equals(key)) {
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return true;
        }
        Node<K, V> temp = head;
        while (temp.next != null) {
            if (temp.next.getKey().equals(key)) {
                if (temp.next == tail) {
                    tail = temp;
                }
                temp.next = temp.next.next;
                size--;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
    public V getValue(K key) {
        Node<K, V> temp = head;
        while (temp != null) {
            if (temp.getKey().equals(key)) {
                return temp.getValue();
            }
            temp = temp.next;
        }
        return null;
    }
    public K getKey(K key) {
        Node<K,V> temp = head;
        while (temp != null) {
            if (temp.getKey().equals(key)) {
                return temp.getKey();
            }
            temp = temp.next;
        }
        return null;
    }
}