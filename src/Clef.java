import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * <b>Clef est la classe heritant de case representant la clef qui permet d'ouvrir la porte.</b>
 * <p>
 * Une clef est une case obligatoirement vide et a un graphisme particulier.
 *
 * @see Case
 * @see Clef#estRamassee
 * 
 * @author Benjamin RANCINANGUE et Francois ADAM
 * @version 1.0
 */
public class Clef extends Case {

	/**
	 * Le booleen qui modelise si la clef est ramassee ou non.
	 */
	private boolean estRamassee;

	/**
	 * Constructeur par defaut de la clef.
	 * Cree une case vide dont la clef n'a aps ete ramassee.
	 */
	public Clef () {
		super.estVide = true;
		this.estRamassee = false;
	}

	/**
	 * Rend le clef ramassee.
	 */
	public void setRamassee() {
		this.estRamassee = true;
	}

	/**
	 * Retourne si la clef a ete ramassee ou non.
	 * 
	 * @return Etat de ramassage de la clef, sous forme d'un boolean : ramassee ou non
	 */
	public boolean getRamassee() {
		return this.estRamassee;
	}

	/**
	 * Retourne le graphisme de la clef suivant si elle est ramassee ou non.
	 * 
	 * @return Un caractere qui represente la clef : 'C' si non ramassee, 'c' sinon.
	 */
	public char graphismeCase () {
		if (this.estRamassee == false) {
			return 'C';
		}
		else {
			return ' ';
		}
	}

	/**
	 *Retourne l'image de la clef
	 *
	 * @param packTexture l'entier qui represente le numero du pack de texture
	 * @return Une icone de clef
	 */
	public Icon imageCase (int packTexture) {
		if (this.estRamassee == false) {
			return new ImageIcon(getClass().getResource("images/" + packTexture + "/clef.png"));
		}
		else {
			return new ImageIcon();
		}
	}

}