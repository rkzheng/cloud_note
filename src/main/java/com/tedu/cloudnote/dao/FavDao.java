package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.Favorite;

public interface FavDao {
public void saveFav(Favorite favorite);

public List<Favorite> getFavorite(String userId);

public Favorite favPreview(String favId);

public int cancelFav(String favId);
}
