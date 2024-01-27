package com.example.manish;

public class JournalEntry {
    private String entry;
    private String date;
    private String time;

    public JournalEntry() {
        // Default constructor required for calls to DataSnapshot.getValue(JournalEntry.class)
    }

    public JournalEntry(String entry, String date, String time) {
        this.entry = entry;
        this.date = date;
        this.time = time;
    }

    public String getEntry() {
        return entry;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
