package net.mjc.zip;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class LoginTask extends AsyncTask<String, Void, String> {

    interface Listener {
        void onComplete(String token, Exception ex);
    }

    private Listener listener;
    private Exception exception;

    public LoginTask(Listener listener) {
        this.listener = listener;
    }

    protected String doInBackground(String... urls) {
        try {
            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
            HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", socketFactory, 443));

            DefaultHttpClient client = new DefaultHttpClient();
            SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);

            DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());
            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

            HttpPost loginReq = new HttpPost();
            loginReq.setURI(new URI("https://test.zipid.com.au/api/ios/login")); //?username=1&password=SeleniumTest1"));
            loginReq.setEntity(new StringEntity("username=1&password=password"));

            HttpResponse response = httpClient.execute(loginReq);

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return EntityUtils.toString(response.getEntity());

            }
            this.exception = new RuntimeException("Failed to login " +
                    response.getStatusLine().getStatusCode() + ":" +
                    response.getStatusLine().getReasonPhrase());
            return null;

        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String token) {
        listener.onComplete(token, exception);
    }
}


