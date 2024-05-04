/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.gramaticalenguaje_2.Model;

import com.ufps.gramaticalenguaje_2.Util.seed.ArbolBinario;
import com.ufps.gramaticalenguaje_2.Util.seed.ListaCD;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author juan_
 */
public class FormaNormalChomsky {
    
    private Gramatica G;
    private String nombre;
    private ListaCD<String> variablesTerminales;
    private ListaCD<String> variablesNoTerminales;
    private String variableInicial;
    private Map<String, ListaCD<String>> producciones;
    private Set<String> palabras;
    private int indice;
    private int sigmaActual;
    private ListaCD<String> sigmas;

    public FormaNormalChomsky(Gramatica gramatica) {
        
        this.G = gramatica;
        this.variablesTerminales = new ListaCD<>();
        this.variablesNoTerminales = new ListaCD<>();
        this.producciones = new LinkedHashMap<>();
        this.palabras = new HashSet<>();
        this.indice = 1;
        this.sigmas = new ListaCD();
        this.sigmaActual = gramatica.getSigmas().getSize();
        
    }

    public void moverGramatica() {

        this.nombre = G.getNombre();

        this.variableInicial = G.getVariableInicial() + "";

        for (Character c : G.getVariablesTerminales()) {
            this.variablesTerminales.insertarFinal(String.valueOf(c));
        }
        for (Character c : G.getVariablesNoTerminales()) {
            this.variablesNoTerminales.insertarFinal(String.valueOf(c));
        }
        for (Map.Entry<Character, ListaCD<String>> entry : G.getProducciones().entrySet()) {
            String clave = String.valueOf(entry.getKey());
            this.producciones.put(clave, entry.getValue());
        }
    }

    public void formaChomsky(String variable) {
        
        if(variable.length() > 1)
        {
        
            throw new RuntimeException("El simbolo para las nuevas variables debe ser de un solo caracter.");
        
        }

        Map<String, ListaCD<String>> aux = new LinkedHashMap();

        for (HashMap.Entry<String, ListaCD<String>> entry : this.producciones.entrySet()) {

            ListaCD<String> nuevas = new ListaCD();

            for (String produccion : entry.getValue()) {

                ListaCD<String> valores = new ListaCD();
                aux.put(entry.getKey(), valores);

                if (produccion.length() > 2) {

                    nuevas.insertarFinal(crearVariables(produccion, this.indice, aux,variable));

                } else {

                    nuevas.insertarFinal(produccion);

                }

            }

            aux.put(entry.getKey(), nuevas);

        }

        this.producciones = aux;
        
        generarSigma("Creando nuevas variables para la Forma Normal de Chomsky:");

    }

    private String crearVariables(String produccion, int indice, Map<String, ListaCD<String>> nuevas, String variable) {

        String temp = "";
        ListaCD aux = new ListaCD();

        if (produccion.length() > 2) {

            boolean encontrado = false;
            temp = crearVariables(produccion.substring(1), indice + 1, nuevas, variable);

            for (HashMap.Entry<String, ListaCD<String>> entry : nuevas.entrySet()) {

                if (entry.getKey().length() > 1) {

                    if (entry.getValue().get(0).equals(temp)) {

                        encontrado = true;
                        int num = Integer.parseInt(entry.getKey().substring(1));
                        indice = num;

                    }

                }

            }

            if (!encontrado) {

                this.variablesNoTerminales.insertarFinal(variable + indice);
                aux.insertarFinal(temp);

                nuevas.put(variable + indice, aux);
                this.indice++;

            }

        } else {

            return produccion;

        }

        return produccion.charAt(0) + variable + indice;

    }

    public void reemplazarTerminales(String variable) {

        if(variable.length() > 1)
        {
        
            throw new RuntimeException("El simbolo para el reemplazo debe ser de un solo caracter.");
        
        }
        
        String nuevaVariable;
        String clave = variable;

        for (int i = 0; i < variablesTerminales.getSize(); i++) {

            nuevaVariable = clave + i;
            ListaCD<String> temp = new ListaCD();
            temp.insertarFinal(variablesTerminales.get(i));

            boolean existe = false;

            for (ListaCD<String> lista : producciones.values()) {

                if (lista.get(0).equals(variablesTerminales.get(i))) {

                    existe = true;
                    break;

                }

            }
            if (!existe) {

                this.producciones.put(nuevaVariable, temp);
                this.variablesNoTerminales.insertarFinal(nuevaVariable);

            }
        }

        Map<String, ListaCD<String>> temp = producciones;

        for (Map.Entry<String, ListaCD<String>> entry : temp.entrySet()) {

            String key = entry.getKey();
            ListaCD<String> valueList = entry.getValue();
            ListaCD<String> produccionList = producciones.get(key);

            for (int i = 0; i < valueList.getSize(); i++) {

                String str = valueList.get(i);

                if (str.length() == 2) {

                    String v1 = buscarProduccion(str.substring(0, 1));
                    String v2 = buscarProduccion(str.substring(1));

                    if ((v1 != null) ^ (v2 != null)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(v1 != null ? v1 : str.charAt(0));
                        sb.append(v2 != null ? v2 : str.charAt(1));
                        produccionList.set(i, sb.toString());
                    }

                } else if (str.length() > 2) {

                    String v = buscarProduccion(str.substring(0, 1));
                    if (v != null) {
                        produccionList.set(i, v + str.substring(1));
                    }

                }

            }

        }
        
        generarSigma("Reemplazando Terminales por Variables:");

    }

    public void generarPalabras(int numPalabras) {

        ArbolBinario arbol = new ArbolBinario(this.variableInicial, this.variablesTerminales, this.variablesNoTerminales, this.producciones);

        while (palabras.size() < numPalabras) {

            ListaCD<String> palabra = arbol.construccionArbol();
            
            if(this.palabras.add(construccionPalabra(palabra))){
                System.out.println(palabra);
            }
            
            arbol.borrarArbol();

        }

    }

    private String construccionPalabra(ListaCD<String> palabra) {

        String s = "";

        for (String c : palabra) {

            s += c + "";

        }

        return s;

    }

    @Override
    public String toString() {
        String s = "FNC={{";
        
        for(String variables : this.variablesNoTerminales)
        {
        
            s += variables + ",";
        
        }
        
        s += "}," + this.variableInicial + ",{";
                
        for(String terminales : this.variablesTerminales)
        {
        
            s += terminales + ",";
        
        }
        
        s += "}," + this.sigmaActual + "}";
        
        return s;
        
    }

    public void generarSigma(String proceso)
    {
    
        String sigma = proceso;
        
        this.sigmaActual++;
        
        sigma += "ς" + (this.sigmaActual) + "={" + "\n";
                
        for(HashMap.Entry<String, ListaCD<String>> entry : this.producciones.entrySet())
        {
        
            sigma += entry.getKey() + "-->" + entry.getValue().producciones() + "\n";
        
        }
        
        sigma += "}\n";
        
        this.sigmas.insertarFinal(sigma);
        
    }
    
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the variablesTerminales
     */
    public ListaCD<String> getVariablesTerminales() {
        return variablesTerminales;
    }

    /**
     * @param variablesTerminales the variablesTerminales to set
     */
    public void setVariablesTerminales(ListaCD<String> variablesTerminales) {
        this.variablesTerminales = variablesTerminales;
    }

    /**
     * @return the variablesNoTerminales
     */
    public ListaCD<String> getVariablesNoTerminales() {
        return variablesNoTerminales;
    }

    /**
     * @param variablesNoTerminales the variablesNoTerminales to set
     */
    public void setVariablesNoTerminales(ListaCD<String> variablesNoTerminales) {
        this.variablesNoTerminales = variablesNoTerminales;
    }

    /**
     * @return the variableInicial
     */
    public String getVariableInicial() {
        return variableInicial;
    }

    /**
     * @param variableInicial the variableInicial to set
     */
    public void setVariableInicial(String variableInicial) {
        this.variableInicial = variableInicial;
    }

    /**
     * @return the producciones
     */
    public Map<String, ListaCD<String>> getProducciones() {
        return producciones;
    }

    /**
     * @param producciones the producciones to set
     */
    public void setProducciones(Map<String, ListaCD<String>> producciones) {
        this.producciones = producciones;
    }

    /**
     * @return the palabras
     */
    public Set<String> getPalabras() {
        return palabras;
    }

    /**
     * @param palabras the palabras to set
     */
    public void setPalabras(Set<String> palabras) {
        this.palabras = palabras;
    }

    public void imprimirGramatica() {
        System.out.println("Nombre de la gramatica: " + this.nombre);
        System.out.println("Variables terminales: " + this.variablesTerminales.toString());
        System.out.println("Variables no terminales: " + this.variablesNoTerminales.toString());
        System.out.println("Variable inicial: " + this.variableInicial);
        System.out.println("Producciones:");
        for (Map.Entry<String, ListaCD<String>> entry : this.producciones.entrySet()) {
            System.out.println("   " + entry.getKey() + " -> " + String.join(" | ", entry.getValue()));
        }
    }

    public String buscarProduccion(String str) {
        for (Map.Entry<String, ListaCD<String>> entry : this.producciones.entrySet()) {
            ListaCD<String> listaProducciones = entry.getValue();
            if (listaProducciones.getSize() == 1 && listaProducciones.containTo(str)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Gramatica getG() {
        return G;
    }

    public void setG(Gramatica G) {
        this.G = G;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public int getSigmaActual() {
        return sigmaActual;
    }

    public void setSigmaActual(int sigmaActual) {
        this.sigmaActual = sigmaActual;
    }

    public ListaCD<String> getSigmas() {
        return sigmas;
    }

    public void setSigmas(ListaCD<String> sigmas) {
        this.sigmas = sigmas;
    }
    
    
    
}
