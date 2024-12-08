import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TicketMachine extends JFrame {
    private JComboBox<String> destinationComboBox;
    private JButton calculateButton;
    private JButton printButton;
    private JLabel priceLabel;
    private JLabel amountToPayLabel;
    private JTextField paymentField;
    private JLabel paymentLabel;
    private JTextArea ticketArea;
    
    private double currentAmount = 0.0;
    private double ticketPrice = 0.0;
    
    private final double[] prices = {2.50, 3.00, 4.50, 6.00}; // Beispielpreise für 4 Ziele

    public TicketMachine() {
        setTitle("Fahrkarten Automat");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null); // Fenster zentrieren
        
        // Verwende ein GridBagLayout für eine flexiblere und ansprechendere Gestaltung
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240)); // Helles Hintergrundbild für bessere Lesbarkeit
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Titel
        JLabel titleLabel = new JLabel("Willkommen beim Ticketautomaten");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);
        
        // Ziel-Auswahl
        JLabel destinationLabel = new JLabel("Ziel wählen:");
        destinationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(destinationLabel, gbc);

        String[] destinations = {"Berlin", "Hamburg", "München", "Köln"};
        destinationComboBox = new JComboBox<>(destinations);
        destinationComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        panel.add(destinationComboBox, gbc);

        // Preis-Anzeige
        priceLabel = new JLabel("Preis: 0,00 €");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(priceLabel, gbc);

        amountToPayLabel = new JLabel("Noch zu bezahlen: 0,00 €");
        amountToPayLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(amountToPayLabel, gbc);

        // Buttons für die Münz- und Scheineinzahlung
        addCoinAndNoteButtons(panel, gbc);
        
        // Ticket-Anzeige
        ticketArea = new JTextArea(6, 30);
        ticketArea.setEditable(false);
        ticketArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(ticketArea);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.add(scrollPane, gbc);
        
        // Berechnungs- und Export-Buttons
        calculateButton = new JButton("Preis berechnen");
        calculateButton.setFont(new Font("Arial", Font.PLAIN, 16));
        calculateButton.setBackground(new Color(0, 123, 255));
        calculateButton.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add(calculateButton, gbc);

        printButton = new JButton("Ticket als PNG exportieren");
        printButton.setFont(new Font("Arial", Font.PLAIN, 16));
        printButton.setBackground(new Color(40, 167, 69));
        printButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 12;
        panel.add(printButton, gbc);

        // Füge das Panel zum Fenster hinzu
        add(panel, BorderLayout.CENTER);
        
        // ActionListener für den Button "Preis berechnen"
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = destinationComboBox.getSelectedIndex();
                ticketPrice = prices[selectedIndex];
                amountToPayLabel.setText("Noch zu bezahlen: " + String.format("%.2f €", ticketPrice)); 
                currentAmount = 0.0;
            }
        });
        
        // ActionListener für den Button "Ticket exportieren"
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ticketPrice == 0.0) {
                    JOptionPane.showMessageDialog(null, "Bitte berechnen Sie zuerst den Ticketpreis.");
                    return;
                }

                if (currentAmount >= ticketPrice) {
                    double change = currentAmount - ticketPrice;
                    String ticket = generateTicket(ticketPrice, currentAmount, change);
                    ticketArea.setText(ticket);
                    exportTicketAsImage();
                    JOptionPane.showMessageDialog(null, "Restgeld: " + String.format("%.2f €", change));
                } else {
                    JOptionPane.showMessageDialog(null, "Zu wenig Geld eingeworfen. Noch fehlt: " + String.format("%.2f €", ticketPrice - currentAmount));
                }
            }
        });
        
        setVisible(true);
    }

    private String generateTicket(double price, double paidAmount, double change) {
        return String.format("********** TICKET **********\n" +
                             "Preis: %.2f €\n" +
                             "Bezahlter Betrag: %.2f €\n" +
                             "Wechselgeld: %.2f €\n" +
                             "****************************", price, paidAmount, change);
    }

    private void exportTicketAsImage() {
        try {
            BufferedImage image = new BufferedImage(500, 300, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
            
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.PLAIN, 14));

            String ticketText = ticketArea.getText();
            String[] lines = ticketText.split("\n");
            int yPosition = 20;
            for (String line : lines) {
                graphics.drawString(line, 20, yPosition);
                yPosition += 20;
            }

            File outputfile = new File("ticket.png");
            ImageIO.write(image, "PNG", outputfile);
            JOptionPane.showMessageDialog(null, "Ticket erfolgreich als PNG gespeichert!");
            graphics.dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Fehler beim Speichern des Tickets: " + ex.getMessage());
        }
    }

    private void addCoinAndNoteButtons(JPanel panel, GridBagConstraints gbc) {
        double[] coinAndNoteValues = {50.00, 20.00, 10.00, 5.00, 2.00, 1.00, 0.50, 0.20, 0.10, 0.05, 0.02, 0.01};
        String[] buttonLabels = {"50€", "20€", "10€", "5€", "2€", "1€", "50ct", "20ct", "10ct", "5ct", "2ct", "1ct"};

        for (int i = 0; i < coinAndNoteValues.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            final double value = coinAndNoteValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setBackground(new Color(255, 204, 0));
            button.setForeground(Color.BLACK);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentAmount += value;
                    amountToPayLabel.setText("Noch zu bezahlen: " + String.format("%.2f €", ticketPrice - currentAmount));
                }
            });
            gbc.gridx = i % 3;
            gbc.gridy = 4 + (i / 3);
            panel.add(button, gbc);
        }
    }

    public static void main(String[] args) {
        new TicketMachine();
    }
}
