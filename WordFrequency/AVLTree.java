package WordFrequency;

/*
 * File Name:       AVLTree.java
 * Author:          Duilio Lucio
 * Project:         Word Frequency
 * Course:          CS231
 * Section:         B
 * Last Modified:   04/21/2023
 */

import java.util.Random;


public class AVLTree extends BSTMap<String, Integer> {

    private int height(Node node) {
        return node != null ? node.getHeight() : -1;
    }

    /**
     * Updates height for a specific node
     * Balance Factor = h(x.right) - h(x.left)
     * @param node, Node, the node to be updated
     */
    private void updateHeight(Node node) {
        int leftChildHeight = height(node.getLeft());
        int rightChildHeight = height(node.getRight());
        node.setHeight(Math.max(leftChildHeight, rightChildHeight) + 1);
    }

    private int balanceFactor(Node node) {
        return height(node.getRight()) - height(node.getLeft());
    }

    /**
     * A simple right roation.
     * returns the leftChild which is supposed to become the parent (or the node)
     * (node = leftChild)
     * @param node, the node to be rotated
     * @return Node, the new node
     */
    private Node rotateRight(Node node) {
        Node leftChild = node.getLeft();

        node.setLeft(leftChild.getRight());
        leftChild.setRight(node);

        updateHeight(node);
        updateHeight(leftChild);

        return leftChild;
    }

    /**
     * A simple right left.
     * returns the right child which is supposed to become the parent (or the node)
     * (node = rightChild)
     * @param node, the node to be rotated
     * @return Node, the new node
     */
    private Node rotateLeft(Node node) {
        Node rightChild = node.getRight();

        node.setRight(node.getLeft());
        rightChild.setLeft(node);

        updateHeight(node);
        updateHeight(rightChild);

        return rightChild;
    }

    /**
     * Rebalance the whole node. Update the node height and then perform rotations based on the Balance Factor.
     * @param node, the node to be rebalanced
     * @return Node, the new node
     */
    private Node rebalance(Node node) {
        if(node.getRight() != null){
            updateHeight(node.getRight());
        }
        if(node.getLeft() != null){
            updateHeight(node.getLeft());
        }
        updateHeight(node);
        int balanceFactor = balanceFactor(node);
        Node newNode = node;
        // Left-heavy
        if (balanceFactor < -1) {
            if (balanceFactor(node.getLeft()) > 0) {
                // LR (left right rotation)
                node.setLeft(rotateLeft(node.getLeft()));
            }
            // R (right rotation)
            newNode = rotateRight(node);
        }

        // Right-heavy
        if (balanceFactor > 1) {
            if (balanceFactor(node.getRight()) < 0) {
                // RL (right left rotation)
                node.setRight(rotateRight(node.getRight()));
            }
            // L (left rotation)
            newNode = rotateLeft(node);
        }

        return newNode;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced. Does nothing if {@code value} is {@code null}.
     * rebalances each node that it finds.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}.
     */
    @Override
    public Integer put(String key, Integer value) {
        Node newNode;
        if (getRoot() == null) {
            newNode = new Node(new KeyValuePair<>(key, value));
            setRoot(newNode);
            incrementSize();
        } else {
            newNode = putAVL(key, value, getRoot());
            setRoot(newNode);
        }
        return newNode.getValue();
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced. Does nothing if {@code value} is {@code null}.
     * rebalances each node that it finds
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @param cur   the current node
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}.
     */
    protected BSTMap<String, Integer>.Node putAVL(String key, Integer value, Node cur) {
        if(cur == null){
            return new Node(new KeyValuePair<>(key, value));
        }
        else if (key.compareTo(cur.getKey()) < 0) {
            cur.setLeft(putAVL(key, value, cur.getLeft()));
        } else if (key.compareTo(cur.getKey()) > 0) {
            cur.setRight(putAVL(key, value, cur.getRight()));
        } else { // in this case, cur.getKey() == key
            cur.setValue(value);
        }
        return rebalance(cur);
    }

    /**
     * converts BSTMap to a String
     * @return String, string representation of the BSTMap
     */
    public String toString() {
        return toString(getRoot(), 0, "root");
    }


    public String toString(Node cur, int depth, String direction) {
        if (cur == null) {
            return "";
        }

        String left = toString(cur.left, depth + 1, "left");
        String myself = direction + '\t' + "     ".repeat(depth) + cur.getKvp() + "  " + cur.getHeight() + '\n';
        String right = toString(cur.right, depth + 1, "right");

        return right + myself + left;
    }

    public static void main(String[] args){
        AVLTree tree = new AVLTree();
        Random rand = new Random();
        for(int i = 0; i < 20; i++){
            tree.put("" + rand.nextInt(1000), i);
        } System.out.println(tree);
    }
}