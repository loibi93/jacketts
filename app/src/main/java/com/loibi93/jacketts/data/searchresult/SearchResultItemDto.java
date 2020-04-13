package com.loibi93.jacketts.data.searchresult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.loibi93.jacketts.ui.UiUtils.humanReadableByteCount;

public class SearchResultItemDto {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

    private String FirstSeen;
    private String Tracker;
    private String TrackerId;
    private String CategoryDesc;
    private String Title;
    private String Guid;
    private String Link;
    private String Comments;
    private String PublishDate;
    private transient int age = -1;
    private List<Integer> Category;
    private long Size;
    private int Files;
    private int Grabs;
    private String Description;
    private Integer RageId;
    private Integer TVDBId;
    private Integer Imdb;
    private Integer TMDb;
    private int Seeders;
    private int Peers;

    private SearchResultItemDto() {
    }

    public String getFirstSeen() {
        return FirstSeen;
    }

    public int getAge() {
        if (age != -1) {
            return age;
        }

        Date publishDate;
        try {
            publishDate = DATE_FORMAT.parse(PublishDate);
            if (publishDate == null) {
                age = 0;
                return age;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            age = 0;
            return age;
        }

        Date now = new Date();
        long diff = now.getTime() - publishDate.getTime();
        return (int) (((diff / 1000f) / 60f) / 60f);
    }

    public String getAgeHumanReadable() {
        int age = getAge();
        if (age > 24) {
            return (age / 24) + "d";
        }

        return age + "h";
    }

    public String getTracker() {
        return Tracker;
    }

    public String getTrackerId() {
        return TrackerId;
    }

    public String getCategoryDesc() {
        return CategoryDesc;
    }

    public String getTitle() {
        return Title;
    }

    public String getGuid() {
        return Guid;
    }

    public String getLink() {
        return Link;
    }

    public String getComments() {
        return Comments;
    }

    public String getPublishDate() {
        return PublishDate;
    }

    public List<Integer> getCategory() {
        return Category;
    }

    public long getSize() {
        return Size;
    }

    public String getSizeHumanReadable() {
        return humanReadableByteCount(Size);
    }

    public int getFiles() {
        return Files;
    }

    public String getFilesHumanReadable() {
        if (Files > 0) {
            return String.valueOf(Files);
        } else {
            return "n.a.";
        }
    }

    public int getGrabs() {
        return Grabs;
    }

    public String getDescription() {
        return Description;
    }

    public Integer getRageId() {
        return RageId;
    }

    public Integer getTVDBId() {
        return TVDBId;
    }

    public Integer getImdb() {
        return Imdb;
    }

    public Integer getTMDb() {
        return TMDb;
    }

    public int getSeeders() {
        return Seeders;
    }

    public int getPeers() {
        return Peers;
    }
}
