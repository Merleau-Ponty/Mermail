package MerMail2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class panneauSelectionCompte extends JPanel implements ActionListener
{
	@SuppressWarnings("rawtypes")
	JComboBox listeCompte = new JComboBox();
	JLabel titre = new JLabel();
	JLabel host = new JLabel();

	@SuppressWarnings("unchecked")
	public panneauSelectionCompte()
	{
		this.setLayout(new GridLayout(4, 1));
		for (int i=0; i < MonApplication.listeComptes.size(); i++)
		{
			listeCompte.addItem(MonApplication.listeComptes.get(i));
		}

		add(listeCompte);
		add(titre);
		add(host);
		//initialisation
		listeCompte.setSelectedIndex(0);
		Compte temp = (Compte)MonApplication.listeComptes.get(0);
		titre.setText("titre: "+temp.getTitre());
		host.setText("host: "+temp.getHost());

		//capture d'evenement
		listeCompte.addActionListener(this);


	}
	
	public void actionPerformed(ActionEvent evt)
	{
		Object src = evt.getSource();
		// Recuperer les informations au compte s�l�ctionn�
		if(src == listeCompte)
		{
			Compte temp = (Compte)MonApplication.listeComptes.get(listeCompte.getSelectedIndex());
			titre.setText("titre: "+temp.getTitre());
			host.setText("host: "+temp.getHost());

		}
	}

	public int getIndexSelection()
	{
		return listeCompte.getSelectedIndex();
	}

}