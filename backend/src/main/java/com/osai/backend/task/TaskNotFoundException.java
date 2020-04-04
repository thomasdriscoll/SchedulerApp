package com.osai.backend.task;

class TaskNotFoundException extends RuntimeException {
	public TaskNotFoundException(Long id) {
		super("Task not found for id=" + id);
	}
}