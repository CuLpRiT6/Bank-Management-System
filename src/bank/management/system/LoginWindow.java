package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class LoginWindow extends JFrame implements ActionListener {

    JLabel label1,label2,label3;

    JTextField accNo;

    JPasswordField pinNo;

    JButton button1,button2,button3,button4;

    LoginWindow() {
        super("Bank Management System");


// Adding  icons
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(700,10,100,100);
        add(image);
        


        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/banker.png"));
        Image i5 = i4.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image1 = new JLabel(i6);
        image1.setBounds(1200,600,100,100);
        add(image1);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/sys.png"));
        Image i8 = i7.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel image2 = new JLabel(i9);
        image2.setBounds(10,500,100,100);
        add(image2);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/money.png"));
        Image i11 = i10.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel image3 = new JLabel(i12);
        image3.setBounds(1400,140,100,100);
        add(image3);



// adding writing space for account no and PIN
        label1 = new JLabel("Welcome to the Login page");
        label1.setForeground(Color.WHITE);
        label1.setBounds(380,200,1000,55);
        label1.setFont(new Font("Lora",Font.BOLD,50));
        add(label1);

        label2 = new JLabel("Account no:");
        label2.setForeground(Color.WHITE);
        label2.setBounds(450,330,300,30);
        label2.setFont(new Font("Roboto", Font.BOLD, 20));
        add(label2);





        accNo = new JTextField("",20);
        accNo.setBounds(600,330,300,30);
        accNo.setFont(new Font("Arial", Font.BOLD, 15));
        add(accNo);

        label3 = new JLabel("Pin Number:");
        label3.setForeground(Color.WHITE);
        label3.setBounds(450,450,300,30);
        label3.setFont(new Font("Roboto", Font.BOLD, 20));
        add(label3);

        pinNo = new JPasswordField(20);
        pinNo.setBounds(600,450,300,30);
        pinNo.setFont(new Font("Arial", Font.BOLD, 15));
        add(pinNo);




// Sign in button
        button1 = new JButton("SIGN IN");
        button1.setForeground(Color.WHITE);
        button1.setBackground(Color.LIGHT_GRAY);
        button1.setFont(new Font("London", Font.BOLD, 15));
        button1.setBounds(500,600,150,30);
        button1.addActionListener(this);
        add(button1);

 // sign up button
        button2 = new JButton("SIGN UP");
        button2.setForeground(Color.WHITE);
        button2.setBackground(Color.LIGHT_GRAY);
        button2.setFont(new Font("London", Font.BOLD, 15));
        button2.setBounds(700,600,150,30);
        button2.addActionListener(this);
        add(button2);

// clear button
        button3 = new JButton("CLEAR");
        button3.setBackground(Color.LIGHT_GRAY);
        button3.setForeground(Color.WHITE);
        button3.setFont(new Font("London", Font.BOLD, 15));
        button3.setBounds(500,700,150,30);
        button3.addActionListener(this);
        add(button3);

// forgot pin button
        button4 = new JButton("FORGOT PIN");
        button4.setForeground(Color.WHITE);
        button4.setBackground(Color.LIGHT_GRAY);
        button4.setFont(new Font("London", Font.BOLD, 15));
        button4.setBounds(700,700,150,30);
        button4.addActionListener(this);
        add(button4);







// adding background
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/bg.jpg"));
        Image i14 = i13.getImage().getScaledInstance(1600,800, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel image4 = new JLabel(i15);
        image4.setBounds(0,0,1600,800);
        add(image4);


// setting location and size of the window
        setLayout(null);
        setSize(1600, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

    }


//  performing actions on the buttons

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == button1) {
                String account = accNo.getText().trim();
                String pin = new String(pinNo.getPassword()).trim();

                if (account.isEmpty() || pin.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter Account Number and PIN", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Conn conn = new Conn();
                    String query = "SELECT * FROM login WHERE card_no='" + account + "' AND pin='" + pin + "'";

                    ResultSet rs = conn.statement.executeQuery(query);

                    if (rs.next()) {
                        Dashboard dashboard = new Dashboard();
                        dashboard.setVisible(true);
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Account Number or PIN", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Database Error", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }


            else if (e.getSource() == button2) {
                SignUp signUpWindow = new SignUp();
                signUpWindow.setVisible(true);
                setVisible(false);

            }
            else if (e.getSource() == button3) {
                accNo.setText("");
                pinNo.setText("");

            }
            if (e.getSource() == button4) {
                String card = JOptionPane.showInputDialog(this, "Enter your Card Number:");
                if (card == null || card.isEmpty()) return;

                String newPin = JOptionPane.showInputDialog(this, "Enter new PIN:");
                if (newPin == null || newPin.isEmpty()) return;

                try {
                    Conn conn = new Conn();
                    ResultSet rs = conn.statement.executeQuery("SELECT * FROM login WHERE card_no='" + card + "'");
                    if (rs.next()) {
                        conn.statement.executeUpdate("UPDATE login SET pin='" + newPin + "' WHERE card_no='" + card + "'");
                        JOptionPane.showMessageDialog(this, "PIN successfully updated!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Card number not found.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }

            }


        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    // calling the LoginWindow class
    public static void main(String[] args) {
        new LoginWindow();
    }
}
