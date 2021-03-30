package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso ORDER BY nome";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

			//	System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				
				Corso c=new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
			}
			st.close();
			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	/*public void getCorso(Corso corso) {
		// TODO
	}  ha detto che non serve*/

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		List<Studente> result=new ArrayList<Studente>();
		String sql="SELECT s.matricola, s.cognome, s.nome, s.CDS FROM iscrizione i , studente s WHERE i.matricola=s.matricola and codins=?";
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				int matricola=rs.getInt("matricola");
				String cognome=rs.getString("cognome");
				String nome=rs.getString("nome");
				String cds=rs.getString("CDS");
				Studente s=new Studente(matricola, cognome, nome, cds);
				result.add(s);
			}
			st.close();
			conn.close();
			return result;
		}catch(SQLException e) {
			throw new RuntimeException("Errore DB", e);
		}
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		
		// ritorna true se l'iscrizione e' avvenuta con successo
		final String sql="INSERT INTO iscrizione "
				+ "VALUES (?, ?)";
		
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			int rs=st.executeUpdate();
			st.close();
			conn.close();
			System.out.println(rs);
			if(rs>0)
				return true;
			else
				return false;
			
			
		}catch(SQLIntegrityConstraintViolationException si) {
			return false;
		}catch(SQLException e) {
			throw new RuntimeException("Errore DB", e);
		}
		
		
	
	}

}
