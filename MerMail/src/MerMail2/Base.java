package MerMail2;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

//http://www.objis.com/formation-java/tutoriel-java-acces-donnees-jdbc.html
/***
 * Permet l'acces aux données de la base de données depuis l'application, comprends plusieurs requetes
 * pré-remplit en lien avec les opérations CRUD des comptes de messageries.
 * 
 * @author MCP, KL
 * @version 1.0
 * 
 */
public class Base {

	private String titre, users, passwords, serveur;
	private int port;
	
	/***
	 * Constructeur sans paramètres d'un objet Base
	 * @author KL
	 */
	public Base() {
	}
	
	/***
	 * Renvoie un objet de type Connection
	 * @return Un objet Connection lié à la base de données
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @see Connection
	 * @author KL
	 */
	public Connection getConnexion()throws ClassNotFoundException, SQLException{
		String url = "jdbc:mysql://localhost/mermail";
		String login = "root" ;
		String password ="";
			
		Class.forName("com.mysql.jdbc.Driver");
		Connection cn = (Connection) DriverManager.getConnection(url, login, password);
		return cn;
	}
	
	/***
	 * Renvoie un objet de type Statement
	 * @return Un objet Statement lié à la base de données
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @see Statement
	 * @author KL
	 */
	public Statement getStatement()throws ClassNotFoundException, SQLException{
			
		Statement st = (Statement) this.getConnexion().createStatement();
		return st;
	}
	/***
	 * Insert un compte dans la base de données
	 * @param titre: Titre du compte
	 * @param users: Username du compte
	 * @param passwords: Mot de passe du compte
	 * @param serveur: Serveur POP du serveur de messagerie
	 * @param port: Port de destination (souvent 110)
	 * @param courant: Si le compte est courant (1) ou non (0)
	 * @return true (vrai) si l'opération s'est bien passé, false (faux) si l'opération à échoué
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @author KL, MCP
	 */
	
		// Insertion des données
		public boolean ajouterEnBase(String titre, String users, String passwords, String serveur, int port, boolean courant) throws ClassNotFoundException, SQLException{
			String sql = "INSERT INTO `compte`(`id_compte`, `courant`, `titre`, `users`, `passwords`, `serveur`, `port`)"
					+ " VALUES ('',false,'"+ titre +"','"+ users +"','"+ passwords +"','"+ serveur +"',"+ port +")";
			System.out.println(sql);
			try	{
				getStatement().executeUpdate(sql);
				return true;
			}	catch(SQLException e)	{
				e.printStackTrace();
				return false;
			}
			
		}
		
		/***
		 * 
		 * @param titre
		 * @param users
		 * @param passwords
		 * @param serveur
		 * @param port
		 * @return
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public boolean sauverEnBase(String titre, String users, String passwords, String serveur, int port) throws ClassNotFoundException, SQLException{
			
			ArrayList<Compte> res = lireEnBase();
			if(res.isEmpty())	{
				ajouterEnBase(titre, users, passwords, serveur, port, true);
			}	else	{
				for(Compte c : res)	{
					if(c.getTitre() == titre)	{
						return false;
					}
				}
				if(ajouterEnBase(titre, users, passwords, serveur, port, false))	{
					return true;
				}	else {
					return false;
				}
				
			}
			return false;
			
			

		}
		//Modification du compte courant 
		/***
		 * 
		 * @param oldTitre
		 * @param titre
		 * @param users
		 * @param passwords
		 * @param serveur
		 * @param port
		 * @throws ClassNotFoundException
		 */
		public void Modification(String oldTitre, String titre, String users, String passwords, String serveur, int port) throws ClassNotFoundException{
			
			try{
				//a finir le update to compte set paramatres where condition
				
				String sql = "UPDATE compte SET id_compte='',titre ='" + titre + "',users ='" + users + "',passwords ='" + passwords + "',serveur ='" + serveur + "',port =" + port + ""
						+ " WHERE titre ='"+ oldTitre +"';";
				System.out.println(sql);
				// execution de la requete
				getStatement().executeUpdate(sql);
			}catch (SQLException e){
				e.printStackTrace();
			}catch (ClassNotFoundException e){
				e.printStackTrace();
			} finally {
				try{
					getConnexion().close();
					getStatement().close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		// Suppression des donnees en passant par le titre
		/***
		 * 
		 * @param titre
		 * @throws ClassNotFoundException
		 */
		public void Suppression(String titre) throws ClassNotFoundException{
	
			try{
		
				String sql = "DELETE FROM compte where titre= '"+ titre +"'"; 
				// execution de la requete
				getStatement().executeUpdate(sql);
			}catch (SQLException e){
				e.printStackTrace();
			}catch (ClassNotFoundException e){
				e.printStackTrace();
			} finally {
			try{
				getConnexion().close();
				getStatement().close();
			}catch (SQLException e){
				e.printStackTrace();
			}
			}
		}
		//etat = 1 = "est courant"; etat = 0 = "pas courant"
		/***
		 * 
		 * @param titre
		 * @param etat
		 * @return
		 */
		public boolean setCourant(String titre,boolean etat){
			String sql = "UPDATE compte SET courant="+ etat +" WHERE titre ='"+ titre +"';";
			try {
				getStatement().executeUpdate(sql);
				return true;
			} catch (SQLException | ClassNotFoundException e) {
				System.err.println(e);
				return false;
			}
			
		}
		
		/***
		 * (A CORRIGER)Choix du compte courant
		 * 
		 * @param titre
		 * @return true si ok sinon false
		 * @author MCP
		 */
		public boolean choixCourant(String titre)	{
			Compte ancien = getCourant();
			ancien.setCourant(false);
			return setCourant(titre, true);
		}
		/***
		 * 
		 * @param titre: Le titre du compte a rechercher (recherche par titre)
		 * @return Le compte qui contient le titre dans le paramètre
		 * @author MCP
		 */
		public Compte getCompte(String titre){
			ArrayList<Compte> result = null;
			try {
				result = lireEnBase();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(result != null){
				try {
					for(Compte c : result){
						if(c.getTitre() == titre){
							return new Compte(titre, c.getHost(), c.getPort(), c.getUser(),c.getPassword(),c.isCourant());
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return null;
		}
		/***
		 * La fonction retourne le compte courant (qui a le champs courant à 1 dans la base de données)
		 * 
		 * @return Le compte courant
		 * @author MCP
		 */
		public Compte getCourant(){
			String sql = "select * from compte where courant = 1";
			try {
			  	ResultSet rs = (ResultSet) getStatement().executeQuery(sql);
			  	while(rs.next())	{
			  		return new Compte(rs.getString("titre"),rs.getString("serveur"),rs.getInt("port"),rs.getString("users"),rs.getString("passwords"),rs.getBoolean("courant"));
			  	}
			} catch (Exception e) {
				System.err.println(e);
				return null;
			}
			return null;
		  	
		}
	
		public ArrayList<Compte> lireEnBase() throws ClassNotFoundException {
			
			ArrayList<Compte> result = new ArrayList<Compte>();
			try{
				String sql = "Select i.cntr, c.* from compte c,(select count(*) cntr from compte c) i";
				ResultSet rs = (ResultSet) this.getStatement().executeQuery(sql);
				
				while (rs.next()){
					//System.out.println(rs.getString("titre"));
					result.add(new Compte(rs.getString("titre"), rs.getString("serveur"), rs.getInt("port"), rs.getString("users"),rs.getString("passwords"),rs.getBoolean("courant")));
				}
				return result;
			}catch (SQLException e){
				e.printStackTrace();
			}catch (ClassNotFoundException e){
				e.printStackTrace();
			} finally {
				try{
					getConnexion().close();
					getStatement().close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
			return null;
		}
		public void close() {
			// TODO Auto-generated method stub
			
		}
		public void write(int courant) {
			// TODO Auto-generated method stub
			
		}
	}