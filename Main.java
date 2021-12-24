package com.company;

import com.company.model.Artist;
import com.company.model.DataSource;
import com.company.model.Song;
import com.company.model.SongArtist;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        if(!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        List<Artist> artists = dataSource.queryArtist(DataSource.ORDER_BY_NONE);
        if(artists == null) {
            System.out.println("No artists!");
            return;
        }

        for(Artist artist : artists) {
            System.out.println("ID = " + artist.getId() + ", Name = " + artist.getName());
        }

        List<String> albumsForArtist =
                dataSource.queryAlbumsForArtist("Carole King", DataSource.ORDER_BY_ASC);

        for(String album : albumsForArtist) {
            System.out.println(album);
        }

        List<SongArtist> songArtists = dataSource.queryArtistsForSong("Evil Woman", DataSource.ORDER_BY_NONE);
        if(songArtists == null) {
            System.out.println("Couldn't find the artist for the song");
            return;
        }

        for(SongArtist artist : songArtists) {
            System.out.println("Artist name = " + artist.getArtistName() +
                " Album name = " + artist.getAlbumName() +
                " Track = " + artist.getTrack());
        }

        dataSource.querySongsMetadata();

        int count = dataSource.getCount(DataSource.TABLE_SONGS);
        System.out.println("Number of songs is: " + count);

        dataSource.createViewForSongArtist();

        songArtists = dataSource.querySongInfoView("She's On Fire");
        if(songArtists.isEmpty()) {
            System.out.println("Couldn't find the artist for the song");
            return;
        }

        for(SongArtist artist : songArtists) {
            System.out.println("FROM VIEW - Artist name = " + artist.getArtistName() +
                " Album name = " + artist.getAlbumName() +
                " Track number = " + artist.getTrack());
        }

        dataSource.close();
    }
}
