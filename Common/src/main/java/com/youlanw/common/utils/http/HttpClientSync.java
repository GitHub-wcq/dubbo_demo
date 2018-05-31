package com.youlanw.common.utils.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Lookup;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.auth.KerberosSchemeFactory;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

/**
 * 同步的HTTP请求对象，支持post与get方法以及可设置代理
 */
public class HttpClientSync {
	// 默认的等待数据超时时间（秒）
	private int socketTimeout = 1000;
	// 默认的连接超时时间
	private int connectTimeout = 2000;
	// 从connect Manager获取Connection 超时时间
	private int connectionRequestTimeout = 2000;

	// 默认的连接池最大连接数
	private int poolSize = 3000;
	// 默认的最大并发限制1500
	private int maxPerRoute = 1500;

	// http代理相关参数
	private String proxyHost = "";
	private int proxyPort = 0;
	private String proxyUsername = "";
	private String proxyPassword = "";

	// 同步httpclient
	private CloseableHttpClient syncHttpClient;
	// 同步加代理httpclient
	private CloseableHttpClient proxySyncHttpClient;

	// region 构造函数

	/*
	 * 构造函数-不使用代理，使用默认连接参数
	 */
	public HttpClientSync() {
		try {
			this.syncHttpClient = createSyncClient(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 构造函数-使用代理，使用默认连接参数
	 */
	public HttpClientSync(String host, int port, String username, String password) {
		this.proxyHost = host;
		this.proxyPort = port;
		this.proxyUsername = username;
		this.proxyPassword = password;

		try {
			this.proxySyncHttpClient = createSyncClient(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 构造函数-不使用代理，指定连接配置
	 */
	public HttpClientSync(int socketTimeout, int connectTimeout, int connectionRequestTimeout, int poolSize,
			int maxPerRoute) {
		this.socketTimeout = socketTimeout;
		this.connectTimeout = connectTimeout;
		this.poolSize = poolSize;
		this.maxPerRoute = maxPerRoute;
		this.connectionRequestTimeout = connectionRequestTimeout;

		try {
			this.syncHttpClient = createSyncClient(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 构造函数-使用代理，指定连接配置
	 */
	public HttpClientSync(int socketTimeout, int connectTimeout, int connectionRequestTimeout, int poolSize,
			int maxPerRoute, String host, int port, String username, String password) {
		this.socketTimeout = socketTimeout;
		this.connectTimeout = connectTimeout;
		this.poolSize = poolSize;
		this.maxPerRoute = maxPerRoute;
		this.connectionRequestTimeout = connectionRequestTimeout;

		this.proxyHost = host;
		this.proxyPort = port;
		this.proxyUsername = username;
		this.proxyPassword = password;

		try {
			this.proxySyncHttpClient = createSyncClient(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// end

	/*
	 * 创建连接对象
	 */
	private CloseableHttpClient createSyncClient(Boolean proxy)
			throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
			MalformedChallengeException, IOReactorException {

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();

		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(proxyUsername, proxyPassword);
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, credentials);

		// 设置协议http和https对应的处理socket链接工厂的对象
		SSLContext sslcontext = SSLContexts.createDefault();
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new PlainConnectionSocketFactory())
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();

		PoolingHttpClientConnectionManager conMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

		if (poolSize > 0) {
			conMgr.setMaxTotal(poolSize);
		}

		if (maxPerRoute > 0) {
			conMgr.setDefaultMaxPerRoute(maxPerRoute);
		} else {
			conMgr.setDefaultMaxPerRoute(10);
		}

		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).build();

		Lookup<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder.<AuthSchemeProvider>create()
				.register(AuthSchemes.BASIC, new BasicSchemeFactory())
				.register(AuthSchemes.DIGEST, new DigestSchemeFactory())
				.register(AuthSchemes.NTLM, new NTLMSchemeFactory())
				.register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory())
				.register(AuthSchemes.KERBEROS, new KerberosSchemeFactory()).build();
		conMgr.setDefaultConnectionConfig(connectionConfig);

		if (proxy) {
			return HttpClients.custom().setConnectionManager(conMgr).setDefaultCredentialsProvider(credentialsProvider)
					.setDefaultAuthSchemeRegistry(authSchemeRegistry).setProxy(new HttpHost(proxyHost, proxyPort))
					.setDefaultCookieStore(new BasicCookieStore()).setDefaultRequestConfig(requestConfig).build();
		} else {
			return HttpClients.custom().setConnectionManager(conMgr).setDefaultCredentialsProvider(credentialsProvider)
					.setDefaultAuthSchemeRegistry(authSchemeRegistry).setDefaultCookieStore(new BasicCookieStore())
					.build();
		}

	}

	/*
	 * 获取不使用代理的连接对象
	 */
	private CloseableHttpClient getSyncHttpClient() {
		return syncHttpClient;
	}

	/*
	 * 获取使用代理的连接对象
	 */
	private CloseableHttpClient getProxySyncHttpClient() {
		return proxySyncHttpClient;
	}

	/*
	 * 同步 Get 方法
	 */
	public String httpGet(String url, Map<String, String> headers, List<BasicNameValuePair> parameters, Boolean proxy) {
		CloseableHttpClient client;
		if (proxy)
			client = getProxySyncHttpClient();
		else
			client = getSyncHttpClient();

		HttpGet getMethod = null;
		String returnValue = "";
		try {
			getMethod = new HttpGet(url);

			if (null != parameters) {
				String params = EntityUtils.toString(new UrlEncodedFormEntity(parameters, "UTF-8"));
				getMethod.setURI(new URI(getMethod.getURI().toString() + "?" + params));
				System.out.println("httpGet-getUrl: " + getMethod.getURI());
			}
			
			if (headers != null && !headers.isEmpty()) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					getMethod.addHeader(entry.getKey(), entry.getValue());
				}
			}

			HttpResponse response = client.execute(getMethod);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				HttpEntity he = response.getEntity();
				returnValue = new String(EntityUtils.toByteArray(he), "UTF-8");
				return returnValue;
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println(Thread.currentThread().getName() + "httpGet Send Error,Code error:" + e.getMessage());
		} catch (ClientProtocolException e) {
			System.out.println(Thread.currentThread().getName() + "httpGet Send Error,Protocol error:" + e.getMessage());
		} catch (IOException e) {
			System.out.println(Thread.currentThread().getName() + "httpGet Send Error,IO error:" + e.getMessage());
		} catch (URISyntaxException e) {
			System.out.println(Thread.currentThread().getName() + "httpGet Send Error,IO error:" + e.getMessage());
		} finally {// 释放连接,将连接放回到连接池
			getMethod.releaseConnection();

		}
		return returnValue;

	}

	/*
	 * 同步 Post 方法
	 */
	public String httpPost(String url, Map<String, String> headers, List<BasicNameValuePair> parameters,
			String requestBody, Boolean proxy) {
		CloseableHttpClient client;
		if (proxy)
			client = getProxySyncHttpClient();
		else
			client = getSyncHttpClient();

		HttpPost postMethod = null;
		String returnValue = "";
		try {
			postMethod = new HttpPost(url);

			if (null != parameters) {
				String params = EntityUtils.toString(new UrlEncodedFormEntity(parameters, "UTF-8"));
				postMethod.setURI(new URI(postMethod.getURI().toString() + "?" + params));
				System.out.println("httpPost-getUrl:" + postMethod.getURI());
			}

			if (headers != null && !headers.isEmpty()) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					postMethod.addHeader(entry.getKey(), entry.getValue());
				}
			}

			if (StringUtils.isNotBlank(requestBody)) {
				StringEntity se = new StringEntity(requestBody,  ContentType.create("text/json", "UTF-8"));
				postMethod.setEntity(se);
			}

			HttpResponse response = client.execute(postMethod);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode < 400) {
				HttpEntity he = response.getEntity();
				returnValue = new String(EntityUtils.toByteArray(he), "UTF-8");
				return returnValue;
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println(Thread.currentThread().getName() + "httpPost Send Error,Code error:" + e.getMessage());
		} catch (ClientProtocolException e) {
			System.out.println(Thread.currentThread().getName() + "httpPost Send Error,Protocol error:" + e.getMessage());
		} catch (IOException e) {
			System.out.println(Thread.currentThread().getName() + "httpPost Send Error,IO error:" + e.getMessage());
		} catch (URISyntaxException e) {
			System.out.println(Thread.currentThread().getName() + "httpPost Send Error,IO error:" + e.getMessage());
		} finally {// 释放连接,将连接放回到连接池
			postMethod.releaseConnection();
		}
		return returnValue;

	}

}
