package com.richielizhang.twitchlens.external;


import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.richielizhang.twitchlens.model.Clip;
import com.richielizhang.twitchlens.model.Game;
import com.richielizhang.twitchlens.model.Stream;
import com.richielizhang.twitchlens.model.Video;


@Service
public class TwitchService {


    private final TwitchApiClient twitchApiClient;


    public TwitchService(TwitchApiClient twitchApiClient) {
        this.twitchApiClient = twitchApiClient;
    }

    @Cacheable("top_games")
    public List<Game> getTopGames() {
        return twitchApiClient.getTopGames().data();
    }





    @Cacheable("games_by_name")

    public List<Game> getGames(String name) {
        return twitchApiClient.getGames(name).data();
    }


    public List<Stream> getStreams(List<String> gameIds, int first) {
        return twitchApiClient.getStreams(gameIds, first).data();
    }


    public List<Video> getVideos(String gameId, int first) {
        List<Video> videos = twitchApiClient.getVideos(gameId, first, "views").data();
        return videos.stream()
            .map(video -> {
                String fixedUrl = video.thumbnailUrl() == null ? null :
                    video.thumbnailUrl()
                        .replace("%{width}", "320")
                        .replace("%{height}", "180");
                return new Video(
                    video.id(), video.streamId(), video.userId(),
                    video.userLogin(), video.userName(), video.title(),
                    video.description(), video.createdAt(), video.publishedAt(),
                    video.url(), fixedUrl, video.viewable(), video.viewCount(),
                    video.language(), video.type(), video.duration(),
                    video.mutedSegments()
                );
            })
            .collect(java.util.stream.Collectors.toList());
    }


    public List<Clip> getClips(String gameId, int first) {
        return twitchApiClient.getClips(gameId, first).data();
    }


    public List<String> getTopGameIds() {
        List<String> topGameIds = new ArrayList<>();
        for (Game game : getTopGames()) {
            topGameIds.add(game.id());
        }
        return topGameIds;
    }
}
