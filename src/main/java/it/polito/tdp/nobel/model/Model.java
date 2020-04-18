package it.polito.tdp.nobel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List<Esame> esami; //partenza
	private double mediaMigliore=0; // qual e' la miglior media fin ora incontrata
	private Set<Esame> soluzioneOttima= null; //insieme di esami che danno media migliore entro quel nuemro di crediti
	
	public Model() {
		EsameDAO dao= new EsameDAO(); 
		this.esami=dao.getTuttiEsami(); 
	}
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		Set<Esame> parziale= new HashSet<>(); 
		ricorsiva(parziale, 0, numeroCrediti); 
		
		
		return soluzioneOttima;
	}

	//complessita' 2^N : metto o non metto
	private void ricorsiva(Set<Esame> parziale, int livello, int m /*numeroCrediti*/) {
		
		//casi di terminazione 
		int crediti= sommaCrediti(parziale); 
		
		if (crediti > m) {
			return; 
		}
		if (crediti==m) {
			double media= calcolaMedia(parziale);
			if (media > mediaMigliore) {
				mediaMigliore= media; 
				soluzioneOttima= new HashSet<>(parziale); 
			}
			
		}
		
		// crediti non ancora sufficienti ovvero < m 
		
		if (livello== esami.size()) {
			//non ci sono piu' esami da considerare 
			return; 
		}
		
		
		// generare i sottoproblemi 
	    // l'Esame e' da aggiungere o no? Provo entrambe le possibilita'
		
		//aggiungo
		parziale.add(esami.get(livello)); 
		ricorsiva(parziale, livello+1, m);
		parziale.remove(esami.get(livello)); //remove(parziale.size()-1); 
		
		//non aggiungo
		ricorsiva(parziale, livello+1, m); 
		
		
	}
	
	//media pesata 
	public double calcolaMedia(Set<Esame> parziale) {
		int crediti=0; 
		int somma=0; 
		
		for (Esame e : parziale) {
			crediti+= e.getCrediti(); 
			somma+= (e.getVoto()*e.getCrediti()); 
		}
		
		return somma/crediti;
	}
	private int sommaCrediti(Set<Esame> parziale) {
		int somma=0; 
		for (Esame e : parziale ) {
			somma+= e.getCrediti(); 
		}
		return  somma;
	}
	
	//altro approccio per risolvere in modo ricorsivo
	// la soluzione non si ha in tempi rapidi infatti al complessita' e' N!
	private void ricorsiva2(Set<Esame> parziale, int livello, int m) {
		//casi di terminazione 
				int crediti= sommaCrediti(parziale); 
				
				if (crediti > m) {
					return; 
				}
				if (crediti==m) {
					double media= calcolaMedia(parziale);
					if (media > mediaMigliore) {
						mediaMigliore= media; 
						soluzioneOttima= new HashSet<>(parziale); 
					}
					
				}
				
				// crediti non ancora sufficienti ovvero < m 
				
				if (livello== esami.size()) {
					//non ci sono piu' esami da considerare 
					return; 
				}
				
				//generazione sotto problemi
				for(Esame e : esami) {
					
					if(!parziale.contains(e)) {
					parziale.add(e); 
					ricorsiva2(parziale, livello+1, m); 
					parziale.remove(e); 
					}
				}
			
		
	}
}
