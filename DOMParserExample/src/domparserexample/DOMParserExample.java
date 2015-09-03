/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domparserexample;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author davidmunro
 */
public class DOMParserExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            File file = new File("C:\\xampp\\htdocs\\DOMParser.xml");

            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(file);
            
            System.out.println("Root element is : " +doc.getDocumentElement().getNodeName());
            
            if(doc.hasChildNodes()){
                printNode(doc.getChildNodes());
            }
            
            
        } catch (SAXException ex) {
            Logger.getLogger(DOMParserExample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DOMParserExample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DOMParserExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void printNode(NodeList list){
        for(int i = 0; i < list.getLength(); i++){
            Node temporaryNode = list.item(i);
            
            if(temporaryNode.getNodeType() == Node.ELEMENT_NODE){
                System.out.println("Node name -----" +temporaryNode.getNodeName());
                System.out.println("Node value ...." +temporaryNode.getTextContent());
                
                if(temporaryNode.hasAttributes()){
                    
                    NamedNodeMap nodeMap = temporaryNode.getAttributes();
                    
                    for(int in = 0; in < nodeMap.getLength(); in++){
                        Node node = nodeMap.item(in);
                        System.out.println("attr value "+node.getNodeValue());
                        System.out.println("attr valueee ......" +node.getNodeValue());
                    }
                }
                if(temporaryNode.hasChildNodes()){
                    printNode(temporaryNode.getChildNodes());
                }
            }
        }
    }
    
}
