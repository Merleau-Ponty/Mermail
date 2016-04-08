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

    /**
	 * @return the expediteur
	 */
	public String getExpediteur() {
		return expediteur;
	}

	/**
	 * @param expediteur the expediteur to set
	 */
	public void setExpediteur(String expediteur) {
		this.expediteur = expediteur;
	}

	/**
	 * @return the destinataire
	 */
	public String getDestinataire() {
		return destinataire;
	}

	/**
	 * @param destinataire the destinataire to set
	 */
	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}

	/**
	 * @return the adresse_destinataire
	 */
	public String getAdresse_destinataire() {
		return adresse_destinataire;
	}

	/**
	 * @param adresse_destinataire the adresse_destinataire to set
	 */
	public void setAdresse_destinataire(String adresse_destinataire) {
		this.adresse_destinataire = adresse_destinataire;
	}

	/**
	 * @return the objet
	 */
	public String getObjet() {
		return objet;
	}

	/**
	 * @param objet the objet to set
	 */
	public void setObjet(String objet) {
		this.objet = objet;
	}

	/**
	 * @return the contenu
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * @param contenu the contenu to set
	 */
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the taille
	 */
	public int getTaille() {
		return taille;
	}

	/**
	 * @param taille the taille to set
	 */
	public void setTaille(int taille) {
		this.taille = taille;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

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