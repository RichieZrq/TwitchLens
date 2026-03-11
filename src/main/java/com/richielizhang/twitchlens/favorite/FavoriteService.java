package com.richielizhang.twitchlens.favorite;


import com.richielizhang.twitchlens.db.FavoriteRecordRepository;
import com.richielizhang.twitchlens.db.ItemRepository;
import com.richielizhang.twitchlens.db.entity.FavoriteRecordEntity;
import com.richielizhang.twitchlens.db.entity.ItemEntity;
import com.richielizhang.twitchlens.db.entity.UserEntity;
import com.richielizhang.twitchlens.model.DuplicateFavoriteException;
import com.richielizhang.twitchlens.model.TypeGroupedItemList;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.List;


@Service
public class FavoriteService {


    private final ItemRepository itemRepository;
    private final FavoriteRecordRepository favoriteRecordRepository;


    public FavoriteService(ItemRepository itemRepository,
                           FavoriteRecordRepository favoriteRecordRepository) {
        this.itemRepository = itemRepository;
        this.favoriteRecordRepository = favoriteRecordRepository;
    }

    @CacheEvict(cacheNames = "recommend_items", key = "#user")

    @Transactional
    public void setFavoriteItem(UserEntity user, ItemEntity item) throws DuplicateFavoriteException {
        ItemEntity persistedItem = itemRepository.findByTwitchId(item.twitchId());
        if (persistedItem == null) {
            persistedItem = itemRepository.save(item);
        }
        if (favoriteRecordRepository.existsByUserIdAndItemId(user.id(), persistedItem.id())) {
            throw new DuplicateFavoriteException();
            //必须要加， 不加db会报错
        }
        FavoriteRecordEntity favoriteRecord = new FavoriteRecordEntity(null, user.id(), persistedItem.id(), Instant.now());
        favoriteRecordRepository.save(favoriteRecord);
    }

    @CacheEvict(cacheNames = "recommend_items", key = "#user")
    public void unsetFavoriteItem(UserEntity user, String twitchId) {
        ItemEntity item = itemRepository.findByTwitchId(twitchId);
        if (item != null) {
            favoriteRecordRepository.delete(user.id(), item.id());
        }
    }


    public List<ItemEntity> getFavoriteItems(UserEntity user) {
        List<Long> favoriteItemIds = favoriteRecordRepository.findFavoriteItemIdsByUserId(user.id());
        return itemRepository.findAllById(favoriteItemIds);
    }


    public TypeGroupedItemList getGroupedFavoriteItems(UserEntity user) {
        List<ItemEntity> items = getFavoriteItems(user);
        return new TypeGroupedItemList(items);
    }
}
