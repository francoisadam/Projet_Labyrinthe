import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Stack;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <b>Partie est la classe "executable" du jeu.</b>
 * <p>
 * Une partie est caracterisee par :
 * <ul>
 * <li>un labyrinthe </li>
 * <li>des dimensions de labyrinthe si le labyrinthe est genere aleatoirement </li>
 * <li>un nom de fichier si le labyrinthe est cree depuis un fichier </li>
 * <li>un theme d'affichage </li>
 * <li>une fenetre graphique permettant l'affichage du labyrinthe et l'ecoute des commandes clavier</li>
 * </ul>
 * 
 * @see Labyrinthe
 * 
 * @author Benjamin RANCINANGUE et Francois ADAM
 * @version 3.3
 */
public class Partie extends JFrame implements KeyListener {	

	private static final long serialVersionUID = -7288046948465832666L;
	private Container conteneur = new JPanel();
	private Container grille = new JPanel();
	private JButton boutonRecommencer = new JButton();
	private JButton boutonAide = new JButton();
	private JButton boutonInfos = new JButton();
	private JLabel enTete = new JLabel("=====< Bienvenue ! >=====");
	private static char touche = ' ';
	private Labyrinthe laby;
	private static Partie partie = new Partie();
	private int hauteur = 0;
	private int largeur = 0;
	private int nbFilons = 0;
	private String fichier = "";
	private int themeJeu = 5;

	/**
	 * Constructeur d'une partie.
	 **/
	public Partie() {
		//Definit le titre de la fenetre
		this.setTitle("Labyrinthe"); 												
		//Empeche le redimensionnement de la fenetre
		this.setResizable(false);										
		//Quitte le processus lors de la fermeture de la fenetre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					

		//Creation d'un JPanel contenant les trois boutons de la fenetre de jeu
		JPanel panneauBoutons = new JPanel();
		panneauBoutons.add(boutonRecommencer);
		panneauBoutons.add(boutonAide);
		panneauBoutons.add(boutonInfos);

		//Parametrage de l'en-tete afin qu'il au centre sur un fond opaque
		enTete.setHorizontalAlignment(JLabel.CENTER);
		enTete.setOpaque(true);		

		//Ajout des icônes dans les boutons
		boutonRecommencer.setIcon(new ImageIcon(getClass().getResource("/images/undo.png")));
		boutonAide.setIcon(new ImageIcon(getClass().getResource("/images/aide.png")));
		boutonInfos.setIcon(new ImageIcon(getClass().getResource("/images/info.png")));

		//Organisation des differents blocs de la fenetre
		conteneur = getContentPane();		
		conteneur.setLayout(new BorderLayout());
		conteneur.add(grille, BorderLayout.CENTER);

		//Ajout des differents ecouteurs d'action clavier et souris
		boutonRecommencer.addActionListener(new boutonRecommencerListener());
		boutonAide.addActionListener(new boutonAideListener());
		boutonInfos.addActionListener(new boutonInfosListener());

		//Parametrage de la fenetre graphique
		grille.setFocusable(true);
		grille.addKeyListener(this);
		grille.setVisible(true);

		//Ajout des composants a la fenetre
		conteneur.add(enTete, BorderLayout.NORTH);
		conteneur.add(panneauBoutons, BorderLayout.SOUTH);				

		//Changement de l'icone et affichage de la fenetre		
		setIconImage(new ImageIcon(getClass().getResource("/images/icone.gif")).getImage());	
	}

	/**
	 * Demande au joueur le type de jeu, ce qui lancera une partie en fonction.
	 * 
	 * @param args ?
	 */
	public static void main(String[] args) {
		partie.choixDeJeu();
	}

	/**
	 * Affiche une boite de dialogue permettant de choisir le type de partie ainsi que le theme, et affiche si besoin des fenetres afin de regler les dimensions et le nombre de filons du labyrinthe.
	 **/
	public void choixDeJeu () {
		//Affichage d'une fenetre qui permet de choisir entre un labyrinthe genere aleatoirement et un fichier txt
		String[] choixPossibles = {"Generer un labyrinthe aleatoirement", "Ouvrir un labyrinthe en .txt"};
		String choix = (String)JOptionPane.showInputDialog(null, "Que voulez-vous faire ?", "Labyrinthe", JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/images/icone.gif")), choixPossibles, choixPossibles[0]);
		
		//Si le bouton "Annuler" n'est pas presse
		if (choix != null) {
			//Affichage d'une fenetre qui permet de choisir le theme du jeu
			String[] choixTheme = {"Theme par defaut", "Theme d'ancien labyrinthe en pierre", "Theme de The legend of zelda", "Theme de Métroid", "Theme d'incendie dans un batiment", "Theme de Mario"};
			String theme = (String)JOptionPane.showInputDialog(null, "Quel theme voulez-vous ?", "Choix du theme", JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/images/icone.gif")), choixTheme, choixTheme[1]);

			//Si le bouton "Annuler" n'est pas presse
			if (theme != null) {
				
				//Enregistrement du theme et changement de la couleur de fond de la fenetre.	
				switch (theme) {
				case "Theme par defaut" : 
					themeJeu = 0;
					grille.setBackground(new Color(91, 60, 17));
					break;
				case "Theme de The legend of zelda" : 
					themeJeu = 2; 
					grille.setBackground(Color.BLACK);
					break;
				case "Theme de Métroid" : 
					themeJeu = 3; 
					grille.setBackground(Color.white);
					break;
				case "Theme d'incendie dans un batiment" : 
					themeJeu = 4; 
					grille.setBackground(new Color(61, 43, 31));
					break;
				case "Theme de Mario" : 
					themeJeu = 5; 
					grille.setBackground(Color.cyan);
					break;
				default :
					themeJeu = 1;
					grille.setBackground(new Color(69, 69, 69));
					break;
				}

				//Si l'utilisateur a choisi d'ouvrir un fichier texte
				if (choix.equals("Ouvrir un labyrinthe en .txt")) {				
					//On ouvre un explorateur qui permet de ne selectionner que des fichiers .txt
					JFileChooser exploreur = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
					exploreur.setFileFilter(filter);
					exploreur.setAcceptAllFileFilterUsed(false);
					int valeurRetournee = exploreur.showOpenDialog(null);

					//Si le fichier est correct, on enregistre le nom de ce dernier, et on initie une partie.
					if (valeurRetournee == JFileChooser.APPROVE_OPTION) {
						partie.fichier = exploreur.getSelectedFile().getPath();
						partie.largeur = 0;
						partie.hauteur = 0;
						partie.nbFilons = 0;
						initialisation();
					}
					//Sinon, on quitte le programme.
					if (valeurRetournee == JFileChooser.CANCEL_OPTION) {
						partie.quitter();
					}
				}
				//Si l'utilisateur a choisi de generer un labyrinthe aleatoirement
				else {
					//On cree une nouvelle fenetre
					JFrame fenetreDimensions = new JFrame();

					//On cree un premier panneau qui contient un texte et un slider afin de regler la hauteur du labyrinthe
					JPanel panneauHauteur = new JPanel();
					JLabel labelHauteur = new JLabel("25 cases :");
					JSlider sliderHauteur = new JSlider();				
					sliderHauteur.setMaximum(21);
					sliderHauteur.setMinimum(6);
					sliderHauteur.setValue(13);
					sliderHauteur.addChangeListener(new ChangeListener(){
						//Action liee au deplacement du slider
						public void stateChanged(ChangeEvent event){
							labelHauteur.setText(2*((JSlider)event.getSource()).getValue()-1 + " cases :");
						}
					});
					panneauHauteur.setBorder(BorderFactory.createTitledBorder("Hauteur du labyrinthe"));
					panneauHauteur.add(labelHauteur);
					panneauHauteur.add(sliderHauteur);

					//On cree un second panneau qui contient un texte et un slider afin de regler la largeur du labyrinthe
					JPanel panneauLargeur = new JPanel();
					JLabel labelLargeur = new JLabel("25 cases :");
					JSlider sliderLargeur = new JSlider();
					sliderLargeur.setMaximum(38);
					sliderLargeur.setMinimum(6);
					sliderLargeur.setValue(13);
					sliderLargeur.addChangeListener(new ChangeListener(){
						public void stateChanged(ChangeEvent event){
							//Action liee au deplacement du slider
							labelLargeur.setText(2*((JSlider)event.getSource()).getValue()-1 + " cases :");
						}
					});
					panneauLargeur.setBorder(BorderFactory.createTitledBorder("Largeur du labyrinthe"));
					panneauLargeur.add(labelLargeur);
					panneauLargeur.add(sliderLargeur);

					//On cree un bouton "Continuer"
					JButton boutonContinuer = new JButton("Continuer");
					boutonContinuer.addActionListener(new ActionListener() {
						//Action liee a l'appui sur le bouton "continuer"
						public void actionPerformed(ActionEvent arg0) {
							//On efface la fenetre precedente et on en recree une
							fenetreDimensions.dispose();
							JFrame fenetreFilons = new JFrame();

							//On enregistre les valeurs des sliders precedents
							partie.largeur = 2*sliderLargeur.getValue()-1;
							partie.hauteur = 2*sliderHauteur.getValue()-1;

							//On cree un nouveau panneau qui contient un texte et un slider afin de regler le nombre de filons
							JPanel panneauFilons = new JPanel();
							JLabel labelFilons = new JLabel(1 + " filon(s) :");
							JSlider sliderFilons = new JSlider();
							sliderFilons.setMinimum(0);
							sliderFilons.setMaximum((partie.largeur*partie.hauteur-partie.largeur-partie.hauteur+3)/4);
							sliderFilons.setValue(1);
							sliderFilons.setMajorTickSpacing((partie.largeur*partie.hauteur-partie.largeur-partie.hauteur+3)/8);
							sliderFilons.setPaintTicks(true);
							sliderFilons.setPaintLabels(true);
							sliderFilons.addChangeListener(new ChangeListener(){
								public void stateChanged(ChangeEvent event) {
									//Action liee au deplacement du slider
									labelFilons.setText(((JSlider)event.getSource()).getValue() + " filons :");
								}
							});
							panneauFilons.setBorder(BorderFactory.createTitledBorder("Nombre de filons"));	
							panneauFilons.add(labelFilons);
							panneauFilons.add(sliderFilons);	

							//On cree un bouton "Jouer"
							JButton boutonJouer = new JButton("Jouer");
							boutonJouer.addActionListener(new ActionListener() {
								//Action liee a l'appui sur le bouton "jouer"
								public void actionPerformed(ActionEvent arg0) {
									fenetreFilons.dispose();
									partie.nbFilons = sliderFilons.getValue();
									initialisation();
								}
							});

							//On cree un bouton "Annuler"
							JButton annulerFilons = new JButton("Annuler");
							annulerFilons.addActionListener(new ActionListener() {
								//Action liee a l'appui sur le bouton "jouer"
								public void actionPerformed(ActionEvent arg0) {
									fenetreFilons.dispose();
									partie.quitter();
								}
							});

							//On cree un panneau recueillant deux bontons
							JPanel boutonsFilons = new JPanel();
							boutonsFilons.add(boutonJouer);
							boutonsFilons.add(annulerFilons);	

							//On parametre la fenetre et agence les differents panneaux, puis on l'affiche
							fenetreFilons.setSize(320, 140);
							fenetreFilons.setTitle("Choix du nombre de filons");
							fenetreFilons.setIconImage(new ImageIcon(getClass().getResource("/images/icone.gif")).getImage());
							fenetreFilons.setLocationRelativeTo(null);
							fenetreFilons.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							fenetreFilons.getContentPane().add(panneauFilons, BorderLayout.CENTER);
							fenetreFilons.getContentPane().add(boutonsFilons, BorderLayout.SOUTH);
							fenetreFilons.setVisible(true);
						}
					});

					//On cree un bouton "Annuler"
					JButton annulerDimension = new JButton("Annuler");
					annulerDimension.addActionListener(new ActionListener() {
						//Un appui sur le bouton "annuler" quitte le jeu
						public void actionPerformed(ActionEvent arg0) {
							fenetreDimensions.dispose();
							partie.quitter();
						}
					});

					//On cree un panneau recueillant deux bontons
					JPanel boutonsDimensions = new JPanel();
					boutonsDimensions.add(boutonContinuer);
					boutonsDimensions.add(annulerDimension);	

					//On parametre la fenetre et agence les differents panneaux, puis on l'affiche
					fenetreDimensions.setSize(575, 120);
					fenetreDimensions.setTitle("Choix des dimensions du labyrinthe");
					fenetreDimensions.setIconImage(new ImageIcon(getClass().getResource("/images/icone.gif")).getImage());
					fenetreDimensions.setLocationRelativeTo(null);
					fenetreDimensions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					fenetreDimensions.getContentPane().add(panneauHauteur, BorderLayout.EAST);
					fenetreDimensions.getContentPane().add(panneauLargeur, BorderLayout.WEST);
					fenetreDimensions.getContentPane().add(boutonsDimensions, BorderLayout.SOUTH);
					fenetreDimensions.setVisible(true);
				}
			}
			//Un appui sur le bouton "annuler" quitte le jeu
			else {
				partie.quitter();
			}
		}
		//Un appui sur le bouton "annuler" quitte le jeu
		else {
			partie.quitter();
		}
	}

	/**
	 * Initialise une partie.
	 */
	public void initialisation () {
		//Affichage d'un message d'accueil
		System.out.println("========================================= BIENVENUE ! ====================================\n");

		//Initialisation de la touche clavier
		Partie.touche = ' ';

		//Operations effectuees si le mode de jeu "labyrinthe txt" a ete choisi
		if (partie.fichier != "") {
			//Creation d'un labyrinthe a partir d'un fichier txt
			partie.laby = new Labyrinthe (partie.fichier);
		}
		//Creation d'un labyrinthe aux dimensions definies si le mode de jeu aleatoire a ete choisi
		else {			
			partie.laby = new Labyrinthe(partie.hauteur, partie.largeur, partie.nbFilons);
		}

		grille.setLayout(new GridLayout(this.laby.getHauteur(),this.laby.getLargeur()));

		//Definition de la taille de la fenetre et affichage
		grille.setPreferredSize(new Dimension(16*partie.laby.getLargeur(),16*partie.laby.getHauteur()));
		partie.pack();
		partie.setLocationRelativeTo(null);
		partie.setVisible(true);	

		//Lance le jeu
		partie.actionsTouches();
	}

	/**
	 * Affiche un message d'adieu et ferme la fenetre. Fin du programme.
	 */
	public void quitter () {
		System.out.println("\n========================================= AU REVOIR ! ====================================");
		partie.dispose();
	}

	/**
	 * Gere le deroulement d'une partie, en fonction des entrees claviers et souris.
	 */
	public void actionsTouches () {
		//Gestion des deplacements du mineur si demande
		if (Partie.touche == 'g' || Partie.touche == 'd' || Partie.touche == 'h' || Partie.touche == 'b') deplacements();

		//Affichage du labyrinthe et des instructions, puis attente de consignes clavier.
		partie.affichageLabyrinthe();

		//Quitte la partie si demande.
		if (Partie.touche == 'q') partie.quitter();

		//Trouve et affiche une solution si demande.
		if (Partie.touche == 's') affichageSolution();

		//Recommence la partie si demande.
		if (Partie.touche == 'r') {
			grille.removeAll();
			partie.initialisation();
		}

		//Affichage de l'aide si demande
		if (Partie.touche == 'a') {
			String texteAide = new String();
			switch(themeJeu) {
			case 2 : texteAide = "Le but du jeu est d'aider Link à trouver la sortie du donjon tout en récupérant le(s) coffre(s).\n Link doit egalement recuperer la Master Sword qui permet de tuer le monstre bloquant le chemin.\n\nLink se déplace à l'aide des touches directionnelles.\nUne solution peut-être affichée en appuyant sur la touche (s).\nLa touche (r) permet de recommencer, (q) de quitter.\n\n Bon jeu !"; break;
			case 3 : texteAide = "Le but du jeu est d'aider Samus à trouver la sortie du vaisseau tout en récupérant le(s) émeraude(s).\nSamus doit egalement recuperer la bombe qui permet de tuer le metroid qui bloque l'accès à la sortie.\n\nSamus se déplace à l'aide des touches directionnelles.\nUne solution peut-être affichée en appuyant sur la touche (s).\nLa touche (r) permet de recommencer, (q) de quitter.\n\n Bon jeu !"; break;
			case 4 : texteAide = "Le but du jeu est d'aider le pompier à trouver la sortie du batiment tout en sauvant le(s) rescapé(s).\nLe pompier doit egalement recuperer l'extincteur qui permet d'éteindre le feu qui bloque l'accès à la sortie.\n\nLe pompier se déplace à l'aide des touches directionnelles.\nUne solution peut-être affichée en appuyant sur la touche (s).\nLa touche (r) permet de recommencer, (q) de quitter.\n\n Bon jeu !"; break;
			case 5 : texteAide = "Le but du jeu est d'aider Mario à trouver le drapeau de sortie tout en ramassant le(s) pièce(s).\nMario doit egalement recuperer l'étoile d'invincibilité qui permet de se débarasser du Goomba qui l'empêche de sortir.\n\nMario se déplace à l'aide des touches directionnelles.\nUne solution peut-être affichée en appuyant sur la touche (s).\nLa touche (r) permet de recommencer, (q) de quitter.\n\n Here we gooo !"; break;
			default : texteAide = "Le but du jeu est d'aider le mineur à trouver la sortie du labyrinthe tout en extrayant le(s) filon(s).\nLe mineur doit egalement recuperer la clef qui permet l'ouverture de le porte qui bloque l'accès à la sortie.\n\nLe mineur se déplace à l'aide des touches directionnelles.\nUne solution peut-être affichée en appuyant sur la touche (s).\nLa touche (r) permet de recommencer, (q) de quitter.\n\n Bon jeu !"; break;
			}
			System.out.println("\n============================================ AIDE ========================================\n\n" + texteAide + "\n\n==========================================================================================\n");
			JOptionPane.showMessageDialog(null, texteAide, "Aide", JOptionPane.QUESTION_MESSAGE);
			Partie.touche = ' ';
		}

		//Affichage de les infos si demande
		if (Partie.touche == 'i') {
			System.out.println("\n============================================ INFOS =======================================\n\nCe jeu a ete developpe par Francois ADAM et Benjamin Rancinangue\ndans le cadre du projet IPIPIP 2015.\n\n==========================================================================================\n");
			Partie.touche = ' ';
			JOptionPane.showMessageDialog(null, "Ce jeu a été développé par François Adam et Benjamin Rancinangue\ndans le cadre du projet IPIPIP 2015.", "Infos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/EMN.png")));
		}

		//Nettoyage de l'ecran de console
		System.out.println("\n==========================================================================================\n");
	}

	/**
	 * Gere toutes les consequences que peut avoir un deplacement du mineur au sein du labyrinthe.
	 */
	public void deplacements () {
		//Efface de la fenetre le mineur
		((JLabel)grille.getComponents()[this.laby.getMineur().getY()*this.laby.getLargeur()+this.laby.getMineur().getX()]).setIcon(this.laby.getLabyrinthe()[this.laby.getMineur().getY()][this.laby.getMineur().getX()].imageCase(themeJeu));
		//Deplace et affiche le mineur suivant la touche pressee
		partie.laby.deplacerMineur(Partie.touche);
		Partie.touche = ' ';

		//Operations effectuees si la case ou se trouve le mineur est une sortie
		if (partie.laby.getLabyrinthe()[partie.laby.getMineur().getY()][partie.laby.getMineur().getX()] instanceof Sortie) {
			//On verifie en premier lieu que tous les filons ont ete extraits
			boolean tousExtraits = true;							
			for (int i = 0 ; i < partie.laby.getHauteur() && tousExtraits == true ; i++) {
				for (int j = 0 ; j < partie.laby.getLargeur() && tousExtraits == true ; j++) {
					if (partie.laby.getLabyrinthe()[i][j] instanceof Filon) {
						tousExtraits = ((Filon)partie.laby.getLabyrinthe()[i][j]).getExtrait();								
					}
				}
			}
			//Si c'est le cas alors la partie est terminee et le joueur peut recommencer ou quitter, sinon le joueur est averti qu'il n'a pas recupere tous les filons
			if (tousExtraits == true) {
				partie.affichageLabyrinthe ();
				System.out.println("\nFelicitations, vous avez trouvé la sortie, ainsi que tous les filons en " + partie.laby.getNbCoups() + " coups !\n\nQue voulez-vous faire à present : [r]ecommencer ou [q]uitter ?");
				String[] choixPossiblesFin = {"Quitter", "Recommencer"};
				int choixFin = JOptionPane.showOptionDialog(null, "Felicitations, vous avez trouve la sortie, ainsi que tous les filons en " + partie.laby.getNbCoups() + " coups !\n\nQue voulez-vous faire a present :", "Fin de la partie", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choixPossiblesFin, choixPossiblesFin[0]);
				if ( choixFin == 1) {
					Partie.touche = 'r';
				}
				else {
					Partie.touche = 'q';
				}					
			}
			else {
				partie.enTete.setText("Tous les filons n'ont pas ete extraits !");
			}
		}
		else {
			//Si la case ou se trouve le mineur est un filon qui n'est pas extrait, alors ce dernier est extrait.
			if (partie.laby.getLabyrinthe()[partie.laby.getMineur().getY()][partie.laby.getMineur().getX()] instanceof Filon && ((Filon)partie.laby.getLabyrinthe()[partie.laby.getMineur().getY()][partie.laby.getMineur().getX()]).getExtrait() == false) {
				((Filon)partie.laby.getLabyrinthe()[partie.laby.getMineur().getY()][partie.laby.getMineur().getX()]).setExtrait();
				System.out.println("\nFilon extrait !");
			}
			//Sinon si la case ou se trouve le mineur est une clef, alors on indique que la clef est ramassee, puis on cherche la porte et on l'efface de la fenetre, avant de rendre la case quelle occupe vide
			else {
				if (partie.laby.getLabyrinthe()[partie.laby.getMineur().getY()][partie.laby.getMineur().getX()] instanceof Clef && ((Clef)partie.laby.getLabyrinthe()[partie.laby.getMineur().getY()][partie.laby.getMineur().getX()]).getRamassee() == false) {
					((Clef)partie.laby.getLabyrinthe()[partie.laby.getMineur().getY()][partie.laby.getMineur().getX()]).setRamassee();
					int[] coordsPorte = {-1,-1};
					for (int i = 0 ; i < this.laby.getHauteur() && coordsPorte[1] == -1 ; i++) {
						for (int j = 0 ; j < this.laby.getLargeur() && coordsPorte[1] == -1 ; j++) {
							if (this.laby.getLabyrinthe()[i][j] instanceof Porte) {							
								coordsPorte[0] = j;
								coordsPorte[1] = i;
							}
						}
					}
					partie.laby.getLabyrinthe()[coordsPorte[1]][coordsPorte[0]].setEtat(true);
					((JLabel)grille.getComponents()[coordsPorte[1]*this.laby.getLargeur()+coordsPorte[0]]).setIcon(this.laby.getLabyrinthe()[coordsPorte[1]][coordsPorte[0]].imageCase(themeJeu));
					System.out.println("\nClef ramassee !");
				}
			}
		}
	}

	/**
	 * Affiche les differents codes d'erreurs detectees via un pop-up et interrompt le programme.
	 * 
	 * @param codeErreur Entier qui caracterise l'erreur
	 */
	public static void affichageErreur (int codeErreur) {
		System.out.println("\n\n!!!!!!!!!! ATTENTION !!!!!!!!!!\n");
		String messageErreur;

		switch (codeErreur) {
		case 1 : messageErreur = "Une ligne comporte moins d'elements que les autres !"; break;
		case 2 : messageErreur = "Un caractere inconnu a ete detecte !"; break;
		case 3 : messageErreur = "Le labyrinthe n'a pas une seule entree !"; break;
		case 4 : messageErreur = "Le labyrinthe n'a pas une seule sortie !"; break;
		case 5 : messageErreur = "Le labyrinthe n'a pas de filon !"; break;
		case 6 : messageErreur = "Trop de filons ont été demandés par rapport au nombre de cases vides du labyrinthe !"; break;
		default : messageErreur = "Erreur non repertoriee."; break;
		}

		System.out.println(messageErreur+"\nLe programme va s'interrompre.");
		JOptionPane.showMessageDialog(null, messageErreur, "Erreur", JOptionPane.ERROR_MESSAGE);		
		System.exit(0);
	}

	/**
	 * Affiche le labyrinthe ainsi que le mineur sur la console et sur la fenetre graphique.
	 */
	public void affichageLabyrinthe () {
		//Affiche le nombre de coups actuel sur la console et sur la fenetre graphique
		System.out.println("Nombre de coups : " + this.laby.getNbCoups() + "\n");		
		this.enTete.setText("Nombre de coups : " + this.laby.getNbCoups());

		//Affichage dans la fenêtre et dans la console du labyrinthe case par case, et quand la case est celle ou se trouve le mineur, affiche ce dernier
		for (int i = 0 ; i < this.laby.getHauteur() ; i++) {
			String ligne = new String();
			for (int j = 0 ; j < this.laby.getLargeur() ; j++) {
				if (i != this.laby.getMineur().getY() || j != this.laby.getMineur().getX()) {
					ligne = ligne + this.laby.getLabyrinthe()[i][j].graphismeCase();					
				}
				else {
					this.laby.getLabyrinthe()[i][j]=new Case();
					ligne = ligne + this.laby.getMineur().graphismeMineur();
				}				
				if (grille.getComponentCount() < this.laby.getLargeur()*this.laby.getHauteur()) {
					grille.add(new JLabel());
					((JLabel)grille.getComponents()[i*this.laby.getLargeur()+j]).setIcon(this.laby.getLabyrinthe()[i][j].imageCase(themeJeu));
				}
			}
			System.out.println(ligne);
		}
		((JLabel)grille.getComponents()[this.laby.getMineur().getY()*this.laby.getLargeur()+this.laby.getMineur().getX()]).setIcon(this.laby.getMineur().imageCase(themeJeu));
	}

	/**
	 *Trouve et affiche une solution vers le plus proche filon ou la sortie, a defaut.
	 */
	public void affichageSolution() {
		//On commence par retirer toutes les traces pré-existantes du labyrinthe
		for (int i = 0 ; i < this.laby.getHauteur() ; i++) {
			for (int j = 0 ; j < this.laby.getLargeur() ; j++) {
				if (this.laby.getLabyrinthe()[i][j] instanceof Trace) {
					this.laby.getLabyrinthe()[i][j] = new Case();
					((JLabel)grille.getComponents()[i*this.laby.getLargeur()+j]).setIcon(this.laby.getLabyrinthe()[i][j].imageCase(themeJeu));
				}
			}
		}

		//On parcourt toutes les cases du labyrinthe. Si on trouve un filon non extrait dont le chemin qui le sépare au mineur est plus petit que shortestPath, on enregistre la longueur du chemin ainsi que les coordonnees de ledit filon
		int shortestPath = Integer.MAX_VALUE;
		int[] coordsNearestFilon = {-1,-1};
		for (int i=0 ; i < this.laby.getHauteur() ; i++) {
			for (int j=0 ; j < this.laby.getLargeur() ; j++) {
				if (this.laby.getLabyrinthe()[i][j] instanceof Filon && ((Filon)this.laby.getLabyrinthe()[i][j]).getExtrait() == false) {
					if (this.laby.solve(j,i) != null) {
						int pathSize = this.laby.solve(j,i).size();
						if (pathSize < shortestPath) {
							shortestPath = pathSize;
							coordsNearestFilon[0] = j;
							coordsNearestFilon[1] = i;
						}
					}
				}
			}
		}

		//Si il n'y a plus de filon non extrait atteignable, on cherche les coordonnes de la clef
		if (coordsNearestFilon[0] == -1) {
			coordsNearestFilon = this.laby.getCoordsClef();
			//Si il n'y a plus de filon non extrait atteignable et que la clef a deja ouvert la porte, on cherche les coordonnes de la sortie
			if (coordsNearestFilon == null)	coordsNearestFilon = this.laby.getCoordsSortie();
		}

		//On cree une pile qui contient des couples de coordonnees qui correspondent a la solution, puis on depile car le dernier element est l'objectif vise
		Stack<Integer[]> solution = this.laby.solve(coordsNearestFilon[0], coordsNearestFilon[1]);
		solution.pop();

		//Tant que l'on n'arrive pas au premier element de la pile (cad la case ou se trouve le mineur), on depile tout en gardant l'element depile, qui contient les coordonnees d'une trace que l'on dessine en suivant dans la fenetre
		while (solution.size() != 1) {
			Integer[] coordsTmp = solution.pop();
			Trace traceTmp = new Trace();
			this.laby.getLabyrinthe()[coordsTmp[1]][coordsTmp[0]] = new Trace();
			((JLabel)grille.getComponents()[coordsTmp[1]*this.laby.getLargeur()+coordsTmp[0]]).setIcon(traceTmp.imageCase());
		}
		System.out.println("\n========================================== SOLUTION =====================================\n");
		this.affichageLabyrinthe();
	}

	/**
	 * Actions executees si une touche est tapee puis relachee. Attribut le caractere de la touche a l'attribut touche afin de provoquer par ailleurs l'action desiree.
	 */
	public void keyPressed (KeyEvent evenementClavier) {	

		switch (evenementClavier.getKeyCode()) {		
		case KeyEvent.VK_A:
			Partie.touche = 'a';
			break;	
		case KeyEvent.VK_I:
			Partie.touche = 'i';
			break;
		case KeyEvent.VK_Q:
			Partie.touche = 'q';
			break;
		case KeyEvent.VK_R:
			Partie.touche = 'r';
			break;	
		case KeyEvent.VK_LEFT:
			Partie.touche = 'g';
			break;				
		case KeyEvent.VK_RIGHT:
			Partie.touche = 'd';
			break;				
		case KeyEvent.VK_UP:
			Partie.touche = 'h';
			break;				
		case KeyEvent.VK_DOWN:
			Partie.touche = 'b';
			break;
		case KeyEvent.VK_S:
			Partie.touche = 's';
			break;	
		default:
			Partie.touche = ' ';
			break;
		}

		if (Partie.touche != ' ') actionsTouches();
	}

	/**
	 * Actions executees si une touche clavier est relachee
	 */
	public void keyReleased (KeyEvent arg0) {
	}

	/**
	 * Actions executees si une touche clavier est tapee
	 */
	public void keyTyped (KeyEvent arg0) {
	}

	/**
	 * Actions executees si une action est effectuee sur le bouton "Recommencer". Affiche une fenetre pop-up demandant au joueur de recommencer ou non, puis agit en conséquence.
	 */
	class boutonRecommencerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {		
			int choixRecommencer = JOptionPane.showConfirmDialog(null, "Voulez-vous recommencer un labyrinthe ?", "Recommencer", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if(choixRecommencer == JOptionPane.OK_OPTION){
				Partie.touche = 'r';
				partie.actionsTouches();
			}
			grille.requestFocus();
		}
	}

	/**
	 * Actions executees si une action est effectuee sur le bouton "Aide". Attribut le carcatere 'a' a l'attribut touche ce qui a pour effet d'afficher le pop-up d'aide.
	 */
	class boutonAideListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Partie.touche = 'a';
			grille.requestFocus();
			partie.actionsTouches();
		}
	}

	/**
	 * Actions executees si une action est effectuee sur le bouton "Info". Attribut le carcatere 'i' a l'attribut touche ce qui a pour effet d'afficher le pop-up d'info.
	 */
	class boutonInfosListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Partie.touche = 'i';
			grille.requestFocus();
			partie.actionsTouches();
		}
	}
}
