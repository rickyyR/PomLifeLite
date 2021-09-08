package org.rickyyr.pomlifelite;

import com.google.gson.Gson;


import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ObjectIOHelper {

  private String filePath;
  private Gson gson = new Gson();
  private FileWriter fileWriter;
  private FileReader fileReader;
  private ArrayList jsonArray;


  public ObjectIOHelper(String filePath) {
    this.filePath = filePath;

  }

  public void writeObjectToFile(Object object) {
    try {
      this.fileReader = new FileReader(this.filePath);
      this.jsonArray = this.gson.fromJson(this.fileReader, ArrayList.class);
      if(this.jsonArray == null) {
        this.jsonArray = new ArrayList();
      }
      this.jsonArray.add(object);
      this.fileWriter = new FileWriter("pomDiary.json");
      this.gson.toJson(this.jsonArray, this.fileWriter);
      this.fileWriter.close();
      this.fileReader.close();
    } catch (FileNotFoundException e) {
      try {
        this.fileWriter = new FileWriter(this.filePath);
        this.jsonArray = new ArrayList();
        this.jsonArray.add(object);
        this.gson.toJson(this.jsonArray, this.fileWriter);
        this.fileWriter.close();
      } catch (IOException ex) {
        e.printStackTrace();
        System.out.println("File not found exception!");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public <T> List<T> getFileContentsAsList(Class<T[]> objectClass) {
    try {
      this.fileReader = new FileReader(this.filePath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    T[] arr = new Gson().fromJson(this.fileReader, objectClass);
    return Arrays.asList(arr);
  }



}

