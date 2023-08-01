public class WeightedQuickUnionUFTest {
    public static void main(String[] args) {
        testConnectivity();
        testFindLargest();
    }

    public static void testConnectivity() {
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(10);

        // Initially, all elements are disconnected 
        assert !uf.isConnected(0, 1);
        assert !uf.isConnected(2, 3);
        assert !uf.isConnected(4, 5);
        assert !uf.isConnected(6, 7);
        assert !uf.isConnected(8, 9);

        // Create some connections
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(8, 9);

        // Test connections
        assert uf.isConnected(0, 1);
        assert uf.isConnected(2, 3);
        assert uf.isConnected(4, 5);
        assert uf.isConnected(6, 7);
        assert uf.isConnected(8, 9);

        // Test non-connections
        assert !uf.isConnected(1, 2);
        assert !uf.isConnected(3, 4);
        assert !uf.isConnected(5, 6);
        assert !uf.isConnected(7, 8);
        assert !uf.isConnected(0, 9);

        System.out.println("Connectivity test passed.");
    }

    public static void testFindLargest() {
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(10);

        uf.union(1, 2);
        uf.union(2, 3);
        uf.union(4, 5);
        uf.union(5, 6);

        // Largest elements in each connected set
        assert uf.find_largest(1) == 3;
        assert uf.find_largest(2) == 3;
        assert uf.find_largest(3) == 3;
        assert uf.find_largest(4) == 6;
        assert uf.find_largest(5) == 6;
        assert uf.find_largest(6) == 6;

        // Elements that are not connected to any other element should return themselves as the largest
        for (int i = 0; i < 10; i++) {
            if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 6) {
                assert uf.find_largest(i) == i;
            }
        }

        System.out.println("Find largest test passed.");
    }
}
