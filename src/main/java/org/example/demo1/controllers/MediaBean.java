package org.example.demo1.controllers;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.demo1.ejbs.LoanServiceBean;
import org.example.demo1.ejbs.MediaServiceBean;
import org.example.demo1.entities.Media;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class MediaBean implements Serializable {

    @Inject
    private MediaServiceBean mediaService;

    @Inject
    private LoanServiceBean loanService; // Injection de LoanServiceBean

    private List<Media> mediaList;
    private Long selectedMediaId;
    private String borrowerName;

    public List<Media> getMediaList() {
        if (mediaList == null) {
            mediaList = mediaService.getAllMedia();
        }
        return mediaList;
    }

    public void borrowMedia() {
        if (selectedMediaId != null && borrowerName != null) {
            loanService.borrowMedia(selectedMediaId, borrowerName); // Utilisation de loanService
            mediaList = mediaService.getAllMedia(); // Mise à jour de la liste
        }
    }

    public void returnMedia(Long mediaId) {
        loanService.returnMedia(mediaId); // Utilisation de loanService
        mediaList = mediaService.getAllMedia(); // Mise à jour de la liste
    }

    public MediaServiceBean getMediaService() {
        return mediaService;
    }

    public void setMediaService(MediaServiceBean mediaService) {
        this.mediaService = mediaService;
    }

    public LoanServiceBean getLoanService() {
        return loanService;
    }

    public void setLoanService(LoanServiceBean loanService) {
        this.loanService = loanService;
    }

    public void setMediaList(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    public Long getSelectedMediaId() {
        return selectedMediaId;
    }

    public void setSelectedMediaId(Long selectedMediaId) {
        this.selectedMediaId = selectedMediaId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }
}

