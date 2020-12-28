public class BinarySearchTree<T extends Comparable<T>> {

    private class NewNode {

        T value;
        NewNode left;
        NewNode right;

        public NewNode(T value, NewNode left, NewNode right){
            this.value = value;
            this.left = left;
            this.right = right;
        }

    }

    private NewNode root = null;
    private int nodeCount = 0;

    public boolean isEmpty(){ return nodeCount == 0; }

    public int size(){ return nodeCount; }

    public boolean add(T elem){
        if(contains(elem))
            return false;

        root = add(root, elem);
        nodeCount++;
        return true;
    }

    private NewNode add(NewNode node, T elem){

        if(node == null) {
            node = new NewNode(elem, null, null);

        } else {

            if(elem.compareTo(node.value) < 0){
                node.left = add(node.left, elem);
            } else {
                node.right = add(node.right, elem);
            }
        }

        return node;
    }

    public boolean remove(T elem){

        if(!contains(elem)) return false;

        root = remove(root, elem);
        nodeCount--;
        return true;
    }

    private NewNode remove(NewNode node, T elem){

        if(node == null) return null;

        int cmp = elem.compareTo(node.value);

        // if elem is greater than the nodes value, dig right
        if(cmp > 0){
            remove(node.right, elem);
        } else if(cmp < 0){
            remove(node.left, elem);
        } else {

            // This is the case with only a right subtree or
            // no subtree at all. In this situation just
            // swap the node we wish to remove with its right child.
            if (node.left == null) {

                NewNode rightChild = node.right;

                node.value = null;
                node = null;
                return rightChild;

            } else if (node.right == null) {

                NewNode leftChild = node.left;

                node.value = null;
                node = null;
                return leftChild;

                // When removing a node from a binary tree with two links the
                // successor of the node being removed can either be the largest
                // value in the left subtree or the smallest value in the right
                // subtree. In this implementation I have decided to find the
                // smallest value in the right subtree which can be found by
                // traversing as far left as possible in the right subtree.
            } else {

                // Find the leftmost node in the right subtree
                NewNode tmp = findMin(node.right);

                // Swap the data
                node.value = tmp.value;

                // Go into the right subtree and remove the leftmost node we
                // found and swapped data with. This prevents us from having
                // two nodes in our tree with the same value.
                node.right = remove(node.right, tmp.value);
            }
        }
        return node;
    }

    private NewNode findMin(NewNode node){
        while(node.left != null) node = node.left;
        return node;
    }

    public boolean contains(T elem) {
        return contains(root, elem);
    }

    private boolean contains(NewNode node, T elem){

        if(node == null) return false;

        int cmp = elem.compareTo(node.value);

        if(cmp > 0){
            return contains(node.right, elem);
        } else if (cmp < 0){
            return contains(node.left, elem);
        } else {
            return true;
        }
    }

    public int height(){
        return height(root);
    }

    private int height(NewNode node){

        if(node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    public java.util.Iterator<T> traverse(String order) {
        switch (order) {
            case "PRE_ORDER":
                return preOrderTraversal();
            case "IN_ORDER":
                return inOrderTraversal();
            case "POST_ORDER":
                return postOrderTraversal();
            case "LEVEL_ORDER":
                return levelOrderTraversal();
            default:
                return null;
        }
    }

    // Returns as iterator to traverse the tree in pre order
    private java.util.Iterator<T> preOrderTraversal() {

        final int expectedNodeCount = nodeCount;
        final java.util.Stack<NewNode> stack = new java.util.Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                NewNode node = stack.pop();
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
                return node.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in order
    private java.util.Iterator<T> inOrderTraversal() {

        final int expectedNodeCount = nodeCount;
        final java.util.Stack<NewNode> stack = new java.util.Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {
            NewNode trav = root;

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {

                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

                // Dig left
                while (trav != null && trav.left != null) {
                    stack.push(trav.left);
                    trav = trav.left;
                }

                NewNode node = stack.pop();

                // Try moving down right once
                if (node.right != null) {
                    stack.push(node.right);
                    trav = node.right;
                }

                return node.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in post order
    private java.util.Iterator<T> postOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final java.util.Stack<NewNode> stack1 = new java.util.Stack<>();
        final java.util.Stack<NewNode> stack2 = new java.util.Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            NewNode node = stack1.pop();
            if (node != null) {
                stack2.push(node);
                if (node.left != null) stack1.push(node.left);
                if (node.right != null) stack1.push(node.right);
            }
        }
        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack2.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return stack2.pop().value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in level order
    private java.util.Iterator<T> levelOrderTraversal() {

        final int expectedNodeCount = nodeCount;
        final java.util.Queue<NewNode> queue = new java.util.LinkedList<>();
        queue.offer(root);

        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !queue.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                NewNode node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                return node.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree();

        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.remove(3);
        System.out.println(tree.size());
        System.out.println(tree.height());
    }
}

