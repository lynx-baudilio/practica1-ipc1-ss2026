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
    
    //============MÉTODOS - FLUJO PRINCIPAL============
    public static void main(String[] args) {
        
        Main main = new Main();
        
        main.iniciarPrograma();
        
    }
    
    public void iniciarPrograma() {
        
        limpiarPantalla();
        
        mostrarPantallaInicio();
                
        mostrarMenu();
        
        switch (pedirOpcion()) {
            case 1:
                juegoAdivinaAnagramas();
                break;
            default:
                
        }
        
    }
    //-------------------------------------------------
    
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
    
    //************Limpiar pantalla************ 
    public void limpiarPantalla() {
        System.out.println("\033[H\033[2J");
        System.out.flush(); 
        //flush limpia el búfer, y es recomendable para uso en terminales de Linux
        //en algunas versiones de windonzzzz no funciona
    }
    //****************************************
    
    //************Pedir Entrada - Menú principal************
    private int pedirOpcion() {
        
        int opcion;
        
        mostrarPedirOpcion();
        
        System.out.println(ANSI_AMARILLO + "(Entradas válidas: 1 - 5) " + ANSI_RESET);
        System.out.print(ANSI_NEGRITA + "» " + ANSI_RESET);
        opcion = Integer.valueOf(scanner.nextLine());
        
        return opcion;
    }
    //******************************************************
    
    //--------------------------------------------
    
    //============MÉTODOS - GUI============
    
    //************Menú principal************
    private void mostrarPantallaInicio() {
        
        String playZone = ANSI_NEGRITA + ANSI_VERDE + 
                "|    ██████╗ ██╗      █████╗ ██╗   ██╗    ███████╗ ██████╗ ███╗   ██╗███████╗    |\n" +
                "|    ██╔══██╗██║     ██╔══██╗╚██╗ ██╔╝    ╚══███╔╝██╔═══██╗████╗  ██║██╔════╝    |\n" +
                "|    ██████╔╝██║     ███████║ ╚████╔╝       ███╔╝ ██║   ██║██╔██╗ ██║█████╗      |\n" +
                "|    ██╔═══╝ ██║     ██╔══██║  ╚██╔╝       ███╔╝  ██║   ██║██║╚██╗██║██╔══╝      |\n" +
                "|    ██║     ███████╗██║  ██║   ██║       ███████╗╚██████╔╝██║ ╚████║███████╗    |\n" +
                "|    ╚═╝     ╚══════╝╚═╝  ╚═╝   ╚═╝       ╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝    |\n" +
                "|                                                                                |"
                + ANSI_RESET;
        
        String logo = ANSI_NEGRITA + 
                "|                                      ___                                       |\n" +
                "|                                    ,\"---\".                                     |\n" +
                "|                                    :     ;                                     |\n" +
                "|                                     `-.-'                                      |\n" +
                "|                                      | |                                       |\n" +
                "|                                      | |                                       |\n" +
                "|                                      | |                                       |\n" +
                "|                                   _.-\\_/-._                                    |\n" +
                "|                                _ / |     | \\ _                                 |\n" +
                "|                               / /   `---'   \\ \\                                |\n" +
                "|                              /  `-----------'  \\                               |\n" +
                "|                             /,-\"\"-.       ,-\"\"-.\\                              |\n" +
                "|                            ( i-..-i       i-..-i )                             |\n" +
                "|                            |`|    |-------|    |'|                             |\n" +
                "|                            \\ `-..-'  ,=.  `-..-'/                              |\n" +
                "|                             `--------|=|-------'                               |\n" +
                "|                                      | |                                       |\n" +
                "|                                      \\ \\                                       |\n" +
                "|                                       ) )                                      |\n" +
                "|                                      / /                                       |\n" +
                "|                                     ( (                                        |\n" +
                "|                                                                                |"
                + ANSI_RESET;
        
        String bienvenido = ANSI_NEGRITA +
                ANSI_CYAN +
                "|                                  Bienvenido :D                                 |\n" +
                ANSI_AMARILLO +
                "|   ACOMODA EL ZOOM DE TU PANTALLA Y PON TEMA OSCURO PARA UNA MEJOR EXPERIENCIA  |\n" +
                ANSI_BLANCO +
                "|                Cuando estés listo presiona ENTER para continuar                |\n" +
                "|                                                                                |" +
                ANSI_RESET;
        
        System.out.println(playZone);
        
        System.out.println(logo);
        
        System.out.println(bienvenido);
        
        scanner.nextLine();
        
    }
    
    private void mostrarMenu() {
                
        limpiarPantalla();
     
        String menuPrincipal = ANSI_NEGRITA + ANSI_AZUL +
                "================================================================================\n" +
                "             ██████   ██████ ██████████ ██████   █████ █████  █████\n" +
                "            ░░██████ ██████ ░░███░░░░░█░░██████ ░░███ ░░███  ░░███ \n" +
                "             ░███░█████░███  ░███  █ ░  ░███░███ ░███  ░███   ░███ \n" +
                "             ░███░░███ ░███  ░██████    ░███░░███░███  ░███   ░███ \n" +
                "             ░███ ░░░  ░███  ░███░░█    ░███ ░░██████  ░███   ░███ \n" +
                "             ░███      ░███  ░███ ░   █ ░███  ░░█████  ░███   ░███ \n" +
                "             █████     █████ ██████████ █████  ░░█████ ░░████████  \n" +
                "            ░░░░░     ░░░░░ ░░░░░░░░░░ ░░░░░    ░░░░░   ░░░░░░░░   \n" +
                "================================================================================\n"
                + ANSI_RESET;
        
        String adivinaAnagramas = ANSI_ROJO + 
                " ░▀█░░░░░░░░░░█▀█░█▀▄░▀█▀░█░█░▀█▀░█▀█░█▀█░░░█▀█░█▀█░█▀█░█▀▀░█▀▄░█▀█░█▄█░█▀█░█▀▀\n" +
                " ░░█░░░░▄▄▄░░░█▀█░█░█░░█░░▀▄▀░░█░░█░█░█▀█░░░█▀█░█░█░█▀█░█░█░█▀▄░█▀█░█░█░█▀█░▀▀█\n" +
                " ░▀▀▀░░░░░░░░░▀░▀░▀▀░░▀▀▀░░▀░░▀▀▀░▀░▀░▀░▀░░░▀░▀░▀░▀░▀░▀░▀▀▀░▀░▀░▀░▀░▀░▀░▀░▀░▀▀▀\n" +
                ANSI_RESET;
        
        String ticTacToe = ANSI_ROJO +
                "              ░▀▀▄░░░░░░░░░▀█▀░▀█▀░█▀▀░░░▀█▀░█▀█░█▀▀░░░▀█▀░█▀█░█▀▀\n" +
                "              ░▄▀░░░░▄▄▄░░░░█░░░█░░█░░░░░░█░░█▀█░█░░░░░░█░░█░█░█▀▀\n" +
                "              ░▀▀▀░░░░░░░░░░▀░░▀▀▀░▀▀▀░░░░▀░░▀░▀░▀▀▀░░░░▀░░▀▀▀░▀▀▀\n" +
                ANSI_RESET;
        
        String sudoku = ANSI_ROJO + 
                "                      ░▀▀█░░░░░░░░░█▀▀░█░█░█▀▄░█▀█░█░█░█░█\n" +
                "                      ░░▀▄░░░▄▄▄░░░▀▀█░█░█░█░█░█░█░█▀▄░█░█\n" +
                "                      ░▀▀░░░░░░░░░░▀▀▀░▀▀▀░▀▀░░▀▀▀░▀░▀░▀▀▀\n" +
                ANSI_RESET;
        
        String reportesYSalir = ANSI_NEGRITA + ANSI_BLANCO + 
                "░█░█░░░░░░░░░█▀▄░█▀▀░█▀█░█▀█░█▀▄░▀█▀░█▀▀░█▀▀    ░█▀▀░░░░░░░░░█▀▀░█▀█░█░░░▀█▀░█▀▄\n" +
                "░░▀█░░░▄▄▄░░░█▀▄░█▀▀░█▀▀░█░█░█▀▄░░█░░█▀▀░▀▀█    ░▀▀▄░░░▄▄▄░░░▀▀█░█▀█░█░░░░█░░█▀▄\n" +
                "░░░▀░░░░░░░░░▀░▀░▀▀▀░▀░░░▀▀▀░▀░▀░░▀░░▀▀▀░▀▀▀    ░▀▀░░░░░░░░░░▀▀▀░▀░▀░▀▀▀░▀▀▀░▀░▀\n" +
                ANSI_RESET;
        
        System.out.println(menuPrincipal + "\n");
        
        System.out.println(adivinaAnagramas);
        System.out.println(ticTacToe);
        System.out.println(sudoku);
        
        System.out.println(reportesYSalir);
    }
    //**************************************
    
    //************Pedir entrada - Menú principal************
    private void mostrarPedirOpcion() {
        
        String pedirOpcion = ANSI_NEGRITA + ANSI_CYAN +
                "                   ╔═╗╦  ╦╔═╗╔═╗  ╦ ╦╔╗╔╔═╗  ╔═╗╔═╗╔═╗╦╔═╗╔╗╔\n" +
                "                   ║╣ ║  ║║ ╦║╣   ║ ║║║║╠═╣  ║ ║╠═╝║  ║║ ║║║║\n" +
                "                   ╚═╝╩═╝╩╚═╝╚═╝  ╚═╝╝╚╝╩ ╩  ╚═╝╩  ╚═╝╩╚═╝╝╚╝\n" +
                ANSI_RESET;
        
        System.out.println("\n" + pedirOpcion);
    }
    //******************************************************
    
    //-------------------------------------
        
}
