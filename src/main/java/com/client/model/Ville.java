package com.client.model;

import lombok.Data;

@Data
public class Ville {
	
	private String Code_commune_INSEE;
	
	private String nomCommune;
	
	private String codePostal;
	
	private String libelleAcheminement;
	
	private String ligne;
	
	private String Latitude;
	
	private String longitude;
	
	public Ville() {
	}

}
