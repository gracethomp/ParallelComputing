import socket
import struct
import random
import time

HOST = "localhost"  # The server's hostname or IP address
PORT = 6666  # The port used by the server


def receive_data():
    data_len = s.recv(2)
    data = s.recv(int.from_bytes(data_len, byteorder='big'))
    print(data.decode('utf-8'))
    return data


def send_matrix(n, m):
    s.send(struct.pack('!i', n))
    s.send(struct.pack('!i', m))
    array_2d = [[random.randint(0, 10000) for _ in range(m)] for _ in range(n)]
    for i in range(n):
        for j in range(m):
            s.send(struct.pack('!i', array_2d[i][j]))


def send_command(command):
    encoded_message = command.encode('utf-8')
    s.send(len(encoded_message).to_bytes(2, byteorder='big'))
    s.send(encoded_message)


with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))

    receive_data()

    rows = 2
    columns = 5
    send_matrix(rows, columns)

    receive_data()

    message = "start"
    send_command(message)

    receive_data()

    while True:
        message = "get"
        send_command(message)
        response = receive_data()
        time.sleep(1)
        if response.decode('utf-8') != 'In progress':
            break
