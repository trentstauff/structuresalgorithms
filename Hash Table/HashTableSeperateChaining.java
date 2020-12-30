import java.util.*;

public class HashTableSeperateChaining<K,V>{

    public class Entry<K,V> {

        K key;
        V value;
        int hash;

        Entry(K key, V value){

            this.key = key;
            this.value = value;
            this.hash = key.hashCode();
        }

        public boolean equals(Entry<K,V> otherEntry){
            if(this.hash != otherEntry.hash) return false;
            return this.key.equals(otherEntry.key);
        }
    }

    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    // size of hash table
    private int capacity = 0;

    // number of elements in the entire hash table
    private int size = 0;

    // the max cap on items before resizing
    private int threshold = 0;

    // the percentage of capacity allowed
    private double loadFactor = 0;

    // our actual hash table
    private LinkedList<Entry<K,V>> hashTable[];

    public HashTableSeperateChaining(){
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeperateChaining(int capacity, double loadFactor){

        if(capacity < 0) throw new IllegalArgumentException("Capacity is too low.");
        if (loadFactor <= 0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor))
            throw new IllegalArgumentException("Illegal loadFactor");
        
        // capacity shouldnt be below 3, strange things can happen if so
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        this.loadFactor = loadFactor;
        this.threshold = (int) this.loadFactor * this.capacity;
        this.hashTable = new LinkedList[capacity];
    }

     // --------------------------------------- USER INFORMATON FUNCTIONS ---------------------------------------

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        Arrays.fill(hashTable, null);
        size = 0;
    }  

    // Returns the list of keys found within the hash table
    public List<K> keys() {

        List<K> keys = new ArrayList<>(size());

        for (LinkedList<Entry<K, V>> bucket : hashTable)
          if (bucket != null) 
            for (Entry<K, V> entry : bucket) 
                keys.add(entry.key);

        return keys;
      }

    // Returns the list of values found within the hash table
    public List<V> values() {

        List<V> values = new ArrayList<>(size());

        for (LinkedList<Entry<K, V>> bucket : hashTable)
            if (bucket != null) 
                for (Entry<K, V> entry : bucket) 
                    values.add(entry.value);

        return values;
    }

    public void print(){

        for (LinkedList<Entry<K, V>> bucket : hashTable){
            if (bucket != null){
                System.out.print("[ ");
                for (Entry<K, V> entry : bucket) 
                    System.out.print(entry.key + ":" + entry.value + ", ");
                System.out.print("]");
                System.out.println("");
            } else {
                System.out.println("[NULL]");
            }
        }
    }

     // --------------------------------------- RETRIEVAL OF AN ENTRY/VALUE ---------------------------------------

    public boolean containsKey(K key){
        return hasKey(key);
    }

    public boolean hasKey(K key){
        
        int index = normalizeIndex(key.hashCode());
        return (bucketSeekEntry(index, key) != null);

    }

    // gets the value of a key
    public V get(K key){

        if(key == null) throw new IllegalArgumentException("Key cannot be null.");

        int index = normalizeIndex(key.hashCode()); 
        Entry<K,V> entry = bucketSeekEntry(index, key);

        if (entry != null) return entry.value;
            return null;

    }

    // looks for a key in a specified linked list and returns the whole entry
    private Entry<K,V> bucketSeekEntry(int index, K key){

            if(key == null) return null;
            
            LinkedList<Entry<K,V>> bucket = hashTable[index];

            if(bucket == null) return null;

            for(Entry<K,V> entry : bucket)
            {
                if(entry.key.equals(key)) return entry;
            }

            return null;
    }

    // --------------------------------------- ADDING OF AN ENTRY ---------------------------------------

    public V add(K key, V value){

        if(key == null) throw new IllegalArgumentException("Key cannot be null.");
        Entry<K,V> entry = new Entry<K,V>(key,value);
        int index = normalizeIndex(entry.hash);

        return bucketInsertEntry(index, entry);

    }

    private V bucketInsertEntry(int index, Entry<K,V> newEntry){

        if(newEntry == null) throw new IllegalArgumentException("Entry cannot be null.");

        LinkedList<Entry<K,V>> bucket = hashTable[index];

        // if no current LL in this index, create one
        if(bucket == null) hashTable[index] = bucket = new LinkedList<Entry<K,V>>();

        Entry<K,V> existentEntry = bucketSeekEntry(index, newEntry.key);

        if(existentEntry == null){
            
            bucket.add(newEntry);
            if(size > threshold) resizeTable();
            size++;
            // since there was no previous entry, use null to indicate
            return null;
        } else {
            V oldValue = existentEntry.value;
            existentEntry.value = newEntry.value;
            return oldValue;
        }
    }

    // --------------------------------------- REMOVAL OF AN ENTRY ---------------------------------------

    public K remove(K key){

        if(key == null) throw new IllegalArgumentException("Key to remove cannot be null.");

        return bucketRemoveEntry(key);

    }

    private K bucketRemoveEntry(K key){

        int index = key.hashCode();

        LinkedList<Entry<K,V>> bucket = hashTable[index];

        Entry<K,V> entryToRemove = bucketSeekEntry(index, key);
        if(entryToRemove == null ) 
        K removedKey = entryToRemove.key;
        bucket.remove(entryToRemove);
        return removedKey;
    }

    // --------------------------------------- WORKER FUNCTIONS ---------------------------------------

    // returns the index of the linked list that the entry may be in
    private int normalizeIndex(int keyHash){
        // the bitmask here removes the negative symbol (if occuring) from the keyHash
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    private void resizeTable(){

        // double the capacity
        capacity*=2;
        // proportionally increase the new threshold
        threshold = (int) (capacity * loadFactor);
        // spawn the new hash table
        LinkedList<Entry<K,V>>[] newHashTable = new LinkedList[capacity];

        for(LinkedList<Entry<K,V>> list : hashTable){
            
            if(list != null){
                for(Entry<K,V> entry : list){

                    int index = normalizeIndex(entry.hash);
                    LinkedList<Entry<K,V>> bucket = newHashTable[index];

                    if (bucket == null) newHashTable[index] = bucket = new LinkedList<>();
                    
                    bucket.add(entry);
                
                }
                list.clear();
                list = null;
            }
        }
        hashTable = newHashTable;
    }

    public static void main(String[] args) {
        HashTableSeperateChaining<String, Integer> hashTable = new HashTableSeperateChaining<>();
        // adding items to hash table
        hashTable.add("Trent", 0);
        hashTable.add("Trent", 1);
        hashTable.add("Kaveen", 2);
        hashTable.add("Adas", 3);
        hashTable.add("Jonah", 4);
        hashTable.add("Sarah", 5);
        hashTable.add("Will", 6);
        hashTable.add("Arden", 7);
        hashTable.add("Marcus", 8);
        hashTable.add("Test", 9);
        
        System.out.println(hashTable.capacity);
        System.out.println("");
        hashTable.remove("Adas");
        // System.out.println(hashTable.keys().toString());
        hashTable.print();
    }
}