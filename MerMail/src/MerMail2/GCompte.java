package MerMail2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

@SuppressWarnings("serial")
public class GCompte extends JPanel
{
  JTextField titre, hostTF, portTF, userTF;
  JPasswordField passwordTF;
  Compte monCompte;
  
  public static ArrayList<Compte> listeComptes = MonApplication.listeComptes;
  public static int courant = MonApplication.courant;
  
  // Menu compte 
  JMenu compteMenu = new JMenu("Comptes");
  JMenuBar monMenu = new JMenuBar();
  
  	
  
  
  public GCompte()
  {
    monMenu = MonApplication.menuBar;
    monMenu.add(compteMenu);
    
    // Sous-menus
 	  JMenuItem ajoutCompteItem = new JMenuItem("Ajout d'un compte");
 	  compteMenu.add(ajoutCompteItem);
 	  JMenuItem modifCompteItem = new JMenuItem("Modification d'un compte");
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
		     	modifComptePop();
		}});		 
		 supprCompteItem.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent e){
		     	supprComptePop();
		}});		 
		 defCompteItem.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent e){
		     	defComptePop();
		}});
  }
  public Compte getCompteCourant()	{
	// Retourne l'objet Compte qui est le compte courant  
	return listeComptes.get(courant);
	  
  }
  
  protected void ajoutComptePop()
  {
  	GCompte monPanneau = new GCompte();
  	int retour = JOptionPane.showConfirmDialog(
			this,
			monPanneau,
		    "enregistrement d'un compte",
		   	JOptionPane.OK_CANCEL_OPTION,
		    JOptionPane.PLAIN_MESSAGE
			);
  	
  	if(retour == JOptionPane.OK_OPTION)
	{
  		Compte temp = monPanneau.getCompte();
  		listeComptes.add(temp);
	}
  }
  protected void modifComptePop()
  {
  	if(listeComptes.size()==0)
  	{
  		JOptionPane.showMessageDialog(this, "<html>Il n'existe aucun compte</html>");
  	}
  	else
  	{

  		panneauSelectionCompte monPanneauSelection = new panneauSelectionCompte();
  		int retour = JOptionPane.showConfirmDialog(
  					this,
					monPanneauSelection,
					"modification d'un compte",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE
					);
	
		if(retour == JOptionPane.OK_OPTION)
		{
			Compte temp = (Compte)listeComptes.get(monPanneauSelection.getIndexSelection());
			GCompte monPanneau = new GCompte(temp);
			int retour2 = JOptionPane.showConfirmDialog(
					this,
					monPanneau,
					"enregistrement d'un compte",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE
					);
	  	
			if(retour2 == JOptionPane.OK_OPTION)
			{
				Compte temp2 = monPanneau.getCompte();
				listeComptes.set(monPanneauSelection.getIndexSelection(), temp2);
				
				
			}
		
		}
  	}
  }

  protected void supprComptePop()
  {
  	if(listeComptes.size()==0)
  	{
  		JOptionPane.showMessageDialog(this, "<html>Il n'existe aucun compte</html>");
  	}
  	else
  	{
  		panneauSelectionCompte monPanneau = new panneauSelectionCompte();
  		int retour = JOptionPane.showConfirmDialog(
					this,
		    		monPanneau,
		    		"supppression d'un compte",
					JOptionPane.YES_NO_OPTION,
		    		JOptionPane.PLAIN_MESSAGE
					);
	
		if(retour == JOptionPane.OK_OPTION)
		{
			listeComptes.remove(monPanneau.getIndexSelection());
			if(courant == monPanneau.getIndexSelection())
			{
				courant = 0;
			}
			else if(courant > monPanneau.getIndexSelection())
			{
				courant--;
			}
			
		
		}
  	}
  }
  protected void defComptePop(){
	  listeComptes = MonApplication.listeComptes;
	  	if(listeComptes.size()==0)
	  	{
	  		JOptionPane.showMessageDialog(this, "<html>Il n'existe aucun compte</html>");
	  	}
	  	else
	  	{
	  		panneauSelectionCompte monPanneau = new panneauSelectionCompte();
	  		Compte temp2 = (Compte)listeComptes.get(courant);
	  		int retour = JOptionPane.showConfirmDialog(
						this,
			    		monPanneau,
			    		"compte actuel: "+temp2,
						JOptionPane.YES_NO_OPTION,
			    		JOptionPane.PLAIN_MESSAGE
						);
		
			if(retour == JOptionPane.OK_OPTION)
			{
				courant = monPanneau.getIndexSelection();
				Compte temp = (Compte)listeComptes.get(courant);
				/*
				popServer = temp.getHost();
				user = temp.getUser();
				password = temp.getPassword();
				port = temp.getPort();
				*/
				try
				{
					FileOutputStream fo = new FileOutputStream("data2.dat");
					DataOutputStream Do = new DataOutputStream(fo);
					fo.write(courant);
					fo.close();
				}
				catch (IOException e)
				{
					System.out.println("Erreur: échec du chargement des données (data2.dat) MonApplication.java");
					//System.exit(0);
				}
			
			}
	  	}
	  }
  
  class panneauSelectionCompte extends JPanel implements ActionListener
  {
  	@SuppressWarnings("rawtypes")
  	JComboBox listeCompte = new JComboBox();
  	JLabel titre = new JLabel();
  	JLabel host = new JLabel();

  	@SuppressWarnings("unchecked")
  	public panneauSelectionCompte()
  	{
  		this.setLayout(new GridLayout(4, 1));
  		for (int i=0; i < MonApplication.listeComptes.size(); i++)
  		{
  			listeCompte.addItem(MonApplication.listeComptes.get(i));
  		}

  		add(listeCompte);
  		add(titre);
  		add(host);
  		//initialisation
  		listeCompte.setSelectedIndex(0);
  		Compte temp = (Compte)MonApplication.listeComptes.get(0);
  		titre.setText("Titre: "+temp.getTitre());
  		host.setText("Host: "+temp.getHost());

  		//capture d'evenement
  		listeCompte.addActionListener(this);


  	}
  	
  	public void actionPerformed(ActionEvent evt)
  	{
  		Object src = evt.getSource();
  		// Recuperer les informations au compte sï¿½lï¿½ctionnï¿½
  		if(src == listeCompte)
  		{
  			Compte temp = (Compte)MonApplication.listeComptes.get(listeCompte.getSelectedIndex());
  			titre.setText("titre: "+temp.getTitre());
  			host.setText("host: "+temp.getHost());

  		}
  	}

  	public int getIndexSelection()
  	{
  		return listeCompte.getSelectedIndex();
  	}

  }

  // Compte temporaire.
  public GCompte(final Compte temp)
  {
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

    hostTF = new JTextField(monCompte.getHost(), 20);
    portTF = new JTextField("" + monCompte.getPort(), 5);
    panel3.add(new JLabel("Server"));
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
    panel2.add(new JLabel("User"));
    panel2.add(userTF);
    panel2.add(new JLabel("Passwd"));
    panel2.add(passwordTF);

  }
  // Recuper le compte
  public Compte getCompte()
  {
  	monCompte.setTitre(titre.getText());
  	monCompte.setHost(hostTF.getText());
  	monCompte.setUser(userTF.getText());
  	monCompte.setPassword(new String(passwordTF.getPassword()));
  	monCompte.setPort(Integer.parseInt(portTF.getText()));
  	return monCompte;
  }
}