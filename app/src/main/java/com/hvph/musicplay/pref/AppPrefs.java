package com.hvph.musicplay.pref;

/**
 * Created by HoangLM4 on 10/13/2014.
 */
public class AppPrefs {
    public enum Category {
        CATEGORY("category");
        private String value;

        private Category(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum NowPlaying {
        SONGS(0),
        ARTIST(1),
        ALBUM(2),
        GENRE(3),
        FOLDER(4),
        FAVORITE(5);

        private int value;

        private NowPlaying(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public enum SharedPreferencesName{

        NOW_PLAYING("NOW_PLAYING"),
        DATA_LIST_MUSIC("DATA_LIST_MUSIC");
        private String value;
        private SharedPreferencesName(String value){
            this.value=value;
        }
        public String getValue(){
            return value;
        }
    }
    public enum Song{
        ID("id"),
        ARTIST("artist"),
        TITLE("title"),
        DATA("data"),
        DISPLAY_NAME("displayName"),
        DURATION("duration"),
        ALBUM("album"),
        LINK("link"),
        DIRECTORY_PATH("directoryPath"),
        GENRE("genre"),
        STATE("state"),
        INDEX("index");
        private String value;
        private Song(String value){
            this.value=value;
        }
        public String getValue(){
            return value;
        }
    }

}
