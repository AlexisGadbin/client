package com.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.client.model.Ville;

@Controller
public class VilleController {

    @RequestMapping("/villes")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		List<Ville> villes = new ArrayList<Ville>();

		String uri = "http://localhost:8181/ville";
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(uri, String.class);
		JSONArray jsonArray = new JSONArray(response);
		for (int i = 0; i < jsonArray.length(); i++) {
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
	
		mv.addObject("villes", villes);
		mv.setViewName("villes");

		return mv;
	}
}
