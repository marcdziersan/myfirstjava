import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class DirectoryExplorer extends JFrame {
    private JTree tree;
    private DefaultTreeModel treeModel;
    private JButton loadButton, saveButton;

    public DirectoryExplorer() {
        setTitle("TreeView Verzeichnis");
        setSize(600, 437);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Erstelle den Baum und das Modell
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);

        // Erstelle Buttons
        JPanel panel = new JPanel();
        loadButton = new JButton("Auslesen");
        saveButton = new JButton("Speichern");
        panel.add(loadButton);
        panel.add(saveButton);
        add(panel, BorderLayout.SOUTH);

        // Button-Listener als explizite innere Klassen
        loadButton.addActionListener(new LoadButtonActionListener());
        saveButton.addActionListener(new SaveButtonActionListener());
    }

    private void loadDirectory() {
        // Verzeichnis auswählen
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = chooser.getSelectedFile();
            DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
            rootNode.removeAllChildren(); // Baumnodes leeren
            addDirectoryToTreeView(selectedDirectory.toPath(), rootNode);
            treeModel.reload();
        }
    }

    private void addDirectoryToTreeView(Path path, DefaultMutableTreeNode parentNode) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(entry.getFileName().toString());
                parentNode.add(node);
                if (Files.isDirectory(entry)) {
                    // Rekursiv für Unterverzeichnisse
                    addDirectoryToTreeView(entry, node);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDirectory() {
        // Speichern Dialog
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Textdateien", "txt"));
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            // Überprüfe, ob die Dateiendung ".txt" vorhanden ist
            if (!selectedFile.getName().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
                saveTreeNodeToFile(rootNode, writer, "");
                JOptionPane.showMessageDialog(this, "Die Verzeichnisstruktur wurde erfolgreich gespeichert.");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Fehler beim Speichern: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveTreeNodeToFile(DefaultMutableTreeNode node, BufferedWriter writer, String indent) throws IOException {
        writer.write(indent + node.getUserObject().toString());
        writer.newLine();
        for (int i = 0; i < node.getChildCount(); i++) {
            saveTreeNodeToFile((DefaultMutableTreeNode) node.getChildAt(i), writer, indent + "  ");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DirectoryExplorer frame = new DirectoryExplorer();
                frame.setVisible(true);
            }
        });
    }

    // ActionListener für den Load-Button
    private class LoadButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadDirectory();
        }
    }

    // ActionListener für den Save-Button
    private class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveDirectory();
        }
    }
}
