package com.client.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.client.model.Ville;

@Controller
public class VilleController {

	@GetMapping("/villes")
	public ModelAndView villes(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		List<Ville> villes = new ArrayList<Ville>();

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
			v.setCode_commune_INSEE(jsonObject.getString("code_commune_INSEE"));
			villes.add(v);
		}

		String requestedVille = request.getParameter("ville");
		for (Ville v : villes) {
			if (v.getNomCommune().equals(requestedVille)) {
				mv.addObject("ville", v);
				break;
			}
		}
		mv.addObject("page", page);
		mv.addObject("villes", villes);
		mv.setViewName("villes");

		return mv;
	}

	@PostMapping("/villes")
	public ModelAndView villepost(HttpServletRequest request) throws IOException {
		String codeCommune = request.getParameter("codeCommune");
		String nomCommune = request.getParameter("nomCommune");
		String codePostal = request.getParameter("codePostal");
		String libelle = request.getParameter("libelle");
		String ligne = request.getParameter("ligne");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");

		JSONObject req = new JSONObject();
		req.put("code_commune_INSEE", codeCommune);
		req.put("nomCommune", nomCommune);
		req.put("codePostal", codePostal);
		req.put("libelleAcheminement", libelle);
		req.put("ligne", ligne);
		req.put("latitude", latitude);
		req.put("longitude", longitude);

        URL url = new URL("http://localhost:8181/ville");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(req.toString());
        osw.flush();
        osw.close();

		return this.villes(request);
	}
}
