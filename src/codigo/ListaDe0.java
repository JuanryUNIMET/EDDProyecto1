/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;

/**
 *
 * @author juanry , Romer
 *
 */

public class ListaDe0 {
    private NodoLista pFirst;
    private int size;
    
    //Constructor de la lista
    public ListaDe0(){
        pFirst = null;
        size = 0;
    }
    
    /**
     * @return the pFirst
     */
    public NodoLista getpFirst() {
        return pFirst;
    }

    /**
     * @param primero the pFirst to set
     */
    public void setpFirst(NodoLista primero) {
        this.pFirst = primero;
    }
    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    /**
     * 
     * @return valor de tipo boolean si la lista es vacia o no
     */
    
    public boolean esVacio(){
        return pFirst == null;
    }
    
    /**
     * 
     * @param name 
     *  Inserta un nombre del usuario al inicio de una lista
     */

    public void insertarInicio (String name){ 
        
        NodoLista node;
        node = new NodoLista(name);       
        if(esVacio()){
            pFirst = node;
            size ++;

        }else {
            NodoLista aux = pFirst;
            node.setSiguiente(aux);
            
            pFirst = node;
            size++;

        }

    }
    
    /**
     * 
     * @param node
     * @return lista añadida el nodo al final
     */

    public  ListaDe0 insertarFinal (NodoLista node){
    
        NodoLista aux = pFirst;
        NodoLista aux2 = aux;
        
        if(esVacio()){
            pFirst = node;
            size ++;
        }else{          
            while (aux != null){
                aux2 = aux;
                
                aux = aux.getSiguiente();

            }
            node.setSiguiente(aux2.getSiguiente());
            aux2.setSiguiente(node);
            size ++;
        }
        return this;
    }    
    
    /**
     * 
     * @param name 
     *  Inserta un nombre al final de la lista, si el nombre del usuario NO existe ya en la lista
     */

    public void insert (String name){
        if(search(name)== null){
        NodoLista nuevoName = new NodoLista(name);
        insertarFinal(nuevoName);
        }
    }    

    /**
     * 
     * @param name
     * @return Busca el nombre de la lista, y si lo encuentra, devuelve el nodo que contiene dicho nombre
     */
  
    public NodoLista search(String name){
        NodoLista nodo = getpFirst();
        while  (nodo != null && !nodo.getUsuario().equals(name) ){
            nodo = nodo.getSiguiente();}    
        return nodo;
    }
    
    /**
     * 
     * @param name 
     * Busca el nodo que contiene el nombre a buscar, sea nulo, no borra nada, si lo consigue, aux se conecta al que le sigue al que, queremos eliminar. 
     */
    
    public void delete( String name){
        NodoLista eliminado = search(name);
        if(eliminado != null){         
            NodoLista aux = getpFirst();
            while (aux != null && aux.getSiguiente() != eliminado ){
                aux = aux.getSiguiente();
            }
            if (aux != null){
              aux.setSiguiente(eliminado.getSiguiente());
            }
        }     
    }
    
    
}
