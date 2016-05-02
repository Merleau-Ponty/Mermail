package MerMail2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class GMessage	{
	
	private ArrayList<Compte> listeComptes;
	GCompte gC = MonApplication.gC;
	
	public GMessage()	{
		/*
		 * TODO: IMPLÉMENTER L'OBJET GRAPPHIQUE DANS CETTE CLASSE AU LIEU DE L'APPLICATION
		 * 
		// GUI: LISTE DES MAILS
		
		Container contentPane = MonApplication.contentPane;
		JList msgList = MonApplication.msgList;
		JTextArea Jbody = MonApplication.Jbody;
		
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
	    contentPane.add(coeur);
	    */
		
	}
	
	// TODO: Ajouter la méthode getMessages et getMessage
	protected void getMessages()
	  {
	      PrintWriter to;
	      BufferedReader from;
	      String str,title, msg;
	      Vector v = new Vector();
	      Compte cc = gC.getCompteCourant();
	      try {
	    	System.out.println("Début de la requete");
	    	Socket s = new Socket(InetAddress.getByName(cc.getHost()),cc.getPort());

	    	System.out.println("Socket bon");
	    	try{ s.connect(s.getRemoteSocketAddress(), 60); 
	    		System.out.println("Info: Connection au socket réussi");
	    	} catch(Exception e)	{
	    		System.out.println("Erreur: Connection au socket en échec \r\n " + e.getMessage());
	    	}
	        
	        to = new PrintWriter(
	                    new BufferedWriter (
	                        new OutputStreamWriter (
	                            s.getOutputStream())),true);
	        from = new BufferedReader(
	                   new InputStreamReader (
	                            s.getInputStream()));
	        
	        System.out.println("Info: Récupération des messages ...");
	        // USER
	        while ( ! (from.readLine()).startsWith("+OK") );
				to.println("USER "+ cc.getUser() );
		        System.out.println("USER "+ cc.getUser());
		        String rep1 = from.readLine();
		        System.out.println("rep1 "+rep1);
	        while ( ! rep1.startsWith("+OK"));
	        // PASSWORD			
	        to.println("PASS "+ cc.getPassword());	        
	        // LIST
	        to.println("LIST");
	        int nbmsg = 0;
	        while ( ! (from.readLine()).startsWith("+OK") );
	        do {
	          nbmsg++;
	          str = from.readLine();
	          if ( str.compareTo(".") != 0 ) v.add(str);
	        } while ( str.compareTo(".") != 0 );
			
	        System.out.println("Nb de messages: " + nbmsg);
			System.out.println("Taille de la liste: "+v.size());
	        for ( int i =0; i < v.size(); i++ ) {
	        	
	          title = (String ) v.elementAt(i);
	          StringTokenizer t = new StringTokenizer(title);
			  String t1 =t.nextToken();
	          int taille =Integer.parseInt(t.nextToken());
	          //System.out.println("Taille du message  --> "+t2);
	          //to.println("RETR "+(new StringTokenizer(title)).nextToken()+"\r");
	          to.println("RETR "+t1+"\r");
	          System.out.println("S: RETR "+ t1);
	          while ( ! (from.readLine()).startsWith("+OK") );
	          	if(taille > 30000)
	          		msg = "Message trop grand";
	          	else {
	          
	          		msg = "";
	          		do {
	            		msg += from.readLine() + "\n";
	            		v.add(msg);
	            		
	          		} while ( ! msg.endsWith("\n.\n") );
	          		
	          	//messages.add(new PopMessage(title, msg));
	          	}	
	          		//messages.add(title);
	          		//messages2.add(msg);
	          	
	          	//System.out.println("Titre: "+title);
	          	System.out.println("#"+ (i+1) +" - - - - - - - - - -");
	          	System.out.println(taille+ " octets");
	          
	        }
	        to.println("QUIT");
	        //System.out.println("- - - - - - - - - -");
			//String test = (String ) messages.elementAt(0);
			//System.out.println("*************"+test);
	        //msgList.setListData(messages);

	        //status.setText("Prêt ...");

	        s.close();

	      } catch ( Exception e ) {System.err.println("Erreur: Connexion impossible à "+ cc.getUser() +"@"+cc.getHost()+"\r\n" +e.getMessage());}


	  }
}