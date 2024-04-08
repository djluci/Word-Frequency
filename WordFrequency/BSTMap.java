package WordFrequency;

/*
 * File Name:       BSTMap.java
 * Author:          Duilio Lucio
 * Project:         Word Frequency
 * Course:          CS231
 * Section:         B
 * Last Modified:   04/20/2023
 */

 import java.util.ArrayList;

 public class BSTMap<K extends Comparable<K>, V> implements MapSet<K, V> {
 
     class Node {
         Node left;
         Node right;
         int height;
         KeyValuePair<K, V> kvp;
 
         public Node(KeyValuePair<K, V> kvp) {
             this.kvp = kvp;
             left = null;
             right = null;
             height = 0;
         }
 
         public Node getLeft() {
             return left;
         }
 
         public Node getRight() {
             return right;
         }
 
 
         public void setLeft(Node left) {
             this.left = left;
         }
 
         public void setRight(Node right) {
             this.right = right;
         }
 
         public void setKvp(KeyValuePair<K, V> kvp) {
             this.kvp = kvp;
         }
 
         public KeyValuePair<K, V> getKvp() {
             return kvp;
         }
 
         public V getValue() {
             return kvp.getValue();
         }
 
         public void setValue(V newValue) {
             kvp.setValue(newValue);
         }
 
         @Override
         public String toString() {
             return "Node{" +
                     "left=" + left +
                     ", right=" + right +
                     ", kvp=" + kvp +
                     '}';
         }
 
         public K getKey() {
             return kvp.getKey();
         }
 
         public int getHeight(){
             return height;
         }
 
         public void setHeight(int height) {
             this.height = height;
         }
     }
 
     private Node root;
     private int size;
 
     protected Node getRoot() {
         return root;
     }
 
     protected void setRoot(Node root) {
         this.root = root;
     }
 
     protected void incrementSize() {
         size++;
     }
 
     public BSTMap() {
         this(null, 0);
     }
 
     public BSTMap(Node root, int size) {
         this.root = root;
         this.size = size;
     }
 
     /**
      * Associates the specified value with the specified key in this map.
      * If the map previously contained a mapping for the key, the old
      * value is replaced. Does nothing if {@code value} is {@code null}.
      *
      * @param key   key with which the specified value is to be associated
      * @param value value to be associated with the specified key
      * @return the previous value associated with {@code key}, or
      * {@code null} if there was no mapping for {@code key}.
      */
     @Override
     public V put(K key, V value) {
         if(root==null){
             root = new Node(new KeyValuePair<>(key, value));
             size++;
             return null;
         }
         return put(key, value, root);
     }
 
     /**
      * Associates the specified value with the specified key in this map.
      * If the map previously contained a mapping for the key, the old
      * value is replaced. Does nothing if {@code value} is {@code null}.
      *
      * @param key   key with which the specified value is to be associated
      * @param value value to be associated with the specified key
      * @param cur   the current node
      * @return the previous value associated with {@code key}, or
      * {@code null} if there was no mapping for {@code key}.
      */
     protected V put(K key, V value, Node cur) {
         if (key.compareTo(cur.getKey()) < 0) {
             if (cur.getLeft() != null) {
                 return put(key, value, cur.getLeft());
             } else {
                 cur.setLeft(new Node(new KeyValuePair<>(key, value)));
                 size++;
                 return null;
             }
         } else if (key.compareTo(cur.getKey()) > 0) {
             if (cur.getRight() != null) {
                 return put(key, value, cur.getRight());
             } else {
                 cur.setRight(new Node(new KeyValuePair<>(key, value)));
                 size++;
                 return null;
             }
         } else { // in this case, cur.getKey() == key
             V toReturn = cur.getValue();
             cur.setValue(value);
             return toReturn;
         }
     }
 
     /**
      * Returns {@code true} if this map contains a mapping for the
      * specified key to a value.
      *
      * @param key The key whose presence in this map is to be tested
      * @return {@code true} if this map contains a mapping for the specified
      * key to a value.
      */
     @Override
     public boolean containsKey(K key) {
         return get(key) != null;
     }
 
     /**
      * Returns the value to which the specified key is mapped,
      * or {@code null} if this map contains no mapping for the key.
      *
      * @param key the key whose associated value is to be returned
      * @return the value to which the specified key is mapped, or
      * {@code null} if this map contains no mapping for the key
      */
     @Override
     public V get(K key) {
         return get(key, getRoot());
     }
 
     /**
      * Returns the value to which the specified key is mapped using a recursive technique,
      * or {@code null} if this map contains no mapping for the key.
      *
      * @param key the key whose associated value is to be returned
      * @param cur the current node
      * @return the value to which the specified key is mapped, or
      * {@code null} if this map contains no mapping for the key
      */
     private V get(K key, Node cur) {
         if (cur == null) {
             return null;
         }
         if (cur.getKey().compareTo(key) < 0) {
             return get(key, cur.getRight());
         } else if (cur.getKey().compareTo(key) > 0) {
             return get(key, cur.getLeft());
         } else {
             return cur.getValue();
         }
     }
 
     /**
      * Returns an ArrayList of all the keys in the map in sorted order from least to
      * greatest.
      *
      * @return an ArrayList of all the keys in the map in sorted order from least to
      * greatest.
      */
     @Override
     public ArrayList<K> keySet() {
         ArrayList<K> output = new ArrayList<>();
         keySet(root, output);
         return output;
     }
 
     /**
      * Returns an ArrayList of all the keys in the map in sorted order from least to
      * greatest.
      *
      * @param cur    the current node
      * @param output the array list on which the keys are added
      */
     private void keySet(Node cur, ArrayList<K> output) {
         if (cur == null) {
             return;
         }
         keySet(cur.getLeft(), output);
         output.add(cur.getKey());
         keySet(cur.getRight(), output);
     }
 
     /**
      * Returns an ArrayList of all the values in the map in the same order as the
      * keys as returned by keySet().
      *
      * @return an ArrayList of all the values in the map in the same order as the
      * keys as returned by keySet().
      */
     @Override
     public ArrayList<V> values() {
         ArrayList<V> output = new ArrayList<>();
         values(root, output);
         return output;
     }
 
     /**
      * Returns an ArrayList of all the values in the map in the same order as the
      * keys as returned by keySet().
      *
      * @param cur    the current node
      * @param output the array list on which the values are added
      */
     private void values(Node cur, ArrayList<V> output) {
         if (cur == null) {
             return;
         }
         values(cur.getLeft(), output);
         output.add(cur.getValue());
         values(cur.getRight(), output);
     }
 
 
     /**
      * Returns an ArrayList of each {@code KeyValuePair} in the map in the same
      * order as the keys as returned by keySet().
      *
      * @return an ArrayList of each {@code KeyValuePair} in the map in the same
      * order as the keys as returned by keySet().
      */
     @Override
     public ArrayList<KeyValuePair<K, V>> entrySet() {
         ArrayList<KeyValuePair<K, V>> output = new ArrayList<>();
         entrySet(root, output);
         return output;
     }
 
     private void entrySet(Node cur, ArrayList<KeyValuePair<K, V>> output) {
         if (cur == null) {
             return;
         }
         entrySet(cur.getLeft(), output);
         output.add(cur.getKvp());
         entrySet(cur.getRight(), output);
     }
 
     /**
      * Returns the number of key-value mappings in this map.
      *
      * @return the number of key-value mappings in this map
      */
     @Override
     public int size() {
         return size;
     }
 
     /**
      * Removes all of the mappings from this map.
      * The map will be empty after this call returns.
      */
     @Override
     public void clear() {
         root = null;
         size = 0;
     }
 
     /**
      * converts BSTMap to a String
      * @return String, string representation of the BSTMap
      */
     public String toString() {
         return toString(root, 0, "root");
     }
 
     public String toString(Node cur, int depth, String direction) {
         if (cur == null) {
             return "";
         }
 
         String left = toString(cur.left, depth + 1, "left");
         String myself = direction + '\t' + "   ".repeat(depth) + cur.getKvp() + '\n';
         String right = toString(cur.right, depth + 1, "right");
 
         return right + myself + left;
     }
 }