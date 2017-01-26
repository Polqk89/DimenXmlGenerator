package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author polak
 */
public class Dimen {
    
    private String mDimenName;
    private Float mDimenSize;
    private String mDimenUnit;
    
    public String toString()
    {
        return mDimenName + " : "+mDimenSize+mDimenUnit;
    }
    public void setDimenName(String name){ mDimenName = name; }
    public String getDimenName(){ return mDimenName; }
    
    public Float getDimenSize(){ return mDimenSize; }
    public void setDimenSize(Float size){ mDimenSize = size; } 
    
    public String getSize(){ 
        String retour;
        if((int)mDimenSize.floatValue() == mDimenSize)
            retour = String.format("%.0f",mDimenSize)+mDimenUnit;
        else
            retour = String.format("%.2f",mDimenSize)+mDimenUnit;
        
        retour = retour.replace(',', '.');

        return retour;
    }
    public void setSize(String size)
    {
        String number = size;
        String unit = size;
        number = number.replaceAll("[^0-9]", "");
        unit = unit.replaceAll("[^A-Za-z]", "");
        try
        {
        mDimenSize = Float.parseFloat(number);
        mDimenUnit = unit;
        }catch(NumberFormatException e){}
    }
    
    public void setDimenUnit(String unit){ mDimenUnit = unit; }
    public String getDimenUnit(){ return mDimenUnit; }
}
