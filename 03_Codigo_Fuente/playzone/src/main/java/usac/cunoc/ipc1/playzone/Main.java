package usac.cunoc.ipc1.playzone;

import java.util.Random;
import java.util.Scanner;
import javax.print.attribute.HashPrintServiceAttributeSet;

public class Main {
    
    //=====================================
    //============ CODIGO ANSI ============
    //=====================================
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_NEGRITA = "\u001B[1m";
    private static final String ANSI_ROJO = "\u001B[31m";
    private static final String ANSI_VERDE = "\u001B[32m";
    private static final String ANSI_AMARILLO = "\u001B[33m";
    private static final String ANSI_AZUL = "\u001B[34m";
    private static final String ANSI_MORADO = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_BLANCO = "\u001B[37m";
    //-------------------------------------
    
    //================================================================
    //============ VARIABLES GLOBALES - ADIVINA ANAGRAMAS ============
    //================================================================
    private static final double PORCENTAJE_INTENTOS = 0.67;
    
    private int cantidadPartidasAbandonadasAA = 0;
    private int cantidadPartidasJugadasAA = 0;
    private int cantidadPartidasGanadasAA = 0;
    private int cantidadPartidasPerdidasAA = 0;
    private int cantidadPalabrasAcertadasAA = 0;
    private int cantidadPalabrasFallidasAA = 0;
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
    //----------------------------------------------------------------
    
    //==========================================================
    //============ VARIABLES GLOBALES - TIC TAC TOE ============
    //==========================================================
    private String[][] tableroTTT = new String[3][3];
    
    private int cantidadPartidasAbandonadasTTT = 0;
    private int cantidadPartidasJugadasTTT = 0;
    private int cantidadPartidasGanadasTTT = 0;
    private int cantidadPartidasEmpatadasTTT = 0;
    
    private int cantidadVictoriasComputadoraTTT = 0;
    
    private int rachaActualVictoriasTTT = 0;
    private int rachaActualDerrotasTTT = 0;
    
    private String fichaJugadorTTT = ANSI_NEGRITA + ANSI_CYAN + "[  X  ]" + ANSI_RESET;
    private String fichaComputadoraTTT = ANSI_NEGRITA + ANSI_ROJO + "[  O  ]" + ANSI_RESET;
    
    private int jugadasTotalesTTT = 0;
    //----------------------------------------------------------
    
    //=====================================================
    //============ VARIABLES GLOBALES - SUDOKU ============
    //=====================================================
    private final static int DIMENSION_SUDOKU = 9;
    private final static int CANTIDAD_PISTAS_INICIALES = 48;
    
    private String[][] tableroSudoku = new String[DIMENSION_SUDOKU][DIMENSION_SUDOKU];
    
    private int cantidadPartidasJugadasSudoku = 0;
    private int cantidadPartidasGanadasSudoku = 0;
    private int cantidadPartidasAbandonadasSudoku = 0;
    private int jugadasInvalidasSudoku = 0;
    private int cantidadJugadasTotalesSudoku = 0;
    
    private int[][] tableroSolucionSudoku = new int[DIMENSION_SUDOKU][DIMENSION_SUDOKU];
    private int[][] tableroJuegoSudoku = new int[DIMENSION_SUDOKU][DIMENSION_SUDOKU];
    private boolean[][] esCasillaFija = new boolean[DIMENSION_SUDOKU][DIMENSION_SUDOKU];
    
    private int filaSeleccionada = 0;
    private int columnaSeleccionada = 0;
    
    //************ BANCO DE TABLEROS - SUDOKU ************
    private final int[][][] BANCO_SUDOKUS = {
        {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        },
        {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 1, 5, 6, 4, 8, 9, 7},
            {5, 6, 4, 8, 9, 7, 2, 3, 1},
            {8, 9, 7, 2, 3, 1, 5, 6, 4},
            {3, 1, 2, 6, 4, 5, 9, 7, 8},
            {6, 4, 5, 9, 7, 8, 3, 1, 2},
            {9, 7, 8, 3, 1, 2, 6, 4, 5}
        },
        {
            {8, 2, 7, 1, 5, 4, 3, 9, 6},
            {9, 6, 5, 3, 2, 7, 1, 4, 8},
            {3, 4, 1, 6, 8, 9, 7, 5, 2},
            {5, 9, 3, 4, 6, 8, 2, 7, 1},
            {4, 7, 2, 5, 1, 3, 6, 8, 9},
            {6, 1, 8, 9, 7, 2, 4, 3, 5},
            {7, 8, 6, 2, 3, 5, 9, 1, 4},
            {1, 5, 4, 7, 9, 6, 8, 2, 3},
            {2, 3, 9, 8, 4, 1, 5, 6, 7}
        },
        {
            {2, 9, 5, 7, 4, 3, 8, 6, 1},
            {4, 3, 1, 8, 6, 5, 9, 2, 7},
            {8, 7, 6, 1, 9, 2, 5, 4, 3},
            {3, 8, 7, 4, 5, 9, 2, 1, 6},
            {6, 1, 2, 3, 7, 8, 4, 9, 5},
            {5, 4, 9, 2, 1, 6, 7, 3, 8},
            {7, 6, 3, 5, 2, 4, 1, 8, 9},
            {9, 2, 8, 6, 3, 1, 7, 5, 4},
            {1, 5, 4, 9, 8, 7, 3, 6, 2}
        }
    };
    //-----------------------------------------------------
    
    
    
    
    //==============================================
    //============ CLASES DE UTILIDADES ============
    //==============================================
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);
    //----------------------------------------------
    
    
    
    
    //===================================================
    //============ MÉTODOS - FLUJO PRINCIPAL ============
    //===================================================
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
            case 2:
                juegoTicTacToe();
                break;
            case 3:
                juegoSudoku();
                break;
            case 4:
                mostrarReportes();
                break;
            case 5: 
                salirDelPrograma = true;
            default:
                
        }
        
        } while (salirDelPrograma == false);
        
        System.out.println("Adiós :c");
        
    }
    //---------------------------------------------------
    
    
    
    
    //=====================================================
    //============ MÉTODOS - ADIVINA ANAGRAMAS ============
    //=====================================================
    public void juegoAdivinaAnagramas() {
        
        limpiarPantalla();
        mostrarReglasAA();
        
        String entradaUsuario;
        
        seleccionarAnagramas();
        
        revolverLetrasAA();
        
        cantidadIntentosPermitidos = calcularIntentosPermitidosAA();
        tableroIntentosFallidos = new String[cantidadIntentosPermitidos];
        
        cantidadAnagramasOcultos = cantidadAnagramasCorrectos;
        tableroAnagramasOcultos = new String[cantidadAnagramasOcultos];

        do {
            
            mostrarAnagramasGUI();
            
            do {
                
                entradaUsuario = pedirEntradaAnagrama();
                
                if (verificarAbandonarPartida(entradaUsuario)) {
                    cantidadPartidasAbandonadasAA = cantidadPartidasAbandonadasAA + 1;
                    return;
                }
                
            } while (verficarEntradaAA(entradaUsuario) == false);
            
        } while (cantidadIntentosPermitidos > 0 && cantidadAnagramasOcultos > 0);
        
        if (cantidadAnagramasOcultos == 0) {
            
            mostrarAnagramasGUI();
                
            mostrarVictoria();
            
            presionarENTER();

            cantidadPartidasGanadasAA = cantidadPartidasGanadasAA + 1;
            cantidadPartidasJugadasAA = cantidadPartidasJugadasAA + 1;
            
            cantidadPalabrasAcertadasAA = cantidadPalabrasAcertadasAA + cantidadPalabrasAcertadasLocal;
            cantidadPalabrasFallidasAA = cantidadPalabrasFallidasAA + cantidadPalabrasFallidasLocal;

        } else {
            
            mostrarAnagramasGUI();

            mostrarDerrota();
            
            mostrarAnagramasFaltantes();
            
            presionarENTER();

            cantidadPartidasPerdidasAA = cantidadPartidasPerdidasAA + 1;
            cantidadPartidasJugadasAA = cantidadPartidasJugadasAA + 1;

            cantidadPalabrasAcertadasAA = cantidadPalabrasAcertadasAA + cantidadPalabrasAcertadasLocal;
            cantidadPalabrasFallidasAA = cantidadPalabrasFallidasAA + cantidadPalabrasFallidasLocal;
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
        
        if (verficadorLongitudAA()== false) {
            return false;
        } else if (verificadorLetrasAA()== false) {
            return false;
        }
        
        return true;
    }
    
    private boolean verficadorLongitudAA() {
        
        if (letrasAnagrama.length == letrasPosibleAnagrama.length) {
            return true;
        }
        
        return false;
    }
    
    private boolean verificadorLetrasAA() {
        
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
    
    private void revolverLetrasAA() {
        
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
    
    private int calcularIntentosPermitidosAA() {
        return (int) Math.floor(cantidadAnagramasCorrectos * PORCENTAJE_INTENTOS);
    }
    
    private boolean verficarEntradaAA(String entrada) {
                                        
        if (verificarNumeroOSimboloAA(entrada) || entrada.length() < 3) {
            mostrarMensajeError("no debe de contener números o símbolos. Tampoco palabras menores de 3 letras");
            return false;
        }
        
        if (verificarYaIngresadaAA(entrada)) {
            mostrarMensajeError("la palabra ya fue ingresada. Intenta de nuevo :p");
            return false;
        } 
        
        if (verificarExisteEnAnagramasCorrectosAA(entrada)) {
            
            System.out.println("Le atinaste D:");
            
            cantidadAnagramasOcultos = cantidadAnagramasOcultos - 1;
            cantidadPalabrasAcertadasLocal = cantidadPalabrasAcertadasLocal + 1;
            
            almacenarEntradaAnagramasOcultosAA(entrada);
            
        } else {
                
            System.out.println("No le atinaste xd");

            cantidadIntentosPermitidos = cantidadIntentosPermitidos - 1;
            cantidadPalabrasFallidasLocal = cantidadPalabrasFallidasLocal + 1;

            almacenarEntradaIntentosFallidosAA(entrada);
        }
        
        return true;
    }
    
    private boolean verificarNumeroOSimboloAA(String entrada) {
        
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
    
    private boolean verificarYaIngresadaAA(String entrada) {
        
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
    
    private boolean verificarExisteEnAnagramasCorrectosAA(String entrada) {
        for (String anagramaCorrecto : anagramasCorrectos) {
            if (entrada.equalsIgnoreCase(anagramaCorrecto)) {
                return true;
            }
        }
        
        return false;
    }
    
    private void almacenarEntradaAnagramasOcultosAA(String entrada) {
        int indice = 0;
        for (String anagramaOculto : tableroAnagramasOcultos) {

            if (anagramaOculto == null) {
                tableroAnagramasOcultos[indice] = entrada;
                return;
            }

            indice = indice + 1;
        }
    }
    
    private void almacenarEntradaIntentosFallidosAA(String entrada) {
        int indice = 0;
            for (String intentoFallido : tableroIntentosFallidos) {

                if (intentoFallido == null) {
                    tableroIntentosFallidos[indice] = entrada;
                    return;
                }

                indice = indice + 1;
            }
    }
    //-----------------------------------------------------

    
    
    
    //===============================================
    //============ MÉTODOS - TIC TAC TOE ============
    //===============================================
    public void juegoTicTacToe() {
        
        limpiarPantalla();
        mostrarReglasTTT();
        
        String entradaJugador;
        
        int turnoComputadora = 1;
        
        int jugadasTotales = 0;
        
        boolean existeGanador = false;
        boolean existeEmpate = false;
        
        llenarTableroConCoordenadasTTT();
        
        do {
            
            limpiarPantalla();
            
            mostrarTableroTTT();
            
            //==== TURNO DEL JUGADOR ====
            do {
                
                entradaJugador = pedirCoordenadaTTT();
                
                if (verificarAbandonarPartida(entradaJugador)) {
                    cantidadPartidasAbandonadasTTT = cantidadPartidasAbandonadasTTT + 1;
                    return;
                }
                
            } while (verificarCoordenadaIngresadaTTT(entradaJugador) == false);
            
            jugadasTotales = jugadasTotales + 1;
            
            if (verificarVictoriaTTT(fichaJugadorTTT)) {
                
                existeGanador = true;
                
                cantidadPartidasGanadasTTT = cantidadPartidasGanadasTTT + 1;
                cantidadPartidasJugadasTTT = cantidadPartidasJugadasTTT + 1;
                
                rachaActualVictoriasTTT = rachaActualVictoriasTTT + 1;
                rachaActualDerrotasTTT = 0;
                
                jugadasTotalesTTT = jugadasTotalesTTT + jugadasTotales;
                
                mostrarTableroTTT();
                mostrarVictoria();
                presionarENTER();
                
            } else if (jugadasTotales == 9) {
                
                existeEmpate = true;
                
                cantidadPartidasEmpatadasTTT = cantidadPartidasEmpatadasTTT + 1;
                cantidadPartidasJugadasTTT = cantidadPartidasJugadasTTT + 1;
                
                jugadasTotalesTTT = jugadasTotalesTTT + jugadasTotales;
                
                mostrarTableroTTT();
                mostrarEmpate();
                presionarENTER();
            }
            
            //==== TURNO DE LA COMPUTADORA ====
            if (!existeGanador && !existeEmpate && jugadasTotales < 9) {
                
                decidirTurnoComputadoraTTT(turnoComputadora);
                
                turnoComputadora = turnoComputadora + 1;
                
                jugadasTotales = jugadasTotales + 1;
                
                if (verificarVictoriaTTT(fichaComputadoraTTT)) {
                    
                    existeGanador = true;
                    
                    cantidadVictoriasComputadoraTTT = cantidadVictoriasComputadoraTTT + 1;
                    cantidadPartidasJugadasTTT = cantidadPartidasJugadasTTT + 1;
                    
                    rachaActualDerrotasTTT = rachaActualDerrotasTTT + 1;
                    rachaActualVictoriasTTT = 0;
                    
                    jugadasTotalesTTT = jugadasTotalesTTT + jugadasTotales;
                    
                    mostrarTableroTTT();
                    mostrarDerrota();
                    presionarENTER();
                }
            }
            
        } while (!existeGanador && !existeEmpate);
        
    }
    
    private boolean verificarCoordenadaIngresadaTTT(String entrada) {
        
        String entradaConFormato = "[ " + entrada + " ]";
        
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                
                if (tableroTTT[fila][columna].equals(entradaConFormato)) {
                    tableroTTT[fila][columna] = fichaJugadorTTT;
                    return true;
                }
            }
        }
        
        mostrarMensajeError("la casilla seleccionada es inválida");
        return false;
    }
    
    private boolean verificarVictoriaTTT(String ficha) {
        return evaluarFilasTTT(ficha) || evaluarColumnasTTT(ficha) || evaluarDiagonalesTTT(ficha);
    }
    
    private boolean evaluarFilasTTT(String ficha) {
        
        for (int fila = 0; fila < 3; fila++) {
            
            if (tableroTTT[fila][0].equals(ficha) && 
                    tableroTTT[fila][1].equals(ficha) && 
                    tableroTTT[fila][2].equals(ficha)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean evaluarColumnasTTT(String ficha) {
        
        for (int columna = 0; columna < 3; columna++) {
            
            if (tableroTTT[0][columna].equals(ficha) && 
                    tableroTTT[1][columna].equals(ficha) && 
                    tableroTTT[2][columna].equals(ficha)) {
                return true;
            }    
        }
        
        return false;
    }
    
    private boolean evaluarDiagonalesTTT(String ficha) {
        
        if (tableroTTT[0][0].equals(ficha) && 
                tableroTTT[1][1].equals(ficha) && 
                tableroTTT[2][2].equals(ficha)) {
            return true;
        } else if (tableroTTT[0][2].equals(ficha) && 
                tableroTTT[1][1].equals(ficha) && 
                tableroTTT[2][0].equals(ficha)) {
            return true;
        }
        
        return false;
    }
    
    private void decidirTurnoComputadoraTTT(int turno) {
        
        if (turno == 1) {
            
            if (tableroTTT[1][1].equals("[ B 2 ]")) {
                tableroTTT[1][1] = fichaComputadoraTTT;
            } else {
                colocarEnEsquinaAleatoriaTTT(fichaComputadoraTTT);
            }
            
        } else {
            
            boolean existeCasillaParaGanar = intentarColocarParaGanarBloquearTTT(fichaComputadoraTTT, fichaComputadoraTTT);
            
            if (!existeCasillaParaGanar) {
                
                boolean existeCasillaParaBloquear = intentarColocarParaGanarBloquearTTT(fichaJugadorTTT, fichaComputadoraTTT);
                
                if (!existeCasillaParaBloquear) {
                    
                    colocarEnCasillaLibreTTT();
                }       
            }   
        }
    }
    
    private void colocarEnEsquinaAleatoriaTTT(String fichaComputadora) {
        
        int opcionDeEsquina;
        boolean fichaColocada = false;
        
        do {            
            
            opcionDeEsquina = random.nextInt(4);
            
            if (opcionDeEsquina == 0 && esCasillaVaciaTTT(0, 0)) {
                tableroTTT[0][0] = fichaComputadora;
                fichaColocada = true;
            } else if (opcionDeEsquina == 1 && esCasillaVaciaTTT(0, 2)) {
                tableroTTT[0][2] = fichaComputadora;
                fichaColocada = true;
            } else if (opcionDeEsquina == 2 && esCasillaVaciaTTT(2, 0)) {
                tableroTTT[2][0] = fichaComputadora;
                fichaColocada = true;
            } else if (opcionDeEsquina == 3 && esCasillaVaciaTTT(2, 2)) {
                tableroTTT[2][2] = fichaComputadora;
                fichaColocada = true;
            } 
            
        } while (fichaColocada == false);
        
    }
    
    private boolean intentarColocarParaGanarBloquearTTT(String fichaABuscar, String fichaAColocar) {
        
        //Revisa si hay 3 en raya en horizontal (revisa las 3 filas)
        for (int fila = 0; fila < 3; fila++) {
            if (evaluarCasillasYColocarTTT(fila,0, fila,1, fila,2, fichaABuscar, fichaAColocar)) {
                return true;
            }
        }
        
        //Revisa si hay 3 en raya en vertical (revisa las 3 columnas)
        for (int columna = 0; columna < 3; columna++) {
            if (evaluarCasillasYColocarTTT(0,columna, 1,columna, 2,columna, fichaABuscar, fichaAColocar)) {
                return true;
            }
        }
        
        //Revisa si hay 3 en raya en las dos diagonales
        if (evaluarCasillasYColocarTTT(0,0, 1,1, 2,2, fichaABuscar, fichaAColocar)) {
            return true;
        }
        if (evaluarCasillasYColocarTTT(0,2, 1,1, 2,0, fichaABuscar, fichaAColocar)) {
            return true;
        }
        
        return false;
    }
        
    private boolean evaluarCasillasYColocarTTT(int f1, int c1, int f2, int c2, int f3, int c3, String fichaABuscar, String fichaAColocar) {
        
        if (tableroTTT[f1][c1].equals(fichaABuscar) && 
                tableroTTT[f2][c2].equals(fichaABuscar) && 
                esCasillaVaciaTTT(f3, c3)) {
            tableroTTT[f3][c3] = fichaAColocar;
            return true;
        }
        
        if (tableroTTT[f1][c1].equals(fichaABuscar) && 
                esCasillaVaciaTTT(f2, c2) && 
                tableroTTT[f3][c3].equals(fichaABuscar)) {
            tableroTTT[f2][c2] = fichaAColocar;
            return true;
        }
        
        if (esCasillaVaciaTTT(f1, c1) && 
                tableroTTT[f2][c2].equals(fichaABuscar) && 
                tableroTTT[f3][c3].equals(fichaABuscar)) {
            tableroTTT[f1][c1] = fichaAColocar;
            return true;
        }
        
        return false;
    }
    
    private boolean esCasillaVaciaTTT(int fila, int columna) {
        return !tableroTTT[fila][columna].equals(fichaJugadorTTT) && !tableroTTT[fila][columna].equals(fichaComputadoraTTT);
    }
    
    private void colocarEnCasillaLibreTTT() {
        
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                
                if (esCasillaVaciaTTT(fila, columna)) {
                    tableroTTT[fila][columna] = fichaComputadoraTTT;
                    return;
                }
            }
        }
    }
    //-----------------------------------------------
    
    
    
    
    //==========================================
    //============ MÉTODOS - SUDOKU ============
    //==========================================
    public void juegoSudoku() {
        
        limpiarPantalla();
        mostrarReglasSudoku();
        
        String entradaCoordenada;
        String entradaNumero;
        boolean juegoTerminado = false;
        
        tableroSolucionSudoku = seleccionarTableroBaseSudoku();
        llenarTableroConCoordenadasSudoku();
        generarPistasAleatoriasSudoku(CANTIDAD_PISTAS_INICIALES);
        
        do {            
            
            limpiarPantalla();
            mostrarTableroSudoku();
            
            do {                
                
                entradaCoordenada = pedirCoordenadaSudoku();
                
                if (verificarAbandonarPartida(entradaCoordenada)) {
                    cantidadPartidasAbandonadasSudoku = cantidadPartidasAbandonadasSudoku + 1;
                    return;
                }
                
            } while (verificarCoordenadaValidaSudoku(entradaCoordenada) == false);
        
            do {       
                
                entradaNumero = pedirNumeroSudoku();
                
                if (verificarAbandonarPartida(entradaNumero)) {
                    cantidadPartidasAbandonadasSudoku = cantidadPartidasAbandonadasSudoku + 1;
                    return;
                }
                
            } while (verificarEntradaSudoku(Integer.parseInt(entradaNumero)));

            if (verificarVictoriaSudoku()) {
                juegoTerminado = true;
                
                cantidadPartidasGanadasSudoku = cantidadPartidasGanadasSudoku + 1;
                cantidadPartidasJugadasSudoku = cantidadPartidasJugadasSudoku + 1;
            }
            
        } while (juegoTerminado == false);
        
        limpiarPantalla();
        mostrarTableroSudoku();
        mostrarVictoria();
        presionarENTER();
    }
    
    private int[][] seleccionarTableroBaseSudoku() {
        int[][] tableroElegido;
        
        int indice = random.nextInt(BANCO_SUDOKUS.length);
        
        tableroElegido = new int[DIMENSION_SUDOKU][DIMENSION_SUDOKU];
        
        for (int i = 0; i < DIMENSION_SUDOKU; i++) {
            for (int j = 0; j < DIMENSION_SUDOKU; j++) {
                tableroElegido[i][j] = BANCO_SUDOKUS[indice][i][j];
            }
        }
        
        return tableroElegido;
    }
    
    private boolean verificarCoordenadaValidaSudoku(String entrada) {

        char[] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};

        for (int f = 0; f < DIMENSION_SUDOKU; f++) {
            for (int c = 0; c < DIMENSION_SUDOKU; c++) {

                String coordSinEspacio = "" + letras[f] + (c + 1);
                String coordConEspacio = "" + letras[f] + " " + (c + 1);

                if (coordSinEspacio.equals(entrada) || coordConEspacio.equals(entrada)) {
                    
                    if (esCasillaFija[f][c]) {
                        mostrarMensajeError("coordenada inválida. Es una casilla fija ._.");
                        return false;
                    } else {
                        filaSeleccionada = f;
                        columnaSeleccionada = c;
                        return true;
                    }
                }
            }
        }
        
        mostrarMensajeError("coordenada con formato inválido D:");
        return false;
    }
    
    private boolean verificarEntradaSudoku(int entradaACasilla) {
        
        if (entradaACasilla >= 1 && entradaACasilla <= 9) {
            procesarJugadaUsuarioSudoku(filaSeleccionada, columnaSeleccionada, entradaACasilla);
        }
        
        mostrarMensajeError("tiene que ser un número que no se repita en fila y columna :p");        
        return false;
    }
    
    private boolean validarReglasSudoku(int fila, int columna, int numero) {
        
        for (int posicionAEvaluar = 0; posicionAEvaluar < DIMENSION_SUDOKU; posicionAEvaluar++) {
            
            if ((posicionAEvaluar != columna && tableroJuegoSudoku[fila][posicionAEvaluar] == numero) ||
                    (posicionAEvaluar != fila && tableroJuegoSudoku[posicionAEvaluar][columna] == numero)) {
                return false;
            }
        }
        
        int inicioFila = (fila / 3) * 3;
        int inicioColumna = (columna / 3) * 3;

        for (int i = inicioFila; i < inicioFila + 3; i++) {
            for (int j = inicioColumna; j < inicioColumna + 3; j++) {
                
                if ((i != fila || j != columna) && tableroJuegoSudoku[i][j] == numero) {
                    return false;
                }
            }
        }
        
        cantidadJugadasTotalesSudoku = cantidadJugadasTotalesSudoku + 1;

        return true;
    }
    
    private void procesarJugadaUsuarioSudoku(int fila, int columna, int numero) {
        
        tableroJuegoSudoku[fila][columna] = numero;

        boolean cumpleReglas = validarReglasSudoku(fila, columna, numero);
        boolean esCorrectoSolucion = (numero == tableroJuegoSudoku[fila][columna]);

        if (cumpleReglas && esCorrectoSolucion) {
            tableroSudoku[fila][columna] = ANSI_NEGRITA + ANSI_AZUL + "[  " + numero + "  ]" + ANSI_RESET;
        } else {
            
            tableroSudoku[fila][columna] = ANSI_NEGRITA + ANSI_ROJO + "[  " + numero + "  ]" + ANSI_RESET;
            jugadasInvalidasSudoku = jugadasInvalidasSudoku + 1;
            
            System.out.println(ANSI_ROJO + "Advertencia: el número es infringe las reglas del Sudoku xd" + ANSI_RESET);
            presionarENTER();
        }
        
    }
    
    private boolean verificarVictoriaSudoku() {
        
        for (int i = 0; i < DIMENSION_SUDOKU; i++) {
            for (int j = 0; j < DIMENSION_SUDOKU; j++) {
                
                if (tableroJuegoSudoku[i][j] != tableroSolucionSudoku[i][j]) {
                    return false;
                }   
            }
        }
        
        return true;
    }
    //------------------------------------------
    
    //============================================
    //============ MÉTODOS - REPORTES ============
    //============================================
    public void mostrarReportes() {
        
        if (cantidadPartidasGanadasAA == 0) {
            cantidadPartidasGanadasAA = 1;
        }
        
        if (cantidadPartidasGanadasTTT == 0) {
            cantidadPartidasGanadasTTT = 1;
        }
        
        if (cantidadPartidasGanadasSudoku == 0) {
            cantidadPartidasGanadasSudoku = 1;
        }
        
        if (jugadasTotalesTTT == 0) {
            jugadasTotalesTTT = 1;
        }
        
        if (jugadasInvalidasSudoku == 0) {
            jugadasInvalidasSudoku = 1;
        }
        
        limpiarPantalla();
        
        mostrarTituloReportes();
        
        String barraAA = barraPorcentajeVictorias(cantidadPartidasJugadasAA, cantidadPartidasGanadasAA);
        mostrarTituloAA();
        
        System.out.println(ANSI_NEGRITA + ANSI_VERDE + "                             PORCENTAJE DE VICTORIAS" + ANSI_RESET);
        System.out.println("                          " + barraAA + " " + (cantidadPartidasJugadasAA + cantidadPartidasGanadasAA) + "%");
        System.out.println(ANSI_NEGRITA + ANSI_MORADO + "CANTIDAD DE PARTIDAS ABANDONADAS: " + ANSI_AZUL + cantidadPartidasAbandonadasAA + ANSI_RESET);
        System.out.println(ANSI_NEGRITA + ANSI_VERDE + "CANTIDAD DE PARTIDAS JUGADAS: " + ANSI_CYAN + cantidadPartidasJugadasAA + ANSI_RESET);
        System.out.println(ANSI_NEGRITA + ANSI_MORADO + "CANTIDAD DE PALABRAS ADIVINIDAS: " + ANSI_AZUL + cantidadPalabrasAcertadasAA + ANSI_RESET);
        System.out.println(ANSI_NEGRITA + ANSI_VERDE + "CANTIDAD DE INTENTOS FALLIDOS: " + ANSI_CYAN + cantidadPalabrasFallidasAA + ANSI_RESET);
        System.out.println();
        
        String barraTTT = barraPorcentajeVictorias(cantidadPartidasJugadasTTT, cantidadPartidasGanadasTTT);
        mostrarTituloTTT();
        
        System.out.println(ANSI_NEGRITA + ANSI_AZUL + "                             PORCENTAJE DE VICTORIAS" + ANSI_RESET);
        System.out.println("                          " + barraTTT + " " + (cantidadPartidasJugadasTTT + cantidadPartidasGanadasTTT) + "%");
        System.out.println(ANSI_NEGRITA + ANSI_ROJO + "CANTIDAD DE PARTIDAS ABANDONADAS: " + ANSI_CYAN + cantidadPartidasGanadasSudoku + ANSI_RESET);
        System.out.println(ANSI_NEGRITA + ANSI_AZUL + "CANTIDAD DE PARTIDAS GANADAS POR EL JUGADOR: " + ANSI_AZUL + cantidadPartidasGanadasSudoku + ANSI_RESET);
        System.out.println(ANSI_NEGRITA + ANSI_ROJO + "CANTIDAD DE PARTIDAS GANADAS POR LA COMPUTADORA: " + ANSI_CYAN + cantidadVictoriasComputadoraTTT + ANSI_RESET);
        System.out.println(ANSI_NEGRITA + ANSI_AZUL + "CANTIDAD DE PARTIDAS EMPATADAS: " + ANSI_AZUL + cantidadPartidasEmpatadasTTT + ANSI_RESET);
        System.out.println(ANSI_NEGRITA + ANSI_ROJO + "RACHA ACTUAL DE VICTORIAS: " + ANSI_CYAN + cantidadVictoriasComputadoraTTT + ANSI_RESET);
        System.out.println(ANSI_NEGRITA + ANSI_AZUL + "RACHA ACTUAL DE DERROTAS: " + ANSI_AZUL + cantidadPartidasEmpatadasTTT + ANSI_RESET);
        System.out.println(ANSI_NEGRITA + ANSI_ROJO + "PROMEDIO DE JUGADAS POR PARTIDA: " + ANSI_CYAN + (cantidadPartidasJugadasTTT / jugadasTotalesTTT) + ANSI_RESET);
        System.out.println();
        
        String barraSudoku = barraPorcentajeVictorias(cantidadPartidasJugadasSudoku, cantidadPartidasGanadasSudoku);
        mostrarTituloSudoku();
        
        System.out.println(ANSI_NEGRITA + ANSI_VERDE + "                             PORCENTAJE DE VICTORIAS" + ANSI_RESET);
        System.out.println("                          " + barraSudoku + " " + (cantidadPartidasJugadasSudoku + cantidadPartidasGanadasSudoku) + "%");
        System.out.println(ANSI_NEGRITA + ANSI_MORADO + "CANTIDAD DE PARTIDAS ABANDONADAS: " + ANSI_AZUL + cantidadPartidasAbandonadasSudoku + ANSI_RESET);
        System.out.println(ANSI_NEGRITA + ANSI_VERDE + "CANTIDAD DE PARTIDAS JUGADAS: " + ANSI_CYAN + cantidadPartidasJugadasSudoku + ANSI_RESET);
        System.out.println(ANSI_NEGRITA + ANSI_MORADO + "PROMEDIO DE JUGADAS INVÁLIDAS: " + ANSI_AZUL + (cantidadJugadasTotalesSudoku / jugadasInvalidasSudoku) + ANSI_RESET);
        System.out.println();
        
        System.out.println(ANSI_NEGRITA
                + ANSI_AMARILLO + "=================="
                + ANSI_BLANCO + "Presiona ENTER para volver al menú principal"
                + ANSI_AMARILLO + "=================="
                + ANSI_RESET);
        scanner.nextLine();
    }
    
    private void mostrarTituloReportes() {
        System.out.println(ANSI_NEGRITA + 
                "================================================================================\n" +
                "       ██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗███████╗███████╗\n" +
                "       ██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝██╔════╝██╔════╝\n" +
                "       ██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   █████╗  ███████╗\n" +
                "       ██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   ██╔══╝  ╚════██║\n" +
                "       ██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   ███████╗███████║\n" +
                "       ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚══════╝╚══════╝\n" + 
                "================================================================================\n" +
                ANSI_RESET);
    }
    
    private void mostrarTituloAA() {
        System.out.println(ANSI_MORADO + 
                "                 ╔═╗╔╦╗╦╦  ╦╦╔╗╔╔═╗  ╔═╗╔╗╔╔═╗╔═╗╦═╗╔═╗╔╦╗╔═╗╔═╗\n" +
                "                 ╠═╣ ║║║╚╗╔╝║║║║╠═╣  ╠═╣║║║╠═╣║ ╦╠╦╝╠═╣║║║╠═╣╚═╗\n" +
                "                 ╩ ╩═╩╝╩ ╚╝ ╩╝╚╝╩ ╩  ╩ ╩╝╚╝╩ ╩╚═╝╩╚═╩ ╩╩ ╩╩ ╩╚═╝" + 
                ANSI_RESET);
    }
    
    private void mostrarTituloTTT() {
        System.out.println(ANSI_ROJO + 
                "                              ╔╦╗╦╔═╗  ╔╦╗╔═╗╔═╗  ╔╦╗╔═╗╔═╗\n" +
                "                               ║ ║║     ║ ╠═╣║     ║ ║ ║║╣ \n" +
                "                               ╩ ╩╚═╝   ╩ ╩ ╩╚═╝   ╩ ╚═╝╚═╝" + 
                ANSI_RESET);
    }
    
    private void mostrarTituloSudoku() {
        System.out.println(ANSI_MORADO + 
                "                              ╔═╗╦ ╦╔╦╗╔═╗╦╔═╦ ╦\n" +
                "                              ╚═╗║ ║ ║║║ ║╠╩╗║ ║\n" +
                "                              ╚═╝╚═╝═╩╝╚═╝╩ ╩╚═╝" + 
                ANSI_RESET);
    }
    
    private String barraPorcentajeVictorias(int partidasJugadas, int victorias) {
        
        int longitud = 24;
        int barraPorcentaje = (partidasJugadas * longitud) / victorias;
        
        String barra = "";
        
        for (int i = 0; i < barraPorcentaje; i++) {
            barra = barra + "■";
        }
        for (int i = 0; i < longitud; i++) {
            barra = barra + ".";
        }
        
        return barra;
    }
    //--------------------------------------------
    
    
    
    
    //==============================================
    //============ MÉTODOS - UTILIDADES ============
    //==============================================
    
    //************ Limpiar pantalla ************ 
    public void limpiarPantalla() {
        System.out.println("\033[H\033[2J");
    }
    
    //************ Verificar si el jugador desea regresar al menú principal ************
    private boolean verificarAbandonarPartida(String entrada) {
        if (entrada.equalsIgnoreCase("salir")) {
            return true;
        }
        return false;
    }

    //************ Pedir Entrada - Menú principal ************
    private int pedirOpcion() {
        
        int opcion;
        
        mostrarPedirOpcion();
        
        System.out.println(ANSI_NEGRITA + ANSI_VERDE + 
                "                        Ingrese la opción que deseas :D " +
                ANSI_RESET);
        System.out.println(ANSI_AMARILLO + 
                "                /==============================================\\\n" +
                "                ||Entradas válidas: solo un número del 1 al 5 ||\n" +
                "                \\==============================================/) " 
                + ANSI_RESET);
        System.out.print(ANSI_NEGRITA + "» " + ANSI_RESET);
        opcion = Integer.valueOf(scanner.nextLine());
        
        return opcion;
    }
    
    //************ Pedir ENTER para continuar ************
    private void presionarENTER() {
        System.out.println(ANSI_NEGRITA + 
                ANSI_AMARILLO + "==========================" + 
                ANSI_BLANCO + "Presiona ENTER para continuar" + 
                ANSI_AMARILLO + "=========================" + 
                ANSI_RESET);
        scanner.nextLine();
    }
    //--------------------------------------------
    
    
    
    
    //=======================================
    //============ MÉTODOS - GUI ============
    //=======================================
    
    //****************************************
    //************ Menú principal ************
    //****************************************
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
    
    //********************************************************
    //************ Pedir entrada - Menú principal ************
    //********************************************************
    private void mostrarPedirOpcion() {
        
        String pedirOpcion = ANSI_NEGRITA + ANSI_CYAN +
                "                   ╔═╗╦  ╦╔═╗╔═╗  ╦ ╦╔╗╔╔═╗  ╔═╗╔═╗╔═╗╦╔═╗╔╗╔\n" +
                "                   ║╣ ║  ║║ ╦║╣   ║ ║║║║╠═╣  ║ ║╠═╝║  ║║ ║║║║\n" +
                "                   ╚═╝╩═╝╩╚═╝╚═╝  ╚═╝╝╚╝╩ ╩  ╚═╝╩  ╚═╝╩╚═╝╝╚╝\n" +
                ANSI_RESET;
        
        System.out.println("\n" + pedirOpcion);
    }
    
    //******************************************
    //************ Mensaje de error ************
    //******************************************
    private void mostrarMensajeError(String mensaje) {
        System.out.println(ANSI_NEGRITA + ANSI_ROJO + "Error: " + ANSI_BLANCO + mensaje + ANSI_RESET);
    }
    
    //****************************************************************
    //************ Mensajes de victoria, derrota y empate ************
    //****************************************************************
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
    
    private void mostrarEmpate() {
        
        System.out.println("\n" + ANSI_AMARILLO +
                "                .######..##...##..#####....####...######..######.\n" +
                "                .##......###.###..##..##..##..##....##....##.....\n" +
                "                .####....##.#.##..#####...######....##....####...\n" +
                "                .##......##...##..##......##..##....##....##.....\n" +
                "                .######..##...##..##......##..##....##....######.\n" +
                "                ................................................." +
                ANSI_RESET);
        
    }
    
    private void mostrarComandoMenu() {
        String comandoParaRegresar = ANSI_NEGRITA + ANSI_MORADO +
                "                    ╔──────────────────────────────────────╗\n" +
                "                    │Comando para regresar al menu: \"salir\"│\n" +
                "                    ╚──────────────────────────────────────╝\n" +
                ANSI_RESET;
        
        System.out.println(comandoParaRegresar);
    }
    //****************************************************************
    
    
    
    
    //*******************************************
    //************ Adivina Anagramas ************
    //*******************************************
    private void mostrarReglasAA() {
        
        String reglas = ANSI_NEGRITA + ANSI_CYAN + 
                "================================================================================\n" +
                "      ░█▀▄░█▀▀░█▀▀░█░░░█▀█░█▀▀░░░░░░░░░█▀█░█▀█░█▀█░█▀▀░█▀▄░█▀█░█▄█░█▀█░█▀▀\n" +
                "      ░█▀▄░█▀▀░█░█░█░░░█▀█░▀▀█░░░▄▄▄░░░█▀█░█░█░█▀█░█░█░█▀▄░█▀█░█░█░█▀█░▀▀█\n" +
                "      ░▀░▀░▀▀▀░▀▀▀░▀▀▀░▀░▀░▀▀▀░░░░░░░░░▀░▀░▀░▀░▀░▀░▀▀▀░▀░▀░▀░▀░▀░▀░▀░▀░▀▀▀\n" +
                "================================================================================\n\n" +
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
        
        mostrarComandoMenu();
        
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
        
        System.out.println(ANSI_NEGRITA + ANSI_VERDE + "                         Ingrese el intendo de anagrama" + ANSI_RESET);
        System.out.println(ANSI_AMARILLO + 
                "   /========================================================================\\\n" +
                "   ||Entradas válidas: palabras (A-Z) sin 'ñ', espacios, símbolos y números||\n" +
                "   \\========================================================================/" + ANSI_RESET);
        System.out.print(ANSI_NEGRITA + "» " + ANSI_RESET);
        
        return scanner.nextLine().trim().toUpperCase();
    }

    
    
    
    //*************************************
    //************ TIC TAC TOE ************
    //*************************************
    private void llenarTableroConCoordenadasTTT() {
        //FILA A
        tableroTTT[0][0] = "[ A 1 ]";
        tableroTTT[0][1] = "[ A 2 ]";
        tableroTTT[0][2] = "[ A 3 ]";
        
        //FILA B
        tableroTTT[1][0] = "[ B 1 ]";
        tableroTTT[1][1] = "[ B 2 ]";
        tableroTTT[1][2] = "[ B 3 ]";
        
        //FILA C
        tableroTTT[2][0] = "[ C 1 ]";
        tableroTTT[2][1] = "[ C 2 ]";
        tableroTTT[2][2] = "[ C 3 ]";
        
    }
    
    private void mostrarReglasTTT() {
        
        String reglas = ANSI_NEGRITA + ANSI_MORADO +
                "================================================================================\n" +
                "    ░█▀▄░█▀▀░█▀▀░█░░░█▀█░█▀▀░░░░░░░░░▀█▀░▀█▀░█▀▀░░░▀█▀░█▀█░█▀▀░░░▀█▀░█▀█░█▀▀\n" +
                "    ░█▀▄░█▀▀░█░█░█░░░█▀█░▀▀█░░░▄▄▄░░░░█░░░█░░█░░░░░░█░░█▀█░█░░░░░░█░░█░█░█▀▀\n" +
                "    ░▀░▀░▀▀▀░▀▀▀░▀▀▀░▀░▀░▀▀▀░░░░░░░░░░▀░░▀▀▀░▀▀▀░░░░▀░░▀░▀░▀▀▀░░░░▀░░▀▀▀░▀▀▀\n" +
                "================================================================================\n\n" + 
                ANSI_CYAN +
                "1. OBJETIVO: Alinea 3 de tus fichas en línea recta, ya sea horizontal, vertical\n" +
                "   o diagonal, antes de que la computadora lo consiga primero xd.\n\n" +
                ANSI_AMARILLO +
                "2. COORDENADAS: Para realizar una jugada, ingresa la coordenada de la\n" +
                "   casilla disponible que mirás tal cual en el tablero (ejemplo: A 1, B 2, C 3).\n\n" +
                ANSI_CYAN +
                "3. TURNO DE LA COMPUTADORA: Justo después de tu movimiento, la computadora\n" +
                "   colocará su ficha buscando bloquearte o armar su jugada.\n\n" +
                ANSI_AMARILLO +
                "4. COMANDO DE SALIDA: Puedes escribir la palabra SALIR en cualquier\n" +
                "   momento si deseas abandonar la partida y volver al menú.\n\n" +
                ANSI_CYAN +
                "5. FIN DEL JUEGO: Ganas al hacer 3 en raya. Pierdes si la computadora\n" +
                "   completa su línea primero, o empatan si se llenan las 9 casillas.\n" +
                ANSI_RESET;
        
        System.out.println(reglas);
        presionarENTER();
    }

    private void mostrarTableroTTT() {
        
        mostrarComandoMenu();
        
        String tablero = ANSI_NEGRITA + ANSI_AZUL + 
                "           ████████╗ █████╗ ██████╗ ██╗     ███████╗██████╗  ██████╗ \n" +
                "           ╚══██╔══╝██╔══██╗██╔══██╗██║     ██╔════╝██╔══██╗██╔═══██╗\n" +
                "              ██║   ███████║██████╔╝██║     █████╗  ██████╔╝██║   ██║\n" +
                "              ██║   ██╔══██║██╔══██╗██║     ██╔══╝  ██╔══██╗██║   ██║\n" +
                "              ██║   ██║  ██║██████╔╝███████╗███████╗██║  ██║╚██████╔╝\n" +
                "              ╚═╝   ╚═╝  ╚═╝╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═╝ ╚═════╝ " + 
                ANSI_RESET;
        
        String ticTacToe = ANSI_NEGRITA + ANSI_MORADO +
                "  ______                  ______                       ______                   \n" +
                " /\\__  _\\__              /\\__  _\\                     /\\__  _\\                  \n" +
                " \\/_/\\ \\/\\_\\    ___      \\/_/\\ \\/    __      ___      \\/_/\\ \\/   ___      __    \n" +
                "    \\ \\ \\/\\ \\  /'___\\       \\ \\ \\  /'__`\\   /'___\\       \\ \\ \\  / __`\\  /'__`\\  \n" +
                "     \\ \\ \\ \\ \\/\\ \\__/        \\ \\ \\/\\ \\L\\.\\_/\\ \\__/        \\ \\ \\/\\ \\L\\ \\/\\  __/  \n" +
                "      \\ \\_\\ \\_\\ \\____\\        \\ \\_\\ \\__/.\\_\\ \\____\\        \\ \\_\\ \\____/\\ \\____\\ \n" +
                "       \\/_/\\/_/\\/____/         \\/_/\\/__/\\/_/\\/____/         \\/_/\\/___/  \\/____/ \n";
        
        System.out.println(tablero);
        System.out.println(ticTacToe);
        
        System.out.println(ANSI_NEGRITA + ANSI_BLANCO + "================================================================================\n" + ANSI_RESET);
        
        for (int i = 0; i < 3; i++) {
            System.out.print("                           ");
            for (int j = 0; j < 3; j++) {
                System.out.print(tableroTTT[i][j]);
                if (j < 2) {
                    System.out.print(ANSI_NEGRITA + ANSI_AZUL + " ║ " + ANSI_RESET);
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println(ANSI_NEGRITA + ANSI_AZUL + "                           ════════╬═════════╬════════" + ANSI_RESET);
            }
        }
        System.out.println();
        System.out.println(ANSI_NEGRITA + ANSI_BLANCO + "================================================================================\n" + ANSI_RESET);
    }
    
    private String pedirCoordenadaTTT() {
        
        System.out.println(ANSI_NEGRITA + ANSI_VERDE + "                    Ingrese la casilla para colocar la ficha" + ANSI_RESET);
        System.out.println(ANSI_AMARILLO + 
                " /============================================================================\\\n" +
                " ||Entradas válidas: coordenadas como se ven en pantalla. Ejemplos: A 1, B 2 ||\n" +
                " \\============================================================================/" + 
                ANSI_RESET);
        System.out.print(ANSI_NEGRITA + "» " + ANSI_RESET);

        return scanner.nextLine().trim().toUpperCase();
    }
    
    
    
    
    //********************************
    //************ SUDOKU ************
    //********************************
    private void llenarTableroConCoordenadasSudoku() {
        
        char[] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        
        for (int i = 0; i < DIMENSION_SUDOKU; i++) {
            for (int j = 0; j < DIMENSION_SUDOKU; j++) {
                
                tableroJuegoSudoku[i][j] = 0;
                
                esCasillaFija[i][j] = false;
                
                tableroSudoku[i][j] = "[ " + letras[i] + " " + (j + 1) + " ]";
                
            }
        }
        
    }

    private void generarPistasAleatoriasSudoku(int cantidadPistas) {
        
        int contador = 0;
        int fila;
        int columna;
        
        while (contador < cantidadPistas) {            
            
            fila = random.nextInt(DIMENSION_SUDOKU);
            columna = random.nextInt(DIMENSION_SUDOKU);
            
            if (esCasillaFija[fila][columna] == false) {
                
                esCasillaFija[fila][columna] = true;
                
                tableroJuegoSudoku[fila][columna] = tableroSolucionSudoku[fila][columna]; 
                
                tableroSudoku[fila][columna] = ANSI_NEGRITA + ANSI_VERDE +
                        "[  " + tableroSolucionSudoku[fila][columna] + "  ]" +
                        ANSI_RESET;
                
                contador = contador + 1;
            }
            
        }
        
    }

    private void mostrarReglasSudoku() {
        
        String reglas = ANSI_NEGRITA + ANSI_AMARILLO +
                "================================================================================\n" +
                "            ░█▀▄░█▀▀░█▀▀░█░░░█▀█░█▀▀░░░░░░░░░█▀▀░█░█░█▀▄░█▀█░█░█░█░█\n" +
                "            ░█▀▄░█▀▀░█░█░█░░░█▀█░▀▀█░░░▄▄▄░░░▀▀█░█░█░█░█░█░█░█▀▄░█░█\n" +
                "            ░▀░▀░▀▀▀░▀▀▀░▀▀▀░▀░▀░▀▀▀░░░░░░░░░▀▀▀░▀▀▀░▀▀░░▀▀▀░▀░▀░▀▀▀\n" + 
                "================================================================================\n\n" +
                ANSI_CYAN +
                "1. OBJETIVO: Rellena el tablero de 9x9 de modo que cada fila, columna\n" +
                "   y caja de 3x3 contenga todos los números del 1 al 9 sin repetir.\n\n" +
                ANSI_VERDE +
                "2. COORDENADAS: Ingresa la coordenada que ves en la casilla, por ejemplo: A 1.\n" +
                "   Las casillas con números iniciales en Blanco Fijo NO se pueden cambiar.\n\n" + 
                ANSI_CYAN + 
                "3. COLORES: Los números correctos ingresados se mostrarán en " + ANSI_AZUL + "AZUL" + ANSI_CYAN + ",\n" +
                "   mientras que los incorrectos o conflictivos se mostrarán en " + ANSI_ROJO + "ROJO" + ANSI_CYAN + ".\n\n" +
                ANSI_VERDE +
                "4. COMANDO DE SALIDA: Puedes escribir SALIR en cualquier momento si deseas\n" +
                "   abandonar la partida y volver al menú principal.\n\n" +
                ANSI_RESET;
        
        System.out.println(reglas);
        presionarENTER();
    }
    
    private void mostrarTableroSudoku() {
        
        mostrarComandoMenu();
        
        String tablero = ANSI_NEGRITA + ANSI_MORADO +
                "           ████████╗ █████╗ ██████╗ ██╗     ███████╗██████╗  ██████╗ \n" +
                "           ╚══██╔══╝██╔══██╗██╔══██╗██║     ██╔════╝██╔══██╗██╔═══██╗\n" +
                "              ██║   ███████║██████╔╝██║     █████╗  ██████╔╝██║   ██║\n" +
                "              ██║   ██╔══██║██╔══██╗██║     ██╔══╝  ██╔══██╗██║   ██║\n" +
                "              ██║   ██║  ██║██████╔╝███████╗███████╗██║  ██║╚██████╔╝\n" +
                "              ╚═╝   ╚═╝  ╚═╝╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═╝ ╚═════╝ \n\n" +
                ANSI_AZUL +
                "               ███████╗██╗   ██╗██████╗  ██████╗ ██╗  ██╗██╗   ██╗   \n" +
                "               ██╔════╝██║   ██║██╔══██╗██╔═══██╗██║ ██╔╝██║   ██║   \n" +
                "               ███████╗██║   ██║██║  ██║██║   ██║█████╔╝ ██║   ██║   \n" +
                "               ╚════██║██║   ██║██║  ██║██║   ██║██╔═██╗ ██║   ██║   \n" +
                "               ███████║╚██████╔╝██████╔╝╚██████╔╝██║  ██╗╚██████╔╝   \n" +
                "               ╚══════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝ ╚═════╝    \n" +
                ANSI_RESET;
        
        System.out.println(tablero);
        
        System.out.println(ANSI_NEGRITA + ANSI_AMARILLO + "================================================================================" + ANSI_RESET);

        for (int i = 0; i < 9; i++) {
            
            System.out.print("   ");
            
            for (int j = 0; j < 9; j++) {
                
                System.out.print(tableroSudoku[i][j]);

                if (j == 2 || j == 5) {
                    System.out.print(ANSI_NEGRITA + ANSI_AMARILLO + " ║ " + ANSI_RESET);
                } else if (j < 8) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            if (i == 2 || i == 5) {
                System.out.println(ANSI_NEGRITA + ANSI_AMARILLO + "   ════════════════════════╬═════════════════════════╬════════════════════════" + ANSI_RESET);
            }
        }
        
        System.out.println(ANSI_NEGRITA + ANSI_AMARILLO + "================================================================================\n" + ANSI_RESET);
    }
    
    private String pedirCoordenadaSudoku() {
        
        System.out.println(ANSI_NEGRITA + ANSI_VERDE + "                  Ingrese la coordenada de casilla a modificar" + ANSI_RESET);
        System.out.println(ANSI_AMARILLO + 
                "            /======================================================\\\n" +
                "            ||Entradas válidas: letra y número. Ejemplo: A 1 o A1 ||\n" +
                "            \\======================================================/" + ANSI_RESET);
        System.out.print(ANSI_NEGRITA + "» " + ANSI_RESET);
        
        return scanner.nextLine().trim().toUpperCase();
    }
    
    private String pedirNumeroSudoku() {
        
        System.out.println(ANSI_NEGRITA + ANSI_VERDE + "                    Ingrese el número a colocar en la casilla");
        System.out.println(ANSI_AMARILLO +
                "                /==============================================\\\n" +
                "                ||Entradas válidas: solo un número del 1 al 9 ||\n" +
                "                \\==============================================/" + ANSI_RESET);
        System.out.print(ANSI_NEGRITA + "» " + ANSI_RESET);
        
        return scanner.nextLine().trim();
    }
    //-------------------------------------
}
