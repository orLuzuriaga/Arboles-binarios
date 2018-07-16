package tree.binarytree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tree.*;
import tree.iterator.InOrderIterator;


/**
 * *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro
 * @param <E> The type of the elements in the tree
 * @see BinaryTree
 */
public class ArrayBinaryTree<E> implements BinaryTree<E> {

    private class BTPos<T> implements Position<T> {

        private T element;
        private int index;
        private ArrayBinaryTree<T> myTree;

        public BTPos(ArrayBinaryTree<T> myTree, T element, int index) {
            this.myTree = myTree;
            this.element = element;
            this.index = index;
        }

        @Override
        public T getElement() {
            return this.element;
        }

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public ArrayBinaryTree<T> getMyTree() {
			return myTree;
		}

		
		public void setElement(T element) {
			this.element = element;
		}
        
        
        
        
        
    }
    
    

    private class ArrayBinaryTreeIterator<T> implements Iterator<Position<T>> {
        
    	private int indice = 0;
        @Override
        public boolean hasNext() {
            
        	return (indice < size);
        }

       
		@Override
        public Position<T> next() {
        	  Position<T>  aux = null;
        	  boolean posValida = false;
        	  int auxIndice = 0;
        	  //Busca la proxima pos valida
        	
        	 if(indice < size) {
        	   aux =  (Position<T>) tree[indice];
        	   
        	   if(aux == null) {
        		   auxIndice = indice++;
        		   
        		   while(!posValida) {
        			   aux =  (Position<T>) tree[auxIndice];
        			   if(aux!= null) {
        				   posValida = true;
        			   }
        			   auxIndice++;
        			   
             	  }
        		   indice = auxIndice;
        	   }
        		 
        	 }
        	 
        	
        	 indice++;
             return aux;
        
        }
    }

    private BTPos<E>[] tree;
    private int size;
    private ArrayBinaryTreeIterator<E> iteratorFactory;
    /**
    
    

    /**
     * Constructor of the class.
     */
    
	public ArrayBinaryTree(int capacity) {
    	 tree = new BTPos[capacity];
         this.iteratorFactory = new ArrayBinaryTreeIterator<>();
        
    }
    
    
    
    

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        
    	return (size == 0);
    }

    @Override
    public boolean isInternal(Position<E> p) throws IllegalStateException {
    	
    	   BTPos<E> pos = checkPosition(p);
           int indiceIzq =  (2 * pos.index) + 1; 
           int indiceDer = (2 * pos.index) + 2;
           boolean sinHijos = false;
          
           //Comprobamos que los indices no sean superior a la capacidad del array 
           //Para evitar generar exceptions no comprobadas
           
           if((indiceIzq <= tree.length) || (indiceDer <= tree.length)) {
           	
           	sinHijos = ((tree[indiceIzq]!=null)||(tree[indiceDer] != null))?true:false;
           	
           }else {
           	
           	throw new ArrayIndexOutOfBoundsException("Indice ilegal");
           }
           return sinHijos;
    	
    }

    @Override
    public boolean isLeaf(Position<E> p) throws IllegalStateException {
    	
    	
    	return (!isInternal(p));
        
    }

    
    
    @Override
    public boolean isRoot(Position<E> p) throws IllegalStateException {
    	BTPos<E> node = checkPosition(p);
    	 if (isEmpty()) {
             throw new IllegalStateException("The tree is empty");
         }
    	 
       return (tree[0].equals(node));
    }

    @Override
    public boolean hasLeft(Position<E> p) throws IllegalStateException {
    	 BTPos<E> node = checkPosition(p);
    	 int indiceIzq =  (2 * node.index) + 1; 
    	 boolean  izq = false;
    	 
    	
    	   if((indiceIzq <= tree.length)) {
           	
           	izq= ((tree[indiceIzq]!=null));
           	
           }
    	   
    	   return izq;
    	
    	
    }

    @Override
    public boolean hasRight(Position<E> p) throws IllegalStateException {
       
    	 BTPos<E> node = checkPosition(p);
    	 int indiceDer=  (2 * node.index) +  2; 
    	 boolean right = false;
    	 
    	
    	   if((indiceDer <= tree.length)) {
           	
           	right = ((tree[indiceDer]!=null));
           	
           }
    	   
    	   return right;
    	
    }

    
    @Override
    public Position<E> root() throws IllegalStateException {
        
    	if(isEmpty()) {
    		throw new IllegalStateException("The tree is Empty");
    	}
    	
    	return tree[0];
    }

    
    
    
    @Override
    public Position<E> left(Position<E> p) throws IllegalStateException, IndexOutOfBoundsException {
      BTPos<E>node = checkPosition(p);
      int indiceIzq = (2 * node.index) + 1;
      BTPos<E>hijoIzq = null;
      
      if((indiceIzq <= tree.length)) {
         	
         	hijoIzq = tree[indiceIzq];
         	
         	
         }else {
        	 
        	 throw new IndexOutOfBoundsException();
         }
      
      if(hijoIzq == null) throw new IllegalStateException("No left child");
          
      
      return hijoIzq;
  	
    	
    }

    @Override
    public Position<E> right(Position<E> p) throws IllegalStateException, IndexOutOfBoundsException {
    	  BTPos<E>node = checkPosition(p);
          int indiceDer = (2 * node.index) + 2;
          BTPos<E>hijoDer = null;
          //Compruevo el indice para evitar acceder a un indice que no existe evitando generar la exception Index
          if((indiceDer<= tree.length)) {
             	
             	hijoDer = tree[indiceDer];
             	
             	
             }else {
            	 
            	 throw new IndexOutOfBoundsException();
             }
          
          if(hijoDer == null) throw new IllegalStateException("No left child");
          
          return hijoDer;
    }

    
    
    
    @Override
    public Position<E> parent(Position<E> p) throws IllegalStateException, IndexOutOfBoundsException {
   	 BTPos<E> node = checkPosition(p);
	 int indiceParent = (node.index - 1)/ 2;
	 
	 BTPos<E>parent = null;
     
     if((indiceParent<= tree.length)) {
        	
        	parent = tree[indiceParent];
        	
        	
        }else {
     	   
     	   throw new IndexOutOfBoundsException("Indice ilegal");
     	   
        }
     
     //if(parent == null) throw new IllegalStateException("No left child");
     
     return parent;
	 
	 
    }
    
    
    
    

    @Override
    public Iterable<Position<E>> children(Position<E> p) throws IllegalStateException {
      List<Position<E>> children = new ArrayList<>();
      
      if (hasLeft(p)) {
          children.add(left(p));
      }
      if (hasRight(p)) {
          children.add(right(p));
      }
      return children;
  }
      
      
   
    
    
    
    
    @Override
    public Iterator<Position<E>> iterator() {
        return  new ArrayBinaryTreeIterator<>();
    
    }

    
    
    
    public ArrayBinaryTreeIterator<E> getIteratorFactory() {
		return iteratorFactory;
	}





	public void setIteratorFactory(ArrayBinaryTreeIterator<E> iteratorFactory) {
		this.iteratorFactory = iteratorFactory;
	}





	@Override
    public E replace(Position<E> p, E e) throws IllegalStateException {
    	 BTPos<E> node = checkPosition(p);
         E temp = p.getElement();
         node.setElement(e);
         return temp;
    }

    
    
    
    
    // Additional Methods
    @Override
    public Position<E> sibling(Position<E> v) throws IllegalStateException,
            IndexOutOfBoundsException {
        
    	BTPos<E> node = checkPosition(v);
    	
    	if(node == this.root()) {
    		 throw new IllegalStateException("the node is root");
    	}else {
    		
    		int iParent = (node.index - 1)/ 2;
            Position<E> parent = this.tree[iParent];
            Position<E> sibling = null;
            
            if (parent != null) {
               
                Position <E> leftPos =  left(parent);
                sibling = (leftPos == node) ? right(parent) : left(parent);
                if (sibling != null) {
                    return sibling;
                }
            }
            throw new IndexOutOfBoundsException("No sibling");
    		
    	}
    	
    }
    
    
    

    @Override
    public Position<E> addRoot(E e) throws IllegalStateException {
    	if (!isEmpty()) {
            throw new IllegalStateException("Tree already has a root");
        }
        size = 1;
        BTPos<E> root = new BTPos<E>(this, e, 0);
        this.tree[0] = root;
        return root;
    }

    
    
    @Override
    public Position<E> insertLeft(Position<E> p, E e) throws IllegalStateException {
       
    	 BTPos<E> parent = checkPosition(p);
    	 
         int indiceIzq = (2 * parent.index) + 1;
         
         Position<E> leftPos = this.tree[indiceIzq];
         if (leftPos != null) {
             throw new IllegalStateException("Node already has a left child");
         }
         BTPos<E> newNode = new BTPos<E>(this, e, indiceIzq);
        this.tree[indiceIzq] = newNode;
         size++;
         return newNode;
    }

    
    
    @Override
    public Position<E> insertRight(Position<E> p, E e) throws IllegalStateException {
          BTPos<E> parent = checkPosition(p);
    	 
         int indiceDer = (2 * parent.index) + 2;
         
         Position<E> rightPos = this.tree[indiceDer];
         if (rightPos != null) {
             throw new IllegalStateException("Node already has a left child");
         }
         BTPos<E> newNode = new BTPos<E>(this, e, indiceDer);
         this.tree[indiceDer] = newNode;
         size++;
         return newNode;
    }
    
    /**
     *  
     * 
     * (2 * parent.index) + 1; izquierdo
     * (2 * parent.index) + 2; derecho
	 * 
	 *       A                    A
	 *     /   \                 / \
	 *    B     D   ->          B   D              
	 *   / \     \               \   \
	 *  E   G     F               G    F
	 *  
	 *  0 1 2 3 4 5 6 
	 *  A B D E G   F
	 */
     

    @Override
    public E remove(Position<E> p) throws IllegalStateException {
    	 BTPos<E> node = checkPosition(p);
    	  BTPos<E> hijoIzq = null;
	      BTPos<E> hijoDer = null;
    	 int indiceIzq = (2 * node.index) +1;
    	 int indiceDer = (2 * node.index) + 2;
    	
    	 //Nos aseguramos de no acceder a una celda superior al tamaño del array
    	 if((indiceIzq <= tree.length)) hijoIzq = this.tree[indiceIzq];
    	 if((indiceDer <= tree.length)) hijoDer = this.tree[indiceDer];
    	 
    		 
 
         
         //Comprobamos que el nodo a borrar no tenga dos hijos
         if (hijoIzq != null && hijoDer != null) {
             throw new IllegalStateException( "Cannot remove node with two children");
         }
         
         //Creamos la variable auxiliar child para guardar el hijo izq o el hijo derecho si no tiene es null
         BTPos<E> child; 
         if (hijoIzq != null) {
             child = hijoIzq;
         } else if (hijoDer != null) {
             child = hijoDer;
         } else { 
             child = null;
         }
         
         
         //Borramos si el nodo es root
         if (node == root()) { // v is the root
             if (child != null) {
                 child.setIndex(0);
             }    
             this.tree[0] = child;
             
         } else { 
        	 
        	 int indiceParent = (node.index -1)/2;
             BTPos<E> parent = this.tree[indiceParent];
             
             if (node == left(parent)) {
            	// int posIzq = (2 * parent.index)+1;
            	 tree[node.getIndex()] = child;
                // parent.getMyTree().tree[node.getIndex()] = child;
             } else {
            	 
            	 tree[node.getIndex()] = child;
            	 //int posDer= (2 * parent.index)+2;
                // parent.getMyTree().tree[posDer] = child;
             }
             
         }
         size--;
         return p.getElement();
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2)
            throws IllegalStateException {
    	BTPos<E> node1 = checkPosition(p1);
        BTPos<E> node2 = checkPosition(p2);
        E temp = p2.getElement();
        node2.setElement(p1.getElement());
        node1.setElement(temp);
    }

    /**
     * Determines whether the given position is a valid node.
     *
     * @param v the position to be checked
     * @return the position casted to BTPos
     */
    private BTPos<E> checkPosition(Position<E> v) throws IllegalStateException {
    	 if (v == null || !(v instanceof BTPos)) {
             throw new IllegalStateException("The position is invalid");
         }
         BTPos<E> btpos = (BTPos<E>) v;
         if (btpos.getMyTree() != this) {
             throw new IllegalStateException("The position does not belong to this tree");
         }
         return (BTPos<E>) v;
    }

    @Override
    public String toString() {
    	 return this.tree.toString();
    }
    
    
    
    /** Removes the subtree rooted at pOrig 
     * 
     * pOrig = B
     * 
	 * 
	 *       A                    A
	 *     /   \                 / \
	 *    B     D   ->         NULL  D              
	 *   / \     \                    \
	 *  E   G     F                    F
	 *  
	 */
   
    public Position<E> removeSubtree(Position<E> p)  throws IllegalStateException{
    	 
    	 BTPos<E> nodoBorrar = checkPosition(p) ;
    	
    	 int cont=0;
    	 
    	 Iterator<BTPos<E>> it = new InOrderIterator(this,nodoBorrar);
    	 
    	 while(it.hasNext()) {
    		 
    		 tree[it.next().index]=null;
    		 cont++;
    		 
    	 }
    	size -= cont;
     	return p;
  	
    	
    }
    
    

    
    

    

}
