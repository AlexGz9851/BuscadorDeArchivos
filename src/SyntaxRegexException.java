
public class SyntaxRegexException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PARENTESIS_DESBALANCEADOS="La expresion regular contiene un numero de parentesis desbalanceado";

	public SyntaxRegexException(String message) {
		super(message);
	}
}
