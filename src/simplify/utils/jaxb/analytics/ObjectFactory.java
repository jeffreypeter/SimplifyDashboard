//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.05 at 09:14:39 PM IST 
//


package simplify.utils.jaxb.analytics;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the simplify.utils.jaxb.analytics package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Application_QNAME = new QName("http://www.SimplifyDashboard.com/ApplicationAnalytics", "application");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: simplify.utils.jaxb.analytics
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ApplicationAnalytics }
     * 
     */
    public ApplicationAnalytics createApplicationAnalytics() {
        return new ApplicationAnalytics();
    }

    /**
     * Create an instance of {@link Year }
     * 
     */
    public Year createYear() {
        return new Year();
    }

    /**
     * Create an instance of {@link Time }
     * 
     */
    public Time createTime() {
        return new Time();
    }

    /**
     * Create an instance of {@link Day }
     * 
     */
    public Day createDay() {
        return new Day();
    }

    /**
     * Create an instance of {@link Month }
     * 
     */
    public Month createMonth() {
        return new Month();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationAnalytics }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.SimplifyDashboard.com/ApplicationAnalytics", name = "application")
    public JAXBElement<ApplicationAnalytics> createApplication(ApplicationAnalytics value) {
        return new JAXBElement<ApplicationAnalytics>(_Application_QNAME, ApplicationAnalytics.class, null, value);
    }

}
