package org.springside.fi.data;

import org.springside.fi.entity.Task;
import org.springside.fi.entity.Runner;
import org.springside.modules.test.data.RandomData;

/**
 * Task相关实体测试数据生成.
 * 
 * @author calvin
 */
public class TaskData {

	public static Task randomTask() {
		Task task = new Task();
		task.setTitle(randomTitle());
		Runner user = new Runner(1L);
		task.setUser(user);
		return task;
	}

	public static String randomTitle() {
		return RandomData.randomName("Task");
	}
}
