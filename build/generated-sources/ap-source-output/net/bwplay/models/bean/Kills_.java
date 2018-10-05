package net.bwplay.models.bean;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Kills.class)
public abstract class Kills_ {

	public static volatile SingularAttribute<Kills, Integer> id;
	public static volatile SingularAttribute<Kills, Calendar> updated_at;
	public static volatile SingularAttribute<Kills, Integer> victim;
	public static volatile SingularAttribute<Kills, Calendar> created_at;
	public static volatile SingularAttribute<Kills, Integer> attacker;

}

