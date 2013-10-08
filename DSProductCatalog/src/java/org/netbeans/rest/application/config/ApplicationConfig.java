/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;
import tmf.org.dsmapi.catalog.service.BadUsageExceptionMapper;
import tmf.org.dsmapi.catalog.service.JacksonConfigurator;

/**
 *
 * @author pierregauthier
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return getRestResourceClasses();
    }

    /**
     * Do not modify this method. It is automatically generated by NetBeans REST support.
     */
    private Set<Class<?>> getRestResourceClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        resources.add(tmf.org.dsmapi.catalog.service.ProductSpecificationFacadeREST.class);
        resources.add(tmf.org.dsmapi.catalog.service.ProductCategoryFacadeREST.class);
        resources.add(tmf.org.dsmapi.catalog.service.ProductOfferingFacadeREST.class);
        resources.add(BadUsageExceptionMapper.class);
        resources.add(JacksonConfigurator.class);
        try {
            Class<?> jacksonProvider = Class.forName("org.codehaus.jackson.jaxrs.JacksonJsonProvider");
            resources.add(jacksonProvider);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return resources;
    }
    
}