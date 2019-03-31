package au.edu.unsw.infs3634.beers;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import au.edu.unsw.infs3634.beers.Entities.Beer;

@Dao
public interface BeerDao {
    @Query("SELECT * FROM beer")
    List<Beer> getBeers();

    @Query("SELECT * FROM beer WHERE id == :beerId")
    Beer getBeer(String beerId);

    @Insert
    void insertAll(Beer... beers);

    @Delete
    void deleteAll(Beer... beers);
}
