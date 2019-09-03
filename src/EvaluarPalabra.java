import java.util.ArrayList;

public class EvaluarPalabra {

	public static int evaluar(ExpTree root, String palabra, int iniSub) {
		// devuelve el index final del substring el cual coincide con el regex.
		int finalSub= -1;
		if(root.sign=='.' ) {
			int izq= evaluar(root.hijoIzq, palabra,  iniSub);
			int der;
			if(izq==-1 ) return -1;
			der= evaluar(root.hijoDer, palabra,  izq);
			if(der<0) return der;
			finalSub = izq > der ? izq : der;
		}else if( root.sign=='+'){
			int izq= evaluar(root.hijoIzq, palabra,  iniSub);
			int der= evaluar(root.hijoDer, palabra,  iniSub);
			finalSub = izq > der ? izq : der;
		}else  if(root.sign=='*') {//TODO CORREGIR CERRADURAS
			int izq = evaluar(root.hijoIzq, palabra, iniSub);
			if(izq<0) return iniSub;
			int newIzq= evaluar(root, palabra, izq);
			finalSub = izq > newIzq ? izq : newIzq;
		}else {
			if(iniSub==-2) return iniSub;
			if(iniSub>=palabra.length()) 
				finalSub = -1;
			else 
				if(palabra.charAt(iniSub)==root.sign)
					finalSub = iniSub +1;
				else
					finalSub =-2;
		}
		
		
		return finalSub;
	}
	
	private static void test(String regex, String palabra, int inicio) {
		ArrayList<Character> postfix;
		ExpTree root;
		
		try {
			postfix = RegexToPosfixConverter.convert(regex);
			root = PosfixToTreeConverter.convert(postfix);
			int fin=evaluar(root, palabra, inicio);
			if(fin<0) 
				System.out.println("false");
			else {
				System.out.print(palabra.substring(inicio, fin));
				System.out.print("-");
				System.out.println(palabra.substring(fin));
			}
		} catch (SyntaxRegexException e) {

			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] a) {
		test( "aab",  "aaab", 1);
		test( "aab",  "aaab", 0);
		test( "aab",  "aaba", 0);
		test( "aab",  "aabb", 0);
		System.out.println();
		test( "aa*b+ab*b",  "b", 0);
		test( "aa*b+ab*b",  "aaab", 0);
		test( "aa*b+ab*b",  "ab", 0);
		test( "aa*b+ab*b",  "abbbbb", 0);
		test( "aa*b+ab*b",  "aba", 0);
		test( "aa*b+ab*b",  "babbbbb", 0);
		System.out.println();
		test("(a+b)*aab*" ,  "abababaab",0);
		test("(a+b)*aab*" ,  "abababaaba",0);
		test("(a+b)*aab*" ,  "abbaabbb",0);
		test("(a+b)*aab*" ,  "abbaa",0);
		test("(a+b)*aab*" ,  "aa",0);
		test("(a+b)*aab*" ,  "baba",0);
		test("(a+b)*aab*" ,  "abab",0);
		

	}
}
