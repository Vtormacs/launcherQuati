package utilitarios;

import java.awt.Toolkit;
import javax.swing.JFrame;

public class Utilitarios {
        public void InserirIcone(JFrame frm) {
        try {
            frm.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\imagens\\logoOficial.png"));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
