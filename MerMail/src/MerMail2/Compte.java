package MerMail2;

import java.io.Serializable;

@SuppressWarnings("serial")
class Compte implements Serializable
{
	private String titre;
	private String host;
	private int port;
	private String user;
	private String password;
	private boolean courant;

	// Constructeur avec parametres
	public Compte(String titre, String host, int port, String user, String password, boolean courant)
	{
		this.titre = titre;
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.courant = false;
	}
	// Constructeur sans parametres
	public Compte()
	{
	this.titre = "titre";
	this.host = "host";
	this.port = 0;
	this.user = "user";
	this.password = "password";
	}

	// NOTE: GETTERS AND SETTERS + TOSTRING

	public String getHost() {
		return host;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Compte [titre=" + titre + ", host=" + host + ", port=" + port + ", user=" + user + ", password="
				+ password + ", courant=" + courant + "]";
	}
	public String toStringUser() {
		return user;
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
	/**
	 * @return the courant
	 */
	public boolean isCourant() {
		return courant;
	}
	/**
	 * @param courant the courant to set
	 */
	public void setCourant(boolean courant) {
		this.courant = courant;
	}
}