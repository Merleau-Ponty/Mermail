package miniMessagerie;

import java.util.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;



public class GPop extends JFrame
{
static ArrayList listeComptes = new ArrayList();
static int courant;

  class PopMessage {

    public String message;
    private String title;

    public PopMessage (String titleW, String msg)
    {
      title = titleW;
      message = msg;
    }

    public String toString()
    {
      return title;
    }

  }
  
  private String popServer = "pop.gmail.com";
  private String user = "ppe.merleau" , password = "ppe@2016";
  private int port = 110;

  private JLabel status = new JLabel("Prêt...");
  private JTextArea Jbody = new JTextArea(10,50);
  private Vector messages = new Vector();
  private Vector messages2 = new Vector();	//ajout perso
  private JList msgList = new JList();

  public GPop ()
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
         public void windowClosing(WindowEvent e){
         	try
			{
				FileOutputStream fo = new FileOutputStream("Data.dat");
				ObjectOutputStream oo = new ObjectOutputStream(fo);
				oo.writeObject(listeComptes);
			}
			catch (IOException f)
			{
				System.out.println("marche po :( l'enregistrement des données à échoué. Recommencez tout!!");
			}
              setVisible(false);
              dispose();
              System.exit(0);
    }});

    exitItem.addActionListener( new ActionListener(){
         public void actionPerformed(ActionEvent e){
         	try
			{
				FileOutputStream fo = new FileOutputStream("Data.dat");
				ObjectOutputStream oo = new ObjectOutputStream(fo);
				oo.writeObject(listeComptes);
			}
			catch (IOException f)
			{
				System.out.println("marche po :( l'enregistrement des données à échoué. Recommencez tout!!");
			}
              System.exit(0);
    }});

    popMsgItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            getMessages();
    }});

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

  protected void getMessages()
  {
      PrintWriter to;
      BufferedReader from;
      String str,title, msg;
      Vector v = new Vector();

      try {
    	  System.out.println(popServer+ port+ user+ password);
    	  Socket socket = new Socket(InetAddress.getByName(popServer),port);
    	  System.out.println("socket OK");
    	  //socket.connect(socket.getRemoteSocketAddress(), 60);
        
        to = new PrintWriter(
                    new BufferedWriter (
                        new OutputStreamWriter (
                            socket.getOutputStream())),true);
        from = new BufferedReader(
                   new InputStreamReader (
                            socket.getInputStream()));
        

        while ( ! (from.readLine()).startsWith("+OK") );
        to.println("USER "+user);
        System.out.println("USER "+user);
        String rep1 = from.readLine();
        System.out.println("rep1 "+rep1);
        while ( ! rep1.startsWith("+OK"));
        System.out.println("PASS avant "+password);
        to.println("PASS "+password);
        System.out.println("PASS après"+password);
        while ( ! (from.readLine()).startsWith("+OK") );

        to.println("LIST");
        int nbmsg = 0;
        while ( ! (from.readLine()).startsWith("+OK") );
        do {
        	nbmsg++;
        	System.out.println("Boucle de lecture messages");
          str = from.readLine();
          if ( str.compareTo(".") != 0 ) v.add(str);
        } while ( str.compareTo(".") != 0 );
		
        System.out.println("nombre de messages "+nbmsg);
		System.out.println("taille de la liste v "+v.size());
        for ( int i =0; i < v.size(); i++ ) {
        	
          title = (String ) v.elementAt(i);
          StringTokenizer t = new StringTokenizer(title);
		  String t1 =t.nextToken();
          int t2 =Integer.parseInt(t.nextToken());
          System.out.println("Taille du message  --> "+t2);
          //to.println("RETR "+(new StringTokenizer(title)).nextToken()+"\r");
          to.println("RETR "+t1+"\r");
          while ( ! (from.readLine()).startsWith("+OK") );
          	if(t2>30000)
          		msg = "Message trop grand";
          	else {
          
          		msg = "";
          		do {
            		msg += from.readLine() + "\n";
          		} while ( ! msg.endsWith("\n.\n") );
          	//messages.add(new PopMessage(title, msg));
          	}	
          		messages.add(title);
          		messages2.add(msg);
          	
          	System.out.println("Titre du message --> "+title);
          	System.out.println("message n° "+i+"==================================================================");
        }
        to.println("QUIT");
        System.out.println("==========================================================================");
		String test = (String ) messages.elementAt(0);
		System.out.println("*************"+test);
        msgList.setListData(messages);

        status.setText("Prêt ...");

        socket.close();

      } catch ( Exception e ) {System.err.println("GetMessages : "+e.getMessage());}


  }

  protected void ajoutComptePop()
  {
  	GComptePop monPanneau = new GComptePop();
  	int retour = JOptionPane.showConfirmDialog(
			this,
			monPanneau,
		    "enregistrement d'un compte",
		   	JOptionPane.OK_CANCEL_OPTION,
		    JOptionPane.PLAIN_MESSAGE
			);
  	
  	if(retour == JOptionPane.OK_OPTION)
	{
  		ComptePop temp = monPanneau.getCompte();
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
			ComptePop temp = (ComptePop)listeComptes.get(monPanneauSelection.getIndexSelection());
			GComptePop monPanneau = new GComptePop(temp);
			int retour2 = JOptionPane.showConfirmDialog(
					this,
					monPanneau,
					"enregistrement d'un compte",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE
					);
	  	
			if(retour2 == JOptionPane.OK_OPTION)
			{
				ComptePop temp2 = monPanneau.getCompte();
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
  		ComptePop temp2 = (ComptePop)listeComptes.get(courant);
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
			ComptePop temp = (ComptePop)listeComptes.get(courant);
			popServer = temp.getHost();
			user = temp.getUser();
			password = temp.getPassword();
			port = temp.getPort();
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
		System.out.println("marche po :( , echec du chargement des données");
		//System.exit(0);
	}
	catch (ClassNotFoundException se)
	{
		System.out.println("sniff :(");
	}
	
	
	try
	{
		FileInputStream fi = new FileInputStream("data2.dat");
		courant = fi.read();
		
	}
	catch (IOException e)
	{
		System.out.println("marche po :( , echec du chargement des données");
		//System.exit(0);
	}
	
	
    (new GPop()).setVisible(true);

  }

}

