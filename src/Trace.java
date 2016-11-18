import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * <b>Trace est la classe heritant de case representant une case sur laquelle un passage a ete effectue.</b>
 * <p>
 * Une trace est une case obligatoirement vide et a un graphisme particulier.
 *
 * @see Case
 * 
 * @author Benjamin RANCINANGUE et Francois ADAM
 * @version 1.1
 */
public class Trace extends Case {
	
	/**
	 * Constructeur par defaut de la trace.
	 * Cree une case vide mais parcourue.
	 */
	public Trace () {
		super.estVide = true;
	}

	/**
	 * Retourne le graphisme de la trace.
	 * 
	 * @return Un caractere : 'X', indiquant que la case a ete parcourure.
	 */
	public char graphismeCase () {
		return 'X';
	}
	
	/**
	 *Retourne l'image de la case
	 * 
	 * @return Une icone d'une trace
	 */
	public Icon imageCase () {
		return new ImageIcon(getClass().getResource("images/chemin.png"));
	}
}