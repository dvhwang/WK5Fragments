package au.edu.unsw.infs3634.beers;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import au.edu.unsw.infs3634.beers.Entities.Beer;

@Database(entities = {Beer.class}, version = 1)
public abstract class BeerDatabase extends RoomDatabase {
    public abstract BeerDao beerDao();
}
