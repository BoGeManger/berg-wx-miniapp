package com.berg.utils;

import com.berg.exception.AppException;
import com.berg.exception.FailException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpClientUtil {
    static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000)
            .setSocketTimeout(20000).setConnectTimeout(20000).build();

    static CloseableHttpClient httpclient = HttpClients.createDefault();

    static CloseableHttpClient httpsclient;

    static Map<String, CloseableHttpClient> httpclientMap = new HashMap<String, CloseableHttpClient>();

    static {
        //绕过验证
        try {
            SSLContext sslContext = SSLContext.getInstance("SSLv3");

            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLConnectionSocketFactory sslFactory = new SSLConnectionSocketFactory(sslContext);
            httpsclient = HttpClients.custom().setSSLSocketFactory(sslFactory).build();
        } catch (Exception e) {
            log.error("init处理HttpsClient异常", e);
        }
    }

    /**
     * get请求，可自定义httpsclient
     *
     * @param url
     * @param clientCustom
     * @return String
     * @author: liuli
     * @version 1.0 初稿
     */
    public static String httpGet(String url, CloseableHttpClient clientCustom) {
        CloseableHttpClient client;
        if (clientCustom != null) {
            client = clientCustom;
        } else if (url.equals("https")) {
            client = httpsclient;
        } else {
            client = httpclient;
        }

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        String result = "";

        try {
            response = client.execute(httpGet);
            if (200 != response.getStatusLine().getStatusCode()) {
                log.error("[HttpClient Get 请求错误] url:[%s], statusCode:[%s]", url, response.getStatusLine().getStatusCode());
                throw new FailException(String.format("[HttpClient Get 请求错误] url:[%s], statusCode:[%s]", url, response.getStatusLine().getStatusCode()));
            }
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("[HttpClient Get 请求错误] url:[{}]", url);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * Post请求，key-value传参。可自定义httpsclient
     *
     * @param url
     * @param data
     * @param clientCustom
     * @return String
     * @author: liuli
     * @version 1.0 初稿
     */
    public static String httpPost(String url, Map<String, String> data, CloseableHttpClient clientCustom) {
        CloseableHttpClient client;
        if (clientCustom != null) {
            client = clientCustom;
        } else if (url.equals("https")) {
            client = httpsclient;
        } else {
            client = httpclient;
        }
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        String result = "";
        for (String key : data.keySet()) {
            nvps.add(new BasicNameValuePair(key, data.get(key)));
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            response = client.execute(httpPost);
            if (200 != response.getStatusLine().getStatusCode()) {
                log.error("[HttpClient Post 请求错误] url:[%s], statusCode:[%s]", url, response.getStatusLine().getStatusCode());
                throw new FailException(String.format("[HttpClient Post 请求错误] url:[%s], statusCode:[%s]", url, response.getStatusLine().getStatusCode()));
            }
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("[HttpClient Post 请求错误] url:[{}], data: [{}]", url, data);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * Post请求，字符串传参。可自定义httpsclient
     *
     * @param url
     * @param data
     * @param clientCustom
     * @return String
     * @author: liuli
     * @version 1.0 初稿
     */
    public static String httpPost(String url, String data, Map<String, String> headers, CloseableHttpClient clientCustom) {
        CloseableHttpClient client;
        if (clientCustom != null) {
            client = clientCustom;
        } else if (url.equals("https")) {
            client = httpsclient;
        } else {
            client = httpclient;
        }

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        String result = "";

        try {
            httpPost.setEntity(new StringEntity(data, "utf-8"));
            httpPost.setHeaders(assemblyHeader(headers));
            response = client.execute(httpPost);
            if (200 != response.getStatusLine().getStatusCode()) {
                log.error("[HttpClient Post 请求错误] url:[%s], statusCode:[%s]", url, response.getStatusLine().getStatusCode());
                throw new FailException(String.format("[HttpClient Post 请求错误] url:[%s], statusCode:[%s]", url, response.getStatusLine().getStatusCode()));
            }
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("[HttpClient Post 请求错误] url:[{}], data: [{}], e: [{}]", url, data, e.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 获取含证书的自定义SSL HttpClient
     *
     * @param certPasth    证书Path
     * @param certPassWord 证书密码
     * @param certId       证书id
     * @return CloseableHttpClient
     * @throws Exception
     * @author: liuli
     * @version 1.0 初稿
     */
    public CloseableHttpClient getClientCustomSSL(String certPasth, String certPassWord, String certId) throws Exception {
        if (httpclientMap.get(certId) == null) {
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(new File(certPasth), certPassWord.toCharArray(),
                            new TrustSelfSignedStrategy())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();

            return httpclient;
        } else {
            return httpclientMap.get(certId);
        }
    }

    /**
     * 上传文件
     *
     * @param url
     * @param localFile
     * @param clientCustom
     * @param param
     * @param filekey
     * @return String
     * @author: liuli
     * @version 1.0 初稿
     */
    @SuppressWarnings("unused")
    public static String uploadFile(String url, String localFile, CloseableHttpClient clientCustom, Map<String, String> param, String filekey) {
        CloseableHttpClient client;
        if (clientCustom != null) {
            client = clientCustom;
        } else if (url.equals("https")) {
            client = httpsclient;
        } else {
            client = httpclient;
        }
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        //httpPost.setHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryJ2CGHPDI7uG9Omow");
        //httpPost.setHeader("access_token", "99558c817d1b43e2bea9c44ba472b221");
        String result = "";
        File file = new File(localFile);
        FileBody bin = new FileBody(new File(localFile));
		
		/*StringBody filename = new StringBody(localFile, ContentType.create("text/plain", Consts.UTF_8));
		StringBody acn = new StringBody(ac, ContentType.create("text/plain", Consts.UTF_8));*/
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart(filekey, bin);
        for (String key : param.keySet()) {
            builder.addPart(key, new StringBody(param.get(key), ContentType.create("text/plain", Consts.UTF_8)));
        }
		/*HttpEntity reqEntity = MultipartEntityBuilder.create()
				.addPart(filekey, bin)
				.addPart("access_token", acn)*/
        HttpEntity reqEntity = builder.build();

        httpPost.setEntity(reqEntity);

        try {
            response = client.execute(httpPost);
            if (200 != response.getStatusLine().getStatusCode()) {
                log.error("[HttpClient请求错误] url:[%s], statusCode:[%s]", url, response.getStatusLine().getStatusCode());
                throw new FailException(String.format("[HttpClient请求错误] url:[%s], statusCode:[%s]", url, response.getStatusLine().getStatusCode()));
            }
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 功能描述：组装头部信息
     *
     * @param headers
     * @return
     */
    public static Header[] assemblyHeader(Map<String, String> headers) {
        if (null == headers) {
            return new Header[]{};
        }

        Header[] allHeader = new BasicHeader[headers.size()];
        int i = 0;
        for (String str : headers.keySet()) {
            allHeader[i] = new BasicHeader(str, headers.get(str));
            i++;
        }
        return allHeader;
    }

}
