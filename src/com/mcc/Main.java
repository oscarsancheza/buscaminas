package com.mcc;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner cap;
    int opc = 0;
    Juego juego;
    String coordenada;
    String[] coordenadas;
    int fil;
    int col;
    int totalMinas;

    juego = new Juego();

    while (opc != 6) {

      System.out.println(
          "Seleccione un nivel:\n"
              + "1. Principiante\n"
              + "2. Intermedio\n"
              + "3. Experto\n"
              + "4. Personalizado\n"
              + "5. mostrarRankings\n"
              + "6. Salir");

      cap = new Scanner(System.in);

      try {
        opc = cap.nextInt();

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

            if (!juego.esTableroValido(fil, col)) {
              System.out.println("El minimo de filas son:" + Tablero.FILAS_MINIMO);
              System.out.println("El maximo de filas son:" + Tablero.FILAS_MAXIMO);
              System.out.println("El minimo de columnas son:" + Tablero.COLUMNAS_MINIMO);
              System.out.println("El maximo de columnas son:" + Tablero.COLUMNAS_MAXIMO);
            } else if (!juego.esMinasValidas(fil, col, totalMinas)) {
              System.out.println("Selecciona un numero valido de minas");
            } else {
              juego.personalizado(fil, col, totalMinas);
              juego.iniciar();
            }
            break;
          case 5:
            juego.mostrarRankings();
            break;
          case 6:
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
}
