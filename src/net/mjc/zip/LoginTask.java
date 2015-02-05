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
//            HttpParams httpParams = new BasicHttpParams();
//
//            SSLContext ctx = SSLContext.getInstance("SSL");
//            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
//            SSLContext.setDefault(ctx);
//
//            HttpClientParams.setRedirecting(httpParams, true);

//            SSLSocketFactory sf = new SSLSocketFactory(ctx);
//            Scheme httpsScheme = new Scheme("https", sf, 443);
//            SchemeRegistry schemeRegistry = new SchemeRegistry();
//            schemeRegistry.register(httpsScheme);
//
//            ClientConnectionManager cm = new SingleClientConnManager(httpParams); //, schemeRegistry);
//            HttpClient client = new DefaultHttpClient(httpParams);
//            conn.setHostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String arg0, SSLSession arg1) {
//                    return true;
//                }
//            });


            HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

            DefaultHttpClient client = new DefaultHttpClient();

            SchemeRegistry registry = new SchemeRegistry();
            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
            socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
            registry.register(new Scheme("https", socketFactory, 443));
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

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    protected void onPostExecute(String token) {
        listener.onComplete(token, exception);
    }
}


