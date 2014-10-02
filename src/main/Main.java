package main;

import gedcom.GEDCOMAnomaly;
import gedcom.GEDCOMError;
import gedcom.GEDCOMParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class Main
{
	static Options mOptions = new Options();
	static File mInputFile = null;
	static File mOutputFile = null;
	
	public static void main(String[] args)
	{	
		
		mOptions.addOption("help", false, "prints this help message");
		mOptions.addOption("file", true, "input file path");
		mOptions.addOption("out", true, "output file path");
		
		CommandLineParser parser = new BasicParser();
		
		try {
			CommandLine cmd = parser.parse( mOptions, args);
			
			if (cmd.hasOption("help")) {
				usage();
			}
			
			if (cmd.hasOption("file")) {
				File tmpFile = new File(cmd.getOptionValue("file"));
				if (tmpFile.exists()) {
					mInputFile = tmpFile;
				}
			}
			
			if (cmd.hasOption("out")) {
				mOutputFile = new File (cmd.getOptionValue("out"));
			}
			
			
		} catch (org.apache.commons.cli.ParseException e1) {
			System.err.println( "Parsing failed.  Reason: " + e1.getMessage() );
		}
		
		
		if (mInputFile == null) {
			JFileChooser jc = new JFileChooser("data");
		
			jc.setFileFilter(new FileNameExtensionFilter("GEDCOM files", "ged"));
		
			int choice = jc.showOpenDialog(jc);
		
			if (choice != JFileChooser.APPROVE_OPTION) return;

			mInputFile = jc.getSelectedFile();
		}
		
		PrintWriter pwOutput = null;
		
		if (mOutputFile != null) {
			try {
				
				Boolean flag = mOutputFile.createNewFile();
				
				pwOutput = new PrintWriter(mOutputFile);
			} catch (FileNotFoundException e) {
				System.err.println( "Unable to create output file.  Reason: " + e.getMessage() );
			} catch (IOException e) {
				System.err.println( "Unable to create output file.  Reason: " + e.getMessage() );
			}
		}
		
		try
		{
			GEDCOMParser gParser = new GEDCOMParser (mInputFile);
			
			String outputText = "";
			
			outputText = "Individuals - \n";
						
			for (Map.Entry<String, Individual> entry : gParser.getIndividuals().entrySet())
			{
				outputText += entry.getValue().toString() + "\n";
			}
			
			outputText += "\nFamilies - \n";
			
			for(Map.Entry<String, Family> entry : gParser.getFamilies().entrySet())
			{
				Family family = entry.getValue();

				//need to add some null checking to ensure existence
				outputText += "Id:\t\t" + family.getId() + "\n";
				outputText += "Husband Name:\t" + family.getHusband().getName() + "\n";
				outputText += "Wife Name:\t" + family.getWife().getName() + "\n";
		//		outputText += "Child Name:\t" + family.getChild().getName() + "\n\n";
				
				
			}
			
			
			
			gParser.getAnomalies(gParser);
			
			LinkedList<GEDCOMError> errors = gParser.getErrors();
			
			outputText += "\nErrors - \n";
			
			
			if(errors.size() == 0)
			{
				outputText += "None found";
			}
			else
			{
				for(int i = 0; i < errors.size(); i++)
				{
					outputText += (i+1) + ". " + errors.get(i).getMessage() + "\n";
				}
			}
			
			LinkedList<GEDCOMAnomaly> anomalies = gParser.getAnomalies();
			
			outputText += "\nAnomalies - \n";
			
			
			if(errors.size() == 0)
			{
				outputText += "None found";
			}
			else
			{
				for(int i = 0; i < anomalies.size(); i++)
				{
					outputText += (i+1) + ". " + anomalies.get(i).getMessage() + "\n";
				}
			}
			
			
			System.out.print(outputText);
			
			if (pwOutput != null) {
				pwOutput.print(outputText);
				pwOutput.close();
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	/*	GEDCOMParser gParser;
		try {
			gParser = new GEDCOMParser (mInputFile);
		
		for(Map.Entry<String, Family> entry : gParser.getFamilies().entrySet())
		{
			Family fam = entry.getValue();
		
		FindErrorAnomaly fn = new FindErrorAnomaly();
			fn.morethanonespouse(fam.getHusband(), fam);
		}
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}*/
	}
	private static void usage () {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "GEDCOMParser", mOptions );
		System.exit(0);
	}
	
}
