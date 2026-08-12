package usac.cunoc.ipc1.playzone;

import java.util.Random;
import java.util.Scanner;

public class Main {
    
    //============CODIGO ANSI============
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_NEGRITA = "\u001B[1m";
    private static final String ANSI_ROJO = "\u001B[31m";
    private static final String ANSI_VERDE = "\u001B[32m";
    private static final String ANSI_AMARILLO = "\u001B[33m";
    private static final String ANSI_AZUL = "\u001B[34m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_BLANCO = "\u001B[37m";
    //-----------------------------------
    
    //============VARIABLES GLOBALES - ADIVINA ANAGRAMAS============
    private static final double PORCENTAJE_INTENTOS = 0.67;
    
    private int cantidadPartidasAbandonadas;
    private int cantidadPartidasJugadas;
    private int cantidadPartidasGanadas;
    private int cantidadPalabrasAcertadas;
    private int cantidadPalabrasFallidas;

    private int cantidadAnagramasCorrectos;

    private String[] anagramasCorrectos;
    private char[] letrasAnagrama;
    private char[] letrasPosibleAnagrama;
    private char[] letrasRevueltas;
    
    //--------------------------------------------------------------
    
    //============VARIABLES GLOBALES - TIC TAC TOE============
    
    //--------------------------------------------------------
    
    //============VARIABLES GLOBALES - SUDOKU============
    
    //---------------------------------------------------z
    
    //============CLASES DE UTILIDADES============
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);
    //--------------------------------------------
    
    public static void main(String[] args) {
        
        Main main = new Main();
        
        main.iniciarPrograma();
        
    }
    
    public void iniciarPrograma() {
                
        mostrarMenu();
        
        switch (pedirOpcion()) {
            case 1:
                juegoAdivinaAnagramas();
                break;
            default:
                
        }
        
    }
    
    private void mostrarMenu() {
        
        limpiarPantalla();
        
        System.out.println("======Playzone======\n");
        System.out.println("1. Adivina Anagramas");
    }
    
    private int pedirOpcion() {
        
        int opcion;
        
        System.out.println(ANSI_NEGRITA + "¿Qué quieres hacer :p?" + ANSI_RESET);
        System.out.print(ANSI_AMARILLO + "(Entradas válidas: 1 - 5) " + ANSI_RESET);
        System.out.print(ANSI_NEGRITA + "-> " + ANSI_RESET);
        opcion = Integer.valueOf(scanner.nextLine());
        
        return opcion;
    }
    
    //============MÉTODOS - ADIVINA ANAGRAMAS============
    
    private void juegoAdivinaAnagramas() {
        limpiarPantalla();
        System.out.println("Iniciando juego Adivina Anagramas");
    }
    
    private void seleccionarAnagramas() {
        
    }
    
    private void verficadorAnagrama() {
        
    }
    
    private void verficadorLongitud() {
        
    }
    
    private void verificadorLetras() {
        
    }
    
    private void revolverLetrasAnagrama() {
        
    }
    
    private void calcularIntentosPermitidos() {
        
    }
    
    //---------------------------------------------------
    
    //============MÉTODOS - UTILIDADES============
    
    public void limpiarPantalla() {
        System.out.println("\033[H\033[2J");
        System.out.flush(); 
        //flush limpia el búfer, y es recomendable para uso en terminales de Linux
        //en algunas versiones de windonzzzz no funciona
    }
    
    //--------------------------------------------
    
    //============MÉTODOS - GUI============
    
    //-------------------------------------
        
}
