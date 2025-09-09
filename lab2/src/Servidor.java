/*
Autor: Vinicius Pereira Tavares de Sousa
RA: 2152398
  
*/

package Lista_1_Sistemas_Distribuidos.Lab_2.src;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;
import java.util.*;

public class Servidor {

    private static final int PORTA = 1025;
    private static final String ARQUIVO = "C:\\Users\\Vinicius Tavares\\Desktop\\Lista_1_Sistemas_Distribuidos\\Lab_2\\src\\fortune-br.txt";

    public void iniciar() {
        System.out.println("Servidor iniciado na porta: " + PORTA);

        try (ServerSocket server = new ServerSocket(PORTA)) {
            while (true) {
                try (Socket socket = server.accept();
                        DataInputStream entrada = new DataInputStream(socket.getInputStream());
                        DataOutputStream saida = new DataOutputStream(socket.getOutputStream())) {

                    String msg = entrada.readUTF();
                    String resposta = processarMensagem(msg);

                    saida.writeUTF(resposta);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String processarMensagem(String msg) {
        try {
            if (msg.contains("\"method\":\"read\"")) {
                List<String> fortunas = carregarFortunas();
                if (fortunas.isEmpty())
                    return "{\"result\":\"Nenhuma fortuna cadastrada.\"}";
                Random rand = new Random();
                String fortuna = fortunas.get(rand.nextInt(fortunas.size()));
                return "{\"result\":\"" + fortuna.replace("\"", "\\\"") + "\"}";
            }
            if (msg.contains("\"method\":\"write\"")) {
                int start = msg.indexOf("[\"") + 2;
                int end = msg.indexOf("\"]");
                if (start >= 2 && end > start) {
                    String nova = msg.substring(start, end);
                    salvarFortuna(nova);
                    return "{\"result\":\"Fortuna adicionada\"}";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{\"result\":\"false\"}";
    }

    private List<String> carregarFortunas() throws IOException {
        List<String> fortunas = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        for (String linha : Files.readAllLines(Paths.get(ARQUIVO))) {
            if (linha.equals("%")) {
                fortunas.add(buffer.toString().trim());
                buffer.setLength(0);
            } else {
                buffer.append(linha).append("\n");
            }
        }
        if (buffer.length() > 0)
            fortunas.add(buffer.toString().trim());
        return fortunas;
    }

    private void salvarFortuna(String fortuna) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            bw.write(fortuna);
            bw.newLine();
            bw.write("%");
            bw.newLine();
        }
    }

    public static void main(String[] args) {
        new Servidor().iniciar();
    }
}