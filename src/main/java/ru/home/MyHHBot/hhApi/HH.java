package ru.home.MyHHBot.hhApi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HH {
    private List<Job> items;
    private List<Region> areas;
    private List<Specialization> specializations;

    HH(){}
}
