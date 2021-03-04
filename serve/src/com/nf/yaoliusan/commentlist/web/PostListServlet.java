package com.nf.yaoliusan.commentlist.web;

import com.nf.yaoliusan.commentlist.bean.Post;
import com.nf.yaoliusan.commentlist.dao.PostDAO;
import com.nf.yaoliusan.commentlist.vo.ResultVO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/post/list")
// 获取所有博客（doGet 方法）
public class PostListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostDAO dao = new PostDAO();
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

            List<Post> posts = dao.getPostByPage(currentPage, pageSize);
            int total = dao.getPostMount();

            // resp.setContentType("text/html");
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.print(ResultVO.ok(posts).setPage(currentPage, pageSize, total).toJSON());
        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter writer = resp.getWriter();
            writer.print(ResultVO.err(114, e.getLocalizedMessage()).toJSON());
        }
    }
}
