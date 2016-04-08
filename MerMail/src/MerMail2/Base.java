package miniMessagerie;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

//http://www.objis.com/formation-java/tutoriel-java-acces-donnees-jdbc.html
public class Base {

	private String titre, users, passwords, serveur;
	private int port;
	
		public Base() {
		super();
		this.titre = titre;
		this.users = users;
		this.passwords = passwords;
		this.serveur = serveur;
		this.port = port;
	}
		public Connection getConnexion()throws ClassNotFoundException, SQLException{
			String url = "jdbc:mysql://localhost/mermail";
			String login = "root" ;
			String password ="";
			Connection cn= null;
			Statement st =null;
			ResultSet rs = null;
			
			Class.forName("com.mysql.jdbc.Driver");
			cn = (Connection) DriverManager.getConnection(url, login, password);
			st = (Statement) cn.createStatement();
			return cn;
		}
		public Statement getStatement()throws ClassNotFoundException, SQLException{
			
			Statement st = (Statement) this.getConnexion().createStatement();
			return st;
		}
		// Insertion des données
	
		public void sauverEnBase(String titre, String users, String passwords, String serveur, int port) throws ClassNotFoundException{
			
			try{
				
				String sql = "INSERT INTO compte(titre, users, passwords, serveur, port) VALUES ('" + titre + "','" + users + "','" + passwords + "','" + serveur + "'," + port +");";
				//String sql = "INSERT INTO compte(titre, users, passwords, serveur, port) VALUES ('"+titre+", "+users+", "+passwords+","+serveur+",+port+')";
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
public void Modification(String titre, String users, String passwords, String serveur, int port) throws ClassNotFoundException{
			
			try{
				//a finir le update to compte set paramatres where condition
				String sql = "UPDATE TO compte(titre, users, passwords, serveur, port) SET ('" + titre + "','" + users + "','" + passwords + "','" + serveur + "'," + port +");";
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
		// Lecture des données
		
		public ResultSet lireEnBase() throws ClassNotFoundException {
			
			try{
				String sql = "Select * from compte";
				ResultSet rs = (ResultSet) this.getStatement().executeQuery(sql);
				
				while (rs.next()){
					System.out.println(rs.getString("users")+rs.getString("serveur"));
				return rs;
				}
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