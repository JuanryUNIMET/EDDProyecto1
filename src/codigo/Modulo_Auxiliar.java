package codigo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;


public class Modulo_Auxiliar {
    
    public Modulo_Auxiliar(){
    }
    
    /**
     * 
     * @param archivo
     * @return grafo
     */
    
    public Grafo leerArchivo(String archivo){
        Grafo grafo = new Grafo(1);
        String linea;
        String datos_txt = "";
        String path = archivo;
        int estado = 1;
        File file = new File(path);
        String lineas[]; 
        try{
            if (!file.exists()){
                file.createNewFile();
            }else{
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                while((linea = br.readLine())!= null){
                    if(!linea.isEmpty() && !linea.equals("usuarios")){
                        if(linea.equals("relaciones")){
                            estado =2;
                        }else if (estado == 1){
                            grafo.crearCuenta(linea);
                        }else if (estado ==2){
                            linea = linea.replace(",", "");
                            lineas = linea.split(" ");
                            grafo.seguir(lineas[0], lineas[1]);
 
                        }                      
                    }
                }
                br.close();
                JOptionPane.showMessageDialog(null, "Se ha leido el archivo");
            }
        }catch( Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }

        return grafo;
    }
    public void escribirArchivo(Grafo graph,String archivo){
        String path = archivo;
        File file = new File(path);
        try{
            if (!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            pw.write("usuarios" + "\n");
            for (int i = 0; i < graph.getCapacidad(); i++) {
                try{
                    pw.write(graph.getListaAdUsers()[i].getpFirst().getUsuario() + "\n");

                }catch (Exception e){
                    System.out.println("En " + i + " no hay usuario registrado");
                }
            }
            pw.write("relaciones");
            for (int i = 0; i < graph.getCapacidad(); i++) {
                NodoLista seguidor = graph.getListaAdUsers()[i].getpFirst();
                while(seguidor.getSiguiente() != null){
                    pw.write("\n" + graph.getListaAdUsers()[i].getpFirst().getUsuario() + ", " + seguidor.getSiguiente().getUsuario());
                    seguidor = seguidor.getSiguiente();
                }
            }
            pw.close();
        }catch( Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    /**
     * 
     * @param grafo 
     * Creamos un objeto Graph y le insertamos los nodos y relaciones de nuestro grafo
     * Buscamos los nodos fuermetente conectados y cambiamos el color de la arista si ambos nodos estan fuertemente enlazados
     */
    public void graficar_grafo(Grafo grafo){
    Graph graph = new SingleGraph("MyGraph");
    graph.setAttribute("ui.stylesheet", "node { shape: circle; fill-color: #ffbdbd; text-color: #000000; size: 40px; } "
            + " edge { size: 2.5px; shape: line; fill-color: #000000; }");
    for (int i = 0; i < grafo.getCapacidad(); i++) {
        String username = grafo.getListaAdUsers()[i].getpFirst().getUsuario();
        Node user = graph.addNode(username);
        user.setAttribute("ui.label", username);
    }

    for (int i = 0; i < grafo.getCapacidad(); i++) {
        String source = grafo.getListaAdUsers()[i].getpFirst().getUsuario();
        NodoLista currentUser = grafo.getListaAdUsers()[i].getpFirst().getSiguiente();
        while (currentUser != null) {
            String target = currentUser.getUsuario();
            Edge edge = graph.addEdge(source + "-" + target, source, target, true);
            edge.setAttribute("ui.style", "fill-color: black;");
            currentUser = currentUser.getSiguiente();
        }
    }


    ListaDe0[] components = alogritmo_de_kosaraju(grafo);
    for (int i = 0; i < components.length; i++) {
        ListaDe0 component = components[i];
        if (component != null) {
            NodoLista currentNode = component.getpFirst();
            NodoLista nextNode = component.getpFirst().getSiguiente();
            while (nextNode != null) {
                String source = currentNode.getUsuario();
                String target = nextNode.getUsuario();
                Edge edge = graph.getEdge(source + "-" + target);
                if (edge != null) {
                    edge.setAttribute("ui.style", "fill-color: #f80000;");
                }
                currentNode = nextNode;
                nextNode = nextNode.getSiguiente();
            }
            String source = currentNode.getUsuario();
            String target = component.getpFirst().getUsuario();
            Edge edge = graph.getEdge(source + "-" + target);
            if (edge != null) {
                edge.setAttribute("ui.style", "fill-color: #000080;");
            }
        }
    }
    System.setProperty("org.graphstream.ui", "org.graphstream.ui.swing");
    Viewer viewer = graph.display();
}

  
public Grafo invertir_grafo(Grafo grafo) {
    Grafo grafo_inv = new Grafo(grafo.getCapacidad());
    for (int i = 0; i < grafo.getCapacidad(); i++) {
        grafo_inv.getListaAdUsers()[i] = new ListaDe0();
    }
    for (int i = 0; i < grafo.getCapacidad(); i++) {
        ListaDe0 l = grafo.getListaAdUsers()[i];

        NodoLista currentUser = l.getpFirst();
        while (currentUser != null) {
            String usuario = currentUser.getUsuario();
            int index = indice(usuario, grafo);
            grafo_inv.getListaAdUsers()[index].insert(grafo.getListaAdUsers()[i].getpFirst().getUsuario());

            currentUser = currentUser.getSiguiente();
        }
    }

    return grafo_inv;
}

/**
 * 
 * @param grafo
 * @return recorremos el grafo usando el dfs y vamos gruardando los nodos fuertemente conectados en el arreglo de listas simples components 
 */
public ListaDe0[] alogritmo_de_kosaraju(Grafo grafo) {
    ListaDe0 order = new ListaDe0();
    boolean[] visited = new boolean[grafo.getCapacidad()];

    for (int i = 0; i < grafo.getCapacidad(); i++) {
        if (!visited[i]) {
            dfs(i, visited, order,grafo);
        }
    }

    Grafo invertido = invertir_grafo(grafo);

    visited = new boolean[grafo.getCapacidad()];
    int numComponents = 0;
    ListaDe0[] componentes = new ListaDe0[grafo.getCapacidad()];

    NodoLista currentUser = order.getpFirst();
    while (currentUser != null) {
        int index = indice(currentUser.getUsuario(), grafo);
        if (!visited[index]) {
            componentes[numComponents] = new ListaDe0();
            dfs(index, visited, componentes[numComponents], invertido);
            numComponents++;
        }
        currentUser = currentUser.getSiguiente();
    }

    return componentes;
}

/**
 * 
 * @param userIndex
 * @param visited
 * @param order
 * @param grafo 
 * recorremos el grafo en profundidad y visitamos los nodos que no han sido visitados
 */
public void dfs(int userIndex, boolean[] visited, ListaDe0 order, Grafo grafo) {
    visited[userIndex] = true;
    ListaDe0 userList = grafo.getListaAdUsers()[userIndex];

    NodoLista currentUser = userList.getpFirst();
    while (currentUser != null) {
        int neighborIndex = indice(currentUser.getUsuario(),grafo);
        if (neighborIndex != -1 && !visited[neighborIndex]) {
            dfs(neighborIndex, visited, order,grafo);
        }
        currentUser = currentUser.getSiguiente();
    }

    order.insert(userList.getpFirst().getUsuario());
}

    public int indice(String userName, Grafo grafo) {
        int index = -1;
        for (int i = 0; i < grafo.getCapacidad(); i++) {
            if(grafo.getListaAdUsers()[i] != null && grafo.getListaAdUsers()[i].getpFirst() != null && grafo.getListaAdUsers()[i].getpFirst().getUsuario().equals(userName)){
                index = i;
                break;
            }
        }

        return index; 
    }

    public String corregir(String nombre){
            if(!nombre.contains("@")){
                nombre ="@"+nombre; 
            }
            nombre = nombre.replace(" ", "");
            
            return nombre;
        }


        
}
