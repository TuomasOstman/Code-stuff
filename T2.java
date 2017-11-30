
// Little Example of database programming with java

import java.sql.*;
import java.util.*;
public class T2{
	//Testistä lainatut vakiot shelliin yhdistämiseen
	private static final String AJURI = "org.postgresql.Driver";
	private static final String PROTOKOLLA = "jdbc:postgresql:";
	private static final String PALVELIN = "localhost";
	private static final int PORTTI = 5432;
	private static final String TIETOKANTA = "postgres";
	private static final String KAYTTAJA = "postgres";
	private static final String SALASANA = "12345678";
	
	public static void main(String[] args){
		try {
			Connection con = null;
			Statement stmt = null;
			// luodaan yhteys
			con = DriverManager.getConnection(PROTOKOLLA + "//" + PALVELIN + ":" + PORTTI + "/" + TIETOKANTA, KAYTTAJA, SALASANA);
			stmt = con.createStatement();
			//Alustetaan scanneri
			Scanner sc = new Scanner(System.in);
			
			ResultSet rset = stmt.executeQuery("SELECT * "+"FROM tilit");
			System.out.println("tilit:");
			while(rset.next()){
				System.out.println(rset.getInt("tilinumero")+ " : "+ rset.getInt("saldo"));
			}
			System.out.println("Tili jolta siirretaan:");
			int tili1 = sc.nextInt();
			System.out.println("Tili jolle siirretaan:");
			int tili2 = sc.nextInt();
			System.out.println("Maara joka siirretaan:");
			int maara = sc.nextInt();
			
			stmt.executeUpdate("UPDATE tilit SET saldo = saldo - "+maara+" WHERE tilinumero = "+tili1+";");
			stmt.executeUpdate("UPDATE tilit SET saldo = saldo + "+maara+" WHERE tilinumero = "+tili2+";");
			
			rset = stmt.executeQuery("SELECT * "+"FROM tilit");
			System.out.println("tilit:");
			while(rset.next()){
				System.out.println(rset.getInt("tilinumero")+ " : "+ rset.getInt("saldo"));
			}
		}
		catch(SQLException poikkeus){	
			System.out.println("Tapahtui seuraava virhe: " + poikkeus.getMessage()); 
		}
	}
}
