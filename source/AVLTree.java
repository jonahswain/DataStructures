/*
DataStructures (Java implementation of certain data structures)
AVLTree
Author: Jonah Swain
*/

import java.lang.RuntimeException;
import java.lang.reflect.Array;

/**
 * <h2>AVLTree</h2>
 * An AVL tree data structure<br>
 * generic dataType is the (object) type of the data to be stored<br>
 * generic keyType is the (object) type of the key used for the node (must implement the Comparable interface)<br><br>
 * Uses tree traversal for some functions to avoid the overhead associated with recursion<br>
 * 
 * @author Jonah Swain (https://github.com/jonahswain/)
 * @since 10/03/2018
 */
public class AVLTree<dataType, keyType extends Comparable<keyType>>{

    /** Head node of the tree */
    private AVLTreeNode<dataType, keyType> headNode;

    /** Size of the tree (number of elements) */
    private int treeSize;

    /** Creates a new AVLTree object */
    public AVLTree(){
        this.headNode = null;
        this.treeSize = 0;
    }

    /**
     * Inserts a new entry into the AVL tree<br>
     * 
     * @param key  The key of the entry to insert
     * @param data  The data to insert
     */
    public void insert(keyType key, dataType data) throws java.lang.RuntimeException {
        if (this.headNode == null){ // If head node is null, insert the new node as the head node
            this.headNode = new AVLTreeNode<dataType, keyType>(key, data);
        } else { // If the head node is not null, find where to insert the new node
            AVLTreeNode<dataType, keyType> currentNode = this.headNode;
            while(true){
                if (currentNode.key().compareTo(key) > 0){ // Insert to the left
                    if (currentNode.getLeftChild() == null){ // Insert a child on the current node
                        currentNode.setLeftChild(new AVLTreeNode<dataType, keyType>(key, data, currentNode));
                        break; // Exit loop
                    } else { // Branch to the next node
                        currentNode = currentNode.getLeftChild();
                    }
                } else if (currentNode.key().compareTo(key) < 0){ // Insert to the right
                    if (currentNode.getRightChild() == null){ // Insert a child on the current node
                        currentNode.setRightChild(new AVLTreeNode<dataType, keyType>(key, data, currentNode));
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
        AVLTreeNode<dataType, keyType> currentNode = this.headNode;
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
     * @param key  The key of the entry to remove
     */
    public void delete(keyType key){
        // Search for node with specified key
        AVLTreeNode<dataType, keyType> currentNode = this.headNode;
        while (currentNode != null){
            if (currentNode.key().compareTo(key) > 0){ // Search to the left
                currentNode = currentNode.getLeftChild();
            } else if (currentNode.key().compareTo(key) < 0){ // Search to the right
                currentNode = currentNode.getRightChild();
            } else { // Key is equal (delete node)
                this.delete(currentNode);
            }
        }
    }

    /**
     * Removes a node from the tree<br>
     * 
     * @param node  The node to remove
     */
    public void delete(AVLTreeNode<dataType, keyType> node) throws java.lang.RuntimeException {
        if (node.getLeftChild() == null && node.getRightChild() == null){ // Check if node is a leaf node
            if (node.getParent().getLeftChild() == node){ // Node is parents' left child
                node.getParent().setLeftChild(null); // Delete node
            } else if (node.getParent().getRightChild() == node){ // Node is parents' right child
                node.getParent().setRightChild(null); // Delete node
            } else { // Node is not a child of parent (unknown error?)
                throw new RuntimeException("Node is not a child of parent");
            }
        } else if (node.getLeftChild() != null){ // Node has a left child to get its replacement from
            AVLTreeNode<dataType, keyType> replacementNode = node.getLeftChild();
            while (replacementNode.getRightChild() != null){ // Get the right-most child of the left child (key closest to key of node to be deleted)
                replacementNode = replacementNode.getRightChild();
            }
            if (replacementNode.getLeftChild() != null){ // Check if replacement node has a child
                replacementNode.getLeftChild().setParent(replacementNode.getParent()); // Set the parent of the child to the parent of the replacement node
                replacementNode.getParent().setRightChild(replacementNode.getLeftChild()); // Re-attach the replacement node's child to its parent
            }
            // Replace node to be deleted
            if (node.getParent().getLeftChild() == node){ // Node is parents' left child
                replacementNode.setParent(node.getParent()); // Set the replacement node's parent to the parent of the node to be deleted
                node.getParent().setLeftChild(replacementNode); // Replace node
            } else if (node.getParent().getRightChild() == node){ // Node is parents' right child
                replacementNode.setParent(node.getParent()); // Set the replacement node's parent to the parent of the node to be deleted
                node.getParent().setRightChild(replacementNode); // Replace node
            } else { // Node is not a child of parent (unknown error?)
                throw new RuntimeException("Node is not a child of parent");
            }
        } else if (node.getRightChild() != null){ // Node has a right child to get its replacement from
            AVLTreeNode<dataType, keyType> replacementNode = node.getRightChild();
            while (replacementNode.getLeftChild() != null){ // Get the left-most child of the right child (key closest to key of node to be deleted)
                replacementNode = replacementNode.getLeftChild();
            }
            if (replacementNode.getRightChild() != null){ // Check if replacement node has a child
                replacementNode.getRightChild().setParent(replacementNode.getParent()); // Set the parent of the child to the parent of the replacement node
                replacementNode.getParent().setLeftChild(replacementNode.getRightChild()); // Re-attach the replacement node's child to its parent
            }
            // Replace node to be deleted
            if (node.getParent().getLeftChild() == node){ // Node is parents' left child
                replacementNode.setParent(node.getParent()); // Set the replacement node's parent to the parent of the node to be deleted
                node.getParent().setLeftChild(replacementNode); // Replace node
            } else if (node.getParent().getRightChild() == node){ // Node is parents' right child
                replacementNode.setParent(node.getParent()); // Set the replacement node's parent to the parent of the node to be deleted
                node.getParent().setRightChild(replacementNode); // Replace node
            } else { // Node is not a child of parent (unknown error?)
                throw new RuntimeException("Node is not a child of parent");
            }
        }
    }

    /**
     * Rotates a sub-tree left about node<br>
     * 
     * @param node  The node to rotate about
     */
    public void rotateLeft(AVLTreeNode<dataType, keyType> node) throws java.lang.RuntimeException{
        if (node.getRightChild() != null){ // Check if node has a child to rotate into its place
            AVLTreeNode<dataType, keyType> treeParent = node.getParent();
            AVLTreeNode<dataType, keyType> newHead = node.getRightChild();
            node.setRightChild(newHead.getLeftChild());
            node.getRightChild().setParent(node);
            newHead.setLeftChild(node);
            node.setParent(newHead);

            if (this.headNode == node){ // Special case for rotating the head node
                // Set the new tree head node
                this.headNode = newHead;
                this.headNode.setParent(null);
            } else { // Standard case
                // Set the tree link with the new head
                if (treeParent.getLeftChild() == node){ // Node is parents' left child
                    treeParent.setLeftChild(newHead); // Replace node
                } else if (treeParent.getRightChild() == node){ // Node is parents' right child
                    treeParent.setRightChild(newHead); // Replace node
                } else { // Node is not a child of parent (unknown error?)
                    throw new RuntimeException("Node is not a child of parent");
                }
            }
            
        } else {
            throw new RuntimeException("Node has no child to rotate into its place"); // Throw an error
        }
    }

    /**
     * Rotates a sub-tree right about node<br>
     * 
     * @param node  The node to rotate about
     */
    public void rotateRight(AVLTreeNode<dataType, keyType> node){
        if (node.getLeftChild() != null){ // Check if node has a child to rotate into its place

            AVLTreeNode<dataType, keyType> treeParent = node.getParent(); // Parent of the sub-tree to be rotated
            AVLTreeNode<dataType, keyType> newHead = node.getLeftChild(); // New head node of the sub tree
            node.setLeftChild(newHead.getRightChild()); // Replace the left child of the node with the right child of the new head
            node.getLeftChild().setParent(node);
            newHead.setRightChild(node); // Replace the old head with the new head
            node.setParent(newHead);

            if (this.headNode == node){ // Special case for rotating the head node
                // Set the new tree head node
                this.headNode = newHead;
                this.headNode.setParent(null);
            } else { // Standard case
                // Set the tree link with the new head
                if (treeParent.getLeftChild() == node){ // Node is parents' left child
                    treeParent.setLeftChild(newHead); // Replace node
                } else if (treeParent.getRightChild() == node){ // Node is parents' right child
                    treeParent.setRightChild(newHead); // Replace node
                } else { // Node is not a child of parent (unknown error?)
                    throw new RuntimeException("Node is not a child of parent");
                }
            }

        } else {
            throw new RuntimeException("Node has no child to rotate into its place"); // Throw an error
        }
    }

    /**
     * Returns the contents of the tree as a sorted array (sorted by key)<br>
     * 
     * @return The contents of the tree as a sorted array (sorted by key)
     */
    public dataType[] toArray() {
        if (headNode != null){ // Ensure the tree is not empty
            @SuppressWarnings("unchecked") // Supress warnings about unchecked operations
            dataType[] array = (dataType[]) Array.newInstance(this.headNode.data().getClass(), this.treeSize); // Create a new array to store the tree data
            this.populateArray(this.headNode, array, 0); // Populate the array
            return array;
        } else {
            return null;
        }
    }

    /**
     * Populates an array with sorted elements of the tree<br>
     * 
     * @param node  The head node of the sub-tree
     * @param array  The array to populate
     * @param arrayIndex  The current index in the array
     * @return The new index in the array (after inserts)
     */
    public int populateArray(AVLTreeNode<dataType, keyType> node, dataType[] array, int arrayIndex){
        // Populate the array with the left-most node first
        if (node.getLeftChild() != null){ // If there is a left child node, recurse to it to populate the array
            arrayIndex = populateArray(node.getLeftChild(), array, arrayIndex);
        }
        array[arrayIndex] = node.data(); // Place the data from the current node into the array
        arrayIndex++; // Increment the array index
        if (node.getRightChild() != null){ // If there is a right child node, recurse to it to populate the array
            arrayIndex = populateArray(node.getRightChild(), array, arrayIndex);
        }

        return arrayIndex; // Return the new array index
    }

    /**
     * Returns the contents of the tree as a string, with each data element on a new line (the data elements must have toString methods)<br>
     * 
     * @return The contents of the tree as a string, with each data element seperated by a newline
     */
    public String toString(){
        if (this.headNode != null){ // If the tree is non-empty, return its string
            return this.toString(this.headNode);
        } else {
            return "";
        }
    }

    /**
     * Returns the contents of the tree with head 'node' as a string with each data element on a new line<br>
     * 
     * @param node  The head of the tree to represent as a string
     * @return The contents of the tree as a string
     */
    public String toString(AVLTreeNode<dataType, keyType> node){
        String string = ""; // Create an empty string to append to
        if (node.getLeftChild() != null){ // If the node has a left child, recurse to it and append its respective string
            string += this.toString(node.getLeftChild());
        }
        string += node.data().toString() + "\n"; // Append this node's data string
        if (node.getRightChild() != null){ // If the node has a right child, recurse to it and append its respective string
            string += this.toString(node.getLeftChild());
        }
        return string; // Return the string
    }

    /**
     * Returns the depth of the tree<br>
     * 
     * @return The depth of the tree
     */
    public int depth(){
        if (this.headNode != null){ // Verify the tree is not empty, and calculate/return the depth
            return this.depth(this.headNode);
        } else {
            return 0;
        }
    }

    /**
     * Returns the depth of the tree with head 'node'<br>
     * 
     * @param node  The head of the tree to get the depth of
     * @return The depth of the tree
     */
    public int depth(AVLTreeNode<dataType, keyType> node){
        int leftChildDepth = 0; // Depth of left sub-tree
        int rightChildDepth = 0; // Depth of right sub-tree
        if (node.getLeftChild() != null){ // If the node has a left child, calculate the depth
            leftChildDepth = this.depth(node.getLeftChild());
        }
        if (node.getRightChild() != null){ // If the node has a right child, calculate the depth
            rightChildDepth = this.depth(node.getRightChild());
        }
        if (leftChildDepth > rightChildDepth){ // Return the max depth of the children plus one
            return leftChildDepth + 1;
        } else {
            return rightChildDepth + 1;
        }
    }

}

/**
 * <h2>AVLTreeNode</h2>
 * A node in an AVL tree data structure<br>
 * generic dataType is the (object) type of the data to be stored<br>
 * generic keyType is the (object) type of the key used for the node (must implement the Comparable interface)<br><br>
 * 
 * @author Jonah Swain (https://github.com/jonahswain/)
 * @since 10/03/2018
 */
class AVLTreeNode<dataType, keyType extends Comparable<keyType>> {

    /** Left child node */
    private AVLTreeNode<dataType, keyType> leftChild;

    /** Right child node */
    private AVLTreeNode<dataType, keyType> rightChild;

    /** Parent node */
    private AVLTreeNode<dataType, keyType> parent;

    /** Node key */
    private keyType key;

    /** Node data */
    private dataType data;

    /**
     * Creates a new AVLTreeNode object with no parent<br>
     * 
     * @param nodeKey  The node's key
     * @param nodeData  The node's data
     */
    public AVLTreeNode(keyType nodeKey, dataType nodeData){
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
        this.key = nodeKey;
        this.data = nodeData;
    }

    /**
     * Creates a new AVLTreeNode object with a parent<br>
     * 
     * @param nodeKey  The node's key
     * @param nodeData  The node's data
     * @param nodeParent  The node's parent
     */
    public AVLTreeNode(keyType nodeKey, dataType nodeData, AVLTreeNode<dataType, keyType> nodeParent){
        this.leftChild = null;
        this.rightChild = null;
        this.parent = nodeParent;
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
    public AVLTreeNode<dataType, keyType> getLeftChild(){
        return this.leftChild;
    }

    /**
     * Gets the right child node of the node
     * 
     * @return Node's right child node
     */
    public AVLTreeNode<dataType, keyType> getRightChild(){
        return this.rightChild;
    }

    /**
     * Gets the parent node of the node
     * 
     * @return Node's parent node
     */
    public AVLTreeNode<dataType, keyType> getParent(){
        return this.parent;
    }

    /**
     * Sets the left child node of the node
     * 
     * @param node  The new left child node
     */
    public void setLeftChild(AVLTreeNode<dataType, keyType> node){
        this.leftChild = node;
    }

    /**
     * Sets the right child node of the node
     * 
     * @param node  The new right child node
     */
    public void setRightChild(AVLTreeNode<dataType, keyType> node){
        this.rightChild = node;
    }

    /**
     * Sets the parent node of the node
     * 
     * @param node  The new parent node
     */
    public void setParent(AVLTreeNode<dataType, keyType> node){
        this.parent = node;
    }

}