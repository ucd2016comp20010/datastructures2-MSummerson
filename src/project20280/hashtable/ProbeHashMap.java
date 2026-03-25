package project20280.hashtable;

import project20280.interfaces.Entry;

import java.util.ArrayList;

public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    private MapEntry<K, V>[] table;
    private final MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);

    public ProbeHashMap() {
        super();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public ProbeHashMap(int cap) {
        super(cap);
    }

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public ProbeHashMap(int cap, int p) {
        super(cap, p);
    }

    @Override
    protected void createTable() {
        table = new MapEntry[capacity];
    }

    private boolean isAvailable(int j) {
        return (table[j] == null || table[j] == DEFUNCT);
    }

    int findSlot(int h, K k) {
        // TODO
        int avail = -1;
        int j = h;
        do {
            if (isAvailable(j)) {
                if (avail == -1) {
                    avail = j;
                }
                if (table[j] == null){
                    break;
                }
            }
            else if(table[j].getKey().equals(k)){
                return j;
            }
            j = (j + 1) % capacity;
        } while (j != h);
        return -(avail + 1);
    }

    @Override
    protected V bucketGet(int h, K k) {
        // TODO
        int j = findSlot(h, k);
        if (j < 0) return null; // no match found
        return table[j].getValue( );
    }

    @Override
    protected V bucketPut(int h, K k, V v) {
        // TODO
        int j = findSlot(h, k);
        if (j >= 0) // this key has an existing entry
            return table[j].setValue(v);
        table[-(j + 1)] = new MapEntry<>(k, v); // convert to proper index
        n++;
        return null;
    }

    @Override
    protected V bucketRemove(int h, K k) {
        // TODO
        int j = findSlot(h, k);
        if (j < 0) return null; // nothing to remove
        V answer = table[j].getValue( );
        table[j] = DEFUNCT; // mark this slot as deactivated
        n--;
        return answer;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>( );
        for (int h=0; h < capacity; h++)
            if (!isAvailable(h)) buffer.add(table[h]);
        return buffer;
    }
}
