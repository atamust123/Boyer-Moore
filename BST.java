
public class BST {

    class Node {

        private double number;
        private char[] countryName;
        private Node left, right;

        public Node(double number, char[] countryName) {
            this.number = number;
            left = right = null;
            this.countryName = new char[countryName.length];
            for (int i = 0; i < countryName.length; i++) {
                this.countryName[i] = countryName[i];
            }
        }

        public double getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public char[] getCountryName() {
            return countryName;
        }

        public void setCountryName(char[] countryName) {
            this.countryName = countryName;
        }

    }

    private Node root;

    public BST() {
        root = null;
    }

    public void insert(double number, char[] countryName) {
        root = insert(root, number, countryName);
    }

    private Node insert(Node root, double number, char[] countryName) {
        if (root == null) {
            root = new Node(number, countryName);
            return root;
        } else if (number < root.number) {
            root.left = insert(root.left, number, countryName);
        } else if (number > root.number) {
            root.right = insert(root.right, number, countryName);
        } else {
            for (int i = 0; i < countryName.length; i++) {
                if (countryName[i] < root.countryName[i]) {
                    root.right = insert(root.right, number, countryName);
                    break;
                } else if (countryName[i] > root.countryName[i]) {
                    root.left = insert(root.left, number, countryName);
                    break;
                }
            }
        }
        return root;
    }

    public char[][] inOrder(int size) {

        char[][] array = new char[size][];
        if (root != null) {
            inOrder(root, array);
        }
        return array;
    }

    private void inOrder(Node root, char[][] array) {
        if (root != null) {
            inOrder(root.left, array);

            char[] array2 = new char[root.countryName.length];
            int i = 0;
            for (char c : root.countryName) {
                array2[i++] = c;
            }
            int counter = 0;
            while (array[counter] != null) {
                counter++;
            }
            array[counter] = array2;
            inOrder(root.right, array);
        }
    }

}
