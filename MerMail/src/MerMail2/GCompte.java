package MerMail2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.ResultSet;

import MerMail2.Compte;
import MerMail2.GCompte;

@SuppressWarnings("serial")
public class GCompte extends JPanel	{
  JTextField titre, hostTF, portTF, userTF;
  JPasswordField passwordTF;
  Compte monCompte;
  
  public static ArrayList<Compte> listeComptes = MonApplication.listeComptes;
  public static int courant = MonApplication.courant;
  
  // Menu compte 
  JMenu compteMenu = new JMenu("Comptes");
  JMenuBar monMenu = new JMenuBar();
  
  // BDD
  private Base maBase = MonApplication.maBase;

  
  private JMenuItem modifCompteItem = new JMenuItem("Modification du compte courant");
  
  public GCompte()	{
    monMenu = MonApplication.menuBar;
    monMenu.add(compteMenu);
    //maBase = MonApplication.InitDB(maBase);
    
    
    // Sous-menus
 	  JMenuItem ajoutCompteItem = new JMenuItem("Ajout d'un compte");
 	  compteMenu.add(ajoutCompteItem);
 	  if(maBase.getCourant() != null){
 		 JMenuItem modifCompteItem = new JMenuItem("Modification du compte courant (" + maBase.getCourant().getTitre()+ ")");  
 	  }	else	{
 		 JMenuItem modifCompteItem = new JMenuItem("Choix du compte courant");
 	  }
 	  
 	  compteMenu.add(modifCompteItem);
 	  JMenuItem supprCompteItem = new JMenuItem("Suppression d'un compte");
 	  compteMenu.add(supprCompteItem);
 	  compteMenu.addSeparator();
 	  JMenuItem defCompteItem = new JMenuItem("Choix du compte courant");
 	  compteMenu.add(defCompteItem);
 	  
		 ajoutCompteItem.addActionListener(new ActionListener(){
		         public void actionPerformed(ActionEvent e){
		         ajoutComptePop();
		}});		 
		 modifCompteItem.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent e){
		     	try {
					modifComptePop();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}});		 
		 supprCompteItem.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent e){
		     	try {
					supprComptePop();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}});		 
		 defCompteItem.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent e){
		     	try {
					defComptePop();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}});
  }
  public Compte getCompteCourant()	{
	// Retourne l'objet Compte qui est le compte courant  
	return listeComptes.get(courant);
	  
  }
  
  protected void ajoutComptePop(){
	  // PROD: Le compte doit etre vide.
  	GCompte monPanneau = new GCompte(new Compte("Mermail", "pop.laposte.net", 110, "mermail@laposte.net", "P@ssw0rd",false));
  	int retour = JOptionPane.showConfirmDialog(
			this,
			monPanneau,
		    "Nouveau Compte",
		   	JOptionPane.OK_CANCEL_OPTION,
		    JOptionPane.PLAIN_MESSAGE
			);
  	
  	
  	if(retour == JOptionPane.OK_OPTION)
	{
  		System.out.println("Tentative d'ajout de compte");
  		Compte temp = monPanneau.getCompte();
  		try {
  			boolean succesSauvegarde = true;
  			
  			succesSauvegarde = maBase.sauverEnBase(temp.getTitre(), temp.getUser(),temp.getPassword(),temp.getHost(),temp.getPort());
  			
  			if(succesSauvegarde)	{
  	  	  		System.out.println("Compte " + temp.getTitre() + " ajouté.");
  	  	  		MonApplication.listeComptes = maBase.lireEnBase();
  	  	  		MonApplication.cc = temp;
  	  	  	}	else	{
  	  	  		System.out.println("Impossible d'ajouter le compte");
  	  	  	}
  	  	  	System.out.println("Il y a maintenant: " + listeComptes.size() + " comptes dans <listeComptes>");

  	  	  	maBase.close();

  	  	  			
  			}	catch (Exception e) {
  				System.err.println("Erreur: " + e + "\r\nDétails: " + e.getMessage());
  			}
  		
	}
  }
  protected void modifComptePop() throws ClassNotFoundException	{
	  if(maBase.getCourant() == null)
		  JOptionPane.showMessageDialog(this, "<html>Il n'y a pas de compte courant</html>");
	  else	{
	  	Compte Compte_a_modifier = maBase.getCourant();
	  	System.out.println("A modifier: " + Compte_a_modifier.getTitre());
	  	GCompte monPanneau = new GCompte(Compte_a_modifier);
	  	Compte oldCompte = maBase.getCourant();
	  	String oldTitre = oldCompte.getTitre();
	  	int retour = JOptionPane.showConfirmDialog(
				this,
				monPanneau,
			    "Modification d'un compte",
			   	JOptionPane.OK_CANCEL_OPTION,
			    JOptionPane.PLAIN_MESSAGE
				);
	  	if(retour == JOptionPane.OK_OPTION)	{
	  		Compte temp = monPanneau.getCompte();
	  		maBase.Modification(oldTitre, temp.getTitre(), temp.getUser(),temp.getPassword(),temp.getHost(),temp.getPort());
	  		MonApplication.listeComptes = maBase.lireEnBase();
		}
	  }
  }
  protected void supprComptePop() throws ClassNotFoundException
  {
  
	  panneauSelectionCompte monPanneau = new panneauSelectionCompte();
	  	int retour = JOptionPane.showConfirmDialog(
				this,
				monPanneau,
			    "Suppression d'un compte",
			   	JOptionPane.OK_CANCEL_OPTION,
			    JOptionPane.PLAIN_MESSAGE
				);
	  	
	  	if(retour == JOptionPane.OK_OPTION)
		{
	  		Compte temp = monPanneau.getCompte();
	  		maBase.Suppression(temp.getTitre());
	  		
		}
  }
  protected void defComptePop() throws ClassNotFoundException, SQLException	{
  	if(listeComptes.size()==0)	{
  		JOptionPane.showMessageDialog(this, "<html>Il n'existe aucun compte</html>");
  	}	else if(listeComptes.size() == 1){
  		panneauSelectionCompte monPanneau = new panneauSelectionCompte();
  		int retour = JOptionPane.showConfirmDialog(
					this,
		    		monPanneau,
		    		"Choisir un compte courant",
					JOptionPane.YES_NO_OPTION,
		    		JOptionPane.PLAIN_MESSAGE
					);
	
		if(retour == JOptionPane.OK_OPTION)	{
			//maBase.setCourant(oldTitre, false);
			maBase.setCourant(monPanneau.getCompte().getTitre(), true);
			MonApplication.cc = maBase.getCompte(monPanneau.getCompte().getTitre());
			System.out.println(monPanneau.getCompte().getTitre() + " est le nouveau cc! (1)");
			try	{
				maBase.lireEnBase();
			}	catch (Exception e)	{
				System.out.println("Erreur: " + e + "\r\n " + e.getMessage());
			}
		
		}
  	}	else if(listeComptes.size() > 1 && MonApplication.cc != null)	{
  		Compte oldCompte = MonApplication.cc;
  		String oldTitre = oldCompte.getTitre();
  		panneauSelectionCompte monPanneau = new panneauSelectionCompte();
  		int retour = JOptionPane.showConfirmDialog(
					this,
		    		monPanneau,
		    		"Compte actuel: "+ oldTitre,
					JOptionPane.YES_NO_OPTION,
		    		JOptionPane.PLAIN_MESSAGE
					);
	
		if(retour == JOptionPane.OK_OPTION)	{
			maBase.setCourant(oldTitre, false);
			maBase.setCourant(monPanneau.getCompte().getTitre(),true);
			MonApplication.cc = maBase.getCompte(monPanneau.getCompte().getTitre());
			System.out.println(monPanneau.getCompte().getTitre() + " est le nouveau cc! (2)");
			try	{
				maBase.lireEnBase();
			}	catch (Exception e)	{
				System.out.println("Erreur: " + e + "\r\n " + e.getMessage());
			}
		}
  	}	else if(MonApplication.cc == null)	{

  		panneauSelectionCompte monPanneau = new panneauSelectionCompte();
  		int retour = JOptionPane.showConfirmDialog(
					this,
		    		monPanneau,
		    		"Choix compte courant",
					JOptionPane.YES_NO_OPTION,
		    		JOptionPane.PLAIN_MESSAGE
					);
	
		if(retour == JOptionPane.OK_OPTION)	{
			maBase.setCourant(monPanneau.getCompte().getTitre(),true);
			MonApplication.cc = maBase.getCompte(monPanneau.getCompte().getTitre());
			System.out.println(monPanneau.getCompte().getTitre() + " est le nouveau cc! (3)");
			try	{
				maBase.lireEnBase();
			}	catch (Exception e)	{
				System.out.println("Erreur: " + e + "\r\n " + e.getMessage());
			}
		}
  	}
  	}

  
  
  class panneauSelectionCompte extends JPanel implements ActionListener	{
	  
  	@SuppressWarnings("rawtypes")
  	JComboBox listeCompte = new JComboBox();
  	JLabel titre = new JLabel();
  	JLabel host = new JLabel("Host: ");

  	// CONSTRUCTEUR SANS PARAMETRE
  	@SuppressWarnings("unchecked")
  	public panneauSelectionCompte()	{

  		this.setLayout(new GridLayout(4, 1));
  		for (int i=0; i < MonApplication.listeComptes.size(); i++)	{
  			listeCompte.addItem(MonApplication.listeComptes.get(i).getTitre());
  		}

  		add(listeCompte);
  		add(titre);
  		add(host);
  		//initialisation
  		listeCompte.setSelectedIndex(0);
  		Compte temp = MonApplication.listeComptes.get(0);
  		titre.setText("Titre: "+temp.getTitre());
  		host.setText("Host: "+temp.getHost());

  		// Capture d'événement
  		listeCompte.addActionListener(this);


  	}
  	
  	public void actionPerformed(ActionEvent evt)	{
  		Object src = evt.getSource();
  		// Recuperer les informations du compte sélectionnée
  		if(src == listeCompte)	{
  			Compte temp = MonApplication.listeComptes.get(listeCompte.getSelectedIndex());
  			titre.setText("Titre: "+temp.getTitre());
  			host.setText("Host: "+temp.getHost());
  		}
  	}
  	public Compte getCompte()	{
  		Compte c = MonApplication.listeComptes.get(listeCompte.getSelectedIndex());
  		return c;
  	}
  	public int getIndexSelection()	{
  		return listeCompte.getSelectedIndex();
  	}
  	

  }

  // Compte temporaire.
  public GCompte(Compte temp)	{
    super();
    this.monCompte = temp;

    JPanel panel1, panel2, panel3;

    this.setLayout(new BorderLayout());
    panel1 = new JPanel();
    panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
    this.add(panel1, BorderLayout.SOUTH);

    panel3 = new JPanel();
    panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
    this.add(panel3, BorderLayout.CENTER);
    
    if(MonApplication.cc != null){
    	hostTF = new JTextField("" + monCompte.getHost(), 20);
        portTF = new JTextField("" + monCompte.getPort(), 5);
        panel3.add(new JLabel("Serveur"));
        panel3.add(hostTF);
        panel3.add(new JLabel("Port"));
        panel3.add(portTF);
        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(panel2, BorderLayout.NORTH);
        titre = new JTextField(monCompte.getTitre(), 12);
        userTF = new JTextField(monCompte.getUser(), 12);
        passwordTF = new JPasswordField(monCompte.getPassword(), 10);
        panel2.add(new JLabel("Titre"));
        panel2.add(titre);
        panel2.add(new JLabel("Utilisateur"));
        panel2.add(userTF);
        panel2.add(new JLabel("Mot de passe"));
        panel2.add(passwordTF);
    }
    if(MonApplication.cc == null){
    	
    }
    
    

    

  }
  // Recuper le compte
  public Compte getCompte()	{
  	monCompte.setTitre(titre.getText());
  	monCompte.setHost(hostTF.getText());
  	monCompte.setUser(userTF.getText());
  	monCompte.setPassword(new String(passwordTF.getPassword()));
  	monCompte.setPort(Integer.parseInt(portTF.getText()));
  	return monCompte;
  }
}