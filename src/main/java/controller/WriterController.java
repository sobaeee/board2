package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import domain.BoardVO;
import service.WriterService;
import service.WriterServiceImpl;

/**
 * Servlet implementation class WriterController
 */
@WebServlet("/writer")
public class WriterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("writer.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String saveFolder ="upload";
		
		ServletContext context = request.getServletContext();
		String realFolder = context.getRealPath(saveFolder);
		
		File targetDir = new File(realFolder); //폴더가 없으면 폴더를 생성해라
		if(!targetDir.exists()) {
			targetDir.mkdir();
		}
		
		int maxSize = 10*1024*1024; //10Mb. 10을 20으로 바꾸면 20Mb
		String encType ="UTF-8";
		
		MultipartRequest multi = 
				new MultipartRequest(request, realFolder, maxSize , encType , new DefaultFileRenamePolicy());
		//넘어온 값을 변수에 저장
		String title = multi.getParameter("title");
		String content = multi.getParameter("content");
		String writer = multi.getParameter("writer");
		
		String realFileName = multi.getOriginalFileName("upfile");
		String realSaveFileName = multi.getFilesystemName("upfile");
		
		
//		String title = request.getParameter("title");
//		String content = request.getParameter("content");
//		String writer = request.getParameter("writer");
//		request는 파일 업로드를 전송하지 않기 때문에 multi 라는 객체를 만들어서 수정함.

		BoardVO vo = new BoardVO();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setWriter(writer);
		vo.setRealFileName(realFileName);
		vo.setRealSaveFileName(realSaveFileName);
		System.out.println(vo);
		
		WriterServiceImpl service = new WriterServiceImpl();
		service.insert(vo);
		//페이지 이동
		response.sendRedirect("list");
		
		
	}

}



