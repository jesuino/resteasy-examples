package org.jboss.jaxrs.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class ResourceInjectionTest {

	@Resource(mappedName = "java:jboss/datasources/ExampleDS")
	DataSource ds;

	@GET
	public String testDatasource() throws SQLException, NamingException {
		Connection con = ds.getConnection();
		PreparedStatement prepareStatement = con
				.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.USERS");
		ResultSet resultSet = prepareStatement.executeQuery();
		String out = "<h2>Users in H2 Database</h2><ul>";
		while (resultSet.next()) {
			String name = resultSet.getString("NAME");
			boolean admin = resultSet.getBoolean("ADMIN");
			out += "<li> " + name;
			if (admin) {
				out += "(admin)";
			}
			out += "</li>";
		}
		out += "</ul>";
		prepareStatement.close();
		con.close();
		return out;

	}

}
