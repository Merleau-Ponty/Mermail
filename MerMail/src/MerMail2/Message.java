package MerMail2;

class Message {

    public String expediteur;
	public String destinataire;
	public String adresse_destinataire;
	public String objet;
	public String contenu;
	public String date;
	public int taille;
	public int status;

    public Message(String expediteur, String destinataire, String adresse_destinataire, String objet, String contenu,
			String date, int taille, int status) {
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.adresse_destinataire = adresse_destinataire;
		this.objet = objet;
		this.contenu = contenu;
		this.date = date;
		this.taille = taille;
		this.status = status;
	}

	public String toString()
    {
      return objet;
    }

  }