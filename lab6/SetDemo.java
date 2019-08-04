import java.util.HashSet;
import java.util.Iterator;
class SetDemo {
	public static void main(String[] ignore) {
		HashSet<String> ourSet = new HashSet<String>();
		ourSet.add("papa");
		ourSet.add("bear");
		ourSet.add("mama");
		ourSet.add("bear");
		ourSet.add("baby");
		ourSet.add("bear");
		for (String x : ourSet) {
			System.out.println(x);
		}
	}
}