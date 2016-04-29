package miniMessagerie;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GCompte extends JPanel
{
  JTextField titre, hostTF, portTF, userTF;
  JPasswordField passwordTF;
  Compte monCompte;

  public GCompte()
  {
    this(new Compte());
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