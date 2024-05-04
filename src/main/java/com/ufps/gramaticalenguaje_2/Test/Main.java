/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.gramaticalenguaje_2.Test;

import com.ufps.gramaticalenguaje_2.Model.FormaNormalChomsky;
import com.ufps.gramaticalenguaje_2.Model.Gramatica;

/**
 *
 * @author forer
 */
public class Main {

    public static void main(String[] args) {

        Gramatica gramatica1 = new Gramatica("G1");
        FormaNormalChomsky chomsky = new FormaNormalChomsky(gramatica1);
        //ChomskyBonito chomsky1 = new ChomskyBonito();
        
        gramatica1.agregarTerminales("a,b,c,d");
        gramatica1.agregarNoTerminales("A,B,C,D,S");
        gramatica1.setVariableInicial('S');
        gramatica1.agregarProducciones('S', "aAb/c/BCa/aAb/Ba/Ca/a/aDcd/acd");
        gramatica1.agregarProducciones('A', "aAb/c");
        gramatica1.agregarProducciones('B', "CD/Cc/c/Bb/b/aDd/ad/Dd/d/Ad");
        gramatica1.agregarProducciones('C', "Cc/c/Bb/b");
        gramatica1.agregarProducciones('D', "aDd/ad/Dd/d");
        
        /*gramatica1.agregarTerminales("1,2,3");
        gramatica1.agregarNoTerminales("A,B,C,D,E");
        gramatica1.setVariableInicial('B');
        gramatica1.agregarProducciones('A', "2BA/32");
        gramatica1.agregarProducciones('B', "ABA/D2/2/*D1/TCD");
        gramatica1.agregarProducciones('C', "BA/B2/B*2/2/3/#AB");
        gramatica1.agregarProducciones('D', "B*A*D/1");
        gramatica1.agregarProducciones('E', "BC/CD1/1");*/
        
        
        System.out.println("Gramatica antes de modificar:");
        gramatica1.imprimirGramatica();
        System.out.println();
        
        /*for (String s : gramatica1.comprobarInutiles()) {
            System.out.println(s);
        }*/
        
        gramatica1.comprobarInalcanzables();

        System.out.println();
        
        
        System.out.println("Gramatica despues de depurar:");
        gramatica1.imprimirGramatica();
        System.out.println();
        
        System.out.println("Gramatica pasada a chomsky");
        //chomsky.moverGramatica();
        
        //chomsky.formaChomsky();
        //chomsky.imprimirGramatica();
        //chomsky.reemplazarTerminales();
        chomsky.imprimirGramatica();
        
        chomsky.generarPalabras(10);
        
        //ArbolBinario a = new ArbolBinario(chomsky.getVariableInicial(), chomsky.getVariablesTerminales(), chomsky.getVariablesNoTerminales(), chomsky.getProducciones()); 
        
        //System.out.println(a.construccionArbol());
        
        //chomsky.generarPalabras(30);
        //ArbolBinario a = new ArbolBinario(chomsky);
        //ArbolDerivacion a = new  ArbolDerivacion(chomsky.getVariableInicial(), chomsky.getVariablesTerminales(), chomsky.getVariablesNoTerminales(), chomsky.getProducciones(), 100);
        //System.out.println(a.construccionArbolDerivacion());
    }
}
