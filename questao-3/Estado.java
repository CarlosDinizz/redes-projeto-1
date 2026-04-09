package jogo.personagens;

import java.io.Serializable;

public class Estado implements Serializable {
    public Personagem jogador1;
    public Personagem jogador2;

    public Estado(Personagem j1, Personagem j2) {
        this.jogador1 = j1;
        this.jogador2 = j2;
    }
}
