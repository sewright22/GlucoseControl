package com.home.sewright22.diabeticglucosecontrol;

import java.util.ArrayList;

/**
 * Created by steve on 1/19/2016.
 */
public class JournalEntryList {
    private ArrayList<JournalEntry> journalEntries = new ArrayList<JournalEntry>();
    private ArrayList<String> values = new ArrayList<String>();

    public void addJournalEntry(JournalEntry journalEntry)
    {
        journalEntries.add(journalEntry);
    }

    public ArrayList<String> getValues() {
        for (JournalEntry entry:journalEntries
             )
        {
            values.add(entry.toString());
        }

        return values;
    }

    public ArrayList<JournalEntry> getJournalEntries() {
        return journalEntries;
    }
}
