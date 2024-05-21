/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructuresfinalproject;

/**
 *
 * @author lawrence
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class HashTable<Key extends Comparable<? super Key>, E> {

    private int M;
    private KVpair<Key, E>[] HT;
    private KVpair<Key, E> Tombstone = new KVpair<Key, E>();

    int[] random;

    private int sfold(String x, int M) {
        char ch[];
        ch = x.toCharArray();
        int intlength = x.length() / 4;
        long sum = 0;
        int count = 0;
        for (int i = 0; i < intlength; i++) {
            sum += (((long) ch[count]) << 24) + (((long) ch[count + 1]) << 16)
                    + (((long) ch[count + 2]) << 8) + ((long) ch[count + 3]);
            count += 4;
        }
        return (Math.abs((int) sum) % M);
    }

    /**
     * If the key is an Integer, then use a simple mod function for the hash
     * function. If the key is a String, then use folding.
     */
    private int h(Key key) {
        Object keyO = (Object) key;
        if (keyO.getClass() == Integer.class) {
            int square = (Integer) keyO^2;
            int length = (int) (Math.log10(square) + 1);  // I add an extra parameter that return if the size was 3 digits or less
            if (length <= Math.log10(M)){
                return square % M;
            }
            int midDigits = getMidDigit(square);
            return midDigits % M;
        } else if (keyO.getClass() == String.class) {
            return sfold((String) keyO, M);
        } else {
            return key.hashCode() % M;
        }
    }
    private int getMidDigit(int key) {
        int number = key;
        String numberStr = Integer.toString(number);
        int length = numberStr.length();
        int midIndex = length / 2;
        if (length%2 == 0){
            return Integer.parseInt(numberStr.substring(midIndex - 1,midIndex + 1));
        }else{
            return Integer.parseInt(numberStr.substring(midIndex,midIndex + 1));
        }
    }

    private int p(Key key, int slot) {
        return random[(slot+1)%M];
    }

    @SuppressWarnings("unchecked") // Generic array allocation
    HashTable(int m) {
        M = m;
        HT = (KVpair<Key, E>[]) new KVpair[M];
        random = new int[M];
        for (int i = 0; i < M; i++){
            random[i] = i;
        }

        for (int i = 1; i < M/2; i++){
            int temp = random[((i+i+69)*137)%M];
            random[((i+i+69)*137)%M] = random[i];
            random[i] = temp;
        }
        System.out.println("");
        System.out.print("Pseudo-random:");
        for (int i = 0; i < M; i++){
            System.out.print(random[i]+" ");
        }
    }

    void empty() {
        HT = (KVpair<Key, E>[]) new KVpair[M];
    }

    /**
     * Insert record r with key k into HT
     */


    void hashInsert(Key k, E r) {
        int home;                                  // Home position for r
        int pos = home = h(k); // Initial position
        int counter = 0;
        int tombPosition = -1; // Initialize to an invalid position

        for (int i = 1; HT[pos] != null && i <= M; i++) {
            if (HT[pos] != Tombstone && HT[pos].key().compareTo(k) == 0) {
                System.out.println("Duplicates not allowed");
                return;
            }
            if (HT[pos] == Tombstone && counter == 0) {
                counter++;
                tombPosition = pos;
            }
            pos = (home + p(k, i)) % M;            // Next probe slot
        }

        if (counter == 1) {
            HT[tombPosition] = new KVpair<>(k, r);
        } else if (HT[pos] == null) {
            HT[pos] = new KVpair<>(k, r);
        } else {
            System.out.println("Hash table is full, cannot insert");
        }
    }




    /**
     * Search in hash table HT for the record with key k
     */
    E hashSearch(Key k) {
        int home;                   // Home position for k
        int pos = home = h(k);      // Initial position

        for (int i = 0; i < M; i++) {
            // Check if the current slot contains the key we are looking for
            if (HT[pos] != null && HT[pos] != Tombstone && HT[pos].key().compareTo(k) == 0) {
                return HT[pos].value();  // Found it
            }
            pos = (home + p(k, i)) % M;  // Next probe position
        }
            return null;
    }




    /**
     * Remove a record with key value k from the hash table
     * BROKEN UNTIL TOMBSTONES ARE IMPLEMENTED!!!
     */
    E hashRemove(Key k) {
        int home;                   // Home position for k
        int pos = home = h(k);      // Initial position
        boolean counter = false;
        for (int i = 0; i < M && HT[pos] != null; i++){
            if ((HT[pos] != Tombstone) && (HT[pos].key().compareTo(k) == 0)) {
                E e = HT[pos].value();
                HT[pos] = Tombstone;  // Mark as tombstone
                return e;  // Found and removed
            }
            pos = (home + p(k, i)) % M;  // Next probe position

                if (HT[pos] != null && HT[pos] != Tombstone) {
                    counter = true;
                }
            }
        if (counter) {
            System.out.println("Key does not exist");  // Key not in hash table
        } else {
            System.out.println("Array is empty");
        }
        return null;
    }

    public String toString() {
        String out = "";
        for (int i = 0; i<HT.length; i++)
            //if (HT[i] != null)
                out = out + i + ":" + HT[i] + ", ";
        return out;
    }

}
