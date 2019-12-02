import java.util.Scanner;
public class Kth {
        public static void main (String[] args) {
        	Scanner in=new Scanner (System.in);
		long k=in.nextLong(), i = 1, a = 9;
		for(; i * a <= k; k -= i * a, a *= 10, i++);
		System.out.println(((a / 9 + k / i) / ((long)Math.pow(10, i - k % i - 1))) % 10);
	}
}
