package est.una.ac.cr.backendprogra.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
/**
 * Configuración para exponer servicios soap establece los endpoints, WSDL y esquemas XSD.
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {


    /**
     * Registra el servlet de despacho de mensajes soap que manejara las solicitudes entrantes en la ruta "/ws/*"
     *
     * @param applicationContext contexto de la aplicación para inyectar configuraciones
     * @return un ServletRegistrationBean configurado con el MessageDispatcherServlet
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }
    /**
     * Define el WSDL para el servicio soap relacionado con personas
     * Configura el puerto, la ubicación, el namespace y el esquema XSD
     *
     * @param personaSchema el esquema XSD que define la estructura de datos para personas
     * @return un DefaultWsdl11Definition que expone el WSDL para el servicio de personas
     */
    @Bean(name = "personas")
    public DefaultWsdl11Definition defaultWsdl11Definition1(XsdSchema personaSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PersonaPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://soapcrud.una.ac.cr/ws/persona");
        wsdl11Definition.setSchema(personaSchema);
        return wsdl11Definition;
    }

    /**
     * Define el WSDL para el servicio soap relacionado con oficinas
     * Configura el puerto, la ubicación, el namespace y el esquema XSD
     *
     * @param oficinaSchema el esquema XSD que define la estructura de datos para oficinas
     * @return un DefaultWsdl11Definition que expone el WSDL para el servicio de oficinas
     */
    @Bean(name = "oficinas")
    public DefaultWsdl11Definition defaultWsdl11Definition2(XsdSchema oficinaSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("OficinaPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://soapcrud.una.ac.cr/ws/oficina");
        wsdl11Definition.setSchema(oficinaSchema);
        return wsdl11Definition;
    }

    /**
     * Define el WSDL para el servicio soap relacionado con registros
     * Configura el puerto, la ubicación, el namespace y el esquema XSD
     *
     * @param registroSchema el esquema XSD que define la estructura de datos para oficinas
     * @return un DefaultWsdl11Definition que expone el WSDL para el servicio de registros
     */
    @Bean(name = "registros")
    public DefaultWsdl11Definition defaultWsdl11Definition3(XsdSchema registroSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("RegistroPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://soapcrud.una.ac.cr/ws/registro");
        wsdl11Definition.setSchema(registroSchema);
        return wsdl11Definition;
    }


    /**
     * Carga el esquema XSD para las personas desde el archivo "personas.xsd"
     * ubicado en el classpath.
     *
     * @return un XsdSchema que representa el esquema para personas.
     */
    @Bean
    public XsdSchema personaSchema() {
        return new SimpleXsdSchema(new org.springframework.core.io.ClassPathResource("personas.xsd"));
    }

    /**
     * Carga el esquema XSD para las oficinas desde el archivo "oficinas.xsd"
     * ubicado en el classpath.
     *
     * @return un XsdSchema que representa el esquema para oficinas.
     */
    @Bean
    public XsdSchema oficinaSchema() {
        return new SimpleXsdSchema(new org.springframework.core.io.ClassPathResource("oficinas.xsd"));
    }

    /**
     * Carga el esquema XSD para las registros desde el archivo "registros.xsd"
     * ubicado en el classpath.
     *
     * @return un XsdSchema que representa el esquema para registros.
     */
    @Bean
    public XsdSchema registroSchema() {
        return new SimpleXsdSchema(new org.springframework.core.io.ClassPathResource("registros.xsd"));
    }
}