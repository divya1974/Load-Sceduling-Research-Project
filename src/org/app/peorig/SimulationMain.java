package org.app.peorig;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public class SimulationMain {
	
	static float area=10000;
	static float efficiency = 0.15f;
	public static void main(String[] args) 
			throws IOException, InterruptedException, ParserConfigurationException, SAXException, TransformerException {
        
//		Running python prediction code
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println(sdf.format(Calendar.getInstance().getTime()));
		//boolean runpython = true;
		//runPythonCodes(runpython);  
//		Setting up variables - running simulation
	
		//String loadforwardfile = "C:/Users/sharmd14/Downloads/Green-Energy-Optimisation---PE-master/Green-Energy-Optimisation---PE-master/Green Energy Optimisation - PE/logs/loadforward.log";	
    	String tracefile = "resources/trace/high_service_time_bada_3.txt";	//high_service_time2
    	String datacenterxml = "resources/datacenters.xml";
    	double threshold = 0.40;
    	int i;
    	/*for(i=10;i<=15;i++)
    	{*/
           
    		// System.out.println("Day "+i);
    	     String load_prefix = "resources/PredLoad/load_pred_file_8"+"_";
    		
    	//output files scheduled same time zone
    /*
    	String loadforwardfile = "resources/logs/loadforward.log/loadforward.log";
    	String logfile = "resources/logs/simulation.log";
    	String energy_prefix = "resources/PredEnergy/energy_pred_file_mst_";
    	String jobloadstr = "resources/PowerConsumption/JobLoad_mst_";
    	String workingenergy="resources/WorkingEnergy/workingenergy_mst_";
    	String renenergyproduced="resources/RenEnergy/renenergy_mst_";
    	String renenergyused="resources/RenEnergyUsed/renenergyused_mst_";
    	String totalresults="resources/TotalResult/result_sched_mst_";
    	String act_energy_prefix = "resources/ActualEnergy/energy_act_file_mst_";
    	*/
    	
    	//output files scheduled diff time zone
  	
    	String loadforwardfile = "resources/logs/loadforward.log/loadforward.log";
    	String logfile = "resources/logs/simulation.log";
    	String energy_prefix = "resources/PredEnergy/energy_pred_file";
    	String jobloadstr = "resources/PowerConsumption/JobLoad_8"+"_";
    	String workingenergy="resources/WorkingEnergy/workingenergy_8"+"_";
    	String renenergyproduced="resources/RenEnergy/renenergy_8"+"_";
    	String renenergyused="resources/RenEnergyUsed/renenergyused_8"+"_";
    	String totalresults="resources/TotalResult/result_sched_8"+"_";
    	String act_energy_prefix = "resources/ActualEnergy/energy_act_file_8"+"_";
        Simulator sim = new Simulator(tracefile, datacenterxml, threshold, 
        		loadforwardfile, logfile, energy_prefix, load_prefix, act_energy_prefix, jobloadstr, workingenergy, renenergyproduced, totalresults, renenergyused);
        
        sim.runSimulation(1);
        System.out.println();
        System.out.println(sdf.format(Calendar.getInstance().getTime()));
        System.out.println();
        
//      output files non schedule same time zone
       
  /*    loadforwardfile = "resources/logs/loadforward_nosched.log/loadforward_nonsched.log";
    	logfile = "resources/logs/simulation_nosched.log";
    	energy_prefix = "resources/PredEnergy_nosched/energy_nosched";
    	jobloadstr = "resources/PowerConsumption/JobLoad_nosched_mst_";
    	workingenergy="resources/WorkingEnergy/workingenergy_nosched_mst_";
    	renenergyproduced="resources/RenEnergy/renenergy_mst_";
    	totalresults="resources/TotalResult/result_nosched_mst_";
    	renenergyused="resources/RenEnergyUsed/renenergyused_nosched_mst_"; */
    
//      output files non schedule diff time zone
        
        loadforwardfile = "resources/logs/loadforward_nosched.log/loadforward_nonsched.log";
    	logfile = "resources/logs/simulation_nosched.log";
    	energy_prefix = "resources/PredEnergy_nosched/energy_nosched";
    	jobloadstr = "resources/PowerConsumption/JobLoad_nosched_8"+"_";
    	workingenergy="resources/WorkingEnergy/workingenergy_nosched_8"+"_";
    	renenergyproduced="resources/RenEnergy/renenergy_8"+"_";
    	totalresults="resources/TotalResult/result_nosched_8"+"_";
    	renenergyused="resources/RenEnergyUsed/renenergyused_nosched_8"+"_"; 
    	
    	Simulator nosched = new Simulator(tracefile, datacenterxml, threshold, 
    			loadforwardfile, logfile, energy_prefix, load_prefix, act_energy_prefix, jobloadstr, workingenergy, renenergyproduced, totalresults, renenergyused);
        nosched.runSimulation(0);
        
		System.out.println(sdf.format(Calendar.getInstance().getTime()));
		
    	}
//	}
	
	static void runPythonCodes(boolean runthis) throws IOException, InterruptedException {
//		if(args.length==0) {
//		if(args.length!=0 && args[0].equals("1")) {
		if(runthis) {
			  
	        	System.out.println("Running python code");
	        	String energyPython = "python resources/python/EnergyPrediction.py";
	        	//String energyPython = "python ./python_multiday/EnergyPrediction.py";
	            
	        	Process e1 = Runtime.getRuntime().exec(energyPython + " "
	            		+ "resources/Input_Energy/Input_Energy_mst_1.csv "
	            		+ "resources/PredEnergy/energy_pred_file_mst_1 "
	            		+ "resources/ActualEnergy/energy_act_file_mst_1 "
	            		+ area +" "+
	            		+ efficiency);
	          //  processBuilder.redirectErrorStream(true);
	            
	            Process e2 = Runtime.getRuntime().exec(energyPython + " "
	            		+ "resources/Input_Energy/Input_Energy_mst_2.csv "
	            		+ "resources/PredEnergy/energy_pred_file_mst_2 "
	            		+ "resources/ActualEnergy/energy_act_file_mst_2 "
	            		+ area +" "+
	            		+ efficiency);
	            Process e3 = Runtime.getRuntime().exec(energyPython + " "
	            		+ "resources/Input_Energy/Input_Energy_mst_3.csv "
	            		+ "resources/PredEnergy/energy_pred_file_mst_3 "
	            		+ "resources/ActualEnergy/energy_act_file_mst_3 "
	            		+ area +" "+
	            		+ efficiency);
	            Process e4 = Runtime.getRuntime().exec(energyPython + " "
	            		+ "resources/Input_Energy/Input_Energy_mst_4.csv "
	            		+ "resources/PredEnergy/energy_pred_file_mst_4 "
	            		+ "resources/ActualEnergy/energy_act_file_mst_4 "
	            		+ area +" "+
	            		+ efficiency);
	            
	          /*  Process e5 = Runtime.getRuntime().exec(energyPython + " "
	            		+ "resources/Input_Energy/Input_Energy_1_5.csv "
	            		+ "resources/PredEnergy/energy_pred_file_1_5 "
	            		+ "resources/ActualEnergy/energy_act_file_1_5 "
	            		+ area +" "+
	            		+ efficiency);
	            */
	            e1.waitFor(); e2.waitFor(); e3.waitFor(); e4.waitFor();
	            System.out.println("Energy prediction ended:\n"+e1.exitValue()+" "+e2.exitValue()+" "+e3.exitValue()+" "+e4.exitValue());
	            
	           
	            String loadPython = "python resources/python/LoadPrediction.py ";
	           // String loadPython = "python ./python/LoadPrediction.py ";
	            
	            Process l1 = Runtime.getRuntime().exec(loadPython + " "
	            		+ "resources/Input_Load/Load_input_1_1.csv "
	            		+ "resources/PredLoad/load_pred_file_1_1 "
	            		+ "resources/ActualLoad/load_act_file_1_1");
	            
	            BufferedReader in = new BufferedReader(new InputStreamReader(l1.getInputStream()));
	            while(l1.isAlive()) {
	            	System.out.println(in.readLine());       	
               //  System.out.println(ret);

	            }  
	            System.out.println(1);
	            Process l2 = Runtime.getRuntime().exec(loadPython + " "
	            		+ "resources/Input_Load/Load_input_1_2.csv "
	            		+ "resources/PredLoad/load_pred_file_1_2 "
	            		+ "resources/ActualLoad/load_act_file_1_2");
	            in = new BufferedReader(new InputStreamReader(l2.getInputStream()));
	            
	            while(l2.isAlive()) {
	            	System.out.println(in.readLine());
	            }
	            System.out.println(2);
	            
	            Process l3 = Runtime.getRuntime().exec(loadPython + " "
	            		+ "resources/Input_Load/Load_input_1_3.csv "
	            		+ "resources/PredLoad/load_pred_file_1_3 "
	            		+ "resources/ActualLoad/load_act_file_1_3");
	            in = new BufferedReader(new InputStreamReader(l3.getInputStream()));
	            while(l3.isAlive()) {
	            	System.out.println(in.readLine());
	            }
	            System.out.println(3);
	            
	            Process l4 = Runtime.getRuntime().exec(loadPython + " "
	            		+ "resources/Input_Load/Load_input_1_4.csv "
	            		+ "resources/PredLoad/load_pred_file_1_4 "
	            		+ "resources/ActualLoad/load_act_file_1_4");
	            in = new BufferedReader(new InputStreamReader(l4.getInputStream()));
	            while(l4.isAlive()) {
//	            	if(!in.ready() || in.readLine()==null)
//	              		 l4.destroy();
	            	System.out.println(in.readLine());
	            }
	            
	            System.out.println(4);
	         
	            
	       /*     Process l5 = Runtime.getRuntime().exec(loadPython + " "
	            		+ "resources/Input_Load/Load_input_1_5.csv "
	            		+ "resources/PredLoad/load_pred_file_1_5 "
	            		+ "resources/ActualLoad/load_act_file_1_5");
	            in = new BufferedReader(new InputStreamReader(l5.getInputStream()));
	            while(l5.isAlive()) {
	            	System.out.println(in.readLine());
	            }
	            System.out.println(5);*/
	            
	            System.out.println("Load prediction ended:\n"+l1.exitValue()+" "+l2.exitValue()+" "+l3.exitValue()+" "+l4.exitValue());
	        }
	        else
	            System.out.println("Not Running Python Code");
			System.out.println();
	}
}

//HPC2N-2002-2.2-cln_v3.swf
//SDSC-BLUE-2000-4.2-cln.swf"
//HPC2N-2002-2.1-cln_v2.swf"
