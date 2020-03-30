package com.osai.backend;

class TaskNotFoundException extends RuntimeException {
	public TaskNotFoundException(Long id) {
		super("Task not found for id=" + id);
	}
}