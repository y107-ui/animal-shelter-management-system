package exceptions;
/**
 * Exception levée quand une table demandée n'existe pas
 */
public class TableNotFoundException extends QueryException {
	 public TableNotFoundException(String tableName) {
	        super("Table introuvable : " + tableName);
	    }
}
