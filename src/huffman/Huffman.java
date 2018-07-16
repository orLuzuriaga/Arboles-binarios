package huffman;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Huffman {
	
	
	
	
	public class NodoHuffman {
		
		

		private char  code;
		private String element;
        private int frequency;
        private NodoHuffman left, right,parent;
        
        
        
        
		public NodoHuffman(String element, int frequency, NodoHuffman left, NodoHuffman right, NodoHuffman parent) {
	        
			this.element = element;
			this.frequency = frequency;
			this.left = left;
			this.right = right;
			this.parent = parent;
			
		}
		
		
		
		
		public char getCode() {
			return code;
		}
		public void setCode(char code) {
			this.code = code;
		}
		public String getElement() {
			return element;
		}
		public void setElement(String element) {
			this.element = element;
		}
		public int getFrequency() {
			return frequency;
		}
		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}

		public NodoHuffman getLeft() {
			return left;
		}

		public void setLeft(NodoHuffman left) {
			this.left = left;
		}

		public NodoHuffman getRight() {
			return right;
		}

		public void setRight(NodoHuffman right) {
			this.right = right;
		}
		
		
		
		public NodoHuffman getParent() {
			return parent;
		}




		public void setParent(NodoHuffman parent) {
			this.parent = parent;
		}
	}
	
	
	

	
	
	
	
	private LinkedList<NodoHuffman> listTree;
	private HashMap<String,NodoHuffman> position;
    private NodoHuffman tree;


   /*
    * Constructor
    */

	public Huffman(String str) {
		
		
		
		
		this.position = new HashMap<>();
		this.listTree = new LinkedList <>(); 
		
		//si la cadena a codificar solo tiene un caracter creamos un árbol con un unico nodo 
		//añadimos un frecuencia de 1, el código(peso) y su elemento
		if(str.length() == 1) {
			
			NodoHuffman newTree = new NodoHuffman(str,1,null, null,null);
			this.tree = newTree;
			this.tree.setCode('0');
		}else {
			listTree =  frecuency(str);
		    this.tree = analisis(listTree);
		    
		  
			
		}
	    
		
	}
	
	
	
	
	/*
	 *  Getters
	 */

	public NodoHuffman getTree() {
		return tree;
	}

	public LinkedList<NodoHuffman> getListTree() {
		return listTree;
	}






	



	/**
     * Primera y segunda etapa: Devuelve una cola que contiene nodos huffman en este método
     * calculamos la frecuencia de ocurrencia de cada caracter, creamos un nodoHuffman
     * que contiene dicho caracter y su frecuencia, a su vez creamos un arbol por cada caracter(unico nodo)
     * y lo guardamos en una estructura(cola) para su posterior analisis.
     * 
     */
	private LinkedList<NodoHuffman> frecuency(String str) {
		LinkedList<NodoHuffman> aux  = new LinkedList <>(); 
				String regex = "";
				NodoHuffman nh = null;
				int frecuency = 0;
				//lista auxiliar contiene los elementos ya calculados todos los caracteres ya calculados
				List<String> calculados = new ArrayList<>();
				

				for(int i = 0; i < str.length()  ; i++) {
					
					//Sacamos el caracter a buscar y lo guardamos en la variable regex
					regex = Character.toString(str.charAt(i));
					
				
					//Comprobamos si el caracter a buscar esta en la lista de calculados
					if(!calculados.contains(regex)) {
			
						// recorremos el el string comparando el caracter i con cada caracter j,
					
						for(int j = 0; j < str.length() ; j++) {

							if(str.charAt(j) == str.charAt(i)) {
								frecuency++;
							}
						}
						//Creamos el tipo de dato huffman que contiene la cadena(elemento), la frecuencia y el codigo por defecto "0"
						//este lo añadimos a nuestra estructura (cola) auxiliar y nuestra lista (calculados)
						 nh = new NodoHuffman(regex,frecuency,null,null,null);
						 aux.add(nh);
						 //Lista auxiliar me permite llevar la cuenta de los caracteres que llevo calculados con su frecuencia
						 calculados.add(regex);
						 frecuency = 0;
						
					}
				}	
	   // Por ultimo ordenamos de menor a mayor para su posterior analisis;
	  aux.sort(((a,b)-> a.getFrequency() - b.getFrequency()));
	  
	  return aux;

	}

	
	
	


	/*
	 * Segunda y tercera etapa: Devuelve un unico árbol donde cada nodo contiene sus caracteres correspondientes, su frecuencia y su peso
	 * aqui lo que hacemos es unir los dos árboles con menor frecuencia, como resultado de cada unión se crea un nuevo árbol
     * cuya raíz es un nodo con frecuencia igual a la suma de los dos nodos que se han fusionado y sin información de carácter. 
     * Este proceso continua hasta que sólo queda un árbol, cada nodo que se utiliza en la fusión ocupara la posición izquierda y derecha
     * en este momento actualizamos su peso siguiendo el criterio de asignación 0 a la izquierda y 1 a la derecha.
	 */
	private NodoHuffman  analisis(LinkedList<NodoHuffman> huffTree) {
		
		NodoHuffman newTree = null;
		NodoHuffman left = null;
		NodoHuffman right = null;
		int frecuency;
		
 
			//Recorremos la lista hasta que su valor sea 1 (Unico arbol)
			while(listTree.size() > 1) {
				//[_,2,3,4,5,5,6]
				left = huffTree.removeFirst();
				//[_,3,4,5,5,6]
				right = huffTree.removeFirst();
				
				 
				 //Calculamos la frecuencia
				 frecuency = left.getFrequency() + right.getFrequency();
				
				//Asignamos los pesos a cada nodo siguiendo el criterio de asignación 0 izquierda 1 derecha 
				 left.setCode('0');
				 right.setCode('1');
				 //Creamos un nuevo nodo que contiene las sumas de las frecuencias, lo referenciamos a sus dos hijos (izq y der) y asiganamos su elemento por defecto
				 
				 newTree = new NodoHuffman("\0",frecuency,left,right, null);
				 //Añado las referencia del padre al nuevo nodo 
				 left.setParent(newTree);
				 right.setParent(newTree);
				 //guardamos las posiciones
				 position.put(left.getElement(), left);
				 position.put(right.getElement(), right);
				
				 
				 
	             
	             //Añadimos el nuevo árbol a la lista de arboles
	             huffTree.add(newTree);
				 
	      	   // Por ultimo ordenamos de menor a mayor 
	       	  huffTree.sort(((a, b)-> a.getFrequency() - b.getFrequency()));
				
				
			} 
			
			
			return huffTree.removeFirst();
	
	}
	
	
	/**
	 * 
	 * @param c caracter que buscaremos su código huffman
	 * @return código huffman 
	 */
	
	public String codificar(char c) {
		
		String strBuscar = Character.toString(c);
		NodoHuffman pos = position.get(strBuscar);
		String code = auxCodificar(pos);
		return code.toString();
	}
	
	
	
	/*
	 * Concatena de forma recursiva el código del caracter
	 */
	private String auxCodificar(NodoHuffman p) {
		
       //Llegamos a la raiz
		if(p.getParent() == null) {
			// La raiz al no tener nigun peso devolvemos un valor por defecto
          return  "";
             
		}else {
			
		//LLamanos al método pasandole el padre y concatenando el código anterior
	      return auxCodificar(p.getParent()) + Character.toString(p.getCode());
		}
		
		
		
		
	}
	
	
	
	

	
	
	
	
	


}
