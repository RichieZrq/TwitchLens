package com.richielizhang.twitchlens.model;

import java.util.List;

public record GameResponse(
        List<Game> data
) {
}
