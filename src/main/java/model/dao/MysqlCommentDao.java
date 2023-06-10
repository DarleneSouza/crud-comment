package model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Comment;
import model.ModelException;
import model.Post;
import model.User;

public class MysqlCommentDao implements CommentDao {



    @Override
    public boolean save(Comment comment) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "INSERT INTO comments (content, post_id, user_id, reacao, comment_date) VALUES (?, ?, ?, ?, ?)";
        db.prepareStatement(sql);
        db.setString(1, comment.getContent());
        db.setInt(2, comment.getPost().getId());
        db.setInt(3, comment.getUser().getId());
        db.setString(4, comment.getReacao());
        db.setDate(5, comment.getCommentDate());
        int rowsAffected = db.executeUpdate();
        return rowsAffected > 0;
    }

    @Override
    public boolean update(Comment comment) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "UPDATE comments SET content = ?, post_id = ?, user_id = ?, reacao = ? WHERE id = ?";
        db.prepareStatement(sql);
        db.setString(1, comment.getContent());
        db.setInt(2, comment.getPost().getId());
        db.setInt(3, comment.getUser().getId());
        db.setString(4, comment.getReacao());
        db.setInt(5, comment.getId());
        int rowsAffected = db.executeUpdate();
        return rowsAffected > 0;
    }

    @Override
    public boolean delete(Comment comment) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "DELETE FROM comments WHERE id = ?";
        db.prepareStatement(sql);
        db.setInt(1, comment.getId());
        int rowsAffected = db.executeUpdate();
        return rowsAffected > 0;
    }

    @Override
    public List<Comment> listAll() throws ModelException {
        DBHandler db = new DBHandler();
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments";
        db.createStatement();
        db.executeQuery(sql);

        while (db.next()) {
            Comment comment = createComment(db);
            comments.add(comment);
        }

        return comments;
    }

    @Override
    public Comment findById(int id) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "SELECT * FROM comments WHERE id = ?";
        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();

        if (db.next()) {
            return createComment(db);
        }

        return null;
    }

    private Comment createComment(DBHandler db) throws ModelException {
        int id = db.getInt("id");
        String content = db.getString("content");
        String Reacao = db.getString("reacao");
        UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
        User user = userDAO.findById(db.getInt("user_id"));
        PostDAO postDAO = DAOFactory.createDAO(PostDAO.class);
        Post post = postDAO.findById(db.getInt("post_id"));
        Date commentDate = db.getDate("comment_date");

        Comment comment = new Comment(id, content, post, user, Reacao, commentDate);
        return comment;
    }
}