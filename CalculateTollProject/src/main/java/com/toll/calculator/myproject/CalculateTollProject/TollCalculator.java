package com.toll.calculator.myproject.CalculateTollProject;

import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TollCalculator {

	public static void main(String[] args) {
		// Read the JSON file
		int startId=0,endId=0;
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("src/main/interchanges.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject locations = (JSONObject) jsonObject.get("locations");

			// Prompt the user to enter the starting and ending interchange IDs
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter the starting interchange ID: ");
			/* int startId = scanner.nextInt(); */
			try {
			    startId = scanner.nextInt();
			} catch (InputMismatchException e) {
			    System.out.println("Invalid input, please enter an integer value.");
			    }
			System.out.print("Enter the ending interchange ID: ");
			/* int endId = scanner.nextInt(); */
			try {
			    endId = scanner.nextInt();
			} catch (InputMismatchException e) {
			    System.out.println("Invalid input, please enter an integer value.");
			    return; // or exit the program, depending on your requirements
			}
			// Calculate the distance between the two interchanges
			double distance = calculateDistance(locations, startId, endId);
			System.out.println("The distance is " + distance);

			// Calculate the toll cost
			double tollCost = distance * 0.25;

			// Display the toll cost to the user
			System.out.println("The toll cost is $" + tollCost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Calculate the distance between two interchanges
	public static double calculateDistance(JSONObject locations, int startId, int endId) {
		double distance = 0.0;
		
		JSONObject startLocation = (JSONObject) locations.get(Integer.toString(startId));
		JSONObject endLocation = (JSONObject) locations.get(Integer.toString(endId));
		
		if (startLocation==endLocation) {
			System.out.println("StartLocation and EndLoaction cannot be same");
		}else {	
		JSONArray routes = (JSONArray) startLocation.get("routes");

		for (int i = 0; i < routes.size(); i++) {
			JSONObject route = (JSONObject) routes.get(i);
			int toId = Integer.parseInt(route.get("toId").toString());
			double routeDistance = Double.parseDouble(route.get("distance").toString());
 
			if (toId == endId) {
				distance = routeDistance;
				break;

			} else {
				distance = routeDistance + calculateDistance(locations, toId, endId);
			}
			return distance;
		}
		}
		return distance;
		
	}
}

