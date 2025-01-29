/**
 * Node class for LinkedList
 * @param <T> key
 * @param <V> value
 */
public class Node<T, V> {
    private T key;
    private V value;
    public Node<T, V> next;

    public Node(T key) {
        this.key = key;
        this.next = null;
    }
    public Node(T key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
    public T getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

}