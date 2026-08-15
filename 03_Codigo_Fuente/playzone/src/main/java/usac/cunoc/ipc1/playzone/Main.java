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
    
    //============VARIABLES GLOBALES - Auxiliares Generales============
    private boolean abandonarPartida = false;
    //-----------------------------------------------------------------
    
    //============VARIABLES GLOBALES - ADIVINA ANAGRAMAS============
    private static final double PORCENTAJE_INTENTOS = 0.67;
    
    private int cantidadPartidasAbandonadasAA = 0;
    private int cantidadPartidasJugadas = 0;
    private int cantidadPartidasGanadas = 0;
    private int cantidadPartidasPerdidas = 0;
    private int cantidadPalabrasAcertadas = 0;
    private int cantidadPalabrasFallidas = 0;
    private String palabraLetrasRevueltas;

    int cantidadPalabrasAcertadasLocal = 0;
    int cantidadPalabrasFallidasLocal = 0;

    private int cantidadAnagramasCorrectos;
    private int cantidadIntentosPermitidos;
    private int cantidadAnagramasOcultos;

    private char[] letrasAnagrama;
    private char[] letrasRevueltas;
    private char[] letrasPosibleAnagrama;
    
    private String[] tableroIntentosFallidos;
    private String[] tableroAnagramasOcultos;    
    private String[] anagramasCorrectos;
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
        
        boolean salirDelPrograma = false;
        
        limpiarPantalla();
        
        mostrarPantallaInicio();
        
        do {            
            
        mostrarMenu();
        
        switch (pedirOpcion()) {
            case 1:
                juegoAdivinaAnagramas();
                break;
            default:
                
        }
        
        } while (salirDelPrograma == false);
        
        System.out.println("Adiós :c");
        
    }
    //-------------------------------------------------
    
    
    
    
    //============MÉTODOS - ADIVINA ANAGRAMAS============
    private void juegoAdivinaAnagramas() {
        
        
        String entradaUsuario;
        
        limpiarPantalla();
        mostrarBienvenidaAnagramas();
        
        seleccionarAnagramas();
        
        revolverLetrasAnagrama();
        
        cantidadIntentosPermitidos = calcularIntentosPermitidos();
        tableroIntentosFallidos = new String[cantidadIntentosPermitidos];
        
        cantidadAnagramasOcultos = cantidadAnagramasCorrectos;
        tableroAnagramasOcultos = new String[cantidadAnagramasOcultos];

        do {
            
            mostrarAnagramasGUI();
            
            do {                
                entradaUsuario = pedirEntradaAnagrama();                
            } while (verficarEntradaAnagrama(entradaUsuario) == false);
            
            if (abandonarPartida) {
                
                cantidadPartidasAbandonadasAA = cantidadPartidasAbandonadasAA + 1;
                abandonarPartida = false;
                return;
                
            } 
            
        } while (cantidadIntentosPermitidos > 0 && cantidadAnagramasOcultos > 0);
        
        if (cantidadAnagramasOcultos == 0) {
            
            mostrarAnagramasGUI();
                
            mostrarVictoria();
            
            presionarENTER();

            cantidadPartidasGanadas = cantidadPartidasGanadas + 1;
            cantidadPartidasJugadas = cantidadPartidasJugadas + 1;

        } else {
            
            mostrarAnagramasGUI();

            mostrarDerrota();
            
            mostrarAnagramasFaltantes();
            
            presionarENTER();

            cantidadPartidasPerdidas = cantidadPartidasPerdidas + 1;
            cantidadPartidasJugadas = cantidadPartidasJugadas + 1;

        }
    }
    
    private void seleccionarAnagramas() {
        
        String[] bancoDePalabras = {
            "SOL", "LOS", "SER", "RES", "AMOR", "ROMA", "RAMO", 
            "MORA", "ARMO", "CASO", "SACO", "COSA", "ASCO",
            "PASO", "SOPA", "POSA", "SAPO", "ARCOS", "ROSCA",
            "ORCAS", "ROCAS", "GATO", "GOTA", "TOGA"
        };
        
        int numeroAleatorio = random.nextInt(bancoDePalabras.length);
        cantidadAnagramasCorrectos = 0;
        
        String anagrama = bancoDePalabras[numeroAleatorio];
        letrasAnagrama = anagrama.toCharArray();
        
        for (String posibleAnagrama : bancoDePalabras) {    
            
            letrasPosibleAnagrama = posibleAnagrama.toCharArray();
            
            if (verficadorAnagrama()) {
                cantidadAnagramasCorrectos = cantidadAnagramasCorrectos + 1;
            }
        }
        
        anagramasCorrectos = new String[cantidadAnagramasCorrectos];
        int indice = 0;
        for (String posibleAnagrama : bancoDePalabras) {
            
            letrasPosibleAnagrama = posibleAnagrama.toCharArray();
            
            if (verficadorAnagrama()) {
                anagramasCorrectos[indice] = posibleAnagrama;
                indice = indice + 1;
            }
        }
    }
    
    private boolean verficadorAnagrama() {
        
        if (verficadorLongitud() == false) {
            return false;
        } else if (verificadorLetras() == false) {
            return false;
        }
        
        return true;
    }
    
    private boolean verficadorLongitud() {
        
        if (letrasAnagrama.length == letrasPosibleAnagrama.length) {
            return true;
        }
        
        return false;
    }
    
    private boolean verificadorLetras() {
        
        for (char letra : letrasAnagrama) {
            
            int cantidadMaxLetra = 0;
            int cantidadLetra = 0;
            
            for (char contadorLetra : letrasAnagrama) {
                
                if (contadorLetra == letra) {
                    cantidadMaxLetra = cantidadMaxLetra + 1;
                }
            }
            
            for (char contadorLetra : letrasPosibleAnagrama) {
                
                if (contadorLetra == letra) {
                    cantidadLetra = cantidadLetra + 1;
                }
            }
            
            if (cantidadLetra != cantidadMaxLetra) {
                return false;
            }
        }
        
        return true;
    }
    
    private void revolverLetrasAnagrama() {
        
        int numeroAleatorio;
        palabraLetrasRevueltas = "";
        
        letrasRevueltas = new char[letrasAnagrama.length];
        
        for (char caracter : letrasAnagrama) {
            
            numeroAleatorio = random.nextInt(letrasRevueltas.length);
            
            //Los char en lugar de null tiene ASCII/Unicode para identificar si un valor está vacío
            while (letrasRevueltas[numeroAleatorio] != '\0') {                
                numeroAleatorio = random.nextInt(letrasRevueltas.length);
            }
            
            letrasRevueltas[numeroAleatorio] = caracter;
        }
        
        for (char letra : letrasRevueltas) {
            
            palabraLetrasRevueltas = palabraLetrasRevueltas + letra;
            
        }
    }
    
    private int calcularIntentosPermitidos() {
        return (int) Math.floor(cantidadAnagramasCorrectos * PORCENTAJE_INTENTOS);
    }
    
    private boolean verficarEntradaAnagrama(String entrada) {
        
        if (entrada.equalsIgnoreCase("salir")) {
            abandonarPartida = true;
        }
                                
        if (verificarNumeroOSimbolo(entrada) || entrada.equalsIgnoreCase("")) {
            System.out.println("Error: palabra inválida. Intenta de nuevo :p");
            return false;
        }
        
        if (verificarYaIngresada(entrada)) {
            System.out.println("Error: la palabra ya fue ingresada. Intenta de nuevo :p");
            return false;
        } 
        
        if (verificarExisteEnAnagramasCorrectos(entrada)) {
            
            System.out.println("Le atinaste D:");
            
            cantidadAnagramasOcultos = cantidadAnagramasOcultos - 1;
            
            almacenarEntradaAnagramasOcultos(entrada);
            
        } else {
                
            System.out.println("No le atinaste xd");

            cantidadIntentosPermitidos = cantidadIntentosPermitidos - 1;

            almacenarEntradaIntentosFallidos(entrada);

        }
        
        return true;
    }
    
    private boolean verificarNumeroOSimbolo(String entrada) {
        
        char[] letrasEntrada = entrada.toCharArray();
        
        for (char letra : letrasEntrada) {
            
            boolean esNumero = letra >= '0' && letra <= '9';
            boolean esMayuscula = letra >= 'A' && letra <= 'Z';
            boolean esMinuscula = letra >= 'a' && letra <= 'z';
            
            boolean esSimbolo = !esNumero && !esMayuscula && !esMinuscula;
            
            if (esNumero) {
                return true;
            } else if (esSimbolo) {
                return true;
            }
        }
        return false;
    }    
    
    private boolean verificarYaIngresada(String entrada) {
        
        for (String anagramaOculto : tableroAnagramasOcultos) {
            if (anagramaOculto != null && anagramaOculto.equalsIgnoreCase(entrada)) {
                return true;
            }
        }
        
        for (String intentoFallido : tableroIntentosFallidos) {
            if (intentoFallido != null && intentoFallido.equalsIgnoreCase(entrada)) {
                return true;
            }
        }
        
        return false;   
    }
    
    private boolean verificarExisteEnAnagramasCorrectos(String entrada) {
        for (String anagramaCorrecto : anagramasCorrectos) {
            if (entrada.equalsIgnoreCase(anagramaCorrecto)) {
                return true;
            }
        }
        
        return false;
    }
    
    private void almacenarEntradaAnagramasOcultos(String entrada) {
        int indice = 0;
        for (String anagramaOculto : tableroAnagramasOcultos) {

            if (anagramaOculto == null) {
                tableroAnagramasOcultos[indice] = entrada;
                return;
            }

            indice = indice + 1;
        }
    }
    
    private void almacenarEntradaIntentosFallidos(String entrada) {
        int indice = 0;
            for (String intentoFallido : tableroIntentosFallidos) {

                if (intentoFallido == null) {
                    tableroIntentosFallidos[indice] = entrada;
                    return;
                }

                indice = indice + 1;
            }
    }
    //---------------------------------------------------
    
    //============MÉTODOS - UTILIDADES============
    
    //************Limpiar pantalla************ 
    public void limpiarPantalla() {
        System.out.println("\033[H\033[2J");
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
    
    //************Pedir ENTER para continuar************
    private void presionarENTER() {
        System.out.println(ANSI_NEGRITA + 
                ANSI_AMARILLO + "==========================" + 
                ANSI_BLANCO + "Presiona ENTER para continuar" + 
                ANSI_AMARILLO + "=========================" + 
                ANSI_RESET);
        scanner.nextLine();
    }
    //**************************************************
    
    //--------------------------------------------
    
    //============MÉTODOS - GUI============
    
    //************Menú principal************
    private void mostrarPantallaInicio() {
        
        String playZone = ANSI_NEGRITA + ANSI_VERDE + 
                "|   ██████╗ ██╗      █████╗ ██╗   ██╗    ███████╗ ██████╗ ███╗   ██╗███████╗   |\n" +
                "|   ██╔══██╗██║     ██╔══██╗╚██╗ ██╔╝    ╚══███╔╝██╔═══██╗████╗  ██║██╔════╝   |\n" +
                "|   ██████╔╝██║     ███████║ ╚████╔╝       ███╔╝ ██║   ██║██╔██╗ ██║█████╗     |\n" +
                "|   ██╔═══╝ ██║     ██╔══██║  ╚██╔╝       ███╔╝  ██║   ██║██║╚██╗██║██╔══╝     |\n" +
                "|   ██║     ███████╗██║  ██║   ██║       ███████╗╚██████╔╝██║ ╚████║███████╗   |\n" +
                "|   ╚═╝     ╚══════╝╚═╝  ╚═╝   ╚═╝       ╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝   |\n" +
                "|                                                                              |"
                + ANSI_RESET;
        
        String logo = ANSI_NEGRITA + 
                "|                                     ___                                      |\n" +
                "|                                   ,\"---\".                                    |\n" +
                "|                                   :     ;                                    |\n" +
                "|                                    `-.-'                                     |\n" +
                "|                                     | |                                      |\n" +
                "|                                     | |                                      |\n" +
                "|                                     | |                                      |\n" +
                "|                                  _.-\\_/-._                                   |\n" +
                "|                               _ / |     | \\ _                                |\n" +
                "|                              / /   `---'   \\ \\                               |\n" +
                "|                             /  `-----------'  \\                              |\n" +
                "|                            /,-\"\"-.       ,-\"\"-.\\                             |\n" +
                "|                           ( i-..-i       i-..-i )                            |\n" +
                "|                           |`|    |-------|    |'|                            |\n" +
                "|                           \\ `-..-'  ,=.  `-..-'/                             |\n" +
                "|                            `--------|=|-------'                              |\n" +
                "|                                     | |                                      |\n" +
                "|                                     \\ \\                                      |\n" +
                "|                                      ) )                                     |\n" +
                "|                                     / /                                      |\n" +
                "|                                    ( (                                       |\n" +
                "|                                                                              |"
                + ANSI_RESET;
        
        String bienvenido = ANSI_NEGRITA +
                ANSI_CYAN +
                "|                                 Bienvenido :D                                |\n" +
                ANSI_AMARILLO +
                "|         ACOMODA EL ZOOM Y PON TEMA OSCURO PARA UNA MEJOR EXPERIENCIA         |\n" +
                ANSI_BLANCO +
                "|                                                                              |\n" +
                "|               Cuando estés listo presiona ENTER para continuar               |\n" +
                "|                                                                              |" +
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
    
    //************Mensajes de victoria y derrota de partida************
    private void mostrarVictoria() {
                
        System.out.println("\n" + ANSI_NEGRITA + ANSI_AZUL +
                "            ..####....####...##..##...####....####...######..######.\n" +
                "            .##......##..##..###.##..##..##..##........##....##.....\n" +
                "            .##.###..######..##.###..######...####.....##....####...\n" +
                "            .##..##..##..##..##..##..##..##......##....##....##.....\n" +
                "            ..####...##..##..##..##..##..##...####.....##....######.\n" +
                "            ........................................................\n" + ANSI_RESET
        );        
    }
    
    private void mostrarDerrota() {
                
        System.out.println("\n" + ANSI_NEGRITA + ANSI_ROJO +
                "        .#####...######..#####...#####...######...####...######..######.\n" +
                "        .##..##..##......##..##..##..##....##....##........##....##.....\n" +
                "        .#####...####....#####...##..##....##.....####.....##....####...\n" +
                "        .##......##......##..##..##..##....##........##....##....##.....\n" +
                "        .##......######..##..##..#####...######...####.....##....######.\n" +
                "        ................................................................\n" + ANSI_RESET
        );        
    }
    //*****************************************************************

    //************Adivina Anagramas************
    private void mostrarBienvenidaAnagramas() {
        
        String reglas = ANSI_NEGRITA + ANSI_CYAN + 
                "================================================================================\n" +
                "      ░█▀▄░█▀▀░█▀▀░█░░░█▀█░█▀▀░░░░░░░░░█▀█░█▀█░█▀█░█▀▀░█▀▄░█▀█░█▄█░█▀█░█▀▀\n" +
                "      ░█▀▄░█▀▀░█░█░█░░░█▀█░▀▀█░░░▄▄▄░░░█▀█░█░█░█▀█░█░█░█▀▄░█▀█░█░█░█▀█░▀▀█\n" +
                "      ░▀░▀░▀▀▀░▀▀▀░▀▀▀░▀░▀░▀▀▀░░░░░░░░░▀░▀░▀░▀░▀░▀░▀▀▀░▀░▀░▀░▀░▀░▀░▀░▀░▀▀▀\n" +
                "================================================================================\n" +
                ANSI_AMARILLO + 
                "1. OBJETIVO: Se te mostrará una lista de letras totalmente desordenadas.\n" +
                "   Debes adivinar todas las palabras que son anagramas, es decir, que se\n" +
                "   pueden formar con exactamente esas mismas letras.\n\n" +
                ANSI_VERDE +
                "2. INTENTOS LIMITADOS: Tendrás una cantidad de intentos fallidos\n" +
                "   calculada automáticamente.\n\n" +
                ANSI_AMARILLO +
                "3. REPETICIONES: Si ingresas una palabra que ya habías intentado,\n" +
                "   ya sea correcta o incorrecta, el sistema te avisará y NO consumirá\n" +
                "   ningún intento.\n\n" +
                ANSI_VERDE +
                "4. COMANDO DE SALIDA: Puedes escribir la palabra SALIR en cualquier\n" +
                "   momento si deseas abandonar la partida y volver al menú.\n\n" +
                ANSI_AMARILLO +
                "5. CONDICIÓN DE VICTORIA: Ganas si descubres TODOS los anagramas\n" +
                "   antes de que tus intentos fallidos lleguen a cero\n" +
                ANSI_RESET;
                
        System.out.println(reglas);
        presionarENTER();
    }
    
    private void mostrarAnagramasGUI() {
        
        limpiarPantalla();
        
        String letrasRevueltas =
                ANSI_AZUL + "================================================================================\n" + ANSI_RESET +
                ANSI_AMARILLO + "                           LETRAS DEL ANAGRAMA: " + ANSI_NEGRITA + ANSI_BLANCO + palabraLetrasRevueltas + "\n" + ANSI_RESET +
                ANSI_AZUL + "================================================================================\n" + ANSI_RESET;
        
        String cantidadIntentosPermitidosYAnagramasFaltantes = 
                ANSI_NEGRITA + ANSI_CYAN + "|CANTIDAD DE INTENTOS PERMITIDOS: " + ANSI_VERDE + cantidadIntentosPermitidos + ANSI_RESET +
                ANSI_NEGRITA + ANSI_CYAN + "      CANTIDAD DE ANAGRAMAS OCULTOS: " + ANSI_VERDE + cantidadAnagramasOcultos + "       |\n" + ANSI_RESET;
        
        String tableros = ANSI_NEGRITA + ANSI_AMARILLO +
                "--------------------------------------------------------------------------------\n" +
                "                        ░▀█▀░█▀█░█▀▄░█░░░█▀▀░█▀▄░█▀█░█▀▀\n" +
                "                        ░░█░░█▀█░█▀▄░█░░░█▀▀░█▀▄░█░█░▀▀█\n" +
                "                        ░░▀░░▀░▀░▀▀░░▀▀▀░▀▀▀░▀░▀░▀▀▀░▀▀▀\n" +
                "--------------------------------------------------------------------------------\n" + ANSI_RESET;
        
        String intentosFallidos = ANSI_NEGRITA + ANSI_ROJO + 
                "╦╔╗╔╔╦╗╔═╗╔╗╔╔╦╗╔═╗╔═╗    ╔═╗╔═╗╦  ╦  ╦╔╦╗╔═╗╔═╗\n" +
                "║║║║ ║ ║╣ ║║║ ║ ║ ║╚═╗    ╠╣ ╠═╣║  ║  ║ ║║║ ║╚═╗\n" +  
                "╩╝╚╝ ╩ ╚═╝╝╚╝ ╩ ╚═╝╚═╝    ╚  ╩ ╩╩═╝╩═╝╩═╩╝╚═╝╚═╝" + ANSI_RESET;
        
        String anagramasOcultos = ANSI_NEGRITA + ANSI_CYAN + 
                "╔═╗╔╗╔╔═╗╔═╗╦═╗╔═╗╔╦╗╔═╗╔═╗    ╔══╗╔═╗╦ ╦╦ ╔╦╗╔═╗╔═╗\n" + 
                "╠═╣║║║╠═╣║ ╦╠╦╝╠═╣║║║╠═╣╚═╗    ║  ║║  ║ ║║  ║ ║ ║╚═╗\n" + 
                "╩ ╩╝╚╝╩ ╩╚═╝╩╚═╩ ╩╩ ╩╩ ╩╚═╝    ╚══╝╚═╝╚═╝╩═╝╩ ╚═╝╚═╝" + ANSI_RESET;
        
        System.out.println(letrasRevueltas + cantidadIntentosPermitidosYAnagramasFaltantes + tableros);
        
        System.out.println(intentosFallidos);
        
        for (String intentoFallido : tableroIntentosFallidos) {
            
            if (intentoFallido == null) {
                System.out.print( 
                    ANSI_BLANCO + ANSI_NEGRITA + "[ " + ANSI_RESET +
                    ANSI_ROJO + " " + ANSI_RESET +
                    ANSI_BLANCO + ANSI_NEGRITA + " ]    " + ANSI_RESET
                );
            } else {
                System.out.print( 
                    ANSI_BLANCO + ANSI_NEGRITA + "[ " + ANSI_RESET +
                    ANSI_ROJO + intentoFallido + ANSI_RESET +
                    ANSI_BLANCO + ANSI_NEGRITA + " ]    " + ANSI_RESET
                );
            }
        }
        
        System.out.println("\n");
        
        System.out.println(anagramasOcultos);
        
        for (String anagramaOculto : tableroAnagramasOcultos) {
            
            if (anagramaOculto == null) {
                System.out.print(
                        ANSI_BLANCO + ANSI_NEGRITA + "[ " + ANSI_RESET +
                        ANSI_CYAN + "?" + ANSI_RESET +
                        ANSI_BLANCO + ANSI_NEGRITA + " ]    " + ANSI_RESET
                );
            } else {
                System.out.print(
                        ANSI_BLANCO + ANSI_NEGRITA + "[ " + ANSI_RESET +
                        ANSI_CYAN + anagramaOculto + ANSI_RESET +
                        ANSI_BLANCO + ANSI_NEGRITA + " ]    " + ANSI_RESET
                );
            }
        }
        
        System.out.println("\n");
        
    }
    
    private void mostrarAnagramasFaltantes() {
        
        System.out.print("Los anagramas faltantes son: ");
        
        for (String anagramaCorrecto : anagramasCorrectos) {
            
            boolean anagramaFaltante = true;
            
            for (String anagramaOculto : tableroAnagramasOcultos) {
                if (anagramaOculto != null && anagramaOculto.equalsIgnoreCase(anagramaCorrecto)) {
                    anagramaFaltante = false;
                    break;
                }
            }
            
            if (anagramaFaltante) {
                System.out.print(
                        ANSI_BLANCO + ANSI_NEGRITA + "[ " + ANSI_RESET
                        + ANSI_VERDE + anagramaCorrecto + ANSI_RESET
                        + ANSI_BLANCO + ANSI_NEGRITA + " ]    " + ANSI_RESET
                );
            }
            
        }
        
        System.out.println("\n");
    }
    
    private String pedirEntradaAnagrama() {
        
        System.out.println(ANSI_AMARILLO + "(Entradas válidas: (A-Z) sin 'ñ', espacios, símbolos y números)" + ANSI_RESET);
        System.out.print(ANSI_NEGRITA + "» " + ANSI_RESET);
        
        return scanner.nextLine().trim().toUpperCase();
    }
    //*****************************************
    
    //-------------------------------------
        
}
