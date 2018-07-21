package com.why.project.recyclerviewselectableadapterdemo.bean;

/**
 * Created by HaiyuKing
 * Used 列表项的bean类
 */

public class Photo {
	private String id;
	private String path;

	public Photo(String id, String path) {
		this.id = id;
		this.path = path;
	}

	public Photo() {
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
