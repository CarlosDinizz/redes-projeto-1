# Questão 1

### Execução dos programas (cliente e servidor) TCP e UDP:

#### a. Execute o cliente TCP antes de executar o servidor TCP. O que acontece? Por quê?

Aparece a seguinte mensagem de erro (no servidor em Python):
"Traceback (most recent call last): File "d:\Faculdade\Redes de Computadores\TCP_python\ClientTCP.py", line 12, in <module>
    cliente.connect((TCP_IP, TCP_PORTA))
    ~~~~~~~~~~~~~~~^^^^^^^^^^^^^^^^^^^^^
TimeoutError: [WinError 10060] Uma tentativa de conexão falhou porque o componente conectado não respondeu
corretamente após um período de tempo ou a conexão estabelecida falhou
porque o host conectado não respondeu"

Isso ocorre porque o cliente enviou o segmento SYN, mas nunca recebeu resposta, nem SYN-ACK e nem RST, o TCP retransmite o SYN algumas vezes em intervalos crescentes e, após esgotar o tempo limite definido pelo SO, lança o timeout.

#### b. Faça o mesmo procedimento para o cliente e servidor UDP. O resultado foi similar ao socket TCP? Compare os resultados e justifique.

Não, o resultado foi diferente, a mensagem exibida foi:
"Endereço IP de destino: 192.168.0.11
Porta UDP de destino: 5005
Mensagem enviada: Hello, World!"

Isso se deve ao fato do UDP executar sendto() sem estabelecer conexão prévia (não há handshake). O datagrama é simplesmente enviado para o destino, e o programa termina sem erro, independente de o servidor estar ativo ou não.

#### c. O que acontece se o número da porta que o cliente tentar se conectar for diferente da porta disponibilizada pelo servidor?

TCP: ConnectionRefusedError imediato. O SO do servidor recebe o SYN numa porta sem nenhum processo escutando e responde com RST.
UDP: O cliente envia normalmente, sem erro, o datagrama chega ao SO do servidor, que descarta silenciosamente (nenhum processo escutando naquela porta), o cliente nunca fica sabendo.

## Vídeos

### Questão 1 e 2
Clique [aqui](./questao-3/README.md) para ver o vídeo sobre as questões 1 e 2
