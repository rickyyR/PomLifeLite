package org.rickyyr.pomlifelite;

public class PomTimer {

  private int pomCount = 1;
  private int minutes = 0;
  private int seconds = 3;
  private boolean isRunning = false;
  private int pauseMinutes = 5;
  private int pauseSeconds = 0;

  public void countDown() {
   if(this.minutes <= 0 && this.seconds <= 0) {
     this.setOFF();
     this.resetTimer();
     this.pomCount++;
   }
   if(this.isRunning) {
     if(this.seconds == 0) {
       this.seconds = 60;
       this.minutes --;
       }
     this.seconds --;
     }
  }
  public void setON() {
     this.isRunning = true;
  }

  public void setOFF() {
    this.isRunning = false;
  }

  public void resetTimer() {
    this.minutes = 0; // TODO change to right time
    this.seconds = 3;
    if(this.pomCount >= 4) {
      this.pauseMinutes = 0;
      this.pauseSeconds = 6; // TODO
    } else {
        this.pauseMinutes = 0; // TODO change to right times
        this.pauseSeconds = 3;
      }
  }

  public void resetPomCount() {
    this.pomCount = 1;
  }

  public void runPause() {
    if(this.pauseMinutes <= 0 && this.pauseSeconds <= 0) {
      this.setOFF();
      this.resetTimer();
    }
    if(this.isRunning) {
      if(this.pauseSeconds == 0) {
        this.pauseSeconds = 60;
        this.pauseMinutes --;
      }
      this.pauseSeconds --;
    }
  }
  // Getter methods
  public String pomCountTostring() { return "Pom: " +  this.pomCount; }
  public String getRemaining() {
    return String.format("%02d", this.minutes) + ":" + String.format("%02d", this.seconds);
  }
  public String getRemainingPauseTime() {
    return String.format("%02d", this.pauseMinutes) + ":" + String.format("%02d", this.pauseSeconds);
  }
  public String getMinutes() {
      return String.valueOf(this.minutes);
  }
  public String getSeconds() {
      return String.valueOf(this.seconds);
  }
  public boolean getIsRunning() { return  this.isRunning; }
} // class

