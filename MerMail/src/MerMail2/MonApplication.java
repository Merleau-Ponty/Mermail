package miniMessagerie;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.Character.Subset;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;
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

import miniMessagerie.Compte;
import miniMessagerie.GCompte;
import miniMessagerie.MonApplication;
import miniMessagerie.panneauSelectionCompte;
import miniMessagerie.Message;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

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

  private JLabel status = new JLabel("Pr�t...");
  private JTextArea Jbody = new JTextArea(10,50);
  private Vector messages = new Vector();
  private Vector messages2 = new Vector();	//ajout perso
  public static JMenuBar menuBar = new JMenuBar();
  static GCompte gC = new GCompte();
  public static Base maBase = new Base();
  public MonApplication () throws ClassNotFoundException, SQLException
  {
    JMenuBar menuBar = new JMenuBar();

    JPanel statusbar = new JPanel();
    JPanel coeur = new JPanel();
    JScrollPane SPbody, SPlist;
    /* -----------------------Base maBase = new Base() --------------------------------- */
    Base maBase = new Base();
    maBase.getConnexion();
    maBase.write(courant);
    maBase.close();

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

    // GUI: LISTE DES MAILS
    // TODO: IMPL�MENTER L'OBJET GRAPHIQUE DANS GMESSAGE AU LIEU DE L'APPLICATION
    JPanel coeur = new JPanel();
    JScrollPane SPbody, SPlist;
    String[] faux_messages = { "Message 1" , "Message 2", "Message 3" };
    final JList msgList = new JList(faux_messages);
    coeur.setLayout(new BorderLayout());

    SPbody = new JScrollPane(Jbody,
                             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                             JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    SPlist = new JScrollPane(msgList,
                             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                             JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    coeur.add(SPlist, BorderLayout.WEST);
    coeur.add(SPbody, BorderLayout.EAST);

    Message m = new Message("a", "b", "a@k?fr", "mlpo", "ihuiyg", "14/05/1865", 12345, 1);

    setVisible(true);

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
				oo.flush();
				oo.close();
				System.out.println("Info: Compte(s) sauv�(s)");

			}
			catch (IOException f)
			{
				System.out.println("Erreur : L'enregistrement des donn�es � �chou�. Veuillez recommencez ult�rieurement.");
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
				System.out.println("Erreur : L'enregistrement des donn�es � �chou�. Veuillez recommencez ult�rieurement.");
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
            try {
				ajoutComptePop();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
        	try {
				defComptePop();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
}});

    msgList.addMouseListener(new MouseAdapter() {


		public void mouseClicked(MouseEvent e) {
        	Message leMessage = new Message();
        	int ch;
        	StringBuffer strContent = new StringBuffer("");
            int index = msgList.locationToIndex(e.getPoint());
   //         if ( index >= 0 && index < messages.size())
   //        	Jbody.setText(((PopMessage)messages.elementAt(index)).message);
            try {
				FileInputStream fi = new FileInputStream("mail_source_mermail.txt");
				while((ch = fi.read()) != -1)	{
					strContent.append((char)ch);
				}
				fi.close();
				String leMsg = strContent.toString();
				String[] arrMsg = leMsg.split("stop|\\n");
				String ArrMsgLine = "";
				String from = "From:";
				String to = "To:";
				String from_adrr = "Return-Path:";
				String subject = "Subject:";
				String date = "Date:";
				int line_start = 0;
				int line_end = 0;
				String contenu = "";

				for (int i = 0; i < arrMsg.length; i++) {
					if(arrMsg[i].startsWith(from))	{
						leMessage.setExpediteur(arrMsg[i].substring(from.length()).trim());
					}
					if(arrMsg[i].startsWith(to))	{
						leMessage.setDestinataire(arrMsg[i].substring(to.length()).trim());
					}
					if(arrMsg[i].startsWith(from_adrr))	{
						leMessage.setAdresse_destinataire(arrMsg[i].substring(from_adrr.length()).trim());
					}
					if(arrMsg[i].startsWith(subject))	{
						leMessage.setObjet(arrMsg[i].substring(subject.length()).trim());
					}
					if(arrMsg[i].startsWith(date))	{
						leMessage.setDate(arrMsg[i].substring(date.length()).trim());
					}
					if(arrMsg[i].startsWith("<html>"))	{
						line_start = i;
						System.out.println("s" + line_start);
					}
					if(arrMsg[i].startsWith("</html>"))	{
						line_end = i;
						System.out.println("e" + line_end);
					}

					leMessage.setTaille(strContent.length()*8);
					ArrMsgLine += i + " - " + arrMsg[i] + "\r\n";
				}
				System.out.println((line_end - line_start));
				for(int compt = line_start; compt < (line_end - line_start)+line_start; compt++)	{
					contenu += arrMsg[compt];
				}
				leMessage.setContenu(contenu);
				Jbody.setText(leMessage.toString());
				System.out.println(leMessage.toString());



			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    });

    pack();

  }



  protected void ajoutComptePop() throws ClassNotFoundException
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
  		//listeComptes.add(temp);
  		maBase.sauverEnBase(temp.getTitre(), temp.getUser(),temp.getPassword(),temp.getHost(),temp.getPort());

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
  protected void defComptePop() throws ClassNotFoundException, SQLException
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
				// A changer pour faire le lien vers la classe BASE
				//FileOutputStream fo = new FileOutputStream("data2.dat");
				//DataOutputStream Do = new DataOutputStream(fo);
				maBase.lireEnBase();
			}
			catch (Exception e)
			{
				System.out.println("marche po :( , echec du chargement des donn�es");
				//System.exit(0);
			}

		}
  	}
  }

  public static void main ( String args[] ) throws ClassNotFoundException, SQLException
  {

  	try
	{
		FileInputStream fi = new FileInputStream("Data.dat");
		ObjectInputStream oi = new ObjectInputStream(fi);
		listeComptes = (ArrayList) oi.readObject();
		ResultSet rs=maBase.lireEnBase();
		listeComptes.add(new Compte(rs.getString("titre"),rs.getString("users"),rs.getInt("port"),rs.getString("serveur"),rs.getString("passwords")));
		System.out.println(listeComptes);
	}
	catch (IOException e)
	{
		System.out.println("Erreur: �chec du chargement des donn�es");
		//System.exit(0);
	}
	catch (ClassNotFoundException se)
	{
		System.out.println("Erreur: Classe non trouv�");
	}


	try
	{
		//////////////////////////////////////////
		FileInputStream fi = new FileInputStream("data2.dat");//Ouvre le fichier
		courant = fi.read();
		//////////////////////////////////////////

		//lire.lireEnBase(String titre, String users, String passwords, String serveur, int port);
	}
	catch (IOException e)
	{
		System.out.println("Erreur: �chec du chargement des donn�es");
		//System.exit(0);
	}


    (new MonApplication()).setVisible(true);

  }

}
