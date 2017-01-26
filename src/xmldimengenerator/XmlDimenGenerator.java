/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmldimengenerator;

import Model.DAO.DimenDAO;
import Model.Dimen;
import Vue.mainView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author polak
 */
public class XmlDimenGenerator {
    
    private static File xmlDimensFile;
    private static DimenDAO dimenDao;
    private static List<Dimen> baseDimensList;
    private static Coeficient coef = new Coeficient();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        mainView mv = new mainView();
        baseDimensList = new ArrayList();
        dimenDao = new DimenDAO();
        mv.setVisible(true);
    }
    
    public static Map<String, List<Dimen>> generateDimens(String baseName, List<Dimen> list)
    {
        return coef.generateDimens(baseName, list);
    }
    public static Coeficient getCoeficient(){ return coef; }
    
    public static List<Dimen> openXmlFile(File file)
    {
        try {
            xmlDimensFile = file;
            baseDimensList = dimenDao.getDimens(xmlDimensFile);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmlDimenGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XmlDimenGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XmlDimenGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return baseDimensList;
    }
    public static void saveXmlFiles(File directory, Map<String,List<Dimen>> list)
    {
        dimenDao.setSaveDirectory(directory);
        for(String key : list.keySet())
        {
            try {
                dimenDao.saveDimens(list.get(key), key);
            } catch (ParserConfigurationException | TransformerException ex) {
                Logger.getLogger(XmlDimenGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
