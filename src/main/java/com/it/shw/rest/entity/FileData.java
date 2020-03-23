package com.it.shw.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Copyright: Harbin Institute of Technology.All rights reserved.
 * @Description: 文件数据实体类
 * @author: thailandking
 * @since: 2020/3/19 17:00
 * @history: 1.2020/3/19 created by thailandking
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileData {
    private Long id;
    private String name;
    private String url;
}
