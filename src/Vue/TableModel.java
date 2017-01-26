/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Model.Dimen;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
import xmldimengenerator.Coeficient;
import xmldimengenerator.XmlDimenGenerator;

/**
 *
 * @author polak
 */
public class TableModel extends AbstractTableModel{
    List<String> entetes = new ArrayList();
    List<List<Dimen>> dimensList;
            
    public TableModel(Map<String,List<Dimen>> list)
    {
        entetes.add("Name");
        dimensList = new ArrayList();
        for(String key : list.keySet())
        {
            entetes.add(key);
            dimensList.add(list.get(key));
        }
        
        if(dimensList.size()>=6)
        {
            Coeficient c = XmlDimenGenerator.getCoeficient();
            HashMap<String, Float> cMap = c.getCoeficients();
            for(int i = 0; i<dimensList.size();i++)
            {
                String key = entetes.get(i+1);
                switch(key)
                {
                    case Coeficient.LDPI_MAP_NAME :
                        Collections.swap(entetes, i+1, 1);
                        Collections.swap(dimensList, i, 0);
                        break;
                    case Coeficient.MDPI_MAP_NAME :
                        Collections.swap(entetes, i+1, 2);
                        Collections.swap(dimensList, i, 1);
                        break;
                    case Coeficient.HDPI_MAP_NAME :
                        Collections.swap(entetes, i+1, 3);
                        Collections.swap(dimensList, i, 2);
                        break;
                    case Coeficient.XHDPI_MAP_NAME :
                        Collections.swap(entetes, i+1, 4);
                        Collections.swap(dimensList, i, 3);
                        break;
                    case Coeficient.XXHDPI_MAP_NAME :
                        Collections.swap(entetes, i+1, 5);
                        Collections.swap(dimensList, i, 4);
                        break;
                    case Coeficient.XXXHDPI_MAP_NAME :
                        Collections.swap(entetes, i+1, 6);
                        Collections.swap(dimensList, i, 5);
                        break;
                    default :
                        break;
                }
            }
        }
    }
    @Override
    public int getRowCount() {
        return dimensList.get(0).size();
    }

    @Override
    public int getColumnCount() {
        return entetes.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex>0)
            return dimensList.get(columnIndex-1).get(rowIndex).getSize();
        else
            return dimensList.get(columnIndex).get(rowIndex).getDimenName();
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return entetes.get(columnIndex);
    }
    
}
