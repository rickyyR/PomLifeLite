package org.rickyyr.pomlifelite;

import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonListHelper <T> {

  private final String filePath;
  private final Gson gson = new Gson();
  private FileWriter fileWriter;
  private FileReader fileReader;
  private List<T> jsonList;

  // Constructor
  public JsonListHelper(String filePath) {
    this.filePath = filePath;
  }
  // Read List from file into the jsonList variable
  private void readListFromFile() {
    try {
      this.fileReader = new FileReader(this.filePath);
      this.jsonList = this.gson.fromJson(this.fileReader, List.class);
      if(this.jsonList == null) {
        this.jsonList = new ArrayList<>();
      }
      this.fileReader.close();
    } catch (FileNotFoundException e) {
      this.jsonList = new ArrayList<>();
      try {
        this.fileWriter = new FileWriter(this.filePath);
        this.gson.toJson(this.jsonList);
        this.fileWriter.close();
        System.out.println("Could not find Diary file! Created empty pomDiary.json."); // TODO remove
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
  // This is for creating a observable list that does not bug with tableview rows.
  public <X> List<X> getListFromFile(Class<X[]> objectClass) {
    try {
      this.fileReader = new FileReader(this.filePath);
    } catch (FileNotFoundException e) {
      return new ArrayList<>();
    }
    X[] arr = new Gson().fromJson(this.fileReader, objectClass);
    try {
      this.fileReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Arrays.asList(arr);
  }



}

