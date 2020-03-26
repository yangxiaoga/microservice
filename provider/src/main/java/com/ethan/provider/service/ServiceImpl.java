package com.ethan.provider.service;

public class ServiceImpl implements Service{

	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}
}
