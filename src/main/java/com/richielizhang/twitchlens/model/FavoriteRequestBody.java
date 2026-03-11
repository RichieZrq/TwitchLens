package com.richielizhang.twitchlens.model;


import com.richielizhang.twitchlens.db.entity.ItemEntity;


public record FavoriteRequestBody(
        ItemEntity favorite
) {}
