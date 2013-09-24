package tmf.org.dsmapi.tt.jaxrs.mapping;

import tmf.org.dsmapi.commons.utils.ReservedKeyword;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tmf.org.dsmapi.tt.model.TroubleTicket;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import tmf.org.dsmapi.commons.utils.ReservedKeyword;
import tmf.org.dsmapi.tt.model.TroubleTicketField;

@Provider
@Produces({"application/json"})
public class TroubleTicketWriter implements MessageBodyWriter<TroubleTicket> {

    @Context
    UriInfo info;

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return TroubleTicket.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(TroubleTicket t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(TroubleTicket tt, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        MultivaluedMap<String, String> map = info.getQueryParameters();

        List<String> fields = null;
        if (map.containsKey(ReservedKeyword.QUERY_KEY_FIELD.getText())) {
            fields = map.get(ReservedKeyword.QUERY_KEY_FIELD.getText());
        }
        if (map.containsKey(ReservedKeyword.QUERY_KEY_FIELD_2.getText())) {
            fields = map.get(ReservedKeyword.QUERY_KEY_FIELD_2.getText());
        }

        if (fields != null) {

            Set<TroubleTicketField> template = FieldSelection.getFields(fields);
            ObjectNode root = TroubleTicketJsonMaker.getView(tt, template);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(entityStream, root);

        } else {

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(entityStream, tt);

        }
    }
}