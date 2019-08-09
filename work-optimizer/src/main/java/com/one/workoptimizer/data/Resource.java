package com.one.workoptimizer.data;

import java.util.List;

/**
 * 
 * @author Ashish G
 *
 */
public class Resource {
	private List<Integer> rooms = null;
	private int senior;
	private int junior;
	
	public List<Integer> getRooms() {
		return rooms;
	}
	public void setRooms(List<Integer> rooms) {
		this.rooms = rooms;
	}
	public int getSenior() {
		return senior;
	}
	public void setSenior(int senior) {
		this.senior = senior;
	}
	public int getJunior() {
		return junior;
	}
	public void setJunior(int junior) {
		this.junior = junior;
	}
	
	@Override
	public String toString() {
		return "Resource [rooms:" + rooms + ", senior:" + senior + ", junior:" + junior + "]";
	}
}
