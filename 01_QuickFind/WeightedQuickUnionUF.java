public class WeightedQuickUnionUF {
    private int[] id;
    private int[] height ;
    private int[] max;
    

    public WeightedQuickUnionUF (int n) {
        id = new int[n];
        height = new int[n];
        max = new int[n];
        for (int i = 0; i < n; i++){
            id[i] = i; // Setting the root of each node to itself
            height[i] = 1;
            max[i] = i; // Setting max to itself
        }
    }

    private int root(int i) {
        int currMax = i;
        while (i != id[i]) {
            id[i] = id[id[i]]; // Improvement 2. Path compression
            i = id[i]; // Chase parent pointers until reach root
            if (i > currMax) {
                currMax = i; // set curr Max
            }

        }
        // Set max 
        max[i] = currMax;
        return i;
    }

    public boolean isConnected(int p, int q) {
        // Unchanged
        return root(p) == root(q);
    }

    public void union(int p, int q){
        // Improvement 1. Weighting height
        int i = root(p);
        int j = root(q);
        // Check the size of the tree
        if (height[i] < height[j]) {
            id[i] = j;
            height[j] += height[i];
        } else {
            id[j] = i;
            height[i] += height[j];
        }
    }

    public int find_largest(int p){
        // Traverse the tree to the largest number in this connected set
        int rootIdx = root(p);
        return max[rootIdx];
    }
}
