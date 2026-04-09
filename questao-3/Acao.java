package jogo.personagens;

import java.io.Serializable;

public class Acao implements Serializable {
    public int tipo; // 1 = atacar, 2 = curar

    public Acao(int tipo) {
        this.tipo = tipo;
    }
}
