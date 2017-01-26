/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import xmldimengenerator.Coeficient;
import Model.Dimen;
import Model.Item;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author polak
 */
public class DimenDAO {
    
    public static final String DIMEN_SIZE_NAME_LDPI = Coeficient.LDPI_MAP_NAME;
    public static final String DIMEN_SIZE_NAME_MDPI = Coeficient.MDPI_MAP_NAME;
    public static final String DIMEN_SIZE_NAME_HDPI = Coeficient.HDPI_MAP_NAME;
    public static final String DIMEN_SIZE_NAME_XHDPI = Coeficient.XHDPI_MAP_NAME;
    public static final String DIMEN_SIZE_NAME_XXHDPI = Coeficient.XXHDPI_MAP_NAME;
    public static final String DIMEN_SIZE_NAME_XXXHDPI = Coeficient.XXXHDPI_MAP_NAME;
    
    private static final String DIMEN_PATH_LDPI = "\\values-ldpi\\";
    private static final String DIMEN_PATH_MDPI = "\\values-mdpi\\";
    private static final String DIMEN_PATH_HDPI = "\\values-hdpi\\";
    private static final String DIMEN_PATH_XHDPI = "\\values-xhdpi\\";
    private static final String DIMEN_PATH_XXHDPI = "\\values-xxhdpi\\";
    private static final String DIMEN_PATH_XXXHDPI = "\\values-xxxhdpi\\";
    private static final String DIMEN_FOLDER_NAME = "\\Dimen-Generated\\";
    
    
    private File mFile;
    private File saveDirectory;
    private DocumentBuilder dBuilder;
    private Document doc;
    private List<Dimen> dimenList;
    
    public DimenDAO()
    {
        dimenList = new ArrayList<>();
    }
    
    public List<Dimen> getDimens(File dimenFile) throws ParserConfigurationException, SAXException, IOException
    {
        mFile = dimenFile;
        dBuilder = DocumentBuilderFactory.newInstance()
                             .newDocumentBuilder();

	doc = dBuilder.parse(mFile);
        
        if(doc.hasChildNodes())
        {
            doc.getXmlVersion();
            parseDimen();
        }

        return dimenList;
    }
    private List<Dimen> parseDimen()
    {
        NodeList dimens = doc.getElementsByTagName("dimen");
        for(int i=0 ; i< dimens.getLength() ; i++)
        {
            Dimen d = new Dimen();
            d.setDimenName(dimens.item(i).getAttributes().item(0).getNodeValue());
            d.setSize(dimens.item(i).getChildNodes().item(0).getNodeValue());

            dimenList.add(d);
        }
        return dimenList;
    }
    private List<Item> parseItem()
    {
        return null;
    }
    
    public void setSaveDirectory(File directory){saveDirectory=directory;}

    public void saveDimens(List<Dimen> dimenList, String dimenName) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, SecurityException
    {
        String filePath;
        if(saveDirectory!=null)
            filePath = saveDirectory.getAbsolutePath();
        else
            filePath = mFile.getAbsolutePath();
        
        filePath += DIMEN_FOLDER_NAME;
        File saveFolder = new File(filePath);
        if(!saveFolder.exists())
        {
            saveFolder.mkdir();
        }
        
        switch(dimenName)
        {
            case DIMEN_SIZE_NAME_LDPI : filePath+=DIMEN_PATH_LDPI;
                break ;
            case DIMEN_SIZE_NAME_MDPI : filePath+=DIMEN_PATH_MDPI;
                break;
            case DIMEN_SIZE_NAME_HDPI : filePath+=DIMEN_PATH_HDPI;
                break;
            case DIMEN_SIZE_NAME_XHDPI : filePath+=DIMEN_PATH_XHDPI;
                break;
            case DIMEN_SIZE_NAME_XXHDPI : filePath+=DIMEN_PATH_XXHDPI;
                break;
            case DIMEN_SIZE_NAME_XXXHDPI : filePath+=DIMEN_PATH_XXXHDPI;
                break;
            default : filePath+=dimenName;
                break;
        }
        File saveFile = new File(filePath);
        if(!saveFile.exists())
        {
            saveFile.mkdir();
        }
        filePath+="dimens.xml";
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Comment com = doc.createComment("This Dimens.xml was generated by XmlDimenGenerator");
        Element resources = doc.createElement("resources");
        doc.appendChild(resources);
        Node parent = resources.getParentNode();
                parent.insertBefore(com, resources);
        
        
        for(int i = 0; i < dimenList.size() ; i++)
        {
            Element dimen = doc.createElement("dimen");
            dimen.appendChild(doc.createTextNode(dimenList.get(i).getSize()));
            resources.appendChild(dimen);
            
            Attr name = doc.createAttribute("name");
            name.setValue(dimenList.get(i).getDimenName());
            dimen.setAttributeNode(name);
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	DOMSource source = new DOMSource(doc);
	StreamResult result = new StreamResult(new File(filePath));

		// Output to console for testing
         
         transformer.transform(source, result);
	System.out.println("File saved!");
    }
}
