import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class LecturaRecursiva {

	public static void busquedaTodaCarpetaYArchivo(String regex, boolean readFilesAlso) {
		final String dir = System.getProperty("user.dir");
		System.out.println(dir);
		listarFicherosPorCarpeta(new File(dir),regex, 0,readFilesAlso);
	}

	public static void listarFicherosPorCarpeta(final File carpeta, String regex, int tab, boolean readFilesAlso) {
	    for (final File ficheroEntrada : carpeta.listFiles()) {
	    	
	    	String word= ficheroEntrada.getName();
	    	int[] indexes = EvaluarPalabra.findBestSubstringMatch(regex , word);
	    	boolean printed= printFile(word, indexes, tab);
	    	
	        if (ficheroEntrada.isDirectory()) {
	        	
	        	if(!printed) printName(ficheroEntrada.getName(), tab);
	            listarFicherosPorCarpeta(ficheroEntrada, regex, tab+1, readFilesAlso);
	            
	        } else if(ficheroEntrada.isFile()) {
	        	
	        	if(!printed && readFilesAlso) printName(ficheroEntrada.getName(), tab);
	        	if(readFilesAlso) readFileFindSubstrings(ficheroEntrada,regex, tab+1);
	        	System.out.println();
	        	
	        }
	    }
	}
	
	
	private static void printName(String name, int tab) {
		
		for(int i=0;i<tab;i++) {System.out.print("    ");}
		System.out.println(name);
		
	}
	
	
	private static void readFileFindSubstrings(File file, String regex, int tab) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					file));
			String line = reader.readLine();
			while (line != null) {
				String[] palabras = line.split("\\s+");
				printListOfWords(regex, palabras,  tab);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {}
	}
	
	
	private static void printListOfWords(String regex ,String[] words, int tab) {
		boolean aMatch=false;
		int[][] listIndexes = new int[words.length][];
		for(int i=0;i<words.length;i++) {
			words[i] = words[i].strip();
			listIndexes[i]=EvaluarPalabra.findBestSubstringMatch(regex , words[i]);
			if(!(listIndexes[i][0]>= listIndexes[i][1] && listIndexes[i][2]!=1 )) {
				if(aMatch) 
					System.out.print(", ");
				else
					for(int ii=0;ii<tab;ii++) {System.out.print("    ");}
				System.out.print( words[i].substring(0, listIndexes[i][0]));
				System.out.print("*" +  words[i].substring(listIndexes[i][0],listIndexes[i][1])+ "*");
				System.out.print( words[i].substring( listIndexes[i][1]));
				aMatch=true;
			}
		}
	}
	
	
	private static boolean printFile(String word, int[] indexes, int tab) {
		if(indexes[0]>= indexes[1] && indexes[2]!=1) return false; // no match
		for(int i=0;i<tab;i++) {System.out.print("    ");}
		if (indexes[2]==1)// AnyWord situation
			System.out.println("*" + word+ "*");
		else {
			System.out.print(word.substring(0, indexes[0]));
			System.out.print("*" + word.substring(indexes[0],indexes[1])+ "*");
			System.out.println(word.substring( indexes[1]));
		}
		return true;
	}
	
	public static void main(String[] a) {
		//busquedaTodaCarpetaYArchivo("pre+e*", false);
		//TODO como va a ejecutar el profe el programa? usando cmd?
		for(String aa: a) {
			busquedaTodaCarpetaYArchivo(aa, false);
		}
	}

}
