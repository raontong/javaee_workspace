package com.ch.exmodel1.exrepository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.ch.exmodel1.dto.BoardDTO;
import com.ch.exmodel1.util.PoolManager;

// crud
public class ExBoardDAO {
	PoolManager pool=new PoolManager();
	
	public void insert(BoardDTO board) {
		
		Connection con = null;
		PreparedStatement pstmt=null;
		
		String sql="insert into board(title,	writer, content) values(?,?,?)";
		
		
		pstmt.setString(1, board.getTitle());

		
		
		
	}
	
	
	
	
}
