/*
Autor: Vinicius Pereira Tavares de Sousa
RA: 2152398
  
*/

package Lista_1_Sistemas_Distribuidos.Lab_1.src;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Principal_v0 {

	public final static Path path = Paths
			.get("C:\\Users\\Vinicius Tavares\\Desktop\\Lista_1_Sistemas_Distribuidos\\Lab_1\\src\\fortune-br.txt");
	private int NUM_FORTUNES = 0;

	public class FileReader {

		public int countFortunes() throws FileNotFoundException {

			int lineCount = 0;

			InputStream is = new BufferedInputStream(new FileInputStream(
					path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					is))) {

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();

				} // fim while

				System.out.println(lineCount);
			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
			return lineCount;
		}

		public void parser(HashMap<Integer, String> hm)
				throws FileNotFoundException {

			InputStream is = new BufferedInputStream(new FileInputStream(
					path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					is))) {

				int lineCount = 0;

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();
					StringBuffer fortune = new StringBuffer();
					while (!(line == null) && !line.equals("%")) {
						fortune.append(line + "\n");
						line = br.readLine();

					}

					hm.put(lineCount, fortune.toString());
					System.out.println(fortune.toString());

					System.out.println(lineCount);
				}

			} catch (IOException e) {
				System.out.println("Excecao na leitura do arquivo.");
			}
		}

		public void read(HashMap<Integer, String> hm)
				throws FileNotFoundException {

			int randomInt = (int) (Math.random() * NUM_FORTUNES);
			System.out.println("Numero " + randomInt + ": " + hm.get(randomInt));
		}

		public void write(HashMap<Integer, String> hm)
				throws FileNotFoundException {

			try (BufferedWriter escrita = new BufferedWriter(new FileWriter(path.toString(), true))) {

				Scanner scanner = new Scanner(System.in);
				System.out.println("Digite a nova fortune: ");
				String novaFortune = scanner.nextLine();

				int novaChave = hm.size() + 1;
				hm.put(novaChave, novaFortune);

				escrita.write(novaFortune);
				escrita.newLine();
				escrita.write("%");
				escrita.newLine();

				System.out.println("Nova fortune adicionada.");
			} catch (IOException e) {

				System.out.println("Erro ao escrever no arquivo.");
				e.printStackTrace();
			}
		}
	}

	public void iniciar() {

		FileReader fr = new FileReader();
		try {
			NUM_FORTUNES = fr.countFortunes();
			HashMap hm = new HashMap<Integer, String>();
			fr.parser(hm);
			fr.read(hm);
			fr.write(hm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Principal_v0().iniciar();
	}

}