// Full working Java code for Dashboard.java with MySQL-integrated Account, Transaction, and Loan Management
// Total: 500+ lines (combined from original + updated sections)

package bank.management.system;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends JFrame {
    // --- UI Components ---
    private JTextField accountNumberField, accountNameField, balanceField;
    private JTextField transactionAccountField, transactionAmountField;
    private JComboBox<String> transactionTypeCombo;
    private JTextField loanAccountField, loanAmountField, loanInterestField, loanTermField;
    private JTextField paymentLoanIdField, paymentAmountField;
    private JTable loansTable;
    private JLabel accountsCountLabel, totalBalanceLabel;
    private JButton addAccountButton, updateAccountButton, deleteAccountButton;
    private JButton processTransactionButton, applyLoanButton, approveLoanButton, makePaymentButton;

    public Dashboard() {
        setTitle("Banking Management Dashboard");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainTabbedPane(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);

        setupEventListeners();
        updateStats();
        updateLoansTable();
    }

    // --- Panels ---
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Banking Dashboard", JLabel.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.setBackground(new Color(44, 62, 80));
        label.setForeground(Color.WHITE);
        panel.add(label);
        return panel;
    }

    private JTabbedPane createMainTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Accounts", createAccountPanel());
        tabbedPane.addTab("Transactions", createTransactionPanel());
        tabbedPane.addTab("Loans", createLoanPanel());
        return tabbedPane;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        accountsCountLabel = new JLabel("Accounts: 0");
        totalBalanceLabel = new JLabel("Total Balance: $0.00");
        panel.add(accountsCountLabel);
        panel.add(totalBalanceLabel);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private JPanel createAccountPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        accountNumberField = new JTextField(15);
        accountNameField = new JTextField(15);
        balanceField = new JTextField(15);
        addAccountButton = new JButton("Add");
        updateAccountButton = new JButton("Update");
        deleteAccountButton = new JButton("Delete");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Account Number:"), gbc);
        gbc.gridx = 1; panel.add(accountNumberField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Account Name:"), gbc);
        gbc.gridx = 1; panel.add(accountNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Initial Balance:"), gbc);
        gbc.gridx = 1; panel.add(balanceField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addAccountButton);
        buttonPanel.add(updateAccountButton);
        buttonPanel.add(deleteAccountButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        return panel;
    }


    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        transactionAccountField = new JTextField(15);
        transactionTypeCombo = new JComboBox<>(new String[]{"Deposit", "Withdrawal"});
        transactionAmountField = new JTextField(15);
        processTransactionButton = new JButton("Submit");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Account Number:"), gbc);
        gbc.gridx = 1; panel.add(transactionAccountField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1; panel.add(transactionTypeCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1; panel.add(transactionAmountField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(processTransactionButton, gbc);

        return panel;
    }


    private JPanel createLoanPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        loanAccountField = new JTextField();
        loanAmountField = new JTextField();
        loanInterestField = new JTextField();
        loanTermField = new JTextField();
        applyLoanButton = new JButton("Apply Loan");

        inputPanel.add(new JLabel("Account Number"));
        inputPanel.add(loanAccountField);
        inputPanel.add(new JLabel("Amount"));
        inputPanel.add(loanAmountField);
        inputPanel.add(new JLabel("Interest Rate (%)"));
        inputPanel.add(loanInterestField);
        inputPanel.add(new JLabel("Term (months)"));
        inputPanel.add(loanTermField);
        inputPanel.add(applyLoanButton);

        loansTable = new JTable();
        JScrollPane tablePane = new JScrollPane(loansTable);

        JPanel paymentPanel = new JPanel(new GridLayout(3, 2));
        paymentLoanIdField = new JTextField();
        paymentAmountField = new JTextField();
        approveLoanButton = new JButton("Approve Loan");
        makePaymentButton = new JButton("Make Payment");

        paymentPanel.add(new JLabel("Loan ID"));
        paymentPanel.add(paymentLoanIdField);
        paymentPanel.add(new JLabel("Payment Amount"));
        paymentPanel.add(paymentAmountField);
        paymentPanel.add(approveLoanButton);
        paymentPanel.add(makePaymentButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tablePane, BorderLayout.CENTER);
        panel.add(paymentPanel, BorderLayout.SOUTH);

        return panel;
    }

    // --- Event Handlers ---
    private void setupEventListeners() {
        addAccountButton.addActionListener(this::addAccount);
        updateAccountButton.addActionListener(this::updateAccount);
        deleteAccountButton.addActionListener(this::deleteAccount);
        processTransactionButton.addActionListener(this::processTransaction);
        applyLoanButton.addActionListener(this::applyForLoan);
        approveLoanButton.addActionListener(this::approveLoan);
        makePaymentButton.addActionListener(this::makeLoanPayment);

    }

    private void addAccount(ActionEvent e) {
        String accountNumber = accountNumberField.getText().trim();
        String accountName = accountNameField.getText().trim();
        String balanceText = balanceField.getText().trim();

        if (accountNumber.isEmpty() || accountName.isEmpty() || balanceText.isEmpty()) {
            showError("Please fill all fields");
            return;
        }

        try {
            double balance = Double.parseDouble(balanceText);
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM accounts WHERE accountNumber = '" + accountNumber + "'");
            if (rs.next()) {
                showError("Account already exists");
            } else {
                String query = "INSERT INTO accounts (accountNumber, accountName, balance) VALUES ('" + accountNumber + "', '" + accountName + "', " + balance + ")";
                c.statement.executeUpdate(query);
                showInfo("Account added successfully");
                updateStats();
            }
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void updateAccount(ActionEvent e) {
        String accountNumber = accountNumberField.getText().trim();
        String accountName = accountNameField.getText().trim();
        String balanceText = balanceField.getText().trim();

        if (accountNumber.isEmpty()) {
            showError("Please enter account number");
            return;
        }

        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM accounts WHERE accountNumber = '" + accountNumber + "'");
            if (!rs.next()) {
                showError("Account not found");
                return;
            }

            String updateQuery = "UPDATE accounts SET ";
            boolean needComma = false;

            if (!accountName.isEmpty()) {
                updateQuery += "accountName = '" + accountName + "'";
                needComma = true;
            }

            if (!balanceText.isEmpty()) {
                double balance = Double.parseDouble(balanceText);
                if (needComma) updateQuery += ", ";
                updateQuery += "balance = " + balance;
            }

            updateQuery += " WHERE accountNumber = '" + accountNumber + "'";
            c.statement.executeUpdate(updateQuery);
            showInfo("Account updated successfully");
            updateStats();
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void deleteAccount(ActionEvent e) {
        String accountNumber = accountNumberField.getText().trim();
        if (accountNumber.isEmpty()) {
            showError("Please enter account number");
            return;
        }

        try {
            Conn c = new Conn();
            int result = c.statement.executeUpdate("DELETE FROM accounts WHERE accountNumber = '" + accountNumber + "'");
            if (result > 0) {
                showInfo("Account deleted successfully");
                updateStats();
            } else {
                showError("Account not found");
            }
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void processTransaction(ActionEvent e) {
        String accountNumber = transactionAccountField.getText().trim();
        String transactionType = (String) transactionTypeCombo.getSelectedItem();
        String amountText = transactionAmountField.getText().trim();

        if (accountNumber.isEmpty() || amountText.isEmpty()) {
            showError("Please fill all fields");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showError("Amount must be positive");
                return;
            }

            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM accounts WHERE accountNumber = '" + accountNumber + "'");
            if (!rs.next()) {
                showError("Account not found");
                return;
            }

            double currentBalance = rs.getDouble("balance");
            double newBalance;

            if ("Deposit".equals(transactionType)) {
                newBalance = currentBalance + amount;
            } else {
                if (currentBalance < amount) {
                    showError("Insufficient funds");
                    return;
                }
                newBalance = currentBalance - amount;
            }

            c.statement.executeUpdate("UPDATE accounts SET balance = " + newBalance + " WHERE accountNumber = '" + accountNumber + "'");
            c.statement.executeUpdate("INSERT INTO transactions(accountNumber, type, amount) VALUES('" + accountNumber + "', '" + transactionType + "', " + amount + ")");

            showInfo(transactionType + " successful. New balance: $" + newBalance);
            updateStats();
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }







    private void applyForLoan(ActionEvent e) {
        String accountNumber = loanAccountField.getText().trim();
        String amountText = loanAmountField.getText().trim();
        String interestText = loanInterestField.getText().trim();
        String termText = loanTermField.getText().trim();

        if (accountNumber.isEmpty() || amountText.isEmpty() || interestText.isEmpty() || termText.isEmpty()) {
            showError("Please fill all fields");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            double interestRate = Double.parseDouble(interestText);
            int termMonths = Integer.parseInt(termText);
            double remainingBalance = amount * (1 + (interestRate / 100));

            Conn conn = new Conn();
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM accounts WHERE accountNumber='" + accountNumber + "'");
            if (!rs.next()) {
                showError("Account not found");
                return;
            }

            String loanId = "LN-" + System.currentTimeMillis();
            String query = "INSERT INTO loans (loanId, accountNumber, amount, interestRate, termMonths, remainingBalance, status) " +
                    "VALUES ('" + loanId + "', '" + accountNumber + "', " + amount + ", " + interestRate + ", " + termMonths + ", " + remainingBalance + ", 'Pending')";

            conn.statement.executeUpdate(query);
            showInfo("Loan application submitted successfully");
            updateLoansTable();
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }










    private void approveLoan(ActionEvent e) {
        String loanId = paymentLoanIdField.getText().trim();

        if (loanId.isEmpty()) {
            showError("Please enter Loan ID");
            return;
        }

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.statement.executeQuery("SELECT status FROM loans WHERE loanId='" + loanId + "'");
            if (rs.next()) {
                String status = rs.getString("status");
                if (!"Pending".equals(status)) {
                    showError("Loan is not pending approval");
                    return;
                }

                conn.statement.executeUpdate("UPDATE loans SET status='Approved' WHERE loanId='" + loanId + "'");
                showInfo("Loan approved successfully");
                updateLoansTable();
            } else {
                showError("Loan not found");
            }
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }
















    private void makeLoanPayment(ActionEvent e) {
        String loanId = paymentLoanIdField.getText().trim();
        String amountText = paymentAmountField.getText().trim();

        if (loanId.isEmpty() || amountText.isEmpty()) {
            showError("Please fill all fields");
            return;
        }

        try {
            double payment = Double.parseDouble(amountText);
            if (payment <= 0) {
                showError("Payment must be positive");
                return;
            }

            Conn conn = new Conn();
            ResultSet rs = conn.statement.executeQuery("SELECT remainingBalance FROM loans WHERE loanId='" + loanId + "' AND status='Approved'");
            if (!rs.next()) {
                showError("Approved loan not found");
                return;
            }

            double remaining = rs.getDouble("remainingBalance");
            if (payment > remaining) {
                showError("Payment exceeds remaining balance");
                return;
            }

            double newBalance = remaining - payment;
            String newStatus = newBalance <= 0.01 ? "Paid" : "Approved";

            conn.statement.executeUpdate("UPDATE loans SET remainingBalance=" + newBalance + ", status='" + newStatus + "' WHERE loanId='" + loanId + "'");
            showInfo("Payment successful. New balance: $" + String.format("%.2f", newBalance));
            updateLoansTable();
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }



































    private void updateStats() {
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.statement.executeQuery("SELECT COUNT(*) AS totalAccounts, SUM(balance) AS totalBalance FROM accounts");
            if (rs.next()) {
                int count = rs.getInt("totalAccounts");
                double total = rs.getDouble("totalBalance");
                accountsCountLabel.setText("Accounts: " + count);
                totalBalanceLabel.setText(String.format("Total Balance: $%.2f", total));
            }
        } catch (Exception e) {
            showError("Error updating stats: " + e.getMessage());
        }
    }

    private void updateLoansTable() {
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM loans");

            List<Object[]> rows = new ArrayList<>();
            while (rs.next()) {
                rows.add(new Object[]{
                        rs.getString("loanId"),
                        rs.getString("accountNumber"),
                        String.format("$%.2f", rs.getDouble("amount")),
                        String.format("%.2f%%", rs.getDouble("interestRate")),
                        rs.getInt("termMonths") + " months",
                        String.format("$%.2f", rs.getDouble("remainingBalance")),
                        rs.getString("status"),
                        rs.getDate("dateApplied").toString()
                });
            }

            String[] columns = {"Loan ID", "Account", "Amount", "Interest", "Term", "Balance", "Status", "Date"};
            loansTable.setModel(new DefaultTableModel(rows.toArray(new Object[0][]), columns));
        } catch (Exception e) {
            showError("Error updating loan table: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }
}

