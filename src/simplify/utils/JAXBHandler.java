package simplify.utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import simplify.utils.jaxb.analytics.ApplicationAnalytics;
import simplify.utils.jaxb.analytics.ObjectFactory;
import simplify.utils.jaxb.analytics.Year;


public class JAXBHandler {
	
	
	
	public static void main(String[] args) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ApplicationAnalytics.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ApplicationAnalytics analytics = new ApplicationAnalytics();
			ArrayList<Year> temp = (ArrayList<Year>) analytics.getYear();
			Year year = new Year();
			year.setId(2015);
			temp.add(year);
			System.out.println("Data Set");
			QName qName = new QName("simplify.utils.jaxb.analytics", "application");
	        JAXBElement<ApplicationAnalytics> root = new JAXBElement<ApplicationAnalytics>(qName, ApplicationAnalytics.class, analytics);
			marshaller.marshal(root, new File("/Simplify360/data/application-analytics.xml"));
			System.out.println("Write Complete");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
