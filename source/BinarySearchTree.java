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

}

/**
 * <h2>BinarySearchTreeNode</h2><br>
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