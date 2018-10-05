/*
 *  Adriano Luis Lopes da Silva
 *  Bwplay.net 03/10/2018
 */
package net.bwplay.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.bwplay.models.bean.Settings;

/**
 *
 * @author adria
 */
public class LoadSettings {

    public static Settings Load(String path) {
        Properties prop = new Properties();
        BufferedReader br;
        Settings settings = new Settings();
        try {
            prop.load(new BufferedReader(new FileReader(path)));
            settings.setApikey(prop.getProperty("apikey"));
            settings.setBaseurl(prop.getProperty("baseurl"));
            settings.setFormatlogpath(prop.getProperty("formatlog_path"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadSettings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoadSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return settings;
    }

}
