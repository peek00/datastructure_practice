import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void  main(String[] args) {
        String word = null;
        int i = 1;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                word = s;
            }
            i++;
        }
        StdOut.println(word);
    }
    
}
