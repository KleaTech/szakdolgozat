package hu.kleatech.jigsaw.utils;

import java.util.*;
import java.util.stream.Stream;

public class ListMap<K, V> implements Iterable<Map.Entry<K, V>> {

    private List<Map.Entry<K, V>> entryList = new LinkedList<>();

    public ListMap(){}
    public ListMap(Map<K, V> otherMap) {
        this.entryList = new LinkedList<>(otherMap.entrySet());
    }
    public ListMap(ListMap<K, V> otherMap) {
        this.entryList = new LinkedList<>(otherMap.entryList);
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return entryList.iterator();
    }

    public Stream<Map.Entry<K, V>> stream() {
        return entryList.stream();
    }

    public void put(K key, V value) {
        entryList.add(new AbstractMap.SimpleImmutableEntry<>(key, value));
    }

    public int size() {
        return entryList.size();
    }
    
    public UnmodifiableListMap unmodifiable() {
        return new UnmodifiableListMap(this);
    }
    
    public static <K, V> ListMap of(Map.Entry<K, V>... entries) {
        ListMap ret = new ListMap();
        Arrays.stream(entries).forEachOrdered(e -> ret.put(e.getKey(), e.getValue()));
        return ret;
    }
    
    public static <K, V> Map.Entry<K, V> e(K key, V value) {
        return Map.entry(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListMap)) return false;
        ListMap<?, ?> listMap = (ListMap<?, ?>) o;
        return Objects.equals(entryList, listMap.entryList);
    }

    @Override
    public int hashCode() { return Objects.hash(entryList); }

    @Override
    public String toString() {
        return "ListMap{" + entryList + '}';
    }
    
    public static class UnmodifiableListMap<K, V> extends ListMap<K, V> {    
        private UnmodifiableListMap(ListMap listMap) {
            super(listMap);
        }
        @Override
        public void put(K key, V value) {
            throw new UnsupportedOperationException("Attempting to modify an UnmodifiableListMap");
        }
    }
    
    public static enum Order {
        ASCENDING, DESCENDING
    }
}