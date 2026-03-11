package com.richielizhang.twitchlens.model;

import java.util.List;

public record VideoResponse(
        List<Video> data
) {
}
