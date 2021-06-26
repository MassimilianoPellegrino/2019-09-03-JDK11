/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...");
    	String s;
    	int n;
    	try {
    		s = boxPorzioni.getValue();
    		n = Integer.parseInt(txtPassi.getText());
    		for(String st: model.getCammino(s, n)) {
    			this.txtResult.appendText("\n"+st);
    		}
    		txtResult.appendText("\nPeso: "+model.getBestW());
    		
    	}catch(NullPointerException e){
    		txtResult.setText("Selezionare un tipo di porzione");
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire un numero");
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...");
    	String s;
    	try {
    		s = boxPorzioni.getValue();
    		
    		for(DefaultWeightedEdge edge: model.getAdiacenti(s))
    			txtResult.appendText("\n"+edge+" "+model.getGrafo().getEdgeWeight(edge));
    		
    		
    	}catch(NullPointerException e){
    		txtResult.setText("Selezionare un tipo di porzione");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	boxPorzioni.getItems().clear();
    	txtResult.appendText("Creazione grafo...");
    	double c;
    	try {
    		c = Double.parseDouble(this.txtCalorie.getText());
    		model.creaGrafo(c);
    		txtResult.appendText("\nGrafico creato, con "+model.getGrafo().vertexSet().size()+" vertici e "+model.getGrafo().edgeSet().size()+" archi");
    		this.boxPorzioni.getItems().addAll(model.getGrafo().vertexSet());
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire un numero");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
