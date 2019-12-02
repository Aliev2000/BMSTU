import java.util.*;
public class MinDist {
        public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		char x = in.next().charAt(0), y = in.next().charAt(0);
		int indX = s.indexOf(x), indY = s.indexOf(y), dist = Math.abs(indX - indY);
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == x) indX = i;
			else if(s.charAt(i) == y) indY = i;
			dist = Math.abs(indX - indY) < dist ? Math.abs(indX - indY) : dist;
		}
		System.out.print(dist - 1);
	}
}
