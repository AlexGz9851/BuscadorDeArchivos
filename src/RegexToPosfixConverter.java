import java.util.ArrayList;
import java.util.Stack;

public class RegexToPosfixConverter {

	
	private static ArrayList<Character> jerarquia = getJerarqArray();
	
	
	private static ArrayList<Character> getJerarqArray(){
		ArrayList<Character> jerarquia = new ArrayList<Character>();
		jerarquia.add('+');// Jerarquia mas baja, 0
		jerarquia.add('.');// Jerarquia 1
		jerarquia.add('*');// Jerarquia mas alta, 2
		return jerarquia;
	}
	
	
	private static void compareOperators(char c, ArrayList<Character> postfixRegex , Stack<Character>operadores) {
		if(operadores.isEmpty() || jerarquia.indexOf(c)>jerarquia.indexOf(operadores.peek())) 
			operadores.add(c);
		else {
			postfixRegex.add(operadores.pop());
			compareOperators(c,postfixRegex,operadores);
		}
	}
	
	
	private static void checkIfConcatPresent(char current, char past,  ArrayList<Character> postfixRegex , Stack<Character>operadores) {
		if ( ! (past=='(' || past=='+')) {
			if( !(current=='*' || current==')' || current=='+')) {
				compareOperators('.', postfixRegex, operadores);
			}
		}
	}	

	
	private static void test(String regex) {
		ArrayList<Character> result;
		try {
			result = fromRegexToPostfix(regex);
			for(char c: result) {
				System.out.print(c+" ");
			}System.out.println();
		} catch (SyntaxRegexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static ArrayList<Character> fromRegexToPostfix(String regex) throws SyntaxRegexException{
		
		Stack<Character> operadores= new Stack<>();
		ArrayList<Character> postfixRegex = new ArrayList<Character>();
		
		for(int i=0;i<regex.length();i++) {
			char c = regex.charAt(i);
			
			if(i!=0)checkIfConcatPresent(c, regex.charAt(i-1),  postfixRegex , operadores);
			
			switch(c) {
			
			  case '(':
			    operadores.add(c);
			    break;
			    
			  case ')': // TODO CONCAT 
			    while(true) {
			    	if(operadores.isEmpty()) throw new SyntaxRegexException(SyntaxRegexException.PARENTESIS_DESBALANCEADOS);
			    	char currentOp= operadores.pop();
			    	if(currentOp=='(') break;
			    	
			    	postfixRegex.add(currentOp);
			    }
				break;
				
			  case '+':
				  compareOperators(c,postfixRegex,operadores);
				  break;
			  case '*':
				  compareOperators(c,postfixRegex,operadores);
				  break;
				  
			  default:
				  postfixRegex.add(c);
				  
				
			}
		}
		while(!operadores.isEmpty()) {
			char op = operadores.pop();
			if(op=='(') throw new SyntaxRegexException(SyntaxRegexException.PARENTESIS_DESBALANCEADOS);
			postfixRegex.add(op);
		}
		
		return postfixRegex;
	}
	
	
	public static void main(String[] a) {
		//
		test("aa*b+ab*c");
		test("(ab*)*(a+b)*c+(db)*");
		test("a(((a+bc)*b)*cd*(a+b)*w)*");	
	}
	
	
}
