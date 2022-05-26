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

	public BoardInfo boardInfo(int startPage, int pageRow, String field, String keyWord) {
		BoardInfo boardinfo = new BoardInfo();
		boardinfo.setList(new ListMapper().read(startPage, pageRow, field, keyWord));
		boardinfo.setTotalRow(new ListMapper().totalRow(field, keyWord));
		return boardinfo;
	}
	
	public BoardInfo boardInfo(int startPage, int pageRow, String titleF, String contentF, String writerF) {
		BoardInfo boardinfo = new BoardInfo();
		boardinfo.setList(new ListMapper().read(startPage, pageRow, titleF, contentF, writerF));
		boardinfo.setTotalRow(new ListMapper().totalRow(titleF, contentF, writerF));
		return boardinfo;
	}

}
//30번째줄 totalRow() 괄호 속에 keyWord를 넣어주지 않으면 해당하지 않는 값까지 다 나온다.
