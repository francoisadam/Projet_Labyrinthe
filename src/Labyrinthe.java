import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * <b>Labyrinthe est la classe representant un labyrinthe.</b>
 * <p>
 * Un labyrinthe est caracterise par :
 * <ul>
 * <li>un tableau de cases qui peuvent etre vides ou pleines </li>
 * <li>un mineur qui se deplace </li>
 * <li>un nombre de coups pour trouver la sortie et tous les filons</li>
 * <li>deux piles de cordonnees utiles a la creation aleatoire</li>
 * </ul>
 * 
 * @see Case
 * @see Mineur
 * @see Labyrinthe#nbCoups
 * 
 * @author Benjamin RANCINANGUE et Francois ADAM
 * @version 2.2
 */
public class Labyrinthe {

	/**
	 * Le tableau de case qui modelise le labyrinthe. Le contenu du tableau est modifiable.
	 * 
	 * @see Cases
	 * 
	 * @see Labyrinthe.GetLabyrinthe()
	 * @see Labyrinthe.GetLargeur()
	 * @see Labyrinthe.GetHauteur()
	 * @see Labyrinthe.AfficherLabyrinthe()
	 */
	private Case[][] labyrinthe;

	/**
	 * L'objet qui modelise le Mineur. Le mineur se deplace.
	 * 
	 * @see Mineur
	 * 
	 * @see Labyrinthe.GetMineur()
	 * @see Labyrinthe.DeplacerMineur()
	 */	
	private Mineur mineur;

	/**
	 * L'entier qui modelise le nombre de coups d'une partie pour trouver la sortie ainsi que tous les filons.
	 * 
	 * @see Labyrinthe.GetNbCoups()
	 */
	private int nbCoups;

	/**
	 * L'arrayList de vecteurs qui permet de stocker les coordonnees des impasses du labyrinthe (utile pour la generation aleatoire de labyrinthe)
	 */
	private static ArrayList<Integer[]> coordsImpasses;

	/**
	 * Constructeur par defaut du labyrinthe. 
	 * Cree un tableau de cases de taille 1*1, cree un nouveau mineur et initialise le nombre de coups a 0.
	 *  
	 * @see Labyrinthe#labyrinthe
	 * @see Labyrinthe#mineur
	 * @see Labyrinthe#nbCoups
	 */
	public Labyrinthe () {
		this.labyrinthe = new Case[1][1];
		this.mineur = new Mineur();
		this.nbCoups = 0;
	}

	/**
	 * Constructeur du labyrinthe vide dimensionne.
	 * Cree un tableau de cases de taille hauteur*largeur, cree un nouveau mineur et initialise le nombre de coups a 0.
	 * 
	 * @param hauteur La hauteur du tableau a creer
	 * @param largeur La largeur du tableau a creer
	 * 
	 * @see Labyrinthe#labyrinthe
	 * @see Labyrinthe#mineur
	 * @see Labyrinthe#nbCoups
	 */
	public Labyrinthe (int hauteur, int largeur) {
		this.labyrinthe = new Case[hauteur][largeur];
		this.mineur = new Mineur();
		this.nbCoups = 0;
	}

	/**
	 * Constructeur du labyrinthe aleatoire. 
	 * Cree un tableau de cases de taille hauteur*largeur, dont les "bords" sont des murs et dont le centre est compose d'une "grille" de cases vides et pleines.
	 * Puis génère des branches aléatoires jusqu'a remplir tout le labyrinthe.
	 * Cree de plus un nouveau mineur, une entree, une sortie, le nombre de filons precise en argument, et initialise le nombre de coups a 0.
	 * Enfin, une porte est placée entre la case de depart et la case de sortie, sur le chemin solution. La clef permettant son ouverture est deposee dans une impasse du albyrinthe, en amont de la porte bien sur.
	 * 
	 * @param hauteur La hauteur du tableau a creer
	 * @param largeur La largeur du tableau a creer
	 * @param nbFilons Le nombre de filons a integrer au labyrinthe	
	 * 
	 * @see Labyrinthe#labyrinthe
	 * @see Labyrinthe#mineur
	 * @see Labyrinthe#nbCoups
	 */
	public Labyrinthe (int hauteur, int largeur, int nbFilons) {		
		this();

		//Si trop de filons sont demandes, affiche un pop-up a ce sujet et quitte le programme. La demonstration de la formule de verification est donnee dans le rapport.
		if (nbFilons > (hauteur*largeur-hauteur-largeur+3)/4) {
			Partie.affichageErreur(6);
		}	

		//Declarations initiales 		
		Stack<Integer[]> cheminVersSortie = new Stack<Integer[]>();
		int nbCasesCommunes = 0;

		//Tant qu'il y a conflit lors du placement de la porte (ce qui est très peu probable), on relance la generation.
		while (nbCasesCommunes - cheminVersSortie.size() + 1 >= 0) {	

			//Initialisation des coordonnees des impasses (utiles pour le placement de la clef)
			coordsImpasses = new ArrayList<Integer[]>();
			//Initialisation de la pile qui contiendra les coordonnes des cases constituant le chemin entre l'entree et la sortie (utile pour le placement de la porte)
			cheminVersSortie = new Stack<Integer[]>();
			//Declaration et initialisation de la pile qui contiendra les coordonnes des cases constituant le chemin entre l'entree et la clef (utile pour le placement de la porte)
			Stack<Integer[]> cheminVersClef = new Stack<Integer[]>();		
			//Declaration et initialisation des coordonnes de la future sortie
			int abscisseSortie = -1;
			int ordonneeSortie = -1;

			//Si le labyrinthe genere n'a pas d'impasse (ce qui est tres peu probable), on relance la generation (car sinon le labyrinthe est trop facile et la clef ne se trouve pas dans une impasse, puiqu'il n'y en a pas).
			while (coordsImpasses.size() < 1) {
				//Creation d'un tableau de case de la taille specifiee en paramètre
				this.labyrinthe = new Case[hauteur][largeur];

				//Remplissage du tableau avec des murs sur le contour et en "grille" à l'intérieur
				for (int i = 0 ; i < this.getHauteur() ; i++) {
					for (int j = 0 ; j < this.getLargeur() ; j++) {
						if ((i == 0 || j == 0 || i == hauteur-1 || j == largeur-1) || (j%2 == 0 && i%2 == 0)) {
							this.labyrinthe[i][j] = new Case('#');
						}
						else {
							this.labyrinthe[i][j] = new Case(' ');
						}
					}
				}

				//Creation aleatoire d'une entree et du mineur, aux memes coordonnees			
				if ((int)(2*Math.random()) == 1) {
					int ordonneeAlea = (hauteur-1)*(int)(2*Math.random());	
					int abscisseAlea = 1+2*(int)((largeur-2)*Math.random()/2);
					this.labyrinthe[ordonneeAlea][abscisseAlea] = new Entree();
					this.mineur = new Mineur(abscisseAlea,ordonneeAlea);
				}
				else {
					int abscisseAlea = (largeur-1)*(int)(2*Math.random());	
					int ordonneeAlea = 1+2*(int)((hauteur-2)*Math.random()/2);
					this.labyrinthe[ordonneeAlea][abscisseAlea] = new Entree();
					this.mineur = new Mineur(abscisseAlea,ordonneeAlea);
				}

				//On stocke les coordonnes du mineur (et donc celle de l'entree) qui vont servir comme point de depart a l'alogrithme
				int x = this.mineur.getX();
				int y = this.mineur.getY();

				//Creation de deux piles vides, qui contiendront les coordonnes des cases successivement parcourues
				Stack<Integer> pileX = new Stack<>();
				Stack<Integer> pileY = new Stack<>();

				//On cree la branche principale du labyrinthe, qui appelera d'elle meme les branches annexes
				branche (x, y, pileX, pileY);

				//Algorithme de recherche de la sortie la plus eloignee possible du mineur ---> difficultee MAXIMAAALE
				//On teste toutes les cases qui jouxtent une case vide sur le "pourtour+1" du labyrinthe (pour pouvoir acceder a la sortie). Si on trouve une sortie dont le chemin qui le sépare au mineur est plus grand que longestPath, on enregistre la longueur du chemin, ainsi que les coordonnees de ladite sortie
				int longestPath = Integer.MIN_VALUE;
				//Tests des cases des bords horizontaux (haut et bas)
				for (int i = 1 ; i < this.getHauteur() ; i += this.getHauteur() - 3) {
					for (int j = 1 ; j < this.getLargeur() - 1 ; j++) {
						if (this.getLabyrinthe()[i][j].getEtat()) {
							int pathSize = this.solve(j,i).size();
							if (pathSize > longestPath) {
								longestPath = pathSize;
								abscisseSortie = j;
								ordonneeSortie = i;
							}
						}
					}
				}
				//Tests des cases des bards verticaux (droit et gauche)
				for (int j = 1 ; j < this.getLargeur() ; j += this.getLargeur() - 3) {
					for (int i = 1 ; i < this.getHauteur() - 1 ; i++) {
						if (this.getLabyrinthe()[i][j].getEtat()) {
							int pathSize = this.solve(j,i).size();
							if (pathSize > longestPath) {
								longestPath = pathSize;
								abscisseSortie = j;
								ordonneeSortie = i;
							}
						}
					}
				}

				//Si la sortie correspondait a une impasse, on retire cette impasse de la liste des impasses (en effet, il ne s'agit plus d'uune impasse puisque la sortie genere un "trou" dans le mur)
				for (int i = 0 ; i < coordsImpasses.size() ; i++) {
					if (coordsImpasses.get(i)[0] == abscisseSortie && coordsImpasses.get(i)[1] == ordonneeSortie) coordsImpasses.remove(i);
				}

				//On "rapatrie" la sortie sur le pourtour du labyrinthe en s'assurant que la sortie ne puisse pas se trouver dans l'un des coins du labyrinthe (inaccessibles au mineur)
				if (abscisseSortie == 1) {
					abscisseSortie = 0;
				}
				else {
					if (ordonneeSortie == 1) {
						ordonneeSortie = 0;
					}
					else {
						if (abscisseSortie == this.getLargeur()-2) {
							abscisseSortie = this.getLargeur()-1;
						}
						else {
							if (ordonneeSortie == this.getHauteur()-2) ordonneeSortie = this.getHauteur()-1;
						}	
					}
				}
				//On cree la case sortie
				this.labyrinthe[ordonneeSortie][abscisseSortie] = new Sortie();
				//A ce stade, le labyrinthe est genere. Toutes les traces utilisees pour la creation des branches sont remplacees par des cases vides
				for (int i = 0 ; i < this.getHauteur() ; i++) {
					for (int j = 0 ; j < this.getLargeur() ; j++) {				
						if (this.labyrinthe[i][j] instanceof Trace) this.labyrinthe[i][j] = new Case(' ');
					}
				}		
			}

			//Algorithme de creation de la clef : parmi les impasses recensees du labyrinthe, on insere aleatoirement une unique clef qui permettra d'ouvrir la porte
			Integer[] coordsClef = coordsImpasses.get((int)(coordsImpasses.size() * Math.random()));
			this.labyrinthe[coordsClef[1]][coordsClef[0]] = new Clef();		

			//Algorithme de creation de la porte : on commence par calculer les chemins qui permettent d'aller de l'entree a la sortie et de l'entree a la clef
			cheminVersSortie = this.solve(abscisseSortie, ordonneeSortie);
			cheminVersClef = this.solve(coordsClef[0], coordsClef[1]);
			//Puis on calcule le nombre de cases communes entre ces deux chemins
			nbCasesCommunes = 0;
			while (nbCasesCommunes < cheminVersClef.size() && cheminVersSortie.get(nbCasesCommunes)[0] == cheminVersClef.get(nbCasesCommunes)[0] && cheminVersSortie.get(nbCasesCommunes)[1] == cheminVersClef.get(nbCasesCommunes)[1]) {
				nbCasesCommunes++;
			}
			//Cela permet ensuite de venir choisir au hasard une case sur la chemin solution pour implanter la porte, tout en s'assurant que la clef rete accessible (la clef est du "bon cote" de la porte)
			int caseAleaPorte = nbCasesCommunes + (int)(Math.random() * ((cheminVersSortie.size() - nbCasesCommunes - 1)));
			this.labyrinthe[cheminVersSortie.get(caseAleaPorte)[1]][cheminVersSortie.get(caseAleaPorte)[0]] = new Porte();
		}

		//Algorithme de creation du nombre exact de filons. Tant que tous les filons n'ont pas ete poses, on recommence
		while (nbFilons != 0) {
			//On determine aleatoirement deux coordonnes au sein du labyrinthe
			int abscisseFilonAlea = 1+(int)((hauteur-2)*Math.random());
			int ordonneeFilonAlea = 1+(int)((largeur-2)*Math.random());
			//On cree une case de test
			Case lookAt = this.labyrinthe[abscisseFilonAlea][ordonneeFilonAlea];
			//Si la case de test est "libre", on y depose un filon et on decremente le nombre de filon
			if (!(lookAt instanceof Entree || lookAt instanceof Sortie || lookAt instanceof Filon || lookAt instanceof Clef  || lookAt instanceof Porte) && lookAt.getEtat() == true) {
				this.labyrinthe[abscisseFilonAlea][ordonneeFilonAlea] = new Filon();
				nbFilons--;
			}
		}
		//Fin de la generation aleatoire de labyrinthe
	}

	/**
	 * Constructeur du labyrinthe a partir d'un fichier texte. 
	 * Cree un tableau de cases de meme taille que le labyrinthe lu dans le fichier texte, cree un nouveau mineur aux coordonnees de l'entree lue, et initialise le nombre de coups a 0.
	 * 
	 * @param nomFichierTxt Nom du fichier texte a lire contenant le labyrinthe		
	 * 
	 * @see Labyrinthe#labyrinthe
	 * @see Labyrinthe#mineur
	 * @see Labyrinthe#nbCoups
	 */	
	public Labyrinthe (String nomFichierTxt) {
		this();

		//Determination de la taille du labyrinthe du fichier texte lu
		String ligneLue = new String();
		int hauteur = 0;
		int largeur = 0;

		try {
			BufferedReader aLire = new BufferedReader(new FileReader(nomFichierTxt));	
			while (ligneLue!=null){
				ligneLue = aLire.readLine();
				if (ligneLue != null) {
					if (ligneLue.length() > largeur) {
						largeur = ligneLue.length();
					}
					hauteur++;
				}
			}				
			aLire.close( );
		}
		catch (IOException e) { 
			System.out.println("Une operation sur les fichiers a leve l'exception "+e);
		}

		//Creation d'un labyrinthe vide de la taille exacte de celui lu
		this.labyrinthe = new Case[hauteur][largeur];

		//Recopie de tous les elements du labyrinthe du fichier texte dans l'objet cree precedemment, avec detection des erreurs de format
		try {
			BufferedReader aLire = new BufferedReader(new FileReader(nomFichierTxt));
			//Declaration et initialisation d'un indice temporaire de comptage de ligne et d'un tableau temporaire pour verifier la presence d'une entree, d'une sortie et d'au moins un filon
			int indiceLigne = 0;
			int[] present = {0,0,0};

			do {
				ligneLue = aLire.readLine();

				if (ligneLue != null) {	
					//Detection de lignes erronees qui comporteraient moins d'elements que d'autres
					if (ligneLue.length() != largeur) {
						Partie.affichageErreur(1);
					}

					for (int indiceCaractere = 0 ; indiceCaractere < largeur ; indiceCaractere++) {
						//Lecture de la case d'indice i sur la ligne actuelle
						char caractereLu = ligneLue.charAt(indiceCaractere);

						//Creation des murs et des couloirs			
						if (caractereLu == ' ' || caractereLu == '#') {
							this.labyrinthe[indiceLigne][indiceCaractere] = new Case(caractereLu);
						}
						else {
							//Creation des filons
							if (caractereLu == 'O') {
								this.labyrinthe[indiceLigne][indiceCaractere] = new Filon();
								present[2]++;
							}
							else {
								//Creation de l'entree et du mineur
								if (caractereLu == 'E') {
									this.labyrinthe[indiceLigne][indiceCaractere] = new Entree();
									present[0]++;
									//Creation du mineur aux coordonnees de la case d'entree
									this.mineur = new Mineur (indiceCaractere, indiceLigne); 
								}
								else {
									//Creation de la sortie
									if (caractereLu == 'S') {
										this.labyrinthe[indiceLigne][indiceCaractere] = new Sortie();
										present[1]++;
									}
									//Detection d'un caractere non repertorie
									else {
										Partie.affichageErreur(2);
									}
								}
							}
						}
					}
					indiceLigne++;
				}
			} while (ligneLue != null);

			//Verification qu'il y ait bien une seule entree, une seule sortie et un filon au minimum. Si tel n'est pas le cas, on affiche un pop-up d'erreur				
			if (present[0] != 1) {
				Partie.affichageErreur(3);
			}
			else {
				if (present[1] != 1) {
					Partie.affichageErreur(4);
				}
				else {
					if (present[2] == 0) {
						Partie.affichageErreur(5);
					}
				}
			}
			aLire.close( );
		}
		catch (IOException e) { 
			System.out.println("Une operation sur les fichiers a leve l'exception "+e);
		}
	}

	/**
	 * Trouve le chemin entre la case ou se trouve le mineur et la case objectif dont les coordonnees (X1, Y1) sont entrees en parametre.
	 * 
	 * @param X1 abscisse de la case cible dont on cherche le chemin qui y mene.
	 * @param Y1 ordonnee de la case cible dont on cherche le chemin qui y mene.
	 * @return une pile de couples d'entiers, qui contiennent les coordonnees des cases successives menant a l'objectif.
	 */
	public Stack<Integer[]> solve (int X1, int Y1) {
		//On cree une pile qui va comporter les coordonnees des cases successives menant a l'objectif
		Stack<Integer[]> pileSolution = new Stack<Integer[]>();

		//On recopie le labyrinthe actuel dans un labyrinthe temporaire
		Labyrinthe labyTemp = new Labyrinthe (this.getHauteur(), this.getLargeur());
		for (int i = 0 ; i < this.getHauteur() ; i++) {
			for (int j = 0 ; j < this.getLargeur() ; j++) {
				if (this.labyrinthe[i][j].getEtat()) {
					if (this.labyrinthe[i][j] instanceof Filon && ((Filon)this.labyrinthe[i][j]).getExtrait() == false) {
						labyTemp.labyrinthe[i][j] = new Filon();
					}
					else {
						labyTemp.labyrinthe[i][j] = new Case(' ');
					}
				}
				else {
					labyTemp.labyrinthe[i][j] = new Case('#');
				}
			}
		}

		//On enregistre les coordonnes du mineur en tant que point de depart de l'algorithme
		int x = this.mineur.getX();
		int y = this.mineur.getY();

		//On marque d'une trace la position actuele du mineur et on enregistre ces premieres coordonnees dans la solution
		labyTemp.labyrinthe[y][x] = new Trace();
		Integer[] coordsDepart = {x,y};
		pileSolution.add(coordsDepart);

		//Tant que l'on ne se trouve pas aux coordonnes de l'objectif
		while (x != X1 || y != Y1) {
			//On enregsitre une direction de deplacement a partir de la case actuelle
			int[] direction = labyTemp.direction (x, y, 1);

			//Si il y a effectivement une direction de deplacement possible...
			if (direction != null) {				
				//On se deplace vers la nouvelle case et on enregistre ses coordonnes, on enregistre ces coordonnees dans la solution puis on marque d'une trace la position actuelle
				x = x + direction[0];
				y = y + direction[1];
				Integer[] b = {x,y};
				pileSolution.add(b);
				labyTemp.labyrinthe[y][x] = new Trace();
			}
			//...Sinon, c'est que l'on est dans une impasse
			else {
				//On remplace le bout de l'impasse par un mur (on "mure" l'impasse), on depile de la solution la derniere case et on reprend les coordonnees de haut de pile
				labyTemp.labyrinthe[y][x] = new Case('#');
				pileSolution.pop();
				//Si la pile se trouve etre vide, c'est qu'il n'y a pas de chemin solution entre la position actuelle du mineur et la case cible. Alors on renvoie null
				if (pileSolution.size() == 0) return null;
				x = pileSolution.peek()[0];
				y = pileSolution.peek()[1];
			}
		}
		//Si l'algorithme se trouve aux coordonnes de la case obectif, on retourne la solution
		return pileSolution;
	}

	/**
	 * Determine les differentes directions possibles de deplacement a partir de la case ayant pour coordonnees (x,y), puis en choisit une parmis et la renvoie.
	 * 
	 * @param x :  abscisse de la case à partir de laquelle on veut determiner les deplacements possibles.
	 * @param y :  ordonnee de la case à partir de laquelle on veut determiner les deplacements possibles.
	 * @param b :  vaut 1 lorsque l'on cherche a poursuivre une branche en cours, ou 2 lorsque l'on cherche a creer une nouvelle branche.
	 * @return un vecteur caracterisant les directions du nouveau deplacement retenu.
	 */
	public int[] direction (int x, int y, int b) {
		ArrayList<Integer> X = new ArrayList<>();
		ArrayList<Integer> Y = new ArrayList<>();

		if (x-b >= 0 && (this.labyrinthe[y][x-b] instanceof Case && this.labyrinthe[y][x-b].getEtat() == true) && !(this.labyrinthe[y][x-b] instanceof Entree) && !(this.labyrinthe[y][x-b] instanceof Trace)) {
			X.add(-b);
			Y.add(0);
		}
		if (x+b < this.getLargeur() && (this.labyrinthe[y][x+b] instanceof Case && this.labyrinthe[y][x+b].getEtat() == true) && !(this.labyrinthe[y][x+b] instanceof Entree) && !(this.labyrinthe[y][x+b] instanceof Trace)) {
			X.add(b);
			Y.add(0);
		}
		if (y-b >= 0 && this.labyrinthe[y-b][x] instanceof Case && this.labyrinthe[y-b][x].getEtat() == true && !(this.labyrinthe[y-b][x] instanceof Entree) && !(this.labyrinthe[y-b][x] instanceof Trace)) {
			X.add(0);
			Y.add(-b);
		}
		if (y+b < this.getHauteur() && this.labyrinthe[y+b][x] instanceof Case && this.labyrinthe[y+b][x].getEtat() == true && !(this.labyrinthe[y+b][x] instanceof Entree) && !(this.labyrinthe[y+b][x] instanceof Trace)) {
			X.add(0);
			Y.add(b);
		}

		//Si les piles ne sont pas vides, cela signifie qu'il existe au moins une direction possible de déplacement. Alors on en choisit une aléatoirement. Sinon on renvoie null.
		if (X.size() != 0) {
			int alea = (int)(X.size()*Math.random());
			int[] directionFinale = {X.get(alea).intValue(),Y.get(alea).intValue()};
			return directionFinale;
		}
		else {
			return null;
		}
	}

	/**
	 * Construit les murs autour de la case de coordonnes (x,y), en fonction de la direction du deplacement de veteur n.
	 * 
	 * @param x : abscisse de la case que l'on veut entourer de murs.
	 * @param y : ordonnee de la case que l'on veut entourer de murs.
	 * @param n : vecteur caracterisant la direction du deplacement.
	 * @param z : vaut 1 lorsque la branche est en cours de construction, ou -1 lorsque l'on a atteint une impasse.
	 */
	public void murs (int x, int y, int[] n, int z) {
		if (n[1] == 0) {
			if (y-z >= 0 && !(this.labyrinthe[y-z][x] instanceof Trace) && !(this.labyrinthe[y-z][x] instanceof Entree)) {
				this.labyrinthe[y-z][x].setEtat(false);
			}
			if (y+z < this.getHauteur() && !(this.labyrinthe[y+z][x] instanceof Trace) && !(this.labyrinthe[y+z][x] instanceof Entree)) {
				this.labyrinthe[y+z][x].setEtat(false);
			}
			if (x-n[0] >= 0 && x-n[0] < this.getLargeur()) {
				if (y-z >= 0 && !(this.labyrinthe[y-z][x-n[0]] instanceof Trace) && !(this.labyrinthe[y-z][x-n[0]] instanceof Entree)) {
					this.labyrinthe[y-z][x-n[0]].setEtat(false);
				}
				if (!(this.labyrinthe[y][x-n[0]] instanceof Trace) && !(this.labyrinthe[y][x-n[0]] instanceof Entree)) {
					this.labyrinthe[y][x-n[0]].setEtat(false);
				}
				if (y+z < this.getHauteur() && !(this.labyrinthe[y+z][x-n[0]] instanceof Trace) && !(this.labyrinthe[y+z][x-n[0]] instanceof Entree)) {
					this.labyrinthe[y+z][x-n[0]].setEtat(false);
				}
			}
		}
		else {
			if (x-z >= 0 && !(this.labyrinthe[y][x-z] instanceof Trace) && !(this.labyrinthe[y][x-z] instanceof Entree)) {
				this.labyrinthe[y][x-z].setEtat(false);
			}
			if (x+z < this.getLargeur() && !(this.labyrinthe[y][x+z] instanceof Trace) && !(this.labyrinthe[y][x+z] instanceof Entree)) {
				this.labyrinthe[y][x+z].setEtat(false);
			}
			if (y-n[1] >= 0 && y-n[1] < this.getHauteur()) {
				if (x-z >= 0 && !(this.labyrinthe[y-n[1]][x-z] instanceof Trace) && !(this.labyrinthe[y-n[1]][x-z] instanceof Entree)) {
					this.labyrinthe[y-n[1]][x-z].setEtat(false);
				}
				if (!(this.labyrinthe[y-n[1]][x] instanceof Trace) && !(this.labyrinthe[y-n[1]][x] instanceof Entree)) {
					this.labyrinthe[y-n[1]][x].setEtat(false);
				}
				if (x+z < this.getLargeur() && !(this.labyrinthe[y-n[1]][x+z] instanceof Trace) && !(this.labyrinthe[y-n[1]][x+z] instanceof Entree)) {
					this.labyrinthe[y-n[1]][x+z].setEtat(false);
				}
			}
		}
	}

	/**
	 * Genere le labyrinthe de façon quasi recursive.
	 * 
	 * @param x : abscisse de la case a partir de laquelle la nouvelle branche debute.
	 * @param y : ordonnee de la case a partir de laquelle la nouvelle branche debute.
	 * @param pileX : pile contenant les abscisses des cases parcourues par l'algorithme.
	 * @param pileY : pile contenant les ordonnees des cases parcourues par l'algorithme.
	 */
	public void branche (int x, int y, Stack<Integer> pileX, Stack<Integer> pileY) {
		int[] n = {0,0};

		//Tant que l'on ne se trouve pas dans une impasse (ie. il existe une direction de deplacement possible a partir de la case actuelle), on fait progresser la branche
		while (direction(x, y, 1) != null) {		
			//On cherche une direction de deplacement a partir de la case actuelle
			n = direction(x, y, 1);

			//On construit les murs qui jouxtent la case actuelle, en fonction de la direction de deplacement (pour que les murs entourent bien la branche même lors des coudes)
			murs(x, y, n, 1);

			//On enregistre les coordonnes de la case nouvellement exploree
			x += n[0];
			y += n[1];

			//On declare la case actuelle comme etant parcourue
			labyrinthe[y][x] = new Trace();

			//On ajoute les coordonnes de cette case aux piles
			pileX.add(x);
			pileY.add(y);
		}

		//On construit les murs du fond de l'impasse et on ajoute les coordonnees de l'impasse a l'historique
		murs(x, y, n, -1);
		Integer[] coordsImpasse = {x,y};
		coordsImpasses.add(coordsImpasse);

		//On depile jusqu'à trouver une case a partir de laquelle un nouveau chemin peut etre cree
		int[] d = null;
		int dX = 0;
		int dY = 0;
		do {
			pileX.pop();
			pileY.pop();
			if (pileX.isEmpty() == false) {
				dX = pileX.peek();
				dY = pileY.peek();
				d = direction(dX, dY, 2);
			}				
		} while (d == null && pileX.isEmpty() == false);

		//Si la pile n'est pas vide et qu'une nouvelle direction de deplacement est possible depuis al case actuelle, alors on efface le mur qui borde la branche, et on cree une nouvelle branche a partir de cette case
		if (pileX.isEmpty() == false) {
			this.labyrinthe[dY+d[1]/2][dX+d[0]/2] = new Trace();
			pileX.add(dX+d[0]/2);
			pileY.add(dY+d[1]/2);
			this.labyrinthe[dY+d[1]][dX+d[0]] = new Trace();
			pileX.add(dX+d[0]);
			pileY.add(dY+d[1]);
			//Creation d'une nouvelle branche
			branche (dX+d[0], dY+d[1], pileX, pileY);
		}
		//Si la pile est vide, cela signifie que tout le labyrinthe a ete parcouru, c'est la fin de l'algorithme
	}

	/**
	 * Retourne le mineur.
	 * 
	 * @return L'objet mineur
	 * 
	 * @see Mineur
	 * @see Labyrinthe#mineur
	 */
	public Mineur getMineur () {
		return this.mineur;
	}

	/**
	 * Retourne la grille du labyrinthe.
	 * 
	 * @return Le labyrinthe sous forme du tableau de case
	 * 
	 * @see Case
	 * @see Labyrinthe#labyrinthe
	 */
	public Case[][] getLabyrinthe () {
		return this.labyrinthe;
	}

	/**
	 * Retourne la largeur du labyrinthe.
	 * 
	 * @return La largeur de la grille de labyrinthe sous forme d'un entier
	 */
	public int getLargeur () {
		return this.labyrinthe[1].length;
	}

	/**
	 * Retourne la hauteur du labyrinthe.
	 * 
	 * @return La hauteur de la grille de labyrinthe sous forme d'un entier
	 */
	public int getHauteur () {
		return this.labyrinthe.length;
	}

	/**
	 * Retourne le nombre de coups.
	 * 
	 * @return Le nombre de coups depuis le debut de la partie sous forme d'un entier
	 * 
	 * @see Labyrinthe#nbCoups
	 */
	public int getNbCoups () {
		return this.nbCoups;
	}

	/**
	 * Renvoie les coordonnees de la sortie.
	 * 
	 * @return un vecteur d'entiers composé des coordonnees de la case, null si il n'y en a plus.
	 */
	public int[] getCoordsSortie () {
		for (int i = 0 ; i < this.getHauteur() ; i++) {
			for (int j = 0 ; j < this.getLargeur() ; j++) {
				if (this.getLabyrinthe()[i][j] instanceof Sortie) {
					int[] coordsSortie = {0,0};
					coordsSortie[0] = j;
					coordsSortie[1] = i;
					return coordsSortie;
				}
			}
		}
		return null;
	}

	/**
	 * Renvoie les coordonnees de la clef.
	 * 
	 * @return un vecteur d'entiers composé des coordonnees de la case, null si il n'y en a plus.
	 */
	public int[] getCoordsClef () {
		for (int i = 0 ; i < this.getHauteur() ; i++) {
			for (int j = 0 ; j < this.getLargeur() ; j++) {
				if (this.getLabyrinthe()[i][j] instanceof Clef) {
					int[] coordsClef = {0,0};
					coordsClef[0] = j;
					coordsClef[1] = i;
					return coordsClef;
				}
			}
		}
		return null;
	}
	/**
	 * Deplace le mineur en fonction de la direction entree par l'utilisateur, en gerant les collisions et en incrementant le nombre de coups quand necessaire.
	 * 
	 * @param c Caractere qui precise la direction entree par l'utilisateur
	 */
	public void deplacerMineur (char c) {
		//Variables temporaires representant les coordonnees du mineur, pour plus de commodite
		int x = this.mineur.getX();
		int y = this.mineur.getY();

		//Gestion des deplacements du mineur en fonction de la commande entree par le joueur, avec gestion des collisions et comptage du nombre de coups
		switch (c) {

		case 'g':
			if (x > 0 && this.labyrinthe[y][x-1].getEtat() == true) {
				this.mineur.setX(x - 1);		
				this.nbCoups++;
			}
			break;

		case 'd':
			if (1 + x < this.getLargeur() && this.labyrinthe[y][x+1].getEtat() == true) {
				this.mineur.setX(x + 1);
				this.nbCoups++;
			}
			break;

		case 'h':
			if (y > 0 && this.labyrinthe[y-1][x].getEtat() == true) {
				this.mineur.setY(y - 1);
				this.nbCoups++;
			}
			break;

		case 'b':
			if (1 + y < this.getHauteur() && this.labyrinthe[y+1][x].getEtat() == true) {
				this.mineur.setY(y + 1);
				this.nbCoups++;
			}
			break;

		default:
			System.out.println("erreur sur la direction demandee");
			break;
		}
	}
}
