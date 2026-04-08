import socket

TCP_IP = ''           # escuta em todas as interfaces
TCP_PORTA = 10435     # porta do servidor
TAMANHO_BUFFER = 1024 # tamanho do buffer de recepção

# Criação do socket TCP
servidor = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# IP e porta que o servidor deve aguardar a conexão
servidor.bind((TCP_IP, TCP_PORTA))

# Define o limite de conexões
servidor.listen(1)

print("Servidor aguardando conexão...")

# Aceita conexão do cliente
conn, addr = servidor.accept()
print("Conectado com:", addr)

while True:
    # Recebe mensagem do cliente
    data = conn.recv(TAMANHO_BUFFER).decode('UTF-8')

    # Verifica se o cliente enviou QUIT
    if data == 'QUIT':
        print("Cliente encerrou o chat.")
        break

    print("Cliente:", data)

    # Servidor digita resposta
    resposta = input("Você: ")

    # Envia resposta ao cliente
    conn.send(resposta.encode('UTF-8'))

    # Verifica se o servidor enviou QUIT
    if resposta == 'QUIT':
        print("Você encerrou o chat.")
        break

# Fecha conexão
conn.close()
servidor.close()