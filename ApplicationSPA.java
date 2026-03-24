
package application;

import connexion.Connexion;
import gestion.GestionBDD;
import data.*;
import Serial.MemoireSPA;
import exceptions.*;  // ← Ajout de l'import

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.util.Map;

/**
 * Application de gestion d'un refuge SPA (ligne de commande).
 */
public class ApplicationSPA {
	/**
	 * AFFichage de menu principale de l'appilcation
	 */
    private static void afficherMenu() {
        System.out.println(" HELP | EXIT");
        System.out.println(" SAVE| LOAD");
        System.out.println(" AFFICHER <table> | STRUCT <table>");
        System.out.println(" REMOVE <table> <id>");
        System.out.println("-------------------------------------------------");
        System.out.println(" [DEMONSTRATION JDBC]");
        System.out.println(" CREER_TABLE_TEST | DROP_TABLE_TEST");
        System.out.println("-------------------------------------------------");
        System.out.println(" [ANIMAUX]");
        System.out.println(" AJOUTER_ANIMAL | RECHERCHER_ANIMAL");
        System.out.println(" AJOUTER_BOX | AFFECTER_BOX | AJOUTER_SOIN | AFFECTER_SOIN" );
        System.out.println("-------------------------------------------------");
        System.out.println(" [FAMILLES]");
        System.out.println(" AJOUTER_FAMILLE | PLACER_FAMILLE ");
        System.out.println("-------------------------------------------------");
        System.out.println(" [BENEVOLES]");
        System.out.println(" AJOUTER_BENEVOLE");
        System.out.println("-------------------------------------------------");
        System.out.println(" [PLANNING]");
        System.out.println(" AJOUTER_CRENEAU | AJOUTER_ACTIVITE");
        System.out.println(" INSCRIRE_PLANNING | MODIFIER_PLANNING | SUPPRIMER_PLANNING");
        System.out.println(" PLANNING_JOUR | PLANNING_BENEVOLE");
        System.out.println("-------------------------------------------------=");
        System.out.println("Formats : Date = YYYY-MM-DD | Heure = HH:MM");
        System.out.println("-------------------------------------------------");
       
    }
    /**
     *memoire local pour la serialisation et la sauvgarde 
     */
    private static ArrayList<Animal> animauxMemo = new ArrayList<>();
    private static ArrayList<Famille> famillesMemo = new ArrayList<>();
    private static ArrayList<Box> boxsMemo = new ArrayList<>();
    private static ArrayList<Soin> soinsMemo = new ArrayList<>();
   
    public static void main(String[] args) {

        //  Gestion de ConnexionException
        Connection cnx = null;
        try {
            cnx = Connexion.connect();
        } catch (ConnexionException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
            return;
        }
        
        if (cnx == null) {
            System.out.println("Connexion impossible.");
            return;
        }

        try {
            GestionBDD g = new GestionBDD(cnx);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            afficherMenu();

            while (true) {
                System.out.print("> ");
                String line = br.readLine();
                if (line == null) break;

                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                String cmd = parts[0].toUpperCase();

                if (cmd.equals("EXIT")) break;

                //  Gestion des exceptions par commande
                try {
                    switch (cmd) {

                        /** Affiche le menu de commande */
                        case "HELP":
                            afficherMenu();
                            break;

                        /** AFFICHAGE d'une table */
                        case "AFFICHER":
                            if (parts.length != 2) {
                                System.out.println("Usage: AFFICHER <table>");
                                break;
                            }
                            g.displayTable(parts[1].toLowerCase());
                            break;
                            /** Affiche la structure d'une table */
                        case "STRUCT":
                            if (parts.length != 2) {
                                System.out.println("Usage: STRUCT <table>");
                                break;
                            }
                            g.structTable(parts[1].toLowerCase(), true);
                            break;

                        /** REMOVE */
                        case "REMOVE": {
                            if (parts.length != 3) {
                                System.out.println("Usage: REMOVE <table> <id>");
                                break;
                            }

                            String table = parts[1].toLowerCase();
                            int id = Integer.parseInt(parts[2]);

                            // récupération de la structure pour trouver la colonne id
                            Map<String, ?> struct = g.structTable(table, false);

                            String idCol = null;
                            for (String col : struct.keySet()) {
                                if (col.equalsIgnoreCase("id") || col.toLowerCase().startsWith("id")) {
                                    idCol = col;
                                    break;
                                }
                            }

                            if (idCol == null) {
                                System.out.println("Impossible de trouver une colonne id pour la table " + table);
                                break;
                            }

                            String sql = "DELETE FROM " + table + " WHERE " + idCol + " = " + id;
                            g.execute(sql);

                            System.out.println("OK: ligne supprimée dans " + table);
                            break;
                        }

                        /** CREATE la table TEST */
                        case "CREER_TABLE_TEST": {
                            String sql =
                                    "CREATE TABLE IF NOT EXISTS test (" +
                                    "id_test INTEGER PRIMARY KEY, " +
                                    "nom VARCHAR(255), " +
                                    "date_naissance DATE" +
                                    ")";
                            g.execute(sql);
                            System.out.println("OK: table TEST créée.");
                            break;
                        }

                        /** DROP la table TEST */
                        case "DROP_TABLE_TEST":
                            g.execute("DROP TABLE IF EXISTS test");
                            System.out.println("OK: table TEST supprimée.");
                            break;

                        /** Ajoute un Animal dans la table ANIMAL */
                        case "AJOUTER_ANIMAL": {
                            System.out.print("id_animal: ");
                            int id = Integer.parseInt(br.readLine());

                            System.out.print("puce: ");
                            String puce = br.readLine();

                            System.out.print("nom: ");
                            String nom = br.readLine();

                            System.out.print("type: ");
                            String type = br.readLine();

                            System.out.print("race: ");
                            String race = br.readLine();

                            System.out.print("date_naissance (YYYY-MM-DD): ");
                            Date dn = Date.valueOf(br.readLine());

                            System.out.print("date_arrivee (YYYY-MM-DD): ");
                            Date da = Date.valueOf(br.readLine());

                            System.out.print("statut: ");
                            String statut = br.readLine();

                            System.out.print("compatible_humains (true/false): ");
                            boolean ch = Boolean.parseBoolean(br.readLine());

                            System.out.print("compatible_chiens (true/false): ");
                            boolean cchiens = Boolean.parseBoolean(br.readLine());

                            System.out.print("compatible_chats (true/false): ");
                            boolean cchats = Boolean.parseBoolean(br.readLine());

                            System.out.print("compatible_bebes (true/false): ");
                            boolean cbebes = Boolean.parseBoolean(br.readLine());

                            System.out.print("nourriture: ");
                            String nour = br.readLine();

                            Animal a = new Animal(id, puce, nom, type, race, dn, da, statut,
                                    ch, cchiens, cchats, cbebes, nour);

                            g.insert(a, "animal");
                            animauxMemo.add(a);
                            System.out.println("OK: animal ajouté (ou mis à jour).");
                            break;
                        }

                        /** Recherche un Animal dans la table */
                        case "RECHERCHER_ANIMAL": {
                            System.out.print("type (ou vide): ");
                            String type = br.readLine().trim();

                            System.out.print("statut (ou vide): ");
                            String statut = br.readLine().trim();

                            String sql = "SELECT * FROM animal WHERE 1=1";
                            if (!type.isEmpty()) sql += " AND type = '" + type.replace("'", "''") + "'";
                            if (!statut.isEmpty()) sql += " AND statut = '" + statut.replace("'", "''") + "'";

                            g.queryAndPrint(sql);
                            break;
                        }
                        
                        /** Ajouter un box dans la table box */
                        case "AJOUTER_BOX": {
                            System.out.print("id_box: ");
                            int idBox = Integer.parseInt(br.readLine());

                            System.out.print("num: ");
                            int num = Integer.parseInt(br.readLine());

                            System.out.print("type_autorise: ");
                            String typeAutorise = br.readLine().replace("'", "''");

                            System.out.print("capacite_max: ");
                            int capaciteMax = Integer.parseInt(br.readLine());

                            String sql = "INSERT INTO box(id_box, num, type_autorise, capacite_max) VALUES (" +
                                    idBox + ", " + num + ", '" + typeAutorise + "', " + capaciteMax + ")";

                            g.execute(sql);
                            Box box = new Box(idBox, num, typeAutorise, capaciteMax);
                            boxsMemo.add(box);
                            
                            System.out.println("OK: box ajouté.");
                            break;
                        }
                        
                        /** Affecter un animal à un box (table animal_box) */
                        case "AFFECTER_BOX": {
                            System.out.print("id_animal: ");
                            int idAnimal = Integer.parseInt(br.readLine());

                            System.out.print("id_box: ");
                            int idBox = Integer.parseInt(br.readLine());

                            System.out.print("date_debut_box (YYYY-MM-DD): ");
                            Date debut = Date.valueOf(br.readLine().trim());

                            // Optionnel (si tu veux gérer la fin et un commentaire)
                            System.out.print("date_fin_box (YYYY-MM-DD) (laisser vide si aucune): ");
                            String finStr = br.readLine().trim();

                            System.out.print("commentaire (laisser vide si aucun): ");
                            String comm = br.readLine().replace("'", "''").trim();

                            String sql;
                            if (finStr.isEmpty()) {
                                // insertion sans date_fin_box (NULL)
                                sql = "INSERT INTO animal_boid_animal, id_box, date_debut_box, date_fin_box, commentaire) VALUES (" +
                                        idAnimal + ", " + idBox + ", '" + debut + "', NULL, " +
                                        (comm.isEmpty() ? "NULL" : "'" + comm + "'") + ")";
                            } else {
                                Date fin = Date.valueOf(finStr);
                                sql = "INSERT INTO animal_boid_animal, id_box, date_debut_box, date_fin_box, commentaire) VALUES (" +
                                        idAnimal + ", " + idBox + ", '" + debut + "', '" + fin + "', " +
                                        (comm.isEmpty() ? "NULL" : "'" + comm + "'") + ")";
                            }

                            g.execute(sql);
                            System.out.println("OK: box affecté à l'animal.");
                            break;
                        }
                        
                        /** Ajouter un soin (table soin) */
                        case "AJOUTER_SOIN": {
                            System.out.print("id_soin: ");
                            int idSoin = Integer.parseInt(br.readLine());

                            System.out.print("libelle: ");
                            String libelle = br.readLine().replace("'", "''");

                            System.out.print("description: ");
                            String description = br.readLine().replace("'", "''");

                            System.out.print("duree_minute: ");
                            int duree = Integer.parseInt(br.readLine());

                            System.out.print("veterinaire: ");
                            String veto = br.readLine().replace("'", "''");

                            String sql =
                                "INSERT INTO soin(id_soin, libelle, description, duree_minute, veterinaire) VALUES (" +
                                idSoin + ", '" + libelle + "', '" + description + "', " + duree + ", '" + veto + "')";

                            g.execute(sql);
                            Soin soin = new Soin(idSoin, libelle, description, duree,veto);
                            soinsMemo.add(soin);
                            
                            System.out.println("OK: soin ajouté.");
                            break;
                        }
                        /** Affecter un soin à un animal (table animal_soin) */
                        case "AFFECTER_SOIN": {
                            System.out.print("id_animal: ");
                            int idAnimal = Integer.parseInt(br.readLine());

                            System.out.print("id_soin: ");
                            int idSoin = Integer.parseInt(br.readLine());

                            System.out.print("date_soin (YYYY-MM-DD): ");
                            Date dateSoin = Date.valueOf(br.readLine().trim());

                            System.out.print("commentaire (laisser vide si aucun): ");
                            String comm = br.readLine().replace("'", "''").trim();

                            String sql =
                                "INSERT INTO animal_soin(id_animal, id_soin, date_soin, commentaire) VALUES (" +
                                idAnimal + ", " + idSoin + ", '" + dateSoin + "', " +
                                (comm.isEmpty() ? "NULL" : "'" + comm + "'") + ")";

                            g.execute(sql);
                            System.out.println("OK: soin affecté à l'animal.");
                            break;
                        }
                        /** Ajouter une FAMILLES */
                        case "AJOUTER_FAMILLE": {
                            System.out.print("id_famille: ");
                            int idFam = Integer.parseInt(br.readLine());

                            System.out.print("nom: ");
                            String nom = br.readLine().replace("'", "''");

                            System.out.print("adresse: ");
                            String adresse = br.readLine().replace("'", "''");

                            System.out.print("telephone: ");
                            String tel = br.readLine().replace("'", "''");

                            System.out.print("capacite: ");
                            int cap = Integer.parseInt(br.readLine());

                            Famille f = new Famille(idFam, nom, adresse, tel, cap);
                            g.insert(f, "famille");
                            famillesMemo.add(f);

                            System.out.println("OK: famille ajoutée.");
                            break;
                        }
                        /** Placer un animal dans une famille (table animal_famille) */
                        case "PLACER_FAMILLE": {
                            System.out.print("id_animal: ");
                            int idAnimal = Integer.parseInt(br.readLine());

                            System.out.print("id_famille: ");
                            int idFamille = Integer.parseInt(br.readLine());

                            System.out.print("type_famille (Accueil/Adoption): ");
                            String typeFamille = br.readLine().trim();
                            
                            // Validation du type_famille
                            if (!typeFamille.equalsIgnoreCase("Accueil") && !typeFamille.equalsIgnoreCase("Adoption")) {
                                System.out.println("Erreur: type_famille doit être 'Accueil' ou 'Adoption'");
                                break;
                            }

                            System.out.print("date_debut_famille (YYYY-MM-DD): ");
                            Date dateDebut = Date.valueOf(br.readLine().trim());

                            System.out.print("date_fin_famille (YYYY-MM-DD) (laisser vide si aucune): ");
                            String finStr = br.readLine().trim();

                            System.out.print("raison_retour (laisser vide si aucune): ");
                            String raison = br.readLine().replace("'", "''").trim();

                            String sql;
                            if (finStr.isEmpty()) {
                                // Insertion sans date_fin_famille (NULL)
                                sql = "INSERT INTO animal_famille(id_animal, id_famille, type_famille, date_debut_famille, date_fin_famille, raison_retour) VALUES (" +
                                        idAnimal + ", " + idFamille + ", '" + typeFamille + "', '" + dateDebut + "', NULL, " +
                                        (raison.isEmpty() ? "NULL" : "'" + raison + "'") + ")";
                            } else {
                                Date dateFin = Date.valueOf(finStr);
                                sql = "INSERT INTO animal_famille(id_animal, id_famille, type_famille, date_debut_famille, date_fin_famille, raison_retour) VALUES (" +
                                        idAnimal + ", " + idFamille + ", '" + typeFamille + "', '" + dateDebut + "', '" + dateFin + "', " +
                                        (raison.isEmpty() ? "NULL" : "'" + raison + "'") + ")";
                            }

                            g.execute(sql);
                            System.out.println("OK: animal placé dans la famille.");
                            break;
                        }
                        /** Ajouter un bénévole (table benevole) */
                        case "AJOUTER_BENEVOLE": {
                            System.out.print("id_benevole: ");
                            int idB = Integer.parseInt(br.readLine());

                            System.out.print("nom: ");
                            String nomB = br.readLine().replace("'", "''");

                            System.out.print("prenom: ");
                            String prenomB = br.readLine().replace("'", "''");

                            System.out.print("telephone: ");
                            String telB = br.readLine().replace("'", "''");

                            System.out.print("role: ");
                            String roleB = br.readLine().replace("'", "''");

                            String sql =
                                    "INSERT INTO benevole(id_benevole, nom, prenom, telephone, role) VALUES (" +
                                    idB + ", '" + nomB + "', '" + prenomB + "', '" + telB + "', '" + roleB + "')";

                            g.execute(sql);
                            System.out.println("OK: bénévole ajouté.");
                            break;
                        }
                        
                        case "MODIFIER_PLANNING": {

                            // ---- 1) ancienne clé (pour retrouver la ligne) ----
                            System.out.print("id_animal ACTUEL: ");
                            int oldAnimal = Integer.parseInt(br.readLine());

                            System.out.print("id_creneau ACTUEL: ");
                            int oldCreneau = Integer.parseInt(br.readLine());

                            System.out.print("id_activite ACTUEL: ");
                            int oldActivite = Integer.parseInt(br.readLine());

                            System.out.print("id_benevole ACTUEL: ");
                            int oldBenevole = Integer.parseInt(br.readLine());

                            // ---- 2) nouvelles valeurs (optionnelles) ----
                            System.out.print("nouvel id_animal (ou '-' si inchangé): ");
                            String inAnimal = br.readLine().trim();

                            System.out.print("nouvel id_creneau (ou '-' si inchangé): ");
                            String inCreneau = br.readLine().trim();

                            System.out.print("nouvel id_activite (ou '-' si inchangé): ");
                            String inActivite = br.readLine().trim();

                            System.out.print("nouvel id_benevole (ou '-' si inchangé): ");
                            String inBenevole = br.readLine().trim();

                            System.out.print("nouveau commentaire (ou '-' si inchangé, vide = NULL): ");
                            String inComm = br.readLine(); // ne pas trim ici pour laisser vide
                            String commTrim = inComm.trim();

                            int newAnimal = inAnimal.equals("-") ? oldAnimal : Integer.parseInt(inAnimal);
                            int newCreneau = inCreneau.equals("-") ? oldCreneau : Integer.parseInt(inCreneau);
                            int newActivite = inActivite.equals("-") ? oldActivite : Integer.parseInt(inActivite);
                            int newBenevole = inBenevole.equals("-") ? oldBenevole : Integer.parseInt(inBenevole);

                            boolean keyChanged =
                                    (newAnimal != oldAnimal) ||
                                    (newCreneau != oldCreneau) ||
                                    (newActivite != oldActivite) ||
                                    (newBenevole != oldBenevole);

                            boolean commentChanged = !commTrim.equals("-");

                            // Valeur SQL du commentaire :
                            // - "-" => inchangé (donc on n'y touche pas)
                            // - ""  => NULL
                            // - texte => 'texte'
                            String commSql = null;
                            if (commentChanged) {
                                String safe = inComm.replace("'", "''"); // protège les apostrophes
                                if (safe.trim().isEmpty()) commSql = "NULL";
                                else commSql = "'" + safe.trim() + "'";
                            }

                            // ---- 3) si on ne change que le commentaire => UPDATE ----
                            if (!keyChanged) {
                                if (!commentChanged) {
                                    System.out.println("Rien à modifier.");
                                    break;
                                }

                                String sql =
                                        "UPDATE participation_activite SET commentaire = " + commSql +
                                        " WHERE id_animal = " + oldAnimal +
                                        " AND id_creneau = " + oldCreneau +
                                        " AND id_activite = " + oldActivite +
                                        " AND id_benevole = " + oldBenevole;

                                g.execute(sql);
                                System.out.println("OK: planning modifié (commentaire).");
                                break;
                            }

                            // ---- 4) si la clé change => DELETE + INSERT ----
                            // (on garde l'ancien commentaire si pas modifié)
                            String deleteSql =
                                    "DELETE FROM participation_activite" +
                                    " WHERE id_animal = " + oldAnimal +
                                    " AND id_creneau = " + oldCreneau +
                                    " AND id_activite = " + oldActivite +
                                    " AND id_benevole = " + oldBenevole;

                            g.execute(deleteSql);

                            // On insère avec ou sans commentaire selon ce que tu as donné
                            String insertSql;
                            if (commentChanged) {
                                insertSql =
                                        "INSERT INTO participation_activite(id_animal, id_creneau, id_activite, id_benevole, commentaire) VALUES (" +
                                        newAnimal + ", " + newCreneau + ", " + newActivite + ", " + newBenevole + ", " + commSql + ")";
                            } else {
                                // commentaire inchangé -> on le perdrait si on ne le relit pas.
                                // version simple (L2) : on réinsère sans commentaire.
                                insertSql =
                                        "INSERT INTO participation_activite(id_animal, id_creneau, id_activite, id_benevole) VALUES (" +
                                        newAnimal + ", " + newCreneau + ", " + newActivite + ", " + newBenevole + ")";
                            }

                            g.execute(insertSql);

                            System.out.println("OK: planning modifié (clé changée).");
                            break;
                        }
                        
                        case "SUPPRIMER_PLANNING": {
                            System.out.print("id_animal: ");
                            int idAnimal = Integer.parseInt(br.readLine());

                            System.out.print("id_creneau: ");
                            int idCreneau = Integer.parseInt(br.readLine());

                            System.out.print("id_activite: ");
                            int idActivite = Integer.parseInt(br.readLine());

                            System.out.print("id_benevole: ");
                            int idBenevole = Integer.parseInt(br.readLine());

                            String sql = "DELETE FROM participation_activite" +
                                    " WHERE id_animal = " + idAnimal +
                                    " AND id_creneau = " + idCreneau +
                                    " AND id_activite = " + idActivite +
                                    " AND id_benevole = " + idBenevole;

                            g.execute(sql);
                            System.out.println("OK: inscription supprimée du planning.");
                            break;
                        }
                        
                        case "PLANNING_JOUR": {
                            System.out.print("date (YYYY-MM-DD): ");
                            Date date = Date.valueOf(br.readLine().trim());

                            String sql =
                                    "SELECT c.date_creneau, c.heure_debut, c.heure_fin, " +
                                    "a.type_activite, b.nom, b.prenom, an.nom AS animal, p.commentaire " +
                                    "FROM participation_activite p " +
                                    "JOIN creneau c ON p.id_creneau = c.id_creneau " +
                                    "JOIN activite a ON p.id_activite = a.id_activite " +
                                    "JOIN benevole b ON p.id_benevole = b.id_benevole " +
                                    "JOIN animal an ON p.id_animal = an.id_animal " +
                                    "WHERE c.date_creneau = '" + date + "' " +
                                    "ORDER BY c.heure_debut";

                            g.queryAndPrint(sql);
                            break;
                        }
                        
                        case "AJOUTER_CRENEAU": {
                            System.out.print("id_creneau: ");
                            int idC = Integer.parseInt(br.readLine());

                            System.out.print("date_creneau (YYYY-MM-DD): ");
                            Date dateC = Date.valueOf(br.readLine().trim());

                            System.out.print("heure_debut (HH:MM): ");
                            String hDeb = br.readLine().trim();

                            System.out.print("heure_fin (HH:MM): ");
                            String hFin = br.readLine().trim();

                            System.out.print("nombre_min_benevoles: ");
                            int minB = Integer.parseInt(br.readLine());

                            String sql =
                                    "INSERT INTO creneau(id_creneau, date_creneau, heure_debut, heure_fin, nombre_min_benevoles) VALUES (" +
                                    idC + ", '" + dateC + "', '" + hDeb + "', '" + hFin + "', " + minB + ")";

                            g.execute(sql);
                            System.out.println("OK: créneau ajouté.");
                            break;
                        }
                        
                        case "PLANNING_BENEVOLE": {
                            System.out.print("id_benevole: ");
                            int idB = Integer.parseInt(br.readLine());

                            String sql =
                                    "SELECT c.heure_debut, c.heure_fin, a.type_activite, an.nom AS animal, p.commentaire " +
                                    "FROM participation_activite p " +
                                    "JOIN creneau c ON p.id_creneau = c.id_creneau " +
                                    "JOIN activite a ON p.id_activite = a.id_activite " +
                                    "JOIN animal an ON p.id_animal = an.id_animal " +
                                    "WHERE p.id_benevole = " + idB + " " +
                                    "ORDER BY c.heure_debut";

                            g.queryAndPrint(sql);
                            break;
                        }
                        
                        case "INSCRIRE_PLANNING": {
                            System.out.print("id_benevole: ");
                            int idB = Integer.parseInt(br.readLine());

                            System.out.print("id_creneau: ");
                            int idC = Integer.parseInt(br.readLine());

                            System.out.print("id_activite: ");
                            int idA = Integer.parseInt(br.readLine());

                            System.out.print("id_animal: ");
                            int idAnimal = Integer.parseInt(br.readLine());

                            System.out.print("commentaire (laisser vide si aucun): ");
                            String comm = br.readLine().replace("'", "''").trim();

                            String sql = "INSERT INTO participation_activite(id_animal, id_creneau, id_activite, id_benevole, commentaire) VALUES (" +
                                    idAnimal + ", " + idC + ", " + idA + ", " + idB + ", " +
                                    (comm.isEmpty() ? "NULL" : "'" + comm + "'") + ")";

                            g.execute(sql);
                            System.out.println("OK: inscription ajoutée au planning.");
                            break;
                        }
                        
                        case "AJOUTER_ACTIVITE": {
                            System.out.print("id_activite: ");
                            int idA = Integer.parseInt(br.readLine());

                            System.out.print("type_activite: ");
                            String typeAct = br.readLine().replace("'", "''");

                            System.out.print("description: ");
                            String desc = br.readLine().replace("'", "''");

                            System.out.print("nombre_min_animaux: ");
                            int minAnim = Integer.parseInt(br.readLine());

                            String sql = "INSERT INTO activite(id_activite, type_activite, description, nombre_min_animaux) VALUES (" +
                                    idA + ", '" + typeAct + "', '" + desc + "', " + minAnim + ")";

                            g.execute(sql);
                            System.out.println("OK: activité ajoutée.");
                            break;
                        }
                        case "SAVE":
                            try {
                                MemoireSPA.save(animauxMemo, famillesMemo, boxsMemo, soinsMemo);
                                System.out.println("Sauvegarde OK");
                            } catch (Exception e) {
                                System.out.println(" Erreur SAVE: " + e.getMessage());
                            }
                            break;
                            

                        case "LOAD":
                            try {
                                MemoireSPA.Donnees d = MemoireSPA.loadAll();
                                animauxMemo = d.animaux;
                                famillesMemo = d.familles;
                                boxsMemo = d.boxs;
                                soinsMemo = d.soins;
                              

                                System.out.println("Chargement OK");
                                System.out.println("Animaux: " + animauxMemo.size()
                                        + " | Familles: " + famillesMemo.size()
                                        + " | Box: " + boxsMemo.size()
                                        + " | Soins: " + soinsMemo.size()
                                       );
                            } catch (Exception e) {
                                System.out.println(" Erreur LOAD: " + e.getMessage());
                            }
                            break;

                        default:
                            System.out.println("Commande inconnue. Tape HELP.");
                    }
                    
                //  Gestion des exceptions personnalisées
                } catch (TableNotFoundException e) {
                    System.out.println("Erreur: " + e.getMessage());
                } catch (QueryException e) {
                    System.out.println("Erreur SQL: " + e.getMessage());
                } catch (DataException e) {
                    System.out.println("Erreur de données: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("Erreur: nombre invalide");
                } catch (IllegalArgumentException e) {
                    System.out.println("Erreur: format de date invalide (YYYY-MM-DD)");
                } catch (Exception e) {
                    System.out.println("Erreur: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //  Gestion de ConnexionException dans finally
            try {
                Connexion.close(cnx);
            } catch (ConnexionException e) {
                System.err.println("Erreur fermeture: " + e.getMessage());
            }
        }
    }
}
