package com.mcc;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Juego {

  public final String REGEX_COORDENADAS = "(^[0-9]+(,[0-9]+))?$";
  private Tablero tablero;
  private Jugador jugador;
  private List<Jugador> rankings;

  public static final String REGEX_COORDENADAS_MINAS = "(^[0-9]+(,[0-9]+)(,[0-9]+))?$";

  public Juego() {
    rankings = new ArrayList<>();
  }

  public void iniciar() {

    if (this.tablero != null) {
      this.tablero.mostrar();
      this.tablero.ocultar();
    }

    Long millis = System.currentTimeMillis();
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
              this.tablero.mostrarMinas();
              System.out.println(
                  "Score:" + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - millis));
            } else {
              this.tablero.mostrar(fil, col);
              if (esGanador()) {
                System.out.println("Ha ganado la partida!!!");
                ingresarGanador(
                    TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - millis));
                mostrarRankings();
                opc = 4;
              }
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
            if (esGanador()) {
              System.out.println("Ha ganado la partida!!!");
              ingresarGanador(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - millis));
              mostrarRankings();
              opc = 4;
            }
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

  public void ingresarGanador(Long score) {
    Scanner scanner = new Scanner(System.in);
    jugador = new Jugador();

    System.out.println("Ingresar nombre Jugador:");

    String nombre = scanner.next();
    jugador.setNombre(nombre);
    jugador.setScore(score);
    rankings.add(jugador);
  }

  public void mostrarRankings() {
    if (!rankings.isEmpty()) {
      rankings.sort(
          (j1, j2) ->
              j1.getScore() > j2.getScore() ? 2 : j1.getScore().equals(j2.getScore()) ? 0 : -1);

      for (Jugador item : rankings) {
        System.out.println(item);
      }
    } else {
      System.out.println("No existe ningun score.");
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

  public void personalizado(int filas, int columnas, int minas) {
    tablero = new Tablero(filas, columnas, minas);
  }

  public boolean esTableroValido(int fil, int col) {
    return ((fil > 0 && fil >= Tablero.FILAS_MINIMO && fil <= Tablero.FILAS_MAXIMO)
        && (col > 0 && col >= Tablero.COLUMNAS_MINIMO && col <= Tablero.COLUMNAS_MAXIMO));
  }

  public boolean esGanador() {
    return (this.tablero != null && this.tablero.esGanador());
  }

  public boolean esMinasValidas(int fil, int col, int minas) {
    return (minas > 0 && minas <= ((fil * col) - 9));
  }
}
