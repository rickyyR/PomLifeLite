package org.rickyyr.pomlifelite;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;


public class PomDiary {

  private String sessionTitle = "Pom Session";
  private Instant sessionStartTimeAndDate;
  private Instant sessionEndTime;
  private FileWriter entryWriter;

  public void setSessionTitle(String sessionTitle) {
    if(!this.sessionTitle.equals(""))
    this.sessionTitle = sessionTitle;
  }

  public void sessionStarted() {
    {
      try {
        this.entryWriter = new FileWriter("Pom-Diary.txt", true);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    DateTimeFormatter formatter =
      DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    this.sessionStartTimeAndDate = Instant.now();
    String asString = formatter.format(sessionStartTimeAndDate);
    try {
      entryWriter.write(this.sessionTitle + ":" + "\n" + "-------------------------" + "\n" + asString + " - ");
      entryWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void sessionEnded() {
    DateTimeFormatter formatter =
      DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    this.sessionEndTime = Instant.now();
    String asString = formatter.format(sessionStartTimeAndDate);
    try {
      this.entryWriter.append(asString + "\n" + "-------------------------" + "\n\n");
      this.entryWriter.flush();
      this.entryWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
