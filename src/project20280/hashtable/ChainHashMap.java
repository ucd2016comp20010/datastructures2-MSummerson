package project20280.hashtable;

import project20280.interfaces.Entry;
import project20280.interfaces.List;
import project20280.interfaces.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
 * Map implementation using hash table with separate chaining.
 */

public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {
    // a fixed capacity array of UnsortedTableMap that serve as buckets
    private UnsortedTableMap<K, V>[] table; // initialized within createTable

    /**
     * Creates a hash table with capacity 11 and prime factor 109345121.
     */
    public ChainHashMap() {
        super();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public ChainHashMap(int cap) {
        super(cap);
    }

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public ChainHashMap(int cap, int p) {
        super(cap, p);
    }

    /**
     * Creates an empty table having length equal to current capacity.
     */
    @Override
    @SuppressWarnings({"unchecked"})
    protected void createTable() {
        table = new UnsortedTableMap[capacity];
    }

    /**
     * Returns value associated with key k in bucket with hash value h. If no such
     * entry exists, returns null.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return associate value (or null, if no such entry)
     */
    @Override
    protected V bucketGet(int h, K k) {
        // TODO
        UnsortedTableMap<K,V> bucket = table[h];
        if (bucket == null) return null;
        return bucket.get(k);
    }

    /**
     * Associates key k with value v in bucket with hash value h, returning the
     * previously associated value, if any.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @param v the value to be associated
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketPut(int h, K k, V v) {
        // TODO
        UnsortedTableMap<K,V> bucket = table[h];
        if (bucket == null) {
            bucket = table[h] = new UnsortedTableMap<>();
        }
        int oldSize = bucket.size();
        V answer = bucket.put(k, v);
        n += (bucket.size() - oldSize);
        return answer;
    }


    /**
     * Removes entry having key k from bucket with hash value h, returning the
     * previously associated value, if found.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketRemove(int h, K k) {
        // TODO
        UnsortedTableMap<K,V> bucket = table[h];
        if (bucket == null) return null;
        int oldSize = bucket.size();
        V answer = bucket.remove(k);
        n -= (oldSize - bucket.size());
        return answer;
    }

    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        /*
        for each element in (UnsortedTableMap []) table
            for each element in bucket:
                print element
        */
        ArrayList<Entry<K, V>> entries = new ArrayList<>();
        for (UnsortedTableMap<K, V> tm : table) {
            if (tm != null) {
                for (Entry<K, V> e : tm.entrySet()) {
                    entries.add(e);
                }
            }
        }
        return entries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hash Table (Non-empty buckets only):\n");
        for (int i = 0; i < capacity; i++) {
            // Only append to the string if there is actually something in the bucket
            if (table[i] != null && !table[i].isEmpty()) {
                sb.append("Bucket ").append(i).append(": ");
                sb.append(table[i].toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }


        // (a) & (b) Polynomial Accumulation
        public static int polyHash(String s, int a) {
            int h = 0;
            for (int i = 0; i < s.length(); i++) {
                h = a * h + s.charAt(i);
            }
            return h;
        }

        // (c) & (d) Cyclic Shift
        public static int cyclicShiftHash(String s, int shift) {
            int h = 0;
            for (int i = 0; i < s.length(); i++) {
                h = (h << shift) | (h >>> (32 - shift)); // Cyclic shift
                h += s.charAt(i);
            }
            return h;
        }

    public static void main(String[] args) throws FileNotFoundException {
//        ChainHashMap<Integer, String> m = new ChainHashMap<Integer, String>();
//        m.put(1, "One");
//        m.put(10, "Ten");
//        m.put(11, "Eleven");
//        m.put(20, "Twenty");
//
//        System.out.println("m: " + m);
//
//        m.remove(11);
//        System.out.println("m: " + m);

//
//        ChainHashMap<Integer, Integer> map = new ChainHashMap<>(19);
//
//        int[] keysToInsert = {12, 44, 13, 88, 23, 94, 11, 39, 20, 16, 5};
//
//        for (int key : keysToInsert) {
//            map.put(key, key);
//        }
//
//        System.out.println("Final Hash Table Structure:");
//        System.out.println(map);
//
//        System.out.println("Value for key 94: " + map.get(94));

//
//        File f = new File("C:\\Users\\micha\\Downloads\\sample_text.txt");
//        if (!f.exists()) {
//            System.out.println("File not found! Please ensure sample_text.txt is in the project root.");
//            return;
//        }
//
//        ChainHashMap<String, Integer> counter = new ChainHashMap<>();
//
//        // 1. Read words and update counts
//        Scanner scanner = new Scanner(f);
//        while (scanner.hasNext()) {
//            // Normalize words: lowercase and remove basic punctuation
//            String word = scanner.next().toLowerCase().replaceAll("[^a-zA-Z]", "");
//
//            if (word.isEmpty()) continue;
//
//            Integer count = counter.get(word);
//            if (count == null) {
//                counter.put(word, 1);
//            } else {
//                counter.put(word, count + 1);
//            }
//        }
//        scanner.close();
//
//        // 2. Extract entries to a list for sorting
//        ArrayList<Entry<String, Integer>> list = new ArrayList<>();
//        for (Entry<String, Integer> entry : counter.entrySet()) {
//            list.add(entry);
//        }
//
//        // 3. Sort by value (frequency) in descending order
//        // We use a lambda expression for the Comparator
//        list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
//
//        // 4. Report top 10
//        System.out.println("\n--- Top 10 Most Frequent Words ---");
//        int limit = Math.min(10, list.size());
//        for (int i = 0; i < limit; i++) {
//            Entry<String, Integer> e = list.get(i);
//            System.out.printf("%d. %s: %d%n", (i + 1), e.getKey(), e.getValue());
//        }




//
//
//        File f = new File("C:\\Users\\micha\\Downloads\\words.txt"); // Ensure this path is correct
//        if (!f.exists()) {
//            System.out.println("File not found!");
//            return;
//        }
//
//        ArrayList<String> allWords = new ArrayList<>();
//        Scanner scanner = new Scanner(f);
//        while (scanner.hasNext()) {
//            String word = scanner.next().toLowerCase().replaceAll("[^a-zA-Z]", "");
//            if (!word.isEmpty()) allWords.add(word);
//        }
//        scanner.close();
//
//        System.out.println("Total words loaded: " + allWords.size());
//
//        // (a) Polynomial accumulation with a = 41
//        System.out.println("Collisions (Poly, a=41): " + countCollisions(allWords, "poly", 41));
//
//        // (b) Polynomial accumulation with a = 17
//        System.out.println("Collisions (Poly, a=17): " + countCollisions(allWords, "poly", 17));
//
//        // (c) Cyclic shift with shift = 7
//        System.out.println("Collisions (Cyclic, shift=7): " + countCollisions(allWords, "cyclic", 7));
//
//        // (d) Cyclic shift for 0 to 31
//        int minCollisions = Integer.MAX_VALUE;
//        int bestShift = -1;
//        System.out.println("\n--- Testing Cyclic Shifts 0-31 ---");
//        for (int s = 0; s <= 31; s++) {
//            int collisions = countCollisions(allWords, "cyclic", s);
//            if (collisions < minCollisions) {
//                minCollisions = collisions;
//                bestShift = s;
//            }
//        }
//        System.out.println("Best shift value: " + bestShift + " with " + minCollisions + " collisions.");
//


        File f = new File("C:\\Users\\micha\\Downloads\\words.txt");
        if (!f.exists()) {
            System.out.println("File not found!");
            return;
        }

        ArrayList<String> allWords = new ArrayList<>();
        Scanner scanner = new Scanner(f);
        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase().replaceAll("[^a-zA-Z]", "");
            if (!word.isEmpty()) allWords.add(word);
        }
        scanner.close();

        System.out.println("Total words loaded: " + allWords.size());

        // --- The specific answer for your question ---
        int oldCollisions = countCollisions(allWords, "old", 0);
        System.out.println("Collisions (Old Java Hash): " + oldCollisions);

        // --- Comparison with other methods ---
        System.out.println("Collisions (Poly, a=41): " + countCollisions(allWords, "poly", 41));
        System.out.println("Collisions (Cyclic, shift=7): " + countCollisions(allWords, "cyclic", 7));
    }

    private static int countCollisions(ArrayList<String> words, String type, int arg) {
        // We use a standard HashMap here just to track unique hash codes found
        java.util.HashMap<Integer, Boolean> foundHashes = new java.util.HashMap<>();
        int collisions = 0;

        for (String w : words) {
            int h = type.equals("poly") ? polyHash(w, arg) : cyclicShiftHash(w, arg);

            if (foundHashes.containsKey(h)) {
                collisions++;
            } else {
                foundHashes.put(h, true);
            }
        }
        return collisions;
    }

    public static int oldJavaHash(String s) {
        int hash = 0;
        int skip = Math.max(1, s.length() / 8);
        for (int i = 0; i < s.length(); i += skip) {
            hash = (hash * 37) + s.charAt(i);
        }
        return hash;
    }

}
