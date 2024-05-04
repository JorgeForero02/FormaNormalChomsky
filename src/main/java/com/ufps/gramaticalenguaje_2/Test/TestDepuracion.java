/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.gramaticalenguaje_2.Test;

import com.ufps.gramaticalenguaje_2.Model.Gramatica;
import com.ufps.gramaticalenguaje_2.Util.seed.ListaCD;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author juan_
 */
public class TestDepuracion {
    
    public static void main(String[] args) {
        
        Gramatica g = new Gramatica("ejemplo");
    
        g.agregarNoTerminales("S,A,B,C,D,E,F");

        g.agregarTerminales("a,b,c,d");
        
        g.agregarProducciones('S', "A/BCa/aDcd/EDF");
        g.agregarProducciones('A', "aAb/c");
        g.agregarProducciones('B', "CD/b/ECd/Ad");
        g.agregarProducciones('C', "Cc/Bb/AaE/λ");
        g.agregarProducciones('D', "aDd/Dd/λ");
        g.agregarProducciones('E', "aaEB/EFG");
        g.agregarProducciones('F', "aFd/d");
        
        g.setVariableInicial('S');
        
        g.variablesGeneradoras();
        g.comprobarInutiles();
        //g.comprobarProducciones();
        g.comprobarInalcanzables();
        
        boolean cond1;
        boolean cond2;
        
        do
        {
        
            cond1 = g.eliminandoUnitarias();
            cond2 = g.eliminandoVacios();
        
        }while(cond1 && cond2);
        
        System.out.println(g.getSigmas().toString());
        
       
        for(HashMap.Entry<Character,ListaCD<String>> entry :g.getProducciones().entrySet())
        {
        
            System.out.println(entry.getKey() + "-->" + entry.getValue().toString());
        
        }
        
        
        
    }
    
    
    
}
