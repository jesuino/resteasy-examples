package org.example;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("resource")
public class TheResource {
	
	
	static int status = 200;

	@GET
	public Response get() {
		System.out.println("[REST] Returning status code " + status);
		return Response.status(TheResource.status).build();
	}
	
	@POST
	@Path("{code}")
	public String setResponseCode(@PathParam("code") int status) {
		TheResource.status = status;
		System.out.println("[REST] Setting status to " + status);
		return "Status set to " + status;
	}
	
}
