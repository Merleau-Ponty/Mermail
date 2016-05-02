package MerMail2;
// Imports
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.MenuBar;
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
import MerMail2.Message;

@SuppressWarnings("serial")
public class MonApplication extends JFrame
{
	// Liste des compte accessible par toute les classes
	public static ArrayList<Compte> listeComptes = new ArrayList<Compte>();
	// m�thode 1: index du compte courant dans la listeComptes
	static int courant;
	// m�thode 2: � finir. D�finir le meme principe mais avec un objet Compte (cc)
	public static Compte cc = null;
	public Compte compteDefault = new Compte("MerMail", "pop.laposte.net", 110, "mermail", "p@ssw0rd");
  


  private JLabel status = new JLabel("Pr�t...");
  private JTextArea Jbody = new JTextArea(10,50);
  private Vector messages = new Vector();
  private Vector messages2 = new Vector();	//ajout perso  
  public static JMenuBar menuBar = new JMenuBar();
  static GCompte gC = new GCompte();

  public MonApplication ()
  {
	// INSTANCIATION GCOMPTE
	listeComptes.add(compteDefault);
	compteDefault.setCourant(true);
	final GMessage gM = new GMessage();
	  
	// GUI: GET CONTENT PANE
	Container contentPane = this.getContentPane();
	contentPane.setLayout(new BorderLayout());
	    
	// GUI: MENU
	JMenu fMenu = new JMenu("Fichiers");
	menuBar.add(fMenu);
	setJMenuBar(menuBar);
	JMenuItem popMsgItem = new JMenuItem("Lire Messages", 2);
    fMenu.add(popMsgItem);
    fMenu.addSeparator();
    JMenuItem exitItem = new JMenuItem("Quitter", 2);
    fMenu.add(exitItem);
	
	// GUI: STATUS BAR
    JPanel statusbar = new JPanel();
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
				System.out.println("Erreur : L'enregistrement des donn�es � �chou�. Veuillez recommencez ult�rieurement." + f.getMessage());
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


   popMsgItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            gM.getMessages();
    }});

    

    msgList.addMouseListener(new MouseAdapter() {
        

		public void mouseClicked(MouseEvent e) {
        	Message leMessage = new Message(); // Message vide
        	int ch;
        	StringBuffer strContent = new StringBuffer("");
            int index = msgList.locationToIndex(e.getPoint());
   //         if ( index >= 0 && index < messages.size())
   //        	Jbody.setText(((PopMessage)messages.elementAt(index)).message);
            try {
            	// Chargement du fichier de test
				FileInputStream fi = new FileInputStream("mail_source_mermail.txt");
				while((ch = fi.read()) != -1)	{
					strContent.append((char)ch); // on remplit un string buffer caract�re par caract�re
				}
				fi.close();
				String leMsg = strContent.toString(); // Conversion de StringBuffer � String
				String[] arrMsg = leMsg.split("stop|\\n"); // D�coupage par ligne
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
					// extraction: expediteur
					if(arrMsg[i].startsWith(from))	{
						leMessage.setExpediteur(arrMsg[i].substring(from.length()).trim());
					}
					// extraction: destinataire
					if(arrMsg[i].startsWith(to))	{
						leMessage.setDestinataire(arrMsg[i].substring(to.length()).trim());
					}
					// extraction: adresse expediteur
					if(arrMsg[i].startsWith(from_adrr))	{
						leMessage.setAdresse_destinataire(arrMsg[i].substring(from_adrr.length()).trim());
					}
					// extraction: sujet
					if(arrMsg[i].startsWith(subject))	{
						leMessage.setObjet(arrMsg[i].substring(subject.length()).trim());
					}
					// extraction: date TODO: Modifier le format en format Latin (11/04/1995 - 12:00)
					if(arrMsg[i].startsWith(date))	{
						leMessage.setDate(arrMsg[i].substring(date.length()).trim());
					}
					// d�part du contenu
					if(arrMsg[i].startsWith("<html>"))	{
						line_start = i;
						System.out.println("s" + line_start);
					}
					// fin du contenu
					if(arrMsg[i].startsWith("</html>"))	{
						line_end = i;
						System.out.println("e" + line_end);
					}
					// calcul : taille du message
					leMessage.setTaille(strContent.length()*8);
					ArrMsgLine += i + " - " + arrMsg[i] + "\r\n";
				}
				// on r�cupere le contenu des lignes entre line_start et line_end
				for(int compt = line_start; compt < (line_end - line_start)+line_start; compt++)	{
					contenu += arrMsg[compt];
				}
				leMessage.setContenu(contenu);
				// Affichage sur le panel de droite
				Jbody.setText(leMessage.toString());
				// DEBUG
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

  


  

  public static void main ( String args[] )
  {
  	
  	try //MCP: Chargement des comptes + affichage dans la console
	{
		FileInputStream fi = new FileInputStream("Data.dat");
		ObjectInputStream oi = new ObjectInputStream(fi);
		listeComptes = (ArrayList) oi.readObject();
		System.out.println("Info: "+ listeComptes.size()+ " Compte(s) charg�(s) :");
		for (Compte c : listeComptes) {
			if(c.isCourant())	{
				System.out.println("#"+c.getTitre() +" - "+ c.getUser()+"@"+c.getHost());
			}
			else
				System.out.println(c.getTitre() +" - "+ c.getUser()+"@"+c.getHost());
		}
	}
	catch (IOException e)
	{
		System.out.println("Erreur: �chec du chargement des donn�es main" + e.getMessage());
		//System.exit(0);
	}
	catch (ClassNotFoundException se)
	{
		System.out.println("Erreur: Classe non trouv�");
	}
	
	/*
	try
	{
		FileInputStream fi = new FileInputStream("data2.dat");
		courant = fi.read();
		
	}
	catch (IOException e)
	{
		System.out.println("Erreur: �chec du chargement des donn�es");
		//System.exit(0);
	}
	*/
	
	
    (new MonApplication()).setVisible(true);

  }

}