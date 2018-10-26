package com.mcc;

import java.util.Scanner;

public class Main {

  static char[][] tablero;
  static char[][] minas;
  static int[][] solucion;
  static Scanner cap;

  public static void main(String[] args) {
    Scanner cap;
    int opc = 0;
    Juego juego;
    String coordenada;
    String[] coordenadas;
    int fil;
    int col;
    int totalMinas;

    while (opc != 5) {

      System.out.println(
          "Seleccione un nivel:\n"
              + "1. Principiante\n"
              + "2. Intermedio\n"
              + "3. Experto\n"
              + "4. Personalizado\n"
              + "5. Salir");

      cap = new Scanner(System.in);

      try {
        opc = cap.nextInt();
        juego = new Juego();

        switch (opc) {
          case 1:
            juego.principiante();
            juego.iniciar();
            break;
          case 2:
            juego.intermedio();
            juego.iniciar();
            break;
          case 3:
            juego.Experto();
            juego.iniciar();
            break;
          case 4:
            System.out.println("Ingresar filas,columnas y minas (fila,columna,minas):");
            cap = new Scanner(System.in);

            coordenada = cap.next(Juego.REGEX_COORDENADAS_MINAS);
            coordenadas = coordenada.split(",");

            fil = Integer.parseInt(coordenadas[0]);
            col = Integer.parseInt(coordenadas[1]);
            totalMinas = Integer.parseInt(coordenadas[2]);

            if (!juego.esPersonalizado(fil, col, totalMinas)) {
              System.out.println(
                  "El maximo de filas son:"
                      + Juego.FILAS_MAXIMO
                      + "\nEl minimo son:"
                      + Juego.FILAS_MINIMO
                      + "\nEl maximo de columnas son:"
                      + Juego.COLUMNAS_MAXIMO
                      + "\nEl minimo son:"
                      + Juego.COLUMNAS_MINIMO
                      + "\nEl limite de minas son:"
                      + ((fil * col) - 9)
                      + "\ny no deben ser menor igual a cero");
            }
            break;
          case 5:
            System.out.println("Salio del juego");
            break;
          default:
            System.out.println("Elija un opcion valida.");
            break;
        }
      } catch (Exception e) {
        System.out.println("Elija un opcion valida.");
      }
    }

  }

  public static void mostrarTablero() {
    for (int i = 0; i < tablero.length; i++) {
      for (int j = 0; j < tablero[0].length; j++) {
        System.out.print(tablero[i][j] + "\t");
      }
      System.out.println();
    }
  }

  public static void generarSolucion() {
    for (int i = 0; i < minas.length; i++) {
      for (int j = 0; j < minas[0].length; j++) {
        for (int k = -1; k <= 1; k++) {
          for (int l = -1; l <= 1; l++) {
            if ((i + k >= 0)
                && (j + l >= 0)
                && (i + k < minas.length)
                && (j + l < minas[0].length)) {
              if (minas[i][j] != 'X') {
                if (minas[i + k][j + l] == 'X') {
                  solucion[i][j]++;
                }
              } else {
                solucion[i][j] = -1;
              }
            }
          }
        }
      }
    }
  }



  public static boolean verficarVictoria(int cantidadMinas) {
    int posiblesMinas = 0;
    for (int i = 0; i < tablero.length; i++) {
      for (int j = 0; j < tablero[0].length; j++) {
        if (tablero[i][j] == '-') {
          posiblesMinas++;
        }
      }
    }
    if (posiblesMinas == cantidadMinas) {
      return true;
    }
    return false;
  }
}
