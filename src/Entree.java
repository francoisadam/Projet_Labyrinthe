import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * <b>Entree est la classe heritant de case representant une entree.</b>
 * <p>
 * Une entree est obligatoirement vide et a un graphisme particulier.
 *
 * @see Case
 * 
 * @author Benjamin RANCINANGUE et Francois ADAM
 * @version 1.1
 */
public class Entree extends Case {
	
	/**
	* Constructeur par défaut d'une entree.
	* Cree une case vide.
	*/
	public Entree () {
		super.estVide = true;
	}
	
	/**
	 * Retourne le graphisme de l'entree.
	 * 
	 * @return Un caractere qui represente une entree, 'E' en l'occurence.
	 */
	public char graphismeCase () {
		return 'E';
	}
	
	/**
	 *Retourne l'image de la case
	 * 
	 * @return Une icone d'une entree
	 */
	public Icon imageCase () {
		return new ImageIcon();
	}

}