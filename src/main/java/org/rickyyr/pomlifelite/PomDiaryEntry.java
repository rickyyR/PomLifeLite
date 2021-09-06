package org.rickyyr.pomlifelite;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class PomDiaryEntry {

  private String entryTitle = "";
  private String entryStartTime = "";
  private String entryEndTime = "";
  private String entryDate = "";
  private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
    .withLocale(Locale.getDefault())
    .withZone(ZoneId.systemDefault());

  public void setEntryTitle(String entryTitle) {
    this.entryTitle = entryTitle;
  }

  public void setDateAndStartTime() {
    String currentDateAndTime = formatter.format(Instant.now());
    String[] splitted = currentDateAndTime.split(",");
    this.entryDate = splitted[0].replaceAll("\\s+", "");
    this.entryStartTime = splitted[1].replaceAll("\\s+", "");
  }

  public void setEntryEndTime() {
    String currentDateAndTime = formatter.format(Instant.now());
    String[] splitted = currentDateAndTime.split(",");
    this.entryEndTime = splitted[1].replaceAll("\\s+", "");
  }
  public String getEntryTitle() {
    return this.entryTitle;
  }

  public String getEntryStartTime() {
    return this.entryStartTime;
  }
  public String getEntryDate() {
    return this.entryDate;
  }
  public String getEntryEndTime() {
    return this.entryEndTime;
  }

}
