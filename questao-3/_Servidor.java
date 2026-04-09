package jogo.personagens;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class _Servidor {

  public static void main(String argv[]) throws Exception {
    Scanner sc = new Scanner(System.in);

    ServerSocket welcomeSocket = new ServerSocket(6789);

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
    } else if (personagem == 2){
        personagemEscolhido = new Mago(nome);
    } else if (personagem == 3) {
        personagemEscolhido = new Anao(nome);
    }

    Socket connectionSocket = welcomeSocket.accept();
    System.out.println("Conexão estabelecida de " + connectionSocket.getInetAddress());

    ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
    ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());

    Personagem cliente = (Personagem) inFromClient.readObject();
    outToClient.writeObject(personagemEscolhido);
    outToClient.flush();

    System.out.println("---------------------------------------------------");
    System.out.println("Seu personagem: " + personagemEscolhido.getTipo());
    System.out.println("Nome: " + personagemEscolhido.getNome());
    System.out.println("Vida: " + personagemEscolhido.getVida());
    System.out.print("\n");
    System.out.println("Personagem do oponente: " + cliente.getTipo());
    System.out.println("Nome: " + cliente.getNome());
    System.out.println("Vida: " + cliente.getVida());
    System.out.println("---------------------------------------------------");

    // Primeiro turno
    Acao acaoCliente = (Acao) inFromClient.readObject();
    if (acaoCliente.tipo == 1) {
        cliente.atacar(personagemEscolhido);
    } else {
        cliente.curar();
    }

    System.out.println("Escolha sua ação: ");
    System.out.println("1. Atacar\n2. Curar-se");

    Acao acaoServidor;
    do {
        acaoServidor = new Acao(sc.nextInt());
    } while (acaoServidor.tipo != 1 && acaoServidor.tipo != 2);

    if (acaoServidor.tipo == 1) {
        personagemEscolhido.atacar(cliente);
    } else {
        personagemEscolhido.curar();
    }

    Estado estado = new Estado(cliente, personagemEscolhido);
    outToClient.writeObject(estado);
    outToClient.flush();

    // Loop de turnos
    while (cliente.estaVivo() && personagemEscolhido.estaVivo()) {

        acaoCliente = (Acao) inFromClient.readObject();

        if (acaoCliente.tipo == 1) {
            cliente.atacar(personagemEscolhido);
        } else {
            cliente.curar();
        }

        if (!personagemEscolhido.estaVivo()) break;

        System.out.println("---------------------------------------------------");
        System.out.println("Seu personagem: " + personagemEscolhido.getTipo());
        System.out.println("Nome: " + personagemEscolhido.getNome());
        System.out.println("Vida: " + personagemEscolhido.getVida());
        System.out.print("\n");
        System.out.println("Personagem do oponente: " + cliente.getTipo());
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("Vida: " + cliente.getVida());
        System.out.println("---------------------------------------------------");

        System.out.println("Escolha sua ação: ");
        System.out.println("1. Atacar\n2. Curar-se");

        do {
            acaoServidor = new Acao(sc.nextInt());
        } while (acaoServidor.tipo != 1 && acaoServidor.tipo != 2);

        if (acaoServidor.tipo == 1) {
            personagemEscolhido.atacar(cliente);
        } else {
            personagemEscolhido.curar();
        }

        estado = new Estado(cliente, personagemEscolhido);
        outToClient.writeObject(estado);
        outToClient.flush();
    }

    sc.close();

    if (personagemEscolhido.estaVivo()){
        System.out.println("Você venceu!");
    } else {
        System.out.println("O cliente venceu!");
    }

    connectionSocket.close();
  }
}
