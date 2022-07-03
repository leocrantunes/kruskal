package br.unirio.edu.apa;

public class Componente {
    private int verticePai;
    private int rank;

    public Componente(int verticePai) {
        this.verticePai = verticePai;
        this.rank = 0;
    }

    public int getVerticePai() {
        return verticePai;
    }

    public void setVerticePai(int verticePai) {
        this.verticePai = verticePai;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}