/*
 *  Adriano Luis Lopes da Silva
 *  Bwplay.net 03/10/2018
 */
package net.bwplay.models.bean;

/**
 *
 * @author adria
 */
public class Settings {

    private String baseurl = "";
    private String apikey = "";
    private String formatlogpath = "";

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getFormatlogpath() {
        return formatlogpath;
    }

    public void setFormatlogpath(String formatlogpath) {
        this.formatlogpath = formatlogpath;
    }

}
