package jogo.personagens;

import java.io.Serializable;
import java.util.Random;

public abstract class Personagem implements Serializable {
    protected String nome;
    protected int vida;
    protected int vidaMaxima;
    protected int ataque;
    protected String tipo;
    private static final Random random = new Random();

    public Personagem(String nome, int vida, int ataque, String tipo) {
        this.nome = nome;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataque = ataque;
        this.tipo = tipo;
    }

    public void atacar(Personagem alvo) {
        int rolar = random.nextInt(20) + 1;
        boolean critico = rolar > 15;

        int dano = critico ? (int)(ataque * 1.3) : ataque;
        alvo.receberDano(dano);

        if (critico) {
            System.out.println(nome + " acertou um CRÍTICO! (rolou " + rolar + ") → " + dano + " de dano em " + alvo.getNome());
        } else {
            System.out.println(nome + " atacou (rolou " + rolar + ") → " + dano + " de dano em " + alvo.getNome());
        }
    }

    public String getNome() { return nome; }
    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public boolean estaVivo() { return vida > 0; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public void receberDano(int dano) {
        vida = Math.max(0, vida - dano);
    }

    public void curar(int quantidade) {
        vida = Math.min(vidaMaxima, vida + quantidade);
    }

    public void curar() {
        curar(5);
    }

    @Override
    public String toString() {
        return nome + " [" + vida + "/" + vidaMaxima + " HP]";
    }
}
