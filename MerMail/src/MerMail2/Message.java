package miniMessagerie;

class Message {

    public String expediteur;
	public String destinataire;
	public String adresse_destinataire;
	public String objet;
	public String contenu;
	public String date;
	public int taille;

    public Message(String expediteur, String destinataire, String adresse_destinataire, String objet, String contenu,
			String date, int taille) {
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.adresse_destinataire = adresse_destinataire;
		this.objet = objet;
		this.contenu = contenu;
		this.date = date;
		this.taille = taille;
	}

	public Message() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Message [expediteur=" + expediteur + ", destinataire=" + destinataire + ", adresse_destinataire="
				+ adresse_destinataire + ", objet=" + objet + ", contenu=" + contenu + ", date=" + date + ", taille="
				+ taille + " octets, status=" + status + "]";
	}



  }