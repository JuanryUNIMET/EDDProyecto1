/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;


public class NodoLista {
    private String nombre_de_usuario ;
    private NodoLista pNext;
    
    public NodoLista (String nombre){
        nombre_de_usuario = nombre;
        pNext = null;
    }

    /**
     * @return the nombre_de_usuario
     */
    public String getUsuario() {
        return nombre_de_usuario;
    }

    /**
     * @param usuario the nombre_de_usuario to set
     */
    public void setUsuario(String usuario) {
        this.nombre_de_usuario = usuario;
    }

    /**
     * @return the pNext
     */
    public NodoLista getSiguiente() {
        return pNext;
    }

    /**
     * @param siguiente the pNext to set
     */
    public void setSiguiente(NodoLista siguiente) {
        this.pNext = siguiente;
    }
    
}
