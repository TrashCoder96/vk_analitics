package ru.vk.analitics.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itimofeev on 10.07.2017.
 */

@Document
public class Data {

	@Id
	private String id;

	private String link;

	private String type;

	private String text;

	private List<String> tags;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getTags() {
		if (this.tags == null) {
			this.tags = new ArrayList<>();
		}
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
