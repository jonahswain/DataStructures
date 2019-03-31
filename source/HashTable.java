/*
DataStructures (Java implementation of certain data structures)
Hash Table
Author: Jonah Swain
*/

import java.lang.RuntimeException;
import java.lang.reflect.Array;

/**
 * <h2>HashTable</h2>
 * A has table data structure<br>
 * generic dataType is the (object) type of the data to be stored<br>
 * generic keyType is the (object) type of the key used<br><br>
 * Uses the builtin hashCode function of the kayType object (all Java Objects have a hashCode function, it is recommended to override it with a good implementation on custom key objects)<br>
 * The table size is prime (if a non-prime table size is provided, it will be increased until it is prime)<br>
 * 
 * @author Jonah Swain (https://github.com/jonahswain/)
 * @since 30/03/2018
 */
public class HashTable<dataType, keyType extends Comparable<keyType>>{

    /** Linear probing collision resolution */
    public static final int linearProbing = 1;
    /** Quadratic probing collision resolution */
    public static final int quadraticProbing = 2;
    /** Chaining collision resolution */
    public static final int chaining = 3;

    /** 'Table' (array) to store the nodes in */
    private HashTableNode<dataType, keyType>[] table;

    /** Number of elements in the table */
    private int tableSize;

    /** Maximum number of elements storable (N/A for chaining collision resolution) */
    private int maxTableSize;

    /** Collision resolution mode (linear/quadratic probing, chaining) */
    private int collisionResolutionMode;

    /** Returns the smallest prime number greater than/equal to a given number<br>
     * 
     * @param num  The number to test/find the smallest prime greater than/equal to
     * @return The smallest prime greater than/equal to the given number
     */
    public static int nextPrime(int num){
        if (num > 1){ // Test if number is greater than 1
            if (num <= 3){ // If number is 2 or 3, then is prime, return num
                return num;
            } else if (num%2 == 0) { // If the number is divisible by 2, test the next number
                return nextPrime(num + 1);
            } else if (num%3 == 0){ // If the number is divisible by 3, test the next odd number
                return nextPrime(num + 2);
            } else {
                int div = 5; // Start with a divisor of 5
                while (div*div < num){ // While the divisor is less than the square root of the number to test, test whether the number is divisible by the divisor or the divisor + 2
                    if ((num%div == 0) || (num%(div + 2) == 0)){
                        return nextPrime(num + 2); // If the number is not prime, test the next odd number
                    }
                    div += 6; // Increment the divisor by 6
                }
                return num; // Number is prime, return it
            }
        } else { // Number < 1, return the first prime (2)
            return 2;
        }
    }

    @SuppressWarnings("unchecked") // Supress warnings about unchecked operations
    /** Creates a new HashTable object with a specified size and collision resolution scheme<br>
     * The table size is prime (if a non-prime table size is provided, it will be increased until it is prime)<br>
     * 
     * @param size  The size of the table (must be prime, or next prime will be used)
     * @param collisionResolution  The collision resolution scheme to use (linear/quadratic probing, chaining)
     */
    public HashTable(int size, int collisionResolution){
        this.maxTableSize = nextPrime(size); // Set the maximum table size to the next prime number
        if ((collisionResolution == 1) || (collisionResolution == 2) || (collisionResolution == 3)){ // Check that a valid collision resolution scheme is chosen
            this.collisionResolutionMode = collisionResolution;
        } else {
            throw new RuntimeException("Invalid collision resolution scheme selected");
        }
        
        this.table = (HashTableNode<dataType, keyType>[]) Array.newInstance(new HashTableNode<dataType, keyType>(null, null).getClass(), this.maxTableSize); // Create table array
        this.tableSize = 0; // Set table size to zero
    }
    
    /** Inserts a new entry into the hash table<br>
     * 
     * @param key  The key of the entry to insert
     * @param data  The data to insert
     */
    public void insert(keyType key, dataType data){
        if ((this.tableSize >= this.maxTableSize) && (this.collisionResolutionMode != chaining)){ // Verify that there is space to insert into the table
            throw new RuntimeException("Hash Table is full"); // Throw an error if the table is full
        }
        int tableIndex = key.hashCode() % this.maxTableSize; // Compute the table index (hash mod table max size)
        if (this.table[tableIndex] == null){ // Check for collisions
            this.table[tableIndex] = new HashTableNode<dataType, keyType>(key, data); // If no collision, insert at relevant index
            this.tableSize++; // Increment the table size (number of elements)
        } else { // Resolve collision
            if (this.collisionResolutionMode == linearProbing){ // Resolve by linear probing
                int offset = 1;
                while (offset < this.maxTableSize){
                    if (this.table[(tableIndex + offset) % this.maxTableSize] == null){
                        this.table[(tableIndex + offset) % this.maxTableSize] = new HashTableNode<dataType, keyType>(key, data); // If no collision, insert at relevant index
                        this.tableSize++; // Increment the table size (number of elements)
                        break;
                    }
                    offset++; // Increment offset
                }
            } else if (this.collisionResolutionMode == quadraticProbing){ // Resolve by quadratic probing
                int offset = 1;
                while (offset < this.maxTableSize){
                    if (this.table[(tableIndex + offset*offset) % this.maxTableSize] == null){
                        this.table[(tableIndex + offset*offset) % this.maxTableSize] = new HashTableNode<dataType, keyType>(key, data); // If no collision, insert at relevant index
                        this.tableSize++; // Increment the table size (number of elements)
                        break;
                    }
                    offset++; // Increment offset
                }
            } else if (this.collisionResolutionMode == chaining){ // Resolve by chaining
                HashTableNode<dataType, keyType> currentChainNode = this.table[tableIndex];
                while(currentChainNode.getChainedNode() != null){ // Traverse the chain until a null reference is found to insert at
                    currentChainNode = currentChainNode.getChainedNode();
                }
                currentChainNode.setChainedNode(new HashTableNode<dataType, keyType>(key, data)); // Insert a node in the chain
                this.tableSize++; // Increment the table size (number of elements)
            }
        }
    }

    /** Gets the data located at the specified key in the table<br>
     * 
     * @param key  The key of the entry to find
     * @return Data at the specified key (null if key not found)
     */
    public dataType get(keyType key){
        int tableIndex = key.hashCode() % maxTableSize; // Compute table index (key hash mod max table size)
        if (this.table[tableIndex] == null){ // Check if an element exists at the table index
            return null; // Return null if no element found
        } else if (this.table[tableIndex].key().equals(key)){ // Check if the found element key matches the key to get
            return this.table[tableIndex].data(); // Return the data if the key matches
        } else { // Traverse table using collision resolution method to find the correct key
            if (this.collisionResolutionMode == linearProbing){ // Linear probing
                int offset = 1;
                while ((offset < this.maxTableSize) && (this.table[(tableIndex + offset) % this.maxTableSize] != null)){ // Traverse through the table until there are no more relevant elements to check
                    if (this.table[(tableIndex + offset) % this.maxTableSize].key().equals(key)){ // Check if the key of the current element matches the requested key
                        return this.table[(tableIndex + offset) % this.maxTableSize].data(); // Return the data if key matches
                    }
                    offset++; // Increment offset
                }
            } else if (this.collisionResolutionMode == quadraticProbing){ // Quadratic probing (use offset squared)
                int offset = 1;
                while ((offset < this.maxTableSize) && (this.table[(tableIndex + offset*offset) % this.maxTableSize] != null)){ // Traverse through the table until there are no more relevant elements to check
                    if (this.table[(tableIndex + offset*offset) % this.maxTableSize].key().equals(key)){ // Check if the key of the current element matches the requested key
                        return this.table[(tableIndex + offset*offset) % this.maxTableSize].data(); // Return the data if key matches
                    }
                    offset++; // Increment offset
                }
            } else if (this.collisionResolutionMode == chaining){ // Chaining
                HashTableNode<dataType, keyType> currentChainNode = this.table[tableIndex];
                while (currentChainNode.getChainedNode() != null){ // Traverse the chain until either the required key is found or the chain ends
                    if (currentChainNode.getChainedNode().key().equals(key)){ // Check the key of the next node in the chain
                        return currentChainNode.getChainedNode().data(); // Return the data if the key matches
                    }
                    currentChainNode = currentChainNode.getChainedNode();
                }
            }
        }
        return null; // If key not found, return null
    }

    /** Removes the entry in the tree with the specified key<br>
     * 
     * @param key  The key of the entry to remove
     */
    public void delete(keyType key){
        int tableIndex = key.hashCode() % maxTableSize; // Compute table index (key hash mod max table size)
        if (this.table[tableIndex] == null){ // Check if an element exists at the table index
            return; // Return if key does not exist in table
        } else if (this.table[tableIndex].key().equals(key)){ // Check if the found element key matches the key to get
            this.table[tableIndex] = null; // Delete the element if the key matches
            this.tableSize--; // Decrement table size (number of items stored)
        } else { // Traverse table using collision resolution method to find the correct key
            if (this.collisionResolutionMode == linearProbing){ // Linear probing
                int offset = 1;
                while ((offset < this.maxTableSize) && (this.table[(tableIndex + offset) % this.maxTableSize] != null)){ // Traverse through the table until there are no more relevant elements to check
                    if (this.table[(tableIndex + offset) % this.maxTableSize].key().equals(key)){ // Check if the key of the current element matches the requested key
                        this.table[(tableIndex + offset) % this.maxTableSize] = null; // Delete the element if the key matches
                        this.tableSize--; // Decrement table size (number of items stored)
                        break;
                    }
                    offset++; // Increment offset
                }
            } else if (this.collisionResolutionMode == quadraticProbing){ // Quadratic probing (use offset squared)
                int offset = 1;
                while ((offset < this.maxTableSize) && (this.table[(tableIndex + offset*offset) % this.maxTableSize] != null)){ // Traverse through the table until there are no more relevant elements to check
                    if (this.table[(tableIndex + offset*offset) % this.maxTableSize].key().equals(key)){ // Check if the key of the current element matches the requested key
                        this.table[(tableIndex + offset*offset) % this.maxTableSize] = null; // Delete the element if the key matches
                        this.tableSize--; // Decrement table size (number of items stored)
                        break;
                    }
                    offset++; // Increment offset
                }
            } else if (this.collisionResolutionMode == chaining){ // Chaining
                HashTableNode<dataType, keyType> currentChainNode = this.table[tableIndex];
                while (currentChainNode.getChainedNode() != null){ // Traverse the chain until either the required key is found or the chain ends
                    if (currentChainNode.getChainedNode().key().equals(key)){ // Check the key of the next node in the chain
                        currentChainNode.setChainedNode(currentChainNode.getChainedNode().getChainedNode()); // Set the chained node of the current node to the chained node of the chained node of the current node, effectively removing the chained node from the chain, deleting the required element
                        this.tableSize--; // Decrement table size (number of items stored)
                        break;
                    }
                    currentChainNode = currentChainNode.getChainedNode();
                }
            }
        }
    }

    /** Gets the load factor of the table<br>
     * 
     * @return Load factor of the table
     */
    public float loadFactor(){
        return this.tableSize/this.maxTableSize;
    }

    @SuppressWarnings("unchecked") // Supress warnings about unchecked operations
    /** Expands the table and re-inserts all the data elements<br>
     * The new table size must be prime, or the next prime number will be used instead<br>
     * 
     * @param newSize  The new table size
     */
    public void expandTable(int newSize){
        if (newSize > this.maxTableSize){
            newSize = nextPrime(newSize); // Ensure the new size is a prime number
            HashTableNode<dataType, keyType>[] oldTable = this.table;
            int oldTableSize = this.maxTableSize;

            this.maxTableSize = newSize;
            this.table = (HashTableNode<dataType, keyType>[]) Array.newInstance(new HashTableNode<dataType, keyType>(null, null).getClass(), this.maxTableSize); // Create new table array

            for (int i = 0; i < oldTableSize; i++){ // Iterate through every element in the old table
                if (oldTable[i] != null){ // If the element is not null, add it to the new table
                    this.insert(oldTable[i].key(), oldTable[i].data());
                    if (this.collisionResolutionMode == chaining){ // If chaining collision resolution is used, add any nodes in the chain to the new table
                        HashTableNode<dataType, keyType> currentChainNode = oldTable[i];
                        while (currentChainNode.getChainedNode() != null){
                            this.insert(currentChainNode.getChainedNode().key(), currentChainNode.getChainedNode().data());
                            currentChainNode = currentChainNode.getChainedNode();
                        }
                    }
                }
            }

        } else {
            throw new RuntimeException("New table size must be larger than current table size");
        }
    }

    /** Returns the contents of the table as a string, with each data element on a new line (the data elements must have toString methods)<br>
     * 
     * @return The contents of the table as a string, with each data element seperated by a newline
     */
    public String toString(){
        String str = "";
        for (int i = 0; i < this.maxTableSize; i++){ // Iterate through every element in the table
            if (this.table[i] != null){ // If the element is not null, add it to the string
                str = str + this.table[i].data().toString() + "\n";
                if (this.collisionResolutionMode == chaining){ // If chaining collision resolution is used, add any nodes in the chain to the string
                    HashTableNode<dataType, keyType> currentChainNode = this.table[i];
                    while (currentChainNode.getChainedNode() != null){
                        str = str + currentChainNode.getChainedNode().data().toString() + "\n";
                        currentChainNode = currentChainNode.getChainedNode();
                    }
                }
            }
        }
        return str;
    }
}

/**
 * <h2>HashTableNode</h2>
 * A node in a has table data structure<br>
 * generic dataType is the (object) type of the data to be stored<br><br>
 * 
 * @author Jonah Swain (https://github.com/jonahswain/)
 * @since 30/03/2018
 */
class HashTableNode<dataType, keyType extends Comparable<keyType>>{

    /** Node key */
    private keyType key;

    /** Node data */
    private dataType data;

    /** Next node in chain (for chaining collision resolution) */
    private HashTableNode<dataType, keyType> chainedNode;

    /** Creates a new HashTableNode object<br>
     * 
     * @param nodeKey  The node's key
     * @param nodeData  The node's data
     */
    public HashTableNode(keyType nodeKey, dataType nodeData){
        this.key = nodeKey;
        this.data = nodeData;
    }

    /** Gets the key of the node<br>
     * 
     * @return The key of the node
     */
    public keyType key(){
        return this.key;
    }

    /** Gets the data of the node<br>
     * 
     * @return The data of the node
     */
    public dataType data(){
        return this.data;
    }

    /** Gets the next node in the chain (for chaining collision resolution)
     * 
     * @return The next chained node
     */
    public HashTableNode<dataType, keyType> getChainedNode(){
        return this.chainedNode;
    }

    /** Sets the next node in the chain (for chaining collision resolution)
     * 
     * @param node  The next chained node
     */
    public void setChainedNode(HashTableNode<dataType, keyType> node){
        this.chainedNode = node;
    }

}