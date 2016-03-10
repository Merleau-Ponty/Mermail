package MerMail2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import MerMail2.Compte;
import MerMail2.GCompte;
import MerMail2.MonApplication;
import MerMail2.panneauSelectionCompte;
import MerMail2.Message;

@SuppressWarnings("serial")
public class MonApplication extends JFrame
{
public static ArrayList<Compte> listeComptes = new ArrayList<Compte>();
static int courant;

  
/* 
  //Compte GMAIL PPE
  private String popServer = "pop.gmail.com";
  private String user = "ppe.merleau" , password = "ppe@2016";
  private int port = 110;
*/

  private JLabel status = new JLabel("Prêt...");
  private JTextArea Jbody = new JTextArea(10,50);
  private Vector messages = new Vector();
  private Vector messages2 = new Vector();	//ajout perso
  private JList msgList = new JList();

  public MonApplication ()
  {
    JMenuBar menuBar = new JMenuBar();

    JPanel statusbar = new JPanel();
    JPanel coeur = new JPanel();
    JScrollPane SPbody, SPlist;

    coeur.setLayout(new BorderLayout());

    Container contentPane = this.getContentPane();
    contentPane.setLayout(new BorderLayout());

    setJMenuBar(menuBar);

    JMenu fMenu = new JMenu("Fichiers");
    menuBar.add(fMenu);
    JMenu compteMenu = new JMenu("Comptes");
    menuBar.add(compteMenu);

    //JMenuItem popServerItem = new JMenuItem("SetServer", 2);
    //fMenu.add(popServerItem);
    
    JMenuItem popMsgItem = new JMenuItem("Lire Messages", 2);
    fMenu.add(popMsgItem);
    fMenu.addSeparator();
    JMenuItem exitItem = new JMenuItem("Sortie", 2);
    fMenu.add(exitItem);
    
    JMenuItem ajoutCompteItem = new JMenuItem("ajout d'un compte");
    compteMenu.add(ajoutCompteItem);
    JMenuItem modifCompteItem = new JMenuItem("modification d'un compte");
    compteMenu.add(modifCompteItem);
    JMenuItem supprCompteItem = new JMenuItem("suppression d'un compte");
    compteMenu.add(supprCompteItem);
    compteMenu.addSeparator();
    JMenuItem defCompteItem = new JMenuItem("choix du compte courant");
    compteMenu.add(defCompteItem);

    statusbar.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
    statusbar.setBorder(new BevelBorder(BevelBorder.LOWERED));
    contentPane.add(statusbar,BorderLayout.SOUTH );
    statusbar.add(status);


    SPbody = new JScrollPane(Jbody,
                             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                             JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    SPlist = new JScrollPane(msgList,
                             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                             JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    coeur.add(SPlist, BorderLayout.WEST);
    coeur.add(SPbody, BorderLayout.EAST);

    contentPane.add(coeur);


    // add listeners for the window and the various buttons
    this.addWindowListener( new WindowAdapter(){
         @SuppressWarnings("resource")
		public void windowClosing(WindowEvent e){
         	try
			{
				FileOutputStream fo = new FileOutputStream("Data.dat");
				ObjectOutputStream oo = new ObjectOutputStream(fo);
				oo.writeObject(listeComptes);
			}
			catch (IOException f)
			{
				System.out.println("Erreur : L'enregistrement des données à échoué. Veuillez recommencez ultérieurement.");
			}
              setVisible(false);
              dispose();
              System.exit(0);
    }});

    exitItem.addActionListener( new ActionListener(){
         @SuppressWarnings("resource")
		public void actionPerformed(ActionEvent e){
         	try
			{
				FileOutputStream fo = new FileOutputStream("Data.dat");
				ObjectOutputStream oo = new ObjectOutputStream(fo);
				oo.writeObject(listeComptes);
			}
			catch (IOException f)
			{
				System.out.println("Erreur : L'enregistrement des données à échoué. Veuillez recommencez ultérieurement.");
			}
              System.exit(0);
    }});

/*
   popMsgItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            getMessages();
    }});
*/
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

    msgList.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            int index = msgList.locationToIndex(e.getPoint());
            if ( index >= 0 && index < messages.size())
   //        	Jbody.setText(((PopMessage)messages.elementAt(index)).message);
          Jbody.setText(((String)messages2.elementAt(index)));
        }
    });

    pack();

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
  protected void defComptePop()
  {
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
				//DataOutputStream Do = new DataOutputStream(fo);
				fo.write(courant);
				fo.close();
			}
			catch (IOException e)
			{
				System.out.println("marche po :( , echec du chargement des données");
				//System.exit(0);
			}
		
		}
  	}
  }

  public static void main ( String args[] )
  {
  	
  	try
	{
		FileInputStream fi = new FileInputStream("Data.dat");
		ObjectInputStream oi = new ObjectInputStream(fi);
		listeComptes = (ArrayList) oi.readObject();
		
	}
	catch (IOException e)
	{
		System.out.println("Erreur: Échec du chargement des données");
		//System.exit(0);
	}
	catch (ClassNotFoundException se)
	{
		System.out.println("Erreur: Classe non trouvé");
	}
	
	
	try
	{
		FileInputStream fi = new FileInputStream("data2.dat");
		courant = fi.read();
		
	}
	catch (IOException e)
	{
		System.out.println("Erreur: Échec du chargement des données");
		//System.exit(0);
	}
	
	
    (new MonApplication()).setVisible(true);

  }

}