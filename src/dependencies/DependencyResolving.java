/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dependencies;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Vladimir
 */
public class DependencyResolving {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JSONParser parser = new JSONParser(); //we use JSONParser in order to be able to read from JSON file
        try { //here we declare the file reader and define the path to the file dependencies.json
            Object obj = parser.parse(new FileReader("C:\\Users\\Vladimir\\Documents\\NetBeansProjects\\DependenciesResolving\\src\\dependencies\\dependencies.json"));
            JSONObject project = (JSONObject) obj; //a JSON object containing all the data in the .json file
            JSONArray dependencies = (JSONArray) project.get("dependencies"); //get array of objects with key "dependencies"
            System.out.print("We need to install the following dependencies: ");
            Iterator<String> iterator = dependencies.iterator(); //define an iterator over the array "dependencies"
            while (iterator.hasNext()) {
                System.out.println(iterator.next()); 
            }//on the next line we declare another object, which parses a Parser object and reads from all_packages.json
            Object obj2 = parser.parse(new FileReader("C:\\Users\\Vladimir\\Documents\\NetBeansProjects\\DependenciesResolving\\src\\dependencies\\all_packages.json"));
            JSONObject tools = (JSONObject) obj2; //a JSON object containing all thr data in the file all_packages.json
            for (int i = 0; i < dependencies.size(); i++) {
                if (tools.containsKey(dependencies.get(i))) {
                    System.out.println("In order to install " + dependencies.get(i) + ", we need the following programs:");
                    JSONArray temporaryArray = (JSONArray) tools.get(dependencies.get(i)); //a temporary JSON array in which we store the keys and values of the dependencies
                    for (i = 0; i < temporaryArray.size(); i++) {
                        System.out.println(temporaryArray.get(i));
                    }
                    ArrayList<Object> arraysOfJsonData = new ArrayList<Object>(); //an array in which we will store the keys of the objects, after we use the values and won't need them anymore
                    for (i = 0; i < temporaryArray.size(); i++) {
                        System.out.println("Installing " + temporaryArray.get(i));
                    }
                    while (!temporaryArray.isEmpty()) {

                        for (Object element : temporaryArray) {

                            if (tools.containsKey(element)) {
                                JSONArray secondaryArray = (JSONArray) tools.get(element); //a temporary array within the scope of the if-statement
                                if (secondaryArray.size() != 0) {
                                    System.out.println("In order to install " + element + ", we need ");
                                }
                                for (i = 0; i < secondaryArray.size(); i++) {
                                    System.out.println(secondaryArray.get(i));
                                }

                                for (Object o : secondaryArray) {

                                    arraysOfJsonData.add(o);
                                    //here we create a file with the installed dependency
                                    File file = new File("C:\\Users\\Vladimir\\Documents\\NetBeansProjects\\DependenciesResolving\\src\\dependencies\\installed_modules\\" + o);
                                    if (file.createNewFile()) {
                                        System.out.println(file.getName() + " is installed!");
                                    } else {
                                    }
                                }
                                secondaryArray.clear();
                            }
                        }
                        temporaryArray.clear();
                        for (i = 0; i < arraysOfJsonData.size(); i++) {
                            temporaryArray.add(arraysOfJsonData.get(i));
                        }
                        arraysOfJsonData.clear();
                    }
                }
            }
            Set<String> keys = tools.keySet(); // here we define a set of keys of the objects in all_packages.json
            for (String s : keys) {
                File file = new File("C:\\Users\\Vladimir\\Documents\\NetBeansProjects\\DependenciesResolving\\src\\dependencies\\installed_modules\\" + s);
                if (file.createNewFile()) {
                    System.out.println(file.getName() + " is installed.");
                } else {
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DependencyResolving.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DependencyResolving.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
