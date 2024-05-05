/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.ufps.gramaticalenguaje_2.Model;

import com.ufps.gramaticalenguaje_2.Util.seed.ListaCD;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Juan David Ortiz Cano - 1152298
 * @author Jorge Andres Forero Serrano - 1152328
 */
public class GestorDeGramaticas {
    private ListaCD<Gramatica> gramaticas;

    public GestorDeGramaticas() {
        this.gramaticas = new ListaCD<>();
    }

    public boolean insertarGramatica(String nombre) {
        
        Gramatica temp = new Gramatica(nombre);
 
        if(this.gramaticas.containTo(temp))
        {
        
            throw new RuntimeException("Ya existe una gramática con este nombre, por favor ingresa uno diferente o cambiar el existente.");
        
        }
        
        if(nombre == null || nombre.isEmpty() || nombre.isBlank())
        {
        
            throw new RuntimeException("Entrada vacía.");
        
        }
        
        this.gramaticas.insertarFinal(temp);
        boolean agregado = true;
        
        return agregado;
        
    }
   
    public boolean eliminarGramatica(Gramatica gramatica)
    {
    
        Gramatica temp = this.gramaticas.removeItem(gramatica);
        
        if(temp == null)
        {
        
            throw new RuntimeException("Elemento no encontrado");
        
        }
        
        boolean eliminado = true;
        
        return eliminado;
    
    }
    
    public boolean insertarTerminales(Gramatica gramatica, String variables)
    {
    
        boolean agregado = gramatica.agregarTerminales(variables);
    
        return agregado;
        
    }
    
    public boolean insertarNoTerminales(Gramatica gramatica, String variables)
    {
    
        boolean agregado = gramatica.agregarNoTerminales(variables);
    
        return agregado;
        
    }

    public boolean insertarProducciones(Gramatica gramatica, char noTerminal, String producciones)
    {
    
        return gramatica.agregarProducciones(noTerminal, producciones);
        
    }
    
    public boolean definirInicial(Gramatica gramatica, char noTerminal)
    {
    
        boolean cambiado = false;
        gramatica.setVariableInicial(noTerminal);
        
        return cambiado;
    
    }
    
    public boolean eliminarTerminal(Gramatica gramatica, char terminal)
    {
    
        return gramatica.eliminarTerminal(terminal);
    
    }
   
    public boolean eliminarNoTerminal(Gramatica gramatica, char noTerminal)
    {
    
        return gramatica.eliminarNoTerminal(noTerminal);
    
    }
    
    public boolean eliminarProducciones(Gramatica gramatica, char noTerminal, String produccion)
    {
    
        return gramatica.eliminarProduccion(noTerminal, produccion);
    
    }
    
    public boolean modificarTerminal(Gramatica gramatica, char terminal, char nuevaTerminal)
    {
    
        return gramatica.modificarVariableTerminal(terminal, nuevaTerminal);
    
    } 
    
    public boolean modificarProduccion(Gramatica gramatica, char terminal, String produccion, String nuevaProduccion)
    {
    
        return gramatica.modificarProduccion(terminal, produccion, nuevaProduccion);
    
    } 
 
    public boolean modificarNoTerminal(Gramatica gramatica, char noTerminal, char nuevaNoTerminal)
    {
    
        return gramatica.modificarVariableNoTerminal(noTerminal, nuevaNoTerminal);
    
    } 
    
    public ListaCD<String> comprobaciones(Gramatica gramatica)
    {
        try{
            
            if(gramatica.getSigmas().getSize() != 0)
            {

                ListaCD<String> nueva = new ListaCD();
                gramatica.setSigmas(nueva);

            }

            gramatica.variablesGeneradoras();
            gramatica.comprobarInutiles();
            gramatica.comprobarProducciones();
            gramatica.comprobarInalcanzables();

            boolean cond1;
            boolean cond2;
            int ciclos = 0;

            do
            {

                cond1 = gramatica.eliminandoUnitarias();
                cond2 = gramatica.eliminandoVacios();
                ciclos++;

            }while(cond1 && cond2 && ciclos < 100);

            if(ciclos == 100)
            {

                throw new RuntimeException("Gramatica ambigua por favor verificarla o ingresar otra.");

            }

            ListaCD<String> aux = gramatica.getSigmas();
            aux.insertarFinal(gramatica.toFormalizado());
            gramatica.setFNC(new FormaNormalChomsky(gramatica));

            return aux;
            
        }catch(Exception e)
        {
        
            throw new RuntimeException("Ha ocurrido un error durante la depuración por favor asegurese de ingresar todos los valores necesarios.");
        
        }
        
    }
    
    public Set<String> generarPalabras(Gramatica gramatica, int numPalabra, int niveles)
    {
            
        gramatica.generarPalabras(numPalabra, niveles);
        
        return gramatica.getPalabras();
    
    }
    
    public ListaCD<String> generarChosmky(Gramatica gramatica, String variable, String terminal)
    {
    
        gramatica.crearChomsky(variable, terminal);
        
        ListaCD<String> valores = gramatica.getFNC().getSigmas();
        valores.insertarFinal(gramatica.getFNC().toString());
        
        return valores;
    
    }
    
    public Set<String> generarPalabrasFNC(Gramatica gramatica, int numPalabra)
    {
            
        gramatica.getFNC().generarPalabras(numPalabra);
        
        return gramatica.getFNC().getPalabras();
    
    }    
    
    /**
     * @return the gramatica
     */
    public ListaCD<Gramatica> getGramaticas() {
        return gramaticas;
    }

    /**
     * @param gramatica the gramatica to set
     */
    public void setGramatica(ListaCD<Gramatica> gramatica) {
        this.gramaticas = gramatica;
    }
    
}

