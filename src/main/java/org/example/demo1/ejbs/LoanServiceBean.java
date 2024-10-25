package org.example.demo1.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.demo1.entities.Loan;
import org.example.demo1.entities.Media;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class LoanServiceBean {

    @PersistenceContext
    private EntityManager em;

    public void borrowMedia(Long mediaId, String borrowerName) {
        Media media = em.find(Media.class, mediaId);
        if (media != null && media.getAvailable()) {
            media.setAvailable(false);
            Loan loan = new Loan();
            loan.setMedia(media);
            loan.setBorrowerName(borrowerName);
            loan.setBorrowDate(LocalDate.now());
            em.persist(loan);
        }
    }

    public void returnMedia(Long mediaId) {
        Media media = em.find(Media.class, mediaId);
        if (media != null) {
            media.setAvailable(true);
            Loan loan = em.createQuery("SELECT l FROM Loan l WHERE l.media = :media AND l.returnDate IS NULL", Loan.class)
                    .setParameter("media", media)
                    .getSingleResult();
            loan.setReturnDate(LocalDate.now());
            em.merge(loan);
        }
    }

    public List<Loan> getCurrentLoans() {
        return em.createQuery("SELECT l FROM Loan l WHERE l.returnDate IS NULL", Loan.class).getResultList();
    }
}

