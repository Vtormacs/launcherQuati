package DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import modelo.Usuario;
import java.sql.SQLException;
import java.sql.ResultSet;
import telas.Login;

public class UsuarioDAO {

    private Connection conexao;

    public UsuarioDAO() {
        this.conexao = new ConexaoBanco().conectarComBanco();
    }

    public static String hashSenha(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void cadastrar(Usuario obj) {
        try {
            String sql = "INSERT INTO cadastroTela (nome, email, senha, nickname) "
                    + "VALUES (?, ?, ?, ?)";

            String senha = obj.getSenha();
            String email = obj.getEmail();
            String salt = senha.concat(email);
            String senhaCriptografada = hashSenha(salt);

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, senhaCriptografada);
            stmt.setString(4, obj.getNickname());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Usuario salvo com sucesso!!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o cliente!!" + erro);
        }
    }

    public void login(String email, String senha) throws IOException, InterruptedException {
        try {
            String sql = "SELECT senha FROM cadastroTela WHERE email = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet resultato = stmt.executeQuery();

            if (resultato.next()) {

                String senhaBanco = resultato.getString("senha");
                String salt = senha.concat(email);
                String senhaCriptografada = hashSenha(salt);

                if (senhaCriptografada.equals(senhaBanco)) {

                    String[] comandos = {
                        "cd /d \"C:\\Users\\vtorl\\launcherQuati\\quati_beta\"", // Comando para mudar o diretório
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

                } else {
                    JOptionPane.showMessageDialog(null, "Senha incorreta.");
                    new Login().setVisible(true);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                new Login().setVisible(true);
            }

            stmt.close();
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao Logar: " + erro);
        }
    }

}
