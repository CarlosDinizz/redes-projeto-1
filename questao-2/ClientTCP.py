import socket

TCP_IP = '192.168.15.91'  # IP do servidor
TCP_PORTA = 10435          # porta do servidor
TAMANHO_BUFFER = 1024      # tamanho do buffer de recepção

# Criação do socket TCP
cliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Conecta ao servidor
cliente.connect((TCP_IP, TCP_PORTA))
print("Conectado ao servidor. Digite QUIT para encerrar.")

while True:
    # Cliente digita mensagem
    mensagem = input("Você: ")

    # Envia mensagem ao servidor
    cliente.send(mensagem.encode('UTF-8'))

    # Verifica se o cliente enviou QUIT
    if mensagem == 'QUIT':
        print("Você encerrou o chat.")
        break

    # Recebe resposta do servidor
    data = cliente.recv(TAMANHO_BUFFER).decode('UTF-8')

    # Verifica se o servidor enviou QUIT
    if data == 'QUIT':
        print("Servidor encerrou o chat.")
        break

    print("Servidor:", data)

# Fecha conexão
cliente.close()