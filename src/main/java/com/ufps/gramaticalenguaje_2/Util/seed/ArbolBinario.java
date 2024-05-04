/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.gramaticalenguaje_2.Util.seed;

import java.util.Map;
import java.util.Random;
import com.ufps.gramaticalenguaje_2.Util.seed.NodoArbol;
import com.ufps.gramaticalenguaje_2.Util.seed.ListaCD;

/**
 *
 * @author forer
 */
public class ArbolBinario {

    private NodoArbol inicial;
    private ListaCD<String> terminales;
    private ListaCD<String> noTerminales;
    private Map<String, ListaCD<String>> producciones;

    public ArbolBinario(String raiz, ListaCD<String> terminales, ListaCD<String> noTerminales, Map<String, ListaCD<String>> producciones) {
        if (raiz == null) {
            throw new RuntimeException("Variable inicial no declarada.");
        }

        this.inicial = new NodoArbol(raiz);
        this.terminales = terminales;
        this.noTerminales = noTerminales;
        this.producciones = producciones;
    }

    public ListaCD<String> construccionArbol() {
        if (terminales.isEmpty() || noTerminales.isEmpty()) {
            throw new RuntimeException("Variables terminales o no terminales nulas.");
        }

        ListaCD<String> palabra = new ListaCD<>();
        construirRecursivo(inicial, palabra);
        
        return palabra;
    }

    private void construirRecursivo(NodoArbol nodo, ListaCD<String> palabra) {
        if (esTerminal(nodo.getValor())) {

            palabra.insertarFinal(nodo.getValor());
            return;

        }

        ListaCD<String> produccionesVar = producciones.get(nodo.getValor());

        if (produccionesVar != null) {

            Random random = new Random();
            String produccion = produccionesVar.get(random.nextInt(produccionesVar.getSize()));

            String varA;
            String varB;

            switch (produccion.length()) {
                case 3, 4 -> {
                    if (producciones.containsKey(produccion.substring(0, 2))) {
                        varA = produccion.substring(0, 2);
                        varB = produccion.substring(2);
                    } else {
                        varA = produccion.substring(0, 1);
                        varB = produccion.substring(1);
                    }
                }
                case 5 -> {
                    varA = produccion.substring(0, 2);
                    varB = produccion.substring(2);
                }
                case 6 -> {
                    varA = produccion.substring(0, 3);
                    varB = produccion.substring(3);
                }
                default -> {
                    varA = produccion.substring(0, 1);
                    varB = produccion.substring(1);
                }
            }

            NodoArbol izquierdo = new NodoArbol(varA);
            NodoArbol derecho = new NodoArbol(varB);

            construirRecursivo(izquierdo, palabra);
            construirRecursivo(derecho, palabra);

            nodo.setIzquierdo(izquierdo);
            nodo.setDerecho(derecho);
        }

    }

    private boolean esTerminal(String info) {

        return this.terminales.containTo(info);

    }

    public void borrarArbol() {

        this.inicial = new NodoArbol(this.inicial.getValor());

    }
}
