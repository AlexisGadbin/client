package com.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class Ville {
	
	private String Code_commune_INSEE;
	
	private String nomCommune;
	
	private String codePostal;
	
	private String libelleAcheminement;
	
	private String ligne;
	
	private String latitude;
	
	private String longitude;
	
	public Ville() {
	}
	

	public double distance(Ville ville) {
		double lat1 = Double.parseDouble(this.getLatitude());
		double lon1 = Double.parseDouble(this.getLongitude());
		double lat2 = Double.parseDouble(ville.getLatitude());
		double lon2 = Double.parseDouble(ville.getLongitude());

		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;

		dist = dist * 1.609344;

		return (dist);
	  }

	  private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	  }

	  private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	  }

}
