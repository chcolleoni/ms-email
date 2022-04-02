package com.ms.email.repositories;

import com.ms.email.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.time.Instant;
import java.util.Optional;

@Repository
public interface IEmailRepositoryBasic extends JpaRepository<Email, Long> {

    default Optional<?> save(EntityManager em, Email email) {

        StoredProcedureQuery oraProcedure = em.createStoredProcedureQuery("PKG_MS_EMAIL.P_SAVE_EMAIL")
                .registerStoredProcedureParameter("P_EMAIL_FROM", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("P_EMAIL_TO", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("P_SUBJECT", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("P_STATUS_EMAIL", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("P_TEXT_EMAIL", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("P_DATE_SEND", Instant.class, ParameterMode.IN)
                .registerStoredProcedureParameter("P_RES", Long.class, ParameterMode.OUT)
                .setParameter("P_EMAIL_FROM", email.getEmailFrom())
                .setParameter("P_EMAIL_TO", email.getEmailTo())
                .setParameter("P_SUBJECT", email.getSubject())
                .setParameter("P_STATUS_EMAIL", email.getStatusEmail())
                .setParameter("P_TEXT_EMAIL", email.getText())
                .setParameter("P_DATE_SEND", email.getSendDateEmail()
                );

        oraProcedure.execute();
        Long vRes = (Long) oraProcedure.getOutputParameterValue("P_RES");
        email.setEmailId(vRes);

        return Optional.ofNullable(vRes);

    }


}