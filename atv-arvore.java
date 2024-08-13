import java.util.Random;

class TreeNode {
    int value;
    TreeNode left, right;

    TreeNode(int item) {
        value = item;
        left = right = null;
    }
}

class BinaryTree {
    TreeNode root;

    void insert(int value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            root = new TreeNode(value);
            return root;
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    static BinaryTree createTreeWithRandomNumbers(int count) {
        BinaryTree tree = new BinaryTree();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            tree.insert(rand.nextInt(101)); 
        }
        return tree;
    }

    void addRandomNumbers(int count) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            insert(rand.nextInt(101)); 
        }
    }


    void balanceTree() {
        root = convertToRightist(root);

        root = balance(root);
    }

    private TreeNode convertToRightist(TreeNode node) {
        TreeNode current = node;
        while (current != null) {
            if (current.left != null) {
                TreeNode left = current.left;
                current.left = left.right;
                left.right = current;
                current = left;
            } else {
                current = current.right;
            }
        }
        return current;
    }

    private TreeNode balance(TreeNode node) {
        int size = countNodes(node);
        int height = (int) (Math.log(size + 1) / Math.log(2)) - 1;

        TreeNode newRoot = buildBalancedTree(node, height);
        return newRoot;
    }

    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    private TreeNode buildBalancedTree(TreeNode node, int height) {
        if (height < 0) return null;

        TreeNode left = buildBalancedTree(node, height - 1);
        TreeNode root = node;
        root.left = left;

        TreeNode right = buildBalancedTree(root.right, height - 1);
        root.right = right;

        return root;
    }

    void printTree(TreeNode node, String prefix, boolean isLeft) {
        if (node != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + node.value);
            printTree(node.left, prefix + (isLeft ? "│   " : "    "), true);
            printTree(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();

        bt = BinaryTree.createTreeWithRandomNumbers(100);

        bt.addRandomNumbers(20);

        bt.balanceTree();

        System.out.println("Árvore balanceada:");
        bt.printTree(bt.root, "", true);
    }
}