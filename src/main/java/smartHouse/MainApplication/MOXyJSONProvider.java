package smartHouse.MainApplication;

import org.eclipse.persistence.jaxb.MarshallerProperties;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Patrik Glendell on 09/10/15.
 * This class is not used at the moment as we dont need controlled parsing at this time
 */


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MOXyJSONProvider implements MessageBodyReader<Object>,MessageBodyWriter<Object> {

    protected Providers providers;
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public Object readFrom(Class<Object> type, Type genericType,
                           Annotation[] annotations, MediaType mediaType,
                           MultivaluedMap<String, String> httpHeaders,
                           InputStream entityStream) throws IOException, WebApplicationException {
        try {
            Class<?> domainClass = getDomainClass(genericType);
            Unmarshaller u = getJAXBContext(domainClass, mediaType).createUnmarshaller();
            u.setProperty("eclipselink.media-type", mediaType.toString());
            u.setProperty("eclipselink.json.include-root", false);
            return u.unmarshal(new StreamSource(entityStream), domainClass).getValue();
        } catch (JAXBException e) {throw new WebApplicationException(e);}
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Object o, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {
        try {
            Class<?> domainClass = getDomainClass(genericType);
            Marshaller m = getJAXBContext(domainClass,mediaType).createMarshaller();
            m.setProperty("eclipselink.media-type", mediaType.toString());
            m.setProperty("eclipselink.json.include-root", false);
            m.setProperty("jaxb.formatted.output",true);
            m.setProperty(MarshallerProperties.INDENT_STRING,true);
            m.marshal(o, entityStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
    private Class<?> getDomainClass(Type genericType) {
        if(genericType instanceof Class) {
            return (Class<?>) genericType;
        }
        else if (genericType instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
        } else {return null;}
    }

    private JAXBContext getJAXBContext(Class<?> type, MediaType mediaType) throws JAXBException {
        ContextResolver<JAXBContext> resolver = providers.getContextResolver(JAXBContext.class,mediaType);
        JAXBContext jaxbContext;
        if(null == resolver || null == (jaxbContext = resolver.getContext(type))) {
            return JAXBContext.newInstance(type);
        } else {
            return jaxbContext;
        }




        }
}
