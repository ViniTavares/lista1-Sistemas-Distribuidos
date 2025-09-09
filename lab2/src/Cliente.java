/*
Autor: Vinicius Pereira Tavares de Sousa
RA: 2152398
  
*/

package Lista_1_Sistemas_Distribuidos.Lab_2.src;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private static final String HOST = "127.0.0.1";
    private static final int PORTA = 1025;

    public void iniciar() {
        Scanner sc = new Scanner(System.in);

        try (Socket socket = new Socket(HOST, PORTA);
                DataInputStream entrada = new DataInputStream(socket.getInputStream());
                DataOutputStream saida = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Cliente iniciado na porta: " + PORTA);
            System.out.print("Digite o metodo (read ou write): ");
            String metodo = sc.nextLine().trim();

            String argumento = "";
            if (metodo.equalsIgnoreCase("write")) {
                System.out.print("Digite a fortuna: ");
                argumento = sc.nextLine().trim();
            }

            String msg = "{\"method\":\"" + metodo + "\",\"args\":[\"" + argumento + "\"]}";
            saida.writeUTF(msg);

            String resposta = entrada.readUTF();
            System.out.println("Servidor respondeu: " + resposta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Cliente().iniciar();
    }
}