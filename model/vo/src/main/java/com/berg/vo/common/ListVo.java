package com.berg.vo.common;

import lombok.Data;

import java.util.List;

@Data
public class ListVo<T> {
    List<T> list;
}
