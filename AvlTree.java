/**
 * AVL Tree implementation
 * @param <T>
 * Used for storing the primes in Primes.java to get the next prime
 */
public class AvlTree<T extends Comparable<T>> {
    private class AVLNode {
        T data;
        AVLNode left, right;
        int height;

        AVLNode(T data) {
            this.data = data;
            this.height = 1;
        }
    }

    private AVLNode root;

    public void insert(T data) {
        if (!contains(data))
            root = insert(root, data);
    }
    private AVLNode insert(AVLNode node, T data) {
        if (node == null) {
            return new AVLNode(data);
        }
        if (data.compareTo(node.data) < 0) {
            node.left = insert(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insert(node.right, data);
        } else {
            return node;
        }
        // Update height of the ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        // Left left
        if (balance > 1 && data.compareTo(node.left.data) < 0) {
            return rotateRight(node);
        }

        // Right right
        if (balance < -1 && data.compareTo(node.right.data) > 0) {
            return rotateLeft(node);
        }

        // Left Right
        if (balance > 1 && data.compareTo(node.left.data) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right Left
        if (balance < -1 && data.compareTo(node.right.data) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }
    public void delete(T data) {
        if (contains(data)) {
            root = delete(root, data);
        }
    }
    private AVLNode delete(AVLNode node, T data) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.data) < 0) {
            node.left = delete(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = delete(node.right, data);
        } else {
            if (node.left == null || node.right == null) { // 0 or 1 child case
                AVLNode temp = null;
                if (temp == node.left) { // 1 child
                    temp = node.right;
                } else {
                    temp = node.left;
                }
                if (temp == null) { // 0 child
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else { // 2 children case
                AVLNode temp = minValueNode(node.right);
                node.data = temp.data;
                node.right = delete(node.right, temp.data);
            }
        }
        if (node == null) {
            return null;
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        // Left left
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }

        // Right right
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }

        // Left Right
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right Left
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    public boolean contains(T data) {
        return contains(root, data);
    }
    private boolean contains(AVLNode node, T data) {
        if (node == null) {
            return false;
        }
        if (data.compareTo(node.data) < 0) {
            return contains(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            return contains(node.right, data);
        } else {
            return true;
        }
    }
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }
    private int height(AVLNode node) { // Chose to overcome null pointer exception
        return node == null ? 0 : node.height;
    }
    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }
    public T findOrSmallestLarger(T data) {
        AVLNode result = findOrSmallestLarger(root, data);
        return result != null ? result.data : null;
    }
    private AVLNode findOrSmallestLarger(AVLNode node, T data) {
        AVLNode smallestLarger = null;
        while (node != null) {
            if (data.compareTo(node.data) == 0) {
                return node;
            } else if (data.compareTo(node.data) < 0) {
                smallestLarger = node;
                node = node.left;
            } else {
                node = node.right;
            }
        }
        if (smallestLarger == null) {
           throw new IllegalArgumentException("No larger element found" + node.data);
        }
        return smallestLarger;
    }

}
