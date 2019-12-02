import java.util.Scanner;
public class Econom {
        public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		int counter = 0;
		for(int i = 0; i < s.length(); i++)
			if(s.charAt(i) == ')') {
				s = s.replace(s.substring(i - 4, i + 1), String.valueOf(counter));
				counter++;
				i -= 5;
			}
		System.out.println(counter);
	}
}
