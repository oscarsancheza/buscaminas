package com.mcc;

public class Jugador {
  private String nombre;
  private Long score;

  public Jugador() {}

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getScore() {
    return score;
  }

  public void setScore(Long score) {
    this.score = score;
  }

  @Override
  public String toString() {
    return "Jugador:" + nombre + " " + ", score:" + score + "\n";
  }
}
