/**
 * Agradecimentos a minha familia, aos meus pais que me incentivam a faculdade,
 * a minha linda namorada, a todos os meus amigos, meu cachorros e gatos A Voce
 * que chegou tao longe e agora esta lendo este documento, muito obrigado,
 * desejo o melhor para ti e que tudo deste projeto seja util a seus futuros
 * projetos
 */
package net.bwplay.utils;

import java.io.File;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * @author adriano
 */
public class HibernateConnect {

    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
            File file = new File("database.xml");
            SESSION_FACTORY = new Configuration().configure(file).buildSessionFactory();
        } catch (HibernateException ex) {
            System.err.println("Failed to connect to database: [" + ex + "]");
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static Session getSession() {
        return SESSION_FACTORY.openSession();
    }

    public void close() {
        if (getSession() != null && getSession().isOpen()) {
            getSession().close();
        }
    }

    

}
