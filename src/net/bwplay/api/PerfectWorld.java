/*
 *  Adriano Luis Lopes da Silva
 *  Bwplay.net 03/10/2018
 */
package net.bwplay.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author adria
 */
public class PerfectWorld {

    private static final PerfectWorld INSTANCE = new PerfectWorld();
    private final CloseableHttpClient client;
    HttpResponse response;
    List<NameValuePair> parameters;
    HttpPost post;

    private String baseurl = "";
    private String apikey = "";

    public PerfectWorld() {
        this.client = HttpClientBuilder.create().build();
    }

    public static PerfectWorld GetInstance() {
        return INSTANCE;
    }

    public void setBaseUrl(String baseurl) {
        this.baseurl = baseurl;
    }

    public void setApiKey(String apikey) {
        this.apikey = apikey;
    }

    public String getRoleData(int roleid) {
        this.post = new HttpPost(this.createUrl("role/character"));
        this.post.setHeader("apikey", this.apikey);
        this.parameters = new ArrayList<>();
        this.parameters.add(new BasicNameValuePair("roleid", String.valueOf(roleid)));
        try {
            post.setEntity(new UrlEncodedFormEntity(this.parameters));
            this.response = client.execute(post);
            String JsonResponse = EntityUtils.toString(this.response.getEntity(), StandardCharsets.UTF_8);
            return JsonResponse;
        } catch (UnsupportedEncodingException ex) {
            System.out.println("API Unsupported instruction entry");
        } catch (IOException ex) {
            System.out.println("API IO issue");
        }
        return "Empty response";
    }

    private String createUrl(String uri) {
        return baseurl + uri;
    }

}
