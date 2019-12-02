import java.util.*;

public class Cpm {
        private static class Job {
		String name, color;
		int key, value, mark;
		Set<Job> parent, to, from;
		public Job(String name, int time) {
			this.name = name;
			this.color = "black";
			this.key = this.value = time;
			this.mark = -1;
			this.parent = new HashSet<>();
			this.to = new HashSet<>();
			this.from = new HashSet<>();
		}
		public void changeValue() {
			if(this.key == this.value && this.from.size() > 0) {
				this.from.forEach(Job :: changeValue);
				Job u = Collections.max(this.from, (x, y) -> x.value - y.value);
				this.from.forEach(v -> {
					if(v.value == u.value)	this.parent.add(v);
				});
				this.value += u.value;
			}
		}
		public void makeBlue() {
			this.color = "blue";
			this.to.forEach(v -> {
				if(!v.color.equals("blue"))	v.makeBlue();
			});
		}
		public void makeRed() {
			this.color = "red";
			this.parent.forEach(Job::makeRed);
		}
	}
	private static void dfs(Job v, List<Job> willBeBlue) {
		v.mark = 0;
		for(Job u : v.to) {
			if(u.mark == -1)	dfs(u, willBeBlue);
			else if(u.mark == 0)	willBeBlue.add(u);
		}
		v.mark = 1;
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Map<String, Job> graph = new Hashtable<>();
		String prev = null;
		while(in.hasNext()) {
			String s = in.next();
			boolean last = false;
			if(!s.equals("<")) {
				if(s.charAt(s.length() - 1) == ';') {
					s = s.substring(0, s.length() - 1);
					last = true;
				}
				if(s.lastIndexOf('(') != -1) {
					int v = Integer.parseInt(s.substring(s.lastIndexOf('(') + 1, s.length() - 1));
					s = s.substring(0, s.lastIndexOf('('));
					graph.put(s, new Job(s, v));
				}
				if(prev != null) {
					graph.get(prev).to.add(graph.get(s));
					graph.get(s).from.add(graph.get(prev));
				}
				if(last) prev = null;
				else prev = s;
			}
		}
		List<Job> willBeBlue = new ArrayList<>();
		graph.values().forEach(v -> {
			if(v.mark == -1)	dfs(v, willBeBlue);
		});
		willBeBlue.forEach(Job :: makeBlue);
		graph.values().forEach(v -> {
			if(!v.color.equals("blue"))	v.changeValue();
		});
		Optional<Job> opt = graph.values().stream().filter(v -> !v.color.equals("blue")).max((a, b) -> a.value - b.value);
		Job x = opt.orElse(null);
		graph.values().forEach(v -> {
			if(!v.color.equals("blue") && v.value == x.value)	v.makeRed();
		});
		StringBuilder gr = new StringBuilder();
		gr.append("digraph {" + "\n");
		for(Job v : graph.values()) {
			gr.append("\t" + v.name + "[label = \"" + v.name + "(" + v.key + ")\"");
			if(!v.color.equals("black"))	gr.append(", color = " + v.color);
			gr.append("]" + "\n");
		}
		for(Job v : graph.values())
			for(Job u : v.to) {
				gr.append("\t" + v.name + " -> " + u.name);
				if((u.value == v.value + u.key && v.color.equals("red") && u.color.equals("red")) || v.color.equals("blue"))
					gr.append(" [color = " + v.color + "]");
				gr.append("\n");
			}
		gr.append("}" + "\n");
		System.out.print(gr);
	}
}
