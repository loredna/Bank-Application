package com.bank.application.repository;

import com.bank.application.model.Transaction;
import com.bank.application.util.SessionFactoryUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.logging.Logger;

public class TransactionRepository {
    private static final Logger LOGGER = Logger.getLogger(TransactionRepository.class.getName());

    public static Long addTransaction(Transaction transaction) {
        Session session = SessionFactoryUtil.getSessionFactory().openSession();
        org.hibernate.Transaction tx = null;
        Long transactionID = null;

        try {
            tx = session.beginTransaction();
            transactionID = (Long) session.save(transaction);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.fine(e.getLocalizedMessage());
        } finally {
            session.close();
        }
        return transactionID;
    }
}
