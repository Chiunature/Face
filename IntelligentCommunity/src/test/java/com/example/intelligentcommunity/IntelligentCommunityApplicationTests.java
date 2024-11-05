package com.example.intelligentcommunity;

import com.example.intelligentcommunity.entity.Menu;
import com.example.intelligentcommunity.mapper.MenuMapper;
import com.example.intelligentcommunity.mapper.RoleMapper;
import com.example.intelligentcommunity.service.MenuService;
import com.example.intelligentcommunity.vo.MenuRouterVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class IntelligentCommunityApplicationTests {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private MenuService menuService;
	@Test
	void contextLoads() {
		List<MenuRouterVO> menuRouterByUserID=menuService.getMenuRouterByUserId(1);
		for(MenuRouterVO menuRouterVO:menuRouterByUserID){

		}
	}

}
