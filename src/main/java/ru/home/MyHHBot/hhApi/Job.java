package ru.home.MyHHBot.hhApi;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Job {
    public String id;
    public String name;

    Area area;
   public Employer employer;
    Salary salary;
    Address address;
    Snippet snippet;
    Schedule schedule;

}
