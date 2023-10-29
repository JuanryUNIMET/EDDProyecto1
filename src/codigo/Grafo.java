/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;

import javax.swing.JOptionPane;

public class Grafo {
    private int capacidad;
    private ListaDe0[] listaAdUsers;
    
    
    public Grafo(int max_u){
        listaAdUsers = new ListaDe0[max_u];
        capacidad = max_u; 
    }
        /**
     * @return the size
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * @param max the size to set
     */
    public void setCapacidad(int max) {
        this.capacidad = max;
    }

    /**
     * @return the base_de_datos_usuarios
     */
    public ListaDe0[] getListaAdUsers() {
        return listaAdUsers;
    }

    /**
     * @param list_users the base_de_datos_usuarios to set
     */
    public void setListaAdUsers(ListaDe0[] list_users) {
        this.listaAdUsers = list_users;
    }
    //Verificamos que el nombre no este vacio ni ya exista. Buscamos un espacio vacio en la lista de adyacencia para insertar el nuevo usuario
    //Si no hay espacios disponibles, creamos otra lista de adyacencia igual a la original pero con un espacio más para nuestro nuevo usuario
    public void crearCuenta(String name){
        if(this.buscarCuenta(name)==null && !name.equals("@")){
            boolean espacioVacio = false;
            for (int i = 0; i < getCapacidad(); i++) {
                if (getListaAdUsers()[i] == null){
                    getListaAdUsers()[i] = new ListaDe0();
                    getListaAdUsers()[i].insertarInicio(name);
                    espacioVacio = true;
                    break;
                }
            }
            if (!espacioVacio){
                ListaDe0[] nueva = new ListaDe0[getCapacidad() +1];
                for (int i = 0; i < getCapacidad() +1; i++) {
                    if (i != getCapacidad()){
                        nueva[i] = getListaAdUsers()[i] ;
                    }else{
                        nueva[i]= new ListaDe0();
                        nueva[i].insertarInicio(name);
                    }
                }
                this.setListaAdUsers(nueva);
                setCapacidad(getCapacidad() + 1);
        }}else{
            JOptionPane.showMessageDialog(null, "Usuario ya Existente");

        }
    }
    
    //Buscamos un par de nodos en la lista de usuarios, y si los encontramos a ambos devolvemos la posicion del primero en la lista de adyacencias
    public int searchNodes(String origen, String destino){
        boolean origenEncontrado = false;
        boolean destinoEncontrado = false;
        int posicion = 0;
        for (int i = 0; i < getCapacidad(); i++) {
            if (destinoEncontrado && origenEncontrado){
                break;
            }
            if (getListaAdUsers()[i].getpFirst().getUsuario().equals(destino)){
                destinoEncontrado = true;
                posicion = i;
            }else if (getListaAdUsers()[i].getpFirst().getUsuario().equals(origen)){
                origenEncontrado = true;
            }          
        }
        if (destinoEncontrado && origenEncontrado){
            return posicion;
        }else{
            return -1;
        }
    }
    //Agregamos al primer usuario a la lista de adyacencia del segundo, si ambos existen
    public void seguir(String origen, String destino){
        int encontrados = searchNodes(origen,destino);
        if (encontrados!= -1){
            getListaAdUsers()[encontrados].insert(origen);
        }
    }
    //Eliminamos al primer usuario de la lista de adyacencia del segundo, si ambos existen
    public void dejarSeguir(String origen, String destino){
        int encontrados = searchNodes(origen,destino);
        if (encontrados!= -1){       
            getListaAdUsers()[encontrados].delete(origen);
        }
    }
    
    //Creamos una nueva lista de adyacencia copiando la original pero sin agregar al usuario que queremos eliminar. Copiamos cada lista de adyacencia de cada usuario
    //y eliminamos de esta al usuario que queremos borrar, si es que está en dicha lista.
    public void eliminarCuenta(String nombre){
        if(buscarCuenta(nombre)!= null){
        ListaDe0[] nueva = new ListaDe0[getCapacidad()-1];
        int index =0;
         for (int i = 0; i < getCapacidad() ; i++) {
                if (!listaAdUsers[i].getpFirst().getUsuario().equals(nombre)){
                    nueva[index] = getListaAdUsers()[i];
                    nueva[index].delete(nombre);
                    index++;
                }
         }
            setListaAdUsers(nueva);
            setCapacidad(getCapacidad() - 1);
        }
    }

    //Buscamos un nodo en la lista de adyacencia por su usuario
   public NodoLista buscarCuenta(String name){
            for (int i = 0; i < getCapacidad(); i++) { 
                if(this.getListaAdUsers()[i]!= null){
                    if (this.getListaAdUsers()[i].getpFirst().getUsuario().equals(name)){
                        return getListaAdUsers()[i].getpFirst();
                    }}
            }

            return null;
        }   
   
    //Retornamos un string con usuarios y relaciones
    public String show(){
        String grafo = "usuarios \n";
        for (int i = 0; i < getCapacidad(); i++) {
            if (getListaAdUsers()[i]!= null){
            grafo += (this.getListaAdUsers()[i].getpFirst().getUsuario()) + "\n";   
        }}
        grafo += "relaciones \n";
        for (int i = 0; i < getCapacidad(); i++) {
            if (getListaAdUsers()[i]!= null){
            grafo += (this.getListaAdUsers()[i].getpFirst().getUsuario() +" Sigue a: ");
            NodoLista aux = this.getListaAdUsers()[i].getpFirst().getSiguiente();
            if(aux == null)
                grafo += ("nadie");
            while(aux!= null){
                grafo += aux.getUsuario() + ", ";
                aux = aux.getSiguiente();
            }
            grafo += "\n";
        }}
        return grafo;
    }


    

}
    
    
