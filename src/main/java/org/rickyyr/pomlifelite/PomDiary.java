package org.rickyyr.pomlifelite;

import java.util.ArrayList;

public class PomDiary {

  private ArrayList<PomDiaryEntry> diary = new ArrayList<>();

  public void addEntry(PomDiaryEntry pomDiaryEntry) {
    this.diary.add(pomDiaryEntry);
  }

  public PomDiaryEntry getEntry(int entryNumber) {
    return this.diary.get(entryNumber);
  }

  public ArrayList<PomDiaryEntry> getDiary() {
    return this.diary;
  }
  public int getEntryCount() {
    return this.diary.size();
  }
}
