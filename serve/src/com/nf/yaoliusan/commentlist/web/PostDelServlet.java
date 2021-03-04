package com.nf.yaoliusan.commentlist.web;

import com.nf.yaoliusan.commentlist.dao.PostDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/post/del")
// 删除博客（doGet 方法）
public class PostDelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            new PostDAO().deletePost(id);
            resp.getWriter().print(id);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().print("-1");
        }
    }
}
