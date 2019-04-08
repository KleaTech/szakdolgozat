package hu.kleatech.jigsaw.utils;

import java.util.*;
import java.util.stream.Stream;

public class ListMap<K, V> implements Iterable<Map.Entry<K, V>> {

    private List<Map.Entry<K, V>> entryList = new LinkedList<>();

    public ListMap(){}
    public ListMap(Map<K, V> otherMap) {
        this.entryList = new LinkedList<>(otherMap.entrySet());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListMap)) return false;
        ListMap<?, ?> listMap = (ListMap<?, ?>) o;
        return Objects.equals(entryList, listMap.entryList);
    }

    @Override
    public int hashCode() { return Objects.hash(entryList); }
}