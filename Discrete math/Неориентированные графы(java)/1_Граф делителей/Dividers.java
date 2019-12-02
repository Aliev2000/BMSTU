import java.util.Scanner;
import java.util.ArrayList;
 
public class Dividers {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long x = in.nextLong();
        ArrayList<Long> divisors = new ArrayList<>();

        if(x == 1)
	    divisors.add((long)1);
	else {
	    int location = 0;
	    for(long i = 1; i <= Math.sqrt(x); i++)
	        if(x % i == 0) {
		    if(x / i != i) {
			divisors.add(location, i);
			divisors.add(location + 1, x / i);
		    } else
			divisors.add(location, i);
		    location++;
		}
	}
 
        System.out.println("graph {");
        for (long elem : divisors)
            System.out.println(elem);

        int numberOfDivisors = divisors.size(); 
        for(int u = 0; u < numberOfDivisors; u++)
            for(int v = u + 1; v < numberOfDivisors; v++)
                if(divisors.get(v) % divisors.get(u) == 0) {
                    boolean flag = true;
		    for(int w = v - 1; w > u && flag; w--)
                        if (divisors.get(v) % divisors.get(w) == 0 && divisors.get(w) % divisors.get(u) == 0)
                            flag = false;
                    if(flag)
                        System.out.println(divisors.get(v) + " -- " + divisors.get(u));
                }
        System.out.println("}");
    }
}
