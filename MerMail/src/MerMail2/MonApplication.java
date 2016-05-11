package MerMail2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import com.mysql.jdbc.ResultSet;

import MerMail2.Base;
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
  
	
	private JLabel status = new JLabel("Prêt...");
	private JTextArea Jbody = new JTextArea(10,50);
	public static JMenuBar menuBar = new JMenuBar();
	static GCompte gC;
	public static Base maBase = new Base();

	private Base InitDB(Base laBase) {
	  try {
		laBase.getConnexion();
	  } catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
		laBase.write(courant);
		laBase.close();
		return laBase;
 	}
  
	public MonApplication () throws ClassNotFoundException, SQLException
  {
	// INSTANCIATION BASE DE DONNÉES
	  try	{
		  Base maBase = this.maBase;
		  InitDB(maBase);
	  } catch (Exception e)	{
		  System.err.println("Erreur: " + e + "\r\n Détails :" + e.getMessage());
	  }
    
    
    // INSTANCIATION GMESSAGE
    final GMessage gM = new GMessage();
    
 // INSTANCIATION GCOMPTE

 	final GCompte gC = new GCompte();
 	
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
    final String[] faux_messages = { "Message 1" , "Message 2", "Message 3" };
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
    
    
    setVisible(true);
    
    contentPane.add(coeur);


    
    exitItem.addActionListener( new ActionListener(){
		public void actionPerformed(ActionEvent e){
              System.exit(0);
    }});

   popMsgItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            gM.getMessages();
    }});

    
   // ON CLICK
    msgList.addMouseListener(new MouseAdapter() {
        

		public void mouseClicked(MouseEvent e) {
        	Message leMessage = new Message();
        	int ch;
        	StringBuffer strContent = new StringBuffer("");
            int index = msgList.locationToIndex(e.getPoint());
            FileInputStream fi = null;
   //         if ( index >= 0 && index < messages.size())
   //        	Jbody.setText(((PopMessage)messages.elementAt(index)).message);
            
            	int idmail = msgList.locationToIndex(e.getPoint())+1;

					try {
						fi = new FileInputStream("mail_source_mermail_"+ idmail + ".txt");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

						try {
							while((ch = fi.read()) != -1)	{
								strContent.append((char)ch);
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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
							// EXTRACTION DES DONNÉES
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
									//System.out.println("s" + line_start);
								}
								if(arrMsg[i].startsWith("</html>"))	{
									line_end = i;
									//System.out.println("e" + line_end);
								}
 
							leMessage.setTaille(strContent.length()*8);
							ArrMsgLine += i + " - " + arrMsg[i] + "\r\n";
					}
					//System.out.println((line_end - line_start));
					for(int compt = line_start; compt < (line_end - line_start)+line_start; compt++)	{
												contenu += arrMsg[compt];
					}
					leMessage.setContenu(contenu);
					Jbody.setText(leMessage.toStringSimple());
					System.out.println(leMessage.toStringSimple());
	
		}
    });

    ArrayList<Compte> res = null;
	  res = maBase.lireEnBase();
	
	String titres_compt = "";
	try {
		if(!res.isEmpty()){
			for(Compte c : res)	{
				listeComptes.add(c);
				titres_compt += "\r\n "+ c.getTitre();
				MonApplication.cc = maBase.getCourant();
			}
			System.out.println(listeComptes.size() + " compte(s) chargé(s) :" + titres_compt);
			System.out.println(maBase.getCourant().getTitre() + " est le cc! " + MonApplication.cc.getTitre());
		}
		
	} catch (Exception e) {
		System.err.println("Il n'y a pas de compte dans la base de données");
	}
    pack();
    
   
    
    
  }

  public static void main ( String args[] ) throws SQLException, ClassNotFoundException	{
  	
	  
		
		
	
    try {
		(new MonApplication()).setVisible(true);
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

  }

}