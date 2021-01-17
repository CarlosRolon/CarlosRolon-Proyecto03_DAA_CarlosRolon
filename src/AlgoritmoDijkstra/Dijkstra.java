
package AlgoritmoDijkstra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos Rolon
 */
public class Dijkstra {
    
    public static  HashMap<String,HashMap<String,Integer>>  Algoritmo(  HashMap<Integer,HashMap<Integer,Integer>> grafo ,int nodoInicial){
        
        List<Integer> nodosVisitados = new ArrayList<>();
        HashMap<Integer,Integer> verticesNodo = new  HashMap<>();
        HashMap<Integer,Integer> conexiones = new  HashMap<>();
        int nodoActual , nodo , d  , distanciaNodoActual;      
        HashMap<Integer, Integer> distancias = new  HashMap<>();
        HashMap<Integer, Integer> distanciasFinales = new  HashMap<>();
        
        // Inicializa las distancias en infinito
        int infinito = (int) Float.POSITIVE_INFINITY; 
        for (Map.Entry n : grafo.entrySet()) {
            nodoActual = (Integer) n.getKey();
            distancias.put( nodoActual , infinito);
            distanciasFinales.put( nodoActual , infinito);
            conexiones.put(nodoActual, nodoActual);
        }
        
        // Inicializa el algoritmo con el nodo inicial
        distancias.put( nodoInicial , 0);
        distanciasFinales.put( nodoInicial , 0);
        conexiones.remove(nodoInicial);
        
        while(distancias.size() > 0){
            // Obtiene el nodo de prioridad
            nodoActual = obtenerNodoPrioridad( distancias );
            // Obtiene la distancia actual al nodo de prioridad
            distanciaNodoActual = distancias.get(nodoActual);
            // Actualiza la distancia final
            distanciasFinales.put( nodoActual , distanciaNodoActual);
            // Agrega el nodo actual como visitado
            nodosVisitados.add(nodoActual);
            // Elimina el nodo actual de la lista por visitar
            distancias.remove(nodoActual);
            
            // Obtiene las conexiones del nodo actual
            verticesNodo = grafo.get(nodoActual);
            for (Map.Entry vertice : verticesNodo.entrySet()) {
                // Obtiene el nodo al que esta conectado el nodo actual
                nodo = (int)vertice.getKey();
                // Obtiene la distancia entre los nodos
                d = distanciaNodoActual + (int)vertice.getValue();
                // Verifica que si el nodo destino no esta visitado
                if (!nodosVisitados.contains(nodo)) {
                    // Verifica que la distancia es menor 
                    if ( d < distancias.get(nodo)) {
                        // Actualiza las distancias
                        distancias.put( nodo ,  d  );
                        conexiones.put(nodo, nodoActual);
                    }
                }
            }
        }
        
       HashMap<String,HashMap<String,Integer>> arbol = GeneralArbolDikstra_S( distanciasFinales , conexiones , grafo );
       return arbol;
        
    }
    
    private static Integer obtenerNodoPrioridad(HashMap<Integer, Integer> distancias ){
        Map.Entry<Integer, Integer> primerElemento = distancias.entrySet().iterator().next();
        int menorDistancia = primerElemento.getValue();
        int nodoCercano  =  primerElemento.getKey();
        for (Map.Entry nodo : distancias.entrySet()) {
            if ((int)nodo.getValue() < menorDistancia) {
                menorDistancia = (int)nodo.getValue();
                nodoCercano  =  (int)nodo.getKey();
            }
        }
        return nodoCercano;
    }
    
    
    private static HashMap<String,HashMap<String,Integer>> GeneralArbolDikstra_S( HashMap<Integer, Integer> distanciasFinales ,  
            HashMap<Integer,Integer> conexiones  , HashMap<Integer,HashMap<Integer,Integer>> grafo ){
        
        HashMap<String,HashMap<String,Integer>> arbol = new HashMap<>();
        int nodoOrigen, nodoDestino , distancia;
        String nodoOrigen_s, nodoDestino_s ;
         
        for (Map.Entry conexion : conexiones.entrySet()) {
            nodoOrigen = (int)conexion.getValue();
            HashMap<String,Integer> nodoArbol = new HashMap<>();
            distancia = distanciasFinales.get(nodoOrigen);
            nodoOrigen_s = "Nodo_" + nodoOrigen + "(" + distancia + ")" ;
            arbol.put(nodoOrigen_s,nodoArbol); 
        }
         
        for (Map.Entry conexion : conexiones.entrySet()) {
            nodoOrigen = (int)conexion.getValue();
            nodoDestino = (int)conexion.getKey();
            
            distancia = distanciasFinales.get(nodoOrigen);
            nodoOrigen_s = "Nodo_" + nodoOrigen + "(" + distancia + ")" ;
            distancia = distanciasFinales.get(nodoDestino);
            nodoDestino_s = "Nodo_" + nodoDestino + "(" + distancia + ")" ;
                  
            HashMap<Integer,Integer> nodo = grafo.get(nodoOrigen);           
            distancia = nodo.get(nodoDestino);
            
            HashMap<String,Integer> nodoArbol = arbol.get(nodoOrigen_s);
            nodoArbol.put( nodoDestino_s , distancia );
        } 
        return arbol;
    }
    
    
    public static void GrafoPesosaArchivo( HashMap<String, HashMap<String,Integer>> grafo , String rutaArch) throws IOException
    {   
        // Iniciliza el archivo
        String nodoF ;
        int peso;
        File output = new File(rutaArch);
        FileWriter writer = new FileWriter(output);
        // Inicializa el grafo
        writer.write("digraph { \n");
        //Recorre el grafo
        for(Map.Entry<String,  HashMap<String,Integer> > entry : grafo.entrySet()) {
            
            String key = entry.getKey();
            HashMap<String,Integer> value = entry.getValue();
            
            for (Map.Entry nodo : value.entrySet()) {
                nodoF = (String) nodo.getKey();
                peso = (int)nodo.getValue();
                 writer.write( key  + " -> " +   
                                nodoF +  "  [ label = \"" + peso  + "\" ]; \n");
            }
            
        }
        //Cierra el grafo
        writer.write("}");
        // Cierra el archivo
        writer.flush();
        writer.close();        
    } 
}
