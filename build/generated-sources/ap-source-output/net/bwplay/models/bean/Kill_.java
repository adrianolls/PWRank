package net.bwplay.models.bean;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Kill.class)
public abstract class Kill_ {

	public static volatile SingularAttribute<Kill, Integer> id;
	public static volatile SingularAttribute<Kill, Calendar> updated_at;
	public static volatile SingularAttribute<Kill, Integer> victim;
	public static volatile SingularAttribute<Kill, Calendar> created_at;
	public static volatile SingularAttribute<Kill, Integer> attacker;

}

