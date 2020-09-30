/*
 * Course: CS2852 - 061
 * Spring
 * Lab 8 - Morse Code Encoder
 * Name: Nigel Nelson
 * Created: 05/10/20
 */
package msoe.nelsonni.lab08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that creates a map in order to translate text
 * to morse code
 * @param <K> The key for a map entry
 * @param <V> The value for a map entry
 */
public class LookupTable<K extends Comparable<? super K>, V>
        implements Map<K, V> {
    private List<Entry<K, V>> morseTable;

    /**
     * Constructor for the LookupTable class
     */
    public LookupTable() {
        this.morseTable = new ArrayList<>();
    }

    /**
     * Embedded class that outlines what an Entry for the Lookup
     * table is
     * @param <K> The key of an entry
     * @param <V> The value of an entry
     */
    public static class Entry<K extends Comparable<? super K>, V>
            implements Comparable<Entry<K, V>>, Map.Entry<K, V> {
        K key;
        V value;

        /**
         * Constructor for an Entry in the LookupTable
         * @param key The key of an entry
         * @param value The value of an entry
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }


        @Override
        public int compareTo(Entry<K, V> o) {
            return this.key.compareTo(o.key);
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }


    @Override
    public int size() {
        return morseTable.size();
    }

    @Override
    public boolean isEmpty() {
        return morseTable.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        int location = Collections.binarySearch(morseTable, new Entry<>((K) key, null));
        return location > -1;
    }

    @Override
    public V get(Object key) {
        int location = Collections.binarySearch(morseTable, new Entry<>((K) key, null));
        if (location > -1) {
            return morseTable.get(location).value;
        } else {
            return null;
        }
    }

    @Override
    public V put(K key, V value) {
        int location = Collections.binarySearch(morseTable, new Entry<>((K) key, null));
        if (location > -1) {
            V previousValue = morseTable.get(location).getValue();
            morseTable.get(location).setValue(value);
            return previousValue;
        } else {
            morseTable.add((location * -1) - 1, new Entry<>(key, value));
            return null;
        }
    }

    @Override
    public V remove(Object key) {
        int location = Collections.binarySearch(morseTable, new Entry<>((K) key, null));
        if (location > -1) {
            V previousValue = morseTable.get(location).getValue();
            morseTable.remove(location);
            return previousValue;
        } else {
            return null;
        }
    }


    @Override
    public void clear() {
        morseTable = new ArrayList<>();
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }


}
