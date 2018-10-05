/*
 *  Adriano Luis Lopes da Silva
 *  Bwplay.net 03/10/2018
 */
package net.bwplay.models.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import net.bwplay.models.bean.Role;
import net.bwplay.utils.HibernateConnect;

/**
 *
 * @author adriano
 */
public class RoleDAO extends AbstractDAO {

    public Role ListarRoleid(int roleid) {
        Session session = HibernateConnect.getSession();
        try {
            Criteria criteria = session.createCriteria(Role.class);
            criteria.add(Restrictions.eq("roleid", roleid));
            if (criteria.list().size() == 0) {
                return null;
            } else {
                return (Role) criteria.list().get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    
}
