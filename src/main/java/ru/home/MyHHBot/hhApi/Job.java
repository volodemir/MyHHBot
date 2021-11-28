package ru.home.MyHHBot.hhApi;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Job {
    String id;
    String name;

    Area area;
    Employer employer;
    Salary salary;
    Address address;
    Snippet snippet;
    Schedule schedule;

}
