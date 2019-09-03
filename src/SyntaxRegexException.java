
public class SyntaxRegexException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PARENTESIS_DESBALANCEADOS="La expresion regular contiene un numero de parentesis desbalanceado";
	public static final String SYNTAX_ERROR = "La expresion regular contiene un error de sintaxis";
	
	public SyntaxRegexException(String message) {
		super(message);
	}
}
