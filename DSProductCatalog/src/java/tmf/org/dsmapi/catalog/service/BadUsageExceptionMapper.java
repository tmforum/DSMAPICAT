/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import tmf.org.dsmapi.catalog.JsonError;
import tmf.org.dsmapi.commons.exceptions.BadUsageException;

@Provider
public class BadUsageExceptionMapper implements ExceptionMapper<BadUsageException> {
    @Override
    public Response toResponse(BadUsageException ex) {
        JsonError error = new JsonError(ex.getType().getInfo(),ex.getMessage(), ex.getKeyValue());
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(error).build();
    }
}
