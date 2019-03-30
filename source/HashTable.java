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
    

    public void insert(keyType key, dataType data){
        // TODO
    }

    public dataType get(keyType key){
        // TODO
    }

    public void delete(keyType key){
        // TODO
    }

    public float loadFactor(){
        return this.tableSize/this.maxTableSize;
    }

    public String toString(){
        // TODO
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