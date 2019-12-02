import java.util.*;

public class Calc {
    private static class Lexer {
        private String s;
        private int i;
        public Lexer(String s) {
            this.s = s;
            this.i = 0;
            while(i < s.length() && Character.isWhitespace(s.charAt(i)))
                i++;
        }
        public int scanInt() {
            int x = Character.digit(s.charAt(i++), 10);
            while(i < s.length() && Character.isDigit(s.charAt(i)))
                x = x * 10 + Character.digit(s.charAt(i++), 10);
            while(i < s.length() && Character.isWhitespace(s.charAt(i)))
                i++;
            return x;
        }
        public String scanVariable() {
            StringBuilder str = new StringBuilder();
            str.append(s.charAt(i++));
            while(i < s.length() && Character.isLetterOrDigit(s.charAt(i)))
                str.append(s.charAt(i++));
            while(i < s.length() && Character.isWhitespace(s.charAt(i)))
                i++;
            return str.toString();
        }
        public void scanElse() {
            while(++i < s.length() && Character.isWhitespace(s.charAt(i)));
        }
    }

    private static class Parser {
        private Lexer lexer;
        private Map<String, Integer> variables;
        private static Scanner in = new Scanner(System.in);
        private Parser(String s) {
            this.lexer = new Lexer(s);
            this.variables = new HashMap<>();
        }
        private int parseE() throws Exception {
            int x = parseT();
            while(lexer.i < lexer.s.length() && (lexer.s.charAt(lexer.i) == '+' || lexer.s.charAt(lexer.i) == '-')){
                if(lexer.s.charAt(lexer.i) == '+') {
                    lexer.scanElse();
                    x += parseT();
                } else {
                    lexer.scanElse();
                    x -= parseT();
                }
            }
            return x;
        }

        private int parseT() throws Exception {
            int x = parseF();
            while(lexer.i < lexer.s.length() && (lexer.s.charAt(lexer.i) == '*' || lexer.s.charAt(lexer.i) == '/')){
                if(lexer.s.charAt(lexer.i) == '*') {
                    lexer.scanElse();
                    x *= parseF();
                } else {
                    lexer.scanElse();
                    x /= parseF();
                }
            }
            return x;
        }

        private int parseF() throws Exception {
            if(lexer.i < lexer.s.length()) {
                if(Character.isDigit(lexer.s.charAt(lexer.i)))
                    return lexer.scanInt();
                else if(Character.isLetter(lexer.s.charAt(lexer.i))) {
                    String h = lexer.scanVariable();
                    if(variables.containsKey(h))
                        return variables.get(h);
                    else {
                        variables.put(h, in.nextInt());
                        return variables.get(h);
                    }
                } else if(lexer.s.charAt(lexer.i) == '-') {
                    lexer.scanElse();
                    return -parseF();
                } else if(lexer.s.charAt(lexer.i) == '(') {
                    lexer.scanElse();
                    int k = parseE();
                    if(lexer.s.length() <= lexer.i || lexer.s.charAt(lexer.i) != ')')
                        throw new Exception();
                    lexer.scanElse();
                    return k;
                } else
                    throw new Exception();
            } else
                throw new Exception();
        }
        public static int parse() throws Exception {
            String s = in.nextLine();
            Parser parser = new Parser(s);
            int ans = parser.parseE();
            if(parser.lexer.i < parser.lexer.s.length())
                throw new Exception();
            return ans;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(Parser.parse());
        } catch(Exception e){
            System.out.println("error");
        }
    }
}
