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
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
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
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Lookup;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.auth.KerberosSchemeFactory;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;


/**
 * 异步的HTTP请求对象，可设置代理
 */
public class HttpClientAsync {

	// region 私有属性定义
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

	// 异步httpclient
	private CloseableHttpAsyncClient asyncHttpClient;

	// 异步加代理的httpclient
	private CloseableHttpAsyncClient proxyAsyncHttpClient;

	// end
	
	// region 构造函数

	/*
	 * 构造函数-不使用代理，使用默认连接参数
	 */
	public HttpClientAsync() {
		try {
			this.asyncHttpClient = createAsyncClient(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 构造函数-使用代理，使用默认连接参数
	 */
	public HttpClientAsync(String host, int port, String username, String password) {
		this.proxyHost = host;
		this.proxyPort = port;
		this.proxyUsername = username;
		this.proxyPassword = password;

		try {
			this.proxyAsyncHttpClient = createAsyncClient(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 构造函数-不使用代理，指定连接配置
	 */
	public HttpClientAsync(int socketTimeout, int connectTimeout, int connectionRequestTimeout, int poolSize,
			int maxPerRoute) {
		this.socketTimeout = socketTimeout;
		this.connectTimeout = connectTimeout;
		this.poolSize = poolSize;
		this.maxPerRoute = maxPerRoute;
		this.connectionRequestTimeout = connectionRequestTimeout;

		try {
			this.asyncHttpClient = createAsyncClient(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 构造函数-使用代理，指定连接配置
	 */
	public HttpClientAsync(int socketTimeout, int connectTimeout, int connectionRequestTimeout, int poolSize,
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
			this.proxyAsyncHttpClient = createAsyncClient(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// end

	// region 连接对象操作
	/*
	 * 创建异步连接对象
	 */
	private CloseableHttpAsyncClient createAsyncClient(Boolean proxy)
			throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
			MalformedChallengeException, IOReactorException {

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();

		SSLContext sslcontext = SSLContexts.createDefault();

		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(proxyUsername, proxyPassword);

		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, credentials);

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder.<SchemeIOSessionStrategy>create()
				.register("http", NoopIOSessionStrategy.INSTANCE)
				.register("https", new SSLIOSessionStrategy(sslcontext)).build();

		// 配置io线程
		IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
				.setIoThreadCount(Runtime.getRuntime().availableProcessors()).build();
		// 设置连接池大小
		ConnectingIOReactor ioReactor;
		ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
		PoolingNHttpClientConnectionManager conMgr = new PoolingNHttpClientConnectionManager(ioReactor, null,
				sessionStrategyRegistry, null);

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
			return HttpAsyncClients.custom().setConnectionManager(conMgr)
					.setDefaultCredentialsProvider(credentialsProvider).setDefaultAuthSchemeRegistry(authSchemeRegistry)
					.setProxy(new HttpHost(proxyHost, proxyPort)).setDefaultCookieStore(new BasicCookieStore())
					.setDefaultRequestConfig(requestConfig).build();
		} else {
			return HttpAsyncClients.custom().setConnectionManager(conMgr)
					.setDefaultCredentialsProvider(credentialsProvider).setDefaultAuthSchemeRegistry(authSchemeRegistry)
					.setDefaultCookieStore(new BasicCookieStore()).build();
		}

	}

	private CloseableHttpAsyncClient getAsyncHttpClient() {
		return asyncHttpClient;
	}

	private CloseableHttpAsyncClient getProxyAsyncHttpClient() {
		return proxyAsyncHttpClient;
	}

	// end
	
	// region 公共接口和方法
	
	// region 公共接口
	/*
	 * 外部回调接口定义-成功
	 */
	public interface AsyncResponseCompletedCallback {
		public void handle(HttpResponse response);
	}
	/*
	 * 外部回调接口定义-失败
	 */
	public interface AsyncResponseFailedCallback {
		public void handle(Exception e);
	}
	/*
	 * 外部回调接口定义-取消
	 */
	public interface AsyncResponseCancelledCallback {
		public void handle();
	}
	
	// end
	
	// 公共方法
	/*
	 * 异步 Get
	 */
	public void httpAsyncGet(String url, Map<String, String> headers, List<BasicNameValuePair> parameters,
			Boolean proxy, AsyncResponseCompletedCallback completedCallback, AsyncResponseFailedCallback failedCallback,
			AsyncResponseCancelledCallback cancelledCallback) {
		CloseableHttpAsyncClient client;
		if (proxy)
			client = getProxyAsyncHttpClient();
		else
			client = getAsyncHttpClient();

		client.start();
		final HttpGet getMethod = new HttpGet(url);
		final CountDownLatch latch = new CountDownLatch(1);

		try {

			System.out.println(" caller thread id is : " + Thread.currentThread().getId());

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
			
			client.execute(getMethod, new FutureCallback<HttpResponse>() {
				//成功的回调
				public void completed(final HttpResponse response) {
					latch.countDown();
					System.out.println("completed callback thread id is : " + Thread.currentThread().getId());
					//System.out.println(getMethod.getRequestLine() + "->" + response.getStatusLine());
					
					try {
						String content = EntityUtils.toString(response.getEntity(), "UTF-8");
						System.out.println(" response content is : " + content);
						//外部操作
						if(completedCallback != null) completedCallback.handle(response);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//失败的回调
				public void failed(final Exception ex) {
					latch.countDown();
					//System.out.println(getMethod.getRequestLine() + "->" + ex);
					System.out.println("failed callback thread id is : " + Thread.currentThread().getId());
					//外部操作
					if(failedCallback != null) failedCallback.handle(ex);
				}
				//取消的回调
				public void cancelled() {
					latch.countDown();
					//System.out.println(getMethod.getRequestLine() + " cancelled");
					System.out.println("cancelled callback thread id is : " + Thread.currentThread().getId());
					//外部操作
					if(cancelledCallback != null) cancelledCallback.handle();
				}

			});

			latch.await();
			client.close();
		} catch (UnsupportedEncodingException e) {
			System.out.println(Thread.currentThread().getName() + "httpGet Send Error,Code error:" + e.getMessage());
		} catch (ClientProtocolException e) {
			System.out
					.println(Thread.currentThread().getName() + "httpGet Send Error,Protocol error:" + e.getMessage());
		} catch (IOException e) {
			System.out.println(Thread.currentThread().getName() + "httpGet Send Error,IO error:" + e.getMessage());
		} catch (URISyntaxException e) {
			System.out.println(Thread.currentThread().getName() + "httpGet Send Error,IO error:" + e.getMessage());
		} catch (InterruptedException e){
			System.out.println(Thread.currentThread().getName() + "httpGet Send Error,IO error:" + e.getMessage());
		}finally {		
			// 释放连接,将连接放回到连接池
			getMethod.releaseConnection();
		}
	}

	/*
	 * 异步 Post
	 */
	public void httpAsyncPost(String url, Map<String, String> headers, List<BasicNameValuePair> parameters,
			Boolean proxy, AsyncResponseCompletedCallback completedCallback, AsyncResponseFailedCallback failedCallback,
			AsyncResponseCancelledCallback cancelledCallback) {
		CloseableHttpAsyncClient client;
		if (proxy)
			client = getProxyAsyncHttpClient();
		else
			client = getAsyncHttpClient();

		client.start();
		final HttpPost postMethod = new HttpPost(url);
		final CountDownLatch latch = new CountDownLatch(1);

		try {

			System.out.println(" caller thread id is : " + Thread.currentThread().getId());

			if (null != parameters) {
				String params = EntityUtils.toString(new UrlEncodedFormEntity(parameters, "UTF-8"));
				postMethod.setURI(new URI(postMethod.getURI().toString() + "?" + params));
				System.out.println("httpPost-postUrl: " + postMethod.getURI());
			}

			if (headers != null && !headers.isEmpty()) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					postMethod.addHeader(entry.getKey(), entry.getValue());
				}
			}
			
			client.execute(postMethod, new FutureCallback<HttpResponse>() {
				//成功的回调
				public void completed(final HttpResponse response) {
					latch.countDown();
					System.out.println("completed callback thread id is : " + Thread.currentThread().getId());
					System.out.println(postMethod.getRequestLine() + "->" + response.getStatusLine());
					
					try {
						String content = EntityUtils.toString(response.getEntity(), "UTF-8");
						System.out.println(" response content is : " + content);
						//外部操作
						if(completedCallback != null) completedCallback.handle(response);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//失败的回调
				public void failed(final Exception ex) {
					latch.countDown();
					//System.out.println(getMethod.getRequestLine() + "->" + ex);
					System.out.println("failed callback thread id is : " + Thread.currentThread().getId());
					//外部操作
					if(failedCallback != null) failedCallback.handle(ex);
				}
				//取消的回调
				public void cancelled() {
					latch.countDown();
					//System.out.println(getMethod.getRequestLine() + " cancelled");
					System.out.println("cancelled callback thread id is : " + Thread.currentThread().getId());
					//外部操作
					if(cancelledCallback != null) cancelledCallback.handle();
				}

			});

			latch.await();
			client.close();
		} catch (UnsupportedEncodingException e) {
			System.out.println(Thread.currentThread().getName() + "httpPost Send Error,Code error:" + e.getMessage());
		} catch (ClientProtocolException e) {
			System.out
					.println(Thread.currentThread().getName() + "httpPost Send Error,Protocol error:" + e.getMessage());
		} catch (IOException e) {
			System.out.println(Thread.currentThread().getName() + "httpPost Send Error,IO error:" + e.getMessage());
		} catch (URISyntaxException e) {
			System.out.println(Thread.currentThread().getName() + "httpPost Send Error,IO error:" + e.getMessage());
		} catch (InterruptedException e){
			System.out.println(Thread.currentThread().getName() + "httpPost Send Error,IO error:" + e.getMessage());
		}finally {		
			// 释放连接,将连接放回到连接池
			postMethod.releaseConnection();
		}
	}
	// end
	
	// end
}