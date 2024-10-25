package org.example.demo1.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.demo1.entities.Media;

import java.util.List;

@Stateless
public class MediaServiceBean {

    @PersistenceContext
    private EntityManager em;

    public List<Media> getAllMedia() {
        return em.createQuery("SELECT m FROM Media m", Media.class).getResultList();
    }

    public void addMedia(Media media) {
        em.persist(media);
    }

    public Media getMediaById(Long id) {
        return em.find(Media.class, id);
    }

    public void updateMedia(Media media) {
        em.merge(media);
    }
}
