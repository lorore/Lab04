package it.polito.tdp.lab04.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	private List<Corso> corsi;
	public Model() {
		corsoDao=new CorsoDAO();
		studenteDao=new StudenteDAO();
	}
	
	public List<String> getTuttiICorsi() {
		corsi=new ArrayList<Corso>(this.corsoDao.getTuttiICorsi());
		return this.dammiNomiCorsi(corsi);
	}
	
	public Studente getStudente(int matricola) {
		
		 return this.studenteDao.getStudenteByMatricola(matricola);
		 
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(String nomeCorso){
		Corso c=this.trovaCorso(nomeCorso);
		return this.corsoDao.getStudentiIscrittiAlCorso(c);
	}
	
	private List<String> dammiNomiCorsi(List<Corso> corsi){
		List<String> lista=new ArrayList<String>();
		lista.add("");
		for(Corso c: corsi) {
			lista.add(c.getNome());
		}
		return lista;
	}
	
	private Corso trovaCorso(String nomeCorso) {
		for(Corso c: corsi) {
			if(c.getNome().equals(nomeCorso))
				return c;
		}
		
		return null;
	}
	
	public boolean matricolaEsiste(int matricola) {
		return this.studenteDao.matricolaEsiste(matricola);
	}
	
	public boolean isStudenteIscrittoAlCorso(int matricola,String nomeCorso) {
		Corso c=this.trovaCorso(nomeCorso);
		return this.studenteDao.isStudenteIscrittoAlCorso(matricola, c.getCodins());
	}
	
	public List<Corso> getCorsiStudente(int matricola){
		return this.studenteDao.getCorsiStudente(matricola);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, String nomeCorso) {
		Corso c=this.trovaCorso(nomeCorso);
	
			return this.corsoDao.inscriviStudenteACorso(studente, c);
		
		
		
	}
	
}
