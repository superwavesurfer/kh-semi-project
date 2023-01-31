package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberUpdateController
 */
@WebServlet("/update.me")
public class MemberUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateController() {
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
		
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("loginUser");
		
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String userNickname = request.getParameter("userNickname");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String genreNo = request.getParameter("genreNo");
		String address = request.getParameter("address");
		
		m.setUserName(userName);
		m.setUserNickname(userNickname);
		m.setPhone(phone);
		m.setEmail(email);
		m.setGenreNo(genreNo);
		m.setAddress(address);
		
		Member updateUser = new MemberService().updateMember(m);
		
		if(updateUser != null) {
			session.setAttribute("loginUser", updateUser);
			session.setAttribute("alertMsg", "회원 정보 수정완료");
			
			response.sendRedirect(request.getContextPath()+"/mypage.me");
		} else {
			request.setAttribute("errorMsg", "회원정보 수정 실패");
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
		}
		
	}

}
