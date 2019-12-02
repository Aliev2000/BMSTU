import java.util.*;

public class FormulaOrder {
        private static enum tag {PLUS, MINUS, MULT, DIV, LPAR, RPAR, VAR, NUMS, COMMA, EQUAL}
	
	private static class Lexer {
		String line, variable;
		ArrayList<tag> tags;
		int variables, commas, iter = 0;
		boolean hasEqual;

		Lexer(String s) {
			this.line = s;
			this.tags = new ArrayList<tag>();
		}

		void findLexems(int n, Map<String, Integer> vars) throws Exception {
			String s = line;
			for(int i = 0, m = line.length(); i < m; i++) {
				switch(s.charAt(i)) {
					case ' ':
						continue;
					case '+':
						if(!hasEqual)
							throw new Exception();
						tags.add(tag.PLUS);
						continue;
					case '-':
						tags.add(tag.MINUS);
						continue;
					case '*':
						tags.add(tag.MULT);
						continue;
					case '/':
						tags.add(tag.DIV);
						continue;
					case '(':
						tags.add(tag.LPAR);
						continue;
					case ')':
						tags.add(tag.RPAR);
						continue;
					case '=':
						if(hasEqual)
							throw new Exception();
						hasEqual = true;
						tags.add(tag.EQUAL);
						continue;
					case ',':
						tags.add(tag.COMMA);
						continue;
					default:
						if(Character.isLetter(s.charAt(i))) {
							tags.add(tag.VAR);
							variables++;
							StringBuilder st = new StringBuilder();
							st.append(s.charAt(i));
							while(++i < m && (Character.isLetterOrDigit(s.charAt(i))))
								st.append(s.charAt(i));
							i--;
							variable = st.toString();
							if(!hasEqual) {
								if(vars.containsKey(variable))
									throw new Exception();
								vars.put(variable, n);
							}
							continue;
						}
						if(Character.isDigit((s.charAt(i)))) {
							tags.add(tag.NUMS);
							while(i < s.length() && Character.isDigit(s.charAt(i)))
								i++;
							i--;
							continue;
						}
				}
				throw new Exception();
			}
		}
		void someFunc(int n, Map<String, Integer> vars, ArrayList<Vertex> graph) throws Exception {
			String s = line;
			int i = 0;
			for(int m = line.length(); i < m && s.charAt(i) != '='; i++);
			for(int m = line.length(); i < m; i++)
				if(Character.isLetter(s.charAt(i))) {
					StringBuilder st = new StringBuilder();
					st.append(s.charAt(i));
					while(++i < s.length() && (Character.isAlphabetic(s.charAt(i)) || Character.isDigit(s.charAt(i))))
						st.append(s.charAt(i));
					String variable = st.toString();
					i--;
					if(!vars.containsKey(variable))
						throw new Exception();
					int t = vars.get(variable);
					graph.get(n).edge.add(graph.get(t));
					continue;
				}
		}

		void parse_left() throws Exception {
			if(tags.get(iter++) == tag.VAR) {
				if(tags.get(iter) == tag.COMMA) {
					iter++;
					commas++;
					parse_left();
				}
				if(iter < tags.size() && tags.get(iter) == tag.EQUAL) {
					iter++;
					if(commas == 0)
						parse_right();
					else {
						parse_right_comma();
						if(commas != 0)
							throw new Exception();
					}
				}
			} else throw new Exception();
		}

		void parse_right() throws Exception {
			parse_f();
			parse_t();
			parse_e();
		}

		void parse_t() throws Exception{
			if (iter < tags.size() && (tags.get(iter) == tag.MULT || tags.get(iter) == tag.DIV)) {
				iter++;
				parse_f();
				parse_t();
			}
		}

		void parse_f() throws Exception {
			if(tags.get(iter) == tag.MINUS || tags.get(iter) == tag.LPAR || tags.get(iter) == tag.NUMS || tags.get(iter) == tag.VAR) {
				if(tags.get(++iter - 1) == tag.MINUS)	parse_f();
				if(tags.get(iter - 1) == tag.LPAR) {
					parse_right();
					if(tags.get(iter) == tag.RPAR) iter++;
					else throw new Exception();
				}
			} else throw new Exception();
		}

		void parse_e() throws Exception {
			if(iter < tags.size() && (tags.get(iter) == tag.PLUS || tags.get(iter) == tag.MINUS)) {
				iter++;
				parse_f();
				parse_t();
				parse_e();
			}
			if(iter == tags.size() - 1 && (tags.get(iter) == tag.COMMA || tags.get(iter) == tag.NUMS))
				throw new Exception();
		}

		void parse_right_comma() throws Exception {
			if(tags.get(iter) == tag.NUMS || tags.get(iter) == tag.VAR)
				if(++iter < tags.size() && tags.get(iter) == tag.COMMA) {
					iter++;
					commas--;
					parse_right_comma();
				}
		}
	}

	private static class Vertex {
		int index;
		String color;
		ArrayList<Vertex> edge;
		Vertex(int index) {
			this.index = index;
			this.color = "white";
			edge = new ArrayList<>();
		}
	}

	private static ArrayList<Vertex> dfs(ArrayList<Vertex> graph) throws Exception {
		ArrayList<Vertex> ans = new ArrayList<>();
		for(Vertex v : graph)
			if(v.color.equals("white"))
				dfs2(v, ans);
		return ans;
	}

	private static void dfs2(Vertex v, ArrayList<Vertex> ans) throws Exception {
		v.color = "gray";
		for(Vertex u : v.edge)
			if(u.color.equals("white"))
				dfs2(u, ans);
			else if(u.color.equals("gray"))
				throw new Exception();
		v.color = "black";
		ans.add(v);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Map<String, Integer> vars = new Hashtable<>();
		ArrayList<Vertex> graph = new ArrayList<>();
		ArrayList<Lexer> lex = new ArrayList<Lexer>();
		try {
			int i = 0;
			while(in.hasNextLine()) {
				lex.add(new Lexer(in.nextLine()));
				graph.add(new Vertex(i));
				lex.get(i).findLexems(i, vars);
				lex.get(i++).parse_left();
			}
			i = -1;
			for(Lexer l : lex)
				l.someFunc(++i, vars, graph);
		}
		catch(Exception e){
			System.out.println("syntax error");
			return;
		}
		ArrayList<Vertex> answer;
		try {
			answer = dfs(graph);
		}
		catch(Exception e) {
			System.out.println("cycle");
			return;
		}
		for(Vertex v : answer)
			System.out.println(lex.get(v.index).line);
	}
}
