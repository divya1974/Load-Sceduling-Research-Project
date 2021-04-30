package org.app.peorig;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReaderDom {
	
	List<DataCenterDetails> getDataCenterDetailsXML(String filePath) {
	//		String filePath = "datacenters.xml";
	    File xmlFile = new File(filePath);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder;
	    List<DataCenterDetails> dcdList=null;
	    try {
	        dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(xmlFile);
	        doc.getDocumentElement().normalize();
	//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	        NodeList nodeList = doc.getElementsByTagName("DataCenterDetails");
	        //now XML is loaded as Document in memory, lets convert it to Object List
	        dcdList = new ArrayList<DataCenterDetails>();
	        for (int i = 0; i < nodeList.getLength(); i++) {
	        	dcdList.add(getDataCenterDetails(nodeList.item(i)));
	        }
	        //lets print Employee list information
	//            for (DataCenterDetails dcd : dcdList) {
	//                System.out.println(dcd.toString());
	//            }
	    } catch (SAXException | ParserConfigurationException | IOException e1) {
	        e1.printStackTrace();
	    }
	    return dcdList;
	}
	
	private static DataCenterDetails getDataCenterDetails(Node node) {
	    DataCenterDetails dcd = new DataCenterDetails();
	    if (node.getNodeType() == Node.ELEMENT_NODE) {
	        Element element = (Element) node;
	        dcd.setId(getTagValue("id", element));
	        dcd.setBroker(getTagValue("broker", element));
	        dcd.setCapacity(getItemDetails((Element)element.getElementsByTagName("load").item(0)));
	        dcd.setGreenEnergy(Double.parseDouble(getTagValue("greenEnergy", element)));
	            
	    }
        return dcd;
    }
	 
	private static double[] getItemDetails(Element ele) {
		double[] cap=new double[60];
		NodeList nodelist = ele.getElementsByTagName("item");
	//		Node node = nodelist.item(0);
	//		System.out.println("------------"+nodelist.getLength()+" "+node.getTextContent());
	//		System.out.println("------------"+nodelist.item(1).getNodeName());
		for(int i=0;i<nodelist.getLength();i++) {
			cap[i] = Double.parseDouble(nodelist.item(i).getTextContent());
		}
		
		return cap;
	}
	
	private static String getTagValue(String tag, Element element) {
	    NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
	    Node node = (Node) nodeList.item(0);
	    return node.getNodeValue();
	}

	void updateXML(int filenum,long thishr,String energyfilename,String loadfilename, String xmlFilePath) throws ParserConfigurationException, SAXException, IOException, TransformerException {
//		System.out.println(filenum+" " +thishr+" "+energyfilename+" "+loadfilename+" ");
		
	    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	    
	    Document doc = documentBuilder.parse(xmlFilePath);
//	    doc.getDocumentElement().normalize();
	    NodeList nodeList = doc.getElementsByTagName("DataCenterDetails");
	    
	    for (int i = 0; i < nodeList.getLength(); i++) {
	    	
	    	Node n = doc.getElementsByTagName("DataCenterDetails").item(i);
	    	DataCenterDetails dcd=getDataCenterDetails(n);
	    	
	    	if(dcd.getId().equals("DC"+filenum)) {
//	    		System.out.println(dcd.getId());
	    		NodeList nodes = n.getChildNodes();
	    		int itemno=0;
		    	for(int j=0;j<nodes.getLength();j++) {
		    		Node ele = nodes.item(j);
		    		if ("greenEnergy".equals(ele.getNodeName())) {
//		    			System.out.println(thishr);
		    			String line = Files.readAllLines(Paths.get(energyfilename)).get((int)(thishr-1));
		    			ele.setTextContent(String.valueOf(line));
//		    			System.out.println(i+" "+line);
		    		}
		    		if ("load".equals(ele.getNodeName())) {
		    			String line = Files.readAllLines(Paths.get(loadfilename)).get((int)(thishr-1));
		    			String linearr[]=line.split(" ");
		    			NodeList itemNodes = ele.getChildNodes();
		    			for(int k=0;k<itemNodes.getLength();k++) {
		    				if ("item".equals(itemNodes.item(k).getNodeName())) {
//		    					itemNodes.item(k).setTextContent("0.4"+filenum);
//		    					System.out.print(i+" "+linearr[itemno]+" ");
		    					itemNodes.item(k).setTextContent(linearr[itemno]);
		    					itemno++;
		    				}
		    			}
//		    			System.out.println();
		    		}
		    	}
	    	}
	    }
	    
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource domSource = new DOMSource(doc);
	    StreamResult streamResult = new StreamResult(new File(xmlFilePath));
	    transformer.transform(domSource, streamResult);
	}
}

//for (int i = 0; i < nodeList.getLength(); i++) {
//DataCenterDetails dcd=getDataCenterDetails(nodeList.item(i));
//if(dcd.getId().equals("DC"+filenum)) {
//	NodeList nodes = nodeList.item(i).getChildNodes();
//	Node n = nodes.item(i);
//	if (n.getNodeType() == Node.ELEMENT_NODE) {
//		Element element = (Element) n;
//		element.getElementsByTagName("greenEnergy").item(0);
//	}
////	for(int j=0;i<nodes.getLength();j++) {
////		System.out.println((nodes.item(j)));
////	}
////	Node element = nodeList.item(i);
////	element.setTextContent(dcd.getId());
//	System.out.println(dcd.getId());
//}
//}