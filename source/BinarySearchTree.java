/*
DataStructures (Java implementation of certain data structures)
BinarySearchTree
Author: Jonah Swain
*/

/**
 * <h2>BinarySearchTree</h2>
 * A binary search tree data structure<br>
 * generic dataType is the (object) type of the data to be stored<br>
 * generic keyType is the (object) type of the key used for the node (must implement the Comparable interface)<br><br>
 * Uses tree traversal for some functions to avoid the overhead associated with recursion<br>
 * 
 * @author Jonah Swain (https://github.com/jonahswain/)
 * @since 10/03/2018
 */
public class BinarySearchTree<dataType, keyType extends Comparable<keyType>> {

    /** Head node of the tree */
    private BinarySearchTreeNode<dataType, keyType> headNode;

    /** Creates a new BinarySearchTree object */
    public BinarySearchTree(){
        this.headNode = null;
    }

    /**
     * Inserts a new entry into the binary search tree<br>
     * 
     * @param key  The key of the entry to insert
     * @param data  The data to insert
     */
    public void insert(keyType key, dataType data){
        // TODO
    }

    /**
     * Gets the data located at the specified key in the tree<br>
     * 
     * @param key  The key of the entry to find
     * @return The data at the specified key (null if key not found)
     */
    public dataType get(keyType key){
        // TODO
    }

    /**
     * Returns the contents of the tree as a sorted array (sorted by key)<br>
     * 
     * @return The contents of the tree as a sorted array (sorted by key)
     */
    public dataType[] toArray(){
        // TODO
    }

    /**
     * Returns the contents of the tree as a string, with each data element on a new line (the data elements must have toString methods)<br>
     * 
     * @return The contents of the tree as a string, with each data element seperated by a newline
     */
    public String toString(){
        // TODO
    }

}

/**
 * <h2>BinarySearchTreeNode</h2>
 * A node in a binary search tree data structure<br>
 * generic dataType is the (object) type of the data to be stored<br>
 * generic keyType is the (object) type of the key used for the node (must implement the Comparable interface)<br><br>
 * 
 * @author Jonah Swain (https://github.com/jonahswain/)
 * @since 10/03/2018
 */
class BinarySearchTreeNode<dataType, keyType extends Comparable<keyType>> {

    /** Left child node */
    private BinarySearchTreeNode<dataType, keyType> leftChild;

    /** Right child node */
    private BinarySearchTreeNode<dataType, keyType> rightChild;

    /** Node key */
    private keyType key;

    /** Node data */
    private dataType data;

    /**
     * Creates a new BinarySearchTreeNode object<br>
     * 
     * @param nodeKey  The node's key
     * @param nodeData  The node's data
     */
    public BinarySearchTreeNode(keyType nodeKey, dataType nodeData){
        this.leftChild = null;
        this.rightChild = null;
        this.key = nodeKey;
        this.data = nodeData;
    }

}