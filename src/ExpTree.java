
public class ExpTree {
		public ExpTree right,left;//,padre;
		public char operator;
		
		/*public ExpTree(char c, ExpTree padre ) {
			//this.padre=padre;
			sign=c;
			hijoDer=null;
			hijoIzq=null;
		}*/
		
		public ExpTree(char c) {
			//this.padre=null;
			operator=c;
			right=null;
			left=null;
		}
}
