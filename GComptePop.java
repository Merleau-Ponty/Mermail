package miniMessagerie;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


class ComptePop implements Serializable
{
	private String titre;
	private String host;
	private int port;
	private String user;
	private String password;
	
	public ComptePop(String titre, String host, int port, String user, String password)
	{
		this.titre = titre;
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
	}
	public ComptePop()
	{
	this.titre = "titre";
	this.host = "host";
	this.port = 0;
	this.user = "user";
	this.password = "password";
	}
	
	public String toString()
    {
      return titre;
    }
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}


public class GComptePop extends JPanel
{
  JTextField titre, hostTF, portTF, userTF;
  JPasswordField passwordTF;
  ComptePop monComptePop;

  public GComptePop()
  {
    this(new ComptePop());
  }
  
  public GComptePop(final ComptePop temp)
  {
    super();
    this.monComptePop = temp;

    JPanel panel1, panel2, panel3;

    this.setLayout(new BorderLayout());
    panel1 = new JPanel();
    panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
    this.add(panel1, BorderLayout.SOUTH);

    panel3 = new JPanel();
    panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
    this.add(panel3, BorderLayout.CENTER);
    
    hostTF = new JTextField(monComptePop.getHost(), 20);
    portTF = new JTextField("" + monComptePop.getPort(), 5);
    panel3.add(new JLabel("Server"));
    panel3.add(hostTF);
    panel3.add(new JLabel("Port"));
    panel3.add(portTF);

    panel2 = new JPanel();
    panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
    this.add(panel2, BorderLayout.NORTH);
    titre = new JTextField(monComptePop.getTitre(), 12);
    userTF = new JTextField(monComptePop.getUser(), 12);
    passwordTF = new JPasswordField(monComptePop.getPassword(), 10);
    panel2.add(new JLabel("Titre"));
    panel2.add(titre);
    panel2.add(new JLabel("User"));
    panel2.add(userTF);
    panel2.add(new JLabel("Passwd"));
    panel2.add(passwordTF);

  }
  public ComptePop getCompte()
  {
  	monComptePop.setTitre(titre.getText());
  	monComptePop.setHost(hostTF.getText());
  	monComptePop.setUser(userTF.getText());
  	monComptePop.setPassword(new String(passwordTF.getPassword()));
  	monComptePop.setPort(Integer.parseInt(portTF.getText()));
  	return monComptePop;
  }
}


class panneauSelectionCompte extends JPanel implements ActionListener
{
	JComboBox listeCompte = new JComboBox();
	JLabel titre = new JLabel();
	JLabel host = new JLabel();
	
	public panneauSelectionCompte()
	{
		this.setLayout(new GridLayout(4, 1));
		for (int i=0; i < GPop.listeComptes.size(); i++)
		{			
			listeCompte.addItem(GPop.listeComptes.get(i));
		}

		add(listeCompte);
		add(titre);
		add(host);
		//initialisation
		listeCompte.setSelectedIndex(0);
		ComptePop temp = (ComptePop)GPop.listeComptes.get(0);
		titre.setText("titre: "+temp.getTitre());
		host.setText("host: "+temp.getHost());

		//capture d'evenement
		listeCompte.addActionListener(this);
		
	
	}

	public void actionPerformed(ActionEvent evt)
	{
		Object src = evt.getSource();
		if(src == listeCompte)
		{
			ComptePop temp = (ComptePop)GPop.listeComptes.get(listeCompte.getSelectedIndex());
			titre.setText("titre: "+temp.getTitre());
			host.setText("host: "+temp.getHost());
			
		}
	}

	public int getIndexSelection()
	{
		return listeCompte.getSelectedIndex();
	}
}
