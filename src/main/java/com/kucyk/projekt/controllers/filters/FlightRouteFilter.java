package com.kucyk.projekt.controllers.filters;

import com.kucyk.projekt.models.Airline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRouteFilter
{
    private String phrase;
    private Airline airline;
    private float priceFrom;
    private float priceTo;

    public String getPhraseDB(){
        return "%"+phrase+"%";
    }

    public boolean isClear()
    {
        if(this.phrase == null && airline == null && this.priceFrom == 0.0f && this.priceTo == 0.0f)
            return true;
        return false;
    }

    public void prepareValues()
    {
        if(this.priceTo<=0)
            this.priceTo = Float.MAX_VALUE;
    }

    public boolean isAirlineNull(){
        if(this.airline==null)
            return true;
        return false;
    }
}
