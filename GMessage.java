package MerMail2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

class GMessage	{
	
	public GMessage()	{
		
	}
	
	// TODO: Ajouter la méthode getMessages et getMessage
	protected void getMessages()
	  {
	      PrintWriter to;
	      BufferedReader from;
	      String str,title, msg;
	      Vector v = new Vector();

	      try {
	    	  System.out.println(""+ 110 + "" + "");
	    	  Socket socket = new Socket(InetAddress.getByName(""),110);
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
	        String user = "";
			to.println("USER "+user );
	        System.out.println("USER "+user);
	        String rep1 = from.readLine();
	        System.out.println("rep1 "+rep1);
	        while ( ! rep1.startsWith("+OK"));
	        String password = "";
			System.out.println("PASS avant "+password );
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
	          		//messages.add(title);
	          		//messages2.add(msg);
	          	
	          	System.out.println("Titre du message --> "+title);
	          	System.out.println("message n° "+i+"==================================================================");
	        }
	        to.println("QUIT");
	        System.out.println("==========================================================================");
			//String test = (String ) messages.elementAt(0);
			//System.out.println("*************"+test);
	        //msgList.setListData(messages);

	        //status.setText("Prêt ...");

	        socket.close();

	      } catch ( Exception e ) {System.err.println("GetMessages : "+e.getMessage());}


	  }
}