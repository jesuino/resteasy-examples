RESTEasy client connection test
--

When creating a resteasy client, you may have to configure the transport layer in order to set how it will create the actually http connection to the HTTP server.

RESTEasy has two main executors for use: Apache client based one and another one that uses HTTPURLConnection. 

This application shows how to use the apache client with a pooled client connection factory and the default one.

### Running

Build and deploy using `mvn clean package` and then run mvn test. You may also modify the test class to test other methods and more client threads accessing the server.

Check also the WAR URL and ajust the test accordingly. 
