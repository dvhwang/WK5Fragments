package au.edu.unsw.infs3634.beers;

import au.edu.unsw.infs3634.beers.Entities.BreweryDBResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BeerService {
    @GET("beers?p=1&key=0cafa2ad34d6df8c2e863a59c773394e")
    Call<BreweryDBResponse> getBeers();
}
