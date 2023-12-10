package com.example.demo.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.bili.Item;
import com.example.demo.entity.bili.ItemDO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static List<ItemDO> import_json_bvid(Path filePath) throws IOException {
//        String filePath = "D:\\T\\Documents\\VSCode\\js\\bili\\up\\37974444\\bvid\\BV1a34y167AZ.json";
        String bvid = filePath.getFileName().toString().split("\\.")[0];
        File file = new File(filePath.toString());
        byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        String jsonString = new String(bytes);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        int view = jsonObject.getJSONObject("data").getJSONObject("stat").getIntValue("view");
        System.out.println(bvid);
//        System.out.println(view);
        JSONArray pages = jsonObject.getJSONObject("data").getJSONArray("pages");
        List<ItemDO> item_list = pages.stream().map(el -> {
            JSONObject o = (JSONObject) el;
//            https://www.bilibili.com/video/${bvid}?p=${el.page}
            Item item = o.toJavaObject(Item.class);
            String url = String.format("https://www.bilibili.com/video/%s?p=%d", bvid, o.getIntValue("page"));
            o.put("bvid", bvid);
            o.put("url", url);
            o.put("view", view);
            ItemDO pageDO = o.toJavaObject(ItemDO.class);
            return pageDO;
//            for (String key : o.keySet()) {
//                Object value = o.get(key);
//                System.out.println(key + " : " + value);
//            }
        }).collect(Collectors.toList());
        return item_list;
    }

    public static List<Path> walk_dir(Path p) throws IOException {

        try (Stream<Path> paths = Files.walk(p)) {
            return paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .collect(Collectors.toList());
//                    .forEach(path -> {
//                        //                            String jsonString = Files.readString(path);
////                        System.out.println(path);
//                        System.out.println(path.getFileName());
//                    });
        }
    }
}