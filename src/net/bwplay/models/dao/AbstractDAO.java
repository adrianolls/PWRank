/*
 *  Adriano Luis Lopes da Silva
 *  Bwplay.net 03/10/2018
 */
package net.bwplay.models.dao;

import net.bwplay.utils.HibernateConnect;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author adriano
 */
public abstract class AbstractDAO {

    public void salvarOuAtualizar(Object obj) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnect.getSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(obj);
            session.flush();
            transaction.commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            transaction.rollback();
            throw new HibernateException("Failed on save proccess in Obj: " + obj.toString() + " " + ex.getMessage() + " " + ex.getCause());

        }
    }

    public void excluir(Object obj) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnect.getSession();
            transaction = session.beginTransaction();
            session.delete(obj);
            transaction.commit();
            session.flush();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new HibernateException("Failed on delete in Obj: " + obj.toString() + " " + ex.getMessage() + " " + ex.getCause());
        }
    }
}
