package org.jboss.resteasy.clientexecutorstest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.client.core.executors.URLConnectionClientExecutor;

public class AnySiteClientFactory {

	private static final String TARGET = "http://www.redhat.com";
	private static AnySiteClientResource resource;
	private static AnySiteClientResource resourceWithPool;
	private static AnySiteClientResource resourceWithUrlConnection;

	@Path("/")
	public static interface AnySiteClientResource {
		@GET
		public String get();
	}

	public static AnySiteClientResource getResource() {
		if (resource == null) {
			resource = createProxy();
		}
		return resource;
	}
	
	public static AnySiteClientResource getResourceWithConnectionPool() {
		if (resourceWithPool == null) {
			resourceWithPool = createProxyWithConnectionPool();
		}
		return resourceWithPool;
	}
	
	public static AnySiteClientResource getResourceWithUrlConnectionExecutor() {
		if (resourceWithUrlConnection == null) {
			resourceWithUrlConnection = createProxyWithUrlConnectionExecutor();
		}
		return resourceWithUrlConnection;
	}
	
	// this should sometimes throw: Invalid use of BasicClientConnManager: connection still allocated
	private static AnySiteClientResource createProxy() {
		return ProxyFactory.create(AnySiteClientResource.class, TARGET);
	}
	
	// this has a pool for client connection - you may use a pool or force the connection release creating your own executor
	public static AnySiteClientResource createProxyWithUrlConnectionExecutor() {
		URLConnectionClientExecutor exe = new URLConnectionClientExecutor();
		return ProxyFactory.create(AnySiteClientResource.class, TARGET, exe);
	}
	
	// this has a pool for client connection - you may use a pool or force the connection release creating your own executor
	public static AnySiteClientResource createProxyWithConnectionPool() {
		ApacheHttpClient4Executor exe = createExecutorWithPool();
		return ProxyFactory.create(AnySiteClientResource.class, TARGET, exe);
	}

	private static ApacheHttpClient4Executor createExecutorWithPool() {
		PoolingHttpClientConnectionManager pccm = new PoolingHttpClientConnectionManager();
		pccm.setMaxTotal(100);
		// you can set the number of connections for a specific route, for example
		HttpHost localhost = new HttpHost("locahost", 8080);
		pccm.setMaxPerRoute(new HttpRoute(localhost), 50);
		CloseableHttpClient hc = HttpClients.custom().setConnectionManager(pccm).build();
		ApacheHttpClient4Executor exe = new ApacheHttpClient4Executor(hc);
		return exe;
	}

}
