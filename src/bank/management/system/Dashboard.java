package bank.management.system;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class Dashboard extends JFrame {
    // Enhanced color scheme
    private static final Color PRIMARY_COLOR = new Color(44, 62, 80);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color CARD_COLOR = new Color(255, 255, 255);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);
    private static final Color ERROR_COLOR = new Color(231, 76, 60);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 30);

    // Fonts
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font LABEL_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TAB_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 14);

    // UI Components
    private JTabbedPane mainTabbedPane;
    private JTextField accountNumberField;
    private JTextField accountNameField;
    private JTextField balanceField;
    private JTextField transactionAccountField;
    private JComboBox<String> transactionTypeCombo;
    private JTextField transactionAmountField;
    private JButton addAccountButton;
    private JButton updateAccountButton;
    private JButton deleteAccountButton;
    private JButton processTransactionButton;
    private JLabel accountsCountLabel;
    private JLabel totalBalanceLabel;

    // Data
    private final Map<String, BankAccount> accounts = new HashMap<>();

    public Dashboard() {
        configureWindow();
        initComponents();
        setupEventListeners();
        applyStyles();
    }

    private void configureWindow() {
        setTitle("Banking Management Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650);
        setMinimumSize(new Dimension(750, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));

        // Header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main tabbed pane
        mainTabbedPane = createMainTabbedPane();
        add(mainTabbedPane, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JPanel headerShadow = new JPanel();
        headerShadow.setBackground(new Color(0, 0, 0, 0));
        headerShadow.setBorder(new MatteBorder(0, 0, 1, 0, SHADOW_COLOR));
        headerPanel.add(headerShadow, BorderLayout.SOUTH);

        JLabel header = new JLabel("Banking Dashboard", JLabel.CENTER);
        header.setFont(HEADER_FONT);
        header.setForeground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 0, 20, 0));
        headerPanel.add(header, BorderLayout.CENTER);

        return headerPanel;
    }

    private JTabbedPane createMainTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(TAB_FONT);
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setForeground(TEXT_COLOR);
        tabbedPane.setBorder(new EmptyBorder(10, 15, 15, 15));

        // Create panels
        JPanel accountPanel = createAccountPanel();
        JPanel transactionPanel = createTransactionPanel();

        // Add tabs
        tabbedPane.addTab("Account Management", accountPanel);
        tabbedPane.addTab("Transactions", transactionPanel);

        // Custom tab UI
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                          int x, int y, int w, int h, boolean isSelected) {}

            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                              int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isSelected) {
                    g2d.setColor(CARD_COLOR);
                    g2d.fillRoundRect(x, y, w, h + 5, 10, 10);
                }
            }

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {}
        });

        return tabbedPane;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        footerPanel.setBackground(PRIMARY_COLOR);
        footerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        accountsCountLabel = createFooterLabel("Accounts: 0");
        totalBalanceLabel = createFooterLabel("Total Balance: $0.00");

        footerPanel.add(accountsCountLabel);
        footerPanel.add(totalBalanceLabel);

        return footerPanel;
    }

    private JLabel createFooterLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(LABEL_FONT);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JPanel createAccountPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formCard = createFormCard();
        panel.add(formCard, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFormCard() {
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(CARD_COLOR);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                new DropShadowBorder(SHADOW_COLOR, 5, 0.3f, 12, false, true, true, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Account number field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formCard.add(createLabel("Account Number:"), gbc);
        gbc.gridx = 1;
        accountNumberField = createTextField();
        formCard.add(accountNumberField, gbc);

        // Account name field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formCard.add(createLabel("Account Name:"), gbc);
        gbc.gridx = 1;
        accountNameField = createTextField();
        formCard.add(accountNameField, gbc);

        // Balance field
        gbc.gridx = 0;
        gbc.gridy = 2;
        formCard.add(createLabel("Initial Balance:"), gbc);
        gbc.gridx = 1;
        balanceField = createTextField();
        formCard.add(balanceField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        addAccountButton = createStyledButton("Add Account", SECONDARY_COLOR);
        updateAccountButton = createStyledButton("Update Account", WARNING_COLOR);
        deleteAccountButton = createStyledButton("Delete Account", ERROR_COLOR);

        buttonPanel.add(addAccountButton);
        buttonPanel.add(updateAccountButton);
        buttonPanel.add(deleteAccountButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        formCard.add(buttonPanel, gbc);

        return formCard;
    }

    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(CARD_COLOR);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                new DropShadowBorder(SHADOW_COLOR, 5, 0.3f, 12, false, true, true, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Transaction account field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formCard.add(createLabel("Account Number:"), gbc);
        gbc.gridx = 1;
        transactionAccountField = createTextField();
        formCard.add(transactionAccountField, gbc);

        // Transaction type combo
        gbc.gridx = 0;
        gbc.gridy = 1;
        formCard.add(createLabel("Transaction Type:"), gbc);
        gbc.gridx = 1;
        transactionTypeCombo = new JComboBox<>(new String[]{"Deposit", "Withdrawal"});
        transactionTypeCombo.setFont(FIELD_FONT);
        transactionTypeCombo.setBackground(Color.WHITE);
        transactionTypeCombo.setBorder(new RoundBorder(new Color(220, 220, 220), 1, 8));
        transactionTypeCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(new EmptyBorder(5, 10, 5, 10));
                return this;
            }
        });
        formCard.add(transactionTypeCombo, gbc);

        // Transaction amount field
        gbc.gridx = 0;
        gbc.gridy = 2;
        formCard.add(createLabel("Amount:"), gbc);
        gbc.gridx = 1;
        transactionAmountField = createTextField();
        formCard.add(transactionAmountField, gbc);

        // Process button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        processTransactionButton = createStyledButton("Process Transaction", SUCCESS_COLOR);
        formCard.add(processTransactionButton, gbc);

        panel.add(formCard, BorderLayout.CENTER);
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(FIELD_FONT);
        field.setBorder(new RoundBorder(new Color(220, 220, 220), 1, 8));
        field.setBackground(Color.WHITE);
        field.setMargin(new Insets(8, 10, 8, 10));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
        button.setOpaque(true);

        // Rounded corners
        button.setBorder(new RoundBorder(bgColor.darker(), 0, 25));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
                button.setBorder(new RoundBorder(bgColor.darker().darker(), 0, 25));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.setBorder(new RoundBorder(bgColor.darker(), 0, 25));
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker().darker());
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
        });

        return button;
    }

    private void setupEventListeners() {
        addAccountButton.addActionListener(this::addAccount);
        updateAccountButton.addActionListener(this::updateAccount);
        deleteAccountButton.addActionListener(this::deleteAccount);
        processTransactionButton.addActionListener(this::processTransaction);
    }

    private void applyStyles() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Set UI defaults for better consistency
            UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
            UIManager.put("TabbedPane.tabAreaInsets", new Insets(5, 5, 0, 5));
            UIManager.put("TabbedPane.selected", CARD_COLOR);
            UIManager.put("TabbedPane.background", BACKGROUND_COLOR);
            UIManager.put("TabbedPane.tabAreaBackground", BACKGROUND_COLOR);

            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            if (balance < 0) {
                showError("Balance cannot be negative");
                return;
            }

            if (accounts.containsKey(accountNumber)) {
                showError("Account already exists");
                return;
            }

            accounts.put(accountNumber, new BankAccount(accountNumber, accountName, balance));
            showInfo("Account added successfully");
            clearAccountFields();
            updateStats();
        } catch (NumberFormatException e1) {
            showError("Invalid balance amount");
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

        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            showError("Account not found");
            return;
        }

        if (!accountName.isEmpty()) {
            account.setAccountName(accountName);
        }

        if (!balanceText.isEmpty()) {
            try {
                double balance = Double.parseDouble(balanceText);
                if (balance < 0) {
                    showError("Balance cannot be negative");
                    return;
                }
                account.deposit(balance - account.getBalance()); // Adjust balance
            } catch (NumberFormatException e1) {
                showError("Invalid balance amount");
                return;
            }
        }

        showInfo("Account updated successfully");
        clearAccountFields();
        updateStats();
    }

    private void deleteAccount(ActionEvent e) {
        String accountNumber = accountNumberField.getText().trim();

        if (accountNumber.isEmpty()) {
            showError("Please enter account number");
            return;
        }

        if (accounts.remove(accountNumber) != null) {
            showInfo("Account deleted successfully");
            clearAccountFields();
            updateStats();
        } else {
            showError("Account not found");
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

        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            showError("Account not found");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showError("Amount must be positive");
                return;
            }

            if ("Deposit".equals(transactionType)) {
                account.deposit(amount);
                showInfo(String.format("Deposit successful. New balance: $%.2f", account.getBalance()));
            } else if ("Withdrawal".equals(transactionType)) {
                if (account.withdraw(amount)) {
                    showInfo(String.format("Withdrawal successful. New balance: $%.2f", account.getBalance()));
                } else {
                    showError("Insufficient funds");
                }
            }
            clearTransactionFields();
            updateStats();
        } catch (NumberFormatException e1) {
            showError("Invalid amount");
        }
    }

    private void updateStats() {
        int count = accounts.size();
        double total = accounts.values().stream().mapToDouble(BankAccount::getBalance).sum();

        accountsCountLabel.setText(String.format("Accounts: %d", count));
        totalBalanceLabel.setText(String.format("Total Balance: $%.2f", total));
    }

    private void clearAccountFields() {
        accountNumberField.setText("");
        accountNameField.setText("");
        balanceField.setText("");
    }

    private void clearTransactionFields() {
        transactionAccountField.setText("");
        transactionAmountField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                "<html><div style='color:" + rgbToHex(ERROR_COLOR) +
                        "; font-family:Segoe UI; font-size:14px;'>" + message + "</div></html>",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this,
                "<html><div style='color:" + rgbToHex(TEXT_COLOR) +
                        "; font-family:Segoe UI; font-size:14px;'>" + message + "</div></html>",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private String rgbToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
        });
    }

    private static class BankAccount {
        private final String accountNumber;
        private String accountName;
        private double balance;

        public BankAccount(String accountNumber, String accountName, double balance) {
            this.accountNumber = accountNumber;
            this.accountName = accountName;
            this.balance = balance;
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
            }
        }

        public boolean withdraw(double amount) {
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        }

        public double getBalance() {
            return balance;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }
    }

    private static class RoundBorder extends AbstractBorder {
        private final Color color;
        private final int thickness;
        private final int radius;

        public RoundBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.drawRoundRect(x, y, width-1, height-1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius/2, radius/2, radius/2, radius/2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = radius/2;
            insets.top = insets.bottom = radius/2;
            return insets;
        }
    }

    private static class DropShadowBorder extends AbstractBorder {
        private final Color shadowColor;
        private final int shadowSize;
        private final float shadowOpacity;
        private final int cornerSize;
        private final boolean showTopShadow;
        private final boolean showLeftShadow;
        private final boolean showBottomShadow;
        private final boolean showRightShadow;

        public DropShadowBorder(Color shadowColor, int shadowSize, float shadowOpacity,
                                int cornerSize, boolean showTopShadow, boolean showLeftShadow,
                                boolean showBottomShadow, boolean showRightShadow) {
            this.shadowColor = shadowColor;
            this.shadowSize = shadowSize;
            this.shadowOpacity = shadowOpacity;
            this.cornerSize = cornerSize;
            this.showTopShadow = showTopShadow;
            this.showLeftShadow = showLeftShadow;
            this.showBottomShadow = showBottomShadow;
            this.showRightShadow = showRightShadow;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            float opacityStep = shadowOpacity / shadowSize;

            for (int i = 0; i < shadowSize; i++) {
                g2d.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(),
                        shadowColor.getBlue(), (int)(255 * (opacityStep * (shadowSize - i)))));

                int offset = shadowSize - i;

                if (showTopShadow) {
                    g2d.drawLine(x + offset, y + offset, x + width - offset, y + offset);
                }
                if (showLeftShadow) {
                    g2d.drawLine(x + offset, y + offset, x + offset, y + height - offset);
                }
                if (showBottomShadow) {
                    g2d.drawLine(x + offset, y + height - offset, x + width - offset, y + height - offset);
                }
                if (showRightShadow) {
                    g2d.drawLine(x + width - offset, y + offset, x + width - offset, y + height - offset);
                }
            }

            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(shadowSize, shadowSize, shadowSize, shadowSize);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = shadowSize;
            return insets;
        }
    }
}

