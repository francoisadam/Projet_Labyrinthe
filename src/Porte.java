import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * <b>Porte est la classe heritant de case representant une porte qu inecessite la clef pouetre ouverte.</b>
 * <p>
 * Une porte est une case obligatoirement vide et a un graphisme particulier.
 *
 * @see Case
 * 
 * @author Benjamin RANCINANGUE et Francois ADAM
 * @version 1.0
 */
public class Porte extends Case {
	
	/**
	 * Constructeur par defaut de la porte.
	 * Cree une case vide dont la porte n'est pas ouverte.
	 */
	public Porte () {
		super.estVide = false;
	}
	
	/**
	 * Rend la porte ouverte
	 */
	public void setOuverte() {
		super.estVide = true;
	}
	
	/**
	 * Retourne le graphisme de la porte suivant si elle est ouverte ou non.
	 * 
	 * @return Un caractere qui represente la porte : 'p' si elle est ouverte, 'P' sinon.
	 */
	public char graphismeCase () {
		if (this.estVide == false) {
			return 'P';
		}
		else {
			return 'p';
		}
	}
	
	/**
	 *Retourne l'image de la porte
	 *
	 * @param packTexture l'entier qui represente le numero du pack de texture
	 * @return Une icone de porte
	 */
	public Icon imageCase (int packTexture) {
		if (this.estVide == false) {
			return new ImageIcon(getClass().getResource("images/" + packTexture + "/porte.png"));
		}
		else {
			return new ImageIcon();
		}
	}

}