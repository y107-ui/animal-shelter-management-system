package data;

import java.io.Serializable;
import java.util.HashMap;
/**
* Classe représentant une box dans le système.
 */
public class Benevole implements IData, Serializable {
    private int id_benevole;
    private String nom;
    private String prenom;
    private String telephone;
    private String role;
    
    private   transient String values;
    private   transient HashMap<String, fieldType> map;
    
    public Benevole(int id_benevole, String nom, String prenom, 
                    String telephone, String role) {
        this.id_benevole = id_benevole;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.role = role;
        getStruct();
    }
    
    @Override
    public void getStruct() {
        map = new HashMap<>();
        map.put("id_benevole", fieldType.INT4);
        map.put("nom", fieldType.VARCHAR);
        map.put("prenom", fieldType.VARCHAR);
        map.put("telephone", fieldType.VARCHAR);
        map.put("role", fieldType.VARCHAR);
        
        values = "(" + id_benevole + ", '" + nom + "', '" + prenom + "', '" +
                 telephone + "', '" + role + "')";
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
        return "Benevole{id=" + id_benevole + ", nom='" + nom + " " + prenom + 
               "', role='" + role + "'}";
    }
}