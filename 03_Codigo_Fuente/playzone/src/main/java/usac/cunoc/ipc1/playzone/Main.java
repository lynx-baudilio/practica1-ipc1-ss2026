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

    private int cantidadAnagramasCorrectos;
    private int cantidadIntentosPermitidos;
    private int cantidadAnagramasOcultos;

    private char[] letrasAnagrama;
    private char[] letrasRevueltas;
    private char[] letrasPosibleAnagrama;
    
    private String[] tableroIntentosFallidos;
    private String[] tableroAnagramasOcultos;
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
        
        int cantidadPalabrasAcertadasLocal;
        int cantidadPalabrasFallidasLocal;
        
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
            
            scanner.nextLine();
            
            if (abandonarPartida) {
                
                cantidadPartidasAbandonadasAA = cantidadPartidasAbandonadasAA + 1;
                abandonarPartida = false;
                return;
                
            } else if (cantidadAnagramasOcultos == 0) {
                
                //mostrarVictoria();
                System.out.println("ganaste :p");
                                
                cantidadPartidasGanadas = cantidadPartidasGanadas + 1;
                cantidadPartidasJugadas = cantidadPartidasJugadas + 1;
                
            } else if (cantidadIntentosPermitidos == 0) {
                
                //mostrarDerrota();
                System.out.println("perdiste xd");

                cantidadPartidasPerdidas = cantidadPartidasPerdidas + 1;
                cantidadPartidasJugadas = cantidadPartidasJugadas + 1;
                
            }
        } while (cantidadIntentosPermitidos > 0 && cantidadAnagramasOcultos > 0);
    }
    
    private void seleccionarAnagramas() {
        
        int numeroAleatorio;
        int indice = 0;
        
        String anagrama;
        
        cantidadAnagramasCorrectos = 0;
        
        String[] bancoDePalabras = {
            "SOL", "LOS", "SER", "RES", "AMOR", "ROMA", "RAMO", 
            "MORA", "ARMO", "OMAR", "CASO", "SACO", "COSA", "ASCO",
            "PASO", "SOPA", "POSA", "SAPO", "ARCOS", "ROSCA", "ORCAS",
            "GATO", "GOTA", "TOGA"
        };
                
        numeroAleatorio = random.nextInt(bancoDePalabras.length);
        anagrama = bancoDePalabras[numeroAleatorio];
        letrasAnagrama = anagrama.toCharArray();
        
        for (String posibleAnagrama : bancoDePalabras) {
            
            letrasPosibleAnagrama = posibleAnagrama.toCharArray();
            
            if (verficadorAnagrama()) {
                                
                cantidadAnagramasCorrectos = cantidadAnagramasCorrectos + 1;
                
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
                                
        if (verificarNumeroOSimbolo(entrada)) {
            System.out.println("Error: Intenta de nuevo. No se permite ingresar Número o Símbolo");
            return false;
        }
        
        letrasPosibleAnagrama = entrada.toCharArray();
        
        if (verficadorAnagrama()) {
            
            System.out.println("Le atinaste :D");
            
            cantidadAnagramasOcultos = cantidadAnagramasOcultos - 1;
            
            int indice = 0;
            for (String anagramaOculto : tableroAnagramasOcultos) {
                
                if (anagramaOculto == null) {
                    tableroAnagramasOcultos[indice] = entrada;
                    return true;
                }
                
                indice = indice + 1;
            }
        } else {
            
            System.out.println("No le atinaste xd");
            
            cantidadIntentosPermitidos = cantidadIntentosPermitidos - 1;
            
            int indice = 0;
            for (String intentoFallido : tableroIntentosFallidos) {
                
                if (intentoFallido == null) {
                    tableroIntentosFallidos[indice] = entrada;
                    return true;
                }
                
                indice = indice + 1;
            }
        }
        return false;
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
                "   calculada automáticamente.\n" +
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
        
        System.out.println(ANSI_NEGRITA + ANSI_BLANCO + "Presiona ENTER para continuar" + ANSI_RESET);
    }
    
    private void mostrarAnagramasGUI() {
        
        limpiarPantalla();
        
        String letrasRevueltas =
                ANSI_AZUL + "================================================================================\n" + ANSI_RESET +
                ANSI_AMARILLO + "                           LETRAS DEL ANAGRAMA: " + ANSI_NEGRITA + ANSI_BLANCO + palabraLetrasRevueltas + "\n" + ANSI_RESET +
                ANSI_AZUL + "================================================================================\n" + ANSI_RESET;
        
        String cantidadIntentosPermitidosYAnagramasFaltantes = 
                ANSI_NEGRITA + ANSI_CYAN + "|CANTIDAD DE INTENTOS PERMITIDOS: " + ANSI_VERDE + cantidadIntentosPermitidos + ANSI_RESET +
                ANSI_NEGRITA + ANSI_CYAN + "      CANTIDAD DE ANAGRAMAS OCULTOS: " + ANSI_VERDE + cantidadAnagramasOcultos + "       |" + ANSI_RESET;
        
        String tableros = ANSI_NEGRITA + ANSI_AMARILLO +
                "--------------------------------------------------------------------------------\n" +
                "                        ░▀█▀░█▀█░█▀▄░█░░░█▀▀░█▀▄░█▀█░█▀▀\n" +
                "                        ░░█░░█▀█░█▀▄░█░░░█▀▀░█▀▄░█░█░▀▀█\n" +
                "                        ░░▀░░▀░▀░▀▀░░▀▀▀░▀▀▀░▀░▀░▀▀▀░▀▀▀\n" +
                "--------------------------------------------------------------------------------\n" + ANSI_RESET +
                ANSI_NEGRITA + ANSI_ROJO + "╦╔╗╔╔╦╗╔═╗╔╗╔╔╦╗╔═╗╔═╗                  " + ANSI_CYAN + "╔═╗╔╗╔╔═╗╔═╗╦═╗╔═╗╔╦╗╔═╗╔═╗\n" + ANSI_RESET +
                ANSI_NEGRITA + ANSI_ROJO + "║║║║ ║ ║╣ ║║║ ║ ║ ║╚═╗                  " + ANSI_CYAN + "╠═╣║║║╠═╣║ ╦╠╦╝╠═╣║║║╠═╣╚═╗\n" + ANSI_RESET +
                ANSI_NEGRITA + ANSI_ROJO + "╩╝╚╝ ╩ ╚═╝╝╚╝ ╩ ╚═╝╚═╝                  " + ANSI_CYAN + "╩ ╩╝╚╝╩ ╩╚═╝╩╚═╩ ╩╩ ╩╩ ╩╚═╝\n" + ANSI_RESET +
                ANSI_NEGRITA + ANSI_ROJO + "╔═╗╔═╗╦  ╦  ╦╔╦╗╔═╗╔═╗                  " + ANSI_CYAN + "   ╔══╗╔═╗╦ ╦╦ ╔╦╗╔═╗╔═╗   \n" + ANSI_RESET +
                ANSI_NEGRITA + ANSI_ROJO + "╠╣ ╠═╣║  ║  ║ ║║║ ║╚═╗                  " + ANSI_CYAN + "   ║  ║║  ║ ║║  ║ ║ ║╚═╗   \n" + ANSI_RESET +
                ANSI_NEGRITA + ANSI_ROJO + "╚  ╩ ╩╩═╝╩═╝╩═╩╝╚═╝╚═╝                  " + ANSI_CYAN + "   ╚══╝╚═╝╚═╝╩═╝╩ ╚═╝╚═╝   " + ANSI_RESET;
        
        System.out.println(letrasRevueltas + cantidadIntentosPermitidosYAnagramasFaltantes + tableros);
        
        for (String intentoFallido : tableroIntentosFallidos) {
            
            if (intentoFallido != null) {
                System.out.println(intentoFallido);
            }
        }
        
        for (String anagramaOculto : tableroAnagramasOcultos) {
            
            if (anagramaOculto != null) {
                System.out.println(anagramaOculto);
            }
        }
        
    }
    
    private String pedirEntradaAnagrama() {
        
        System.out.println(ANSI_AMARILLO + "(Entradas válidas: palabras sin símbolos y sin números)" + ANSI_RESET);
        System.out.print(ANSI_NEGRITA + "» " + ANSI_RESET);
        
        return scanner.nextLine().trim().toUpperCase();
    }
    //*****************************************
    
    //-------------------------------------
        
}
