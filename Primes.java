/**
 * Primes class to store and calculate prime numbers
 * It uses AvlTree to store prime numbers
 * It calculates prime numbers up to 2*10^6
 * It's used for hashing in HashMap as table size
 */
public class Primes {
    private static AvlTree<Integer> primes;
    static { // not to compute primes every time
        primes = new AvlTree<>();
        calcPrimes();
    }
    private static void calcPrimes() {
        int[] nums = new int[2000005];
        for (int i = 2; i < 2000005; i++) {
            if (nums[i] == 0) {
                primes.insert(i);
                for (int j=i; j < 2000005; j+=i) {
                    nums[j] = i;
                }
            }
        }
    }
    public static int nextPrime(int current){
        int want = 2*current;
        return primes.findOrSmallestLarger(want);
    }
}
