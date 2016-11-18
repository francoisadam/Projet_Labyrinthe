import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * <b>Sortie est la classe heritant de case representant une sortie.</b>
 * <p>
 * Une sortie est obligatoirement vide et a un graphisme particulier.
 *
 * @see Case
 * 
 * @author Benjamin RANCINANGUE et Francois ADAM
 * @version 1.1
 */
public class Sortie extends Case {
	
	/**
	* Constructeur par defaut d'une sortie.
	* Cree une case vide.
	*/
	public Sortie () {
		super.estVide = true;
	}
	
	/**
	 * Retourne le graphisme de la sortie.
	 * 
	 * @return Un caractere qui represente une sortie, 'S' en l'occurence.
	 */
	public char graphismeCase () {
		return 'S';
	}
	
	/**
	 *Retourne l'image de la case
	 * 
	 * @param packTexture l'entier qui represente le numero du pack de texture
	 * @return Une icone de sortie
	 */
	public Icon imageCase (int packTexture) {
		return new ImageIcon(getClass().getResource("images/" + packTexture + "/sortie.png"));
	}

}