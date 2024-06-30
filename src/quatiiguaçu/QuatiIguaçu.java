package quatiiguaçu;

import java.io.IOException;
import java.io.*;

public class QuatiIguaçu {

    public static void main(String[] args) {
        try {
            // Comandos que você quer executar
            String[] comandos = {
                "cd /d \"C:\\Users\\vitor\\QuatiIguaçu\\quati_beta\"", // Comando para mudar o diretório
                "python testeQuati.py" // Comando para executar o script Python
            };

            // Cria um processo para executar os comandos
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", String.join(" && ", comandos));
            builder.redirectErrorStream(true); // Redireciona o erro para a saída padrão

            Process processo = builder.start();

            // Captura a saída do processo
            InputStream inputStream = processo.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Lê a saída do processo linha por linha
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }

            // Espera o processo terminar
            processo.waitFor();

            // Fecha o BufferedReader
            reader.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
