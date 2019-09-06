import java.util.ArrayList;
import java.util.Set;

public class EvaluarPalabra {

	private static int evaluar(ExpTree root, String palabra, int iniSub, boolean exact) {
			palabra = palabra.toLowerCase();
			ArrayList<Integer> posibilidades = evaluarInner(root, palabra, iniSub);
			int max=posibilidades.get(0);
			for(int i: posibilidades) { max = i>max ? i : max; }
			return  exact ? ( max==palabra.length() ?  max :  -1) :  max;
	}
	
	
	
	private static ArrayList<Integer> evaluarInner(ExpTree root, String palabra, int iniSub) {
		// devuelve el conjunto de  indexes finales del substring el cual coincide con el regex.
		// compara el string con el arbol generado por el regex.
		
		ArrayList<Integer> posibilidades, left, right; // indices finales posibles  para la substring. [iniSub, fin)
		posibilidades= new ArrayList<Integer>();
		posibilidades.add(-1);
		char c= root.operator;
		switch(c) {
			case '+':
				// unión.
				left=evaluarInner(root.left, palabra, iniSub);
				right=evaluarInner(root.right, palabra, iniSub);
				posibilidades.addAll(left);
				posibilidades.addAll(right);// uniendo conjuntos
				break;
		    case '.':
		    	// concatenacion
		    	left=evaluarInner(root.left, palabra, iniSub);
		    	for(int l: left) {
		    		if(l>=iniSub) {
		    			right = evaluarInner(root.right, palabra, l);
		    			for(int r: right) {
		    				if(r>=l) posibilidades.add(r);
		    			}
		    		}
		    	}
		    	break;
		    case '*':
		    	// cerradura
		    	posibilidades.add(iniSub);
		    	if(root.left==null) {
		    		posibilidades.add(palabra.length());
		    		break;
		    	}
		    	left=evaluarInner(root.left, palabra, iniSub);
		    	for(int l: left) {
		    		if(l>iniSub) {
		    			posibilidades.add(l);
		    			right = evaluarInner(root, palabra, l);
		    			for(int r: right) {
			    			if(r>l) {
			    				posibilidades.add(r);		
			    			}
		    			}
		    		}
		    	}
		
		    break;
		    default:
		    	//letra.
		    	if(iniSub<palabra.length() && iniSub>=0) {
		    		if(palabra.charAt(iniSub)==c) posibilidades.add(iniSub+1);
		    	}
		}
		return posibilidades;
	}
	
	
	public static int[] findBestSubstringMatch(String regex, String word) {
		int[] bestInicioFin = {0,0,0}; // start, end, anyWord flag. 0, false, 1 true.
		if(regex.equals("*")) { // accepts any substring
			bestInicioFin[2] = 1; //any string flag.
			return bestInicioFin;
		}
		ArrayList<Character> postfix;
		ExpTree root;
		try {
			postfix = RegexToPosfixConverter.convert(regex);
			root = PosfixToTreeConverter.convert(postfix);
			for(int inicio=0;inicio<word.length();inicio++) {
				int fin=evaluar(root, word, inicio, false);
				if(fin-inicio>bestInicioFin[1]-bestInicioFin[0]) {
					bestInicioFin[0]=inicio;
					bestInicioFin[1]=fin;
				}
			}
			
		}catch (SyntaxRegexException e) {
			e.printStackTrace();
		}
		return bestInicioFin;
	}
	
	/*test functions*/
	public static void test(String regex, String palabra, int inicio) {
		test( regex,  palabra,  inicio, false);
	}
	
	
	public static void test(String regex, String palabra, int inicio, boolean exact) {
		ArrayList<Character> postfix;
		ExpTree root;
		
		try {
			postfix = RegexToPosfixConverter.convert(regex);
			root = PosfixToTreeConverter.convert(postfix);
			int fin=evaluar(root, palabra, inicio, exact);
			if(fin<0) 
				System.out.println("false");
			else {
				System.out.print(palabra.substring(0, inicio));
				System.out.print("-");
				System.out.print(palabra.substring(inicio, fin));
				System.out.print("-");
				System.out.println(palabra.substring(fin, palabra.length()));
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
