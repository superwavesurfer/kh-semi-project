package com.kh.bookBoard.controller;

import java.io.File;


import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.bookBoard.model.service.BookBoardService;
import com.kh.bookBoard.model.vo.Book;
import com.kh.bookBoard.model.vo.Cover;
import com.kh.common.Controller.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class bookUpdateController
 */
@WebServlet("/update.bo")
public class bookUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bookUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		
		
		if(ServletFileUpload.isMultipartContent(request)) {
			int maxSize = 10*1024*1024;
			
			String savePath = request.getSession().getServletContext().getRealPath("/resources/bookBoard_upfiles");
			System.out.println(savePath);
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			int bno = Integer.parseInt(multiRequest.getParameter("bno"));
			String title = multiRequest.getParameter("title");
			
			String genre = multiRequest.getParameter("genre");
			
			String author = multiRequest.getParameter("author");
			String pubdateR = multiRequest.getParameter("pubdate");
			Date pubdate = Date.valueOf(pubdateR);
			String bookContent = multiRequest.getParameter("bookContent");
			
			Book b = new Book();
			
			b.setBookNo(bno);
			b.setBookTitle(title);
			b.setGenre(genre);
			b.setAuthor(author);
			b.setCreateBook(pubdate);
			b.setBookInfo(bookContent);
			
			Cover c= null;
			if(multiRequest.getOriginalFileName("reUpfile")!=null) {
				c = new Cover();
				c.setOriginName(multiRequest.getOriginalFileName("reUpfile"));	//??????????????? ??????
				c.setChangeName(multiRequest.getFilesystemName("reUpfile"));	//???????????? ??????
				c.setFile_path("/resources/bookBoard_upfiles/");
				
				
				c.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo")));
					
				//????????? ??????????????? ????????? ????????? ?????? ??????????????? ????????? ??????????????? ???????????? ????????????.
				new File(savePath + multiRequest.getParameter("originFileName")).delete();
				
				
				
			}
			
			int result = new BookBoardService().updateBook(b, c);
			
			
			if(result>0) {
				response.sendRedirect(request.getContextPath()+"/detail.bo?bno="+bno);
			}else {
				System.out.println("??????");
			}
			
			
			
			
			
		}
		
		
	}

}
