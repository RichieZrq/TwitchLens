package com.richielizhang.twitchlens.model;

import java.util.List;

public record StreamResponse(
        List<Stream> data
) {
}
