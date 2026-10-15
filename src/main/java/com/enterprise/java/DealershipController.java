package com.enterprise.java;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class DealershipController {

	@RequestMapping("/")
	public String carSelection(HttpSession session) {
		if(session.isNew())
			session.setAttribute("carSelection", new ArrayList<String>());
		return "SelectCar";
	}
	
	@RequestMapping("/saveCar")
	public String saveCar(HttpSession session,
			@RequestParam String carType) {
		if(session.isNew() || session.getAttribute("carSelection") == null) {
			session.setAttribute("carSelection", new ArrayList<String>());
			return "SelectCar";
		}
		else {
			((ArrayList<String>)session.getAttribute("carSelection")).add(carType);
		return "selectOptions";
		}
	}
	
	@RequestMapping("/saveOptions")
	@ResponseBody
	public String saveOptions(HttpSession session,
			@RequestParam(required=false) String AirConditioning,
			@RequestParam(required=false) String automaticTransmission,
			@RequestParam(required=false) String PowerWindows) {
		if(session.isNew() || session.getAttribute("carSelection") == null) {
			session.setAttribute("carSelection", new ArrayList<String>());
			return "SelectCar";
		}
		else {
			ArrayList<String> list = ((ArrayList<String>)session.getAttribute("carSelection"));
			if(AirConditioning != null) list.add(AirConditioning);
			if(automaticTransmission != null) list.add(automaticTransmission);
			if(PowerWindows != null) list.add(PowerWindows);
			
			String data = "";
			int cost = 0;
			for(String item : list) {
				data += "<br>"+item;
				try {
					String value = item.split(Pattern.quote("$"))[1].replaceAll(",", "");
					System.out.println(value);
					cost += Integer.parseInt(value);
				}
				catch(Exception e) {}
			}

			return "<!DOCTYPE html>"+
			"<html>"+
			"<head>"+
			"<meta charset=\"UTF-8\">"+
			"<title>Car Selection</title>"+
			"</head>"+
			"<body>"+
			data+
			"<br><br>Total: $"+cost+
			"</body>"+
			"</html>"
			;
		}
	}
}
