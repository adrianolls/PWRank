/*
 *  Adriano Luis Lopes da Silva
 *  Bwplay.net 03/10/2018
 */
package net.bwplay.models.bean;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author adria
 */
@Entity
public class Kills implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private int attacker;
    private int victim;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created_at;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttacker() {
        return attacker;
    }

    public void setAttacker(int attacker) {
        this.attacker = attacker;
    }

    public int getVictim() {
        return victim;
    }

    public void setVictim(int victim) {
        this.victim = victim;
    }

    public Calendar getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Calendar created_at) {
        this.created_at = created_at;
    }

    public Calendar getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Calendar updated_at) {
        this.updated_at = updated_at;
    }

}
