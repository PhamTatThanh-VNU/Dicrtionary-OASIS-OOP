class TreeNode {
    Word word;
    TreeNode left;
    TreeNode right;
    int height;

    public TreeNode(Word word) {
        this.word = word;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}

public class TreeBinary {
    TreeNode root;

    public void insert(Word word) {
        root = insertWord(root, word);
    }

    public String search(Word word) {
        return searchWord(root, word);
    }

    public void remove(String wordTarget) {
        root = removeWord(root, wordTarget);
    }

    // Process AVL Tree
    private TreeNode insertWord(TreeNode root, Word word) {
        if (root == null) {
            return new TreeNode(word);
        }

        int compare = word.getWord_target().compareTo(root.word.getWord_target());
        if (compare < 0) {
            root.left = insertWord(root.left, word);
        } else if (compare > 0) {
            root.right = insertWord(root.right, word);
        } else {
            // Duplicate word, do not insert
            return root;
        }

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));

        int balance = getBalance(root);

        // Left Left Case
        if (balance > 1 && word.getWord_target().compareTo(root.left.word.getWord_target()) < 0) {
            return rotateRight(root);
        }

        // Right Right Case
        if (balance < -1 && word.getWord_target().compareTo(root.right.word.getWord_target()) > 0) {
            return rotateLeft(root);
        }

        // Left Right Case
        if (balance > 1 && word.getWord_target().compareTo(root.left.word.getWord_target()) > 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        // Right Left Case
        if (balance < -1 && word.getWord_target().compareTo(root.right.word.getWord_target()) < 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    private String searchWord(TreeNode root, Word word) {
        if (root == null || root.word.getWord_target().equals(word.getWord_target())) {
            return (root != null) ? "Meaning: " + root.word.getWord_explain() : "Cannot find word in Dictionary.";
        }

        int compareResult = word.getWord_target().compareTo(root.word.getWord_target());

        if (compareResult < 0) {
            return searchWord(root.left, word);
        } else {
            return searchWord(root.right, word);
        }
    }

    private TreeNode removeWord(TreeNode root, String wordTarget) {
        if (root == null) {
            return root;
        }

        int compare = wordTarget.compareTo(root.word.getWord_target());
        if (compare < 0) {
            root.left = removeWord(root.left, wordTarget);
        } else if (compare > 0) {
            root.right = removeWord(root.right, wordTarget);
        } else {
            if (root.left == null || root.right == null) {
                TreeNode temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                TreeNode temp = new TreeNode(root.word);
                temp.word = minValue(root.right);
                root.word = temp.word;
                root.right = removeWord(root.right, temp.word.getWord_target());
            }
        }

        if (root == null) {
            return root;
        }

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));

        int balance = getBalance(root);

        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rotateRight(root);
        }

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0) {
            return rotateLeft(root);
        }

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    private TreeNode rotateRight(TreeNode B) {
        TreeNode A = B.left;
        TreeNode D = A.right;

        B.left = D;
        A.right = B;

        B.height = 1 + Math.max(getHeight(B.left), getHeight(B.right));
        A.height = 1 + Math.max(getHeight(A.left), getHeight(A.right));

        return A;
    }

    private TreeNode rotateLeft(TreeNode A) {
        TreeNode B = A.right;
        TreeNode C = B.left;

        B.left = A;
        A.right = C;

        A.height = 1 + Math.max(getHeight(A.left), getHeight(A.right));
        B.height = 1 + Math.max(getHeight(B.left), getHeight(B.right));

        return B;
    }

    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalance(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private Word minValue(TreeNode root) {
        Word minValue = root.word;
        while (root.left != null) {
            minValue = root.left.word;
            root = root.left;
        }
        return minValue;
    }
}
