package org.example.demo1.ejbs;


import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.demo1.entities.Media;

import java.util.List;

@Stateful
public class AdminServiceBean {

    @PersistenceContext
    private EntityManager em;

    private Media currentMedia; // To hold the currently selected media

    public void addMedia(Media media) {
        em.persist(media);
    }

    public void updateMedia(Media media) {
        em.merge(media);
    }

    public void deleteMedia(Long mediaId) {
        Media media = em.find(Media.class, mediaId);
        if (media != null) {
            em.remove(media);
        }
    }

    public List<Media> getAllMedia() {
        return em.createQuery("SELECT m FROM Media m", Media.class).getResultList();
    }

    public Media getMediaById(Long id) {
        return em.find(Media.class, id);
    }

    // Set the currently selected media
    public void setCurrentMedia(Media media) {
        this.currentMedia = media;
    }

    // Get the currently selected media
    public Media getCurrentMedia() {
        return currentMedia;
    }

    // Clear the current media selection
    public void clearCurrentMedia() {
        currentMedia = null;
    }
}
