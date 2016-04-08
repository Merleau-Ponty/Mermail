package MerMail2;

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
import MerMail2.Message;

@SuppressWarnings("serial")
public class MonApplication extends JFrame
{
public static ArrayList<Compte> listeComptes = new ArrayList<Compte>();
static int courant;
public static Compte cc = null;
public Compte compteDefault = new Compte("MerMail", "pop.laposte.net", 110, "mermail", "p@ssw0rd");
  
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
    // TODO: IMPLÉMENTER L'OBJET GRAPHIQUE DANS GMESSAGE AU LIEU DE L'APPLICATION
    JPanel coeur = new JPanel();
    JScrollPane SPbody, SPlist;
    coeur.setLayout(new BorderLayout());

    SPbody = new JScrollPane(Jbody,
                             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                             JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    SPlist = new JScrollPane(msgList,
                             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                             JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    coeur.add(SPlist, BorderLayout.WEST);
    coeur.add(SPbody, BorderLayout.EAST);
    
    Component m = new Message("a", "b", "a@k?fr", "mlpo", "ihuiyg", "14/05/1865", 12345, 1);
    msgList.add(m);
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
				System.out.println("Info: Compte(s) sauvé(s)");
			}
			catch (IOException f)
			{
				System.out.println("Erreur : L'enregistrement des données à échoué. Veuillez recommencez ultérieurement." + f.getMessage());
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


   popMsgItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            gM.getMessages();
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

  


  

  public static void main ( String args[] )
  {
  	
  	try //MCP: Chargement des comptes + affichage dans la console
	{
		FileInputStream fi = new FileInputStream("Data.dat");
		ObjectInputStream oi = new ObjectInputStream(fi);
		listeComptes = (ArrayList) oi.readObject();
		System.out.println("Info: "+ listeComptes.size()+ " Compte(s) chargé(s) :");
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
		System.out.println("Erreur: Échec du chargement des données main" + e.getMessage());
		//System.exit(0);
	}
	catch (ClassNotFoundException se)
	{
		System.out.println("Erreur: Classe non trouvé");
	}
	
	/*
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
	*/
	
	
    (new MonApplication()).setVisible(true);

  }

}