package vntd.demo.musicplayer_client.object;

import java.util.List;

public class Song {
    private int SongId;
    private String SongName;
    private String SingerName;
    private int TurnNum;
    private String SongPath;
    private String ShortDecription;
    private String Content;
    private String SongImage;
    private String SingerAvatar;
    private List<UserComment> userComments;
    private double Rating;

    private int File;

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }

    public Song(String songName, int file) {

        SongName = songName;
        File = file;
    }

    public Song(String songName, String singerName, int turnNum) {
        SongName = songName;
        SingerName = singerName;
        TurnNum = turnNum;
        SongPath = null;
        Content = null;
        ShortDecription = null;
    }


    public Song(String songName, String singerName, int turnNum, String songPath) {
        SongName = songName;
        SingerName = singerName;
        TurnNum = turnNum;
        SongPath = songPath;
    }

    public Song(String songName, String singerName, int turnNum, String songPath, String shortDecription, String content) {
        SongName = songName;

        SingerName = singerName;
        TurnNum = turnNum;
        SongPath = songPath;
        ShortDecription = shortDecription;
        Content = content;
    }

    public Song(int songId, String songName, String singerName, int turnNum, String songPath,
                 String shortDecription, String content, String songImage, String singerAvatar, double rating) {
        SongId = songId;
        SongName = songName;

        SingerName = singerName;
        TurnNum = turnNum;
        SongPath = songPath;
        ShortDecription = shortDecription;
        Content = content;
        SongImage = songImage;
        SingerAvatar = singerAvatar;
        Rating = rating;
    }

    public Song(int songId, String songName, String singerName, int turnNum, String songPath,
                String shortDecription, String content, String songImage, String singerAvatar) {
        SongId = songId;
        SongName = songName;

        SingerName = singerName;
        TurnNum = turnNum;
        SongPath = songPath;
        ShortDecription = shortDecription;
        Content = content;
        SongImage = songImage;
        SingerAvatar = singerAvatar;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(long rating) {
        Rating = rating;
    }

    public int getSongId() {
        return SongId;
    }

    public void setSongId(int songId) {
        SongId = songId;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    public String getSingerName() {
        return SingerName;
    }

    public void setSingerName(String singerName) {
        SingerName = singerName;
    }

    public int getTurnNum() {
        return TurnNum;
    }

    public void setTurnNum(int turnNum) {
        TurnNum = turnNum;
    }

    public String getSongPath() {
        return SongPath;
    }

    public String getSongImage() {
        return SongImage;
    }

    public void setSongImage(String songImage) {
        SongImage = songImage;
    }

    public String getSingerAvatar() {
        return SingerAvatar;
    }

    public void setSingerAvatar(String singerAvatar) {
        SingerAvatar = singerAvatar;
    }

    public void setSongPath(String songPath) {
        SongPath = songPath;
    }

    public String getShortDecription() {
        return ShortDecription;
    }

    public void setShortDecription(String shortDecription) {
        ShortDecription = shortDecription;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public List<UserComment> getUserComments() {
        return userComments;
    }

    public void setUserComments(List<UserComment> userComments) {
        this.userComments = userComments;
    }

    @Override
    public String toString() {
        return "Song{" +
                "SongName='" + SongName + '\'' +
                ", SingerName='" + SingerName + '\'' +
                ", TurnNum=" + TurnNum +
                ", SongPath='" + SongPath + '\'' +
                ", ShortDecription='" + ShortDecription + '\'' +
                ", Content='" + Content + '\'' +
                ", File=" + File +
                '}';
    }
}
