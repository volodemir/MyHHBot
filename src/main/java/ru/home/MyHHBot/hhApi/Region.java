package ru.home.MyHHBot.hhApi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Region {
    String id;
    String parent_id;
    String name;

    List<City> areas;
    public Region(){}
}
