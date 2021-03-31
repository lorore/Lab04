/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;



import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;


public class FXMLController {

	private Model model;
	
	   @FXML
	    private TextField txtNome;

	    @FXML
	    private TextField txtCognome;
	
	  @FXML
	    private TextField txtMatricola;
	
	  @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private ComboBox<String> comboBoxCorsi;

	    @FXML
	    private Button btnCercaIscrittiCorso;

	    @FXML
	    private Button btnCheck;

	    @FXML
	    private Button btnCercaCorsi;

	    @FXML
	    private Button btnCerca;

	    @FXML
	    private TextArea txtResult;

	    @FXML
	    private Button btnReset;

	    @FXML
	    void handleCerca(ActionEvent event) {
	    	txtResult.clear();
	    	String nomeCorso=comboBoxCorsi.getValue();
	    	if(nomeCorso==null || nomeCorso.isEmpty() ) {
	    		txtResult.setText("Errore: non è stato selezionato alcun corso");
	    		return;
	    	}
	    	String matricola=txtMatricola.getText();
	    	if(matricola==null || matricola.isEmpty()) {
	    		txtResult.setText("Per piacere inserire un numero di matricola" );
	    		return;
	    	}
	    	
	    	matricola=matricola.replaceAll(" ", "");
	    	int mat=0;
	    	try {
	    		mat=Integer.parseInt(matricola);
	    		
	    	}catch(NumberFormatException ne) {
	    		txtResult.setText("Errore: la matricola deve essere un numero intero!");
	    		return;
	    	}
	    	
	 
	    	Corso c=this.model.getCorsoByNome(nomeCorso);
	    	if(c==null) {
	    		txtResult.setText("Non esiste nessun corso chiamato "+nomeCorso);
	    		return;
	    	}
	    	Studente s=this.model.getStudente(mat);
	    	if(s==null) {
	    		txtResult.setText("La matricola " +mat+" non corrisponde a nessuno studente");
	    		return;
	    	}else {
	    	boolean iscrittoCorrettamente=this.model.inscriviStudenteACorso(s, c);
	    	if(iscrittoCorrettamente) {
	    		txtResult.setText("Studente iscritto correttamente");
	    		return;
	    	}else {
	    		txtResult.setText("Studente già iscritto a questo corso");
    			return;
	    	}
	    	}
	    }

	    @FXML
	    void handleCercaCorsi(ActionEvent event) {
	    	txtResult.clear();
	    	String matricola=txtMatricola.getText();
	    	if(matricola==null || matricola.isEmpty()) {
	    		txtResult.setText("Il campo è vuoto, per piacere inserire un numero di matricola" );
	    		return;
	    	}
	    	
	    	matricola=matricola.replaceAll(" ", "");
	    	int mat=0;
	    	try {
	    		mat=Integer.parseInt(matricola);
	    		
	    	}catch(NumberFormatException ne) {
	    		txtResult.setText("Errore: la matricola deve essere un numero intero!");
	    		return;
	    	}
	    	
	    	
	    	Studente s=this.model.getStudente(mat);
	    	if(s==null) {
	    		txtResult.setText("La matricola " +mat+" non corrisponde a nessuno studente");
	    		return;
	    	}
	    	else {
	    	List<Corso> listaCorsi=this.model.getCorsiStudente(s);
	    	
	    	
	    	
	    	if(listaCorsi.size()==0) {
	    		txtResult.setText("La matricola " +mat+" non corrisponde a nessuno studente");
	    		//basta questo perchè nello schema ER dice che relazione con iscrizione è (1,N)
	    		//txtResult.setText("La matricola non risulta iscritta ad alcun corso");
	    		return;
	    	}else {
	    		for(Corso c: listaCorsi) {
	    			txtResult.appendText(String.format("%-10s", c.getCodins()));
	    			txtResult.appendText(String.format("%-5s", c.getNumeroCrediti()));
	    			txtResult.appendText(String.format("%-40s", c.getNome()));
	    			txtResult.appendText(String.format("%-5s", c.getPeriodoDidattico()));
	    			txtResult.appendText("\n");
	    			
	    		}
	    		return;
	    	}
	    	}
	    }

	    @FXML
	    void handleCercaIscrittiCorso(ActionEvent event) {
	    	txtResult.clear();
	    	String nomeCorso=comboBoxCorsi.getValue();
	    	if(nomeCorso==null || nomeCorso.isEmpty() ) {
	    		txtResult.setText("Errore: non è stato selezionato alcun corso");
	    		return;
	    	}
	    	Corso c=this.model.getCorsoByNome(nomeCorso);
	    	if(c==null) {
	    		txtResult.setText("Non esiste nessun corso chiamato "+nomeCorso);
	    		return;
	    	}else {
	    		
	    	
	    	List<Studente> studentiCorso=this.model.getStudentiIscrittiAlCorso(c);
	    	if(studentiCorso.size()==0) {
	    		txtResult.setText("Non risultano studenti iscritti a questo corso");
	    		return;
	    	}else {
	    		for(Studente s: studentiCorso) {
	    			txtResult.appendText(String.format("%-10s", s.getMatricola()));
	    			txtResult.appendText(String.format("%-20s", s.getCognome()));
	    			txtResult.appendText(String.format("%-20s", s.getNome()));
	    			txtResult.appendText(String.format("%-10s", s.getCds()));
	    			txtResult.appendText("\n");
	    		}
	    		return;
	    	}
	    	}
	    }

	    @FXML
	    void handleCheck(ActionEvent event) {
	    	String mat=this.txtMatricola.getText();
	    	
	    	
	    	if(mat==null || mat.isEmpty()) {
	    		txtResult.setText("Il campo è vuoto, per piacere inserire un numero di matricola");
	    		return;
	    	}
	    	
	    	String matr=mat.replaceAll(" ", "");
	    	int matricola=0;
	    	try {
	    		matricola=Integer.parseInt(matr);
	    	}catch(NumberFormatException ne) {
	    		txtResult.setText("Errore: la matricola deve essere un numero intero!");
	    		return;
	    	}
	    	
	    	Studente s=this.model.getStudente(matricola);
	    	if(s==null) {
	    		txtResult.setText("La matricola " + matr  +" non corrisponde a nessuno studente");
	    		return;
	    	}else {
	    		txtNome.setText(s.getNome());
	    		txtCognome.setText(s.getCognome());
	    		return;
	    	}

	    }

	    @FXML
	    void handleReset(ActionEvent event) {
	    	this.txtCognome.clear();
	    	this.txtNome.clear();
	    	this.txtMatricola.clear();
	    	this.txtResult.clear();
	    	
	    }
	    
	    public void setModel(Model model) {
			this.model=model;
		//	List<String> nomiCorsi=this.model.getTuttiICorsi();
			List<Corso> corsi=this.model.getTuttiICorsi();
			List<String> nomiCorsi=new ArrayList<String>();
			nomiCorsi.add("");
			for(Corso c: corsi)
				nomiCorsi.add(c.getNome());
			
	    	comboBoxCorsi.getItems().addAll(nomiCorsi);
		}

	    @FXML
	    void initialize() {
	        assert comboBoxCorsi != null : "fx:id=\"comboBoxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert btnCheck != null : "fx:id=\"btnCheck\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
	    }
}