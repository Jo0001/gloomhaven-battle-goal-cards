package de.jo0001.gloomhaven.battleGoalCards.other;

public class Card {
    private final String title;
    private final String description;
    private final int checks;
    private final int id;

    public Card(String title, String description, int checks, int id) {
        this.title = title;
        this.description = description;
        this.checks = checks;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getChecks() {
        return checks;
    }

    public int getId() {
        return id;
    }

}