package com.example.sy42app.Let10Net.bean;


import java.util.List;

public class VideoListResponse {
    public String result;
    public List<VideoInfo> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<VideoInfo> getList() {
        return list;
    }

    public void setList(List<VideoInfo> list) {
        this.list = list;
    }
}
