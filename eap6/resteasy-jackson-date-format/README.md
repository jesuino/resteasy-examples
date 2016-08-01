# Change date format on JBoss EAP 6

This is example of how to change the date format of a JAX-RS application using a Jackson context resolver.

Notice also we must declare the dependency on the Jackson modules, so we add manifest entries (check [pom.xml](./pom.xml)).

### Testing

After build and deploy on JBoss EAP 6, access `http://localhost:8080/resteasy-jackson-date-format/rs/test-model` and you should see the date formatted.
