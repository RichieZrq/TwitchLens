package com.richielizhang.twitchlens.external;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richielizhang.twitchlens.model.ClipResponse;
import com.richielizhang.twitchlens.model.GameResponse;
import com.richielizhang.twitchlens.model.StreamResponse;
import com.richielizhang.twitchlens.model.VideoResponse;


@FeignClient(name = "twitch-api")
public interface TwitchApiClient {


    @GetMapping("/games")
    GameResponse getGames(@RequestParam("name") String name);


    @GetMapping("/games/top")
    GameResponse getTopGames();


    @GetMapping("/videos/")
    VideoResponse getVideos(
        @RequestParam("game_id") String gameId, 
        @RequestParam("first") int first,
        @RequestParam("sort") String sort
    );


    @GetMapping("/clips/")
    ClipResponse getClips(@RequestParam("game_id") String gameId, @RequestParam("first") int first);


    @GetMapping("/streams/")
    StreamResponse getStreams(@RequestParam("game_id") List<String> gameIds, @RequestParam("first") int first);
}
