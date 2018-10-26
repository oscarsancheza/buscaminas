package com.mcc;

public class Casilla {
  private int minasAlrededor;
  private boolean esMina;
  private boolean esVisible;
  private boolean esBandera;

  @Override
  public String toString() {
    String resultado = "*  ";

    if (esMina && esVisible) {
      resultado = "X  ";
    } else if (esBandera) {
      resultado = "P  ";
    } else if (esVisible) {
      resultado = "" + minasAlrededor + "  ";
    }

    return resultado;
  }

  public void aumentarMinas() {
    minasAlrededor++;
  }

  public int getMinasAlrededor() {
    return minasAlrededor;
  }

  public void setMinasAlrededor(int minasAlrededor) {
    this.minasAlrededor = minasAlrededor;
  }

  public boolean esMina() {
    return esMina;
  }

  public void setEsMina(boolean esMina) {
    this.esMina = esMina;
  }

  public boolean esVisible() {
    return esVisible;
  }

  public void setEsVisible(boolean esVisible) {
    this.esVisible = esVisible;
  }

  public boolean esBandera() {
    return esBandera;
  }

  public void setEsBandera(boolean esBandera) {
    this.esBandera = esBandera;
  }
}
