/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmldimengenerator;

import Model.Dimen;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author polak
 */
public class Coeficient {
    

    
    public static final Float LDPI = 0.75f;
    public static final Float MDPI = 1f;
    public static final Float HDPI = 1.2f;
    public static final Float XHDPI = 1.8f;
    public static final Float XXHDPI = 2.4f;
    public static final Float XXXHDPI = 3.0f;
    
    public static final String LDPI_MAP_NAME = "LDPI";
    public static final String MDPI_MAP_NAME = "MDPI";
    public static final String HDPI_MAP_NAME = "HDPI";
    public static final String XHDPI_MAP_NAME = "XHDPI";
    public static final String XXHDPI_MAP_NAME = "XXHDPI";
    public static final String XXXHDPI_MAP_NAME = "XXXHDPI";
    
    private Float ldpi;
    private Float mdpi;
    private Float hdpi;
    private Float xhdpi;
    private Float xxhdpi;
    private Float xxxhdpi;
    
    private static final int mdpiSizeX = 320;
    private static final int mdpiSizeY = 480;
    
    HashMap<String, Float> coef;
    
    public Coeficient()
    {
        ldpi = LDPI;
        mdpi = MDPI;
        hdpi = HDPI;
        xhdpi = XHDPI;
        xxhdpi = XXHDPI;
        xxxhdpi = XXXHDPI;
        
        coef = new HashMap();
        coef.put(LDPI_MAP_NAME, LDPI);
        coef.put(MDPI_MAP_NAME, MDPI);
        coef.put(HDPI_MAP_NAME, HDPI);
        coef.put(XHDPI_MAP_NAME, XHDPI);
        coef.put(XXHDPI_MAP_NAME, XXHDPI);
        coef.put(XXXHDPI_MAP_NAME, XXXHDPI);
    }
    
    public HashMap getCoeficients()
    { return coef; }
    
    
    public void setCoeficient(String name, Float value)
    { coef.replace(name, value); }

    public void addCoeficient(String name, Float value)
    { coef.put(name, value); }
    
    public void addCoeficient(String name, int xSizeDp, int ySizeDp)
    {
        Float x = xSizeDp/mdpiSizeX*coef.get(MDPI_MAP_NAME);
        Float y = ySizeDp/mdpiSizeY*coef.get(MDPI_MAP_NAME);
        
    }
    
    public void removeCoeficien(String name)
    { coef.remove(name); }
    public void reset()
    {
        ldpi = LDPI;
        mdpi = MDPI;
        hdpi = HDPI;
        xhdpi = XHDPI;
        xxhdpi = XXHDPI;
        xxxhdpi = XXXHDPI;
        
        coef.replace(LDPI_MAP_NAME, LDPI);
        coef.replace(MDPI_MAP_NAME, MDPI);
        coef.replace(HDPI_MAP_NAME, HDPI);
        coef.replace(XHDPI_MAP_NAME, XHDPI);
        coef.replace(XXHDPI_MAP_NAME, XXHDPI);
        coef.replace(XXXHDPI_MAP_NAME, XXXHDPI);
    }
    
    public Map<String, List<Dimen>> generateDimens(String baseDimenName, List<Dimen> baseList)
    {
        HashMap<String, Float> bufferMap = new HashMap(coef);
        HashMap<String, List<Dimen>> map = new HashMap();
        map.put(baseDimenName, baseList);
        Float baseCoef=0f;
        if(coef.containsKey(baseDimenName))
            baseCoef = coef.get(baseDimenName);
        
        bufferMap.remove(baseDimenName);
        
        map.put(baseDimenName, baseList);
        
        for (Map.Entry<String, Float> s : bufferMap.entrySet())
        {
            String key = s.getKey();
            Float value = s.getValue();
            List<Dimen> buffer = new ArrayList();
            for(int i = 0; i< baseList.size() ; i++)
            {
                    Dimen dimenBuff = new Dimen();
                    dimenBuff.setDimenName(baseList.get(i).getDimenName());
                    dimenBuff.setDimenSize(baseList.get(i).getDimenSize()/baseCoef*value);
                    dimenBuff.setDimenUnit(baseList.get(i).getDimenUnit());
                    buffer.add(dimenBuff);
            }
            map.put(key, buffer);
        }
        return map;
    }
}
