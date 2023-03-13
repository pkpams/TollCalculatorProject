package com.toll.calculator.myproject.CalculateTollProject;

	import org.json.simple.JSONObject;
	import org.json.simple.parser.JSONParser;
	import org.junit.jupiter.api.Assertions;
	import org.junit.jupiter.api.Test;
	import java.io.FileReader;

	public class TollCalculatorTest {
	    
	    @Test
	    public void testCalculateDistance() {
	        JSONParser parser = new JSONParser();
	        try {
	            Object obj = parser.parse(new FileReader("src/main/interchanges.json"));
	            JSONObject jsonObject = (JSONObject) obj;
	            JSONObject locations = (JSONObject) jsonObject.get("locations");
	            double distance = TollCalculator.calculateDistance(locations, 1, 5);
	            Assertions.assertEquals(25.0, distance);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}



