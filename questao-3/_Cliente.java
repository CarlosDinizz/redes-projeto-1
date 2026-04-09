package jogo.personagens;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class _Cliente {

    public static void main(String argv[]) throws Exception 
    { 
        String sentence; 
        String modifiedSentence;

        Scanner sc = new Scanner(System.in);
        BufferedReader inFromUser = 
          new BufferedReader(new InputStreamReader(System.in));

        String enderecoIpServidor = "localhost";


        int personagem = 0;
        do {
            System.out.println("Escolha seu personagem: ");
            System.out.print("1. Guerreiro\n2.Mago\n3.Anão\nEscolha: ");
            personagem = sc.nextInt();
        } while (personagem != 1 && personagem != 2 && personagem != 3);

        Personagem personagemEscolhido = null;

        int precisaNome = -1;
        do {
            System.out.print("Quer nome para o seu personagem?\n1. Sim\n2. Não\nEscreva: ");
            try {
                precisaNome = sc.nextInt();
            } catch (InputMismatchException err){
                System.out.println("Digite 0 para não ou 1 para sim.");
            }
        } while (precisaNome != 1 && precisaNome != 2);

        String nome = "";
        if (personagem == 1){
            nome = "Guerreiro";
        } else if (personagem == 2){
            nome = "Mago";
        } else if (personagem == 3){
            nome = "Anão";
        }

        if (precisaNome == 1){
            System.out.print("Escreva o nome: ");
            sc.nextLine();
            nome = sc.nextLine();
        }

        if (personagem == 1){
            personagemEscolhido = new Guerreiro(nome);
        }
        else if (personagem == 2){
            personagemEscolhido = new Mago(nome);
        }
        else if (personagem == 3) {
            personagemEscolhido = new Anao(nome);
        }


        Socket clientSocket = new Socket(enderecoIpServidor, 6789);
        //System.out.println("Envie uma mensagem para o servidor:" );
        ObjectOutputStream outToServer =
          new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

        outToServer.writeObject(personagemEscolhido);
        outToServer.flush();

        Personagem personagemServidor = (Personagem) inFromServer.readObject();

        System.out.println("---------------------------------------------------");
        System.out.println("Seu personagem: " + personagemEscolhido.getTipo());
        System.out.println("Nome: " + personagemEscolhido.getNome());
        System.out.println("Vida: " + personagemEscolhido.getVida());
        System.out.print("\n");
        System.out.println("Personagem do oponente: " + personagemServidor.getTipo());
        System.out.println("Nome: " + personagemServidor.getNome());
        System.out.println("Vida: " + personagemServidor.getVida());
        System.out.println("---------------------------------------------------");


        System.out.println("Escolha sua ação: ");
        System.out.println("1. Atacar\n2. Curar-se");

        Acao acao = null;
        do {
            acao = new Acao(sc.nextInt());
        } while (acao.tipo != 1 && acao.tipo != 2);

        outToServer.writeObject(acao);
        outToServer.flush();

        Estado estado = (Estado) inFromServer.readObject();

        personagemEscolhido = estado.jogador1;
        personagemServidor = estado.jogador2;

        while (personagemEscolhido.estaVivo() && personagemServidor.estaVivo()){

            System.out.println("---------------------------------------------------");
            System.out.println("Seu personagem: " + personagemEscolhido.getTipo());
            System.out.println("Nome: " + personagemEscolhido.getNome());
            System.out.println("Vida: " + personagemEscolhido.getVida());
            System.out.print("\n");
            System.out.println("Personagem do oponente: " + personagemServidor.getTipo());
            System.out.println("Nome: " + personagemServidor.getNome());
            System.out.println("Vida: " + personagemServidor.getVida());
            System.out.println("---------------------------------------------------");


            System.out.println("Escolha sua ação: ");
            System.out.println("1. Atacar\n2. Curar-se");

            do {
                acao = new Acao(sc.nextInt());
            } while (acao.tipo != 1 && acao.tipo != 2);

            outToServer.writeObject(acao);
            outToServer.flush();

            estado = (Estado) inFromServer.readObject();

            personagemEscolhido = estado.jogador1;
            personagemServidor = estado.jogador2;
        }

        clientSocket.close();
        sc.close();

        if (personagemEscolhido.estaVivo()){
            System.out.println("Voce venceu!");
        }
        else {
            System.out.println("O oponente venceu.");
        }
  
    } 
}    
