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
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("C:\\Users\\Vladimir\\Documents\\NetBeansProjects\\DependenciesResolving\\src\\dependencies\\dependencies.json"));
            JSONObject project = (JSONObject) obj;
            JSONArray dependencies = (JSONArray) project.get("dependencies");
            System.out.println("We need to install the following dependencies: ");
            Iterator<String> iterator = dependencies.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
            Object obj2 = parser.parse(new FileReader("C:\\Users\\Vladimir\\Documents\\NetBeansProjects\\DependenciesResolving\\src\\dependencies\\all_packages.json"));
            JSONObject tools = (JSONObject) obj2;
            for (int i = 0; i < dependencies.size(); i++) {
                if (tools.containsKey(dependencies.get(i))) {
                    System.out.println("In order to install " + dependencies.get(i) + ", we need the following programs:");
                    JSONArray temporaryArray = (JSONArray) tools.get(dependencies.get(i));
                    for (i = 0; i < temporaryArray.size(); i++) {
                        System.out.println(temporaryArray.get(i));
                    }
                    ArrayList<Object> arraysOfJsonData = new ArrayList<Object>();
                    while (!temporaryArray.isEmpty()) {
                        for (i = 0; i < temporaryArray.size(); i++) {
                            System.out.println("Installing " + temporaryArray.get(i));
                        }
                        for (Object element : temporaryArray) {
                            System.out.println("In order to install " + element + ", we need the following programs: ");
                            if (tools.containsKey(element)) {
                                JSONArray secondaryArray = (JSONArray) tools.get(element);
                                for (i = 0; i < secondaryArray.size(); i++) {
                                    System.out.println(secondaryArray.get(i));
                                }
                                if (secondaryArray.size() == 0) {
                                    System.out.println(element + " is already installed.");
                                }
                                for (Object o : secondaryArray) {

                                    arraysOfJsonData.add(o);
                                    File file = new File("C:\\Users\\Vladimir\\Documents\\NetBeansProjects\\DependenciesResolving\\src\\dependencies\\installed_modules\\" + o);
                                    if (file.createNewFile()) {
                                        System.out.println("File is created!");
                                    } else {
                                        System.out.println("File already exists.");
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
            File[] files = new File("C:\\Users\\Vladimir\\Documents\\NetBeansProjects\\DependenciesResolving\\src\\dependencies\\installed_modules").listFiles();
            ArrayList<String> fileNames = new ArrayList<String>();
            for(File file: files) {
                fileNames.add(file.getName());
            }
            Set<String> keys = tools.keySet();
            for(String s: keys) {
                File file = new File("C:\\Users\\Vladimir\\Documents\\NetBeansProjects\\DependenciesResolving\\src\\dependencies\\installed_modules\\" + s);
                                    if (file.createNewFile()) {
                                        System.out.println("Installing "+file.getName());
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
