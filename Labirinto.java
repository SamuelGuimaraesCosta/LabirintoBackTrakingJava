import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Labirinto {
	
	private static char[][] matriz = new char[][] {};
	
	public static int tamanhoY, tamanhoX, inicioX = -1, inicioY = -1;
	
	public static String caminhoPercorrido = "";
	
	public static Boolean temChave = false, mapaDesafio = false, abriuPorta = false;
	
	public static void lerArquivoLabirinto() {
		try {
			File myObj = new File("labirinto.txt");
			
		    Scanner myReader = new Scanner(myObj);
		    
		    tamanhoY = 0;
		    
		    while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        
		        tamanhoX = data.length();
		        
		        tamanhoY++;
		    }
		    
		    char [][] mAux = new char[tamanhoY][tamanhoX];
		    
		    myReader.close();
		    
		    myReader = new Scanner(myObj);
		    
		    int aux = 0;
		    
		    while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        
		        for(int i = 0; i < data.length(); i++) {
		        	mAux[aux][i] = data.charAt(i);
		        	
		        	if(data.charAt(i) == 'E') {
		        		inicioX = i;
		        		inicioY = aux;
		        	}
		        	if(data.charAt(i) == 'D' || data.charAt(i) == 'K') {
		        		mapaDesafio = true;
		        	}
		        }
		        
		        aux++;
		    }
		    
		    matriz = mAux;
		    
		    myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ocorreu um erro ao ler o arquivo labirinto.txt");
			
		    e.printStackTrace();
		}
	}
	
	public static void imprimeLabirinto() {
		try {
            Thread.sleep(150);
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		for(int i = 0; i< 50; i++) System.out.println();
		
		for(int y = 0; y < matriz.length; y++) {
			for(int x = 0; x < matriz[y].length; x++) {
				System.out.print(matriz[y][x] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
	}
	
	public  static boolean localValido(int linha, int coluna, int nlinhas, int ncolunas) {
        return linha < nlinhas && coluna < ncolunas && linha >= 0 && coluna >= 0; 
    }
	
	public static Boolean resolverLabirintoTraverse(final char[][] labirinto, final int linha, final int coluna) {
		imprimeLabirinto();
		
		if(localValido(linha, coluna, labirinto.length, labirinto[0].length)) {
			if(labirinto[linha][coluna] == 'D' && !temChave) {
				return false;
			}
			if(labirinto[linha][coluna] == 'X' || labirinto[linha][coluna] == '.' || (labirinto[linha][coluna] == '*')) {
				return false;
			}
			if(labirinto[linha][coluna] == 'E') {
	            return true;
	        }
			if(labirinto[linha][coluna] == 'K') {
				temChave = true;
			}
			if(labirinto[linha][coluna] == 'D') {
				abriuPorta = true;
			}
			if(labirinto[linha][coluna] == 'S') {
				labirinto[linha][coluna] = 'S';
			} else {
		        labirinto[linha][coluna] = '*';
			}

	        //baixo
	        if(resolverLabirintoTraverse(labirinto, linha + 1, coluna)) {
	        	caminhoPercorrido += 'B';
	        	return true;
	        }
	        //esquerda
	        if(resolverLabirintoTraverse(labirinto, linha, coluna - 1)) {
	        	caminhoPercorrido += 'E';
	        	return true;
	        }
	        //cima
	        if(resolverLabirintoTraverse(labirinto, linha - 1, coluna)) {
	            caminhoPercorrido += 'C';
	            return true;
	        }
	        //direita
	        if(resolverLabirintoTraverse(labirinto, linha, coluna + 1)) {
	        	caminhoPercorrido += 'D';
	            return true;
	        }
	        
		    //backtrack
	        if(labirinto[linha][coluna] != 'S')
	        	labirinto[linha][coluna] = mapaDesafio ? ' ' : '.';
		    
		    return false;
		}
		return false;
	}
	
	public static void main(String[] args) {
		lerArquivoLabirinto();
		
		imprimeLabirinto();
		
		Boolean caminhoExiste = false;
		
        for(int i = 0 ;i < matriz.length; i++)
            for(int j = 0; j <matriz[i].length; j++)
                if(matriz[i][j] == 'S') {
                    caminhoExiste = resolverLabirintoTraverse(matriz, i, j);
                }
        
		imprimeLabirinto();
		
		if(!caminhoExiste)
			for(int i = 0 ;i < matriz.length; i++)
	            for(int j = 0; j <matriz[i].length; j++)
	                if(matriz[i][j] == 'S') {
	                    caminhoExiste = resolverLabirintoTraverse(matriz, i, j);
	                }
        
		String resp = caminhoExiste == true ? "Sim" : "Não";
		
		System.out.println("Labirinto Resolvido: " + resp);
		
		System.out.println("Caminho Percorrido: " + caminhoPercorrido);
	}
}