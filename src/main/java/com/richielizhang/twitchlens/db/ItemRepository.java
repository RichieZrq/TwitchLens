package com.richielizhang.twitchlens.db;


import com.richielizhang.twitchlens.db.entity.ItemEntity;
import org.springframework.data.repository.ListCrudRepository;


public interface ItemRepository extends ListCrudRepository<ItemEntity, Long> {


    ItemEntity findByTwitchId(String twitchId);
}
