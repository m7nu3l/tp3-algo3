package uba.algo3.tp3.ejercicio3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import uba.algo3.tp3.ejercicio1.GrafoMaterias;
import uba.algo3.tp3.ejercicio1.Nodo;

public class ListColoringGoloso {

	public static ArrayList<Nodo> OrdenarPorColores(GrafoMaterias g)
	{
		ArrayList<Nodo> ordenados = new ArrayList<Nodo>(g.getGrafo());
		Collections.sort(ordenados);
		return ordenados;
	}
	
	//modifica el grafo y me lo devuelve con todos los nodos de un color
	public static void Goloso(GrafoMaterias g, boolean random)
	{		
		ArrayList<Nodo> ordenados = OrdenarPorColores(g); 
		
		for(Nodo n : ordenados) 
		{
			Double[] valoresPorColor = new Double[n.cantColores()]; //O(c)
			
			for(int j = 0; j < valoresPorColor.length; j++)
				valoresPorColor[j] = 0.0;
			
			for(Integer vecino : n.getVecinos())
			{
				int i = 0;
				
				for(Integer c : n.getColores())
				{
					// si fue pintado
					if(g.getNodo(vecino).getColorPintado()!=-1){
						
						if(c == g.getNodo(vecino).getColorPintado())
							valoresPorColor[i] += 1.0;
						
					}else{
						for(Integer colorVecino : g.getNodo(vecino).getColores())
						{
							if(c == colorVecino)
								valoresPorColor[i] += 1.0/g.getNodo(vecino).cantColores();
							
						}
					}
					i++;
				}
				
			}
			
			
			// busco el que tenga valor minimo, porque es el que afecta menos a los demas (tienen mas opciones para elegir)
			
			Double min = valoresPorColor[0];
			int idx = 0;
			for(int j = 1; j < valoresPorColor.length; j++)
			{
				if(valoresPorColor[j] < min)
				{
					min = valoresPorColor[j];
					idx = j;
				}
			}
			
			Integer resultado = n.getColores().get(idx);
			if (random)
			{
				ArrayList<Integer> minimos = new ArrayList<Integer>(n.getColores().size());
				
				for(int j = 0; j < valoresPorColor.length; j++)
				{
					if(valoresPorColor[j].equals(min))
						minimos.add(n.getColores().get(j));
				}
				
				Random rand = new Random();	
				Integer randIdx = rand.nextInt(minimos.size());
				resultado = minimos.get(randIdx);
			}
			n.setColorPintado(resultado);
		}
		
	}
	
	//devuelve la cantidad de conflictos
	public static LinkedList<Integer[]> solve(GrafoMaterias g)
	{
		LinkedList<Integer[]> conflictivas = new LinkedList<Integer[]>();
		Goloso(g, false); 
		//Ahora el grafo tiene solo un color en cada nodo
		Boolean[] visitados = new Boolean[g.getSize()]; 
		Boolean[] conflictos = new Boolean[g.getSize()];
		
		Queue<Integer> cola = new LinkedList<Integer>(); //En la cola se guarda el index de cada nodo del grafo
		for(int j = 0; j < visitados.length; j++){
			visitados[j] = false;
			conflictos[j] = false;
		}

		for (int k = 0; k < visitados.length; k++)
		{
			if (visitados[k])
				continue;
			visitados[k] = true;
			cola.add(k);
			
			// El ciclo no pushea dos veces el mismo nodo
			// O(#nodos)
			while(!cola.isEmpty())
			{//Mientras haya vecinos sigo recorriendo
				Integer idxNodo = cola.poll(); //index del nodo en el grafo materias
				visitados[idxNodo] = true;
				// O(#nodos)
				for(Integer vecino : g.getNodo(idxNodo).getVecinos())//recorro los vecinos del nodo en el grafo
				{
					if (!conflictos[vecino])
					{
						if(g.getNodo(idxNodo).getColorPintado() == g.getNodo(vecino).getColorPintado()) //hay conflicto
						{	
							Integer[] tupla = {idxNodo,vecino};
							conflictivas.add(tupla);
						}
					}
					
					if (!visitados[vecino])
					{
						cola.add(vecino);
						visitados[vecino] = true;
					}
				}
				
				conflictos[idxNodo] = true;
			}
			
		}
		return conflictivas;
	}
}
