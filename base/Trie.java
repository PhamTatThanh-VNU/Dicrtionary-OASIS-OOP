class TreeNode {
    Word word;
    TreeNode left;
    TreeNode right;

    public TreeNode(Word word) {
        this.word = word;
        this.left = null;
        this.right = null;
    }
}

public class Trie {
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

    //Process Binary Tree
    private TreeNode insertWord(TreeNode root, Word word) {
        if (root == null) {
            root = new TreeNode(word);
            return root;
        }
        int compare = word.getWord_target().compareTo(root.word.getWord_target());
        if (compare < 0) {
            root.left = insertWord(root.left, word);
        } else if (compare > 0) {
            root.right = insertWord(root.right, word);
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
    private TreeNode removeWord(TreeNode root, String word_target) {
        if (root == null) {
            return root;
        }
        int compare = word_target.compareTo(root.word.getWord_target());
        if (compare < 0) {
            root.left = removeWord(root.left, word_target);
        } else if (compare > 0) {
            root.right = removeWord(root.right, word_target);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            // Node with two children, get the inorder successor (smallest in the right subtree)
            root.word = minValue(root.right);

            // Delete the inorder successor
            root.right = removeWord(root.right, root.word.getWord_target());
        }
        return root;
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
