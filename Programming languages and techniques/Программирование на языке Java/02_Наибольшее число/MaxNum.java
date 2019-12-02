import java.util.*;

public class MaxNum {
        public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		ArrayList<Integer> ints = new ArrayList<>(N);
		for(int i = 0; i < N; i++)
			ints.add(in.nextInt());
		Collections.sort(ints, new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				return -((String.valueOf(a) + String.valueOf(b)).compareTo(String.valueOf(b) + String.valueOf(a)));
			}
		});
		StringBuilder number = new StringBuilder("");
		for(int i = 0; i < N; i++)
			number.append(ints.get(i));
		System.out.println(number);
	}
}

