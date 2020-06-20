package org.river.controllers;

import org.river.entities.Comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author - Haribo
 */
public class FoulLanguageMapSanitizer implements Sanitizer {
    Map<String, Integer> foulLanguageMap = new HashMap<>();
    int maxFoulLanguageLength = 0;

    /**
     * It will filter out the comment with foul language by foul language map.
     *
     * @param commentList commentList
     * @return comments with foul language
     */
    @Override
    public List<Comment> sanitize(List<Comment> commentList) {
        List<Comment> commentWithFoulLanguageList = new ArrayList<>();
        initializeFoulLanguageMap();

        for (Comment comment: commentList) {
            ArrayList<String> foulLanguageList = queryFoulLanguage(comment.getDescription());
            if(foulLanguageList.size() > 0) {
                commentWithFoulLanguageList.add(comment);
            }
        }

        return commentWithFoulLanguageList;
    }

    private void initializeFoulLanguageMap() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://docs.google.com/spreadsheets/d/1hIEi2YG3ydav1E06Bzf2mQbGZ12kh2fe4ISgLg_UBuM/export?format=csv").openConnection().getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                try {
                    String[] content = line.split(",");
                    if (content.length == 0) {
                        continue;
                    }

                    String foulLanguage = content[0];
                    if (foulLanguage.length() > maxFoulLanguageLength) {
                        maxFoulLanguageLength = foulLanguage.length();
                    }
                    foulLanguageMap.put(foulLanguage, 1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Iterates over a String input and checks whether a cuss word was found in comment.
     *
     * @param description description
     * @return list of foul language within the comment
     */
    private ArrayList<String> queryFoulLanguage(String description) {
        ArrayList<String> foulLanguageList = new ArrayList<>();
        int descriptionLength = description.length();

        for (int start = 0; start < descriptionLength; start++) {
            for (int offset = 1; offset < (descriptionLength+1 - start) && offset < maxFoulLanguageLength; offset++) {
                String wordToCheck = description.substring(start, start + offset);
                if (foulLanguageMap.containsKey(wordToCheck)) {
                    foulLanguageList.add(wordToCheck);
                }
            }
        }

        return foulLanguageList;
    }
}
