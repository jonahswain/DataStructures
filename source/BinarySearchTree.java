/*
DataStructures (Java implementation of certain data structures)
BinarySearchTree
Author: Jonah Swain
*/

import java.lang.RuntimeException;

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
public class BinarySearchTree<dataType, keyType extends Comparable<keyType>>{

    /** Head node of the tree */
    private BinarySearchTreeNode<dataType, keyType> headNode;

    /** Size of the tree (number of elements) */
    private long treeSize;

    /** Creates a new BinarySearchTree object */
    public BinarySearchTree(){
        this.headNode = null;
        this.treeSize = 0;
    }

    /**
     * Inserts a new entry into the binary search tree<br>
     * 
     * @param key  The key of the entry to insert
     * @param data  The data to insert
     */
    public void insert(keyType key, dataType data) throws java.lang.RuntimeException {
        if (this.headNode == null){ // If head node is null, insert the new node as the head node
            this.headNode = new BinarySearchTreeNode<dataType, keyType>(key, data);
        } else { // If the head node is not null, find where to insert the new node
            BinarySearchTreeNode<dataType, keyType> currentNode = this.headNode;
            while(true){
                if (currentNode.key().compareTo(key) > 0){ // Insert to the left
                    if (currentNode.getLeftChild() == null){ // Insert a child on the current node
                        currentNode.setLeftChild(new BinarySearchTreeNode<dataType, keyType>(key, data));
                        break; // Exit loop
                    } else { // Branch to the next node
                        currentNode = currentNode.getLeftChild();
                    }
                } else if (currentNode.key().compareTo(key) < 0){ // Insert to the right
                    if (currentNode.getRightChild() == null){ // Insert a child on the current node
                        currentNode.setRightChild(new BinarySearchTreeNode<dataType, keyType>(key, data));
                        break; // Exit loop
                    } else { // Branch to the next node
                        currentNode = currentNode.getRightChild();
                    }
                } else { // Keys are equal
                    throw new RuntimeException("Duplicate key error"); // Throw an error
                }
            }
        }

        this.treeSize++; // Increment tree size
    }

    /**
     * Gets the data located at the specified key in the tree<br>
     * 
     * @param key  The key of the entry to find
     * @return The data at the specified key (null if key not found)
     */
    public dataType get(keyType key){
        BinarySearchTreeNode<dataType, keyType> currentNode = this.headNode;
        while (currentNode != null){
            if (currentNode.key().compareTo(key) > 0){ // Search to the left
                currentNode = currentNode.getLeftChild();
            } else if (currentNode.key().compareTo(key) < 0){ // Search to the right
                currentNode = currentNode.getRightChild();
            } else { // Key is equal (return data)
                return currentNode.data();
            }
        }

        return null; // Key not found, return null
    }

    /**
     * Removes the entry in the tree with the specified key<br>
     * 
     * @param keyType  The key of the entry to remove
     */
    public void delete(keyType key){
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

    /**
     * Returns the depth of the tree
     * 
     * @return The depth of the tree
     */
    public int depth(){
        return this.headNode.depth();
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

    /**
     * Gets the key of the node
     * 
     * @return The key of the node
     */
    public keyType key(){
        return this.key;
    }

    /**
     * Gets the data of the node
     * 
     * @return The data of the node
     */
    public dataType data(){
        return this.data;
    }

    /**
     * Gets the left child node of the node
     * 
     * @return Node's left child node
     */
    public BinarySearchTreeNode<dataType, keyType> getLeftChild(){
        return this.leftChild;
    }

    /**
     * Gets the right child node of the node
     * 
     * @return Node's right child node
     */
    public BinarySearchTreeNode<dataType, keyType> getRightChild(){
        return this.rightChild;
    }

    /**
     * Sets the left child node of the node
     * 
     * @param BinarySearchTreeNode  The new left child node
     */
    public void setLeftChild(BinarySearchTreeNode<dataType, keyType> node){
        this.leftChild = node;
    }

    /**
     * Sets the right child node of the node
     * 
     * @param BinarySearchTreeNode  The new right child node
     */
    public void setRightChild(BinarySearchTreeNode<dataType, keyType> node){
        this.rightChild = node;
    }

    /**
     * A recursive helper method for the BinarySearchTree's toArray method
     * 
     * @return An array representing the sub-tree with the current node as a head
     */
    public dataType[] toArrayHelper(){
        // TODO
    }

    /**
     * A recursive helper method for the BinarySearchTree's toString method
     * 
     * @return A string representing the sub-tree with the current node as a head
     */
    public String toStringHelper(){
        // TODO
    }

    /**
     * Gets the maximum depth of the sub-tree with the current node as a head
     * 
     * @return The depth of the sub-tree with the current node as a head
     */
    public int depth(){
        // TODO
    }

}