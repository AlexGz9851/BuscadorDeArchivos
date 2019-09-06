import java.util.ArrayList;

public class PosfixToTreeConverter {

	public static ExpTree convert(ArrayList<Character> postfix)throws SyntaxRegexException {
		ExpTree root=null;
		root = convert(postfix, root);
		return root;
	}
	
	private static ExpTree convert(ArrayList<Character> postfix, ExpTree root) throws SyntaxRegexException{
		if(postfix.isEmpty()) throw new SyntaxRegexException(SyntaxRegexException.SYNTAX_ERROR);
		char c = postfix.remove(postfix.size() -1);
		root= new ExpTree(c);
		
		if(c=='.' || c=='+') {
			root.right = convert(postfix, root.right);
			root.left = convert(postfix, root.left);
		}else if(c=='*') {
			if(!postfix.isEmpty()) root.left = convert(postfix, root.left); // covers posibility of having: "*" expresion.
		}
		
		return root;
	}
	
	
	
	
	/*FUNCTIONS FOR TESTING*/
	private static void test(ArrayList<Character> postfix) {
		try {
			ExpTree result = convert(postfix);
			printTree(result);
			
		}catch(SyntaxRegexException e) {
			e.printStackTrace();
		}
	}
	
	private static void printTree(ExpTree root) {
		if(root==null) return;
		if(root.left!=null) {
			System.out.print("(");
			printTree(root.left);
			System.out.print(")");
		}
		
		System.out.print(root.operator);
		
		if(root.right!=null) {
			System.out.print("(");
			printTree(root.right);
			System.out.print(")");
		}
		
	}
	
	public static void main(String[] a) {
		ArrayList<Character> post;
		try {
			post = RegexToPosfixConverter.convert("a(((a+bc)*b)*cd*(a+b)*w)*");
			test(post);	
		} catch (SyntaxRegexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
