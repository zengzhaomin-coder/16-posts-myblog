package com.nf.yaoliusan.commentlist.dao;

import com.nf.yaoliusan.commentlist.bean.Post;
import com.nf.yaoliusan.commentlist.utils.DBHelper;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class PostDAO {
    /**
     * 获取所有文章
     * @return 所有的 posts
     * @throws Exception
     */
    public List<Post> getAllPosts() throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "select id, title, content, author, cover, created from post order by created desc";
        try {
            return new QueryRunner().query(
                    conn, sql, new BeanListHandler<Post>(Post.class));
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * @param post 要插入的文章
     * @return 带 Id 的文章
     * @throws Exception
     */
    public Post addPost(Post post) throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "insert into post (title, content, author, cover) values (?, ?, ?, ?)";
        Object[] params = {
                post.getTitle(), post.getContent(), post.getAuthor(), post.getCover()
        };
        try {
            QueryRunner runner = new QueryRunner();
            BigDecimal res = runner.insert(conn, sql, new ScalarHandler<BigDecimal>(), params);
            post.setId(res.longValue());
            return post;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 删除某篇 post
     * @param id post 的主键
     * @throws Exception
     */
    public void deletePost(String id) throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "delete from post where id = ?";
        try {
            new QueryRunner().update(conn, sql, id);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 根据 PostId 获取某篇文章
     * @param id post 的主键
     * @return post
     * @throws Exception
     */
    public Post getPostById(int id) throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "select id, title, content, author, cover, likes, created from post where id = ?";
        try {
            return new QueryRunner().query(
                    conn, sql, new BeanHandler<Post>(Post.class), id);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 分页的功能
     * @param currentPage
     * @param limit
     * @return
     * @throws Exception
     */
    public List<Post> getPostByPage(int currentPage, int limit) throws Exception {
        Connection conn = DBHelper.getConnection();
//        String sql = "select * from post fetch next ? rows offset ? rows only order by id";
        String sql = "select * from " +
                "(select row_number() over (order by created desc) as rn, id ,title, content, author, cover, created from post)" +
                "as s where rn between ? and ?";
        try {
            return new QueryRunner().query(
                    conn, sql, new BeanListHandler<Post>(Post.class),
                    (currentPage - 1) * limit + 1,
                    currentPage * limit);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 统计所有数量
     * @return
     * @throws Exception
     */
    public int getPostMount() throws Exception {
        Connection conn = DBHelper.getConnection();
        try {
            String sql = "select count(*) from post";
            return (int) new QueryRunner().query(conn, sql, new ScalarHandler<>());
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 添加点赞数量
     * @param id
     * @return 某篇 post
     * @throws Exception
     */
    public int like(String id) throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "update post set likes = likes + 1 where id = ?";
        try {
            QueryRunner runner = new QueryRunner();
            runner.update(conn, sql, id);
            sql = "select likes from post where id = ?";
            return (int) runner.query(conn, sql, new ScalarHandler<>(), id);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    // 测试方法，统计所有的博客总数
    public static void main(String[] args) throws Exception {
        PostDAO postDAO = new PostDAO();
        System.out.println(postDAO.getPostMount());
    }
}
