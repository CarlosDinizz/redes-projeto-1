# Questão 3

### Faça uma aplicação qualquer usando o socket. Essa aplicação pode ser para enviar arquivos ou controlar algum objeto em uma das pontas da conexão ou gerenciar diversas conexões usado threads. Após elaboração do projeto, um vídeo deve ser gravado mostrando o funcionamento da aplicação e explicando o código.

<br>
<br>

## Vídeos

Clique [aqui](./questao-3/README.md) para ver o vídeo sobre a questão 3

-----------------------------------------------------------------

# Jogo de Turnos Multiplayer

Jogo de turnos em Java onde dois jogadores se enfrentam em tempo real via rede local.
Cada jogador escolhe um personagem e toma ações alternadas até um dos dois ser derrotado.

---

## Personagens

| Personagem | Vida | Ataque | Cura |
|------------|------|--------|------|
| Guerreiro  | 100  | 20     | 7    |
| Mago       | 60   | 30     | 11   |
| Anão       | 150  | 15     | 25   |

> Ataques têm chance de crítico: se o dado (1-20) for maior que 15, o dano é multiplicado por 1.3.

---

## Pré-requisitos

- [Java JDK](https://www.oracle.com/java/technologies/downloads/) instalado (versão 8 ou superior)
- Dois computadores na mesma rede local (ou dois terminais na mesma máquina para testar)

Para verificar se o Java está instalado, abra o terminal e digite:
```bash
java -version
```

---

## Como baixar o projeto

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd JogoTurno
```

---

## Como compilar

Dentro da pasta `JogoTurno`, rode:

```bash
javac -encoding UTF-8 -d out *.java
```

Isso vai criar uma pasta `out` com os arquivos compilados.

---

## Como jogar

### Máquina que vai ser o Servidor

1. Descubra seu IP local. No terminal digite:
```bash
   ipconfig
```
   Procure por **Endereço IPv4** (exemplo: `192.168.0.109`)

2. Rode o servidor:
```bash
   java -cp out jogo.personagens._Servidor
```

3. Escolha seu personagem e aguarde o outro jogador conectar.

---

### Máquina que vai ser o Cliente

1. Abra o arquivo `_Cliente.java` e altere o IP para o IP do servidor:
```java
   String enderecoIpServidor = "192.168.0.109"; // coloque o IP do servidor aqui
```

2. Recompile:
```bash
   javac -encoding UTF-8 -d out *.java
```

3. Rode o cliente:
```bash
   java -cp out jogo.personagens._Cliente
```

---

## Testando na mesma máquina

Se quiser testar sem um segundo computador, troque o IP no `_Cliente.java` por:
```java
String enderecoIpServidor = "localhost";
```

Abra dois terminais na mesma pasta e rode o servidor em um e o cliente no outro.

---

## Fluxo do jogo

```
Servidor escolhe personagem → aguarda conexão
Cliente conecta e escolhe personagem
Ambos veem os personagens um do outro
Cada turno: os dois escolhem atacar ou curar
O jogo termina quando a vida de um chegar a zero
```
