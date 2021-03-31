package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	
	
	public Studente getStudenteByMatricola(int matricola) {
		
		final String sql="SELECT * "
				+ "FROM studente "
				+ "WHERE matricola=? ";

		
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs=st.executeQuery();
			
			if(rs.next()) {
			int mat=rs.getInt("matricola");
			String cognome=rs.getString("cognome");
			String nome=rs.getString("nome");
			String cds=rs.getString("cds");
			
			Studente s=new Studente(mat, cognome, nome, cds);
			
			
			st.close();
			conn.close();
			return s;
			}
			else {
				st.close();
				conn.close();
				return null;
				
			}
			
		}catch(SQLException e) {
			throw new RuntimeException("Errore Db", e);
		}
		
	
	
	
	
	}
	

	
		public List<Corso> getCorsiStudente(Studente s){
		
		List<Corso> result=new ArrayList<Corso>();
		final String sql="SELECT c.codins, c.crediti, c.nome, c.pd "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins=i.codins AND i.matricola=? ";
		
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			ResultSet rs=st.executeQuery();
			
			
			
			while(rs.next()) {
				 String codins=rs.getString("codins");
				 int numeroCrediti=rs.getInt("crediti");
				 String nome=rs.getString("nome");
				 int periodoDidattico=rs.getInt("pd");
				 Corso c=new Corso(codins, numeroCrediti, nome, periodoDidattico);
				 result.add(c);
			}
			st.close();
			conn.close();
			return result;
		}catch(SQLException e) {
			throw new RuntimeException("Error DB", e);
		}
		
	}
		
		public boolean isStudenteIscrittoAlCorso(int matricola,String codins) {
			final String sql="SELECT * "
					+ "FROM iscrizione "
					+ "WHERE matricola=? AND codins=? ";
			
			try {
				Connection conn=ConnectDB.getConnection();
				PreparedStatement st=conn.prepareStatement(sql);
				st.setInt(1, matricola);
				st.setString(2, codins);
				ResultSet rs=st.executeQuery();
				st.close();
				conn.close();
				return rs.next();
			}catch(SQLException e) {
				throw new RuntimeException("Error DB", e);
			}
		}
}
