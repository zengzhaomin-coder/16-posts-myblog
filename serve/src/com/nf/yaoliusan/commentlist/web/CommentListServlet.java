package com.nf.yaoliusan.commentlist.web;

import com.nf.yaoliusan.commentlist.bean.Comment;
import com.nf.yaoliusan.commentlist.dao.CommentDAO;
import com.nf.yaoliusan.commentlist.vo.ResultVO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/comments")
// 获取所有评论（doGet 方法）
public class CommentListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int currentPage, pageSize;
            try {
                currentPage = Integer.parseInt(req.getParameter("curr"));
            } catch (Exception e) {
                currentPage = 1;
            }
            try {
                pageSize = Integer.parseInt(req.getParameter("size"));
            } catch (Exception e) {
                pageSize = 5;
            }

            int postid = Integer.parseInt(req.getParameter("postid"));

            CommentDAO commentDAO = new CommentDAO();
            List<Comment> comments = commentDAO.getCommentsByPostIdByPage(postid, currentPage, pageSize);
            PrintWriter writer = resp.getWriter();
            writer.print(ResultVO.ok(comments).toJSON());
        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter writer = resp.getWriter();
            writer.print(ResultVO.err(113, e.getLocalizedMessage()).toJSON());
        }
    }
}
