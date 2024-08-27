import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Exercício 02");
        frame.setContentPane(new UserInterface().painelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(new Dimension(600, 900));
        frame.setResizable(true);
        frame.setVisible(true);

    }
}