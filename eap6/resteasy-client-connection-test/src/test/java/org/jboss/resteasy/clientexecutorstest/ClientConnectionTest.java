package org.jboss.resteasy.clientexecutorstest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * 
 * Tests connections to the server
 * @author wsiqueir
 *
 */
public class ClientConnectionTest {

	final CyclicBarrier gate = new CyclicBarrier(SIZE);
	// number of threads that will fire the request at the same time to the server 
	private static final int SIZE = 1;
	
	
	// TODO: Use parametrized test...
	public enum ExecutorsType {

		// not tested..
		URL_CONNECTION("http://localhost:8080/resteasy-client-connection-test/url-connection"), 
		// with connection pool on server side
		POOLED("http://localhost:8080/resteasy-client-connection-test/pooled"), 
		// the default one
		DEFAULT("http://localhost:8080/resteasy-client-connection-test");
		
		private String url;
		
		private ExecutorsType(String url) {
			this.url = url;
		}
	}
	// change the executor to see each behavior
	ExecutorsType executorType = ExecutorsType.URL_CONNECTION;

	@Test
	public void test() throws InterruptedException, BrokenBarrierException {
		IntStream.range(0, SIZE).mapToObj(i -> new Thread(this::invokeWs)).forEach(t -> t.start());
		System.out.println("---------- TESTS ----------");
		gate.await();
		// keep the thread alive for a while
		Thread.sleep(10000);
	}
	
	private void invokeWs() {
		try {
			gate.await();
			switch (executorType) {
			case DEFAULT:
				System.out.println("DEFAULT EXECUTOR (NOT USING POOL): " + accessSite(executorType.url));
				break;
			case POOLED:
				System.out.println("POOLED EXECUTOR: " + accessSite(executorType.url));
				break;
			case URL_CONNECTION:
				System.out.println("URL CONNECTION EXECUTOR: " + accessSite(executorType.url));
				break;
			default:
				break;
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private String accessSite(String url) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			InputStream is = conn.getInputStream();

			InputStreamReader in = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(in);
			// we just read the response but we don't really care about it
			br.lines().collect(Collectors.joining());
			in.close();
			br.close();
			is.close();
			return "OK";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR RETRIEVING CONTENT ";
		}
	}

}
