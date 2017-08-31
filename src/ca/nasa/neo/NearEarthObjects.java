package ca.nasa.neo;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class NearEarthObjects {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		AstroidsCalculationsImpl aci = new AstroidsCalculationsImpl();
		String jsonObjects = aci.earthObjects();
        
        String values = new NearEarthObjects().parseJsonObjects(jsonObjects);
        String numberOfNeo = values.split("/")[0].toString();
        String largestNeo_reference_id = values.split("/")[1].toString().split(",")[0];
        String closestNeo_refference_id= values.split("/")[2].toString().split(",")[0];
        
        String  largestNeoDetails = aci.getDetailsNeo(largestNeo_reference_id);
        String closestNeoDetails = aci.getDetailsNeo(closestNeo_refference_id);
        
        try (PrintStream out = new PrintStream(new FileOutputStream("result.txt"))) {
            out.println("List of NEOs : "+numberOfNeo);
            out.println("Largest Neo Details :"+largestNeoDetails);
            out.println("Closest Neo Details :"+closestNeoDetails);
        }
        
	}
	/**
	 * Json Object parser.
	 * 
	 * @param jsonObjects
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String parseJsonObjects(String jsonObjects) throws Exception{
		 Object obj = new JSONParser().parse(jsonObjects);
         
	        // typecasting obj to JSONObject
	        JSONObject neoDetails= (JSONObject) obj;
	        HashMap largestNeoMap = new HashMap();
	        HashMap closestNeoMap = new HashMap();
	        JSONArray neo = (JSONArray) neoDetails.get("near_earth_objects");
	        for (Object item : neo){
	        	 JSONObject diameters= (JSONObject) item;
	        	 String neo_reference_id = (String) diameters.get("neo_reference_id");
	        	 double absolute_magnitude_h = (double) diameters.get("absolute_magnitude_h");
	        	 JSONObject estimated_diameter = (JSONObject) diameters.get("estimated_diameter");
	        	 JSONObject kilometers = (JSONObject)estimated_diameter.get("kilometers");
	        	 double estimated_diameter_max = (double)kilometers.get("estimated_diameter_max");
	        	 
	        	 largestNeoMap.put(estimated_diameter_max,neo_reference_id);
	        	 closestNeoMap.put(absolute_magnitude_h,neo_reference_id);
	        	//System.out.println(neo_reference_id+ "/n");
	        	
	        }
	       	      	       //number of NEOs /largest NEO reference id, diameter / closest NEO reference id , magnitude
	      String values = neo.size()+"/"+largestNeoMap.get(Collections.max(largestNeoMap.keySet()))+","+Collections.max(largestNeoMap.keySet())+"/"+closestNeoMap.get(Collections.max(closestNeoMap.keySet()))+","+Collections.max(closestNeoMap.keySet());
	       
	        
		return values ;
	}
	
	
}
