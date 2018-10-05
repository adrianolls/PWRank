/*
 *  Adriano Luis Lopes da Silva
 *  Bwplay.net 03/10/2018
 */
package net.bwplay.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Properties;
import net.bwplay.api.PerfectWorld;
import net.bwplay.models.bean.Kills;
import net.bwplay.models.bean.Role;
import net.bwplay.models.dao.RoleDAO;
import net.bwplay.models.dao.KillDAO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author adriano
 */
public class RankController implements Runnable {

    private String formatlogpath = "";

    private FileReader file_reader = null;
    private BufferedReader br = null;
    private String brline = null;
    private Properties prop = null;
    private boolean proccessLogFile = false;

    private Role attacker;
    private Role victim;

    private Kills kill;

    private int victimid;
    private int attackerid;

    private String victimdatastring;
    private String attackerdatastring;

    private JSONObject victimobj;
    private JSONObject attackerobj;

    private JSONObject jsondata;

    private KillDAO killdao;
    private RoleDAO characterdao;

    private PerfectWorld api;

    private JSONParser jsonparser;

    public RankController(String formatlogpath) {
        this.api = PerfectWorld.GetInstance();
        this.formatlogpath = formatlogpath;

        this.killdao = new KillDAO();
        this.characterdao = new RoleDAO();
        this.prop = new Properties();
        this.jsonparser = new JSONParser();

        StartLogReader();
    }

    @Override
    public void run() {
        System.out.println("Start of Rank Controller");
        while (true) {
            this.proccessLogFile = proccessLine();
            if (this.proccessLogFile) {
                this.victimid = Integer.valueOf(this.prop.getProperty("roleid"));
                this.attackerid = Integer.valueOf(this.prop.getProperty("attacker"));

                this.victimdatastring = this.api.getRoleData(this.victimid);
                this.attackerdatastring = this.api.getRoleData(this.attackerid);

                try {
                    this.victimobj = (JSONObject) this.jsonparser.parse(this.victimdatastring);
                    this.attackerobj = (JSONObject) this.jsonparser.parse(this.attackerdatastring);
                } catch (ParseException ex) {
                    System.out.println("Error parsing Role JSON from API");
                }
                UpdateUserData();
                RegisterKill();
            }
        }
    }

    private void StartLogReader() {
        try {
            this.file_reader = new FileReader(this.formatlogpath);
            this.br = new BufferedReader(this.file_reader);
            // GOTO EOF
            while ((brline = br.readLine()) != null) {
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Formatlog File not found");
        } catch (IOException ex) {
            System.out.println("IO Issue in Rank Controller");
        }
    }

    private boolean proccessLine() {
        try {
            brline = br.readLine();
            if (brline == null) {
                Thread.sleep(1000L);
                return false;
            } else {
                brline = brline.replaceAll(" ", "\n");
                brline = brline.replaceAll(":", "\n");
                if (brline.contains("die")) {
                    prop.load(new StringReader(brline));
                    int type = Integer.valueOf(prop.getProperty("type"));
                    return type == 258 || type == 2;
                }
            }
        } catch (IOException ex) {
            System.out.println("IO Issue in Rank Controller");
        } catch (InterruptedException ex) {
            System.out.println("Rank Thread Interruption issue");
        }
        return false;
    }

    private void UpdateUserData() {
        this.victim = characterdao.ListarRoleid(this.victimid);
        this.attacker = characterdao.ListarRoleid(this.attackerid);

        if (this.victim == null) {
            this.victim = RegisterCharacter(this.victimobj, true);

        } else {
            this.jsondata = (JSONObject) this.victimobj.get("status");
            this.victim.setLevel(((Long) this.jsondata.get("level")).intValue());
            this.victim.setLevel2(((Long) this.jsondata.get("level2")).intValue());
            this.victim.setReputation(((Long) this.jsondata.get("reputation")).intValue());
            this.victim.setPvp_deads(this.victim.getPvp_deads() + 1);
            characterdao.salvarOuAtualizar(this.victim);
        }
        if (this.attacker == null) {
            this.attacker = RegisterCharacter(this.attackerobj, false);
        } else {
            this.jsondata = (JSONObject) this.attackerobj.get("status");
            this.attacker.setLevel(((Long) this.jsondata.get("level")).intValue());
            this.attacker.setLevel2(((Long) this.jsondata.get("level2")).intValue());
            this.attacker.setReputation(((Long) this.jsondata.get("reputation")).intValue());
            this.attacker.setPvp_kills(this.attacker.getPvp_kills() + 1);
            characterdao.salvarOuAtualizar(this.attacker);
        }

    }

    private Role RegisterCharacter(JSONObject obj, boolean victim) {
        this.jsondata = (JSONObject) obj.get("base");

        Role model = new Role();
        model.setRoleid(((Long) this.jsondata.get("id")).intValue());
        model.setUserid(((Long) this.jsondata.get("userid")).intValue());
        model.setName((String) this.jsondata.get("name"));
        model.setOccupation(((Long) this.jsondata.get("cls")).intValue());
        model.setGender(((Long) this.jsondata.get("gender")).intValue());

        this.jsondata = (JSONObject) obj.get("status");

        model.setLevel(((Long) this.jsondata.get("level")).intValue());
        model.setLevel2(((Long) this.jsondata.get("level2")).intValue());
        model.setReputation(((Long) this.jsondata.get("reputation")).intValue());

        model.setPvp_deads(victim ? 1 : 0);
        model.setPvp_kills(victim ? 0 : 1);

        characterdao.salvarOuAtualizar(model);
        return model;
    }

    private void RegisterKill() {
        this.kill = new Kills();
        this.kill.setAttacker(this.attackerid);
        this.kill.setVictim(this.victimid);
        this.kill.setCreated_at(Calendar.getInstance());
        this.kill.setUpdated_at(Calendar.getInstance());
        this.killdao.salvarOuAtualizar(kill);
    }

}
