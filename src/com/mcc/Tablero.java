package com.mcc;

import java.util.Random;

public class Tablero {

  public static final int FILAS_MAXIMO = 20;
  public static final int COLUMNAS_MAXIMO = 20;
  public static final int FILAS_MINIMO = 4;
  public static final int COLUMNAS_MINIMO = 4;

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
    int numMinas = this.numeroMinas;
    if (casillas != null && casillas.length > 0) {
      while (numMinas != 0) {

        random = new Random();

        int minaUno = (int) (random.nextDouble() * (filas));
        int minaDos = (int) (random.nextDouble() * (columnas));

        if (!casillas[minaUno][minaDos].esMina()) {
          casillas[minaUno][minaDos].setEsMina(true);
          numMinas--;

          int[][] datos = obtenerInicioFinCoordenada(minaUno, minaDos);
          int inicioFilas = datos[0][0];
          int finFilas = datos[0][1];

          int inicioColumnas = datos[1][0];
          int finColumnas = datos[1][1];

          for (int x = inicioFilas; x < finFilas; x++) {
            for (int y = inicioColumnas; y < finColumnas; y++) {
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
        || (fila <= 0 || fila > this.filas)
        || (columna <= 0 || columna > this.columnas)) {
      throw new IndexOutOfBoundsException("coordenada fuera del rango.");
    }

    revelarCasilla(fila - 1, columna - 1);

    mostrar();
  }

  public boolean esMina(int fila, int columna) {
    if (casillas == null
        || casillas.length == 0
        || (fila <= 0 || fila > this.filas)
        || (columna <= 0 || columna > this.columnas)) {
      throw new IndexOutOfBoundsException("coordenada fuera del rango.");
    }

    return (!casillas[fila - 1][columna - 1].esBandera()
        && casillas[fila - 1][columna - 1].esMina());
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

  public void mostrarMinas() {
    if (casillas != null && casillas.length > 0) {
      for (int i = 0; i < this.filas; i++) {
        for (int j = 0; j < this.columnas; j++) {
          if (this.casillas[i][j].esMina()) {
            this.casillas[i][j].setEsVisible(true);
          }
          System.out.print(this.casillas[i][j]);
        }
        System.out.println();
      }
    }
  }

  public void agregarBandera(int fila, int columna, boolean esBandera) {
    if (casillas == null
        || casillas.length == 0
        || (fila <= 0 || fila > this.filas)
        || (columna <= 0 || columna > this.columnas)) {
      throw new IndexOutOfBoundsException("coordenada fuera del rango.");
    }

    Casilla casilla = casillas[fila - 1][columna - 1];
    if (!casilla.esVisible()) {
      casilla.setEsBandera(esBandera);
    }
  }

  public boolean esTableroValido(int fil, int col) {
    return (fil > 0 && fil >= Tablero.FILAS_MINIMO && fil <= Tablero.FILAS_MAXIMO)
        && (col > 0 && col >= Tablero.COLUMNAS_MINIMO && col <= Tablero.COLUMNAS_MAXIMO);
  }

  public boolean esMinasValidas(int fil, int col, int minas) {
    return (minas > 0 && minas <= ((fil * col) - 9));
  }

  /**
   * @param fila
   * @param columna
   * @return arreglo de int donde la posicion [0][0] = inicioFilas [0][1] = finFilas [1][0] =
   *     inicioColumnas [1][1] = finFilas
   */
  private int[][] obtenerInicioFinCoordenada(int fila, int columna) {
    int[][] datos = new int[2][2];

    if (fila == 0) {
      datos[0][0] = 0;
      datos[0][1] = 2;
    } else {
      datos[0][0] = fila - 1;
      datos[0][1] = (fila == filas - 1) ? fila + 1 : fila + 2;
    }

    if (columna == 0) {
      datos[1][0] = 0;
      datos[1][1] = 2;
    } else {
      datos[1][0] = columna - 1;
      datos[1][1] = (columna == columnas - 1) ? columna + 1 : columna + 2;
    }

    return datos;
  }

  public boolean esGanador() {
    boolean gano = false;

    if (this.casillas != null && casillas.length > 0) {
      int numeroMinasConBanderas = 0;
      int visibles = 0;
      Casilla casilla;

      for (int i = 0; i < this.filas; i++) {
        for (int j = 0; j < this.columnas; j++) {
          casilla = this.casillas[i][j];
          if (casilla.esMina() && casilla.esBandera()) {
            numeroMinasConBanderas++;
          } else if (casilla.esVisible() && !casilla.esBandera()) {
            visibles++;
          }
        }
      }

      int totalVisibles = ((this.filas * this.columnas) - this.numeroMinas);
      if (totalVisibles == visibles && numeroMinasConBanderas == this.numeroMinas) {
        gano = true;
      }
    }

    return gano;
  }

  private void revelarCasilla(int fila, int columna) {
    Casilla casilla = this.casillas[fila][columna];

    if (casilla == null || casilla.esVisible() || casilla.esMina() || casilla.esBandera()) {
      return;
    }

    casilla.setEsVisible(true);

    if (casilla.getMinasAlrededor() == 0) {
      revelarSiguienteCasilla(fila, columna);
    }
  }

  private void revelarSiguienteCasilla(int fila, int columna) {

    int[][] datos = obtenerInicioFinCoordenada(fila, columna);
    int inicioFilas = datos[0][0];
    int finFilas = datos[0][1];

    int inicioColumnas = datos[1][0];
    int finColumnas = datos[1][1];

    for (int x = inicioFilas; x < finFilas; x++) {
      for (int y = inicioColumnas; y < finColumnas; y++) {
        if (!casillas[x][y].esMina()
            && !casillas[x][y].esBandera()
            && !casillas[x][y].esVisible()) {
          revelarCasilla(x, y);
        }
      }
    }
  }
}
