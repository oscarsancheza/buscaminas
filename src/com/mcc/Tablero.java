package com.mcc;

import java.util.Random;

public class Tablero {

  private Casilla[][] casillas;
  private int filas;
  private int columnas;
  private int numeroMinas;

  public Tablero(int filas, int columnas, int numeroMinas) {
    this.numeroMinas = numeroMinas;
    this.filas = filas;
    this.columnas = columnas;
    crear();
    colocarMinas();
  }

  public void ocultar() {
    if (casillas != null && casillas.length > 0) {
      for (int i = 0; i < this.filas; i++) {
        for (int j = 0; j < this.columnas; j++) {
          this.casillas[i][j].setEsVisible(false);
        }
      }
    }
  }

  private void crear() {
    casillas = new Casilla[filas][columnas];
    Casilla casilla;
    for (int i = 0; i < filas; i++) {
      for (int j = 0; j < columnas; j++) {
        casilla = new Casilla();
        casilla.setMinasAlrededor(0);
        casilla.setEsVisible(true);
        casillas[i][j] = casilla;
      }
    }
  }

  private void colocarMinas() {
    Random random;
    if (casillas != null && casillas.length > 0) {
      while (numeroMinas != 0) {

        random = new Random();

        int minaUno = (int) (random.nextDouble() * (filas - 1));
        int minaDos = (int) (random.nextDouble() * (columnas - 1));

        if (!casillas[minaUno][minaDos].esMina()) {
          casillas[minaUno][minaDos].setEsMina(true);
          numeroMinas--;

          int iniciaFilas;
          int fil;
          if (minaUno == 0 || minaUno == filas - 1) {
            fil = 2;
            iniciaFilas = 0;
          } else {
            fil = minaUno + 2;
            iniciaFilas = minaUno - 1;
          }

          int iniciaColumnas;
          int col;
          if ((minaDos == columnas - 1) || (minaDos == 0)) {
            col = 2;
            iniciaColumnas = 0;
          } else {
            col = minaDos + 2;
            iniciaColumnas = minaDos - 1;
          }

          for (int x = iniciaFilas; x < fil; x++) {
            for (int y = iniciaColumnas; y < col; y++) {
              if (!casillas[x][y].esMina()) {
                casillas[x][y].aumentarMinas();
              }
            }
          }
        }
      }
    }
  }

  public void mostrar(int fila, int columna) {

    if (casillas == null
        || casillas.length == 0
        || (fila < 0 || fila > this.filas)
        || (columna < 0 || columna > this.columnas)) {
      throw new IndexOutOfBoundsException("coordenada fuera del rango.");
    }

    Casilla casilla = casillas[fila - 1][columna - 1];

    if (!casilla.esBandera()) {
      casilla.setEsVisible(true);
    }
    mostrar();
  }

  public boolean esMina(int fila, int columna) {
    if (casillas == null
        || casillas.length == 0
        || (fila < 0 || fila > this.filas)
        || (columna < 0 || columna > this.columnas)) {
      throw new IndexOutOfBoundsException("coordenada fuera del rango.");
    }

    return casillas[fila - 1][columna - 1].esMina();
  }

  public void mostrar() {
    if (casillas != null && casillas.length > 0) {
      for (int i = 0; i < this.filas; i++) {
        for (int j = 0; j < this.columnas; j++) {
          System.out.print(this.casillas[i][j]);
        }
        System.out.println();
      }
    }
  }

  public void mostrarTodos() {
    if (casillas != null && casillas.length > 0) {
      for (int i = 0; i < this.filas; i++) {
        for (int j = 0; j < this.columnas; j++) {
          this.casillas[i][j].setEsVisible(true);
          System.out.print(this.casillas[i][j]);
        }
        System.out.println();
      }
    }
  }

  public void agregarBandera(int fila, int columna, boolean esBandera) {
    if (casillas == null
        || casillas.length == 0
        || (fila < 0 || fila > this.filas)
        || (columna < 0 || columna > this.columnas)) {
      throw new IndexOutOfBoundsException("coordenada fuera del rango.");
    }

    Casilla casilla = casillas[fila - 1][columna - 1];
    if (!casilla.esVisible()) {
      casilla.setEsBandera(esBandera);
    }
  }
}
