package exceptions;
/**
 * Exception levée quand un animal est incompatible avec son environnement
 */
public class IncompatibleAnimalException extends BusinessException {
	 public IncompatibleAnimalException(String animalName, String incompatibility) {
	        super("L'animal '" + animalName + "' est incompatible : " + incompatibility);
	    }
}
