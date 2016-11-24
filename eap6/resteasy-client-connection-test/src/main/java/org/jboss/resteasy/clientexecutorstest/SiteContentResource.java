package org.jboss.resteasy.clientexecutorstest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/")
public class SiteContentResource {

	@GET
	public String getSiteContent() {
		return AnySiteClientFactory.getResource().get();
	}
	
	@GET
	@Path("pooled")
	public String getSiteContentWithPooling() {
		return AnySiteClientFactory.getResourceWithConnectionPool().get();
	}
	
	@GET
	@Path("url-connection")
	public String getSiteContentWithUrlConnectionExecutor() {
		return AnySiteClientFactory.getResourceWithUrlConnectionExecutor().get();
	}
}
