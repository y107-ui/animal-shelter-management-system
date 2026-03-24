package data;

import java.io.Serializable;
import java.util.HashMap;
import java.sql.Date;
/**
 * Classe représentant une box dans le système.
 */
public class Soin implements IData, Serializable {
    private int id_soin;
    private String libelle;
    private String description;
    private int duree_minute;
    private String veterinaire;
    
    private  transient String values;
    private  transient HashMap<String, fieldType> map;
    
    public Soin(int id_soin, String libelle, String description, 
                int duree_minute, String veterinaire) {
        this.id_soin = id_soin;
        this.libelle = libelle;
        this.description = description;
        this.duree_minute = duree_minute;
        this.veterinaire = veterinaire;
        getStruct();
    }
    
    @Override
    public void getStruct() {
        map = new HashMap<>();
        map.put("id_soin", fieldType.INT4);
        map.put("libelle", fieldType.VARCHAR);
        map.put("description", fieldType.VARCHAR);
        map.put("duree_minute", fieldType.INT4);
        map.put("veterinaire", fieldType.VARCHAR);
        
        values = "(" + id_soin + ", '" + libelle + "', '" + description + "', " +
                 duree_minute + ", '" + veterinaire + "')";
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
        return "Soin{id=" + id_soin + ", libelle='" + libelle + 
               "', duree=" + duree_minute + "min, veterinaire='" + veterinaire + "'}";
    }
}