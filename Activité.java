package data;
/**
*Classe représentant une activité dans le système.
 */
import java.io.Serializable;
import java.util.HashMap;

class Activite implements IData, Serializable {
    private int id_activite;
    private String type_activite;
    private String description;
    private int nombre_min_animaux;
    
    private String values;
    private HashMap<String, fieldType> map;
    
    public Activite(int id_activite, String type_activite, 
                    String description, int nombre_min_animaux) {
        this.id_activite = id_activite;
        this.type_activite = type_activite;
        this.description = description;
        this.nombre_min_animaux = nombre_min_animaux;
        getStruct();
    }
    
    @Override
    public void getStruct() {
        map = new HashMap<>();
        map.put("id_activite", fieldType.INT4);
        map.put("type_activite", fieldType.VARCHAR);
        map.put("description", fieldType.VARCHAR);
        map.put("nombre_min_animaux", fieldType.INT4);
        
        values = "(" + id_activite + ", '" + type_activite + "', '" + 
                 description + "', " + nombre_min_animaux + ")";
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
        return "Activite{id=" + id_activite + ", type='" + type_activite + 
               "', min_animaux=" + nombre_min_animaux + "}";
    }
}