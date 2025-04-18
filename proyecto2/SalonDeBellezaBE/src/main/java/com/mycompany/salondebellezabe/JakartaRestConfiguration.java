package com.mycompany.salondebellezabe;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@ApplicationPath("api/v1")
public class JakartaRestConfiguration extends ResourceConfig {

    public JakartaRestConfiguration() {
        packages("com.mycompany.salondebellezabe.controllers").register(MultiPartFeature.class);
        packages("com.mycompany.salondebellezabe.excepciones");
    }
    
}
