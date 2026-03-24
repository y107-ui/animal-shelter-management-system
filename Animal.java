package data;

import java.io.Serializable;
import java.util.HashMap;
import java.sql.Date;

/**
 * Classe représentant un animal dans le système.
 * Implémente l'interface IData pour la gestion de la structure des données
 * et l'interface Serializable pour permettre la sérialisation des objets.
 */
public class Animal implements IData, Serializable {
    private int id_animal;
    private String puce;
    private String nom;
    private String type;
    private String race;
    private Date date_naissance;
    private Date date_arrivee;
    private String statut;
    private boolean compatible_humains;
    private boolean compatible_chiens;
    private boolean compatible_chats;
    private boolean compatible_bebes;
    private String nourriture;
    
    private  transient String values;
    private  transient HashMap<String, fieldType> map;
    
    public Animal(int id_animal, String puce, String nom, String type, String race,
                  Date date_naissance, Date date_arrivee, String statut,
                  boolean compatible_humains, boolean compatible_chiens,
                  boolean compatible_chats, boolean compatible_bebes, String nourriture) {
        this.id_animal = id_animal;
        this.puce = puce;
        this.nom = nom;
        this.type = type;
        this.race = race;
        this.date_naissance = date_naissance;
        this.date_arrivee = date_arrivee;
        this.statut = statut;
        this.compatible_humains = compatible_humains;
        this.compatible_chiens = compatible_chiens;
        this.compatible_chats = compatible_chats;
        this.compatible_bebes = compatible_bebes;
        this.nourriture = nourriture;
        getStruct();
    }
    /**
     * Initialise la structure de données avec les champs de l'animal
     * et leurs types correspondants, et prépare la chaîne de valeurs
     * pour l'insertion en base de données.
     */
    @Override
    public void getStruct() {
        map = new HashMap<>();
        map.put("id_animal", fieldType.INT4);
        map.put("puce", fieldType.VARCHAR);
        map.put("nom", fieldType.VARCHAR);
        map.put("type", fieldType.VARCHAR);
        map.put("race", fieldType.VARCHAR);
        map.put("date_naissance", fieldType.VARCHAR); // DATE
        map.put("date_arrivee", fieldType.VARCHAR); // DATE
        map.put("statut", fieldType.VARCHAR);
        map.put("compatible_humains", fieldType.NUMERIC); // BOOLEAN
        map.put("compatible_chiens", fieldType.NUMERIC);
        map.put("compatible_chats", fieldType.NUMERIC);
        map.put("compatible_bebes", fieldType.NUMERIC);
        map.put("nourriture", fieldType.VARCHAR);
        
        values = "(" + id_animal + ", '" + puce + "', '" + nom + "', '" + type + "', '" + race + "', '" +
                 date_naissance + "', '" + date_arrivee + "', '" + statut + "', " +
                 (compatible_humains ? "TRUE" : "FALSE") + ", " +
                 (compatible_chiens ? "TRUE" : "FALSE") + ", " +
                 (compatible_chats ? "TRUE" : "FALSE") + ", " +
                 (compatible_bebes ? "TRUE" : "FALSE") + ", '" + nourriture + "')";
    }
    
    @Override
    public String getValues() {
        return values;
    }
    
    @Override
    public HashMap<String, fieldType> getMap() {
        return map;
    }
    
    @Override
    public boolean check(HashMap<String, fieldType> tableStruct) {
        if (tableStruct.size() != this.map.size()) {
            return false;
        }
        for (String key : this.map.keySet()) {
            if (!tableStruct.containsKey(key)) {
                return false;
            }
            if (tableStruct.get(key) != this.map.get(key)) {
                return false;
            }
        }
        return true;
    }
    
    // Getters/Setters
    public int getId_animal() { return id_animal; }
    public void setId_animal(int id_animal) { this.id_animal = id_animal; }
    public String getPuce() { return puce; }
    public void setPuce(String puce) { this.puce = puce; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }
    
    @Override
    public String toString() {
        return "Animal{id=" + id_animal + ", nom='" + nom + "', type='" + type + 
               "', race='" + race + "', statut='" + statut + "'}";
    }
}



