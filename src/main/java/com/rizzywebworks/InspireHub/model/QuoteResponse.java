package com.rizzywebworks.InspireHub.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuoteResponse {
    private Success success;
    private Contents contents;
    private Copyright copyright;

    // Getters and setters

    @Getter
    @Setter
    public static class Success {
        private int total;

        // Getters and setters
    }

    @Getter
    @Setter
    public static class Contents {
        private List<QuoteData> quotes;

        // Getters and setters
    }

    @Getter
    @Setter
    public static class QuoteData {
        private String id;
        private String quote;
        private int length;
        private String author;
        private String language;
        private List<String> tags;
        private String sfw;
        private String permalink;
        private String title;
        private String category;
        private String background;
        private String date;

        // Getters and setters
    }

    @Getter
    @Setter
    public static class Copyright {
        private String url;
        private String year;

        // Getters and setters
    }
}

