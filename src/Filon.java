import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * <b>Filon est la classe heritant de case representant un filon.</b>
 * <p>
 * Un filon est une case obligatoirement vide et a un graphisme particulier.
 *
 * @see Case
 * @see Filon#estExtrait
 * 
 * @author Benjamin RANCINANGUE et Francois ADAM
 * @version 1.1
 */
public class Filon extends Case {
	
	/**
	 * Le booleen qui modelise si le filon est extrait ou non.
	 */
	private boolean estExtrait;
	
	/**
	 * Constructeur par defaut du filon.
	 * Cree une case vide dont le filon n'est pas extrait.
	 */
	public Filon () {
		super.estVide = true;
		this.estExtrait = false;
	}
	
	/**
	 * Rend le filon extrait.
	 */
	public void setExtrait() {
		this.estExtrait = true;
	}
	
	/**
	 * Retourne l'etat d'extraction du filon.
	 * 
	 * @return Etat d'extraction du filon, sous forme d'un boolean : extrait ou non
	 */
	public boolean getExtrait() {
		return this.estExtrait;
	}
	
	/**
	 * Retourne le graphisme du filon suivant s'il est extrait ou non.
	 * 
	 * @return Un caractere qui represente le filon : 'F' si non extrait, '.' sinon.
	 */
	public char graphismeCase () {
		if (this.estExtrait == false) {
			return 'O';
		}
		else {
			return ' ';
		}
	}
	
	/**
	 *Retourne l'image de la case
	 *
	 * @param packTexture l'entier qui represente le numero du pack de texture
	 * @return Une icone d'un filon
	 */
	public Icon imageCase (int packTexture) {
		if (this.estExtrait == false) {
			return new ImageIcon(getClass().getResource("images/" + packTexture + "/filon.png"));
		}
		else {
			return new ImageIcon();
		}
	}

}