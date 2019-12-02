import java.util.*;

public class Gauss {
        private static int gcd(int a, int b) {
		int x = Math.abs(a);
		int y = Math.abs(b);
		if(x == 0 || y == 0) return x + y;
		if(x > y) return gcd(x % y, y);
		return gcd(y % x, x);
	}

	private static void getRidOfZero(int n, int i, int[][] numerator, int[][] denominator) {
		for(int  row = i + 1; row < n; row++)
			if(numerator[row][i] != 0) {
				for(int column = 0; column < n + 1; column++) {
					int temp = numerator[i][column];
					numerator[i][column] = numerator[row][column];
					numerator[row][column] = temp;
					temp = denominator[i][column];
					denominator[i][column] = denominator[row][column];
					denominator[row][column] = temp;
				}
				break;
			}
	}

	private static void operation1(int n, int num, int den, int i, int[][] numerator, int[][] denominator) {
		for(int column = 0; column < n + 1; column++) {
			numerator[i][column] *= den;
			denominator[i][column] *= num;
			int k = gcd(numerator[i][column], denominator[i][column]);
			numerator[i][column] /= k;
			denominator[i][column] /= k;
		}
	}

	private static void operation2(int n, int i, int[][] numerator, int[][] denominator) {
		for(int row = 0; row < n; row++) {
			if(row == i) continue;
			int num = numerator[row][i];
			int den = denominator[row][i];
			for(int column = 0; column < n + 1; column++) {
				numerator[row][column] = numerator[row][column] * denominator[i][column] * den - denominator[row][column] * numerator[i][column] * num;
				denominator[row][column] *= denominator[i][column] * den;
				int k = gcd(numerator[row][column], denominator[row][column]);
				numerator[row][column] /= k;
				denominator[row][column] /= k;
			}
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[][] numerator = new int[n][n + 1];
		int[][] denominator = new int[n][n + 1];
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n + 1; j++) {
				numerator[i][j] = in.nextInt();
				denominator[i][j] = 1;
			}
		for(int i = 0; i < n; i++) {
			if(numerator[i][i] == 0) {
				getRidOfZero(n, i, numerator, denominator);
				if(numerator[i][i] == 0) {
					System.out.println("No solution");
					return;
				}
			}
			int num = numerator[i][i];
			int den = denominator[i][i];
			operation1(n, num, den, i, numerator, denominator);
			operation2(n, i, numerator, denominator);
		}
		for(int i = 0; i < n; i++) {
			if (denominator[i][n] < 0) System.out.printf("%d/%d\n", -numerator[i][n], -denominator[i][n]);
			else System.out.printf("%d/%d\n", numerator[i][n], denominator[i][n]);
		}
	}
}
