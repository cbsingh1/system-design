package com.cbsingh.SystemDesign;

import java.util.LinkedList;

public class GenericHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private int size;
    private int capacity;
    private LinkedList<Entry<K,V>> [] buckets;

    static class Entry<K, V>{
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public GenericHashMap(){
        capacity = INITIAL_CAPACITY;
        size =0;
        buckets = new LinkedList[capacity];
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);

        if(buckets[bucketIndex]==null){
            buckets[bucketIndex] = new LinkedList<>();
        }

        for (Entry<K,V> entry : buckets[bucketIndex]){
            if(entry.key.equals(key)){
                entry.value = value;
                return;
            }
        }

        buckets[bucketIndex].add(new Entry<>(key, value));
        size++;

        if((double) size/capacity >= LOAD_FACTOR){
            resize();
        }
    }

    private void resize() {
        capacity *= 2;
        LinkedList<Entry<K,V>> []newBuckets = new LinkedList[capacity];

        for(LinkedList<Entry<K,V>> bucket : buckets){
            if(bucket != null){
                for (Entry<K, V> entry : bucket) {
                    int newBucketIndex = getBucketIndex(entry.key);
                    if(newBuckets[newBucketIndex] == null)
                        newBuckets[newBucketIndex] = new LinkedList<>();

                    newBuckets[newBucketIndex].add(entry);

                }
            }
        }
        buckets = newBuckets;
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);

        for(Entry<K,V> entry : buckets[bucketIndex]){
            if(entry.key.equals(key)){
                return entry.value;
            }
        }
        return null;
    }

    private int getBucketIndex(K key){
        int hashCode = key.hashCode();
        return hashCode % capacity;
    }

    public void remove(K key){
        int bucketIndex = getBucketIndex(key);

        if(buckets[bucketIndex] != null) {
            for(Entry<K,V> entry : buckets[bucketIndex]){
                if(entry.key.equals(key)){
                    buckets[bucketIndex].remove(entry);
                    size--;
                    return;
                }
            }
        }
    }

    public int size(){
        return size;
    }

    public static void main(String[] args) {
        GenericHashMap<String, Integer> ageMap = new GenericHashMap<>();
        ageMap.put("Alice", 30);
        ageMap.put("Bob", 25);

        System.out.println("Alice's age: " + ageMap.get("Alice")); // Output: Alice's age: 30
        System.out.println("Bob's age: " + ageMap.get("Bob"));     // Output: Bob's age: 25

        ageMap.remove("Alice");
       System.out.println("After removing Alice, Size of Map:  " + ageMap.size()); // Output: After removing Alice: 1
    }
}
