import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * <b>Mineur est la classe representant un mineur.</b>
 * <p>
 * Un mineur est caracterise par :
 * <ul>
 * <li>son abscisse X</li>
 * <li>son ordonnee Y</li>
 * </ul>
 * 
 * @see Mineur#mineurX
 * @see Mineur#mineurY
 * 
 * @author Benjamin RANCINANGUE et Francois ADAM
 * @version 1.1
 */
public class Mineur {
	
	/**
	 * L'entier qui modelise l'abscisse du mineur.
	 */
	private int mineurX;
	
	/**
	 * L'entier qui modelise l'ordonnee du mineur.
	 */
	private int mineurY;
	
	/**
	 * Constructeur par defaut du mineur.
	 * Cree un mineur dont l'abscisse et l'ordonnee sont nulles.
	 */
	public Mineur() {
		this.mineurX = 0;
		this.mineurY = 0;
	}

	/**
	 * Constructeur du mineur en fonction d'une abscisse et d'une ordonnee.
	 * Cree un mineur dont l'abscisse et l'ordonnee sont entrees en parametre.
	 * 
	 * @param x Abscisse du nouveau mineur.
	 * @param y Ordonnee du nouveau mineur.
	 */
	public Mineur(int x, int y) {
		this.mineurX = x;
		this.mineurY = y;
	}
	
	/**
	 * Retourne l'abscisse du mineur.
	 * 
	 * @return L'abscisse du mineur, sous forme d'un entier.
	 */
	public int getX () {
		return this.mineurX;
	}
	
	/**
	 * Retourne l'ordonnee du mineur.
	 * 
	 * @return L'ordonnee du mineur, sous forme d'un entier.
	 */
	public int getY () {
		return this.mineurY;
	}
	
	/**
	 * Affecte une nouvelle abscisse au mineur.
	 * 
	 * @param x Nouvelle abscisse a attribuer au mineur.
	 */
	public void setX (int x) {
		this.mineurX = x;
	}
	
	/**
	 * Affecte une nouvelle ordonnee au mineur.
	 * 
	 * @param y Nouvelle ordonnee a attribuer au mineur.
	 */
	public void setY (int y) {
		this.mineurY = y;
	}
	
	/**
	 * Retourne le graphisme du mineur.
	 * 
	 * @return Un caractere 'M' qui represente le mineur.
	 */
	public char graphismeMineur () {
		return 'M';
	}
	
	/**
	 *Retourne l'image du mineur
	 * 
	 * @param packTexture l'entier qui represente le numero du pack de texture
	 * @return Une icone d'un mineur
	 */
	public Icon imageCase (int packTexture) {
		return new ImageIcon(getClass().getResource("images/" + packTexture + "/mineur.png"));
	}
}