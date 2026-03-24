package data;

import java.io.Serializable;
import java.util.HashMap;
/**
* Classe représentant une box dans le système.
 */
public class Box implements IData, Serializable {
    private int id_box;
    private int num;
    private String type_autorise;
    private int capacite_max;
    
    private  transient String values;
    private  transient  HashMap<String, fieldType> map;
    
    public Box(int id_box, int num, String type_autorise, int capacite_max) {
        this.id_box = id_box;
        this.num = num;
        this.type_autorise = type_autorise;
        this.capacite_max = capacite_max;
        getStruct();
    }
    
    @Override
    public void getStruct() {
        map = new HashMap<>();
        map.put("id_box", fieldType.INT4);
        map.put("num", fieldType.INT4);
        map.put("type_autorise", fieldType.VARCHAR);
        map.put("capacite_max", fieldType.INT4);
        
        values = "(" + id_box + ", " + num + ", '" + type_autorise + "', " +
                 capacite_max + ")";
    }
    
    @Override
    public String getValues() { return values; }
    
    @Override
    public HashMap<String, fieldType> getMap() { return map; }
    
    @Override
    public boolean check(HashMap<String, fieldType> tableStruct) {
        if (tableStruct.size() != this.map.size()) return false;
        for (String key : this.map.keySet()) {
            if (!tableStruct.containsKey(key) || tableStruct.get(key) != this.map.get(key)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Box{id=" + id_box + ", num=" + num + 
               ", type='" + type_autorise + "', capacite=" + capacite_max + "}";
    }
}