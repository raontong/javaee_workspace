package com.ch.shop.model.size;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ch.shop.model.color.ColorDAO;
import com.ch.shop.model.color.ColorService;

@Service
public class SizeServiceImpl implements SizeService{

	@Autowired
	private SizeDAO sizeDAO;

	@Override
	public List getList() {
		
		return sizeDAO.selectAll();
	}
}
