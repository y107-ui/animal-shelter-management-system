package gestion;

import data.IData;
import data.fieldType;
import exceptions.*;

import java.sql.*;
import java.util.*;

/**
 * Classe de gestion de la base de données.
 * Fournit des méthodes pour interagir avec la base de données PostgreSQL,
 * incluant la récupération de structure, l'affichage de tables, l'exécution
 * de requêtes, l'insertion et la suppression de données.
 */
public class GestionBDD {

    private final Connection cnx;

    public GestionBDD(Connection cnx) {
        this.cnx = cnx;
    }

    /** 
     * Convertit un type SQL en fieldType.
     * 
     * @param sqlType Type SQL de la colonne (constante de java.sql.Types)
     * @return Le fieldType correspondant
     */
    private fieldType sqlTypeToFieldType(int sqlType) {
        switch (sqlType) {
            case Types.INTEGER:
            case Types.SMALLINT:
            case Types.TINYINT:
            case Types.BIGINT:
                return fieldType.INT4;

            case Types.NUMERIC:
            case Types.DECIMAL:
            case Types.BIT:
            case Types.BOOLEAN:
                return fieldType.NUMERIC;

            case Types.FLOAT:
            case Types.REAL:
            case Types.DOUBLE:
                return fieldType.FLOAT8;

            case Types.DATE:
            case Types.TIMESTAMP:
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
                return fieldType.VARCHAR;

            default:
                return fieldType.VARCHAR;
        }
    }


    /** Retourne la structure d'une table */
    public HashMap<String, fieldType> structTable(String table, boolean display) throws QueryException {
        HashMap<String, fieldType> struct = new HashMap<>();

        try {
            DatabaseMetaData meta = cnx.getMetaData();
            try (ResultSet rs = meta.getColumns(null, "uapv2500276", table.toLowerCase(), null)) {
                if (!rs.isBeforeFirst()) {
                    throw new TableNotFoundException(table);
                }
                while (rs.next()) {
                    String col = rs.getString("COLUMN_NAME");
                    int type = rs.getInt("DATA_TYPE");
                    struct.put(col, sqlTypeToFieldType(type));
                }
            }
        } catch (TableNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw new QueryException("Erreur lors de la récupération de la structure de '" + table + "'", e);
        }

        if (display) {
            System.out.println("STRUCTURE DE " + table);
            for (Map.Entry<String, fieldType> e : struct.entrySet()) {
                System.out.println("- " + e.getKey() + " : " + e.getValue());
            }
        }

        return struct;
    }


    /** Affiche le contenu d'une table */
    public void displayTable(String table) throws QueryException {
        String sql = "SELECT * FROM " + table;

        try (PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData md = rs.getMetaData();
            int nbCols = md.getColumnCount();

            for (int i = 1; i <= nbCols; i++) {
                System.out.print(md.getColumnName(i) + "\t");
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= nbCols; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("does not exist")) {
                throw new TableNotFoundException(table);
            }
            throw new QueryException("Erreur lors de l'affichage de '" + table + "'", e);
        }
    }
    
    /** Affiche le contenu d'une table avec filtre */
    public void queryAndPrint(String sql) throws QueryException {
        try (PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData md = rs.getMetaData();
            int n = md.getColumnCount();

            for (int i = 1; i <= n; i++) System.out.print(md.getColumnName(i) + "\t");
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= n; i++) System.out.print(rs.getString(i) + "\t");
                System.out.println();
            }
        } catch (SQLException e) {
            throw new QueryException("Erreur lors de l'exécution de la requête", e);
        }
    }

    /** Exécute une requête hors INSERT */
    public void execute(String query) throws QueryException {
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.execute();
        } catch (SQLException e) {
            throw new QueryException("Erreur lors de l'exécution de la requête", e);
        }
    }

    /** Trouve une colonne id */
    private String findIdColumn(HashMap<String, fieldType> struct) {
        if (struct.containsKey("id")) return "id";
        for (String k : struct.keySet()) {
            if (k.toLowerCase().startsWith("id_")) return k;
        }
        return struct.keySet().iterator().next();
    }

    /** INSERT générique avec mise à jour si id déjà présent */
    public void insert(IData data, String table) throws DataException, QueryException {

        HashMap<String, fieldType> tableStruct = structTable(table, false);

        if (!data.check(tableStruct)) {
            throw new DataException("Structure incompatible entre la table et l'objet.");
        }

        String idCol = findIdColumn(tableStruct);

        List<String> updateParts = new ArrayList<>();
        for (String col : tableStruct.keySet()) {
            if (!col.equalsIgnoreCase(idCol)) {
                updateParts.add(col + " = EXCLUDED." + col);
            }
        }
        String updateSet = String.join(", ", updateParts);

        String sql =
                "INSERT INTO " + table + " VALUES " + data.getValues() +
                " ON CONFLICT (" + idCol + ") DO UPDATE SET " + updateSet;

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new QueryException("Erreur lors de l'insertion dans '" + table + "'", e);
        }
    }

    /** Supprime une ligne par id */
    public void removeById(String table, int id) throws QueryException {
        try {
            HashMap<String, fieldType> struct = structTable(table, false);
            String idCol = findIdColumn(struct);

            String sql = "DELETE FROM " + table + " WHERE " + idCol + " = ?";
            try (PreparedStatement ps = cnx.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new QueryException("Erreur lors de la suppression dans '" + table + "'", e);
        }
    }

    /** Supprime une table */
    public void dropTable(String table) throws QueryException {
        execute("DROP TABLE IF EXISTS " + table);
    }
}
