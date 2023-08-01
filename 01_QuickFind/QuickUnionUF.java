public class QuickUnionUF {
    private int[] id;

    public QuickUnionUF (int n) {
        id = new int[n];
        for (int i = 0; i < n; i++){
            id[i] = i; // Setting the root of each node to itself
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            i = id[i]; // Chase parent pointers until reach root
        }
        return i;
    }

    public boolean isConnected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q){
        int i = root(p);
        int j = root(q);
        id[i] = j; // Change root of p to point to root of q
    }
}
