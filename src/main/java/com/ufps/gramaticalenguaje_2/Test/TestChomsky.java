/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.gramaticalenguaje_2.Test;

import com.ufps.gramaticalenguaje_2.Model.FormaNormalChomsky;
import com.ufps.gramaticalenguaje_2.Model.Gramatica;

/**
 *
 * @author juan_
 */
public class TestChomsky {
    
    public static void main(String[] args) {
        Gramatica g = new Gramatica("ejemplo");
    
        g.agregarNoTerminales("A,B,C,D");

        g.agregarTerminales("1,2,3");
        
        g.agregarProducciones('A', "CBA/1");
        g.agregarProducciones('B', "ABA/D2");
        g.agregarProducciones('C', "BCABA/B2CBA/2/3");
        g.agregarProducciones('D', "1");
        
        g.setVariableInicial('B');
        
        FormaNormalChomsky FNC = new FormaNormalChomsky(g);
        
        //FNC.formaChomsky();
        
        System.out.println(FNC.getProducciones().toString());
        
    }
    
}
