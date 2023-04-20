package com.client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.client.model.Ville;

@Controller
public class HomeController {
    
    @RequestMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		List<Ville> villes = new ArrayList<>();

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
			v.setCodeCommuneInsee(jsonObject.getString("codeCommuneInsee"));
			villes.add(v);
		}

		mv.addObject("villes", villes);
		mv.setViewName("home");

		return mv;
	}

    @PostMapping("/")
    public ModelAndView homePost(HttpServletRequest request) {

        String ville1 = request.getParameter("ville1");
        String ville2 = request.getParameter("ville2");

        
		String uri1 = "http://localhost:8181/ville/"+ville1;
		RestTemplate restTemplate = new RestTemplate();

		String response1 = restTemplate.getForObject(uri1, String.class);
		JSONObject jsonObject1 = new JSONObject(response1);
        Ville v1 = new Ville();
        v1.setLibelleAcheminement(jsonObject1.getString("libelleAcheminement"));
        v1.setLigne(jsonObject1.getString("ligne"));
        v1.setLatitude(jsonObject1.getString("latitude"));
        v1.setLongitude(jsonObject1.getString("longitude"));
        v1.setCodePostal(jsonObject1.getString("codePostal"));
        v1.setNomCommune(jsonObject1.getString("nomCommune"));
        v1.setCodeCommuneInsee(jsonObject1.getString("codeCommuneInsee"));

        String uri2 = "http://localhost:8181/ville/"+ville2;

		String response2 = restTemplate.getForObject(uri2, String.class);
		JSONObject jsonObject2 = new JSONObject(response2);
        Ville v2 = new Ville();
        v2.setLibelleAcheminement(jsonObject2.getString("libelleAcheminement"));
        v2.setLigne(jsonObject2.getString("ligne"));
        v2.setLatitude(jsonObject2.getString("latitude"));
        v2.setLongitude(jsonObject2.getString("longitude"));
        v2.setCodePostal(jsonObject2.getString("codePostal"));
        v2.setNomCommune(jsonObject2.getString("nomCommune"));
        v2.setCodeCommuneInsee(jsonObject2.getString("codeCommuneInsee"));

        double distance = v1.distance(v2);

		ModelAndView mv = this.home();

        mv.addObject("ville1", v1);
        mv.addObject("ville2", v2);
        mv.addObject("distance", distance);

		return mv;
    }
}
