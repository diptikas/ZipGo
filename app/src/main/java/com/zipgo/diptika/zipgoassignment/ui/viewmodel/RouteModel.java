package com.zipgo.diptika.zipgoassignment.ui.viewmodel;

/**
 * Created by Diptika on 19/09/16.
 */

public class RouteModel {
    private String id;

    private String description;

    private String name;

    private String[] stops_sequence;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String[] getStops_sequence ()
    {
        return stops_sequence;
    }

    public void setStops_sequence (String[] stops_sequence)
    {
        this.stops_sequence = stops_sequence;
    }


}
