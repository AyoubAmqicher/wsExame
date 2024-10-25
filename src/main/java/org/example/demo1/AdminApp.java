package org.example.demo1;

import org.example.demo1.ejbs.AdminServiceBean;
import org.example.demo1.entities.Media;
import org.example.demo1.models.MediaTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminApp extends JFrame {
    private AdminServiceBean adminService; // Stateful bean instance
    private JTextField titleField;
    private JTextField artistField;
    private JTextField genreField;
    private JComboBox<String> typeComboBox; // CD or DVD
    private JTable mediaTable;
    private MediaTableModel tableModel;

    public AdminApp(AdminServiceBean adminService) {
        this.adminService = adminService;

        setTitle("Gestion des CD/DVD");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for media fields
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Titre :"));
        titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("Artiste :"));
        artistField = new JTextField();
        panel.add(artistField);

        panel.add(new JLabel("Genre :"));
        genreField = new JTextField();
        panel.add(genreField);

        panel.add(new JLabel("Type :"));
        typeComboBox = new JComboBox<>(new String[]{"CD", "DVD"});
        panel.add(typeComboBox);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener((ActionListener) new AddMediaActionListener());
        panel.add(addButton);

        JButton updateButton = new JButton("Modifier");
        updateButton.addActionListener(new UpdateMediaActionListener());
        panel.add(updateButton);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new DeleteMediaActionListener());
        panel.add(deleteButton);

        add(panel, BorderLayout.NORTH);

        // Table to display CDs/DVDs
        tableModel = new MediaTableModel(adminService.getAllMedia());
        mediaTable = new JTable(tableModel);
        mediaTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = mediaTable.getSelectedRow();
            if (selectedRow >= 0) {
                Long mediaId = tableModel.getMediaId(selectedRow);
                Media selectedMedia = adminService.getMediaById(mediaId);
                adminService.setCurrentMedia(selectedMedia);
                fillFieldsWithMedia(selectedMedia);
            }
        });
        add(new JScrollPane(mediaTable), BorderLayout.CENTER);

        setVisible(true);
    }

    // Fill fields with selected media details
    private void fillFieldsWithMedia(Media media) {
        titleField.setText(media.getTitle());
        typeComboBox.setSelectedItem(media.getType());
    }

    // ActionListener for adding a CD/DVD
    private class AddMediaActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Media media = new Media();
            media.setTitle(titleField.getText());

            media.setType((String) typeComboBox.getSelectedItem());
            adminService.addMedia(media);
            tableModel.updateMedia(adminService.getAllMedia());
            clearFields();
        }
    }

    // ActionListener for updating a CD/DVD
    private class UpdateMediaActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Media currentMedia = adminService.getCurrentMedia();
            if (currentMedia != null) {
                currentMedia.setTitle(titleField.getText());
                currentMedia.setType((String) typeComboBox.getSelectedItem());
                adminService.updateMedia(currentMedia);
                tableModel.updateMedia(adminService.getAllMedia());
                clearFields();
                adminService.clearCurrentMedia(); // Clear selection after update
            }
        }
    }

    // ActionListener for deleting a CD/DVD
    private class DeleteMediaActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Media currentMedia = adminService.getCurrentMedia();
            if (currentMedia != null) {
                adminService.deleteMedia(currentMedia.getId());
                tableModel.updateMedia(adminService.getAllMedia());
                clearFields();
                adminService.clearCurrentMedia(); // Clear selection after deletion
            }
        }
    }

    // Method to clear text fields
    private void clearFields() {
        titleField.setText("");
        artistField.setText("");
        genreField.setText("");
        typeComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        // Initialize the EJB (you need to configure this based on your environment)
        AdminServiceBean adminService = new AdminServiceBean(); // Replace with appropriate injection method
        new AdminApp(adminService);
    }
}
