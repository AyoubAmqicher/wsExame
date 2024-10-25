package org.example.demo1.models;

import org.example.demo1.entities.Media;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MediaTableModel extends AbstractTableModel {
    private List<Media> mediaList;
    private final String[] columnNames = {"ID", "Titre", "Artiste", "Genre", "Type"};

    public MediaTableModel(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    @Override
    public int getRowCount() {
        return mediaList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Media media = mediaList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return media.getId();
            case 1:
                return media.getTitle();
            case 2:
                return media.getType();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Long getMediaId(int rowIndex) {
        return mediaList.get(rowIndex).getId();
    }

    public void updateMedia(List<Media> mediaList) {
        this.mediaList = mediaList;
        fireTableDataChanged();
    }
}

