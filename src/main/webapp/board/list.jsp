<%@page import="domain.BoardInfo"%>
<%@page import="java.util.Iterator"%>
<%@page import="domain.BoardVO"%>
<%@page import="java.util.Collection"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
BoardInfo boardInfo = (BoardInfo) request.getAttribute("boardInfo");
Collection<BoardVO> list = boardInfo.getList();
int totalRow = boardInfo.getTotalRow();

int totalNum = (Integer) request.getAttribute("totalNum");
int pageNum = (Integer) request.getAttribute("pageNum");
int pageRow = (Integer) request.getAttribute("pageRow");;

int pagingNum = (Integer) request.getAttribute("pagingNum");
int startNum = (Integer) request.getAttribute("startNum");

String field = (String)request.getAttribute("field");
String keyWord = (String)request.getAttribute("keyWord");

String titleF = (String)request.getAttribute("titleF");
String contentF = (String)request.getAttribute("contentF");
String writerF = (String)request.getAttribute("writerF");


int lastPage = totalRow/pageRow+((totalRow%pageRow == 0)?0:1);
if(pageNum > lastPage || pageNum < 1) {
	/* response.sendRedirect("http://www.naver.com"); */
}
//Collection<BoardVO> list = (Collection)request.getAttribute("list");
//int totalRow = (Integer)request.getAttribute("totalRow");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>목록</title>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div>
			<div class="pull-left">전체글 수 : <%=totalRow%></div><br/>
<%-- 			<div>
			<form>
			제목:<input type="text" name="titleF" value="<%=titleF %>" class="form-control" style="display:inline-block; width:10%"><br/>
				내용:<input type="text" name="contentF" value="<%=contentF %>" class="form-control" style="display:inline-block; width:10%"><br/>
				작성자:<input type="text" name="writerF" value="<%=writerF %>" class="form-control" style="display:inline-block; width:10%"><br/>
				<button class="btn btn-default">검색</button>
				</form>
			</div> --%>
			<div class="pull-right" style="width:310px">
				<form>
				<select name="field" class="form-control" style="display:inline-block; width:30%">
					<option value="">전체</option>
					<option value="title"<%="title".equals(field)?"selected='selected'":"" %>>제목</option>
					<option value="content" <%="content".equals(field)?"selected='selected'":"" %>>내용</option>
					<option value="titleContent" <%="titleContent".equals(field)?"selected='selected'":"" %>>제목+내용</option>
				</select>
				<input type="text" name="keyWord" value="<%=keyWord %>" class="form-control" style="display:inline-block; width:50%">
				<button class="btn btn-default">검색</button>
				</form>
			</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>첨부파일</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<%
				Iterator<BoardVO> it = list.iterator();
				while (it.hasNext()) {
					BoardVO vo = it.next();
				%>
				<tr>
					<td><%=totalNum--%></td>
					<td><a href="view?num=<%=vo.getNum()%>"><%=vo.getTitle()%></a></td>
					<td><%=vo.getWriter()%></td>
					<td><%=vo.getRealFileName()!=null?"<img src='img/pick.png' style='width:10px;height:10px;'>":""%></td>
					<td><%=vo.getWriterDate()%></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>

		<nav style="text-align: center;">
			<ul class="pagination">
				<%
				if (pageNum == 1) {
				%>
				<li class="disabled"> 
					<span aria-hidden="true">&laquo;</span>
				</li>
				<!-- li를 주석처리하면 1페이지일때 이전버튼이 사라진다. -->
				<%
				} else {
				%>
				<li>
					<a href="?pageNum=<%=pageNum-1 %>" aria-label="Previous"> 
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
				<%
				}
				%>
				
				<%
				for(int i = startNum; i <= (startNum+pagingNum)-1; i++){
					if(i > lastPage) break;
					if(pageNum == i) {
						%>
						<li class="active"><a><%=i%></a></li>
						<%
					} else {
						%>
						<li><a href="?pageNum=<%=i%>&field=<%=field%>&keyWord=<%=keyWord%>"><%=i%></a></li>
						<%
					}
				
				}
				%>
<!-- 			
				<li><a href="?pageNum=1">1</a></li>
				<li><a href="?pageNum=2">2</a></li>
				<li><a href="?pageNum=3">3</a></li>	
				<li><a href="?pageNum=4">4</a></li>
				<li><a href="?pageNum=5">5</a></li> -->
				<%
				if(lastPage == pageNum){
				%>
				<li class="disabled">
					<span aria-hidden="true">&raquo;</span>
				</li>
				<%
				} else {
					%>
				<li>
					<a href="?pageNum=<%=pageNum+1 %>" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a>
				</li>
				<%
				}
				%>
			</ul>
		</nav>
		<div class="pull-right">
			<a href="writer" class="btn btn-default">글쓰기</a>
		</div>
	</div>
</body>
</html>
