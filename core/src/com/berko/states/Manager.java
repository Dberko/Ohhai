package com.berko.states;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Manager {
	
	private Stack<State> states;
	
	public Manager(){
		states = new Stack<State>();
		
	}
	
	public void push(State s) {
		states.push(s);
	}
	
	public void pop() {
		states.pop();
	}
	
	public void set(State s) {
		states.pop();
		states.push(s);
		
	}
	
	public void update(float dt) {
		states.peek().update(dt); // Very top of stack
		
	}
	
	public void render(SpriteBatch sb) {
		states.peek().render(sb);
	}

}
