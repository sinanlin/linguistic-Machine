package com.mikelab;

import java.io.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class ExperimentMachine{

  private Map<String, String> mMenu;
  private BufferedReader mReader;
  private Map<String, String> mdataSet;


  public ExperimentMachine(){

      mReader = new BufferedReader(new InputStreamReader(System.in));
      mMenu = new HashMap<String, String>();
      mdataSet = new TreeMap<String, String>();


      mMenu.put("Add","Adding new data");
      mMenu.put("Display","Displaying all data");
      mMenu.put("quit","Exit the program");
  }


  public void run(){
    String choice = "";

    do{
      try{
        choice = promptAction();

        switch(choice){
          case "add":
          promptNewData();
          break;

          case "display":
          displayData();
          break;

          case "quit":
          System.out.println("Thanks for playing!");
          break;

          default:
          System.out.printf("Unknown choice: '%s'. Try again. %n%n",choice);
          break;
        }


      }catch(IOException ioe){
        System.out.println("Problem with input");
        ioe.printStackTrace();
      }
    }while(!choice.equals("quit"));

  }

  private String promptAction() throws IOException{

    for(Map.Entry<String, String> option : mMenu.entrySet()){
    System.out.printf("%s - %s %n",
                      option.getKey(),
                      option.getValue());
  }

    System.out.print("What would you like to do: ");
    String choice = mReader.readLine();

    return choice.trim().toLowerCase();
  }


  private void promptNewData() throws IOException{
    System.out.print("Enter the data key: ");
    String key = mReader.readLine();
    System.out.print("Enter the data value: ");
    String value = mReader.readLine();

    mdataSet.put(key,value);
  }

  private void displayData(){

    int NextLine = 0;

    for(Map.Entry<String, String> entry: mdataSet.entrySet()){
      NextLine = NextLine + 1;
      if(NextLine == 6){
        System.out.printf("%n");
        NextLine = 1;
      }
      System.out.printf("<%s, %s> ",
                        entry.getKey(),
                        entry.getValue());
    }

    System.out.printf("%n");
  }

  public void exportTo(String fileName){

    try(
      FileOutputStream fos = new FileOutputStream(fileName);
      PrintWriter writer = new PrintWriter(fos);
    ){
      for(Map.Entry<String, String> entry: mdataSet.entrySet()){
        writer.printf("%s|%s|%n",
                  entry.getKey(),
                  entry.getValue());
      }

    }catch(IOException ioe){
      System.out.printf("Problem saving %s %n",fileName);
      ioe.printStackTrace();
    }
  }

  public void importFrom(String fileName){
    try(
      FileInputStream fis = new FileInputStream(fileName);
      BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
    ){
      String line;
      while((line = reader.readLine()) != null){
        String[] args = line.split("\\|");
        //Add those data back to the Map.
        mdataSet.put(args[0],args[1]);
      }
    }catch(IOException ioe){
      System.out.printf("Problem loading %s %n",fileName);
      ioe.printStackTrace();
    }
  }


}
