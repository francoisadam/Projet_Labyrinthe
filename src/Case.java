import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * <b>Case est la classe representant une case de la grille de labyrinthe.</b>
 * <p>
 * Une case est soit vide, soit pleine et peut etre :
 * <ul>
 * <li>un couloir</li>
 * <li>un mur</li>
 * <li>une entree</li>
 * <li>une sortie</li>
 * <li>un filon</li>
 * </ul>
 * 
 * @see Entree
 * @see Sortie
 * @see Porte
 * @see Case#estVide
 * 
 * @author Benjamin RANCINANGUE et Francois ADAM
 * @version 1.1
 */
public class Case {

	/**
	 * Le boolean qui modelise si une case est vide ou non.
	 * 
	 * @see Case#setVide()
	 * @see Case#getEtat()
	 */
	protected boolean estVide;

	/**
	 * Constructeur par defaut d'une case.
	 * Cree une case vide.
	 */
	public Case () {
		this.estVide = true;
	}

	/**
	 * Constructeur d'une case en fonction de son type.
	 * Cree une case vide si c'est un couloir (' '), cree une case pleine sinon.
	 * 
	 * @param c Caractere de la case. ' ' est un couloir, '#' est un mur, 'E  est une entree, 'S' est une sortie, "F" est un filon.
	 */
	public Case (char c) {
		if (c == ' ') {
			this.estVide = true;
		}
		else {
			this.estVide = false;
		}
	}

	/**
	 * Setteur qui rend la case vide.
	 * @param b bolean, etat a attribuer a la case (vide ou non).
	 */
	public void setEtat (boolean b) {
		this.estVide = b;
	}



	/**
	 * Retourne l'etat de la case.
	 * 
	 * @return Etat de la case, sous forme d'un boolean : vide ou non
	 */
	public boolean getEtat () {
		return this.estVide;
	}

	/**
	 * Retourne le graphisme de la case.
	 * 
	 * @return Un caractere qui represente la case. ' ' si c'est un couloir, '#' sinon.
	 */
	public char graphismeCase () {
		if (this.getEtat() == true) {
			return ' ';
		}
		else {
			return '#';
		}
	}

	/**
	 *Retourne l'image de la case
	 *
	 * @param packTexture l'entier qui represente le numero du pack de texture
	 * @return Une icone qui dépend du type de case
	 */
	public Icon imageCase (int packTexture) {
		if (this.getEtat() == true) {
			return new ImageIcon();
		}
		else {
			return new ImageIcon(getClass().getResource("images/" + packTexture + "/mur.png"));
		}	
	}
}