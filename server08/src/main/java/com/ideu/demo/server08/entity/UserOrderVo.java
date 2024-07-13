package com.ideu.demo.server08.entity;

import lombok.Data;

@Data
public class UserOrderVo extends Order{
    String engName;//分配的工程师
    String engPhone;
}
