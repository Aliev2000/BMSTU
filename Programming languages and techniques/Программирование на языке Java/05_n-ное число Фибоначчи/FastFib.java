import java.util.*;
import java.math.BigInteger;

public class FastFib {
        public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		BigInteger[][] matrix = {
			{BigInteger.ONE,BigInteger.ONE},
			{BigInteger.ONE,BigInteger.ZERO},
		};
		BigInteger fn = BigInteger.ZERO, fn1 = BigInteger.ONE, a, b, c, d;
		while (n > 0) {
			if(n % 2 == 1) {
				c = fn;
				fn = fn.multiply(matrix[0][0]).add(fn1.multiply(matrix[1][0]));
				fn1 = c.multiply(matrix[0][1]).add(fn1.multiply(matrix[1][1]));
			}
			a = matrix[0][0];
			b = matrix[0][1];
			c = matrix[1][0];
			d = matrix[1][1];
			matrix[0][0] = a.multiply(a).add(b.multiply(c));
			matrix[0][1] = a.multiply(b).add(b.multiply(d));
			matrix[1][0] = c.multiply(a).add(d.multiply(c));
			matrix[1][1] = c.multiply(b).add(d.multiply(d));
			n /= 2;
		}
		System.out.print(fn);
	}
}
