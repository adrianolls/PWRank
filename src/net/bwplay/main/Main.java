package net.bwplay.main;

import net.bwplay.api.PerfectWorld;
import net.bwplay.controllers.RankController;
import net.bwplay.models.bean.Settings;
import net.bwplay.utils.HibernateConnect;
import net.bwplay.utils.LoadSettings;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.SessionFactory;

/**
 *
 * @author Adriano
 */
public class Main {

    public static Settings settings;
    private static RankController RankService;
    private static Thread RankThread;

    private static SessionFactory session;

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4.properties");
        settings = LoadSettings.Load("settings.cfg");
        PerfectWorld api = PerfectWorld.GetInstance();
        api.setApiKey(settings.getApikey());
        api.setBaseUrl(settings.getBaseurl());
        session = HibernateConnect.getSessionFactory();
        try {
            session.openSession().createSQLQuery("select 1").list();
        } catch (Exception ex) {
            System.out.println("Invalid MySQL Connection: " + ex.getMessage() + " | Verify database.xml");
            System.exit(0);
        }
        RankService = new RankController(settings.getFormatlogpath());
        RankThread = new Thread(RankService);
        RankThread.start();
        System.out.println("Startup complete");
        KeepAliveProccess(settings);
    }

    private static void KeepAliveProccess(Settings settings) {
        while (true) {
            try {
                Thread.sleep(3000L);
                if (RankThread.getState() == Thread.State.TERMINATED) {
                    RankService = new RankController(settings.getFormatlogpath());
                    RankThread = new Thread(RankService);
                    RankThread.start();
                    System.out.println("RankController Restarted | https://github.com/adrianolls/ to report issue");
                }
            } catch (InterruptedException ex) {
                System.out.println("Main Thread Interruption issue");
            }
        }
    }
}
