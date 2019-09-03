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
		if(c=='.' || c=='+' || c=='*') {
			if(c!='*') {
				root.hijoDer = convert(postfix, root.hijoDer);
			}
			root.hijoIzq = convert(postfix, root.hijoIzq);
		}
		return root;
	}
	
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
		if(root.hijoIzq!=null) {
			System.out.print("(");
			printTree(root.hijoIzq);
			System.out.print(")");
		}
		
		System.out.print(root.sign);
		
		if(root.hijoDer!=null) {
			System.out.print("(");
			printTree(root.hijoDer);
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
