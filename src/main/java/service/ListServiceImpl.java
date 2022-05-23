package service;

import java.util.Collection;

import domain.BoardInfo;
import domain.BoardVO;
import mapper.ListMapper;

public class ListServiceImpl implements ListService {

	@Override
	public Collection<BoardVO> read() {
		return new ListMapper().read();
	}

	public int totalRow() {
		return new ListMapper().totalRow();
	}

	public BoardInfo boardInfo() {
		BoardInfo boardinfo = new BoardInfo();
		boardinfo.setList(new ListMapper().read());
		boardinfo.setTotalRow(new ListMapper().totalRow());
		return boardinfo;
	}

	public BoardInfo boardInfo(int startPage, int pageRow) {
		BoardInfo boardinfo = new BoardInfo();
		boardinfo.setList(new ListMapper().read(startPage, pageRow));
		boardinfo.setTotalRow(new ListMapper().totalRow());
		return boardinfo;
	}

}
