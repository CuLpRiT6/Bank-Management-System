package bank.management.system;

import javax.swing.*;

public class SignUp extends JFrame {

    JLabel label1;

    JButton button1;



    SignUp() {

        super("Personal Details");



        setLayout(null);
        setSize(700, 750);
        setLocation(400,50);
        setVisible(true);


    }

    public static void main(String[] args) {
        new SignUp();

    }
}
