package it.polito.tdp.lab04.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	public Model() {
		corsoDao=new CorsoDAO();
		studenteDao=new StudenteDAO();
	}
	
	public List<Corso> getTuttiICorsi() {
	
		return this.corsoDao.getTuttiICorsi();
	}
	
	public Studente getStudente(int matricola) {
		
		 return this.studenteDao.getStudenteByMatricola(matricola);
		 
	}
	
	public Corso getCorsoByNome(String nomeCorso) {
		return this.corsoDao.getCorsoByNome(nomeCorso);
	}
	public List<Studente> getStudentiIscrittiAlCorso(Corso c){
		
		return this.corsoDao.getStudentiIscrittiAlCorso(c);
	}
	

	

	
	
	
	
	public List<Corso> getCorsiStudente(Studente s){
		return this.studenteDao.getCorsiStudente(s);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso c) {
		
	
			return this.corsoDao.inscriviStudenteACorso(studente, c);
		
		
		
	}
	
}
