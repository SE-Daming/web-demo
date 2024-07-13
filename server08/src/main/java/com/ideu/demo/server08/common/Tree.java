package com.ideu.demo.server08.common;



import lombok.Data;

import java.util.List;

@Data
public class Tree
{
    private String id;
    private String pid;

    private String cityName;

    private String type;

    private List<Tree> children;

}
