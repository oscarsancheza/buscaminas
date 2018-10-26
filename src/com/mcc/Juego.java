package com.mcc;

import java.util.Scanner;

public class Juego {

  public final String REGEX_COORDENADAS = "(^[0-9](,[0-9]+))?$";
  private Tablero tablero;

  public static final String REGEX_COORDENADAS_MINAS = "(^[0-9](,[0-9]+)(,[0-9]+))?$";
  public static final int FILAS_MAXIMO = 20;
  public static final int COLUMNAS_MAXIMO = 20;
  public static final int FILAS_MINIMO = 4;
  public static final int COLUMNAS_MINIMO = 4;

  public Juego() {}

  public void iniciar() {
    if (this.tablero != null) {
      this.tablero.mostrar();
      this.tablero.ocultar();
    }

    String coordenada;
    String[] coordenadas;
    int fil;
    int col;

    int opc = 0;
    Scanner cap;
    while (opc != 4) {
      System.out.println(
          "Seleccione una opcion:\n"
              + "1. ingresar coordenada\n"
              + "2. Ingresar bandera\n"
              + "3. Quitar bandera\n"
              + "4. Salir\n");

      cap = new Scanner(System.in);

      try {
        opc = cap.nextInt();

        switch (opc) {
          case 1:
            this.tablero.mostrar();
            System.out.println("Ingresar coordenada (fila,columna):");
            cap = new Scanner(System.in);

            coordenada = cap.next(REGEX_COORDENADAS);
            coordenadas = coordenada.split(",");

            fil = Integer.parseInt(coordenadas[0]);
            col = Integer.parseInt(coordenadas[1]);

            if (this.tablero.esMina(fil, col)) {
              opc = 4;
              System.out.println("Ha terminado el juego...");
              this.tablero.mostrarTodos();
            } else {
              this.tablero.mostrar(fil, col);
            }
            break;
          case 2:
          case 3:
            System.out.println("Ingresar coordenada (fila,columna):");
            cap = new Scanner(System.in);

            coordenada = cap.next(REGEX_COORDENADAS);
            coordenadas = coordenada.split(",");

            fil = Integer.parseInt(coordenadas[0]);
            col = Integer.parseInt(coordenadas[1]);

            this.tablero.agregarBandera(fil, col, (opc == 2));
            this.tablero.mostrar();
            break;
          case 4:
            System.out.println("Ha terminado el juego...");
            break;
          default:
            System.out.println("Elija un opcion valida.");
            break;
        }

      } catch (IndexOutOfBoundsException e) {
        System.out.println(e.getMessage());
      } catch (Exception e) {
        System.out.println("Elija un opcion valida.");
      }
    }
  }

  public void principiante() {
    tablero = new Tablero(8, 8, 10);
  }

  public void intermedio() {
    tablero = new Tablero(16, 16, 40);
  }

  public void Experto() {
    tablero = new Tablero(16, 30, 99);
  }

  public boolean esPersonalizado(int filas, int columnas, int minas) {
    boolean esPersonalizado = true;

    if ((minas <= 0 || minas >= ((filas * columnas) - 9))
        || (filas < 0 || filas < FILAS_MINIMO || filas > FILAS_MAXIMO)
        || (columnas < 0 || columnas < COLUMNAS_MINIMO || columnas > COLUMNAS_MAXIMO)) {
      esPersonalizado = false;
    } else {
      tablero = new Tablero(filas, columnas, minas);
    }

    return esPersonalizado;
  }
}
