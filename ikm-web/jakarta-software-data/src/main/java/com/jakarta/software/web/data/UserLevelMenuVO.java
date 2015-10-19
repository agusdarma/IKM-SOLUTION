package com.jakarta.software.web.data;

import java.util.LinkedList;
import java.util.List;

import com.jakarta.software.web.entity.UserMenu;

public class UserLevelMenuVO {

	private String header;

	private List<UserMenu> bodies;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public List<UserMenu> getBodies() {
		return bodies;
	}

	public void setBodies(List<UserMenu> bodies) {
		this.bodies = bodies;
	}

	public void addBody(UserMenu body) {
		if (body == null)
			return;
		if (bodies == null) {
			bodies = new LinkedList<UserMenu>();
		}
		bodies.add(body);
	}
}
