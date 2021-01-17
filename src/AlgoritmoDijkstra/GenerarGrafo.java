/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoritmoDijkstra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos Rolon
 */
public class GenerarGrafo {
    
 static int maximoPeso = 100;
    
 
    
    public static HashMap<Integer,HashMap<Integer, Integer>> ErdosRenyi_Peso(String rutaArch, int n , int m) throws IOException
    {
        HashMap<Integer,HashMap<Integer, Integer>> grafo =  Metodo_ErdosRenyi_Peso( n , m );
        GrafoPesosaArchivo(grafo , rutaArch);
        return grafo;
    } 
    
     public static HashMap<Integer,HashMap<Integer, Integer>> Barabasi_Alberti_Peso(String rutaArch, int n , int m) throws IOException
    {
        HashMap<Integer,HashMap<Integer, Integer>> grafo =  Metodo_Barabasi_Albert_P( n , m );
        GrafoPesosaArchivo(grafo , rutaArch);
        return grafo;
    } 
        
   // Metodos de creacion de grafos
  
     
     public static  HashMap<Integer,HashMap<Integer, Integer>>  Metodo_Barabasi_Albert_P(int n , int d ){
        HashMap<Integer,HashMap<Integer,Integer>> grafo = new HashMap<>();
        double probNodo,probRandom ;
        double nV = 0 , grado;
        int peso;
        HashMap<Integer,Integer> vertices;

         // Genera Nodos Iniciales
        for (int i = 0; i < d; i++) {
            HashMap<Integer,Integer> ini = new HashMap<>();            
            
            
            for (int j = 0; j < d; j++) {
                if ( i != j){
                    peso = (int)(Math.random()*maximoPeso);
                    ini.put(j , peso);
                } 
            }
            grafo.put(i,ini);
        }              
        // Genera Conexiones 
        for (int i = d; i < n ; i++) {
            // Agrega el nodo nuevo
            HashMap<Integer,Integer> ini  = new HashMap<>();
            grafo.put(i,ini);          
            nV = 0;            
            // Busca conectarse
            for (int j = 0 ; j < i && nV < d ; j++) {
                vertices = grafo.get(j);
                grado  = vertices.size();                
                // Probabilidad del nodo
                probNodo = 1 - (grado/ d);                 
                // Probabilidad alteatoria
                probRandom = Math.random();    
                // Verifica que este dentro de la probablidad
                if (probNodo >= probRandom )
                {                    
                    peso = (int)(Math.random()*maximoPeso);
                    HashMap<Integer,Integer> num1 = grafo.get(j);

                    num1.put(i, peso);

                    nV++;
                }
            }
        }
        return grafo;
    }  
     

    public static  HashMap<Integer,HashMap<Integer,Integer>>  Metodo_ErdosRenyi_Peso(int n , int m ){
        HashMap<Integer,HashMap<Integer,Integer>> grafo = new HashMap<>();
        int numero1 , numero2 , peso ;
        //Genera Nodos 
        for (int i = 0; i < n; i++) {
            HashMap<Integer,Integer> x = new HashMap<>();
            grafo.put(i,x);
        }        
        // Genera Conexiones 
        for (int i = 0; i < m; i++) {
            numero1 = (int)(Math.random()*n);
            numero2 = (int)(Math.random()*n);
            
            if(numero1 == numero2)
                continue;
            
            HashMap<Integer,Integer> num1 = grafo.get(numero1);
            HashMap<Integer,Integer> num2 = grafo.get(numero2);
            peso = (int)(Math.random()*maximoPeso);
            num1.put(numero2,peso);
        }
        return grafo;
    }
    
    
    
    //Metodo para escribir el grafo en un archivo 
    public static void GrafoPesosaArchivo( HashMap<Integer, HashMap<Integer,Integer>> grafo , String rutaArch) throws IOException
    {   
        // Iniciliza el archivo
        int nodoF ;
        int peso;
        File output = new File(rutaArch);
        FileWriter writer = new FileWriter(output);
        // Inicializa el grafo
        writer.write("digraph { \n");
        //Recorre el grafo
        for(Map.Entry<Integer,  HashMap<Integer,Integer> > entry : grafo.entrySet()) {
            
            Integer key = entry.getKey();
            HashMap<Integer,Integer> value = entry.getValue();
            //Escribe cada conexion

            for (Map.Entry nodo : value.entrySet()) {
                nodoF = (int)nodo.getKey();
                peso = (int)nodo.getValue();
                 writer.write(Integer.toString(key)  + " -> " +   
                                nodoF +  "  [ label = \""+ peso  +"\" ]; \n");
        
            }
            
        }
        //Cierra el grafo
        writer.write("}");
        // Cierra el archivo
        writer.flush();
        writer.close();
        
    }
    
    
   
    
}

