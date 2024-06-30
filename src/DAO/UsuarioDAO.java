package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import modelo.Usuario;
import java.sql.SQLException;

public class UsuarioDAO {

    private Connection conexao;

    public UsuarioDAO() {
        this.conexao = new ConexaoBanco().conectarComBanco();
    }

    public void cadastrar(Usuario obj) {
        try {
            String sql = "INSERT INTO cadastroTela (nome, email, senha, nickname) "
                    + "VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, obj.getSenha());
            stmt.setString(4, obj.getNickname());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Usuario salvo com sucesso!!");
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o cliente!!" + erro);
        }
    }
    
    public void login(String email, String senha){
        try {
            String sql = "SELECT senha FROM funcionarios WHERE email = ?";
            
            
        } catch (Exception e) {
        }
    }

}
