package org.rickyyr.pomlifelite;

import com.google.gson.Gson;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;


public class JsonListHelper <T> {

  private String filePath;
  private Gson gson = new Gson();
  private FileWriter fileWriter;
  private FileReader fileReader;
  private List<T> jsonList;
  private Type objectType;

  // Constructor
  public JsonListHelper(String filePath, Type objectType) {
    this.filePath = filePath;
    this.objectType = objectType;
  }
  // Read List from file into the jsonList variable
  private void readListFromFile() {
    try {
      this.fileReader = new FileReader(this.filePath);
      this.jsonList = this.gson.fromJson(this.fileReader, List.class);
      this.fileReader.close();
    } catch (FileNotFoundException e) {
      this.jsonList = new ArrayList<>();
      try {
        this.fileWriter = new FileWriter(this.filePath);
        this.gson.toJson(this.jsonList);
        this.fileWriter.close();
        System.out.println("Could not find Diary file! Created empty pomDiary.json.");
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  // Write the list inside the jsonList variable to File (Replacing old file!)
  private void writeListToFile() {
    try {
      this.fileWriter = new FileWriter(this.filePath);
      this.gson.toJson(this.jsonList, this.fileWriter);
      this.fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  // Read a existing list from a file, add a Object to it and save the file
  public void addObjectToList(T object) {
    this.readListFromFile();
    if(this.jsonList == null)  {
      this.jsonList = new ArrayList<>();
    }
    this.jsonList.add(object);
    this.writeListToFile();
  }
  // Replace the List in File with a List given in parameter
  public void replaceListInFile(List<T> newList ) {
    this.jsonList = newList;
    this.writeListToFile();
  }
  // Read list from file and return it for further actions
  public <X> List<X> getListFromFile(Class<X[]> objectClass) {
    try {
      this.fileReader = new FileReader(this.filePath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    X[] arr = new Gson().fromJson(this.fileReader, objectClass);
    return Arrays.asList(arr);
  }



}

