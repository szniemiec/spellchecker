import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ICS 23 Summer 2004
 * Project #5: Lost for Words
 * <p>
 * Implement your hash table here.  You are required to use the separate
 * chaining strategy that we discussed in lecture, meaning that collisions
 * are resolved by having each cell in the table be a linked list of all of
 * the strings that hashed to that cell.
 */

public class HashTable {

    private final int numberOfBuckets;
    private final StringHasher hasher;
    private final List<String>[] table;

    /**
     * The constructor is given a table size (i.e. how big to make the array)
     * and a StringHasher, which is used to hash the strings.
     *
     * @param tableSize number of elements in the hash array
     *                  hasher    Object that creates the hash code for a string
     * @see StringHasher
     */
    public HashTable(int tableSize, StringHasher hasher) {
        this.hasher = hasher;
        this.numberOfBuckets = tableSize;
        this.table = new ArrayList[numberOfBuckets];
        fillTable();
    }

    /**
     * Takes a string and adds it to the hash table, if it's not already
     * in the hash table.  If it is, this method has no effect.
     *
     * @param s String to add
     */
    public void add(String s) {
        int index = getIndex(s);
        table[index].add(s);
    }

    /**
     * Takes a string and returns true if that string appears in the
     * hash table, false otherwise.
     *
     * @param s String to look up
     */
    public boolean lookup(String s) {
        int index = getIndex(s);
        for (int i = 0; i < table[index].size(); i++) {
            if (table[index].get(i).equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes a string and removes it from the hash table, if it
     * appears in the hash table.  If it doesn't, this method has no effect.
     *
     * @param s String to remove
     */
    public void remove(String s) {
        int index = getIndex(s);
        table[index].removeIf(word -> word.equals(s));
    }

    public StringHasher getHasher() {
        return hasher;
    }

    private void fillTable() {
        for (int i = 0; i < numberOfBuckets; i++) {
            table[i] = new ArrayList<>();
        }
    }

    private int getIndex(String s) {
        int hashCode = this.hasher.hash(s);
        int index = hashCode % numberOfBuckets;
        return index < 0 ? index * -1 : index;
    }
}
