package com.bwie.cms.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bwie.cms.bean.User;
import com.bwie.cms.util.TimeRandomUtil;
import com.bwie.cms.util.UserRandomUtil;

@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-context.xml")
public class UserTest {

	@Resource
	public RedisTemplate redisTemplate;
	
	@Test
	public void userTest(){
		
		long time1 = System.currentTimeMillis();
		
		//jdk json
//		ValueOperations ops = redisTemplate.opsForValue();
		
		//hash
		BoundHashOperations ops_hash = redisTemplate.boundHashOps("hash_user");
		
		for (int i = 0; i < 50000; i++) {
			
			User user = new User();
			
			//id
			user.setId(i);
			
			//姓名
			user.setName(UserRandomUtil.getChineseName());
			
			//性别
			user.setSex(getSex());
			
			//电话
			user.setPhone(getPhone());
			
			//邮箱
			user.setEm(UserRandomUtil.getEmail());
			
			//生日
			user.setBirthday(TimeRandomUtil.randomDate("1949-01-01 00:00:00","2001-01-01 00:00:00"));
			
//			System.out.println(user);
//			ops.set(i+"", user);
			ops_hash.put(i+"", user.toString());
		}
		long time2 = System.currentTimeMillis();
		
		long time = time2-time1;
		
		System.out.println("序列化方式  hash, 耗时"+time);
	}

	private String getSex() {
		
		return new Random().nextBoolean()?"男":"女";
	}

	private String getPhone() {
		
		String phone ="";
		
		for (int i = 0; i < 9; i++) {
			phone+=new Random().nextInt(10);
		}
		
		return "13"+phone;
	}
	
}
