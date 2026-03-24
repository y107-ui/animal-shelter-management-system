package data;

import java.util.HashMap;
/**
 * Classe représentant une famille dans le système.
*/
public class Famille implements IData {
    private int id_famille;
    private String nom;
    private String adresse;
    private String telephone;
    private int capacite;
    
    private  transient String values;
    private  transient HashMap<String, fieldType> map;
    
    public Famille(int id_famille, String nom, String adresse, 
                   String telephone, int capacite) {
        this.id_famille = id_famille;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.capacite = capacite;
        getStruct();
    }
    
    @Override
    public void getStruct() {
        map = new HashMap<>();
        map.put("id_famille", fieldType.INT4);
        map.put("nom", fieldType.VARCHAR);
        map.put("adresse", fieldType.VARCHAR);
        map.put("telephone", fieldType.VARCHAR);
        map.put("capacite", fieldType.INT4);
        
        values = "(" + id_famille + ", '" + nom + "', '" + adresse + "', '" +
                 telephone + "', " + capacite + ")";
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
        return "Famille{id=" + id_famille + ", nom='" + nom + 
               "', capacite=" + capacite + "}";
    }
}
