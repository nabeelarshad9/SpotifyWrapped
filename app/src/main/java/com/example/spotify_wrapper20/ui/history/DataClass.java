package com.example.spotify_wrapper20.ui.history;

public class DataClass {
    private String dataDate;
    private String dataArtist;
    private String dataSongs;
    private String dataGenres;

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    public String getDataArtist() {
        return dataArtist;
    }

    public void setDataArtist(String dataArtist) {
        this.dataArtist = dataArtist;
    }

    public String getDataSongs() {
        return dataSongs;
    }

    public void setDataSongs(String dataSongs) {
        this.dataSongs = dataSongs;
    }

    public String getDataGenres() {
        return dataGenres;
    }

    public void setDataGenres(String dataGenres) {
        this.dataGenres = dataGenres;
    }



    public DataClass(String dataDate, String dataArtist, String dataSongs, String dataGenres) {
        this.dataDate = dataDate;
        this.dataArtist = dataArtist;
        this.dataSongs = dataSongs;
        this.dataGenres = dataGenres;
    }

    public String getDataDate() {
        return dataDate;
    }
}