package com.osai.backend.task;

class TaskNotFoundException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public TaskNotFoundException(final Long id) {
		super("Task not found for id=" + id);
	}
}