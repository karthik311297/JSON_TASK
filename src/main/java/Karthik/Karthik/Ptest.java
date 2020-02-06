package Karthik.Karthik;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

//import org.json.JSONObject;
import org.junit.Test;

public class Ptest {

	@Test
	public void test() {
		 Parser np=new Parser("D:\\ECLIPSE PROJECTS\\First\\Karthik\\Resources\\Jsresp"); 
		 File file=new File("D:\\ECLIPSE PROJECTS\\First\\Karthik\\Resources\\file.txt");
		  String test="";
		  BufferedReader br;
		  try{
		  br = new BufferedReader(new FileReader(file)); 
		  String st; 
		  
		  while ((st = br.readLine()) != null) 
		  { // System.out.println(st); 
		  	 test+=st+"\n";}
		  }catch(Exception e){
			  System.out.println(e);
		  }	
		  System.out.println(test);
		  String xt=np.parse();
		  System.out.println(xt);
		  assertEquals(test,xt);
	}

}
