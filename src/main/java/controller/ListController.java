package controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.BoardInfo;
import domain.BoardVO;
import service.ListServiceImpl;
import service.WriterService;
import service.WriterServiceImpl;

/**
 * Servlet implementation class WriterController
 */
@WebServlet("/list")
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		ListServiceImpl service = new ListServiceImpl();
		
		//페이징
		int pageRow = 3; //한 페이지당 몇개를 보여줄 것인가?
		int pageNum = 1; //현재페이지
		int pagingNum = 5; //글 목록 넘어갈때 누르는 숫자를 5개 보여주겠다.
		
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		int startPage = (pageNum - 1) * pageRow;
		//LIMIT 할때 쓰는 것.
		
		BoardInfo boardInfo = service.boardInfo(startPage, pageRow);
		
		//글번호 넘버링
		int totalNum = boardInfo.getTotalRow() - ((pageNum - 1) * pageRow);
		
		//페이징
		int startNum = (((pageNum-1)/pagingNum)*pagingNum)+1;   //시작 페이지
		
		request.setAttribute("startNum", startNum);
		request.setAttribute("pagingNum", pagingNum);
		
		request.setAttribute("pageRow", pageRow);
		
		request.setAttribute("totalNum", totalNum);
		request.setAttribute("boardInfo", boardInfo);
		request.setAttribute("pageNum", pageNum);
		
		/*
		 * Collection<BoardVO> list = service.read(); int totalRow = service.totalRow();
		 * 
		 * request.setAttribute("list", list); request.setAttribute("totalRow",
		 * totalRow);
		 */
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
		dispatcher.forward(request, response);
	}


}



