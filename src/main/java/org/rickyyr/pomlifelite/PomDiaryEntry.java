package org.rickyyr.pomlifelite;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class PomDiaryEntry implements Serializable {

  private String entryTitle = "Placeholder";
  private String entryDate = "Placeholder";
  private String entryStartTime = "Placeholder";
  private String entryEndTime = "";
  private final transient DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
    .withLocale(Locale.getDefault())
    .withZone(ZoneId.systemDefault());

  public PomDiaryEntry() {
    this.setDateAndStartTime();
  }

  public PomDiaryEntry(String entryTitle) {
    this.entryTitle = entryTitle;
    this.setDateAndStartTime();
  }
  // Getters and Setters:
  public void setEntryTitle(String entryTitle) {
    this.entryTitle = entryTitle;
  }
  public String getEntryTitle() {
    return this.entryTitle;
  }
  public void setEntryDate() {
    String currentDateAndTime = formatter.format(Instant.now());
    String[] splitted = currentDateAndTime.split(",");
    this.entryDate = splitted[0].replaceAll("\\s+", "");
  }
  public String getEntryDate() {
    return this.entryDate;
  }
  public void setEntryStartTime() {
    String currentDateAndTime = formatter.format(Instant.now());
    String[] splitted = currentDateAndTime.split(",");
    this.entryStartTime = splitted[1].replaceAll("\\s+", "");
  }
  public String getEntryStartTime() {
    return this.entryStartTime;
  }
  public void setEntryEndTime() {
    String currentDateAndTime = formatter.format(Instant.now());
    String[] splitted = currentDateAndTime.split(",");
    this.entryEndTime = splitted[1].replaceAll("\\s+", "");
  }
  public String getEntryEndTime() {
    return this.entryEndTime;
  }
  public void setDateAndStartTime() {
    String currentDateAndTime = formatter.format(Instant.now());
    String[] splitted = currentDateAndTime.split(",");
    this.entryDate = splitted[0].replaceAll("\\s+", "");
    this.entryStartTime = splitted[1].replaceAll("\\s+", "");
  }
  public String getStartAndEndTime() {
    return this.entryStartTime + " - " + this.entryEndTime; }
  public DateTimeFormatter getFormatter() {
    return this.formatter;
  }






}
