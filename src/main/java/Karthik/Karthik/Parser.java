package Karthik.Karthik;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.json.JSONObject;

public class Parser {

	private String file_path;
	public Parser(String fl){
		  file_path=fl;
	}
	public String parse(){
		File file = new File(file_path); 
		  String test="";
		  JSONObject record;
		  BufferedReader br;
		  try{
		  br = new BufferedReader(new FileReader(file)); 
		  String st; 
		  
		  while ((st = br.readLine()) != null) 
		  {  //System.out.println(st); 
		  	test+=st;}
		  }catch(Exception e){
			  System.out.println(e);
		  }	
		  //System.out.println(test);
		  record=new JSONObject(test);
		 // System.out.println(record.getString("clientContext")+"\n"+record.getNumber("expirationMillis").longValue()+"\n"+record.getNumber("status").intValue()+"\n"+record.getString("uId")+"\n"+record.getJSONObject("user").toString()+"\n"+record.getJSONObject("location").toString()+"\n");
		  return record.getString("clientContext")+"\n"+record.getNumber("expirationMillis").longValue()+"\n"+record.getNumber("status").intValue()+"\n"+record.getString("uId")+"\n"+record.getJSONObject("user").toString()+"\n"+record.getJSONObject("location").toString()+"\n";
		}
	}

