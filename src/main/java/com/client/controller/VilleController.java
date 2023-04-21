package com.client.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.client.model.Ville;

@Controller
public class VilleController {

	@GetMapping("/villes")
	public ModelAndView villes(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		List<Ville> villes = new ArrayList<>();

		int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

		String uri = "http://localhost:8181/ville";
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(uri, String.class);
		JSONArray jsonArray = new JSONArray(response);
		int max = page > 67 ? jsonArray.length() : page*50;
		for (int i = (page-1)*50; i < max; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Ville v = new Ville();
			v.setLibelleAcheminement(jsonObject.getString("libelleAcheminement"));
			v.setLigne(jsonObject.getString("ligne"));
			v.setLatitude(jsonObject.getString("latitude"));
			v.setLongitude(jsonObject.getString("longitude"));
			v.setCodePostal(jsonObject.getString("codePostal"));
			v.setNomCommune(jsonObject.getString("nomCommune"));
			v.setCodeCommuneInsee(jsonObject.getString("codeCommuneInsee"));
			villes.add(v);
		}
		Ville ville = null;
		String requestedVille = request.getParameter("ville");
		for (Ville v : villes) {
			if (v.getNomCommune().equals(requestedVille)) {
				ville = v;
				break;
			}
		}

		if(ville != null) {
			mv.addObject("ville", ville);

			String url = "https://api.openweathermap.org/data/2.5/weather?lat="+ville.getLatitude()+"&lon="+ville.getLongitude()+"&lang=fr&appid=ed9c9f1ee18ebb8c5346720a6e25abbd";

			String apiKey = "ed9c9f1ee18ebb8c5346720a6e25abbd";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requete = new HttpEntity<>(headers);
			
			ResponseEntity<String> reponse = null;
			try {
				reponse = restTemplate.exchange(url, HttpMethod.GET, requete, String.class);
				JSONObject jo = new JSONObject(reponse.getBody());
				mv.addObject("meteo", true);
				DecimalFormat df = new DecimalFormat("#.##");
				mv.addObject("temperature", df.format(jo.getJSONObject("main").getDouble("temp")-273.15));
				mv.addObject("description", jo.getJSONArray("weather").getJSONObject(0).getString("description"));
				mv.addObject("icon", jo.getJSONArray("weather").getJSONObject(0).getString("icon"));
			} catch (Exception e) {
				throw new Error("Erreur lors de la requête");
			}
		}

		mv.addObject("page", page);
		mv.addObject("villes", villes);
		mv.setViewName("villes");

		return mv;
	}

	@PostMapping("/villes")
	public ModelAndView villepost(HttpServletRequest request) {
		String codeCommune = request.getParameter("codeCommune");
		String nomCommune = request.getParameter("nomCommune");
		String codePostal = request.getParameter("codePostal");
		String libelle = request.getParameter("libelle");
		String ligne = request.getParameter("ligne");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");

		JSONObject req = new JSONObject();
		req.put("codeCommuneInsee", codeCommune);
		req.put("nomCommune", nomCommune);
		req.put("codePostal", codePostal);
		req.put("libelleAcheminement", libelle);
		req.put("ligne", ligne);
		req.put("latitude", latitude);
		req.put("longitude", longitude);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> requete = new HttpEntity<>(req.toString(0), headers);

		ResponseEntity<String> reponse = restTemplate.exchange("http://localhost:8181/ville", HttpMethod.PUT, requete, String.class);

		if (reponse.getStatusCode() != HttpStatus.OK) {
			throw new Error("Erreur lors de la requête");
		}

		return this.villes(request);
	}
}
