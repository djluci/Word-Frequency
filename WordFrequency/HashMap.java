package WordFrequency;
/*
 * File Name:       HashMap.java
 * Author:          Duilio Lucio
 * Project:         Word Frequency
 * Course:          CS231
 * Section:         B
 * Last Modified:   04/20/2023
 */

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class HashMap<K, V> implements MapSet<K, V> {

    LinkedList<KeyValuePair<K, V>>[] map;
    int size;
    int initialCapacity;
    float loadFactor;
    String hashFunction;

    public HashMap(String hashFunction, int initialCapacity) {
        this.hashFunction = hashFunction;
        this.initialCapacity = initialCapacity;
        loadFactor = 0.75F;
        map = (LinkedList<KeyValuePair<K, V>>[]) new LinkedList[initialCapacity];
        size = 0;
    }

    public HashMap(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        loadFactor = 0.75F;
        map = (LinkedList<KeyValuePair<K, V>>[]) new LinkedList[initialCapacity];
        size = 0;
    }

    public String getHashFunction(){
        return hashFunction;
    }

    /**
     * helper function to find the length of the map array
     * -> method was changed to public for extensions <-
     * @return the length of the map array
     */
    public int capacity() {
        return map.length;
    }

    /**
     * Returns a long value representing the hash of a String key.
     * It is similar in implementation to Java's built-in hash method for UTF-16 Strings
     * @param value String, the key to be hashed
     * @return long, the long value representing the hash
     */
    private long hashCodePrime(String value){
        long h = 0; // prime
        int length = value.length() >> 1;
        for(int i = 0; i < length; i++) {
            h = (31 * h) + value.charAt(i);
        }
        return h;
    }

    /**
     * this method returns the index that will be used by any given key for this
     * mapping
     * @param key: the Key to be hashed
     * @return the integer key for the map array
     */
    private int hash(K key) {
        long hashCode;
        if(Objects.equals(hashFunction, "sha256")){
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            assert digest != null;
            byte[] hash = digest.digest(((String )key).getBytes(StandardCharsets.UTF_8));
            hashCode = new BigInteger(hash).longValue();
        }else if(Objects.equals(hashFunction, "long prime")){
            hashCode = key instanceof String ? hashCodePrime((String) key) : key.hashCode();
        }else{
            hashCode = key.hashCode();
        }
        return Math.abs((int) hashCode % capacity()); // this returns a value between 0 and capacity() - 1, inclusive
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
    public V put(K key, V value) {
        int index = hash(key);

        if (map[index] == null) {
            map[index] = new LinkedList<KeyValuePair<K, V>>();
            // if size ever gets too big compared to capacity, then I need to recreate my map to be bigger
            // map = (LinkedList<KeyValuePair<K, V>>[]) new LinkedList[map.length * someReasonableFactor];
        } else {
            for (KeyValuePair<K, V> kvp : map[index]) {
                if (kvp.getKey().equals(key)) {
                    V oldValue = kvp.getValue();
                    kvp.setValue(value);
                    return oldValue;
                }
            }
        }
        map[index].add(new KeyValuePair<K, V>(key, value));
        size++;

        if (size > loadFactor * capacity()) {
            LinkedList<KeyValuePair<K, V>>[] oldMap = map;
            map = (LinkedList<KeyValuePair<K, V>>[]) new LinkedList[(int)(map.length / loadFactor)];
            size = 0;
            for (LinkedList<KeyValuePair<K, V>> ll : oldMap) {
                if (ll != null && ll.size() != 0) {
                    for (KeyValuePair<K, V> kvp : ll) {
                        put(kvp.getKey(), kvp.getValue());
                    }
                }
            }
        }

        return null;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key
     */
    public V get(K key) {
        int index = hash(key);

        if (map[index] == null) {
            return null;
        } else {
            for (KeyValuePair<K, V> kvp : map[index]) {
                if (kvp.getKey().equals(key)) {
                    return kvp.getValue();
                }
            }
            return null;
        }
    }

    /**
     * Removes a certain key from the array map by searching
     * for it in the linked list at the position of hash(key)
     * @param key: K, the key to be removed
     * @return: V, the value that was removed
     */
    public V remove(K key) {
        LinkedList<KeyValuePair<K, V>> ll = map[hash(key)];
        LinkedList<KeyValuePair<K, V>> newll = new LinkedList<KeyValuePair<K, V>>();
        V toReturn = null;
        for (KeyValuePair<K, V> item : ll) {
            if (item.getKey() != key) {
                newll.add(item);
            } else {
                toReturn = item.getValue();
            }
        }
        map[hash(key)] = newll;
        size--;

        if (size < (int) ((loadFactor * loadFactor * loadFactor) * capacity())) {
            LinkedList<KeyValuePair<K, V>>[] oldMap = map;
            map = (LinkedList<KeyValuePair<K, V>>[]) new LinkedList[(int)(map.length * loadFactor)];
            size = 0;
            for (LinkedList<KeyValuePair<K, V>> ill : oldMap) {
                if (ill != null && ill.size() != 0) {
                    for (KeyValuePair<K, V> kvp : ill) {
                        put(kvp.getKey(), kvp.getValue());
                    }
                }
            }
        }

        return toReturn;

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
        if (map[hash(key)] != null) {
            for (KeyValuePair<K, V> item : map[hash(key)]) {
                if (item.getKey() == key) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns an ArrayList of all the keys in the map
     *
     * @return an ArrayList of all the keys in the map
     */
    @Override
    public ArrayList<K> keySet() {
        ArrayList<K> output = new ArrayList<>();
        for (LinkedList<KeyValuePair<K, V>> ll : map) {
            if (ll != null && ll.size() != 0) {
                for (KeyValuePair<K, V> kv : ll) {
                    output.add(kv.getKey());
                }
            }
        }
        return output;
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
        for (LinkedList<KeyValuePair<K, V>> ll : map) {
            if(ll!=null){
                for (KeyValuePair<K, V> kv : ll) {
                    output.add(kv.getValue());
                }
            }
        }
        return output;
    }

    /**
     * Returns an ArrayList of each {@code KeyValuePair} in the map in the same
     * order as the keys as returned by keySet().
     *
     * @return an ArrayList of each {@code KeyValuePair} in the map in the same
     * order as the keys as returned by keySet().
     */
    @Override
    public ArrayList<MapSet.KeyValuePair<K, V>> entrySet() {
        ArrayList<KeyValuePair<K, V>> output = new ArrayList<>();
        for (LinkedList<KeyValuePair<K, V>> ll : map) {
            if (ll != null && ll.size() != 0) {
                output.addAll(ll);
            }
        }
        return output;
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
        map = (LinkedList<KeyValuePair<K, V>>[]) new LinkedList[initialCapacity];
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (LinkedList<KeyValuePair<K, V>> ll : map) {
            if (ll != null && ll.size() != 0) {
                output.append(hash(ll.getFirst().getKey())).append(" -> ").append("[ ");
                for (KeyValuePair<K, V> kv : ll) {
                    output.append(kv.getKey()).append(": ").append(kv.getValue()).append(",");
                }
                output.append("]\n");
            }

        }
        return output.toString();
    }

    public long countCollisions(){
        long count = 0;
        for (LinkedList<KeyValuePair<K,V>> ll: map) {
            if(ll != null && ll.size() != 0){
                count += ll.size()-1;
            }
        }
        return count;
    }

    public void __extension_addKVP(int index, KeyValuePair<K, V> kvp){
        if(map[index] == null){
            map[index] = new LinkedList<>();
            map[index].add(kvp);
        }else{
            for (KeyValuePair<K, V> someKvp: map[index]) {
                if(kvp.getKey() == someKvp.getKey()){
                    ((KeyValuePair<String, Integer>) someKvp).setValue((Integer) someKvp.getValue() + (Integer) kvp.getValue());
                    return;
                }
            }
            map[index].add(kvp);
        }
        size++;
    }

    public void __extension_updateCapacity(int newCapacity){
        map = new LinkedList[newCapacity];
    }

}