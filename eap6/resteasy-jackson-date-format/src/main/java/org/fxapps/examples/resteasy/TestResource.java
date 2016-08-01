package org.fxapps.examples.resteasy;


import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fxapps.examples.resteasy.model.TestModel;

@Path("test-model")
public class TestResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TestModel objMethod() throws Exception {
		TestModel sm = new TestModel();
		sm.setDate(new Date());		
		return sm;
	}
}